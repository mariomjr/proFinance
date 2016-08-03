package org.com.proFinance.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import org.com.proFinance.entity.ADUser;


/**
 * @author mario.hjunior
 *
 */
@Named
@ApplicationScoped
public class LDAPUtil {
	
	public static final String pathAd = "DC=SAGAGYN,DC=LOCAL";
	
	public static final String pathAdMalls = "DC=GRUPOSAGA,DC=local";
	
	public static final String urlAd = "ldap://192.6.1.152:389";
	
	public static final String urlAdMalls = "ldap://10.62.50.10:389";
	
	public static final String usuarioAutenticacaoMalls = "proMalls@gruposaga.local";
	
	public static final String senhaUsuarioMalls = "@saga2016";
	
	public static final String usuarioAutenticacao = "cadadmin@sagagyn.local";
	
	public static final String senhaUsuario = "@saga2015";
	
	public static final String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	
	public static final String tipoSecurityAutenticator = "simple";
	
	@SuppressWarnings("unused")
	public static boolean autenticarAD(String login, String senha){
		
		List<ADUser> listUserAD = new ArrayList<ADUser>();
		StringBuilder pesquisa = new StringBuilder();
		pesquisa.append("(&(objectClass=user)");
		pesquisa.append("(sAMAccountName="+login+")");
		pesquisa.append("(|(userAccountControl=66048)(userAccountControl=512))");
		pesquisa.append(")");

		listUserAD = buscaLDAP(pesquisa.toString());
		 InitialDirContext context = null;
		if(listUserAD.isEmpty() == false){
			ADUser adUser = listUserAD.get(0);
			try {
		        context = new InitialDirContext(getPropertiesLDAPMalls(adUser.getDistinguishedName(), senha));
		        return true;
		    } catch (Exception e) {
		    	e.printStackTrace();
		        return false;
		    }
		}else{
			return false;
		}
	}
	
	public static ADUser getADUserByLogin (String login){
		List<ADUser> listUserAD = new ArrayList<ADUser>();

		StringBuilder pesquisa = new StringBuilder();
		pesquisa.append("(&(objectClass=user)");
		pesquisa.append("(sAMAccountName="+login+")");
		pesquisa.append("(|(userAccountControl=66048)(userAccountControl=512))");
		pesquisa.append(")");

		listUserAD = buscaLDAP(pesquisa.toString());
		
		if(!listUserAD.isEmpty()){
			return listUserAD.get(0);
			
		}else{
			
			pesquisa = new StringBuilder();
			pesquisa.append("(&(objectClass=user)");
			pesquisa.append("(sAMAccountName="+login+")");
			pesquisa.append("(userAccountControl=66048)"); 
			pesquisa.append(")");

			listUserAD = buscaLDAP(pesquisa.toString());
			
			if(!listUserAD.isEmpty()){
				return listUserAD.get(0);
			}
		}
		
		return null;

	}
	
	@SuppressWarnings("rawtypes")
	public static List<ADUser> buscaLDAP(String consulta){
		
		LdapContext contexto = null;
		ADUser adUser = null;
		List<ADUser> listRetorno = new ArrayList<ADUser>();
		
		try {
			NamingEnumeration results = null;
			contexto = new InitialLdapContext(getPropertiesLDAPMalls(usuarioAutenticacaoMalls, senhaUsuarioMalls), null);
			SearchControls search = new SearchControls();
			String[] atributosRetorno = getCamposRetorno();
			search.setSearchScope(SearchControls.SUBTREE_SCOPE);
			search.setReturningAttributes(atributosRetorno);


			results = contexto.search(pathAdMalls,consulta,search);
			
			while (results != null && results.hasMoreElements()) {
		
				try {
					adUser = new ADUser();
					SearchResult searchResult = (SearchResult) results.next();
					
                    String retorno = null;

                    for (int i = 0; i < atributosRetorno.length; i++) {
                        if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {
                            retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());
                            setPropertyToAdUser(adUser, atributosRetorno[i], retorno.split(":")[1]);
                        }else{
                        	setPropertyToAdUser(adUser, atributosRetorno[i], "Não disponível");
                        }

                    }
                 
		
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				listRetorno.add(adUser);
			}
			
			contexto.close();
			return listRetorno;	
			
		} catch (SizeLimitExceededException sizeLimit) {

			System.out.println("Limite excedeu...");
			sizeLimit.printStackTrace();

		} catch (NamingException ex) {
			ex.printStackTrace();
			System.out.println("NamingException is: " + ex);
		}
		
		return new ArrayList<ADUser>();
	}
	
	@SuppressWarnings("rawtypes")
	public static List<ADUser> buscaLDAPPaginado(String consulta){
		
		LdapContext contexto = null;
		ADUser adUser = null;
		List<ADUser> listRetorno = new ArrayList<ADUser>();
		
		int pageSize = 1000;
		byte[] cookie = null;
		try {
			NamingEnumeration results = null;
			contexto = new InitialLdapContext(getPropertiesLDAP(usuarioAutenticacao, senhaUsuario), null);
			SearchControls search = new SearchControls();
			String[] atributosRetorno = getCamposRetorno();
			search.setSearchScope(SearchControls.SUBTREE_SCOPE);
			search.setReturningAttributes(atributosRetorno);
			
			contexto.setRequestControls(new Control[] { new PagedResultsControl(
					pageSize, Control.CRITICAL) });
			
			
			
			do {
				
				results = contexto.search(pathAd,consulta,search);
				
				while (results != null && results.hasMoreElements()) {
			
					try {
						adUser = new ADUser();
						SearchResult searchResult = (SearchResult) results.next();
						
	                    String retorno = null;
	
	                    for (int i = 0; i < atributosRetorno.length; i++) {
	                        if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {
	                            retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());
	                            setPropertyToAdUser(adUser, atributosRetorno[i], retorno.split(":")[1]);
	                        }else{
	                        	setPropertyToAdUser(adUser, atributosRetorno[i], "N�o dispon�vel");
	                        }
	
	                    }
	                 
			
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					listRetorno.add(adUser);
				}
					Control[] controls = contexto.getResponseControls();
					if (controls != null) {
						for (int i = 0; i < controls.length; i++) {
							if (controls[i] instanceof PagedResultsResponseControl) {
								PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
								cookie = prrc.getCookie();
							} else {
								
							}
						}
					}

					contexto.setRequestControls(new Control[] { new PagedResultsControl(
							pageSize, cookie, Control.CRITICAL) });
				
			}while (cookie != null);
			
			contexto.close();
			return listRetorno;	
			
		} catch (SizeLimitExceededException sizeLimit) {

			System.out.println("Limite excedeu...");
			sizeLimit.printStackTrace();

		} catch (NamingException ex) {
			ex.printStackTrace();
			System.out.println("NamingException is: " + ex);

		} catch (IOException ie) {
			System.err.println("LDAP - IOException ");
			ie.printStackTrace();
		}
		
		return new ArrayList<ADUser>();
	}
	
	

	
	private static void setPropertyToAdUser(ADUser adUserObject, String name, String value) throws Exception {
		value = value.trim();
        if (name.equalsIgnoreCase("CN")) {
            adUserObject.setCN(value);
            return;
        }
        if (name.equalsIgnoreCase("description")) {
            adUserObject.setDescription(value);
            return;
        }
        if (name.equalsIgnoreCase("displayName")) {
            adUserObject.setDisplayName(value);
            return;
        }
        if (name.equalsIgnoreCase("DN")) {
            adUserObject.setDN(value);
            return;
        }
        if (name.equalsIgnoreCase("givenName")) {
            adUserObject.setGivenName(value);
            return;
        }

        if (name.equalsIgnoreCase("homeDrive")) {
            adUserObject.setHomeDrive(value);
            return;
        }
        if (name.equalsIgnoreCase("name")) {
            adUserObject.setName(value);
            return;
        }
        if (name.equalsIgnoreCase("objectCategory")) {
            adUserObject.setObjectCategory(value);
            return;
        }
        if (name.equalsIgnoreCase("objectClass")) {
            adUserObject.setObjectClass(value);
            return;
        }
        if (name.equalsIgnoreCase("physicalDeliveryOfficeName")) {
            adUserObject.setPhysicalDeliveryOfficeName(value);
            return;
        }

        if (name.equalsIgnoreCase("profilePath")) {
            adUserObject.setProfilePath(value);
            return;
        }
        if (name.equalsIgnoreCase("sAMAccountName")) {
            adUserObject.setsAMAccountName(value);
            return;
        }
        if (name.equalsIgnoreCase("SN")) {
            adUserObject.setSN(value);
            return;
        }
        if (name.equalsIgnoreCase("userAccountControl")) {
            adUserObject.setUserAccountControl(value);
            return;
        }
        if (name.equalsIgnoreCase("userPrincipalName")) {
            adUserObject.setUserPrincipalName(value);
            return;
        }

        if (name.equalsIgnoreCase("homeMDB")) {
            adUserObject.setHomeMDB(value);
            return;
        }
        if (name.equalsIgnoreCase("legacyExchangeDN")) {
            adUserObject.setLegacyExchangeDN(value);
            return;
        }
        if (name.equalsIgnoreCase("mail")) {
            adUserObject.setMail(value);
            return;
        }
        if (name.equalsIgnoreCase("mAPIRecipient")) {
            adUserObject.setmAPIRecipient(value);
            return;
        }
        if (name.equalsIgnoreCase("mailNickname")) {
            adUserObject.setMailNickname(value);
            return;
        }
        if (name.equalsIgnoreCase("c")) {
            adUserObject.setC(value);
            return;
        }
        if (name.equalsIgnoreCase("company")) {
            adUserObject.setCompany(value);
            return;
        }
        if (name.equalsIgnoreCase("department")) {
            adUserObject.setDepartment(value);
            return;
        }
        if (name.equalsIgnoreCase("homephone")) {
            adUserObject.setHomephone(value);
            return;
        }
        if (name.equalsIgnoreCase("l")) {
            adUserObject.setL(value);
            return;
        }
        if (name.equalsIgnoreCase("location")) {
            adUserObject.setLocation(value);
            return;
        }
        if (name.equalsIgnoreCase("manager")) {
            adUserObject.setManager(value);
            return;
        }
        if (name.equalsIgnoreCase("mobile")) {
            adUserObject.setMobile(value);
            return;
        }
        if (name.equalsIgnoreCase("OU")) {
            adUserObject.setOU(value);
            return;
        }
        if (name.equalsIgnoreCase("postalCode")) {
            adUserObject.setPostalCode(value);
            return;
        }
        if (name.equalsIgnoreCase("st")) {
            adUserObject.setSt(value);
            return;
        }
        if (name.equalsIgnoreCase("streetAddress")) {
            adUserObject.setStreetAddress(value);
            return;
        }
        if (name.equalsIgnoreCase("telephoneNumber")) {
            adUserObject.setTelephoneNumber(value);
            return;
        }
        if (name.equalsIgnoreCase("memberOf")) {
            adUserObject.setMemberOf(value);
            return;
        }
        
        if (name.equalsIgnoreCase("lastLogon")) {
            adUserObject.setLastLogon(value);
            return;
        }
        if (name.equalsIgnoreCase("distinguishedName")) {
            adUserObject.setDistinguishedName(value);
            return;
        }
        if (name.equalsIgnoreCase("title")) {
            adUserObject.setTitle(value);
            return;
        }
        if (name.equalsIgnoreCase("accountExpires")) {
            adUserObject.setAccountExpires(value);
            return;
        }
        if (name.equalsIgnoreCase("badPasswordTime")) {
            adUserObject.setBadPasswordTime(value);
            return;
        }
        if (name.equalsIgnoreCase("pwdLastSet")) {
            adUserObject.setPwdLastSet(value);
            return;
        }
        if (name.equalsIgnoreCase("operatingSystem")) {
            adUserObject.setOperatingSystem(value);
            return;
        }
        if (name.equalsIgnoreCase("whenCreated")) {
            adUserObject.setWhenCreated(value);
            return;
        }

    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Hashtable getPropertiesLDAP(String usuario, String senha){
		Hashtable env = new Hashtable(5, 0.75f);
		env.put(Context.SECURITY_AUTHENTICATION, tipoSecurityAutenticator);
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, senha);
		env.put(Context.INITIAL_CONTEXT_FACTORY,contextFactory);
		env.put(Context.PROVIDER_URL, urlAd);
		
		return env;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Hashtable getPropertiesLDAPMalls(String usuario, String senha){
		Hashtable env = new Hashtable(5, 0.75f);
		env.put(Context.SECURITY_AUTHENTICATION, tipoSecurityAutenticator);
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, senha);
		env.put(Context.INITIAL_CONTEXT_FACTORY,contextFactory);
		env.put(Context.PROVIDER_URL, urlAdMalls);
		
		return env;
	}
	
	
	public static String[] getCamposRetorno(){
		String[] campos = { "CN", "description", "displayName", "DN",
				"givenName", "homeDrive", "name", "objectCategory",
				"objectClass", "physicalDeliveryOfficeName", "profilePath",
				"sAMAccountName", "SN", "userAccountControl",
				"userPrincipalName", "homeMDB", "legacyExchangeDN", "mail",
				"mAPIRecipient", "mailNickname", "c", "company",
				"department", "homephone", "l", "location", "manager",
				"mobile", "OU", "postalCode", "st", "streetAddress",
				"telephoneNumber", "uid", "memberOf", "lastLogon", "distinguishedName",
				"title","accountExpires", "badPasswordTime","pwdLastSet","operatingSystem","whenCreated" };
		return campos;
	}
	
	public static String nullToNaoDisponivel(String value) {
        return value == null ? "Não disponível" : value;
    }

}

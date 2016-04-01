package org.com.proFinance.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;


public class UtilSelectItem {


	public static List<SelectItem> getListaSelectItem(@SuppressWarnings("rawtypes") List listaConsulta, String campoDescricao) throws Exception {
		return getListaSelectItem(listaConsulta, campoDescricao, true);
	}
	
	public static SelectItem getSelectItem(Object item,String campoDescricao) throws Exception {
		
		
		return new SelectItem(item, capitalizeString( (String) invocarMetodoGet(item, campoDescricao)));
	}
	
  
	public static List<SelectItem> getListaSelectItem(@SuppressWarnings("rawtypes") List listaConsulta, String campoDescricao, boolean emBranco) throws Exception {
    	List<SelectItem> listaDeSelectItem = new ArrayList<SelectItem>();
    	if (emBranco) {
    		listaDeSelectItem.add(new SelectItem(new Integer(0), ""));
    	}
    	try {
            if (listaConsulta != null){
            	for (Object item : listaConsulta) {
            		listaDeSelectItem.add(new SelectItem(item, capitalizeString( (String) invocarMetodoGet(item, campoDescricao))));
				} 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDeSelectItem;
    }
    
	
	public static List<SelectItem> getListaSelectItem(String[] listArray, boolean emBranco) throws Exception {
    	List<SelectItem> listaDeSelectItem = new ArrayList<SelectItem>();
    	if (emBranco) {
    		listaDeSelectItem.add(new SelectItem(new Integer(0), ""));
    	}
    	try {
            if (listArray != null){
            	for (int i = 0; i < listArray.length; i++) {
				
            		listaDeSelectItem.add(new SelectItem(listArray[i], capitalizeString( (String) invocarMetodoGet(listArray[i], listArray[i].toString()))));
            		
				}
            	
            	
				 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDeSelectItem;
    }
    
    
    private static String getNomeDoMetodoGet(String nomeDoAtributo) {
        return "get" + nomeDoAtributo.substring(0, 1).toUpperCase() + nomeDoAtributo.substring(1);
    }

    private static Object invocarMetodoGet(Object objetoDoMetodo, String nomeDaPropriedade) {
        try {
            Method metodo = objetoDoMetodo.getClass().getMethod(getNomeDoMetodoGet(nomeDaPropriedade), (Class[]) null);
            return metodo.invoke(objetoDoMetodo, (Object[]) null);
        } catch (Throwable e) {
            return objetoDoMetodo;
        }
    }
    
    public static String capitalizeString(String string) {
    	  char[] chars = string.toLowerCase().toCharArray();
    	  boolean found = false;
    	  for (int i = 0; i < chars.length; i++) {
    	    if (!found && Character.isLetter(chars[i])) {
    	      chars[i] = Character.toUpperCase(chars[i]);
    	      found = true;
    	    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
    	      found = false;
    	    }
    	  }
    	  return String.valueOf(chars);
    	}

    public static List<SelectItem> getListEnum(Object[] objects){
    	
    	List<SelectItem> items = new ArrayList<SelectItem>();
    	
    	for (int i = 0; i < objects.length; i++) {
    		
			items.add(new SelectItem(objects[i], invocarMetodoGet(objects[i], "label").toString()) );
		}
    	
    	
    	return items;
    	
    }
    
    public static List<SelectItem> getListaSelectItemDoEnum(Class classe, String label, boolean emBranco){
    	EnumSet<?> enumSet = EnumSet.allOf(classe);
        Object[] enums = enumSet.toArray();
    	List<SelectItem> listaSelectItemEnums = new ArrayList<SelectItem>();
        
        if (emBranco) {
			listaSelectItemEnums.add(new SelectItem("", ""));
		}
    	for (Object object : enums) {
			listaSelectItemEnums.add(new SelectItem(object, (String) invocarMetodoGet(object, label)));
		}
    	return listaSelectItemEnums;
    }
}

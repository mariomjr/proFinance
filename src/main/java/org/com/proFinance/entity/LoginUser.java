package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class LoginUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 193639066436580798L;


	@Id
    @GeneratedValue
	private Long id;
	
	@Transient
	private String password;
	
	@Column(nullable=false, unique = true)
	private String login;
	
	private Calendar dataUltimaEntrada;
	
	@Transient
	private ADUser adUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ADUser getAdUser() {
		return adUser;
	}

	public void setAdUser(ADUser adUser) {
		this.adUser = adUser;
	}

	public Calendar getDataUltimaEntrada() {
		return dataUltimaEntrada;
	}

	public void setDataUltimaEntrada(Calendar dataUltimaEntrada) {
		this.dataUltimaEntrada = dataUltimaEntrada;
	}
	
}

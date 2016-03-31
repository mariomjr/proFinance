package org.com.proFinance.entity;

import java.io.Serializable;

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
	
	private String password;
	
	@Column(nullable=false, unique = true)
	private String login;
	
	@Transient
	private boolean logado = false;

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

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	
}

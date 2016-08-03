package org.com.proFinance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Empresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8514449540597480332L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String nome;
	
	private String cnpj;
	
	@ManyToOne
	@JoinColumn(name = "socioEmpresa_id") 
	private SocioEmpresa socioEmpresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public SocioEmpresa getSocioEmpresa() {
		return socioEmpresa;
	}

	public void setSocioEmpresa(SocioEmpresa socioEmpresa) {
		this.socioEmpresa = socioEmpresa;
	}

}

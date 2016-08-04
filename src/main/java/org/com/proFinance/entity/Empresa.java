package org.com.proFinance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.com.proFinance.enuns.SimNao;

@Entity
public class Empresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8514449540597480332L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String randomId;
	
	private String nome;
	
	private String cnpj;
	
	@ManyToOne
	@JoinColumn(name = "socioEmpresa_id") 
	private SocioEmpresa socioEmpresa;
	
	@Enumerated(EnumType.STRING)
	private SimNao ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRandomId() {
		return randomId;
	}

	public void setRandomId(String randomId) {
		this.randomId = randomId;
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

	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((randomId == null) ? 0 : randomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Empresa))
			return false;
		Empresa other = (Empresa) obj;
		if (getRandomId() == null) {
			if (other.getRandomId() != null)
				return false;
		} else if (!getRandomId().equals(other.getRandomId()))
			return false;
		return true;
	}

}

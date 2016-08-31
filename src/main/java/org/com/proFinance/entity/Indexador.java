package org.com.proFinance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.com.proFinance.enuns.SimNao;

@Entity
public class Indexador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3881736421855032098L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String descricao;
	
	private Long codigo;
	
	@Enumerated(EnumType.STRING)
	private SimNao ativo;
	
	@Transient
	private String mesAux;
	@Transient
	private String anoAux;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}

	public String getMesAux() {
		return mesAux;
	}

	public void setMesAux(String mesAux) {
		this.mesAux = mesAux;
	}

	public String getAnoAux() {
		return anoAux;
	}

	public void setAnoAux(String anoAux) {
		this.anoAux = anoAux;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Indexador))
			return false;
		Indexador other = (Indexador) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}

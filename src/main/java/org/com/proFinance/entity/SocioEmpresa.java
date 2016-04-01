package org.com.proFinance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.com.proFinance.enuns.SimNao;

@Entity
public class SocioEmpresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5685646148332061093L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String nome;
	
	private String sigla;
	
	private String cpfCnpj;
	
	@Enumerated(EnumType.STRING)
	private SimNao ativo;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}
}

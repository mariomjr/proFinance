package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Projeto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9160867114450100205L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String nome;
	
	private Calendar dataInicial;
	
	private Calendar dataFinalPrevista;

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

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinalPrevista() {
		return dataFinalPrevista;
	}

	public void setDataFinalPrevista(Calendar dataFinalPrevista) {
		this.dataFinalPrevista = dataFinalPrevista;
	}
	
}

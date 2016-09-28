package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	
	@OneToMany(mappedBy = "socioEmpresa", targetEntity= Empresa.class, cascade=CascadeType.ALL)
	private List<Empresa> listEmpresa;
	
	@Transient
	private List<Empresa> listEmpresaAtivas;
	
	@Enumerated(EnumType.STRING)
	private SimNao ativo;

	@Transient
	private Double valorTotalCredito;
	
	@Transient
	private Double valorTotalDebito;

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

	public SimNao getAtivo() {
		return ativo;
	}

	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}

	public List<Empresa> getListEmpresa() {
		if(listEmpresa == null){
			listEmpresa = new ArrayList<Empresa>();
		}
		return listEmpresa;
	}

	public void setListEmpresa(List<Empresa> listEmpresa) {
		this.listEmpresa = listEmpresa;
	}
	
	public List<Empresa> getListEmpresaAtivas() {
		listEmpresaAtivas = new ArrayList<Empresa>();
		for(Empresa empresa : getListEmpresa()){
			if(empresa.getAtivo().equals(SimNao.SIM)){
				listEmpresaAtivas.add(empresa);
			}
		}
		return listEmpresaAtivas;
	}

	public void setListEmpresaAtivas(List<Empresa> listEmpresaAtivas) {
		this.listEmpresaAtivas = listEmpresaAtivas;
	}

	public Double getValorTotalCredito() {
		if(valorTotalCredito == null){
			valorTotalCredito = 0.0;
		}
		return valorTotalCredito;
	}

	public void setValorTotalCredito(Double valorTotalCredito) {
		this.valorTotalCredito = valorTotalCredito;
	}

	public Double getValorTotalDebito() {
		return valorTotalDebito;
	}

	public void setValorTotalDebito(Double valorTotalDebito) {
		this.valorTotalDebito = valorTotalDebito;
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
		if (!(obj instanceof SocioEmpresa))
			return false;
		SocioEmpresa other = (SocioEmpresa) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

package org.com.proFinance.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OcorrenciaSocioEmpresa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5453089010715724277L;

	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "socioEmpresa_id") 
	private SocioEmpresa socioEmpresa;
	
	private Double valorCredito;
	
	private Double valorDebito;
	
	@ManyToOne
	@JoinColumn(name = "diaCorridoProjeto_id") 
	private DiaCorridoProjeto diaCorridoProjeto;
	
	@ManyToOne
	@JoinColumn(name = "projeto_id") 
	private Projeto projeto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SocioEmpresa getSocioEmpresa() {
		return socioEmpresa;
	}

	public void setSocioEmpresa(SocioEmpresa socioEmpresa) {
		this.socioEmpresa = socioEmpresa;
	}

	public Double getValorCredito() {
		return valorCredito;
	}

	public void setValorCredito(Double valorCredito) {
		this.valorCredito = valorCredito;
	}

	public Double getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(Double valorDebito) {
		this.valorDebito = valorDebito;
	}

	public DiaCorridoProjeto getDiaCorridoProjeto() {
		return diaCorridoProjeto;
	}

	public void setDiaCorridoProjeto(DiaCorridoProjeto diaCorridoProjeto) {
		this.diaCorridoProjeto = diaCorridoProjeto;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((socioEmpresa == null) ? 0 : socioEmpresa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OcorrenciaSocioEmpresa))
			return false;
		OcorrenciaSocioEmpresa other = (OcorrenciaSocioEmpresa) obj;
		if (socioEmpresa == null) {
			if (other.socioEmpresa != null)
				return false;
		} else if (!socioEmpresa.equals(other.socioEmpresa))
			return false;
		return true;
	}
	
}

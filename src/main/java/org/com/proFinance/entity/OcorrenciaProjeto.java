package org.com.proFinance.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.com.proFinance.enuns.EnumCreditoDebito;

@Entity
public class OcorrenciaProjeto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8676004369591053638L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Calendar data;
	
	private Double valor;
	
	@Enumerated(EnumType.STRING)
	private EnumCreditoDebito creditoDebito;
	
	@ManyToOne
	@JoinColumn(name = "diaCorridoProjeto_id") 
	private DiaCorridoProjeto diaCorridoProjeto;
	
	@ManyToOne
	@JoinColumn(name = "socioEmpresa_id") 
	private SocioEmpresa socioEmpresa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public EnumCreditoDebito getCreditoDebito() {
		return creditoDebito;
	}

	public void setCreditoDebito(EnumCreditoDebito creditoDebito) {
		this.creditoDebito = creditoDebito;
	}

	public DiaCorridoProjeto getDiaCorridoProjeto() {
		return diaCorridoProjeto;
	}

	public void setDiaCorridoProjeto(DiaCorridoProjeto diaCorridoProjeto) {
		this.diaCorridoProjeto = diaCorridoProjeto;
	}

	public SocioEmpresa getSocioEmpresa() {
		return socioEmpresa;
	}

	public void setSocioEmpresa(SocioEmpresa socioEmpresa) {
		this.socioEmpresa = socioEmpresa;
	}
}

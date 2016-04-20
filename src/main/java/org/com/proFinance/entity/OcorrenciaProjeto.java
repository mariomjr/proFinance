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
	
	private Calendar dataInclusao;
	
	private Double valor;
	
	@Enumerated(EnumType.STRING)
	private EnumCreditoDebito creditoDebito;
	
	@ManyToOne
	@JoinColumn(name = "diaCorridoProjeto_id") 
	private DiaCorridoProjeto diaCorridoProjeto;
	
	@ManyToOne
	@JoinColumn(name = "projeto_id") 
	private Projeto projeto;
	
	@ManyToOne
	@JoinColumn(name = "socioEmpresa_id") 
	private SocioEmpresa socioEmpresa;
	
	@ManyToOne
	@JoinColumn(name = "loginUser_id") 
	private LoginUser loginUser;
	
	private String descricao;
	
	private Boolean mostrarOcorrencia;

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
	
	public Calendar getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Calendar dataInclusao) {
		this.dataInclusao = dataInclusao;
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

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getMostrarOcorrencia() {
		return mostrarOcorrencia;
	}

	public void setMostrarOcorrencia(Boolean mostrarOcorrencia) {
		this.mostrarOcorrencia = mostrarOcorrencia;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OcorrenciaProjeto))
			return false;
		OcorrenciaProjeto other = (OcorrenciaProjeto) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

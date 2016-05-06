package org.com.proFinance.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Entity
public class Anexo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 865326983373451252L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;

	@Column
	private String nomeArquivo;

	@Column
	private String extensao;

	@Transient
	private byte[] anexo;
	
	@Transient
	private StreamedContent file;
	
	@Transient
	private StreamedContent fileUpload;
	
	private Integer projeto;
	
	private Calendar dataUpload;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public byte[] getAnexo() {
		return anexo;
	}

	public void setAnexo(byte[] anexo) {
		this.anexo = anexo;
	}

	public Integer getProjeto() {
		return projeto;
	}

	public void setProjeto(Integer projeto) {
		this.projeto = projeto;
	}

	public Calendar getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(Calendar dataUpload) {
		this.dataUpload = dataUpload;
	}

	public StreamedContent getFile() {
		if(anexo!= null){
			InputStream itStream = new ByteArrayInputStream(anexo); 
			file = new DefaultStreamedContent(itStream, extensao, nomeArquivo);
		}
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public StreamedContent getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(StreamedContent fileUpload) {
		this.fileUpload = fileUpload;
	}

}




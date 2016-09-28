package org.com.proFinance.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.com.proFinance.converters.CalendarToString;
import org.com.proFinance.dao.ProjetoDao;
import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.util.ExcelExportUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jxl.format.Alignment;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@ManagedBean
@ViewScoped
public class RelatorioProjetosBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9215732506847090130L;
	
	@Inject
	private ProjetoDao projetoDao;
	
	private Calendar dataInicioFiltro;
	private Calendar dataFimFiltro;
	
	private StreamedContent relatorioExcel;
	
	private Projeto projetoSelect;
	
	private List<Projeto> listProjetoAtivo;
	
	@PostConstruct
	public void init(){
		listProjetoAtivo = projetoDao.getListProjetosAtivos();
	}
	
	public void gerarRelatorio(){
		
		List<DiaCorridoProjeto> listDiaCorridoProjeto = projetoDao.listDiaCorridoProjeto(getDataInicioFiltro(), getDataFimFiltro(), getProjetoSelect());
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-disposition", "attachment; filename=RelatorioNota.xls");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(outputStream);
			WritableSheet sheet = workbook.createSheet("Relatório", 0);
			
			criarLinhaCabecalho(sheet);
			
			int coluna = 0;
			int nLinha = 1;
			SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
			
			
			for(DiaCorridoProjeto diaCorridoProjeto : listDiaCorridoProjeto){
				
				coluna = 0;
				
				ExcelExportUtils.adicionarCelula(coluna++, nLinha, CalendarToString.obterDataCalendar(diaCorridoProjeto.getData()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,format(diaCorridoProjeto.getValorIndexador()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,format(diaCorridoProjeto.getTaxaJuro()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,format(diaCorridoProjeto.getFatorDiario()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,formatMoney(diaCorridoProjeto.getValorCredito()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,formatMoney(diaCorridoProjeto.getValorDebito()), false, sheet, 20);
				ExcelExportUtils.adicionarCelula(coluna++, nLinha,formatMoney(diaCorridoProjeto.getValorSaldoTotal()), false, sheet, 20);
				
				nLinha++;
			}
			
			workbook.write();
			workbook.close();

			InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			String fileName = "RelatorioProjeto.xls";
			relatorioExcel = new DefaultStreamedContent(inputStream, "application/vnd.ms-excel", fileName);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	private String formatMoney(Double valor){
		if(valor!= null){
			NumberFormat nfMoney = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
			nfMoney.setMinimumFractionDigits(2);
			nfMoney.setMaximumFractionDigits(2);
			return nfMoney.format(valor);
		}
		return "";
	}
	
	private String format(Double valor){
		if(valor!= null){
			NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
			Integer digitos = digitosAposVirgula(valor.toString());
			nf.setMinimumFractionDigits(digitos);
			nf.setMaximumFractionDigits(digitos);
			return nf.format(valor);
		}
		return "";
	}
	
	public Integer digitosAposVirgula(String numero){
		if(numero.indexOf(".")>0){
			return (numero.substring(numero.indexOf(".")+1, numero.length())).length();
		}else if((numero.indexOf(",")>0)){
			return (numero.substring(numero.indexOf(",")+1, numero.length())).length();
		}else{
			return 0;
		}
	}
	
	
	
	private void criarLinhaCabecalho(WritableSheet sheet) throws RowsExceededException, WriteException {
		
		int nLinha = 0;
		int coluna = 0;
		
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Data", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Indexador", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Fator mensal", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Fator Diário", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Crédito", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Débito", true, sheet, 20, Alignment.CENTRE);
		ExcelExportUtils.adicionarCelula(coluna++, nLinha, "Saldo", true, sheet, 20, Alignment.CENTRE);
		
	}

	public Calendar getDataInicioFiltro() {
		return dataInicioFiltro;
	}

	public void setDataInicioFiltro(Calendar dataInicioFiltro) {
		this.dataInicioFiltro = dataInicioFiltro;
	}

	public Calendar getDataFimFiltro() {
		return dataFimFiltro;
	}

	public void setDataFimFiltro(Calendar dataFimFiltro) {
		this.dataFimFiltro = dataFimFiltro;
	}

	public StreamedContent getRelatorioExcel() {
		gerarRelatorio();
		return relatorioExcel;
	}

	public void setRelatorioExcel(StreamedContent relatorioExcel) {
		this.relatorioExcel = relatorioExcel;
	}

	public Projeto getProjetoSelect() {
		return projetoSelect;
	}

	public void setProjetoSelect(Projeto projetoSelect) {
		this.projetoSelect = projetoSelect;
	}

	public List<Projeto> getListProjetoAtivo() {
		return listProjetoAtivo;
	}

	public void setListProjetoAtivo(List<Projeto> listProjetoAtivo) {
		this.listProjetoAtivo = listProjetoAtivo;
	}
	
}

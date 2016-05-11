package org.com.proFinance.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.com.proFinance.converters.CalendarToString;
import org.com.proFinance.dao.IndexadorDao;
import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.Indexador;
import org.com.proFinance.entity.OcorrenciaProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.enuns.EnumCreditoDebito;
import org.com.proFinance.wsBancoCentral.WSSerieVO;
import org.com.proFinance.wsBancoCentral.WSValorSerieVO;

public class ProjetoService {

	@Inject
	WsBancoCentroService wsBancoCentroService;
	
	@Inject
	IndexadorDao indexadorDao;
	
	public void gerarNovoInvestimento(Projeto projeto, boolean isSimulacao) {
		
		int ordem = 0;
		WSSerieVO wsSerieVo = null;
		
		if(projeto.getIndexador()!= null){
			wsSerieVo = buscaValoresWebServiceIndicador(projeto.getIndexador(),projeto.getDataInicial(),projeto.getDataFinalPrevistaNova());
		}
		
		DiaCorridoProjeto diaCorridoProjeto = null;

		diaCorridoProjeto = new DiaCorridoProjeto();
		diaCorridoProjeto.setProjeto(projeto);
		diaCorridoProjeto.setData(projeto.getDataInicial());
		trataJuroMesIndexador(projeto, diaCorridoProjeto, wsSerieVo);
		insereOcorrenciaProjeto(projeto, diaCorridoProjeto);
		diaCorridoProjeto.setTaxaJuro((diaCorridoProjeto.getSomaJuroMesIndexador() / 100) + 1);
		diaCorridoProjeto.setFatorDiario(calculaFatorDiario(
				diaCorridoProjeto.getTaxaJuro(), projeto.getDataInicial()));

		diaCorridoProjeto.setOrdem(ordem++);
		
		double valor = 0.0;
		if(isSimulacao){
			valor = projeto.getValorSimulador();
			diaCorridoProjeto.setValorSaldo(valor);
		}else{
			valor = projeto.getValorInicial();
		}
		
		projeto.getListDiasCorridosProjeto().add(diaCorridoProjeto);

		adicionarDiasCorrido(projeto, valor, wsSerieVo, projeto.getDataInicial(), projeto.getDataFinalPrevistaNova(), ordem);

	}
	
	public void adicionarDiasCorrido(Projeto projeto, Double valor, WSSerieVO wsSerieVo, Calendar dataInicial, Calendar dataFinal, int ordem){
		DiaCorridoProjeto diaCorridoProjeto = null;
		for (Calendar data : listDataDiaCorrido(dataInicial,
				dataFinal)) {
			diaCorridoProjeto = new DiaCorridoProjeto();
			diaCorridoProjeto.setProjeto(projeto);
			diaCorridoProjeto.setData(data);
			trataJuroMesIndexador(projeto, diaCorridoProjeto, wsSerieVo);
			diaCorridoProjeto.setTaxaJuro((diaCorridoProjeto.getSomaJuroMesIndexador() / 100) + 1);
			diaCorridoProjeto.setFatorDiario(calculaFatorDiario(
					diaCorridoProjeto.getTaxaJuro(), data));
			diaCorridoProjeto.setOrdem(ordem++);

			valor = valor * diaCorridoProjeto.getFatorDiario();
			diaCorridoProjeto.setValorSaldo(valor);

			projeto.getListDiasCorridosProjeto().add(diaCorridoProjeto);
		}
	}

	private void insereOcorrenciaProjeto(Projeto projeto, DiaCorridoProjeto diaCorridoProjeto) {
		for(OcorrenciaProjeto ocorrencia : projeto.getListOcorrenciasProjeto()){
			ocorrencia.setDiaCorridoProjeto(diaCorridoProjeto);
			ocorrencia.setData(diaCorridoProjeto.getData());
			adicionaDebitoCredito(ocorrencia, diaCorridoProjeto);
			diaCorridoProjeto.getListOcorrenciasProjeto().add(ocorrencia);
		}
		
	}

	private void trataJuroMesIndexador(Projeto projeto, DiaCorridoProjeto diaCorridoProjeto, WSSerieVO wsSerieVo) {
		Double valor = 0.0;
		
		if(projeto.getJuroMes()>0){
			diaCorridoProjeto.setJuroMes(projeto.getJuroMes());
			valor += projeto.getJuroMes();
		}
		if(projeto.getIndexador()!= null && wsSerieVo!= null){
			diaCorridoProjeto.setValorIndexador(valorIndexador(wsSerieVo.getValores(), diaCorridoProjeto.getData().get(Calendar.MONTH)+1, 
					diaCorridoProjeto.getData().get(Calendar.YEAR)));
			valor += diaCorridoProjeto.getValorIndexador();
		}
		
		diaCorridoProjeto.setSomaJuroMesIndexador(valor);
	}
	
	private WSSerieVO buscaValoresWebServiceIndicador(Indexador indexador, Calendar dataInicial, Calendar dataFinal){
		return wsBancoCentroService.getValorByCodigoAndDataInicialFinal(indexador.getCodigo(), dataInicial, dataFinal);
		
	}
	
	public double valorIndexador(WSValorSerieVO[] arrayValorSeries, Integer mes, Integer ano){
		
		for(WSValorSerieVO valorSerieVO : arrayValorSeries){
			if(mes.intValue() == Integer.valueOf(valorSerieVO.getMes()).intValue()
					&& ano.intValue() == Integer.valueOf(valorSerieVO.getAno()).intValue()){
				return valorSerieVO.getValor().doubleValue();
			}
		}
		
		return 0.0;
	}

	private List<Calendar> listDataDiaCorrido(Calendar dataInicial,
			Calendar dataFinal) {
		List<Calendar> listDataDiaCorrido = new ArrayList<Calendar>();

		Integer diaInicial = dataInicial.get(Calendar.DAY_OF_MONTH);
		Integer mesInicial = dataInicial.get(Calendar.MONTH);
		Integer anoInicial = dataInicial.get(Calendar.YEAR);

		Integer diaFinal = dataFinal.get(Calendar.DAY_OF_MONTH);
		Integer mesFinal = dataFinal.get(Calendar.MONTH);
		Integer anoFinal = dataFinal.get(Calendar.YEAR);

		Calendar dataAux = null;

		while (diaInicial.intValue() != diaFinal.intValue()
				|| mesInicial.intValue() != mesFinal.intValue()
				|| anoInicial.intValue() != anoFinal.intValue()) {
			dataAux = new GregorianCalendar(anoInicial, mesInicial, diaInicial);
			listDataDiaCorrido.add(dataAux);
			dataAux.add(Calendar.DAY_OF_YEAR, 1);
			diaInicial = dataAux.get(Calendar.DAY_OF_MONTH);
			mesInicial = dataAux.get(Calendar.MONTH);
			anoInicial = dataAux.get(Calendar.YEAR);
		}

		return listDataDiaCorrido;
	}

	private double valorElevado(Calendar dataMes) {
		int valorMes = dataMes.getActualMaximum(Calendar.DAY_OF_MONTH);
		double valor = 1 / (double) valorMes;

		return valor;
	}

	private double calculaFatorDiario(Double taxaJuro, Calendar data) {
		return Math.pow(taxaJuro, valorElevado(data));
	}

	public void recalcularProjeto(Projeto projeto, DiaCorridoProjeto diaCorridoProjeto) {
		
		ordenarListaProjeto(projeto.getListDiasCorridosProjeto());
		
		double valorTotal = diaCorridoProjeto.getValorSaldoTotal();
		double valorFator = diaCorridoProjeto.getFatorDiario();
		
		for(DiaCorridoProjeto diaCorrido : projeto.getListDiasCorridosProjeto()){
			if(diaCorrido.getOrdem()>diaCorridoProjeto.getOrdem()){
				diaCorrido.setValorSaldo(valorTotal*valorFator);
				valorFator = diaCorrido.getFatorDiario();
				valorTotal = diaCorrido.getValorSaldo();
			}
			
		}
		
	}
	
	public void recalcularProjeto(Projeto projeto) {
		
		ordenarListaProjeto(projeto.getListDiasCorridosProjeto());
		
		double valor = 0.0;
		double valorFator = 0.0;
		
		for(DiaCorridoProjeto diaCorrido : projeto.getListDiasCorridosProjeto()){
			if(diaCorrido.getOrdem()>0){
				diaCorrido.setValorSaldo(valor*valorFator);
				valorFator = diaCorrido.getFatorDiario();
				valor = diaCorrido.getValorSaldoTotal();
			}else{
				valorFator = diaCorrido.getFatorDiario();
				valor = diaCorrido.getValorSaldoTotal();
			}
		}
		
	}
	
	public void adicionaDebitoCredito(OcorrenciaProjeto ocorrencia, DiaCorridoProjeto diaCorridoProjeto){
		if(ocorrencia.getCreditoDebito().equals(EnumCreditoDebito.DEBITO)){
			if(diaCorridoProjeto.getValorDebito()!= null){
				diaCorridoProjeto.setValorDebito(diaCorridoProjeto.getValorDebito()+(ocorrencia.getValor()*(-1)));
			}else{
				diaCorridoProjeto.setValorDebito(ocorrencia.getValor()*(-1));
			}
		}else{
			if(diaCorridoProjeto.getValorCredito()!= null){
				diaCorridoProjeto.setValorCredito(diaCorridoProjeto.getValorCredito()+ocorrencia.getValor());
			}else{
				diaCorridoProjeto.setValorCredito(ocorrencia.getValor());
			}
		}
	}
	
	public void recalcularMudancaData(Projeto projeto){
		WSSerieVO wsSerieVo = null;
		
		if(projeto.getIndexador()!= null){
			wsSerieVo = buscaValoresWebServiceIndicador(projeto.getIndexador(),projeto.getDataFinalPrevistaAtual(),projeto.getDataFinalPrevistaNova());
		}
		DiaCorridoProjeto diaCorrido = getDiaCorrido(projeto.getListDiasCorridosProjeto(), projeto.getDataFinalPrevistaAtual());
		adicionarDiasCorrido(projeto, diaCorrido.getValorSaldo(), wsSerieVo,projeto.getDataFinalPrevistaAtual(),projeto.getDataFinalPrevistaNova(), diaCorrido.getOrdem());
		
		ordenarListaProjeto(projeto.getListDiasCorridosProjeto());
	}
	
	private DiaCorridoProjeto getDiaCorrido(List<DiaCorridoProjeto> listDiaCorrido, Calendar data){
		for(DiaCorridoProjeto diaCorrido : listDiaCorrido){
			if(diaCorrido.getData().equals(data)){
				return diaCorrido;
			}
		}
		return null;
	}

	public void ordenarListaProjeto(List<DiaCorridoProjeto> listDiasCorridosProjeto) {
		Collections.sort(listDiasCorridosProjeto,new Comparator<DiaCorridoProjeto>(){
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public int compare(DiaCorridoProjeto  o1, DiaCorridoProjeto  o2) {
				Comparable c1 = (Comparable) o1.getOrdem();
				Comparable c2 = (Comparable) o2.getOrdem();
				return c1.compareTo(c2);
			}
			
		});
	}
	
	Integer colunaTaxaJuro;
	Integer colunaFatorDiario;
	Integer colunaDebito;
	Integer colunaCredito;
	Integer colunaJuroMes;
	Integer colunaSaldo;
	Integer colunaIndexador;
	Integer colunaData;
	Indexador indexador;
	Double valorAntigo;
	int i;
	int ordem;
	
	public void inserirDiasCorridoProjeto(Projeto projeto, Row linhaRow) {
		if(linhaRow != null){
			if(linhaRow.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC){
				DiaCorridoProjeto diaCorridoProjeto = new DiaCorridoProjeto();
				
				if(colunaData!= null){
					diaCorridoProjeto.setData(CalendarToString.dateToCalendar(linhaRow.getCell(colunaData).getDateCellValue()));
					if(i == 0){
						projeto.setDataInicial(diaCorridoProjeto.getData());
						i++;
					}
				}
				if(colunaTaxaJuro!= null){
					diaCorridoProjeto.setTaxaJuro(linhaRow.getCell(colunaTaxaJuro).getNumericCellValue());
				}
				if(colunaFatorDiario!= null){
					diaCorridoProjeto.setFatorDiario(linhaRow.getCell(colunaFatorDiario).getNumericCellValue());
				}
				if(colunaJuroMes!= null){
					diaCorridoProjeto.setJuroMes(linhaRow.getCell(colunaJuroMes).getNumericCellValue());
					projeto.setJuroMes(diaCorridoProjeto.getJuroMes());
				}else{
					diaCorridoProjeto.setJuroMes(0.0);
					projeto.setJuroMes(0.0);
				}
				
				if(colunaSaldo!= null){
					diaCorridoProjeto.setValorSaldo(linhaRow.getCell(colunaSaldo).getNumericCellValue());
				}
				if(colunaDebito!= null){
					diaCorridoProjeto.setValorDebito(linhaRow.getCell(colunaDebito).getNumericCellValue());
				}
				if(colunaCredito!= null){
					diaCorridoProjeto.setValorCredito(linhaRow.getCell(colunaCredito).getNumericCellValue());
				}
				if(colunaIndexador!= null){
					projeto.setIndexador(indexador);
					diaCorridoProjeto.setValorIndexador(linhaRow.getCell(colunaIndexador).getNumericCellValue());
				}
				
				diaCorridoProjeto.setOrdem(ordem++);
				
				projeto.getListDiasCorridosProjeto().add(diaCorridoProjeto);
			}else if(linhaRow.getCell(0).getStringCellValue().equalsIgnoreCase("DATA")){
				
				colunaTaxaJuro = null;
				colunaFatorDiario = null;
				colunaDebito = null;
				colunaCredito = null;
				colunaJuroMes = null;
				colunaSaldo = null;
				colunaIndexador = null;
				colunaData = null;
				indexador = null;
				i = 0;
				ordem = 0;
				for(int coluna= 0; coluna< linhaRow.getLastCellNum(); coluna++){
					setValoresCampoColuna(linhaRow.getCell(coluna).getStringCellValue(), coluna);
				}
			}
		}
		
	}

	private void setValoresCampoColuna(String stringCell, Integer coluna) {
		if(stringCell.toUpperCase().contains("TAXA JURO") || stringCell.toUpperCase().contains("FATOR MENSAL")){
			colunaTaxaJuro = coluna;
		}else if(stringCell.toUpperCase().contains("SALDO")){
			colunaSaldo = coluna;
		}else if(stringCell.toUpperCase().contains("DÉBITO") || stringCell.toUpperCase().contains("DEBITO")){
			colunaDebito = coluna;
		}else if(stringCell.toUpperCase().contains("CRÉDITO") || stringCell.toUpperCase().contains("CREDITO")){
			colunaCredito = coluna;
		}else if(stringCell.toUpperCase().contains("JURO")){
			colunaJuroMes = coluna;
		}else if(stringCell.toUpperCase().contains("DIÁRIO")){
			colunaFatorDiario = coluna;
		}else if(indexadorDao.findIndexadorByNome(stringCell.toUpperCase())!= null){
			indexador = indexadorDao.findIndexadorByNome(stringCell.toUpperCase());
			colunaIndexador = coluna;
		}else if(stringCell.toUpperCase().contains("DATA")){
			colunaData = coluna;
		}
	}

}

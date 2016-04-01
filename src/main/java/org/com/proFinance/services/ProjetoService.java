package org.com.proFinance.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.com.proFinance.entity.DiaCorridoProjeto;
import org.com.proFinance.entity.Projeto;
import org.com.proFinance.enuns.EnumCreditoDebito;

public class ProjetoService {

	public void gerarNovoInvestimento(Projeto projeto) {

		double valor = projeto.getValorInicial();
		int ordem = 0;
		DiaCorridoProjeto diaCorridoProjeto = null;

		diaCorridoProjeto = new DiaCorridoProjeto();
		diaCorridoProjeto.setData(projeto.getDataInicial());
		diaCorridoProjeto.setJuroMes(projeto.getJuroMes());
		diaCorridoProjeto.setTaxaJuro((projeto.getJuroMes() / 100) + 1);
		diaCorridoProjeto.setFatorDiario(calculaFatorDiario(
				diaCorridoProjeto.getTaxaJuro(), projeto.getDataInicial()));

		if (projeto.getCreditoDebito() != null
				&& projeto.getCreditoDebito().equals(EnumCreditoDebito.DEBITO)) {
			valor = valor * (-1);
			diaCorridoProjeto.setValorDebito(valor);
		} else {
			diaCorridoProjeto.setValorCredito(valor);
		}

		diaCorridoProjeto.setOrdem(ordem++);
		projeto.getListDiasCorridosProjeto().add(diaCorridoProjeto);

		for (Calendar data : listDataDiaCorrido(projeto.getDataInicial(),
				projeto.getDataFinalPrevista())) {
			diaCorridoProjeto = new DiaCorridoProjeto();
			diaCorridoProjeto.setData(data);
			diaCorridoProjeto.setJuroMes(projeto.getJuroMes());
			diaCorridoProjeto.setTaxaJuro((projeto.getJuroMes() / 100) + 1);
			diaCorridoProjeto.setFatorDiario(calculaFatorDiario(
					diaCorridoProjeto.getTaxaJuro(), data));
			diaCorridoProjeto.setOrdem(ordem++);

			valor = valor * diaCorridoProjeto.getFatorDiario();
			diaCorridoProjeto.setValorSaldo(valor);

			projeto.getListDiasCorridosProjeto().add(diaCorridoProjeto);

		}

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

}

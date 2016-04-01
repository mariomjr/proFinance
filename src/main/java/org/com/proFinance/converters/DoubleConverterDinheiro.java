package org.com.proFinance.converters;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;


@Named
public class DoubleConverterDinheiro implements Converter {


	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) throws ConverterException {

		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return 0.0d;
		} else {
			valorTela = valorTela.replaceAll(Pattern.quote("."), "");
			try {
				NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
				Double a = nf.parse(valorTela.replace(".", ",")).doubleValue();
				return a;

			} catch (Exception e) {
				return 0.0d;
			}
		}
	}



	public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) throws ConverterException {

		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return null;
		} else {
			NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("pt", "BR"));
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			String s = nf.format(Double.valueOf(valorTela.toString()));
			return s;
		}
	}
}

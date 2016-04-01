package org.com.proFinance.converters;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Named;


@Named
public class CalendarToString implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1695588552972696922L;

	/**
	 * Retorna data no formato dd/MM/yyyy
	 * @param data Calendar
	 * @return dd/MM/yyyy
	 */
	public static String formatarCalendar(Calendar data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(data.getTime());
			
		} else {
			return "";
		}
	}
	
	public static Calendar stringparaCalendar(String data, String formatoEntrada) {

		Calendar calendario = new GregorianCalendar();

		DateFormat dateFormat = new SimpleDateFormat(formatoEntrada, new Locale("pt-BR"));
		try {
			Date novaData = dateFormat.parse(data);
			
			calendario.setTime(novaData);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return calendario;

	}
	
	
	/**
	 * 
	 * @param data
	 * @return string no formato dd/MM/yyyy HH:mm
	 */
	public static String obterDataEHoraCalendar(Calendar data) {
		return obterDataEHora(data);
	}
	
	
	/**
	 * 
	 * @param data
	 * @return string no formato dd/MM/yyyy HH:mm
	 */
	public static String obterDataEHora(Calendar data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			return formato.format(data.getTime());
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * @param data
	 * @return string no formato dd/MM/yyyy HH:mm
	 */
	public static String obterDataEHoraUS(Calendar data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			return formato.format(data.getTime());
		} else {
			return "";
		}
	}
	
	
	/**
	 * Retorna no formato dd/MM/yyyy
	 * @param data Calendar
	 * @return dd/MM/yyyy
	 */
	public static String obterData(Calendar data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(data.getTime());
		} else {
			return "";
		}
	}
	
	/**
	 * Retorna no formato dd/MM/yyyy
	 * @param data Calendar
	 * @return dd/MM/yyyy
	 */
	public static String obterDataCalendar(Calendar data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(data.getTime());
		} else {
			return "";
		}
	}	
	
	
	
	/**
	 * Retorna no formato dd/MM/yyyy HH:mm conforme Date
	 * @param data Date
	 * @return dd/MM/yyyy HH:mm
	 */
	public static String obterDataEHora(Date data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			return formato.format(data);
		} else {
			return "";
		}
	}
	
	/**
	 * Retorna no formato dd/MM/yyyy conforme Date
	 * @param data Date
	 * @return dd/MM/yyyy
	 */
	public static String obterDataDate(Date data) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(data);
		} else {
			return "";
		}
	}
	
	
	/**
	 * Retorna conforme personaliza��o de entrada
	 * @param data Calendar
	 * @param formatoData Ex.: "dd/MM/yyyy"
	 * @return formato defino da entrada do argumento
	 */
	public static String formatarDataPersonalizado(Calendar data, String formatoData) {
		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat(formatoData);
			return formato.format(data.getTime());
		} else {
			return "";
		}
	}
	
	public static Calendar segundosEmCalendar(Double segundos) throws java.lang.StringIndexOutOfBoundsException{
		
		double hrCadast = segundos;
		double hr_minute = hrCadast / 3600;
		
		System.out.println(hr_minute);
		int horasc = Integer.valueOf( Double.valueOf(hr_minute).toString().replace(".", ",").split(",")[0] );
		String value = Double.valueOf(hr_minute).toString().replace(".", ",").split(",")[1].trim();
		
		double minutos_frac =0;
		if (value.length() > 1) {
			minutos_frac = Double.valueOf(  value.substring(0, value.length()-1) );
		}else{
			minutos_frac = Double.valueOf(  value);
		}
		
		double minutos = (minutos_frac/100) *60;
		int minutos_st = Integer.valueOf( Double.valueOf(minutos).toString().replace(".", ",").split(",")[0] );
		Calendar date =new GregorianCalendar();
		date.setTime(new Date());
		date.set(Calendar.HOUR_OF_DAY, horasc);
		date.set(Calendar.MINUTE, minutos_st);
		
		return date;
		
	}
	
	public Calendar getCAlendarToTimeLong(long time){
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time);
		return cal;
	}
	
	public static String calendarparaStringUS(Calendar data) {

		if (data != null) {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			return formato.format(data.getTime());

		} else {
			return "";
		}
	}
	
}

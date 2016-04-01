package org.com.proFinance.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;


@Named
public class PrimefacesCalendarConverter implements Converter {


	private Logger logger = Logger.getLogger(getClass().getName());



	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (logger.isDebugEnabled()) {
			logger.debug("getAsObject");
		}
		Calendar calendar = null;
		Date convertedToDate;

		if (!StringUtils.isBlank(value)) {
			convertedToDate = convertToDate(context, (org.primefaces.component.calendar.Calendar) component, value);
			calendar = Calendar.getInstance();
			calendar.setTime(convertedToDate);
		}

		return calendar;
	}



	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("getAsString");
		}
		String convertedValue = null;

		if (value != null) {
			convertedValue = convertToString(context, (org.primefaces.component.calendar.Calendar) component, value);
		}

		return convertedValue;
	}



	protected Date convertToDate(FacesContext context, org.primefaces.component.calendar.Calendar pfCalendarComponent,
			String submittedValue) {
		Locale locale = pfCalendarComponent.calculateLocale(context);
		SimpleDateFormat format = new SimpleDateFormat(pfCalendarComponent.getPattern(), locale);
		format.setTimeZone(pfCalendarComponent.calculateTimeZone());

		try {
			return format.parse(submittedValue);
		} catch (ParseException e) {
			throw new ConverterException(e);
		}
	}



	protected String convertToString(FacesContext context, org.primefaces.component.calendar.Calendar pfCalendarComponent,
			Object value) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pfCalendarComponent.getPattern(),
				pfCalendarComponent.calculateLocale(context));

		dateFormat.setTimeZone(pfCalendarComponent.calculateTimeZone());

		return dateFormat.format(((Calendar) value).getTime());
	}
}

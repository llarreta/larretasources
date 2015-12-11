package ar.com.larreta.commons.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.larreta.commons.AppConfigData;

public class FormatPatterns implements Serializable {
	
	private static final String AGO_YEAR_FORMAT = "ago.year.format";

	public static final String GENERAL_DATE_FORMAT = "general.date.format";
	
	private String generalDateFormat;
	
	private AppConfigData appConfigData;
	
	public FormatPatterns(AppConfigData appConfigData){
		this.appConfigData = appConfigData;
	}

	public String getGeneralDateFormat() {
		return appConfigData.getGeneralDateFormat();
	}

	private SimpleDateFormat getFormatter(String format) {
		return new SimpleDateFormat(format);
	}
	
	
	

}

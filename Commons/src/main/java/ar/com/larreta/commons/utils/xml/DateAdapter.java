package ar.com.larreta.commons.utils.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.commons.utils.FormatPatterns;

public class DateAdapter extends XmlAdapter<String, Date> {

	@Autowired
	private FormatPatterns formatPatterns;
	
	private DateFormat formatter = new SimpleDateFormat(formatPatterns.getGeneralDateFormat());

	@Override
	public Date unmarshal(String value) throws Exception {
		return formatter.parse(value);
	}

	@Override
	public String marshal(Date value) throws Exception {
		return formatter.format(value);
	}

}

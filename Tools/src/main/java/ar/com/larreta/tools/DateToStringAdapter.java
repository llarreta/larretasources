package ar.com.larreta.tools;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("DateToStringAdapter")
public class DateToStringAdapter extends StandardAdapter {

	@Value("${app.dateFormat}")
	private String defaultPattern;
	
	private SimpleDateFormat simpleDateFormat;

	@PostConstruct
	public void initialize(){
		simpleDateFormat = new SimpleDateFormat(defaultPattern);
		simpleDateFormat.setLenient(Boolean.FALSE);
	}
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		return simpleDateFormat.format(toAdapt);
	}

}

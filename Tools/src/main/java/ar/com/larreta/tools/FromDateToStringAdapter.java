package ar.com.larreta.tools;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("FromDateToStringAdapter")
public class FromDateToStringAdapter extends StandardAdapter {

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
		try {
			return simpleDateFormat.format(toAdapt);
		} catch (IllegalArgumentException e){}
		return StringUtils.EMPTY;
	}

}

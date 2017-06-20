package ar.com.larreta.validators;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component @Scope("prototype")
public class FormatValidatorDate implements FormatValidatorProcessor{

	private SimpleDateFormat simpleDateFormat;
	
	@Value("${app.dateFormat}")
	private String defaultPattern;
	
	public void setPattern(String pattern){
		if (StringUtils.isEmpty(pattern)){
			pattern = defaultPattern;
		}
		simpleDateFormat = new SimpleDateFormat(pattern);
		simpleDateFormat.setLenient(Boolean.FALSE);
	}
	
	@Override
	public Boolean process(String text) {
		try {
			return !StringUtils.isEmpty(text) && simpleDateFormat.parse(text)!=null;
		} catch (ParseException e) {
		}
		return Boolean.FALSE;
	}

}

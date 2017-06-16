package ar.com.larreta.validators;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.validators.annotations.Format;

@Component @Scope("prototype")
public class FormatValidatorFactory {

	private Map<Format.FormatType, FormatValidatorProcessor> processors = new HashMap<>();
	
	@Autowired
	private FormatValidatorDate formatValidatorDate;
	@Autowired
	private FormatValidatorString formatValidatorString;
	
	@PostConstruct
	public void initialize(){
		processors.put(Format.FormatType.STRING, formatValidatorString);
		processors.put(Format.FormatType.DATE, formatValidatorDate);
	}
	
	public void setPattern(Format.FormatType formatType, String pattern){
		processors.get(formatType).setPattern(pattern);
	}	
	
	public Boolean process(Format.FormatType formatType, String text) {
		return processors.get(formatType).process(text);
	}
	
}

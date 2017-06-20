package ar.com.larreta.validators;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component @Scope("prototype")
public class FormatValidatorString implements FormatValidatorProcessor {

	private Pattern pattern;
	
	@Override
	public void setPattern(String patternText) {
		pattern = Pattern.compile(patternText);
	}
	
	@Override
	public Boolean process(String text) {
		return !StringUtils.isEmpty(text) && pattern.matcher(text).matches();
	}


}

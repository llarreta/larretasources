package ar.com.larreta.validators;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.tools.CollectionsUtils;
import ar.com.larreta.validators.annotations.NotNull;

public class NotNullValidator implements ConstraintValidator<Annotation, Object> {

	public static final String BAR = "/";

	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;
	
	private List avaiableActions;
	private NotNull notNull;
	
	@Override
	public void initialize(Annotation annotation) {
		notNull = (NotNull) annotation; 
		avaiableActions = Arrays.asList(notNull.avaiableActions());
		avaiableActions = CollectionsUtils.removeEmtpyElements(avaiableActions);
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		if (avaiableActions.contains(getActionFromURL())){
			return (((field instanceof String) && (!StringUtils.isEmpty((CharSequence) field))) || 
					(!(field instanceof String) && (field!=null)));
		}
		return Boolean.TRUE;
	}

	/**
	 * Get action from URL
	 */
	public String getActionFromURL() {
		String url = request.getRequestURL().toString();
		if (BAR.equals(url.substring(url.length()-1))){
			url = url.substring(0, url.length() - 1);
		}
		Integer lastBar = url.lastIndexOf(BAR);
		return url.substring(lastBar+1);
	}

}

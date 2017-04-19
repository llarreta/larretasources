package ar.com.larreta.validators;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.tools.CollectionsUtils;
import ar.com.larreta.validators.annotations.NotNull;

@Transactional
public class NotNullValidator implements ConstraintValidator<Annotation, Object> {

	public static final String BAR = "/";

	@Autowired
	protected ServletContext context;
	
	@Autowired
	protected HttpServletRequest request;
	
	private NotNull notNull;
	
	@Override
	public void initialize(Annotation annotation) {
		notNull = (NotNull) annotation; 
	}

	private List getAvaiableActions() {
		return CollectionsUtils.removeEmtpyElements(Arrays.asList(notNull.avaiableActions()));
	}

	@Override
	public boolean isValid(Object field, ConstraintValidatorContext context) {
		Collection avaiableActions = getAvaiableActions();
		if (avaiableActions.isEmpty() || avaiableActions.contains(getActionFromURL())){
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

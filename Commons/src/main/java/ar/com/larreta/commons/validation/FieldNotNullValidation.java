/**
 * 
 */
package ar.com.larreta.commons.validation;

import javax.faces.application.FacesMessage;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.validation.constraints.FieldNotNull;

/**
 * @author ignacio.m.larreta
 *
 */
public class FieldNotNullValidation extends StandardControllerImpl implements ConstraintValidator<FieldNotNull, String>{

	private boolean ignoreCase;
	private String message;
	private String key;
	private String fieldId;
	private String formId;
	
	public void initialize(FieldNotNull constraintAnnotation) {
		this.message = constraintAnnotation.message();
		this.ignoreCase = constraintAnnotation.ignoreCase();
		this.key = constraintAnnotation.key();
		this.fieldId = constraintAnnotation.fieldId();
		this.formId = constraintAnnotation.fieldId();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)){
			String messages = AppManager.getInstance().getResourceBundle().getString(this.key);
			addMessage(this.formId, this.fieldId, "Error!", messages, FacesMessage.SEVERITY_ERROR);
			
			return false;
		}
		return true;
	}

}

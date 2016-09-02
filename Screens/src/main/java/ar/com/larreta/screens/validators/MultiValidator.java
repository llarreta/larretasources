package ar.com.larreta.screens.validators;

import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.validate.ClientValidator;

import ar.com.larreta.screens.ScreenElement;

public class MultiValidator extends ar.com.larreta.commons.domain.Entity implements javax.faces.validator.Validator, ClientValidator {

	public ScreenElement screenElement;
	

	public MultiValidator(ScreenElement screenElement){
		this.screenElement = screenElement;
	}
	
	public Map<String, Object> getMetadata() {
		return null;
	}

	public String getValidatorId() {
		return null;
	}

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (screenElement.getValidators()!=null){
			Iterator<Validator> it = screenElement.getValidators().iterator();
			while (it.hasNext()) {
				Validator validator = (Validator) it.next();
				validator.validate(context, component, value);
			}
		}
	}

}

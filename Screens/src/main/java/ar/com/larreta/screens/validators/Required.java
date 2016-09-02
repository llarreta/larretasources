package ar.com.larreta.screens.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.screens.ScreenUtils;

@Entity
@DiscriminatorValue("required")
public class Required extends Validator {

	public Required(){
		setId(new Long(1));
	}
	
	@Override
	public void customValidate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if ((value==null)||((value instanceof CharSequence)&&(StringUtils.isEmpty((CharSequence) value)))){
			throw new ValidatorException(new FacesMessage(ScreenUtils.messaging("#{msg[validation.error.required]}")));
		}
	}

}

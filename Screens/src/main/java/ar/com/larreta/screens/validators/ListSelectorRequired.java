package ar.com.larreta.screens.validators;

import java.util.Arrays;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.primefaces.model.DualListModel;

import ar.com.larreta.screens.ScreenUtils;

@Entity
@DiscriminatorValue("listSelectorRequired")
public class ListSelectorRequired extends Validator {

	@Override
	public void customValidate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if ((value==null) || 
				((value instanceof Object[]) && (Arrays.asList((Object[])value).size()<=0)) || 
				((value instanceof DualListModel) && (((DualListModel)value).getTarget().size()<=0)) 
		    ){
			throw new ValidatorException(new FacesMessage(ScreenUtils.messaging("#{msg[validation.error.required.listSelector]}")));
		}
	}

}

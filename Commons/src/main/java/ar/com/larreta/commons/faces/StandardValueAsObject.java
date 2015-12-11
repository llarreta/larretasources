package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;

public class StandardValueAsObject extends AppObjectImpl implements ValueAsObject {

	@Override
	public Object getAsObject(EntityConverter converter, FacesContext context, UIComponent component, String value) {
		return StringUtils.EMPTY;
	}

}

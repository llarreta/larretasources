package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;

public class StandardValueAsString extends AppObjectImpl  implements ValueAsString {

	@Override
	public String getAsString(EntityConverter converter, FacesContext context, UIComponent component, Object value) {
		if (value!=null){
			return value.toString();
		}
		return StringUtils.EMPTY;
	}

}

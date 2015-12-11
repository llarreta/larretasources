package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface ValueAsString {
	public String getAsString(EntityConverter converter, FacesContext context, UIComponent component, Object value);

}

package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface ValueAsObject {
	public Object getAsObject(EntityConverter converter, FacesContext context, UIComponent component, String value);
}

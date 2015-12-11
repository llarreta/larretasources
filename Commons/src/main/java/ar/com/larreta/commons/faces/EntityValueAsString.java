package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;

public class EntityValueAsString extends AppObjectImpl implements ValueAsString {

	@Override
	public String getAsString(EntityConverter converter, FacesContext context, UIComponent component, Object value) {
		Entity entity = (Entity) value;
		return entity.getId().toString();
	}

}

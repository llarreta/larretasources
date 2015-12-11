package ar.com.larreta.commons.faces;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;

public class EntityValueAsObject extends AppObjectImpl implements ValueAsObject {

	@Override
	public Object getAsObject(EntityConverter converter, FacesContext context, UIComponent component, String value) {
		if ((value!=null) && (!StringUtils.EMPTY.equals(value))){
			try {
				Entity entity = converter.getEntity();
				entity.setId(new Long(value));
				return converter.getStandardService().getEntity(entity);
			} catch (Exception e){
				getLog().error(AppException.getStackTrace(e));
			} 
		}
		return StringUtils.EMPTY;
	}

}

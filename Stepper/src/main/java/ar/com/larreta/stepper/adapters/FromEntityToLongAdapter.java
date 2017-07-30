package ar.com.larreta.stepper.adapters;

import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.tools.StandardAdapter;

@Component("FromEntityToLongAdapter")
public class FromEntityToLongAdapter extends StandardAdapter {

	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		Entity entity = (Entity) toAdapt;
		if (entity!=null){
			return entity.getId();
		}
		return null;
	}

}

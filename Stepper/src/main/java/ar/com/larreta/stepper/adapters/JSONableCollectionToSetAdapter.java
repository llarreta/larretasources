package ar.com.larreta.stepper.adapters;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Adapter;
import ar.com.larreta.tools.StandardAdapter;

@Component("JSONableCollectionToSetAdapter")
public class JSONableCollectionToSetAdapter extends StandardAdapter {

	private static final String DUMMY_PROPERTY = "id";

	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception{
		Set collection = new HashSet();
		
		Class genericType = generics[0];
		
		JSONableCollection set = (JSONableCollection) toAdapt;
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Serializable actual = (Serializable) it.next();
			Serializable newInstance = (Serializable) applicationContext.getBean(genericType);
			
			Adapter adapter = beanUtils.getAdapter(actual.getClass(), newInstance.getClass());
			if (adapter!=null){
				actual = (Serializable) adapter.process(actual, genericType, null);
			}
			
			beanUtils.copy(actual, newInstance);
			collection.add(newInstance);
		}

		return collection;

	}

}

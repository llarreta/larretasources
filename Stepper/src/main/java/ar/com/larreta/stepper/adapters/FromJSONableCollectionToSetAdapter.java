package ar.com.larreta.stepper.adapters;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Adapter;
import ar.com.larreta.tools.StandardAdapter;

@Component("FromJSONableCollectionToSetAdapter")
public class FromJSONableCollectionToSetAdapter extends StandardAdapter {

	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception{
		Set collection = new HashSet();
		
		Class genericType = generics[0];
		if (toAdapt!=null){
			JSONableCollection set = (JSONableCollection) toAdapt;
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Serializable actual = (Serializable) it.next();
				Adapter adapter = beanUtils.getAdapter(actual.getClass(), genericType);
				if (adapter!=null){
					actual = (Serializable) adapter.process(actual, genericType, null);
				}
				
				if (actual!=null){
					if (!actual.getClass().equals(genericType)){
						Serializable newInstance = (Serializable) applicationContext.getBean(genericType);
						beanUtils.copy(actual, newInstance);
						actual = newInstance;
					}
					collection.add(actual);
				}
			}
		}

		return collection;

	}

}

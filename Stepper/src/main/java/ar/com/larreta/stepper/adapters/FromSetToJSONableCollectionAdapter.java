package ar.com.larreta.stepper.adapters;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.tools.Adapter;
import ar.com.larreta.tools.StandardAdapter;

@Component("FromSetToJSONableCollectionAdapter")
public class FromSetToJSONableCollectionAdapter extends StandardAdapter {
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {		
		JSONableCollection collection = new JSONableCollection<>();
		
		Class genericType = generics[0];
		
		Set set = (Set) toAdapt;
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Serializable actual = (Serializable) it.next();
			Serializable newInstance = null;
			try {
				newInstance = (Serializable) applicationContext.getBean(genericType);
			} catch (NoSuchBeanDefinitionException e){}

			Adapter adapter = beanUtils.getAdapter(actual.getClass(), genericType);
			if (adapter!=null){
				actual = (Serializable) adapter.process(actual, genericType, null);
			}
			if (newInstance!=null){
				beanUtils.copy(actual, newInstance);
			} else {
				newInstance = actual;
			}
			collection.add(newInstance);
		}
		
		return collection;
	}

}

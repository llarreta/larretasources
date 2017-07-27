package ar.com.larreta.stepper.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.Persister;
import ar.com.larreta.tools.BeanUtils;
import ar.com.larreta.tools.StandardAdapter;

@Component("LongToEntityAdapter")
public class LongToEntityAdapter extends StandardAdapter {

	@Autowired
	private BeanUtils beanUtils;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	private Persister persister;
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		Long id = (Long) toAdapt;
		return persister.get(type, id);
	}

}

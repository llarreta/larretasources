package ar.com.larreta.tools;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StandardAdapter implements Adapter {

	@Autowired
	protected BeanUtils beanUtils;
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Override
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception {
		return (Serializable) toAdapt;
	}

	@Override
	public String getPropertyTarget(String propertyName) {
		return propertyName;
	}

}

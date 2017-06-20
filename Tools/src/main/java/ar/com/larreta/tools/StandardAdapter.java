package ar.com.larreta.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StandardAdapter implements Adapter {

	@Autowired
	private BeanUtils beanUtils;
	
	@Override
	public void process(String property, Object source, Object target) throws Exception{
		beanUtils.write(target, property, beanUtils.read(source, property));
	}

}

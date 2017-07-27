package ar.com.larreta.stepper;

import java.io.Serializable;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.query.Persister;
import ar.com.larreta.tools.BeanUtils;

public class StepElement implements Serializable {
	
	protected static @Log Logger LOG;
	
	@Autowired
	protected BeanUtils beanUtils;
	
	//FIXME: Ver si se puede colocar como query y asignar correctamente segun corresponda ejemplo en load
	@Autowired
	protected Persister persister;
	
	@Autowired
	protected ApplicationContext applicationContext;
}

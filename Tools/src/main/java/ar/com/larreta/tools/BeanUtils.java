package ar.com.larreta.tools;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

	private final static Logger LOGGER = Logger.getLogger(BeanUtils.class);
	
	public void copy(Object source, Object target){
		try {
			PropertyUtils.copyProperties(target, source);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error copiando propiedades", e);
		}
	}
	
}

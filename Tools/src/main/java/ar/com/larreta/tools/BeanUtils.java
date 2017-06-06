package ar.com.larreta.tools;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
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
	
	public Object getValue(Object source, String property){
		try {
			return PropertyUtils.getProperty(source, property);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error obteniendo propiedades", e);
		}
		return StringUtils.EMPTY;
	}
	
}

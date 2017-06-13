package ar.com.larreta.tools;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

	private final static Logger LOGGER = Logger.getLogger(BeanUtils.class);
	
	public void copy(Object source, Object target){
		try {
			Collection<PropertyDescriptor> descriptors = Arrays.asList(PropertyUtils.getPropertyDescriptors(target));
			Iterator<PropertyDescriptor> it = descriptors.iterator();
			while (it.hasNext()) {
				PropertyDescriptor propertyDescriptor = (PropertyDescriptor) it.next();
				String propertyName = propertyDescriptor.getName();
				write(target, propertyName, read(source, propertyName));
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error copiando propiedades", e);
		}
	}
	
	public Object read(Object source, String property){
		try {
			if (PropertyUtils.isReadable(source, property)){
				return PropertyUtils.getProperty(source, property);
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error obteniendo propiedades", e);
		}
		return null;
	}
	
	public void write(Object target, String property, Object value){
		try {
			if ((value!=null) && (PropertyUtils.isWriteable(target, property))){
				PropertyUtils.setProperty(target, property, value);
			}
		} catch (IllegalArgumentException e){
			LOGGER.error("El tipo " + value.getClass().getName() + " no puede asignarse a " + property);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error escribiendo propiedades", e);
		}
	}
	
}

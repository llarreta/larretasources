package ar.com.larreta.tools;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

	private final static Logger LOGGER = Logger.getLogger(BeanUtils.class);

	private Map<String, Adapter> adapters;

	private Map<Class, Class> instances;
	
	@Autowired
	private StandardAdapter standardAdapter;
	@Autowired
	private StringToDateAdapter stringToDateAdapter;
	@Autowired
	private DateToStringAdapter dateToStringAdapter;
	
	@PostConstruct
	public void initialize(){
		adapters = new HashMap<>();
		adapters.put(String.class.getName() + Date.class.getName(), stringToDateAdapter);
		adapters.put(Date.class.getName() + String.class.getName(), dateToStringAdapter);
	
		instances = new HashMap<>();
		instances.put(Set.class, HashSet.class);
	
	}
	
	private Adapter getAdapter(Class source, Class target){
		Adapter adapter = adapters.get(source.getName() + target.getName());
		if (adapter!=null){
			return adapter;
		}
		return standardAdapter;
	}
	
	
	public void copy(Object source, Object target){
		try {
			Collection<PropertyDescriptor> descriptors = Arrays.asList(PropertyUtils.getPropertyDescriptors(target));
			Iterator<PropertyDescriptor> it = descriptors.iterator();
			while (it.hasNext()) {
				PropertyDescriptor targetPropertyDescriptor = (PropertyDescriptor) it.next();
				String propertyName = targetPropertyDescriptor.getName();
				PropertyDescriptor sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(source, propertyName);
				
				if (sourcePropertyDescriptor!=null && targetPropertyDescriptor!=null){
					Adapter adapter = getAdapter(sourcePropertyDescriptor.getPropertyType(), targetPropertyDescriptor.getPropertyType());
					adapter.process(propertyName, source, target);
				}
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
	
	public Iterator iterator(Object source, String property){
		Collection collection = (Collection)read(source, property);
		if (collection==null){
			return new ArrayList<>().iterator();
		}
		return collection.iterator();
	}
	
	public void writeToAll(Collection source, String property, Serializable value){
		if (source!=null){
			Iterator it = source.iterator();
			while (it.hasNext()) {
				write(it.next(), property, value);
			}
		}
	}
	
	public Object newInstanceType(Object source, String property){
		try {
			PropertyDescriptor descriptor =	PropertyUtils.getPropertyDescriptor(source, property);
			Class replace = instances.get(descriptor.getPropertyType());
			if (replace!=null){
				return replace.newInstance();
			}
			return descriptor.getPropertyType().newInstance();
		} catch (Exception e){
			LOGGER.error("Ocurrio un error newInstanceType", e);
		}
		return null;
	}
	
	public Object newInstance(Class type, Object[] args){
		try {
			Collection<Class> paramsTypes = new ArrayList<>();
			Collection argsInstance = Arrays.asList(args);
			Iterator it = argsInstance.iterator();
			while (it.hasNext()) {
				paramsTypes.add(it.next().getClass());
			}
			Constructor constructor = type.getConstructor((Class[]) paramsTypes.toArray());
			return constructor.newInstance(args);
		} catch (Exception e){
			LOGGER.error("Ocurrio un error newInstance", e);
		}
		return null;
	}
	
}

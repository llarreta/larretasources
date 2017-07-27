package ar.com.larreta.tools;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

	public static final String GET = "get";
	public static final String AS = "As";
	public static final String ADAPTER = "Adapter";
	public static final String TO = "To";

	private final static Logger LOGGER = Logger.getLogger(BeanUtils.class);

	@Autowired
	protected ApplicationContext applicationContext;
	
	private Map<Class, Class> instances;
	
	@Autowired
	private StandardAdapter standardAdapter;
	
	@PostConstruct
	public void initialize(){
		instances = new HashMap<>();
		instances.put(Set.class, HashSet.class);
	}
	
	public Adapter getAdapter(PropertyDescriptor source, PropertyDescriptor target){
		Collection<Class> sourceTypes = getTypes(source);
		Collection<Class> targetTypes = getTypes(target);
		
		String completeName = getFixedName(source) + TO + getFixedName(target);
		
		Collection<String> keys = new ArrayList();
		keys.add(completeName + ADAPTER);
		addKeys(sourceTypes, targetTypes, keys, completeName);
		addKeys(sourceTypes, targetTypes, keys, StringUtils.EMPTY);

		return searchAdapter(keys);
	}
	
	public Adapter getAdapter(Class source, Class target){
		Collection<Class> sourceTypes = getTypes(source);
		Collection<Class> targetTypes = getTypes(target);
		
		Collection<String> keys = new ArrayList();
		addKeys(sourceTypes, targetTypes, keys, StringUtils.EMPTY);

		return searchAdapter(keys);
	}

	public Adapter searchAdapter(Collection<String> keys) {
		Adapter adapter = null;
		Iterator<String> it = keys.iterator();
		while (it.hasNext() && adapter==null) {
			String key = (String) it.next();
			try {
				adapter = (Adapter) applicationContext.getBean(key);
			} catch (NoSuchBeanDefinitionException e){
				LOGGER.debug("No se encontro adapter para " + key);
			}
		}
		
		return adapter;
	}

	public void addKeys(Collection<Class> sourceTypes, Collection<Class> targetTypes, Collection<String> keys, String aux) {
		StringBuilder header = new StringBuilder();
		header.append(aux);
		if (!StringUtils.isEmpty(aux)){
			header.append(AS);
		}
		Iterator<Class> itSources = sourceTypes.iterator();
		while (itSources.hasNext()) {
			StringBuilder body = new StringBuilder();
			body.append(header);
			Class sourceClass = (Class) itSources.next();
			body.append(sourceClass.getSimpleName());
			body.append(TO);
			if (targetTypes.size()>0){
				Iterator<Class> itTarget = targetTypes.iterator();
				while (itTarget.hasNext()) {
					StringBuilder footer = new StringBuilder();
					footer.append(body);
					Class targetClass = (Class) itTarget.next();
					footer.append(targetClass.getSimpleName());
					footer.append(ADAPTER);
					keys.add(footer.toString());
				}
			} else {
				body.append(ADAPTER);
				keys.add(body.toString());
			}
		}
	}

	public Collection<Class> getTypes(PropertyDescriptor descriptor) {
		Collection<Class> types = new ArrayList<>();
		if (descriptor!=null){
			Class type = descriptor.getPropertyType();
			types.addAll(getTypes(type));
		}
		return types;
	}

	public Collection<Class> getTypes(Class type) {
		Collection<Class> types = new ArrayList<>();
		do {
			types.add(type);
			type = type.getSuperclass();
		} while ((type!=null) && (!type.equals(Object.class)));
		return types;
	}

	private String getFixedName(PropertyDescriptor descriptor) {
		if (descriptor!=null){
			String draftName = descriptor.getName();
			if (draftName!=null && draftName.length()>2){
				return draftName.substring(0,1).toUpperCase() + draftName.substring(1);
			}
		}
		return StringUtils.EMPTY;
	}
	
	//FIXME: Mejorar este algoritmo
	public void copy(Object source, Object target){
		try {
			Set<String> propertiesNames = getPropertiesNames(source, target);
			
			Iterator<String> it = propertiesNames.iterator();
			while (it.hasNext()) {
				String propertyName = (String) it.next();
				
				PropertyDescriptor sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(source, propertyName);
				PropertyDescriptor targetPropertyDescriptor = PropertyUtils.getPropertyDescriptor(target, propertyName);
				
				Adapter adapter = getAdapter(sourcePropertyDescriptor, targetPropertyDescriptor);
				if (adapter==null && targetPropertyDescriptor!=null){
					Object targetObject = null;
					try {
						targetObject = applicationContext.getBean(targetPropertyDescriptor.getPropertyType());
					} catch (NoSuchBeanDefinitionException e){}
					if (targetObject!=null) {
						write(target, propertyName, targetObject);
						copy(read(source, propertyName), targetObject);
					} else {
						adapter = standardAdapter;
					}
				}
				if (adapter!=null){
					Class propertyTargetType = getPropertyType(target, propertyName);
					Class[] propertyTargetGenerics = TypedClassesUtils.getGenerics(target.getClass(), propertyName);
					
					Object propertySourceObject = read(source, propertyName);
					Object propertyTargetObject = adapter.process(propertySourceObject, propertyTargetType, propertyTargetGenerics);
					
					String propertyTargetName = adapter.getPropertyTarget(propertyName);
					
					write(target, propertyTargetName, propertyTargetObject);
				}
				
			}
		} catch (Exception e){
			LOGGER.error("Ocurrio un error copiando propiedades", e);
		}
	}
	
	public Class getPropertyType (Object source, String property){
		if (source!=null && !StringUtils.isEmpty(property)) {
			Method method = MethodUtils.getAccessibleMethod(source.getClass(), GET + property.substring(0, 1).toUpperCase() + property.substring(1), null);
			if (method!=null){
				return method.getReturnType();
			}
		}
		return null;
	}

	private Set<String> getPropertiesNames(Object source, Object target) {
		Collection<PropertyDescriptor> descriptors = new ArrayList<>();
		if (source!=null){
			descriptors.addAll(Arrays.asList(PropertyUtils.getPropertyDescriptors(source)));
		}
		if (target!=null){
			descriptors.addAll(Arrays.asList(PropertyUtils.getPropertyDescriptors(target)));
		}

		Set<String> propertiesNames = new HashSet<>();
		Iterator<PropertyDescriptor> it = descriptors.iterator();
		while (it.hasNext()) {
			PropertyDescriptor propertyDescriptor = (PropertyDescriptor) it.next();
			propertiesNames.add(propertyDescriptor.getName());
		}
		return propertiesNames;
	}
	
	public Object read(Object source, String property){
		try {
			if (PropertyUtils.isReadable(source, property)){
				return PropertyUtils.getProperty(source, property);
			}
		} catch (NestedNullException e){
			LOGGER.debug("La propiedad " + property + " tiene como valor null");
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
	
	@Deprecated
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
	
	@Deprecated
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

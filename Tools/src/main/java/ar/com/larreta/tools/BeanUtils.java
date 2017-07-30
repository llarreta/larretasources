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

	public static final String DESTINATION = "Destination";
	public static final String ORIGIN = "Origin";
	public static final String TARGET = "Target";
	public static final String SOURCE = "Source";
	public static final String FROM = "From";
	public static final String IN = "In";
	public static final String GET = "get";
	public static final String AS = "As";
	public static final String ADAPTER = "Adapter";
	public static final String TO = "To";
	public static final String TO_PROP = "ToProp";

	private final static Logger LOGGER = Logger.getLogger(BeanUtils.class);
	private final static Logger LOGGER_ADAPTERS = Logger.getLogger("adapters");

	@Autowired
	protected ApplicationContext applicationContext;
	
	private Map<Class, Class> instances;
	
	private Collection<String> keysNotFound = new ArrayList();
	
	@Autowired
	private StandardAdapter standardAdapter;
	
	@PostConstruct
	public void initialize(){
		instances = new HashMap<>();
		instances.put(Set.class, HashSet.class);
	}
	
	public Adapter getAdapter(Object sourceObject, Object targetObject, PropertyDescriptor sourceProperty, PropertyDescriptor targetProperty){
		Collection<Class> sourcePropertyTypes = getTypes(sourceProperty);
		Collection<Class> targetPropertyTypes = getTypes(targetProperty);
		
		String sourcePropertyName = getFixedName(sourceProperty);
		String targetPropertyName = getFixedName(targetProperty);
		
		String sourceObjectType = getFixedType(sourceObject);
		String targetObjectType = getFixedType(targetObject);
		
		Collection<String> keys = new ArrayList();
		addKeys(keys, sourceObjectType, targetObjectType, sourcePropertyName, targetPropertyName, sourcePropertyTypes, targetPropertyTypes);

		return searchAdapter(keys);
	}

	private String getFixedType(Object sourceObject) {
		if (sourceObject!=null){
			return sourceObject.getClass().getSimpleName();
		}
		return StringUtils.EMPTY;
	}
	
	public Adapter getAdapter(Class source, Class target){
		Collection<Class> sourceTypes = getTypes(source);
		Collection<Class> targetTypes = getTypes(target);
		
		Collection<String> keys = new ArrayList();
		addKeys(keys, null, null, null, null, sourceTypes, targetTypes);

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
				keysNotFound.add(key);
				LOGGER.debug("No se encontro adapter para " + key);
				LOGGER_ADAPTERS.info("No se encontro adapter para " + key);
			}
		}
		
		return adapter;
	}

	private void addKeys(Collection<String> keys, String sourceObjectType, 					String targetObjectType,
												  String sourcePropertyName, 				String targetPropertyName,
												  Collection<Class> sourcePropertyTypes, 	Collection<Class> targetPropertyTypes) {
		
		String source 			= getFixed(SOURCE, 		sourceObjectType);
		String target 			= getFixed(TARGET, 		targetObjectType);
		
		String origin 			= getFixed(ORIGIN, 		sourcePropertyName);
		String destination 		= getFixed(DESTINATION, 	targetPropertyName);
		
		addKeys(keys, source + target + origin + destination, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, source + target + origin, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, source + target + destination, sourcePropertyTypes, targetPropertyTypes);
		
		addKeys(keys, source + origin + destination, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, source + origin, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, source + destination, sourcePropertyTypes, targetPropertyTypes);
		
		addKeys(keys, target + origin + destination, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, target + origin, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, target + destination, sourcePropertyTypes, targetPropertyTypes);
		
		addKeys(keys, origin + destination, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, origin, sourcePropertyTypes, targetPropertyTypes);
		addKeys(keys, destination, sourcePropertyTypes, targetPropertyTypes);

		addKeys(keys, StringUtils.EMPTY, sourcePropertyTypes, targetPropertyTypes);
		
		keys.removeAll(keysNotFound);
		
	}
	
	public String getFixed(String prefix, String name){
		if (!StringUtils.isEmpty(name)){
			return prefix + name;
		}
		return StringUtils.EMPTY;
	}

	private void addKeys(Collection<String> keys, String aux, Collection<Class> sourcePropertyTypes,
			Collection<Class> targetPropertyTypes) {
		if (!StringUtils.isEmpty(aux)){
			keys.add(aux + ADAPTER);
		}
		
		if (sourcePropertyTypes!=null){
		Iterator<Class> itSourcePropertyTypes = sourcePropertyTypes.iterator();
			while (itSourcePropertyTypes.hasNext()) {
				Class sourceClass = (Class) itSourcePropertyTypes.next();
				String from  = FROM + sourceClass.getSimpleName();
				if (targetPropertyTypes!=null){
					Iterator<Class> itTargetPropertyTypes = targetPropertyTypes.iterator();
					while (itTargetPropertyTypes.hasNext()) {
						Class targetClass = (Class) itTargetPropertyTypes.next();
						String to = TO + targetClass.getSimpleName();
						keys.add(aux + from + to + ADAPTER);
					}
				}
				keys.add(aux + from + ADAPTER);
			}
		}
		
		if (targetPropertyTypes!=null){
			Iterator<Class> itTargetPropertyTypes = targetPropertyTypes.iterator();
			while (itTargetPropertyTypes.hasNext()) {
				Class targetClass = (Class) itTargetPropertyTypes.next();
				String to = TO + targetClass.getSimpleName();
				keys.add(aux + to + ADAPTER);
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
			Set<String> processProperties = new HashSet<>();
			while (it.hasNext()) {
				String propertyName = (String) it.next();
				
				PropertyDescriptor sourcePropertyDescriptor = null;
				PropertyDescriptor targetPropertyDescriptor = null;
				
				if (!StringUtils.isEmpty(propertyName)){
					if(source!=null){
						sourcePropertyDescriptor = PropertyUtils.getPropertyDescriptor(source, propertyName);
					}
					targetPropertyDescriptor = PropertyUtils.getPropertyDescriptor(target, propertyName);
				}
				
				Adapter adapter = getAdapter(source, target, sourcePropertyDescriptor, targetPropertyDescriptor);
				if (adapter==null && targetPropertyDescriptor!=null){
					if(!processProperties.contains(propertyName)){
						Object targetObject = null;
						try {
							targetObject = applicationContext.getBean(targetPropertyDescriptor.getPropertyType());
						} catch (NoSuchBeanDefinitionException e){}
						if (targetObject!=null) {
							write(target, propertyName, targetObject);
							copy(read(source, propertyName), targetObject);
							processProperties.add(propertyName);
						} else {
							adapter = standardAdapter;
						}
					}
				}
				if (adapter!=null){
					Class propertyTargetType = getPropertyType(target, propertyName);
					Class[] propertyTargetGenerics = TypedClassesUtils.getGenerics(target.getClass(), propertyName);
					
					Object propertySourceObject = read(source, propertyName);
					Object propertyTargetObject = adapter.process(propertySourceObject, propertyTargetType, propertyTargetGenerics);
					
					String propertyTargetName = adapter.getPropertyTarget(propertyName);
					
					write(target, propertyTargetName, propertyTargetObject);
					processProperties.add(propertyTargetName);
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
			if (source!=null && !StringUtils.isEmpty(property) && PropertyUtils.isReadable(source, property)){
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
			if (target!=null && !StringUtils.isEmpty(property) && 
					(value!=null) && (PropertyUtils.isWriteable(target, property))){
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

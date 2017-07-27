package ar.com.larreta.tools;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.ResolvableType;

public class TypedClassesUtils {

	public static final String GET = "get";

	/**
	 * Retorna el tipo generico que implementa una propiedad
	 * @param instance
	 * @param property
	 * @return
	 */
	public static Class getGeneric(Class type, String property, Integer index){
		return getGenerics(type, property)[index];
	}
	
	/**
	 * Retorna los tipos genericos que implementa una propiedad
	 * @param instance
	 * @param property
	 * @return
	 */
	public static Class[] getGenerics(Class type, String property){
		if (type!=null && !StringUtils.isEmpty(property)){
			Method method = MethodUtils.getAccessibleMethod(type, GET + property.substring(0, 1).toUpperCase() + property.substring(1), null);
			if (method!=null){
				ResolvableType resolvableType =  ResolvableType.forMethodReturnType(method);
				return resolvableType.resolveGenerics();
			}
		}
		return null;
	}
	
	
	/**
	 * Retorna los tipos genericos que implementa una clase
	 * @param type
	 * @param instance
	 * @return
	 */
	public static Collection<Class> getGenerics(Class type, Object instance){
		Class<?>[] generics = ResolvableType.forClass(type, instance.getClass()).resolveGenerics();
		return Arrays.asList(generics);
	}
	
	/**
	 * Retorna el tipo generico segun el index
	 * @param type
	 * @param instance
	 * @param index
	 * @return
	 */
	public static Class getGenerics(Class type, Object instance, Integer index){
		Collection<Class> generics = getGenerics(type, instance);
		return (Class) generics.toArray()[index];
	}
}

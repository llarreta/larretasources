package ar.com.larreta.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.core.ResolvableType;

public class TypedClassesUtils {

	/**
	 * Retorna los tipos genericos 
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
		Iterator<Class> it = generics.iterator();
		Integer tmpIndex = 0;
		while (it.hasNext()) {
			Class actual = (Class) it.next();
			if (tmpIndex==index){
				return actual;
			}
			tmpIndex++;
		}
		return null;
	}
}

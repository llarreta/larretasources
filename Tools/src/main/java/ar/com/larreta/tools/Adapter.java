package ar.com.larreta.tools;

public interface Adapter {
	public String getPropertyTarget(String propertyName);
	public Object process(Object toAdapt, Class type, Class[] generics) throws Exception;
}

package ar.com.larreta.tools;

public interface Adapter {
	public void process(String property, Object source, Object target) throws Exception;
}

package ar.com.larreta.tools;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class PropertyAnnotation implements Serializable {

	private Annotation annotation;
	private String property;
	
	public PropertyAnnotation(String property, Annotation annotation){
		this.property = property;
		this.annotation = annotation;
	}
	
	public Annotation getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
}

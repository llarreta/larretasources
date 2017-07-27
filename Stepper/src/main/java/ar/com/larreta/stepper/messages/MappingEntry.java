package ar.com.larreta.stepper.messages;

public class MappingEntry extends JSONable {

	private String methods;
	private String patterns;
	private String produces;
	
	public String getMethods() {
		return methods;
	}
	public void setMethods(String methods) {
		this.methods = methods;
	}
	public String getPatterns() {
		return patterns;
	}
	public void setPatterns(String patterns) {
		this.patterns = patterns;
	}
	public String getProduces() {
		return produces;
	}
	public void setProduces(String produces) {
		this.produces = produces;
	}
	
}

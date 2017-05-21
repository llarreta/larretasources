package ar.com.larreta.rest.messages;

public class MappingEntry extends JSONable {

	private String verbs;
	private String patterns;
	private String produces;
	
	public String getVerbs() {
		return verbs;
	}
	public void setVerbs(String verbs) {
		this.verbs = verbs;
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

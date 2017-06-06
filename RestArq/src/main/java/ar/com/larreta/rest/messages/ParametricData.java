package ar.com.larreta.rest.messages;

public class ParametricBody extends Body {

	private Long 			id;
	private String 			description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

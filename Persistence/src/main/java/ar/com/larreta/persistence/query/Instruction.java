package ar.com.larreta.persistence.query;

public abstract class Instruction {

	private String description;

	public Instruction(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

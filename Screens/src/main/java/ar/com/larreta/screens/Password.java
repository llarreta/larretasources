package ar.com.larreta.screens;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "password")
@DiscriminatorValue(value = "password")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class Password extends ValuedElement {

	private String match;
	private String label;
	private Boolean required;
	private Boolean feedback;
	private Boolean inline;
	private String promptLabel;
	private String weakLabel;
	private String goodLabel;
	private String strongLabel;

	public Password(){}
	
	public Password(String bindingObject, String bindingProperty){
		super();
		setBindingObject(bindingObject);
		setBindingProperty(bindingProperty);
	}
	
	@Basic
	public Boolean getInline() {
		return inline;
	}
	public void setInline(Boolean inline) {
		this.inline = inline;
	}
	
	@Basic @Column(name="matchId")
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	@Basic
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Basic
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	@Basic
	public Boolean getFeedback() {
		return feedback;
	}
	public void setFeedback(Boolean feedback) {
		this.feedback = feedback;
	}
	
	@Basic
	public String getPromptLabel() {
		return promptLabel;
	}
	public void setPromptLabel(String promptLabel) {
		this.promptLabel = promptLabel;
	}
	
	@Basic
	public String getWeakLabel() {
		return weakLabel;
	}
	public void setWeakLabel(String weakLabel) {
		this.weakLabel = weakLabel;
	}
	
	@Basic
	public String getGoodLabel() {
		return goodLabel;
	}
	public void setGoodLabel(String goodLabel) {
		this.goodLabel = goodLabel;
	}
	
	@Basic
	public String getStrongLabel() {
		return strongLabel;
	}
	public void setStrongLabel(String strongLabel) {
		this.strongLabel = strongLabel;
	}
	
	
}

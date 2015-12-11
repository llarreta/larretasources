package ar.com.larreta.prode.views;

import javax.faces.event.ActionEvent;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.prode.domain.Team;

public class TeamDataView extends DataView{
	
	public static final String TEAM = "team";

	private Long id;
	@NotNull(message="{validations.notnull.name}")
	@NotEmpty(message="{validations.notnull.name}")
	private String name = "";
	private String library;
	private String shield;
	private String eneabled = "true";
	private String action = "create";
	private String msgSaveorUpdate = "#{msg['app.save']}";
	
	public void notNull(){
		System.out.println("entre " + name);
		if(name != null && !name.equals("") && !name.equals(" ")){
			this.eneabled = "false";
		}else{
			this.eneabled = "true";
		}
	}
	
	public void updateDialog(){
		this.msgSaveorUpdate = "#{msg['app.update']}";
		this.action = "update";
		
	}
	
	public void remove(ActionEvent actionEvent){
		Team selected = (Team) actionEvent.getComponent().getAttributes().get(TEAM);
		super.setSelected(selected);
//		getController().remove(this, null);
	}
	
	public void resetSelectedItem(ActionEvent actionEvent){
		selected=null;
		id=null;
		name=null;
		shield=null;
		library=null;
		this.msgSaveorUpdate = "#{msg['app.save']}";
		this.action = "create";
	}
	
	public void setSelected(Team selected) {
		super.setSelected(selected);
		id = selected.getId();
		name = selected.getName();
		library = selected.getLibrary();
		shield = selected.getShield();
	}
	
	@Override
	public Team getSelected() {
		return (Team)super.getSelected();
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the library
	 */
	public String getLibrary() {
		return library;
	}
	/**
	 * @param library the library to set
	 */
	public void setLibrary(String library) {
		if(library == null | library.equals("")){
			library = " ";
		}
		this.library = library;
	}
	/**
	 * @return the shield
	 */
	public String getShield() {
		return shield;
	}
	/**
	 * @param shield the shield to set
	 */
	public void setShield(String shield) {
		if(shield == null | shield.equals("")){
			shield = " ";
		}
		this.shield = shield;
	}
	/**
	 * @return the eneabled
	 */
	public String getEneabled() {
		return eneabled;
	}
	/**
	 * @param eneabled the eneabled to set
	 */
	public void setEneabled(String eneabled) {
		this.eneabled = eneabled;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
	/**
	 * @return the msgSaveorUpdate
	 */
	public String getMsgSaveorUpdate() {
		return msgSaveorUpdate;
	}
	/**
	 * @param msgSaveorUpdate the msgSaveorUpdate to set
	 */
	public void setMsgSaveorUpdate(String msgSaveorUpdate) {
		this.msgSaveorUpdate = msgSaveorUpdate;
	}

	@Override
	public Entity newSelected() {
		return new Team();
	}

}

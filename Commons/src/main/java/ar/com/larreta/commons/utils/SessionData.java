package ar.com.larreta.commons.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ar.com.larreta.commons.domain.Notification;
import ar.com.larreta.commons.domain.User;
	
@Controller
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class SessionData implements Serializable {
	
	private Integer cantMessages = 10;
	private String iconNotification;
	private String iconMessages;
	
	private String metadataName;
	private String metadataContent;
	
	private Integer metadataNameAccessCount = 0;
	private Integer metadataContentAccessCount = 0;
	
	private Collection<Notification> notifications = new ArrayList<Notification>();

	public String getMetadataName() {
		String meta = metadataName;
		metadataContentAccessCount++;
		if (metadataNameAccessCount>=2){
			metadataName = "";
		}
		return meta;
	}

	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}

	public String getMetadataContent() {
		String meta = metadataContent;
		metadataContentAccessCount++;
		if (metadataContentAccessCount>=2){
			metadataContent = "";
		}
		return meta;
	}

	public void setMetadataContent(String metadataContent) {
		this.metadataContent = metadataContent;
	}
	
	public void setMetadata(String name, String content){
		setMetadataName(name);
		setMetadataContent(content);
	}

	public void redirect(String url){
		redirect(url, "0");
	}
	
	public void redirect(String url, String delay){
		setMetadata("Refresh", delay + "; URL=" + url);
		RequestContext.getCurrentInstance().execute("location.reload();");
	}
	
	public Boolean getAvaiableNotifications(){
		return (notifications!=null) && (!notifications.isEmpty());
	}
	
	public Boolean getNotAvaiableNotifications(){
		return !getAvaiableNotifications();
	}
	
	
	public Collection<Notification> getNotifications() {
		return notifications;
	}	
	
	public void remove(Notification notification){
		notifications.remove(notification);
	}
	
	/**
	 * @return the cantNotifications
	 */
	public Integer getCountNotifications() {
		return notifications.size();
	}
	
	public void add(Notification notification){
		notifications.add(notification);
	}
	
	public void add(Collection<Notification> notifications){
		notifications.addAll(notifications);
	}


	/**
	 * @return the cantMessages
	 */
	public Integer getCantMessages() {
		return cantMessages;
	}

	/**
	 * @param cantMessages the cantMessages to set
	 */
	public void setCantMessages(Integer cantMessages) {
		this.cantMessages = cantMessages;
	}
	
	public User getActualUser(){
		return SessionUtils.getActualUser();
	}

	/**
	 * @return the iconNotification
	 */
	public String getIconNotification() {
		if(getCountNotifications() > 0){
			this.iconNotification = "fa fa-bell";
		}else{
			this.iconNotification = "fa fa-bell-o";
		}
		return iconNotification;
	}

	/**
	 * @param iconNotification the iconNotification to set
	 */
	public void setIconNotification(String iconNotification) {
		this.iconNotification = iconNotification;
	}

	/**
	 * @return the iconMessages
	 */
	public String getIconMessages() {
		if(this.cantMessages > 0){
			this.iconMessages = "fa fa-envelope";
		}else{
			this.iconMessages = "fa fa-envelope-o";
		}
		return iconMessages;
	}

	/**
	 * @param iconMessages the iconMessages to set
	 */
	public void setIconMessages(String iconMessages) {
		this.iconMessages = iconMessages;
	}

}

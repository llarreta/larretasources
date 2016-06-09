package ar.com.larreta.commons.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.controllers.Paginator;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Notification;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.filters.URLsManager;
import ar.com.larreta.commons.utils.SessionData;
import ar.com.larreta.commons.utils.SessionUtils;

public class DataView extends AppObjectImpl {
	
	public static final String DATA_VIEW = "dataView";
	
	protected Entity selected;
	
	protected Paginator paginator;
	
	private StandardController controller;
	
	private String forward;
	
	private String nextScreenId;

	public DataView() {
		super();
		paginator = newPaginator();
		paginator.setDataView(this);
	}
	
	public String getNextScreenId() {
		return nextScreenId;
	}

	public void setNextScreenId(String nextScreenId) {
		this.nextScreenId = nextScreenId;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	protected Paginator newPaginator() {
		return new Paginator();
	}
	
	public StandardController getController() {
		return controller;
	}

	public void setController(StandardController controller) {
		this.controller = controller;
	}

	public Date getNow(){
		return new Date();
	}
	
	public String getVersion(){
		return AppManager.getInstance().getAppConfigData().getVersion();
	}
	
	public SessionData getSessionData() {
		return AppManager.getInstance().getSessionData();
	}

	public String getGeneralDateFormat(){
		return AppManager.getInstance().getAppConfigData().getGeneralDateFormat();
	}
	
	public Paginator getPaginator() throws PaginatorNotFoundException {
		if (paginator==null){
			throw new PaginatorNotFoundException();
		}
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public Entity getSelected() {
		return selected;
	}

	public void setSelected(Entity selected) {
		this.selected = selected;
	}
	
	public Entity newSelected(){
		try {
			if ((controller!=null) && (controller.getEntityClass()!=null)) {
				return (Entity) controller.getEntityClass().newInstance();
			}
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return null;
	}

	public String getMetadataName() {
		return getSessionData().getMetadataName();
	}

	public void setMetadataName(String metadataName) {
		getSessionData().setMetadataName(metadataName);
	}

	public String getMetadataContent() {
		return getSessionData().getMetadataContent();
	}

	public void setMetadataContent(String metadataContent) {
		getSessionData().setMetadataContent(metadataContent);
	}
	
	public void setMetadata(String name, String content){
		getSessionData().setMetadata(name, content);
	}

	public void redirect(String url){
		getSessionData().redirect(url);
	}
	
	public void redirect(String url, String delay){
		getSessionData().redirect(url, delay);
	}
	
	public Boolean getAvaiableNotifications(){
		return getSessionData().getAvaiableNotifications();
	}
	
	public Boolean getNotAvaiableNotifications(){
		return getSessionData().getNotAvaiableNotifications();
	}
	
	
	public Collection<Notification> getNotifications() {
		return getSessionData().getNotifications();
	}	
	
	public void remove(Notification notification){
		getSessionData().remove(notification);
	}
	
	/**
	 * @return the cantNotifications
	 */
	public Integer getCountNotifications() {
		return getSessionData().getCountNotifications();
	}
	
	public void add(Notification notification){
		getSessionData().add(notification);
	}
	
	public void add(Collection<Notification> notifications){
		getSessionData().add(notifications);
	}


	/**
	 * @return the cantMessages
	 */
	public Integer getCantMessages() {
		return getSessionData().getCantMessages();
	}

	/**
	 * @param cantMessages the cantMessages to set
	 */
	public void setCantMessages(Integer cantMessages) {
		getSessionData().setCantMessages(cantMessages);
	}
	
	public User getActualUser(){
		return SessionUtils.getActualUser();
	}

	/**
	 * @return the iconNotification
	 */
	public String getIconNotification() {
		return getSessionData().getIconNotification();
	}

	/**
	 * @param iconNotification the iconNotification to set
	 */
	public void setIconNotification(String iconNotification) {
		getSessionData().setIconNotification(iconNotification);
	}

	/**
	 * @return the iconMessages
	 */
	public String getIconMessages() {
		return getSessionData().getIconMessages();
	}

	/**
	 * @param iconMessages the iconMessages to set
	 */
	public void setIconMessages(String iconMessages) {
		getSessionData().setIconMessages(iconMessages);
	}

	public String getPreviousURL(){
		return URLsManager.getInstance().getPreviousURL();
	}
	
	public String getForwardURL(){
		return URLsManager.getInstance().getForwardURL();
	}
	
	public Collection getOneRow(){
		Collection rows = new ArrayList();
		rows.add(new Integer(1));
		return rows;
	}

	
	
	
	
}

package ar.com.larreta.commons.controllers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppConfigData;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.aspects.GenericExecution;
import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.services.impl.StandardServiceImpl;
import ar.com.larreta.commons.views.DataView;

public class StandardControllerImpl extends AppObjectImpl implements StandardController {
	
	private static final String ENTITY = "entity";
	private static final String MSG_POSTFIX = "']}";
	private static final String MSG_PREFIX = "#{msg['";
	private static final String TWO_POINTS = ":";
	public static final String OPEN_MB = "${";
	public static final String CLOSE_MB = "}";
	
	private static final String SELECTED = "selected";
	
	@Autowired
	protected AppConfigData appConfigData;
	
	@Autowired
	@Qualifier(StandardServiceImpl.STANDARD_SERVICE)
	protected StandardService service;

	protected DataView dataView;

	protected Class entityClass;
	
	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	public DataView getDataView() {
		return dataView;
	}

	public void setDataView(DataView dataView) {
		this.dataView = dataView;
		dataView.setController(this);
	}

	public StandardControllerImpl() {
		super();
	}
	
	public StandardService getService() throws NotServiceAssignedException {
		if (service==null){
			throw new NotServiceAssignedException();
		}
		return service;
	}

	public void setService(StandardService standardService) {
		this.service = standardService;
	}
	
	/**
	 * Agrega un mensaje para poder ser utilizado en la vista
	 * @param form
	 * @param field
	 * @param summary
	 * @param detail
	 * @param severity
	 */
	public static void addMessage(String form, String field, String summary,	String detail, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(form + TWO_POINTS + field, new FacesMessage(severity, summary, detail));
	}

	/**
	 * Agrega un mensaje para poder ser utilizado en la vista
	 * @param form
	 * @param field
	 * @param summary
	 * @param detail
	 * @param severity
	 */
	public static void addMessage(String field, String summary,	String detail, Severity severity) {
		FacesContext.getCurrentInstance().addMessage(field, new FacesMessage(severity, summary, detail));
	}
	
	protected HttpServletRequest getHttpServletRequest(RequestContext context) {
		ServletExternalContext externalContext = (ServletExternalContext) context.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getNativeRequest();
		return request;
	}
	
	public String getServerURL(RequestContext context) {
		String uri = getHttpServletRequest(context).getRequestURI();
		return getActualURL(context).toString().replace(uri, StringUtils.EMPTY);
	}

	public StringBuilder getActualURL(RequestContext context) {
		return new StringBuilder(getHttpServletRequest(context).getRequestURL());
	}
	
	public String getContextPath(RequestContext context) {
		return getHttpServletRequest(context).getContextPath();
	}
	
	public String getAppURL(RequestContext context) {
		return getServerURL(context) + getContextPath(context);
	}

	/**
	 * Retorna un DualListModel a partir del targe y source pasado por parametro
	 * Quitando del source lo que ya se encuentra en el target
	 * @param setTarget
	 * @param setSource
	 * @return
	 */
	public DualListModel getDualListModel(Collection setTarget, Collection setSource) {
		List target = new ArrayList(getNotNullSet(setTarget));
		List source = new ArrayList(getNotNullSet(setSource));
		if (target!=null){
			source.removeAll(target);
		}
		DualListModel dualListModel = new DualListModel(source, target);
		return dualListModel;
	}

	/**
	 * En caso del que la collection sea nulo lo inicializa
	 * @param source
	 * @return
	 */
	private Collection getNotNullSet(Collection source) {
		Collection target = source;
		if (target==null){
			target = new ArrayList();
		}
		return target;
	}


	public String getFlowRequestInfo(RequestContext flowRequestContext) {
		if (flowRequestContext==null){
			return StringUtils.EMPTY;
		}
		return flowRequestContext.getFlowExecutionUrl();
	}
	
	@GenericExecution
	public void starting(RequestContext flowRequestContext) throws PaginatorNotFoundException{
		
		varsAssign(flowRequestContext);
		getLog().info("starting:" + getFlowRequestInfo(flowRequestContext));
		DataView dataView = getDataView();
		try {
			if ((dataView!=null) && (dataView.getPaginator()!=null)){
					dataView.getPaginator().refresh();
			}
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}

	}

	/**
	 * Asignacion de variables de flujo
	 * dataView, entityClass, etc
	 * @param flowRequestContext
	 */
	protected void varsAssign(RequestContext flowRequestContext) {
		dataView = (DataView) flowRequestContext.getFlowScope().get(DataView.DATA_VIEW);
		dataView.setController(this);
		Object objectEntityClass = flowRequestContext.getFlowScope().get(ENTITY);
		if (objectEntityClass!=null){
			entityClass = objectEntityClass.getClass();
		}
	}

	public void initCreate(RequestContext flowRequestContext){
		getLog().info("initCreate:" + getFlowRequestInfo(flowRequestContext));
		DataView dataView = getDataView();
		dataView.setSelected(dataView.newSelected());
	}
	public void initUpdate(RequestContext flowRequestContext){
		getLog().info("initUpdate:" + getFlowRequestInfo(flowRequestContext));
	}
	public void preCreate(RequestContext flowRequestContext){
		getLog().info("preCreate:" + getFlowRequestInfo(flowRequestContext));
	}
	public void onCreate(RequestContext flowRequestContext) throws NotServiceAssignedException{
		getLog().info("onCreate:" + getFlowRequestInfo(flowRequestContext));
		getService().save(getDataView().getSelected());	
	}
	public void postCreate(RequestContext flowRequestContext){
		getLog().info("postCreate:" + getFlowRequestInfo(flowRequestContext));
	}
	public void preUpdate(RequestContext flowRequestContext){
		getLog().info("preUpdate:" + getFlowRequestInfo(flowRequestContext));
	}
	public void onUpdate(RequestContext flowRequestContext) throws NotServiceAssignedException{
		getLog().info("onUpdate:" + getFlowRequestInfo(flowRequestContext));
		getService().update(getDataView().getSelected());	
	}
	public void postUpdate(RequestContext flowRequestContext){
		getLog().info("postUpdate:" + getFlowRequestInfo(flowRequestContext));
	}
	public void preDelete(RequestContext flowRequestContext){
		getLog().info("preDelete:" + getFlowRequestInfo(flowRequestContext));
	}
	public void onDelete(RequestContext flowRequestContext){
		getLog().info("onDelete:" + getFlowRequestInfo(flowRequestContext));
		service.delete(getDataView().getSelected());
	}
	public void postDelete(RequestContext flowRequestContext){
		getLog().info("postDelete:" + getFlowRequestInfo(flowRequestContext));
	}
	
	/**
	 * Evento create que se invoca con AJAX
	 * @param actionEvent
	 * @throws NotServiceAssignedException 
	 */
	public final void create(ActionEvent actionEvent) throws NotServiceAssignedException{
		getLog().info("create:" + actionEvent.toString());
		preCreate(null);
		onCreate(null);
		postCreate(null);
	}
	
	/**
	 * Evento update que se invoca con AJAX
	 * @param actionEvent
	 * @throws NotServiceAssignedException 
	 */
	public final void update(ActionEvent actionEvent) throws NotServiceAssignedException{
		setSelected(actionEvent);
		preUpdate(null);
		onUpdate(null);
		postUpdate(null);
	}
	
	/**
	 * Evento delete que se invoca con AJAX
	 * @param actionEvent
	 */
	public final void delete(ActionEvent actionEvent){
		setSelected(actionEvent);
		preDelete(null);
		onDelete(null);
		postDelete(null);
	}
	
	/**
	 * Setea como entidad seleccionada el atributo selected pasado como parametro
	 * @param actionEvent
	 */
	public void setSelected(ActionEvent actionEvent) {
		DataView dataView = getDataView();
		Object selected = getEventAttribute(actionEvent, SELECTED);
		if ((selected!=null) && (dataView!=null)){
			dataView.setSelected((Entity) selected);
		}
	}

	public Object getEventAttribute(ActionEvent actionEvent, String eventAttribute) {
		if ((actionEvent==null) || (actionEvent.getComponent()==null) || (actionEvent.getComponent().getAttributes()==null)){
			return null;
		}
		return actionEvent.getComponent().getAttributes().get(eventAttribute);
	}

	public AppConfigData getAppConfigData() {
		return appConfigData;
	}
	
}

package ar.com.larreta.commons.controllers;

import java.util.Collection;

import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;

import org.primefaces.model.DualListModel;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.views.DataView;

public interface StandardController extends AppObject {
	public StandardService getService()throws NotServiceAssignedException;
	public void setService(StandardService standardService);
	
	
	public String getServerURL(RequestContext context);

	public StringBuilder getActualURL(RequestContext context);
	
	public String getContextPath(RequestContext context);
	
	public String getAppURL(RequestContext context);

	/**
	 * Retorna un DualListModel a partir del targe y source pasado por parametro
	 * Quitando del source lo que ya se encuentra en el target
	 * @param setTarget
	 * @param setSource
	 * @return
	 */
	public DualListModel getDualListModel(Collection setTarget, Collection setSource);

	public String getFlowRequestInfo(RequestContext flowRequestContext);
	
	public void starting(RequestContext flowRequestContext) throws AppException;

	public void initCreate(RequestContext flowRequestContext);

	public void initUpdate(RequestContext flowRequestContext);

	public void preCreate(RequestContext flowRequestContext);

	public void onCreate(RequestContext flowRequestContext) throws NotServiceAssignedException;	

	public void postCreate(RequestContext flowRequestContext);

	public void preUpdate(RequestContext flowRequestContext);

	public void onUpdate(RequestContext flowRequestContext) throws NotServiceAssignedException;	

	public void postUpdate(RequestContext flowRequestContext);

	public void preDelete(RequestContext flowRequestContext);

	public void onDelete(RequestContext flowRequestContext);

	public void postDelete(RequestContext flowRequestContext);

	/**
	 * Evento create que se invoca con AJAX
	 * @param actionEvent
	 * @throws NotServiceAssignedException 
	 */
	public void create(ActionEvent actionEvent) throws NotServiceAssignedException;

	/**
	 * Evento update que se invoca con AJAX
	 * @param actionEvent
	 * @throws NotServiceAssignedException 
	 */
	public void update(ActionEvent actionEvent) throws NotServiceAssignedException;
	
	/**
	 * Evento delete que se invoca con AJAX
	 * @param actionEvent
	 */
	public void delete(ActionEvent actionEvent);
	
	/**
	 * Setea como entidad seleccionada el atributo selected pasado como parametro
	 * @param actionEvent
	 */
	public void setSelected(ActionEvent actionEvent);

	public Object getEventAttribute(FacesEvent actionEvent, String eventAttribute);

	public DataView getDataView();

	public void setDataView(DataView dataView);

	public Class getEntityClass();
}

package ar.com.larreta.screens.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.event.FacesEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.Table;
import ar.com.larreta.screens.impl.ScreenListener;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreensControllerImpl extends StandardControllerImpl {

	private static final String EVENT_ELEMENT_ID = "eventElementId";
	private static final String EVENT_NAME = "eventName";
	private static final String SCREEN_ID = "screenId";
	private static final String SCREEN_REF = "screen";
	
	private Screen screen;
	
	private String eventName;
	private String eventElementId;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventElementId() {
		return eventElementId;
	}

	public void setEventElementId(String eventElementId) {
		this.eventElementId = eventElementId;
	}

	public ScreensService getService() throws NotServiceAssignedException {
		return (ScreensService) super.getService();
	}

	@Autowired
	@Qualifier(ScreenServiceImpl.SCREEN_SERVICE)
	public void setService(ScreensService standardService) {
		super.setService(standardService);
	}

	private Long getScreenId(RequestContext flowRequestContext) {
		Long screenId = null;
		if (getDataView().getNextScreenId()!=null){
			screenId = new Long(getDataView().getNextScreenId());
		}
		if (screenId==null){
			String id = flowRequestContext.getRequestParameters().get(SCREEN_ID);
			if (!StringUtils.isEmpty(id)){
				screenId = new Long(id);
			}
		}
		return screenId;
	}
	
	public Screen getScreen(){
		return screen;
	}
	
	public Screen getScreen(RequestContext flowRequestContext) {
		try {
			screen = (Screen) getService().getScreen(getScreenId(flowRequestContext));
			flowRequestContext.getFlowScope().put(SCREEN_REF, screen); 
			if (!StringUtils.isEmpty(screen.getEntityClass())){
				setEntityClass(getClass().getClassLoader().loadClass(screen.getEntityClass()));
			}
		} catch (Exception e){
			getLog().error("Ocurrio un error", e);
		}
		return screen;
	}

	@Override
	public void starting(RequestContext flowRequestContext) throws PaginatorNotFoundException {

		getScreen(flowRequestContext);
		
		screen.getSearchMap().recursiveFind(Form.class);
		
		super.starting(flowRequestContext);
		callListener(flowRequestContext, screen.getInitActionListener());
	}

	@Override
	public void initCreate(RequestContext flowRequestContext) {
		getScreen(flowRequestContext);
		super.initCreate(flowRequestContext);
		callListener(flowRequestContext, screen.getInitActionListener());
	}

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		getScreen(flowRequestContext);
		
		try {
			if (screen.getIsLazyProperties()){
				DataView dataView = getDataView();
				dataView.setSelected(getService().getEntity(dataView.getSelected(), screen.getLazyPropertiesSplitted(), screen.getLazyCollectionsSplitted()));
			}
		} catch (Exception e){
			getLog().error("Ocurrio un error obteniendo entidad a actualizar", e);
		}
		
		super.initUpdate(flowRequestContext);
		
		callListener(flowRequestContext, screen.getInitActionListener());
	}

	@Override
	public void preDelete(RequestContext flowRequestContext) {
		super.preDelete(flowRequestContext);
		callListener(flowRequestContext, screen.getPreActionListener());
	}
	
	@Override
	public void preCreate(RequestContext flowRequestContext) {
		super.preCreate(flowRequestContext);
		callListener(flowRequestContext, screen.getPreActionListener());
	}

	@Override
	public void preUpdate(RequestContext flowRequestContext) {
		super.preUpdate(flowRequestContext);
		callListener(flowRequestContext, screen.getPreActionListener());
	}
	
	@Override
	public void postCreate(RequestContext flowRequestContext) {
		super.postCreate(flowRequestContext);
		callListener(flowRequestContext, screen.getPostActionListener());
	}

	@Override
	public void postUpdate(RequestContext flowRequestContext) {
		super.postUpdate(flowRequestContext);
		callListener(flowRequestContext, screen.getPostActionListener());
	}

	@Override
	public void postDelete(RequestContext flowRequestContext) {
		super.postDelete(flowRequestContext);
		callListener(flowRequestContext, screen.getPostActionListener());
	}
	
	private void callListener(RequestContext flowRequestContext, ScreenListener listener){
		try {
			if (listener!=null){
				listener.execute(flowRequestContext, this, getDataView().getSelected());
			}
		} catch (Exception e) {
			getLog().error("Ocurrio un error", e);
		}
		
	}
	
	/**
	 * Se ejecuta cuando se agrega un componente ajax con un evento 
	 * @param actionEvent
	 */
	public final void onEvent(FacesEvent actionEvent){
		String eventName = (String) getEventAttribute(actionEvent, EVENT_NAME);
		String eventElementId = (String) getEventAttribute(actionEvent, EVENT_ELEMENT_ID);
		try {
			ScreenElement screenElement = screen.getSearchMap().recursiveFind(new Long(eventElementId));
			Collection args = new ArrayList();
			args.add(actionEvent);
			args.add(this);
			MethodUtils.invokeMethod(screenElement, eventName, args.toArray());
		} catch (Exception e) {
			getLog().error("Ocurrio un error onEvent", e);
		} 
	}
}

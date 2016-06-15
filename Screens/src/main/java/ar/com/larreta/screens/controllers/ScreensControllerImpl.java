package ar.com.larreta.screens.controllers;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.exceptions.PaginatorNotFoundException;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.impl.ScreenListener;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreensControllerImpl extends StandardControllerImpl {

	private static final String SCREEN_ID = "screenId";
	private static final String SCREEN_REF = "screen";
	
	private Screen screen;

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
	
	public void getScreen(RequestContext flowRequestContext) {
		try {
			screen = (Screen) getService().getScreen(getScreenId(flowRequestContext));
			flowRequestContext.getFlowScope().put(SCREEN_REF, screen);
			if (!StringUtils.isEmpty(screen.getEntityClass())){
				setEntityClass(getClass().getClassLoader().loadClass(screen.getEntityClass()));
			}
		} catch (Exception e){
			getLog().error("Ocurrio un error", e);
		}
	}

	@Override
	public void starting(RequestContext flowRequestContext) throws PaginatorNotFoundException {
		getScreen(flowRequestContext);
		super.starting(flowRequestContext);
	}

	@Override
	public void initCreate(RequestContext flowRequestContext) {
		getScreen(flowRequestContext);
		super.initCreate(flowRequestContext);
	}

	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		getScreen(flowRequestContext);
		super.initUpdate(flowRequestContext);
	}

	@Override
	public void postCreate(RequestContext flowRequestContext) {
		super.postCreate(flowRequestContext);
		callListener(flowRequestContext);
	}

	@Override
	public void postUpdate(RequestContext flowRequestContext) {
		super.postUpdate(flowRequestContext);
		callListener(flowRequestContext);
	}

	@Override
	public void postDelete(RequestContext flowRequestContext) {
		super.postDelete(flowRequestContext);
		callListener(flowRequestContext);
	}
	
	private void callListener(RequestContext flowRequestContext){
		try {
			ScreenListener listener = screen.getScreenListener();
			if (listener!=null){
				listener.execute(getDataView().getSelected());
			}
		} catch (Exception e) {
			getLog().error("Ocurrio un error", e);
		}
		
	}
	
}

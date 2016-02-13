package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.views.DataView;
import ar.com.larreta.smarttrace.domain.Classification;
import ar.com.larreta.smarttrace.domain.Provider;
import ar.com.larreta.smarttrace.domain.UnitType;
import ar.com.larreta.smarttrace.views.MaterialTypeDataView;

public class MaterialTypeController extends StandardControllerImpl {
	
	@Override
	public void initCreate(RequestContext flowRequestContext) {
		super.initCreate(flowRequestContext);
		loadUnitsTypes();
		loadProviders();
		loadClassifications();
	}
	
	@Override
	public void initUpdate(RequestContext flowRequestContext) {
		super.initUpdate(flowRequestContext);
		loadUnitsTypes();
		loadProviders();
		loadClassifications();
	}
	
	public void hacerAlgo(){
		System.out.println("Hola");
	}
	
	private void loadClassifications() {
		List<Classification> classifications = new ArrayList<Classification>();
		classifications.addAll(service.load(Classification.class));
		((MaterialTypeDataView) dataView).setClassifications(classifications);
	}

	private void loadProviders() {
		List<Provider> providers = new ArrayList<Provider>();
		providers.addAll(service.load(Provider.class));
		((MaterialTypeDataView) dataView).setProviders(providers);
	}

	private void loadUnitsTypes() {
		List<UnitType> unitsTypes = new ArrayList<UnitType>();
		unitsTypes.addAll(service.load(UnitType.class));
		((MaterialTypeDataView) dataView).setUnitsTypes(unitsTypes);
	}
	
}

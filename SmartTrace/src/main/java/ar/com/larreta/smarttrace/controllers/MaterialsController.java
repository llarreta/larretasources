package ar.com.larreta.smarttrace.controllers;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.exceptions.AppException;
import ar.com.larreta.smarttrace.domain.UnitType;

public class MaterialsController extends StandardControllerImpl {

	/**
	 * Retorna los tipos de unidades disponibles
	 * @return
	 */
	public Collection<UnitType> getAvaiablesUnitsTypes(){
		try {
			return service.load(UnitType.class);
		} catch (Exception e) {
			getLog().error(AppException.getStackTrace(e));
		}
		return new ArrayList<UnitType>();
	}
	
	
	
}

package ar.com.larreta.rest.business.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.larreta.persistence.model.ParametricEntity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.messages.ParametricData;

public abstract class LoadParametricEntityBusinessImpl<B extends ParametricData, E extends ParametricEntity> extends LoadBusinessImpl<B, E> {

	@Override
	@Autowired @Qualifier(BusinessListenerConfig.PARAMETRIC_DATA_BEFORE_LOAD_LISTENER)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}
}

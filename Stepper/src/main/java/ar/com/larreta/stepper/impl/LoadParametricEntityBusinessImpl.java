package ar.com.larreta.stepper.impl;

import ar.com.larreta.mystic.model.ParametricEntity;
import ar.com.larreta.stepper.messages.ParametricData;

public abstract class LoadParametricEntityBusinessImpl<B extends ParametricData, E extends ParametricEntity> extends LoadBusinessImpl<B, E> {

	/*@Override
	@Autowired @Qualifier(BusinessListenerConfig.PARAMETRIC_DATA_BEFORE_LOAD_LISTENER)
	public void setBeforeLoadListeners(Set<BusinessListener> beforeLoadListeners) {
		super.setBeforeLoadListeners(beforeLoadListeners);
	}*/
}

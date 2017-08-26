package ar.com.larreta.stepper.impl;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.SourcedListener;
import ar.com.larreta.stepper.TargetedListener;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.tools.TypedClassesUtils;

/**
 * Asigna un objeto serializable sobre un objeto de tipo json
 * en su totalidad 
 *
 * @param <E>
 */
//FIXME: Revisar si es necesario recibir con E un JSONable o puede ser siempre ParametricDAta
@Deprecated
public abstract class JsonAsignBusinessListener <E extends JSONable>  extends StepImpl
		implements SourcedListener, TargetedListener {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{

		JSONable json = (JSONable) applicationContext.getBean(TypedClassesUtils.getGenerics(JsonAsignBusinessListener.class, this, 0));
		Serializable data = (Serializable) beanUtils.read(source, getSourceProperty());
		
		beanUtils.copy(data, json);
		
		beanUtils.write(target, getTargetProperty(), json);
		
		return null;
	}

}

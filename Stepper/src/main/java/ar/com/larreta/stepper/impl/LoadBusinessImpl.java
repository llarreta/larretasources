package ar.com.larreta.stepper.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.mystic.query.Query;
import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.LoadBusiness;
import ar.com.larreta.stepper.aspects.BeforeCallStep;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.messages.Body;
import ar.com.larreta.stepper.messages.JSONable;
import ar.com.larreta.stepper.messages.JSONableCollection;
import ar.com.larreta.stepper.messages.LoadBody;
import ar.com.larreta.tools.Const;
import ar.com.larreta.tools.TypedClassesUtils;

@Transactional @Scope(Const.PROTOTYPE)
public abstract class LoadBusinessImpl <B extends JSONable, E extends Entity> extends StepImpl implements LoadBusiness{

	public static final String FIRST_RESULT = "firstResult";

	private static @Log Logger LOG;	
	
	private Select select;
	
	public Select getSelect() {
		return select;
	}
	
	@PostConstruct
	public void initilize(){
		select = applicationContext.getBean(Select.class);
		Class<?> entityType 	= TypedClassesUtils.getGenerics(LoadBusinessImpl.class, this, 1);
		select.addMainEntityProjection(entityType.getName());
	}
	
	@BeforeCallStep(steps={	CleanLimitsResultsBusinessListener.BUSINESS_LISTENER_NAME, 
							MaxResultsBusinessListener.BUSINESS_LISTENER_NAME, 
							FirstResultBusinessListener.BUSINESS_LISTENER_NAME})
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		try {
			Class<?> loadDataType 	= TypedClassesUtils.getGenerics(LoadBusinessImpl.class, this, 0);
			
			Body body = (Body) source;
			
			JSONableCollection<B> jsonableCollection = new JSONableCollection<B>();	
			LoadBody<B> response = new LoadBody<>();
			response.setResult(jsonableCollection);
			
			Collection result = execute(select, source, target, args);
				
			if (result!=null){
				Iterator<E> it = result.iterator();
				while (it.hasNext()) {
					E entity = null;
					Object actual = it.next();
					if (actual instanceof Object[]) {
						Object[] actualArray = (Object[]) actual;
						entity = (E) actualArray[0];
					} else {
						entity = (E) actual;
					}
					B bodyElement = (B) applicationContext.getBean(loadDataType);
					B processResult = (B) process(entity, bodyElement);
					jsonableCollection.add(processResult);
				}
			}

			response.setMaxResults(result.size());
			Integer firstResult = 0;
			if (body!=null){
				firstResult = (Integer) beanUtils.read(body, FIRST_RESULT);
				if (firstResult==null){
					firstResult=0;
				}
			}
			response.setFirstResult(firstResult);
			
			return response;
		} catch (Exception e){
			LOG.error("Ocurrio un error ejecutando LoadBusinessImpl", e);
		}
		throw new BusinessException();
	}

	public B process(E entity, B bodyElement) {
		beanUtils.copy(entity, bodyElement);
		return bodyElement;
	}

	protected Collection execute(Query query, Serializable source, Serializable target, Object... args) throws PersistenceException {
		Collection result = query.execute();
		return result;
	}
}

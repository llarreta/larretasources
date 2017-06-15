package ar.com.larreta.school.business.paymentPlans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.rest.messages.JSONable;

public abstract class PaymentPlansBeforePersistListenerTemplate extends BusinessListenerImpl {

	private static @Log Logger LOG;
	
	private Set<BusinessListener> beforePersistListeners;

	public Set<BusinessListener> getBeforePersistListeners() {
		return beforePersistListeners;
	}

	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		this.beforePersistListeners = beforePersistListeners;
	}
	
	protected Serializable processGeneral(String collectionPropertyName, Class type, JSONable json, Entity entity, Object... args) throws BusinessException {
		Set collection = new HashSet();
		beanUtils.write(entity, collectionPropertyName, collection);

		Iterator it = ((Collection)beanUtils.read(json, collectionPropertyName)).iterator();
		
		while (it.hasNext()) {
			try {
				JSONable jsonData = (JSONable) it.next();
				Entity actualEntity = (Entity) applicationContext.getBean(type);
				
				beanUtils.copy(jsonData, actualEntity);
				
				BusinessImpl.callListeners(beforePersistListeners, jsonData, actualEntity, null);
				collection.add(actualEntity);
			} catch (Exception e){
				LOG.error("Ocurrio un error en PaymentPlansBeforePersistListener", e);
			}
		}
		
		return null;
	}


}

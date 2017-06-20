package ar.com.larreta.school.business.paymentPlans;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.business.impl.BusinessImpl;
import ar.com.larreta.rest.business.impl.BusinessListenerImpl;
import ar.com.larreta.rest.business.impl.SourcedListener;
import ar.com.larreta.rest.business.impl.TargetedListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class PaymentPlansListener<E extends Serializable>  extends BusinessListenerImpl implements SourcedListener, TargetedListener{

	private static @Log Logger LOG;
	
	private Set<BusinessListener> beforePersistListeners;

	public String property;
	
	public PaymentPlansListener(String property) {
		super();
		this.property = property;
	}

	@Override
	public String getSourceProperty() {
		return property;
	}

	@Override
	public String getTargetProperty() {
		return property;
	}
	
	public Set<BusinessListener> getBeforePersistListeners() {
		return beforePersistListeners;
	}

	public void setBeforePersistListeners(Set<BusinessListener> beforePersistListeners) {
		this.beforePersistListeners = beforePersistListeners;
	}

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		Collection collection = (Collection) beanUtils.newInstanceType(target, getTargetProperty());
		beanUtils.write(target, getTargetProperty(), collection);

		Iterator it = beanUtils.iterator(source, getSourceProperty());
		
		while (it.hasNext()) {
			try {
				Serializable sourceFromCollection = (Serializable) it.next();
				Serializable targetToCollection = (Serializable) applicationContext.getBean(
							TypedClassesUtils.getGenerics(PaymentPlansListener.class, this, 0));
				
				beanUtils.copy(sourceFromCollection, targetToCollection);
				
				BusinessImpl.callListeners(beforePersistListeners, sourceFromCollection, targetToCollection, null);
				collection.add(targetToCollection);
			} catch (Exception e){
				LOG.error("Ocurrio un error en PaymentPlansBeforePersistListener", e);
			}
		}
		
		return null;
	}


}

package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.rest.business.BusinessListener;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class IteratorListener<E extends Serializable>  extends BusinessListenerImpl implements SourcedListener, TargetedListener{

	private static @Log Logger LOG;
	
	private Set<BusinessListener> beforeIterateListeners;
	private Set<BusinessListener> afterIterateListeners;
	
	private Set args = new HashSet<>();
	
	public void addArg(Serializable arg){
		args.add(arg);
	}

	public Set<BusinessListener> getBeforeIterateListeners() {
		return afterIterateListeners;
	}

	public void setBeforeIterateListeners(Set<BusinessListener> beforeIterateListeners) {
		this.afterIterateListeners = beforeIterateListeners;
	}

	public Set<BusinessListener> getAfterIterateListeners() {
		return afterIterateListeners;
	}

	public void setAfterIterateListeners(Set<BusinessListener> afterIterateListeners) {
		this.afterIterateListeners = afterIterateListeners;
	}
	
	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException{
		Collection collection = initializePersist(getTarget(source, target, args));

		Iterator it = beanUtils.iterator(getSource(source, target, args), getSourceProperty());
		
		while (it.hasNext()) {
			try {
				Serializable sourceFromCollection = (Serializable) it.next();
				Serializable targetToCollection = getTargetToCollection(sourceFromCollection);
				
				BusinessImpl.callListeners(afterIterateListeners, sourceFromCollection, targetToCollection, this.args.toArray());
				exectue(sourceFromCollection, targetToCollection);
				persist(collection, targetToCollection);
				BusinessImpl.callListeners(beforeIterateListeners, sourceFromCollection, targetToCollection, this.args.toArray());
			} catch (Exception e){
				LOG.error("Ocurrio un error en IteratorListener", e);
			}
		}
		
		return null;
	}

	protected Serializable getTargetToCollection(Serializable sourceFromCollection) {
		Serializable targetToCollection = (Serializable) applicationContext.getBean(TypedClassesUtils.getGenerics(IteratorListener.class, this, 0));
		return targetToCollection;
	}

	public Serializable getTarget(Serializable source, Serializable target, Object... args) {
		return target;
	}

	public Serializable getSource(Serializable source, Serializable target, Object... args) {
		return source;
	}
	
	/**
	 * Inicializa lo necesario para poder persistir lo generado
	 * @param getTarget(target)
	 * @return
	 */
	public Collection initializePersist(Serializable target) {
		Collection collection = (Collection) beanUtils.newInstanceType(target, getTargetProperty());
		beanUtils.write(target, getTargetProperty(), collection);
		return collection;
	}

	/**
	 * Almacena el objeto generado en la nueva collection
	 * @param collection
	 * @param getTarget(targetToCollection)
	 */
	public void persist(Collection collection, Serializable targetToCollection) {
		collection.add(targetToCollection);
	}

	/**
	 * Ejecuta la accion por cada elemento de la collection del source
	 * @param getTarget(sourceFromCollection)
	 * @param getTarget(targetToCollection)
	 */
	public void exectue(Serializable sourceFromCollection, Serializable targetToCollection)  throws Exception{
		beanUtils.copy(sourceFromCollection, targetToCollection);
	}


}

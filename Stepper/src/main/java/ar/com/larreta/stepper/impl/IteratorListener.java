package ar.com.larreta.stepper.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;

import ar.com.larreta.annotations.Log;
import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class IteratorListener<E extends Serializable>  extends StepImpl implements SourcedListener, TargetedListener{

	private static @Log Logger LOG;
	
	private Set args = new HashSet<>();
	
	public void addArg(Serializable arg){
		args.add(arg);
	}

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		Collection collection = initializePersist(getTarget(source, target, args));

		Iterator it = beanUtils.iterator(getSource(source, target, args), getSourceProperty());
		
		while (it.hasNext()) {
			try {
				Serializable sourceFromCollection = (Serializable) it.next();
				Serializable targetToCollection = getTargetToCollection(sourceFromCollection);

				collection.add(process(sourceFromCollection, targetToCollection));

			} catch (Exception e){
				LOG.error("Ocurrio un error en IteratorListener", e);
			}
		}
		
		return null;
	}

	public Serializable process(Serializable sourceFromCollection, Serializable targetToCollection) {
		beanUtils.copy(sourceFromCollection, targetToCollection);
		return targetToCollection;
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


}

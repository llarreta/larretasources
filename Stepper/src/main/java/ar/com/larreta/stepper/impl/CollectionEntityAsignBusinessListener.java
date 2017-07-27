package ar.com.larreta.stepper.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class CollectionEntityAsignBusinessListener<E extends Entity> extends StepImpl
		implements SourcedListener, TargetedListener {

	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException{
		Collection ids = (Collection) beanUtils.read(source, getSourceProperty());
		
		if (ids!=null){
			Collection targetCollection = (Collection) beanUtils.newInstanceType(target, getTargetProperty());
			beanUtils.write(target, getTargetProperty(), targetCollection);
			
			Iterator<Long> it = ids.iterator();
			while (it.hasNext()) {
				Long id = (Long) it.next();
				if (id!=null){
					Entity entity = (Entity) applicationContext.getBean(TypedClassesUtils.getGenerics(CollectionEntityAsignBusinessListener.class, this, 0));
					if (entity!=null){
						targetCollection.add(entity);
						beanUtils.write(entity, "id", id);
					}
				}
				
			}
		}
		
		return null;
	}

}

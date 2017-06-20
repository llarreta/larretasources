package ar.com.larreta.rest.business.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import ar.com.larreta.persistence.model.Entity;
import ar.com.larreta.rest.exceptions.BusinessException;
import ar.com.larreta.tools.TypedClassesUtils;

public abstract class CollectionEntityAsignBusinessListener<E extends Entity> extends BusinessListenerImpl
		implements SourcedListener, TargetedListener {

	@Override
	public Serializable process(Serializable source, Serializable target, Object... args) throws BusinessException {
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

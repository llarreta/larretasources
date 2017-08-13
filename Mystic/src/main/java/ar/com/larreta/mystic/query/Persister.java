package ar.com.larreta.mystic.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.model.Entity;
import ar.com.larreta.tools.Const;
import ar.com.larreta.tools.PropertyAnnotation;
import ar.com.larreta.tools.TypedClassesUtils;

@Component @Scope(Const.PROTOTYPE)
public class Persister extends Query {
	
	public void save(Entity entity){
		sessionFactory.getCurrentSession().save(entity);
	}
	
	public void saveOrUpdate(Entity entity){
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}
	
	public void update(Entity entity) throws PersistenceException{
		orphanRemoval(entity);
		sessionFactory.getCurrentSession().update(entity);
	}
	
	public void delete(Entity entity){
		sessionFactory.getCurrentSession().delete(entity);
	}
	
	public Entity get(Class type, Serializable id){
		return (Entity) sessionFactory.getCurrentSession().get(type, id);
	}
	
	public void orphanRemoval(Entity entity) throws PersistenceException{
		Collection<PropertyAnnotation> annotatedProperties = beanUtils.getAnnotatedProperties(entity.getClass(), OneToMany.class);
		if (annotatedProperties!=null){
			Iterator<PropertyAnnotation> it = annotatedProperties.iterator();
			while (it.hasNext()) {
				PropertyAnnotation propertyAnnotation = (PropertyAnnotation) it.next();
				Class orhpanClass = TypedClassesUtils.getGeneric(entity.getClass(), propertyAnnotation.getProperty(), 0);
				Set orphansCandidates = (Set) beanUtils.read(entity, propertyAnnotation.getProperty());
	
				Select selectOrphans = applicationContext.getBean(Select.class);
				selectOrphans.addProjections("id");
				selectOrphans.addMainEntity(orhpanClass.getName());
				selectOrphans.addWhereEqual(((OneToMany)propertyAnnotation.getAnnotation()).mappedBy() + ".id", entity.getId());
				
				if (orphansCandidates!=null){
					Collection<Long> ids = new ArrayList<>();
					Iterator<Entity> itEntities = orphansCandidates.iterator();
					while (itEntities.hasNext()) {
						Entity orphanCandidate = (Entity) itEntities.next();
						if (orphanCandidate.getId()!=null){
							ids.add(orphanCandidate.getId());
						}
					}
					selectOrphans.addWhereNotIn("id", ids);

					Collection orphans = selectOrphans.execute();
					
					if (orphans!=null && orphans.size()>0){
						Update delete = applicationContext.getBean(Update.class);
						delete.addMainEntity(orhpanClass.getName());
						delete.addSet("deleted", new Date());
						delete.addWhereIn("id", orphans);
						delete.execute();
					}
					/*if (orphans.size()>0){
						Iterator<Entity> itOrphans = orphans.iterator();
						while (itOrphans.hasNext()) {
							delete(itOrphans.next());
							
						}
						Delete delete = applicationContext.getBean(Delete.class);
						delete.addMainEntity(orhpanClass.getName());
						delete.addWhereIn("id", selectOrphans.execute());
						
						delete.execute();
					}*/

					Iterator<Entity> itEntity = orphansCandidates.iterator();
					while (itEntity.hasNext()) {
						Entity orphan = (Entity) itEntity.next();
						orphanRemoval(orphan);
					}
				}
				
				
			}
		}
	}
}

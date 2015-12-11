package ar.com.larreta.commons.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.exceptions.AppException;

/**
 * Procesa el resultado del DAO y lo vuelca sobre la entidad principal 
 *
 */
public class ResultProcessor extends AppObjectImpl {
	
	private List<Entity> entities;
	
	private LoadArguments args;
	
	public Collection<Entity> getEntities() {
		return entities;
	}

	public ResultProcessor(List result, LoadArguments args){
		this.args = args;
		entities = new ArrayList<Entity>();
		try {
			Iterator<Object[]> itList = result.iterator();
			while (itList.hasNext()) {
				Object[] objects = null;
				
				try {
					objects = (Object[]) itList.next();
				} catch (ClassCastException e){
					// Si ocurre un problema de casteo, asumimos que es debido a que no se incluyo ProjectedProperties por lo que tenemos que retonrnar las entidades tal cual llegan.
					entities = result;
					break;
				}
				
				if ((objects!=null) && (objects.length>0)){
					Entity entity = (Entity) objects[0];
				
					Integer index = 1;
					Iterator<ProjectedProperty> itProjected = args.getAllOrdereProperties().iterator();
					
					Map<String, Entity> toSetEntities = new HashMap<String, Entity>();
					toSetEntities.put(StringUtils.EMPTY, entity);
					
					while (itProjected.hasNext()) {
						ProjectedProperty projectedProperty = (ProjectedProperty) itProjected.next();
						Entity toSet = toSetEntities.get(projectedProperty.getPrefix());
						Entity value = (Entity) objects[index];
						
						projectedProperty.setValue(toSet, value);
						
						toSetEntities.put(projectedProperty.getName(), value);
						index++;
					}
					
					// Eviatamos que vuelva a agregar entidades que ya se encuentran en el resultado
					if (!entities.contains(entity)){
						entities.add(entity);
					}
				}
			}
		} catch (Exception e){
			getLog().error(AppException.getStackTrace(e));
		}

		
	}
	
}

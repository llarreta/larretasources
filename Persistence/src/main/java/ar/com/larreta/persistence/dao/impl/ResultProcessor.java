package ar.com.larreta.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.persistence.dao.args.LoadArguments;
import ar.com.larreta.persistence.model.impl.PersistenceEntityImpl;

/**
 * Procesa el resultado del DAO y lo vuelca sobre la entidad principal 
 *
 */
public class ResultProcessor {
	
	private static final Logger LOGGER = Logger.getLogger(ResultProcessor.class);
	
	private List<PersistenceEntityImpl> entities;
	
	private LoadArguments args;
	
	public Collection<PersistenceEntityImpl> getEntities() {
		return entities;
	}

	public ResultProcessor(List result, LoadArguments args){
		this.args = args;
		entities = new ArrayList<PersistenceEntityImpl>();
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
					PersistenceEntityImpl entity = (PersistenceEntityImpl) objects[0];
				
					Integer index = 1;
					Iterator<ProjectedProperty> itProjected = args.getAllOrdereProperties().iterator();
					
					Map<String, PersistenceEntityImpl> toSetEntities = new HashMap<String, PersistenceEntityImpl>();
					toSetEntities.put(StringUtils.EMPTY, entity);
					
					while (itProjected.hasNext()) {
						ProjectedProperty projectedProperty = (ProjectedProperty) itProjected.next();
						PersistenceEntityImpl toSet = toSetEntities.get(projectedProperty.getPrefix());
						PersistenceEntityImpl value = (PersistenceEntityImpl) objects[index];
						
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
			LOGGER.error("Ocurrio un error instanciando ResultProcessor" , e);
		}

		
	}
	
}

package ar.com.larreta.commons.persistence.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Se encarga de splittear todas las propiedades que seran necesarias obtener de la base
 * para poder retornar lo que el usuario pretende 
 */
public class ProjectedPropertiesSplitter implements Serializable {

	private List<ProjectedProperty> ordererProperties;
	private LoadArguments args;

	public ProjectedPropertiesSplitter(LoadArguments args){
		this.args = args;
		// Splitteamos las propiedades para poder determinar todos los atributos a obtener de la base
		Set<ProjectedProperty> projected = splittProjectedProperties(args.getProjectedProperties());
		List<ProjectedProperty> ordererProjected = new ArrayList<ProjectedProperty>(projected);
		Collections.sort(ordererProjected, new Comparator<ProjectedProperty>() {
			public int compare(ProjectedProperty property1, ProjectedProperty property2) {
				return property1.getName().compareTo(property2.getName());
			}
		});
		this.ordererProperties = ordererProjected;
	}

	public List<ProjectedProperty> getOrdererProperties() {
		return ordererProperties;
	}
	
	private Set<ProjectedProperty> splittProjectedProperties(Collection<ProjectedProperty> properties) {
		Set<ProjectedProperty> projected = new HashSet<ProjectedProperty>();
		if (properties!=null){ 
			Iterator<ProjectedProperty> it = properties.iterator();
			
			while (it.hasNext()) {
				ProjectedProperty projectedProperty = (ProjectedProperty) it.next();
				
				Collection<String> names = Arrays.asList(StringUtils.split(projectedProperty.getName(), StandardDAOImpl.DOT));
				Iterator<String> itNames = names.iterator();
				
				StringBuilder accumulatedName = new StringBuilder();
				
				// Para el primero
				if (itNames.hasNext()) {
					String property = (String) itNames.next();
					accumulatedName.append(property);
					
					addProjectedProperties(projected, projectedProperty, itNames, accumulatedName);
				}
				
				// Para el resto
				while (itNames.hasNext()) {
					String property = (String) itNames.next();
					accumulatedName.append(StandardDAOImpl.DOT);
					accumulatedName.append(property);
				
					addProjectedProperties(projected, projectedProperty, itNames, accumulatedName);
				}
			}
		}
		
		return projected;
	}
	
	private void addProjectedProperties(Set<ProjectedProperty> projected, ProjectedProperty toAdd, Iterator<String> itNames, StringBuilder accumulatedName) {
		ProjectedProperty projectedProperty = toAdd;
		if (itNames.hasNext()){
			projectedProperty = new ProjectedProperty(args, accumulatedName.toString());
		}
		projected.add(projectedProperty);
	}
	
}

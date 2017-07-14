package ar.com.larreta.persistence.query;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class ReferencesManager implements Serializable {

	public static final String DOT = ".";
	
	@Autowired
	protected ApplicationContext 		applicationContext;
	
	private Query query;

	private Map<String, Reference> references = new HashMap<>();

	
	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
	/**
	 * Retorna una Reference
	 * Si ya existe retorna la referencia 
	 * De lo contrario la construye 
	 * @param description
	 * @return
	 */
	public Reference buildReference(String description){
		//Busco si ya existe la referencia
		Reference reference = searchReferenceByDescription(description);
		if (reference==null){
			// Como no existe creo una nueva
			reference = applicationContext.getBean(Reference.class);
			reference.setDescription(description);
			reference.setQuery(query);
			
			// obtengo la referencia del padre
			Integer lastDot = description.lastIndexOf(DOT);
			Reference parent = query.getMainEntity();
			if (lastDot>0){
				parent = buildReference(description.substring(0, lastDot));
				// se agrega el join con la entidad padre
				query.addInnerJoin(parent);
			}
			reference.setParentReference(parent);
		}
		references.put(description, reference);
		return reference;
	}
	
	/**
	 * Busca una referencia por la descripcion
	 * @param description
	 * @return
	 */
	public Reference searchReferenceByDescription(String description){
		return references.get(description);
	}
	
}

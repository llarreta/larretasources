package ar.com.larreta.persistence.query;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Reference implements Serializable {

	public static final String DOT = ".";
	
	private Reference parentReference;
	private Query query;
	
	private String description;
	private String alias;

	public Reference getParentReference() {
		return parentReference;
	}

	public void setParentReference(Reference parentReference) {
		this.parentReference = parentReference;
	}
	
	public void generateAlias(){
		String[] descriptionSplitted = StringUtils.split(description, DOT);
		Collection<String> splittedTexts = Arrays.asList(descriptionSplitted);
		Iterator<String> it = splittedTexts.iterator();
		StringBuilder descriptionBuilder = new StringBuilder();
		Boolean first = Boolean.TRUE;
		while (it.hasNext()) {
			String partialDescription = (String) it.next();
			descriptionBuilder.append(partialDescription);
			
			Reference reference = query.getReferencesManager().searchReferenceByDescription(descriptionBuilder.toString());
			if (reference!=null){
				parentReference = reference;
			}
			
			if (!first){
				descriptionBuilder.append(DOT);
			}
			first = Boolean.FALSE;
		}
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Reference) {
			Reference reference = (Reference) obj;
			return reference.getDescription().equals(getDescription());
			
		}
		return Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}
}

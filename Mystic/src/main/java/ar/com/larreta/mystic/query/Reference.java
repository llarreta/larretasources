package ar.com.larreta.mystic.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

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
	
	private Boolean mainEntity = Boolean.FALSE;
	
	public String getDescription() {
		return description;
	}

	public String getShortDescription() {
		Integer lastDot = description.lastIndexOf(DOT);
		String toShortDescription = description;
		if (lastDot>0){
			toShortDescription = description.substring(lastDot+1);
		}
		return toShortDescription;
	}

	public String getAlias() {
		return alias;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (containParent()){
			builder.append(parentReference.getAlias());
			builder.append(DOT);
		}
		String shortDescription = getShortDescription();
		if (mainEntity){
			shortDescription = getAlias();
		}
		builder.append(shortDescription);
		return builder.toString();
	}
	
	public Boolean getMainEntity() {
		return mainEntity;
	}

	public void setMainEntity(Boolean mainEntity) {
		if (mainEntity!=null){
			this.mainEntity = mainEntity;
		}
	}

	public Boolean isAliasEnable(){
		return containParent() && (description.indexOf(DOT)>0);
	}

	public Boolean containParent() {
		return parentReference!=null;
	}
	
	public Reference getParentReference() {
		return parentReference;
	}

	public void setParentReference(Reference parentReference) {
		this.parentReference = parentReference;
	}
	
	public void setDescription(String description) {
		this.description = description;
		alias = generateAlias();
	}

	private String generateAlias() {
		Collection<String> splitted = ar.com.larreta.tools.StringUtils.splitWords(getShortDescription());
		Iterator<String> it = splitted.iterator();
		StringBuilder builder = new StringBuilder();
		while (it.hasNext()) {
			String toGenerateAlias = it.next().toUpperCase();
			if (toGenerateAlias.length()>3){
				toGenerateAlias = toGenerateAlias.substring(0, 3);
			}
			builder.append(toGenerateAlias);
		}
	
		String index = new Long(System.currentTimeMillis()).toString();
		return builder.toString() + index.substring(index.length() - 2);
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

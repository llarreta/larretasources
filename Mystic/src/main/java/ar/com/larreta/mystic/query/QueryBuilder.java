package ar.com.larreta.mystic.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;
import ar.com.larreta.tools.StringUtils;

@Component @Scope(Const.PROTOTYPE)
public class QueryBuilder implements Serializable {

	public static final String AND = "AND";
	public static final String FROM = "FROM";
	public static final String WHERE = "WHERE";
	
	private Query query;

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
	public String build(){
		StringBuilder builder = new StringBuilder();

		buildInstruction(builder);
		buildProjections(builder);
		buildFrom(builder);
		buildJoins(builder);
		buildWheres(builder);
		
		return builder.toString();
	}

	public void buildWheres(StringBuilder builder) {
		if (query.getWheres().size()>0){
			builder.append(WHERE);
			builder.append(Const.SPACE);
			Iterator<Where> it = query.getWheres().iterator();
			Boolean first = Boolean.TRUE;
			while (it.hasNext()) {
				if (!first){
					builder.append(Const.SPACE);
					builder.append(AND);
					builder.append(Const.SPACE);
				}
				Where where = (Where) it.next();
				builder.append(where);
				first = Boolean.FALSE;
			}
		}
	}

	private void buildJoins(StringBuilder builder) {
		List<Join> listJoins = new ArrayList<>(query.getJoins());
		Collections.sort(listJoins, new Comparator<Join>() {
			@Override
			public int compare(Join joinA, Join joinB) {
				return joinA.getReference().getVirtualPath().compareTo(joinB.getReference().getVirtualPath());
			}
		});
		
		Iterator<Join> itJoins = listJoins.iterator();
		while (itJoins.hasNext()) {
			Join join = (Join) itJoins.next();
			builder.append(join.getJoinType());
			builder.append(Const.SPACE);
			builder.append(join.getReference().toString());
			builder.append(Const.SPACE);
			builder.append(join.getReference().getAlias());
			builder.append(Const.SPACE);
		}
	}

	private void buildFrom(StringBuilder builder) {
		builder.append(Const.SPACE);
		builder.append(FROM);
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getDescription());
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getAlias());
		builder.append(Const.SPACE);
	}

	private void buildProjections(StringBuilder builder) {
		Boolean first = Boolean.TRUE;
		List<Reference> listProjections = new ArrayList(query.getProjections());
		Collections.sort(listProjections, new Comparator<Reference>() {
			@Override
			public int compare(Reference referenceA, Reference referenceB) {
				if (referenceA.getParentReference()==null){
					return -1;
				}
				if (referenceB.getParentReference()==null){
					return 1;
				}
				return referenceA.getAlias().compareTo(referenceB.getAlias());
			}
		});
		Iterator<Reference> it = listProjections.iterator();
		while (it.hasNext()) {
			if (!first){
				builder.append(StringUtils.COMMA);
			}
			first = Boolean.FALSE;
			Reference reference = (Reference) it.next();
			String projection = reference.toString();
			if (reference.getParentReference()==null){
				projection = reference.getAlias();
			}
			builder.append(projection);
		}
	}

	private void buildInstruction(StringBuilder builder) {
		builder.append(query.getInstruction().getDescription());
		builder.append(Const.SPACE);
	}
	
	
}



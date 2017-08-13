package ar.com.larreta.mystic.query;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Delete extends Query {
	
	@Autowired @Qualifier(DeleteBuilder.NAME)
	@Override
	public void setQueryBuilder(QueryBuilder queryBuilder) {
		super.setQueryBuilder(queryBuilder);
	}

	@Autowired
	public void setInstruction(DeleteInstruction instruction) {
		super.setInstruction(instruction);
	}
	
	
	public void addInnerJoin(String description){
		//FIXME:Not supported, throw exception
	}
	
	public void addInnerJoin(Reference reference){
		//FIXME:Not supported, throw exception
	}

	@Override
	public void addMainEntityProjection(String mainEntity) {
		//FIXME:Not supported, throw exception
	}

	@Override
	public void addProjections(String... projections) {
		//FIXME:Not supported, throw exception
	}

	@Override
	protected Collection execute(org.hibernate.Query query) {
		return Arrays.asList(query.executeUpdate());
	}

}

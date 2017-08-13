package ar.com.larreta.mystic.query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component(DeleteBuilder.NAME) @Scope(Const.PROTOTYPE)
public class DeleteBuilder extends QueryBuilder {
	
	public final static String  NAME = "DeleteBuilder";

	@Override
	protected void buildFrom(StringBuilder builder) {
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getDescription());
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getAlias());
		builder.append(Const.SPACE);
	}

	@Override
	protected void buildProjections(StringBuilder builder) {
	}

	@Override
	protected void buildJoins(StringBuilder builder) {
	}
}

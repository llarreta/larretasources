package ar.com.larreta.mystic.query;

import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.mystic.query.wheres.Where;
import ar.com.larreta.tools.Const;

@Component(UpdateBuilder.NAME) @Scope(Const.PROTOTYPE)
public class UpdateBuilder extends QueryBuilder {
	
	public final static String  NAME = "UpdateBuilder";
	
	@Override
	public String build(){
		StringBuilder builder = new StringBuilder();

		buildInstruction(builder);
		buildFrom(builder);
		buildSets(builder);
		buildWheres(builder);
		
		return builder.toString();
	}
	
	protected void buildSets(StringBuilder builder) {
		buildWheres(((Update)query).getSets(), builder, "SET", ",");
	}
	
	@Override
	protected void buildFrom(StringBuilder builder) {
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getDescription());
		builder.append(Const.SPACE);
		builder.append(query.getMainEntity().getAlias());
		builder.append(Const.SPACE);
	}

}

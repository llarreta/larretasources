package ar.com.larreta.mystic.query.wheres;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component(NotIn.NAME)
@Scope(Const.PROTOTYPE)
public class NotIn extends In {
	
	public static final String NAME = "NotIn";

	@Override
	protected String getPrefix() {
		return " NOT IN ";
	}

}

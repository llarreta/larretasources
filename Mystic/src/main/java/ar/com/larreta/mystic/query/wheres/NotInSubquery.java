package ar.com.larreta.mystic.query.wheres;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component(NotInSubquery.NAME)
@Scope(Const.PROTOTYPE)
public class NotInSubquery extends InSubquery {
	public static final String NAME = "NotInSubquery";

	@Override
	protected String getPrefix() {
		return " NOT IN ";
	}
}

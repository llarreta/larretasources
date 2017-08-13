package ar.com.larreta.mystic.query.wheres;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component(EqualYear.NAME)
@Scope(Const.PROTOTYPE)
public class EqualYear extends Equal {

	public static final String NAME = "EqualYear";
	
	@Override
	protected String getReferenceToString() {
		return "YEAR(" + super.getReferenceToString() + ")";
	}

}

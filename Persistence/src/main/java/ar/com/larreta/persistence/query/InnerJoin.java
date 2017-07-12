package ar.com.larreta.persistence.query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class InnerJoin extends Join {

	public static final String JOIN_NAME = "INNER JOIN";

	public InnerJoin() {
		super(JOIN_NAME);
	}

}

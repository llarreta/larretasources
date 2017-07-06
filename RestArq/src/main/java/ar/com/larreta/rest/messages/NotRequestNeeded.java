package ar.com.larreta.rest.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component(NotRequestNeeded.COMPONENT_NAME) @Scope(Const.PROTOTYPE)
public class NotRequestNeeded extends Request<NotRequestNeededBody> {

	public static final String COMPONENT_NAME = "notRequestNeeded";
	
	@Autowired @Qualifier(NotRequestNeededBody.COMPONENT_NAME)
	@Override
	public void setBody(NotRequestNeededBody body) {
		super.setBody(body);
	}

}

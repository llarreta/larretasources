package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Role;

@Component
public class RoleSaver extends ParametricEntitySaver {
	
	public RoleSaver() {
		super();	
	}

	@Override
	public Class getABMClass() {
		return Role.class;
	}

}

package ar.com.larreta.commons.faces;

import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Role;

@Controller
public class RoleConverter extends EntityConverter {

	@Override
	public Entity getEntity() {
		return new Role();
	}

}

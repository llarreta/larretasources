package ar.com.larreta.commons.faces;

import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.domain.Profile;

@Controller
public class ProfileConverter extends EntityConverter {

	@Override
	public Entity getEntity() {
		return new Profile();
	}

}

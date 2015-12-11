package ar.com.larreta.prode.faces;

import org.springframework.stereotype.Controller;

import ar.com.larreta.commons.domain.Entity;
import ar.com.larreta.commons.faces.EntityConverter;
import ar.com.larreta.prode.domain.Player;

@Controller
public class PlayerConverter extends EntityConverter {

	@Override
	public Entity getEntity() {
		return new Player();
	}

}

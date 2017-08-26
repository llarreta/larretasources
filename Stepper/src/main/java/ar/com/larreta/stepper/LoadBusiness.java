package ar.com.larreta.stepper;

import ar.com.larreta.mystic.query.Select;

public interface LoadBusiness extends Step {
	public Select getSelect();
}

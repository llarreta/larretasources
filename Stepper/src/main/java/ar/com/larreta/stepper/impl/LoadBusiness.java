package ar.com.larreta.stepper.impl;

import ar.com.larreta.mystic.query.Select;
import ar.com.larreta.stepper.Step;

public interface LoadBusiness extends Step {
	public Select getSelect();
}

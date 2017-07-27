package ar.com.larreta.stepper;

import java.io.Serializable;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.stepper.exceptions.BusinessException;

public interface Step {

	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException;
}

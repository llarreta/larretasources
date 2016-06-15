package ar.com.larreta.screens.impl;

import java.io.Serializable;

import ar.com.larreta.commons.domain.Entity;

public interface ScreenListener extends Serializable{
	public void execute(Entity entity);
}

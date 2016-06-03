package ar.com.larreta.commons.controllers;

import java.util.List;

import ar.com.larreta.commons.domain.Entity;

public interface LoadListener {
	public void alert(List<Entity> entities);
}

package ar.com.larreta.screens.impl;

import java.io.Serializable;

import javax.faces.event.FacesEvent;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.screens.ListSelector;

public interface ListSelectorListener extends Serializable {
	
	public void process(FacesEvent facesEvent, StandardController controller, ListSelector listSelector);

}

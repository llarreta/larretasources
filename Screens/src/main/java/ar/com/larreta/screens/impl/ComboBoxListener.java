package ar.com.larreta.screens.impl;

import java.io.Serializable;

import javax.faces.event.FacesEvent;

import ar.com.larreta.commons.controllers.StandardController;
import ar.com.larreta.screens.ComboBox;

public interface ComboBoxListener extends Serializable {
	
	public void process(FacesEvent facesEvent, StandardController controller, ComboBox comboBox);

}

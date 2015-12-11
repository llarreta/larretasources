package ar.com.larreta.commons.controllers;

import javax.faces.event.ActionEvent;

import ar.com.larreta.commons.controllers.impl.StandardControllerImpl;
import ar.com.larreta.commons.domain.Notification;
import ar.com.larreta.commons.views.DataView;

public class NotificationsController extends StandardControllerImpl {
	
	/**
	 * Confirmacion de la accion en la notificacion
	 * @param actionEvent
	 */
	public final void confirm(ActionEvent actionEvent){
		setSelected(actionEvent);
		DataView dataView = getDataView();
		((Notification) dataView.getSelected()).execute();

		if (dataView.getNotAvaiableNotifications()){
			dataView.redirect("home");
		}
	}
}

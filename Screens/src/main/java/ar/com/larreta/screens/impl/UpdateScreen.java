package ar.com.larreta.screens.impl;

public abstract class UpdateScreen extends CreateScreen {

	public static final String PRE_UPDATE = "preUpdate";

	public UpdateScreen(Long id, Class entityClass) {
		super(id, entityClass);
	}

	protected String getConfirmAction() {
		return PRE_UPDATE;
	}

}

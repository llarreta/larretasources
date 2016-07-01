package ar.com.larreta.screens.impl;

public abstract class UpdateScreen extends CreateScreen {

	public static final String PRE_UPDATE = "preUpdate";

	public UpdateScreen(Class entityClass) {
		super(entityClass);
	}
	
	public UpdateScreen(Class entityClass, String listener){
		super(entityClass, listener);
	}
	
	@Override
	public Long getId() {
		return screenConstantIds.getIdentifier(getEntityClassShortName() + "Update");
	}

	protected String getConfirmAction() {
		return PRE_UPDATE;
	}

}

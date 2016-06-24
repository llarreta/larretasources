package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class ProfileSaver extends ABMSaver {

	private Class abmClass = Profile.class;
	
	public ProfileSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.PROFILE_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("description", 	"app.description", 	"tableElement.description",  	"80%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.PROFILE_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.PROFILE_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.PROFILE_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				ProfileSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.PROFILE_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.PROFILE_UPDATE, abmClass) {
			
			@Override
			public void initialize() {
				super.initialize();
				setLazyCollections("roles");
			}

			@Override
			protected void makeBody() {
				ProfileSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.PROFILE_MAIN;
			}
		};
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.description", 			"description");
		index = screen.addMultiBox(index, "app.roles.avaiables", "app.roles.assigned", "roles", Role.class.getName(), "description");
	}

}

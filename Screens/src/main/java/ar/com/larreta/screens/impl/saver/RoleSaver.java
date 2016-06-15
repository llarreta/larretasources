package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class RoleSaver extends ABMSaver {
	private Class abmClass = Role.class;
	
	public RoleSaver() {
		super();	

		mainScreen = new MainScreen(ScreenImplementationsIds.ROLE_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("description", 	"app.description", 		"tableElement.description", 	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.ROLE_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.ROLE_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.ROLE_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				RoleSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.ROLE_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.ROLE_UPDATE, abmClass) {
			
			@Override
			protected void makeBody() {
				RoleSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.ROLE_MAIN;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.description", "description");
	}

}

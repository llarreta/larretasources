package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

@Component
public class UserSaver extends ABMSaver {
	private Class abmClass = User.class;
	
	public UserSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.USER_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("nick", 	"app.nick", 	"tableElement.nick",  	"40%"));
				table.addColumn(0, getColumnWithLabelProperty("email", 	"app.email", 	"tableElement.email",  	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.USER_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.USER_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.USER_CREATE, abmClass) {

			@Override
			public void initialize() {
				super.initialize();
				setPreActionListenerName(UserListener.class.getName());
			}
			
			@Override
			protected void makeBody() {
				UserSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.USER_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.USER_UPDATE, abmClass) {
			
			@Override
			public void initialize() {
				super.initialize();
				setLazyCollections("profiles");
				setPreActionListenerName(UserListener.class.getName());
			}

			@Override
			protected void makeBody() {
				UserSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.USER_MAIN;
			}
		};
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.nick", 				"nick");
		index = screen.addPassword(index, "app.password", 		"password");
		index = screen.addInput(index, "app.email", 			"email");
		index = screen.addMultiBox(index, "app.profiles.avaiables", "app.profiles.assigned", "profiles", Profile.class.getName(), "description");
	}

}

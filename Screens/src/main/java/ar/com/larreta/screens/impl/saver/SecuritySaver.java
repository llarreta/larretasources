package ar.com.larreta.screens.impl.saver;

import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class SecuritySaver extends ABMSaver {
	private Class abmClass = Security.class;
	
	public SecuritySaver() {
		super();	
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.SECURITY_UPDATE, abmClass) {
			
			@Override
			public void initialize() {
				super.initialize();
				setInitActionListenerName(SecurityListener.class.getName());
			}

			@Override
			protected void makeBody() {
				SecuritySaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.SECURITY_UPDATE;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.loginPage",				 	"loginPage");
		index = screen.addInput(index, "app.loginProcessingUrl", 		"loginProcessingUrl");
		index = screen.addInput(index, "app.defaultSuccessUrl", 		"defaultSuccessUrl");
		index = screen.addInput(index, "app.failureUrl", 				"failureUrl");
		index = screen.addInput(index, "app.usernameParameter", 		"usernameParameter");
		index = screen.addInput(index, "app.passwordParameter", 		"passwordParameter");
		index = screen.addInput(index, "app.logoutUrl", 				"logoutUrl");
		index = screen.addInput(index, "app.logoutSuccessUrl", 			"logoutSuccessUrl");
		index = screen.addInput(index, "app.deleteCookies", 			"deleteCookies");
	}
}

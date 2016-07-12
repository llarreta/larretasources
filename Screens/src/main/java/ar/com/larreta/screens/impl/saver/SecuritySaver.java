package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Security;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.UpdateScreen;

@Component
public class SecuritySaver extends ABMSaver {
	
	public SecuritySaver() {
		updateScreen = new UpdateScreen(getABMClass()) {
			
			@Override
			public void initialize() {
				super.initialize();
				setInitActionListenerName(SecurityListener.class.getName());
			}
			
			@Override
			public Long getNextScreenId() {
				return getId();
			}

			@Override
			protected void makeBody() {
				SecuritySaver.this.makeBody(this);
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addCheckBox(index, "app.securityAvaiable",		 	"avaiable");
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

	@Override
	public Class getABMClass() {
		return Security.class;
	}

	@Override
	protected void makeColumn(MainScreen screen) {
	}
}

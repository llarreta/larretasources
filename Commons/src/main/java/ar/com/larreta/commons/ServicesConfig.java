package ar.com.larreta.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ar.com.larreta.commons.domain.Profile;
import ar.com.larreta.commons.services.StandardService;

//FIXME: Ver si aun es necesaria esta clase
@Configuration
@DependsOn("appConfig")
public class ServicesConfig extends AppObjectImpl {

	/*@Bean(name="profileService")
	public StandardService getProfileService(){
		StandardService service = AppManager.getInstance().getStandardService();
		service.setEntityClass(Profile.class);
		return service;
	}*/
	
}

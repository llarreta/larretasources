package ar.com.larreta.commons;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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

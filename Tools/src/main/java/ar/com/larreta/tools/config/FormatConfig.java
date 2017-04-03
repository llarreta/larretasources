package ar.com.larreta.tools.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:format.properties")
public class FormatConfig {
	
	@Value("${date.format}")
	private String dateFormatValue;
	
	@Bean
	public SimpleDateFormat dateFormatter(){
		return new SimpleDateFormat(dateFormatValue); 
	}
	

}

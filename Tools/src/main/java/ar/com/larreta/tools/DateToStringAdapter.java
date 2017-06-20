package ar.com.larreta.tools;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateToStringAdapter implements Adapter {

	@Autowired
	private BeanUtils beanUtils;
	
	@Value("${app.dateFormat}")
	private String defaultPattern;
	
	private SimpleDateFormat simpleDateFormat;

	@PostConstruct
	public void initialize(){
		simpleDateFormat = new SimpleDateFormat(defaultPattern);
		simpleDateFormat.setLenient(Boolean.FALSE);
	}
	
	@Override
	public void process(String property, Object source, Object target) throws Exception {
		beanUtils.write(target, property, simpleDateFormat.format(beanUtils.read(source, property)));
	}

}

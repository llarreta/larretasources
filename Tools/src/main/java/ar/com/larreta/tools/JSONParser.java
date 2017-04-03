package ar.com.larreta.tools;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JSONParser implements Serializable {

	private final static Logger LOGGER = Logger.getLogger(JSONParser.class);
	
	private ObjectMapper mapper = new ObjectMapper(); 
	
	public String parse(Object toParse) {
		try {
			return mapper.writeValueAsString(toParse);
		} catch (JsonProcessingException e) {
			LOGGER.error("Ocurrio un error generando JSON", e);
		}
		return StringUtils.EMPTY;
	}
	
}

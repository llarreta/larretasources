package ar.com.larreta.commons.utils.xml;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.commons.exceptions.AppException;

public class XMLHelper {
	
	private static final Logger LOGGER = Logger.getLogger(XMLHelper.class);

    public static String javaToXML(Object toPersist){
    	try{
	        JAXBContext context = JAXBContext.newInstance(toPersist.getClass());
	 
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        StringWriter stringWriter = new StringWriter();
	        
	        m.marshal(toPersist, stringWriter);
	        
	        return stringWriter.toString();
        } catch (Exception e) {
        	LOGGER.error(AppException.getStackTrace(e));
        }
    	return StringUtils.EMPTY;
    }
    
    public static Object xmlToJava(Class clase, String xml) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clase);
            Unmarshaller u = jc.createUnmarshaller();
            
            StringReader stringReader = new StringReader(xml);
            
            return u.unmarshal(stringReader);
        } catch (JAXBException e) {
        	LOGGER.error(AppException.getStackTrace(e));
        }
        return StringUtils.EMPTY;
    }
	
}

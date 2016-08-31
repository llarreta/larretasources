package co.com.ig.common.error.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class XmlUnmarshaller {
		
	public static Object unmarshaller(Class<?> aClass, String message) throws JAXBException, UnsupportedEncodingException{
		JAXBContext jc = JAXBContext.newInstance(aClass);
		Unmarshaller u = jc.createUnmarshaller();
		JAXBElement<?> root = (JAXBElement<?>) u.unmarshal(new StreamSource(new ByteArrayInputStream(message.getBytes("UTF-8"))));
		return root.getValue();
	}
}

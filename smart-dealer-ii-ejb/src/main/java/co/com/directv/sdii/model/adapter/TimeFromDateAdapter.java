package co.com.directv.sdii.model.adapter;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;

/**
 * Adaptador que permite obtener una hora de una fecha
 * y que esta sea serializada sin el offset de la zona horaria (agregando una Z al final); es decir
 * en formato hh:mm:ssZ
 * 
 * En los pojos donde se requiera usar, se debe usar la anotación @XmlJavaTypeAdapter(TimeFromDateAdapter.class)
 * en el getter del atributo Date.
 * También se puede asignar para que se use en todo un paquete con las anotaciones a nivel de paquete:
 * @XmlJavaTypeAdapter(value = UtcTimestampAdapter.class, type = Date.class)
 * @XmlSchemaType(name = "dateTime", type = XMLGregorianCalendar.class)
 * 
 * Fecha de Creación: 14/09/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class TimeFromDateAdapter extends XmlAdapter<XMLGregorianCalendar, Date> {
	
	private final static Logger log = UtilsBusiness.getLog4J(TimeFromDateAdapter.class);
	
	public Date unmarshal(XMLGregorianCalendar value) {
		Calendar cal = value.toGregorianCalendar();
		Date d = cal.getTime();
		return d;
	}

	public XMLGregorianCalendar marshal(Date value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		try {
			XMLGregorianCalendar xmlcal = DatatypeFactory.newInstance()
					.newXMLGregorianCalendarTime(cal.get(Calendar.HOUR_OF_DAY),
							cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND),
							0);
			return xmlcal;
		} catch (Exception e) {
			log.error("no se pudo transformar correctamente la fecha a formato de hora", e);
			return null;
		}
	}
	
}
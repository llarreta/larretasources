package co.com.directv.sdii.model.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador que permite obtener una fecha y que esta sea
 * serializada sin el offset de la zona horaria (agregando una Z al final)
 * 
 * En los pojos donde se requiera usar, se debe usar la anotación
 * UtcTimestampAdapter(UtcTimestampAdapter) en el getter del atributo
 * Date.
 * También se puede asignar para que se use en todo un paquete con la
 * anotaciones a nivel de paquete:
 * @XmlJavaTypeAdapter(value = UtcTimestampAdapter.class, type = Date.class)
 * @XmlSchemaType(name = "dateTime", type = XMLGregorianCalendar.class)
 * 
 * Fecha de Creación: 14/09/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class UtcTimestampAdapter extends XmlAdapter<Calendar, Date> {

	private static TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
	
	@Override
	public Calendar marshal(Date date) throws Exception {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.DST_OFFSET, 0);
		calendar.set(Calendar.ZONE_OFFSET, 0);
		calendar.setTimeZone(UTC_TIME_ZONE);
		
		return calendar;
	}

	@Override
	public Date unmarshal(Calendar calendar) throws Exception {
		return calendar.getTime();
	}

}
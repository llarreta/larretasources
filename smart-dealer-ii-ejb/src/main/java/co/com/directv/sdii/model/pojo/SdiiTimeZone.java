/**
 * Creado 25/11/2010 10:47:45
 */
package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 25/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class SdiiTimeZone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2875216350554732044L;

	private Long id;
	
	private String timeZoneName;
	
	private String timeZoneKey;
	
	private Long countryId;

	
	
	
	public SdiiTimeZone() {
		super();
	}

	
	
	public SdiiTimeZone(Long id, String timeZoneName, String timeZoneKey,
			Long countryId) {
		super();
		this.id = id;
		this.timeZoneName = timeZoneName;
		this.timeZoneKey = timeZoneKey;
		this.countryId = countryId;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeZoneName() {
		return timeZoneName;
	}

	public void setTimeZoneName(String timeZoneName) {
		this.timeZoneName = timeZoneName;
	}

	public String getTimeZoneKey() {
		return timeZoneKey;
	}

	public void setTimeZoneKey(String timeZoneKey) {
		this.timeZoneKey = timeZoneKey;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}



	@Override
	public String toString() {
		return "SdiiTimeZone [id=" + id + ", timeZoneKey=" + timeZoneKey + "]";
	}
	
}

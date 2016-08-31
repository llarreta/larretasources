package co.com.directv.sdii.model.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Entidad de la fecha de evento del procesamiento en paralelo de core y asignador
 * @author Aharker
 *
 */
public class WoInfoEsbParentDate implements Serializable{

	private Long id;
	private Country country;
	private Timestamp dateInfo;
	public WoInfoEsbParentDate() {
		super();
	}
	public WoInfoEsbParentDate(Long id, Country country, Timestamp dateInfo) {
		super();
		this.id = id;
		this.country = country;
		this.dateInfo = dateInfo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Timestamp getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(Timestamp dateInfo) {
		this.dateInfo = dateInfo;
	}
}

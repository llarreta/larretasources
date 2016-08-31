package co.com.directv.sdii.model.dto;

import java.io.Serializable;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerInfoAddressDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	private String customerKey;
	private String id;
	private String country;
	private String stateOrProvince;
	private String streetName;
	private int streetType;
	private String city;
	private String streetNrFirst;
	private String streetNrLast;
	private String streetSufix;
	private String postcode;
	private String elementInvolves;
	private String locality;
	private String careOfName;
    //private String geoCode;
	private String categorizedByname;
	private String extraIndications;
	
    public CustomerInfoAddressDTO(String customerKey, String id,
			String country, String stateOrProvince, String streetName,
			int streetType, String city, String streetNrFirst,
			String streetNrLast, String streetSufix, String postcode,
			String elementInvolves, String locality, String careOfName,
			String categorizedByname, String extraIndications) {
		super();
		this.customerKey = customerKey;
		this.id = id;
		this.country = country;
		this.stateOrProvince = stateOrProvince;
		this.streetName = streetName;
		this.streetType = streetType;
		this.city = city;
		this.streetNrFirst = streetNrFirst;
		this.streetNrLast = streetNrLast;
		this.streetSufix = streetSufix;
		this.postcode = postcode;
		this.elementInvolves = elementInvolves;
		this.locality = locality;
		this.careOfName = careOfName;
		this.categorizedByname = categorizedByname;
		this.extraIndications = extraIndications;
	}

	public String getExtraIndications() {
		return extraIndications;
	}

	public void setExtraIndications(String extraIndications) {
		this.extraIndications = extraIndications;
	}

	public CustomerInfoAddressDTO() {
		super();
	}
    
	public CustomerInfoAddressDTO(String customerKey, String id,
			String country, String stateOrProvince, String streetName,
			int streetType, String city, String streetNrFirst,
			String streetNrLast, String streetSufix, String postcode,
			String elementInvolves, String locality, String careOfName,
			String geoCode) {
		super();
		this.customerKey = customerKey;
		this.id = id;
		this.country = country;
		this.stateOrProvince = stateOrProvince;
		this.streetName = streetName;
		this.streetType = streetType;
		this.city = city;
		this.streetNrFirst = streetNrFirst;
		this.streetNrLast = streetNrLast;
		this.streetSufix = streetSufix;
		this.postcode = postcode;
		this.elementInvolves = elementInvolves;
		this.locality = locality;
		this.careOfName = careOfName;
		//this.geoCode = geoCode;
	}
	
	public String getCustomerKey() {
		return customerKey;
	}
	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStateOrProvince() {
		return stateOrProvince;
	}
	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public int getStreetType() {
		return streetType;
	}
	public void setStreetType(int streetType) {
		this.streetType = streetType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreetNrFirst() {
		return streetNrFirst;
	}
	public void setStreetNrFirst(String streetNrFirst) {
		this.streetNrFirst = streetNrFirst;
	}
	public String getStreetNrLast() {
		return streetNrLast;
	}
	public void setStreetNrLast(String streetNrLast) {
		this.streetNrLast = streetNrLast;
	}
	public String getStreetSufix() {
		return streetSufix;
	}
	public void setStreetSufix(String streetSufix) {
		this.streetSufix = streetSufix;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getElementInvolves() {
		return elementInvolves;
	}
	public void setElementInvolves(String elementInvolves) {
		this.elementInvolves = elementInvolves;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCareOfName() {
		return careOfName;
	}
	public void setCareOfName(String careOfName) {
		this.careOfName = careOfName;
	}
	public String getCategorizedByname() {
		return categorizedByname;
	}

	public void setCategorizedByname(String categorizedByname) {
		this.categorizedByname = categorizedByname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

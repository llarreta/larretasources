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
public class CustomerInfoIndividualINamedDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6287143018842312729L;
	
	private String formattedName;
	private String legalName;
	private String aristocraticTitle;
	private String formOfAddress;
	private String generation;
	private String givenNames;
	private String preferredGivenName;
	private String middleNames;
	private String familyNamePrefix;
	private String familyNames;
	private String familyGeneration;
	private String qualifications;
	
	public String getFormattedName() {
		return formattedName;
	}
	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getAristocraticTitle() {
		return aristocraticTitle;
	}
	public void setAristocraticTitle(String aristocraticTitle) {
		this.aristocraticTitle = aristocraticTitle;
	}
	public String getFormOfAddress() {
		return formOfAddress;
	}
	public void setFormOfAddress(String formOfAddress) {
		this.formOfAddress = formOfAddress;
	}
	public String getGeneration() {
		return generation;
	}
	public void setGeneration(String generation) {
		this.generation = generation;
	}
	public String getGivenNames() {
		return givenNames;
	}
	public void setGivenNames(String givenNames) {
		this.givenNames = givenNames;
	}
	public String getPreferredGivenName() {
		return preferredGivenName;
	}
	public void setPreferredGivenName(String preferredGivenName) {
		this.preferredGivenName = preferredGivenName;
	}
	public String getMiddleNames() {
		return middleNames;
	}
	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}
	public String getFamilyNamePrefix() {
		return familyNamePrefix;
	}
	public void setFamilyNamePrefix(String familyNamePrefix) {
		this.familyNamePrefix = familyNamePrefix;
	}
	public String getFamilyNames() {
		return familyNames;
	}
	public void setFamilyNames(String familyNames) {
		this.familyNames = familyNames;
	}
	public String getFamilyGeneration() {
		return familyGeneration;
	}
	public void setFamilyGeneration(String familyGeneration) {
		this.familyGeneration = familyGeneration;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

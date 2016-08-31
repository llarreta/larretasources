package co.com.directv.sdii.model.pojo;

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable,Auditable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -961675529619816827L;
	private Long id;
	private Position position;
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Rol rol;
	private Dealer dealer;
	private String login;
	private String email;
	@BusinessRequired
	private String name;
	@BusinessRequired
	private String idNumber;
	private String isActive;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Country country;
	
	@BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private SdiiTimeZone sdiiTimeZone;
	

	// Constructors

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	/** default constructor */
	public User() {
	}
	
	public User(Long id){
		this.id = id;
	}

	/** minimal constructor */
	public User(Rol rol, String login, String name, String idNumber,
			String isActive, Country country) {
		this.rol = rol;
		this.login = login;
		this.name = name;
		this.idNumber = idNumber;
		this.isActive = isActive;
		this.country = country;
	}

	/** full constructor */
	public User(Position position, Rol rol, Dealer dealer, String login,
			String email, String name, String idNumber, String isActive, Country country, SdiiTimeZone sdiiTimeZone) {
		this.position = position;
		this.rol = rol;
		this.dealer = dealer;
		this.login = login;
		this.email = email;
		this.name = name;
		this.idNumber = idNumber;
		this.isActive = isActive;
		this.country = country;
		this.sdiiTimeZone = sdiiTimeZone;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public SdiiTimeZone getSdiiTimeZone() {
		return sdiiTimeZone;
	}

	public void setSdiiTimeZone(SdiiTimeZone sdiiTimeZone) {
		this.sdiiTimeZone = sdiiTimeZone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
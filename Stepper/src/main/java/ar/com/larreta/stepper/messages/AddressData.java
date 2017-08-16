package ar.com.larreta.stepper.messages;

public class AddressData extends JSONable {

	private Long 		id;
	private String 		street;
	private Integer 	number;
	private Integer 	floor;
	private String 		department;
	private Long 		country;
	private Long 		state;
	private Long		location;
	private String 		postalCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Long getCountry() {
		return country;
	}
	public void setCountry(Long country) {
		this.country = country;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Long getLocation() {
		return location;
	}
	public void setLocation(Long location) {
		this.location = location;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}

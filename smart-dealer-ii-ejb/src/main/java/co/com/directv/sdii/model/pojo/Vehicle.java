package co.com.directv.sdii.model.pojo;

// default package

import co.com.directv.sdii.audit.Auditable;
import co.com.directv.sdii.rules.BusinessRequired;



/**
 * Vehicle entity. @author MyEclipse Persistence Tools
 */

public class Vehicle implements java.io.Serializable,Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3160216410240135969L;

	// Fields
	private Long id;
        
        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private City city;

        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private Dealer dealer;

        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private VehicleType vehicleType;

        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private MembershipType membershipType;

        @BusinessRequired(isComplexType=true, fieldNameRequired="id")
	private VehicleStatus vehicleStatus;

        @BusinessRequired
	private String plate;

        @BusinessRequired
	private String color;

        @BusinessRequired
	private String model;

        @BusinessRequired
	private String brand;

        @BusinessRequired
	private Double loadCapacity;

        @BusinessRequired
	private Long peopleCapacity;

	// Constructors

	/** default constructor */
	public Vehicle() {
	}

	/** minimal constructor */
	public Vehicle(City city, Dealer dealer, VehicleType vehicleType,
			MembershipType membershipType, VehicleStatus vehicleStatus,
			String plate, String color, String model, String brand) {
		this.city = city;
		this.dealer = dealer;
		this.vehicleType = vehicleType;
		this.membershipType = membershipType;
		this.vehicleStatus = vehicleStatus;
		this.plate = plate;
		this.color = color;
		this.model = model;
		this.brand = brand;
	}

        public Vehicle(Long id, String plate, String color, String model, String brand) {
            this.id = id;
            this.plate = plate;
            this.color = color;
            this.model = model;
            this.brand = brand;
	}

	/** full constructor */
	public Vehicle(City city, Dealer dealer, VehicleType vehicleType,
			MembershipType membershipType, VehicleStatus vehicleStatus,
			String plate, String color, String model, String brand,
			Double loadCapacity, Long peopleCapacity) {
		this.city = city;
		this.dealer = dealer;
		this.vehicleType = vehicleType;
		this.membershipType = membershipType;
		this.vehicleStatus = vehicleStatus;
		this.plate = plate;
		this.color = color;
		this.model = model;
		this.brand = brand;
		this.loadCapacity = loadCapacity;
		this.peopleCapacity = peopleCapacity;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public VehicleType getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public MembershipType getMembershipType() {
		return this.membershipType;
	}

	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}

	public VehicleStatus getVehicleStatus() {
		return this.vehicleStatus;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public String getPlate() {
		return this.plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getLoadCapacity() {
		return this.loadCapacity;
	}

	public void setLoadCapacity(Double loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public Long getPeopleCapacity() {
		return this.peopleCapacity;
	}

	public void setPeopleCapacity(Long peopleCapacity) {
		this.peopleCapacity = peopleCapacity;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", plate=" + plate + "]";
	}

}
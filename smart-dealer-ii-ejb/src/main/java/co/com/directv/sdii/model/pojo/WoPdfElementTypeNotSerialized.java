package co.com.directv.sdii.model.pojo;

import java.io.Serializable;

public class WoPdfElementTypeNotSerialized implements Serializable {

	//Fields
	private static final long serialVersionUID = 2671784132653123894L;
	private Long id;
	private ElementType elementType;
	private Integer position;
	private Country country;
	
	//Constructors
	/**
	 * default constructor
	 */
	public WoPdfElementTypeNotSerialized(){
	}
	
	public WoPdfElementTypeNotSerialized(Long id){
		super();
		this.id = id;
	}

	
	//Property access
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public ElementType getElementType() {
		return elementType;
	}
	
	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}
	
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
}

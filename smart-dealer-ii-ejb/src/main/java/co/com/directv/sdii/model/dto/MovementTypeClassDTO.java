package co.com.directv.sdii.model.dto;

import java.io.Serializable;

public class MovementTypeClassDTO implements Serializable {

	
	private static final long serialVersionUID = 867939556901529248L;
	
	private String movClassCode;
	private String movClassName;
	
	/**
	 * Constructor: <Descripcion>
	 * @author
	 */
	public MovementTypeClassDTO() {
		super();
	}

	/**
	 * Constructor: <Descripcion>
	 * @param movClassCode
	 * @param movClassName
	 * @author
	 */
	public MovementTypeClassDTO(String movClassCode, String movClassName) {
		super();
		this.movClassCode = movClassCode;
		this.movClassName = movClassName;
	}
	
	/**
	 * @return the movClassCode
	 */
	public String getMovClassCode() {
		return movClassCode;
	}
	/**
	 * @param movClassCode the movClassCode to set
	 */
	public void setMovClassCode(String movClassCode) {
		this.movClassCode = movClassCode;
	}
	/**
	 * @return the movClassName
	 */
	public String getMovClassName() {
		return movClassName;
	}
	/**
	 * @param movClassName the movClassName to set
	 */
	public void setMovClassName(String movClassName) {
		this.movClassName = movClassName;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

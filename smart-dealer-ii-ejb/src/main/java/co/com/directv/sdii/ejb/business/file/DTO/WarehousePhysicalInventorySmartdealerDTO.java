package co.com.directv.sdii.ejb.business.file.DTO;

public class WarehousePhysicalInventorySmartdealerDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4288543497844193963L;
	
	private String campo1;
	private String campo2;
	private String campo3;
	private String campo4;
	private String campo5;
	private String campo6;
	private String campo7;
	private String campo8;
	private String elementType;
	private String typeInventary;
	
	/**
	 * Constructor: vacio
	 * @author
	 */
	public WarehousePhysicalInventorySmartdealerDTO() {
		super();
	}

	/**
	 * Constructor: <Descripcion>
	 * @param campo1
	 * @param campo2
	 * @param campo3
	 * @param campo4
	 * @param campo5
	 * @param campo6
	 * @author
	 */
	public WarehousePhysicalInventorySmartdealerDTO(String typeInventary,String elementType,String campo1,
			String campo2, String campo3, String campo4, String campo5,
			String campo6) {
		super();
		this.typeInventary=typeInventary;
		this.elementType = elementType;
		this.campo1 = campo1;
		this.campo2 = campo2;
		this.campo3 = campo3;
		this.campo4 = campo4;
		this.campo5 = campo5;
		this.campo6 = campo6;
		this.campo7 = "";
		this.campo8 = "";
	}

	/**
	 * Constructor: <Descripcion>
	 * @param campo1
	 * @param campo2
	 * @param campo3
	 * @param campo4
	 * @param campo5
	 * @param campo6
	 * @param campo7
	 * @param campo8
	 * @author
	 */
	public WarehousePhysicalInventorySmartdealerDTO(String typeInventary,String elementType,String campo1,
			String campo2, String campo3, String campo4, String campo5,
			String campo6, String campo7, String campo8) {
		super();
		this.typeInventary=typeInventary;
		this.elementType = elementType;
		this.campo1 = campo1;
		this.campo2 = campo2;
		this.campo3 = campo3;
		this.campo4 = campo4;
		this.campo5 = campo5;
		this.campo6 = campo6;
		this.campo7 = campo7;
		this.campo8 = campo8;
	}
	
	/**
	 * @return the campo1
	 */
	public String getCampo1() {
		return campo1;
	}
	/**
	 * @param campo1 the campo1 to set
	 */
	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}
	/**
	 * @return the campo2
	 */
	public String getCampo2() {
		return campo2;
	}
	/**
	 * @param campo2 the campo2 to set
	 */
	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}
	/**
	 * @return the campo3
	 */
	public String getCampo3() {
		return campo3;
	}
	/**
	 * @param campo3 the campo3 to set
	 */
	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}
	/**
	 * @return the campo4
	 */
	public String getCampo4() {
		return campo4;
	}
	/**
	 * @param campo4 the campo4 to set
	 */
	public void setCampo4(String campo4) {
		this.campo4 = campo4;
	}
	/**
	 * @return the campo5
	 */
	public String getCampo5() {
		return campo5;
	}
	/**
	 * @param campo5 the campo5 to set
	 */
	public void setCampo5(String campo5) {
		this.campo5 = campo5;
	}
	/**
	 * @return the campo6
	 */
	public String getCampo6() {
		return campo6;
	}
	/**
	 * @param campo6 the campo6 to set
	 */
	public void setCampo6(String campo6) {
		this.campo6 = campo6;
	}
	/**
	 * @return the campo7
	 */
	public String getCampo7() {
		return campo7;
	}
	/**
	 * @param campo7 the campo7 to set
	 */
	public void setCampo7(String campo7) {
		this.campo7 = campo7;
	}
	/**
	 * @return the campo8
	 */
	public String getCampo8() {
		return campo8;
	}
	/**
	 * @param campo8 the campo8 to set
	 */
	public void setCampo8(String campo8) {
		this.campo8 = campo8;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the elementType
	 */
	public String getElementType() {
		return elementType;
	}

	/**
	 * @param elementType the elementType to set
	 */
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	/**
	 * @return the typeInventary
	 */
	public String getTypeInventary() {
		return typeInventary;
	}

	/**
	 * @param typeInventary the typeInventary to set
	 */
	public void setTypeInventary(String typeInventary) {
		this.typeInventary = typeInventary;
	}
	
	
	
}

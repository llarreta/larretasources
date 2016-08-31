package co.com.directv.sdii.model.dto;

/**
 * Representa un registro del archivo plano procesado de la plantilla de control de calidad
 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
 *
 */
public class QAItemDTO implements java.io.Serializable{

	private static final long serialVersionUID = 8113039551148808482L;
	
	private Integer elementType;
	private String elementSerial;
	private Integer elementStatus;
	private String elementTypeCode;
	
	/**
	 * Instancia un nuevo item de control de calidad
	 */
	public QAItemDTO() {
		elementType = -1;
		elementSerial= null;
		elementStatus=-1;
		elementTypeCode=null;
	}

	public Integer getElementType() {
		return elementType;
	}

	public void setElementType(Integer elementType) {
		this.elementType = elementType;
	}

	public String getElementSerial() {
		return elementSerial;
	}

	public void setElementSerial(String elementSerial) {
		this.elementSerial = elementSerial;
	}

	public Integer getElementStatus() {
		return elementStatus;
	}

	public void setElementStatus(Integer elementStatus) {
		this.elementStatus = elementStatus;
	}

	public String getElementTypeCode() {
		return elementTypeCode;
	}

	public void setElementTypeCode(String elementTypeCode) {
		this.elementTypeCode = elementTypeCode;
	}
	
	
	
	
}

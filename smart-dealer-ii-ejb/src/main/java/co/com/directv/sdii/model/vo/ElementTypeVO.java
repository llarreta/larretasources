/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.ElementType;

/**
 * Objeto que encapsula la información de un ElementType
 * 
 * Fecha de Creación: 12/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ElementType    
 */
public class ElementTypeVO extends ElementType implements Serializable {

	public ElementTypeVO() {
		super();
	}
	
	public ElementTypeVO(String code, Long id, String name) {
		super();
		setTypeElementName(name);
		setTypeElementCode(code);
		setId(id);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1884662695741239296L;
	
	private String measureUnitName;
	private String elementClassName;
	private String elementModelName;
	
	private String nameCodeToPrint;

	public String getNameCodeToPrint() {
		return nameCodeToPrint;
	}

	public void setNameCodeToPrint(String nameCodeToPrint) {
		this.nameCodeToPrint = nameCodeToPrint;
	}

	public String getMeasureUnitName() {
		return measureUnitName;
	}

	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}

	public String getElementClassName() {
		return elementClassName;
	}

	public void setElementClassName(String elementClassName) {
		this.elementClassName = elementClassName;
	}

	public String getElementModelName() {
		return elementModelName;
	}

	public void setElementModelName(String elementModelName) {
		this.elementModelName = elementModelName;
	}
	
	
	
	

}

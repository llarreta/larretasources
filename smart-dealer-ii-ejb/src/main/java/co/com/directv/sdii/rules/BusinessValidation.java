/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.rules;

/**
 *
 * Clase que encapsula la definición de los atributos de una anoración
 * <code>co.com.directv.sdii.rules.BusinessRequired</code> para su posterior
 * procesamiento desde la clase <code>co.com.directv.sdii.rules.BusinessRuleValidationManager</code>
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.rules.BusinessRequired
 * @see co.com.directv.sdii.rules.BusinessRuleValidationManager
 *
 */
public class BusinessValidation {

	/**
	 * Nombre de la propiedad que se debe validar
	 */
    private String fieldName;
    
    /**
     * Determina si la propiedad que se debe validad es compleja es decir 
     * que se debe invocar un método sobre la propiedad para obtener el valor
     * y validar si la propiedad es nula.
     */
    private boolean isComplexType;
    
    /**
     * Nombre de la propiedad dentro del objeto completo
     */
    private String complexTypeFieldName;

    public String getComplexTypeFieldName() {
        return complexTypeFieldName;
    }

    public void setComplexTypeFieldName(String complexTypeFieldName) {
        this.complexTypeFieldName = complexTypeFieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isIsComplexType() {
        return isComplexType;
    }

    public void setIsComplexType(boolean isComplexType) {
        this.isComplexType = isComplexType;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.rules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Anotación que debe ser usada al nivel de campos en clases POJO o de persistencia
 * que ayuda al desarrollador a definir los campos que son obligatorios.
 *
 * El modo de uso es el siguiente:<br/><br/>
 *
 * 1. Si el campo es de tipo primitivo, Wrapper de primitivo o String:<br/><br/>
 *
 * @BusinessRequired<br/>
 * private TipoCampo nombreCampo<br/><br/>
 *
 * 2. Si el campo es de tipo de otra entidad persistente que es requerido por lo menos el identificador:<br/><br/>
 *
 * @BusinessRequired(isComplexType=true, fieldNameRaquired="nombreAtributoId")<br/>
 * private TipoCampo nombreCampo<br/><br/>
 *
 * 
 * Fecha de Creación: Mar 25, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.rules.BusinessRuleValidationManager
 * @see co.com.directv.sdii.rules.BusinessValidation
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BusinessRequired {

    boolean isComplexType() default false;
    String fieldNameRequired() default "";
    
}

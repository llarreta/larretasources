package co.com.directv.sdii.rules;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;


/**
 *
 * Artefacto que implementa el patrón singleton y que por introspección
 * obtiene las anotaciones sobre campos marcados como obligatorios con
 * la anoración <code>co.com.directv.sdii.rules.BusinessRequired</code>
 * Luego a partir de un objeto obtiene los valores de dichos campos
 * y verifica que hayan sido asignados
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.rules.BusinessRequired
 * @see co.com.directv.sdii.rules.BusinessValidation
 *
 */
public final class BusinessRuleValidationManager {

	/**
	 * Mapa cuya llave es el nombre de la clase POJO que será validada y cuyo valores serán
	 * listas con la información de validación para cada uno de sus atributos 
	 */
    private static Map<String, List<BusinessValidation>> validatorsCache = new HashMap<String, List<BusinessValidation>>();

    /**
     *instancia de la clase que ayuda en la implemenatación del patrón singleton 
     */
    private static BusinessRuleValidationManager mySelf = null;

    /**
     * Log que será usado para escribir mensajes del sistema
     */
    private final static Logger log = UtilsBusiness.getLog4J(BusinessRuleValidationManager.class);

    /**
     * Constructor de la clase, se declara privado para implementar el patrón singleton
     */
    private BusinessRuleValidationManager() {
    }

    /**
     * Método que implementa el patrón singleton
     * @return instancia única de la clase
     */
    public static BusinessRuleValidationManager getInstance(){
        if(mySelf == null){
            mySelf = new BusinessRuleValidationManager();
        }
        return mySelf;
    }

    /**
     * Determina si un objeto que es instancia de una clase VO que hereda de un POJO, tiene valores
     * asignados a cada uno de los atributos definidos como obligatorios
     * @param obj2Validate
     * @return verdadero en caso que el objeto tenga asignadas todas las propiedades
     * obligatorias, falso en otro caso.
     */
    public boolean isValid(Object obj2Validate){
        boolean isValid = true, isValidAux;

        List<BusinessValidation> businessValidations = getBusinessValidation(obj2Validate);

        try {
            for (BusinessValidation businessValidation : businessValidations) {
                Method fieldGetter = findGetterInClass(obj2Validate.getClass(), businessValidation.getFieldName());
                Object value = fieldGetter.invoke(obj2Validate, new Object[]{});
                isValidAux = isValueNull(value, businessValidation);
                if(!isValidAux){
                    log.error("::::--Validation error:---:::: field " + businessValidation.getFieldName() + " is Null");
                    isValid = false;
                }
            }
         } catch (IntrospectionException ex) {
                log.error("Error de introspección", ex);
         } catch (IllegalArgumentException ex) {
                log.error("Error de argumento inválido",ex);
         } catch (IllegalAccessException ex) {
                log.error("Error de Acceso inválido", ex);
         } catch (InvocationTargetException ex) {
                log.error("Error de argumento inválido", ex);
         }
        return isValid;
    }

    /**
     * Obtiene una lista con los validadores que aplican a un objeto determinado
     * dado el nombre de su clase.
     * @param obj2Validate objeto del cual se obtendrá el nombre de la clase
     * para determinar los validadores que le aplican
     * @return lista con los validadores que aplican al objeto
     */
    private List<BusinessValidation> getBusinessValidation(Object obj2Validate) {
        String className = obj2Validate.getClass().getName();
        List<BusinessValidation> businessValidations = validatorsCache.get(className);

        if(businessValidations == null || businessValidations.isEmpty()){
            Class superClass = obj2Validate.getClass().getSuperclass();
            businessValidations = findBusinessValidationsInClass(superClass);
            validatorsCache.put(className, businessValidations);
        }
        return businessValidations;


    }

    /**
     * Encuentra una lista de validaciones en una clase determinada
     * @param anObjectClass clase en donde se analizarán las anotaciones para determinar
     * las validaciones que aplican sobre las propiedades de la clase
     * @return Lista con objetos que encapsulan la información de las validaciones
     */
    private List<BusinessValidation> findBusinessValidationsInClass(Class anObjectClass){
        List<BusinessValidation> validations = new ArrayList<BusinessValidation>();

        Field[] fields = anObjectClass.getDeclaredFields();

        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation annotation : fieldAnnotations) {
                if(annotation instanceof BusinessRequired){
                    BusinessValidation validation = new BusinessValidation();
                    BusinessRequired bussAnnotation = (BusinessRequired)annotation;
                    validation.setFieldName(field.getName());
                    validation.setComplexTypeFieldName(bussAnnotation.fieldNameRequired());
                    validation.setIsComplexType(bussAnnotation.isComplexType());

                    validations.add(validation);
                }
            }
        }
        return validations;
    }

    /**
     * Determina si un valor es nulo
     * @param value objeto que representa el valor a ser validado
     * @param businessValidation objeto con la información
     * @return verdadero en caso que el valor no sea nulo, falso en caso que sea nulo
     * @throws IntrospectionException en caso de error al tratar de obtener el método accesor
     * para una propiedad del objeto
     * @throws IllegalAccessException en caso de error al tratar de acceder al valor de la propiedad del objeto
     * @throws IllegalArgumentException en caso de error
     * @throws InvocationTargetException en caso de error
     */
    private boolean isValueNull(Object value, BusinessValidation businessValidation) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        if(value == null){
           return false;
        }

        if(value instanceof Number){
           return !(((Number)value).doubleValue() < 0D);
        }else if(value instanceof String){
            return !value.toString().trim().equalsIgnoreCase("");
        }else if(businessValidation != null && businessValidation.isIsComplexType()){
            Class complexTypeClass = value.getClass();
            String complexFieldName = businessValidation.getComplexTypeFieldName();
            Method complexGetter = findGetterInClass(complexTypeClass, complexFieldName);
            Object complexValue = complexGetter.invoke(value, new Object[]{});
            return isValueNull(complexValue, null);
        }
        return true;
    }

    /**
     * Operación que encuentra el método accesor para una propiedad específica dentro de una definición de clas
     * @param anObjectClass definición de la clase a la que se le realizará la intronspección para obtener el valor.
     * @param propertyName nombre de la propiedad a la que se le quiere obtener el método accesor
     * @return método accesor de la propiedad
     * @throws IntrospectionException en caso de no encontrar le método accesor para esa propiedad
     */
    private Method findGetterInClass(Class anObjectClass, String propertyName) throws IntrospectionException{
        PropertyDescriptor propDescriptors[] = Introspector.getBeanInfo(anObjectClass).getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propDescriptors) {
            if(propertyDescriptor.getName().equalsIgnoreCase(propertyName)){
                return propertyDescriptor.getReadMethod();
            }
        }
        return null;
    }

}

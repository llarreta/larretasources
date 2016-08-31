package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.AdjustmentElementsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.AdjustmentElementsVO;
import co.com.directv.sdii.ws.business.stock.IAdjustmentElementsWS;

/**
 * Servicio web que expone las operaciones relacionadas con AdjustmentElements
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.AdjustmentElementsFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="AdjustmentElementsWSService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IAdjustmentElementsWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="AdjustmentElementsWSPort")
@Stateless()
public class AdjustmentElementsWS implements IAdjustmentElementsWS {

	@EJB
    private AdjustmentElementsFacadeBeanLocal ejbRef;
	
	
	
	/**
	 * Metodo: actualiza la información de un AdjustmentElements
	 * @param obj objeto que encapsula la información del AdjustmentElements a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo AdjustmentElements
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	public void updateAdjustmentElements(AdjustmentElementsVO objAdjustmentElements) throws BusinessException{
		ejbRef.updateAdjustmentElements(objAdjustmentElements);
	}
	
	/**
	 * Metodo: borra un AdjustmentElements de la persistencia
	 * @param obj objeto que encapsula la información del AdjustmentElements, solo se requiere la propiedad id
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	public void deleteAdjustmentElements(AdjustmentElementsVO objAdjustmentElements) throws BusinessException{
		ejbRef.deleteAdjustmentElements(objAdjustmentElements);
	}
	
	/**
	 * Metodo: Obtiene un AdjustmentElements dado el identificador del mismo
	 * @param id identificador del AdjustmentElements a ser consultado
	 * @return AdjustmentElements con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el AdjustmentElements con el id, ver códigos de excepción.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	public AdjustmentElementsVO getAdjustmentElementsByID(Long id) throws BusinessException{
		return ejbRef.getAdjustmentElementsByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los AdjustmentElements almacenados en la persistencia
	 * @return lista con los AdjustmentElements existentes, una lista vacia en caso que no exista ninguno.
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jjimenezh
	 */
	public List<AdjustmentElementsVO> getAllAdjustmentElementss() throws BusinessException{
		return ejbRef.getAllAdjustmentElementss();
	}
	
}

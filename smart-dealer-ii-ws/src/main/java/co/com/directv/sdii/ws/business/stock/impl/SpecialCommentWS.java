package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SpecialCommentResponse;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.ws.business.stock.ISpecialCommentWS;

/**
 * Servicio web que expone las operaciones relacionadas con SpecialComment
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.SpecialCommentFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="SpecialCommentService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.ISpecialCommentWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="SpecialCommentPort")
@Stateless()
public class SpecialCommentWS implements ISpecialCommentWS {

	@EJB
    private SpecialCommentFacadeBeanLocal ejbRef;
	
	/**
	 * Metodo: persiste la información de un SpecialComment
	 * @param obj objeto que encapsula la información necesaria para construir el SpecialComment,
	 * no debe venir asignada la propiedad id, de lo contrario se generará un error
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
	public void createSpecialComment(SpecialCommentVO objSpecialComment) throws BusinessException{
		ejbRef.createSpecialComment(objSpecialComment);
	}
	
	/**
	 * Metodo: actualiza la información de un SpecialComment
	 * @param obj objeto que encapsula la información del SpecialComment a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo SpecialComment
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
	public void updateSpecialComment(SpecialCommentVO objSpecialComment) throws BusinessException{
		ejbRef.updateSpecialComment(objSpecialComment);
	}
	
	/**
	 * Metodo: borra un SpecialComment de la persistencia
	 * @param obj objeto que encapsula la información del SpecialComment, solo se requiere la propiedad id
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
	public void deleteSpecialComment(SpecialCommentVO objSpecialComment) throws BusinessException{
		ejbRef.deleteSpecialComment(objSpecialComment);
	}
	
	/**
	 * Metodo: Obtiene un SpecialComment dado el identificador del mismo
	 * @param id identificador del SpecialComment a ser consultado
	 * @return SpecialComment con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el SpecialComment con el id, ver códigos de excepción.
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
	public SpecialCommentVO getSpecialCommentByID(Long id) throws BusinessException{
		return ejbRef.getSpecialCommentByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los SpecialComment almacenados en la persistencia
	 * @return lista con los SpecialComment existentes, una lista vacia en caso que no exista ninguno.
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
	public List<SpecialCommentVO> getAllSpecialComments() throws BusinessException{
		return ejbRef.getAllSpecialComments();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.ISpecialCommentWS#createSpecialComments(java.lang.Long, java.util.List)
	 */
	@Override
	public void createSpecialComments(Long referenceId,
			List<SpecialCommentVO> specialCommentsList)
			throws BusinessException {
		ejbRef.createSpecialComments(referenceId,specialCommentsList);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.ISpecialCommentWS#getSpecialCommentsByReferenceId(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public SpecialCommentResponse getSpecialCommentsByReferenceId(
			Long referenceId, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRef.getSpecialCommentsByReferenceId(referenceId,requestCollInfo);
	}
	
}

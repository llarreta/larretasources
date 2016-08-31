package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.stock.AdjustmentFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.TransferReasonFacadeBeanLocal;
import co.com.directv.sdii.model.dto.AdjustmenElementsRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.collection.AdjustmentElementCollDTO;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.TransferReasonResponse;
import co.com.directv.sdii.model.vo.AdjustmentStatusVO;
import co.com.directv.sdii.model.vo.AdjustmentTypeVO;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.model.vo.SerializedVO;
import co.com.directv.sdii.model.vo.TransferReasonVO;
import co.com.directv.sdii.reports.dto.AdjustmentElementDTO;
import co.com.directv.sdii.ws.business.stock.IAdjustmentWS;

/**
 * Servicio web que expone las operaciones relacionadas con Adjustment
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.AdjustmentFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="AdjustmentWSService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IAdjustmentWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="AdjustmentWSPort")
@Stateless()
public class AdjustmentWS implements IAdjustmentWS {

	@EJB
    private AdjustmentFacadeBeanLocal ejbRef;

	@EJB
    private TransferReasonFacadeBeanLocal ejbTransferReason;
	
	/**
	 * Metodo: persiste la información de un Adjustment
	 * @param obj objeto que encapsula la información necesaria para construir el Adjustment,
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
	public Long createAdjustment(AdjustmentVO objAdjustment, Long userId) throws BusinessException{
		return ejbRef.createAdjustment(objAdjustment, userId);
	}
	
	/**
	 * Metodo: actualiza la información de un Adjustment
	 * @param obj objeto que encapsula la información del Adjustment a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Adjustment
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
	public void updateAdjustment(AdjustmentVO objAdjustment,Long userId) throws BusinessException{
		ejbRef.updateAdjustment(objAdjustment,userId);
	}
	
	/**
	 * Metodo: borra un Adjustment de la persistencia
	 * @param obj objeto que encapsula la información del Adjustment, solo se requiere la propiedad id
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
	public void deleteAdjustment(AdjustmentVO objAdjustment) throws BusinessException{
		ejbRef.deleteAdjustment(objAdjustment);
	}
	
	/**
	 * Metodo: Obtiene un Adjustment dado el identificador del mismo
	 * @param id identificador del Adjustment a ser consultado
	 * @return Adjustment con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Adjustment con el id, ver códigos de excepción.
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
	public AdjustmentVO getAdjustmentByID(Long id) throws BusinessException{
		return ejbRef.getAdjustmentByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los Adjustment almacenados en la persistencia
	 * @return lista con los Adjustment existentes, una lista vacia en caso que no exista ninguno.
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
	public List<AdjustmentVO> getAllAdjustments() throws BusinessException{
		return ejbRef.getAllAdjustments();
	}
	
	/**
	 * Metodo: Obtiene todos los tipos de ajuste 
	 * @return lista con los Adjustment existentes, una lista vacia en caso que no exista ninguno.
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
	public List<AdjustmentTypeVO> getAllAdjustmentsTypes() throws BusinessException{
		return ejbRef.getAllAdjustmentsTypes();
	}

	@Override
	public AdjustmentVO createAdjustmentSerializedElement(List<AdjustmentElementDTO> listAdjustmentElementsVO, AdjustmentVO adjustmentVO, Long userId) throws BusinessException {
		return ejbRef.createAdjustmentSerializedElement(listAdjustmentElementsVO, adjustmentVO, userId);
		
	}
	
	//Metodos para causales de translados
	
	/**
	 * Metodo:  Persiste la información de un objeto TransferReasonVO
	 * @param obj objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void createTransferReason(TransferReasonVO obj) throws BusinessException, DAOServiceException, DAOSQLException{
		ejbTransferReason.createTransferReason(obj);
	}
	
	/**
	 * Metodo: actualiza la información de un TransferReasonVO
	 * @param obj objeto que encapsula la información de un TransferReasonVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateTransferReason(TransferReasonVO obj) throws BusinessException{
		ejbTransferReason.updateTransferReason(obj);
		
	}
	
	/**
	 * Metodo: Borra de la persistencia la información de un TransferReasonVO
	 * @param obj información del TransferReasonVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 * @throws DAOSQLException 
	 * @throws DAOServiceException 
	 */
	public void deleteTransferReason(TransferReasonVO obj) throws BusinessException, DAOServiceException, DAOSQLException{
		ejbTransferReason.deleteTransferReason(obj);
	}

	/**
	 * Metodo: Obtiene la información de todos los TransferReason almacenados en la persistencia
	 * @return Lista con los TransferReason existentes, una lista vacia en caso que no existan TransferReason en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de todos los TransferReason
	 * @author mrugeles
	 */
	public List<TransferReasonVO> getAllTransferReasons() throws BusinessException{
		return ejbTransferReason.getAllTransferReasons();
	}

	/**
	 * Método: Permite consultar todos los TransferReason en estado dado un ElementModel 
	 * @param elementModelId - Long Código del ElementModel
	 * @param TransferReasonStatus - String Estado
	 * @param requestCollInfo - RequestCollectionInfo información de la paginación
	 * @return TransferReasonResponse con los resultados de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason en estado por ElementModel
	 * @author gfandino
	 */
	public TransferReasonResponse getTransferReasonByFilter(String transferReasonName,RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbTransferReason.getTransferReasonByFilter(transferReasonName, requestCollInfo);
	}

	/**
	 * Metodo: Obtiene la información de un TransferReason por su identificador
	 * @param id identificador del TransferReason a ser consultado
	 * @return objeto con la información del TransferReason dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por ID
	 * @author mrugeles
	 */
	public TransferReasonVO getTransferReasonByID(Long id) throws BusinessException{
		return ejbTransferReason.getTransferReasonByID(id);
		
	}

	/**
	 * Metodo: Obtiene la información de un TransferReason dado su estado
	 * @param status - String estado del elemento
	 * @return List<TransferReason> correspondiente al estado; vacio en otro caso
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta de TransferReason por estado
	 * @author gfandino
	 */
	public List<TransferReasonVO> getTransferReasonsByIsActive(String status)throws BusinessException{
		return ejbTransferReason.getTransferReasonsByIsActive(status);
	}

	@Override
	public List<TransferReasonVO> getAdjustmentReasonbyAdjustmentType(
			AdjustmentTypeVO adjustmentType) throws BusinessException {
		return ejbTransferReason.getAdjustmentReasonbyAdjustmentType(adjustmentType);
	}

	@Override
	public AdjustmentVO createAdjustmentNotSerializedElement(
			List<AdjustmentElementDTO> listAdjustmentElementsVO,
			AdjustmentVO adjustmentVO, Long userID) throws BusinessException {
		return ejbRef.createAdjustmentNotSerializedElement(listAdjustmentElementsVO, adjustmentVO, userID);
	}

	@Override
	public SerializedVO findSerializedElement(String serialCode, Long userId) throws BusinessException{
		
		return ejbRef.findSerializedElement(serialCode, userId);

	}
	
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo) throws BusinessException{
		
		return ejbRef.searchAdjustmentsBySearchParameters(params, requestCollInfo);

	}
	
	@Override
	public AdjustmentElementCollDTO getCheckAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getCheckAdjustmentElementsForAuthorization(request, requestCollInfo);
	}
	
	@Override
	public AdjustmentElementCollDTO getAdjustmentElementsForAuthorization(
			AdjustmenElementsRequestDTO request, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getAdjustmentElementsForAuthorization(request, requestCollInfo);
	}
	
	@Override
	public void createAdjustmentElementsAuthorization(
			AdjustmenElementsRequestDTO request) throws BusinessException {
		ejbRef.createAdjustmentElementsAuthorization(request);
	}
	
	@Override
	public void approvalAllElementsOfAdjustment(AdjustmenElementsRequestDTO request) throws BusinessException {
		ejbRef.approvalAllElementsOfAdjustment(request);
	}
	

	/**
	 * Metodo encargado de consultar los estados para un ajuste
	 * @throws BusinessException
	 */
	public List<AdjustmentStatusVO> getAllAdjustmentStatus() throws BusinessException {
		return ejbRef.getAllAdjustmentStatus();
	}
	
}

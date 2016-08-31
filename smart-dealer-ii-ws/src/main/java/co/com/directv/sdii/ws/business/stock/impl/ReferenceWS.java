package co.com.directv.sdii.ws.business.stock.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.RefIncStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.RefIncTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.RefInconsistencyFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.RefQuantityControlItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceElementItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceModTypeFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceModificationFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReferenceStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ReportedElementFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.TransportCompanyFacadeBeanLocal;
import co.com.directv.sdii.model.dto.FilterReferencesToPrintDTO;
import co.com.directv.sdii.model.dto.InfoToPrintReferencesDTO;
import co.com.directv.sdii.model.dto.ReferenceShipmentDTO;
import co.com.directv.sdii.model.dto.ReferencesFilterDTO;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.dto.collection.TransportCompanyDTO;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.NotSerializedElementInSelectReferenceToPrintPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RefInconsistencyResponse;
import co.com.directv.sdii.model.pojo.collection.RefQuantityControlItemsResponse;
import co.com.directv.sdii.model.pojo.collection.ReferenceResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedElementInReferencePaginationResponse;
import co.com.directv.sdii.model.vo.DeliveryVO;
import co.com.directv.sdii.model.vo.RefConfirmationVO;
import co.com.directv.sdii.model.vo.RefIncStatusVO;
import co.com.directv.sdii.model.vo.RefIncTypeVO;
import co.com.directv.sdii.model.vo.RefInconsistencyVO;
import co.com.directv.sdii.model.vo.RefQuantityControlItemVO;
import co.com.directv.sdii.model.vo.RefRecieveDataVO;
import co.com.directv.sdii.model.vo.ReferenceElementItemVO;
import co.com.directv.sdii.model.vo.ReferenceModTypeVO;
import co.com.directv.sdii.model.vo.ReferenceModificationVO;
import co.com.directv.sdii.model.vo.ReferenceStatusVO;
import co.com.directv.sdii.model.vo.ReferenceVO;
import co.com.directv.sdii.model.vo.ReportedElementVO;
import co.com.directv.sdii.model.vo.SpecialCommentVO;
import co.com.directv.sdii.model.vo.TransportCompanyVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.model.vo.WarehouseVO;
import co.com.directv.sdii.ws.business.stock.IReferenceWS;
import co.com.directv.sdii.ws.model.dto.ReportedElementForValidationDTO;

/**
 * Servicio web que expone las operaciones relacionadas con Reference
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ReferenceFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="ReferenceService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IReferenceWS",
		targetNamespace="http://reference.stock.business.ws.sdii.directv.com.co/",
		portName="ReferencePort")
		@Stateless()
public class ReferenceWS implements IReferenceWS {

	@EJB
	private ReferenceFacadeBeanLocal ejbRef;	
	@EJB
	private ReferenceModificationFacadeBeanLocal ejbRefMod;
	@EJB
	private ReferenceModTypeFacadeBeanLocal ejbRefModType;
	@EJB
	private ReferenceStatusFacadeBeanLocal ejbRefStatus;
	@EJB
	private RefInconsistencyFacadeBeanLocal ejbRefInc;
	@EJB
	private RefIncStatusFacadeBeanLocal ejbRefIncStatus;
	@EJB
	private RefIncTypeFacadeBeanLocal ejbRefIncType;
	@EJB
	private TransportCompanyFacadeBeanLocal ejbRefTrans;
	@EJB
	private RefQuantityControlItemFacadeBeanLocal ejbRefQtyCtrl;
	@EJB
	private ReportedElementFacadeBeanLocal reportedElementFacadeBean;
	@EJB
	private ReferenceElementItemFacadeBeanLocal referenceElementItemFacade;
	
	/**
	 * Metodo: persiste la información de un Reference
	 * @param obj objeto que encapsula la información necesaria para construir el Reference,
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
	public void createReference(ReferenceVO objReference) throws BusinessException{
		ejbRef.createReference(objReference);
	}

	/**
	 * Metodo: actualiza la información de un Reference
	 * @param obj objeto que encapsula la información del Reference a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo Reference
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
	public void updateReference(ReferenceVO objReference) throws BusinessException{
		ejbRef.updateReference(objReference);
	}

	/**
	 * Metodo: borra un Reference de la persistencia
	 * @param obj objeto que encapsula la información del Reference, solo se requiere la propiedad id
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
	public void deleteReference(ReferenceVO objReference) throws BusinessException{
		ejbRef.deleteReference(objReference);
	}
	
	@Override
	public Long deleteReferenceLogic(Long referenceID, Long userDelete) throws BusinessException {
		return ejbRef.deleteReferenceLogic(referenceID, userDelete);
	}

	/**
	 * Metodo: Obtiene un Reference dado el identificador del mismo
	 * @param id identificador del Reference a ser consultado
	 * @return Reference con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el Reference con el id, ver códigos de excepción.
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
	public ReferenceVO getReferenceByID(Long id) throws BusinessException{
		return ejbRef.getReferenceByID(id);
	}

	/**
	 * Metodo: Obtiene todos los Reference almacenados en la persistencia
	 * @return lista con los Reference existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceVO> getAllReferences() throws BusinessException{
		return ejbRef.getAllReferences();
	}

	/**
	 * Metodo: Obtiene todos los Reference almacenados en la persistencia
	 * @param country - Long identificador del país
	 * @return lista con los Reference existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceVO> getAllReferencesAndByCountry(Long country) throws BusinessException{
		return ejbRef.getAllReferencesAndByCountry(country);
	}

	/**
	 * Metodo: Obtiene la informacion de la Reference para imprimir
	 * @param referenceId id de la reference a imprimir
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
	 * @author cduarte
	 */
	public InfoToPrintReferencesDTO getInfoToPrintReferencesById(@WebParam(name="referenceId") Long referenceId)throws BusinessException{
		return ejbRef.getInfoToPrintReferencesById(referenceId);
	}

	
	/**
	 * Metodo: Obtiene todos los Reference almacenados en la persistencia
	 * @return lista con los Reference existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceVO> getReferencesByIdOrSourceWhOrTargetWh(Long referenceId, Long sourceWhId, Long targetWhId, Date referenceCreationDate, String recorderUserName) throws BusinessException{
		return ejbRef.getReferencesByIdOrSourceWhOrTargetWh(referenceId, sourceWhId, targetWhId, referenceCreationDate, recorderUserName);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#updateReferenceAndElements(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List, boolean)
	 */
	public void updateReferenceAndElements(ReferenceVO reference, List<ReferenceElementItemVO> referenceElements, boolean isFinished, Long userId)throws BusinessException{
		ejbRef.updateReferenceAndElements(reference, referenceElements, isFinished, userId);
	}

	/**
	 * Metodo: persiste la información de un ReferenceModification
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceModification,
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
	public void createReferenceModification(ReferenceModificationVO objReferenceModification) throws BusinessException{
		ejbRefMod.createReferenceModification(objReferenceModification);
	}

	/**
	 * Metodo: actualiza la información de un ReferenceModification
	 * @param obj objeto que encapsula la información del ReferenceModification a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceModification
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
	public void updateReferenceModification(ReferenceModificationVO objReferenceModification) throws BusinessException{
		ejbRefMod.updateReferenceModification(objReferenceModification);
	}

	/**
	 * Metodo: borra un ReferenceModification de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceModification, solo se requiere la propiedad id
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
	public void deleteReferenceModification(ReferenceModificationVO objReferenceModification) throws BusinessException{
		ejbRefMod.deleteReferenceModification(objReferenceModification);
	}

	/**
	 * Metodo: Obtiene un ReferenceModification dado el identificador del mismo
	 * @param id identificador del ReferenceModification a ser consultado
	 * @return ReferenceModification con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceModification con el id, ver códigos de excepción.
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
	public ReferenceModificationVO getReferenceModificationByID(Long id) throws BusinessException{
		return ejbRefMod.getReferenceModificationByID(id);
	}

	/**
	 * Metodo: Obtiene todos los ReferenceModification almacenados en la persistencia
	 * @return lista con los ReferenceModification existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceModificationVO> getAllReferenceModifications() throws BusinessException{
		return ejbRefMod.getAllReferenceModifications();
	}

	/**
	 * Metodo: persiste la información de un ReferenceModType
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceModType,
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
	public void createReferenceModType(ReferenceModTypeVO objReferenceModType) throws BusinessException{
		ejbRefModType.createReferenceModType(objReferenceModType);
	}

	/**
	 * Metodo: actualiza la información de un ReferenceModType
	 * @param obj objeto que encapsula la información del ReferenceModType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceModType
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
	public void updateReferenceModType(ReferenceModTypeVO objReferenceModType) throws BusinessException{
		ejbRefModType.updateReferenceModType(objReferenceModType);
	}

	/**
	 * Metodo: borra un ReferenceModType de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceModType, solo se requiere la propiedad id
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
	public void deleteReferenceModType(ReferenceModTypeVO objReferenceModType) throws BusinessException{
		ejbRefModType.deleteReferenceModType(objReferenceModType);
	}

	/**
	 * Metodo: Obtiene un ReferenceModType dado el identificador del mismo
	 * @param id identificador del ReferenceModType a ser consultado
	 * @return ReferenceModType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceModType con el id, ver códigos de excepción.
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
	public ReferenceModTypeVO getReferenceModTypeByID(Long id) throws BusinessException{
		return ejbRefModType.getReferenceModTypeByID(id);
	}

	/**
	 * Metodo: Obtiene todos los ReferenceModType almacenados en la persistencia
	 * @return lista con los ReferenceModType existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceModTypeVO> getAllReferenceModTypes() throws BusinessException{
		return ejbRefModType.getAllReferenceModTypes();
	}

	/**
	 * Metodo: persiste la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información necesaria para construir el ReferenceStatus,
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
	public void createReferenceStatus(ReferenceStatusVO objReferenceStatus) throws BusinessException{
		ejbRefStatus.createReferenceStatus(objReferenceStatus);
	}

	/**
	 * Metodo: actualiza la información de un ReferenceStatus
	 * @param obj objeto que encapsula la información del ReferenceStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ReferenceStatus
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
	public void updateReferenceStatus(ReferenceStatusVO objReferenceStatus) throws BusinessException{
		ejbRefStatus.updateReferenceStatus(objReferenceStatus);
	}

	/**
	 * Metodo: borra un ReferenceStatus de la persistencia
	 * @param obj objeto que encapsula la información del ReferenceStatus, solo se requiere la propiedad id
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
	public void deleteReferenceStatus(ReferenceStatusVO objReferenceStatus) throws BusinessException{
		ejbRefStatus.deleteReferenceStatus(objReferenceStatus);
	}

	/**
	 * Metodo: Obtiene un ReferenceStatus dado el identificador del mismo
	 * @param id identificador del ReferenceStatus a ser consultado
	 * @return ReferenceStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ReferenceStatus con el id, ver códigos de excepción.
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
	public ReferenceStatusVO getReferenceStatusByID(Long id) throws BusinessException{
		return ejbRefStatus.getReferenceStatusByID(id);
	}

	/**
	 * Metodo: Obtiene todos los ReferenceStatus almacenados en la persistencia
	 * @return lista con los ReferenceStatus existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ReferenceStatusVO> getAllReferenceStatuss() throws BusinessException{
		return ejbRefStatus.getAllReferenceStatuss();
	}

	/**
	 * Metodo: actualiza la información de un RefInconsistency
	 * @param obj objeto que encapsula la información del RefInconsistency a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefInconsistency
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
	public void updateRefInconsistency(RefInconsistencyVO objRefInconsistency) throws BusinessException{
		ejbRefInc.updateRefInconsistency(objRefInconsistency);
	}

	/**
	 * Metodo: borra un RefInconsistency de la persistencia
	 * @param obj objeto que encapsula la información del RefInconsistency, solo se requiere la propiedad id
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
	public void deleteRefInconsistency(RefInconsistencyVO objRefInconsistency) throws BusinessException{
		ejbRefInc.deleteRefInconsistency(objRefInconsistency);
	}

	/**
	 * Metodo: Obtiene un RefInconsistency dado el identificador del mismo
	 * @param id identificador del RefInconsistency a ser consultado
	 * @return RefInconsistency con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefInconsistency con el id, ver códigos de excepción.
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
	public RefInconsistencyVO getRefInconsistencyByID(Long id) throws BusinessException{
		return ejbRefInc.getRefInconsistencyByID(id);
	}

	/**
	 * Metodo: Obtiene todos los RefInconsistency almacenados en la persistencia
	 * @return lista con los RefInconsistency existentes, una lista vacia en caso que no exista ninguno.
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
	public List<RefInconsistencyVO> getAllRefInconsistencys() throws BusinessException{
		return ejbRefInc.getAllRefInconsistencys();
	}

	/**
	 * Metodo: persiste la información de un RefIncStatus
	 * @param obj objeto que encapsula la información necesaria para construir el RefIncStatus,
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
	public void createRefIncStatus(RefIncStatusVO objRefIncStatus) throws BusinessException{
		ejbRefIncStatus.createRefIncStatus(objRefIncStatus);
	}

	/**
	 * Metodo: actualiza la información de un RefIncStatus
	 * @param obj objeto que encapsula la información del RefIncStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefIncStatus
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
	public void updateRefIncStatus(RefIncStatusVO objRefIncStatus) throws BusinessException{
		ejbRefIncStatus.updateRefIncStatus(objRefIncStatus);
	}

	/**
	 * Metodo: borra un RefIncStatus de la persistencia
	 * @param obj objeto que encapsula la información del RefIncStatus, solo se requiere la propiedad id
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
	public void deleteRefIncStatus(RefIncStatusVO objRefIncStatus) throws BusinessException{
		ejbRefIncStatus.deleteRefIncStatus(objRefIncStatus);
	}

	/**
	 * Metodo: Obtiene un RefIncStatus dado el identificador del mismo
	 * @param id identificador del RefIncStatus a ser consultado
	 * @return RefIncStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefIncStatus con el id, ver códigos de excepción.
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
	public RefIncStatusVO getRefIncStatusByID(Long id) throws BusinessException{
		return ejbRefIncStatus.getRefIncStatusByID(id);
	}

	/**
	 * Metodo: Obtiene todos los RefIncStatus almacenados en la persistencia
	 * @return lista con los RefIncStatus existentes, una lista vacia en caso que no exista ninguno.
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
	public List<RefIncStatusVO> getAllRefIncStatuss() throws BusinessException{
		return ejbRefIncStatus.getAllRefIncStatuss();
	}

	/**
	 * Metodo: persiste la información de un RefIncType
	 * @param obj objeto que encapsula la información necesaria para construir el RefIncType,
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
	public void createRefIncType(RefIncTypeVO objRefIncType) throws BusinessException{
		ejbRefIncType.createRefIncType(objRefIncType);
	}

	/**
	 * Metodo: actualiza la información de un RefIncType
	 * @param obj objeto que encapsula la información del RefIncType a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo RefIncType
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
	public void updateRefIncType(RefIncTypeVO objRefIncType) throws BusinessException{
		ejbRefIncType.updateRefIncType(objRefIncType);
	}

	/**
	 * Metodo: borra un RefIncType de la persistencia
	 * @param obj objeto que encapsula la información del RefIncType, solo se requiere la propiedad id
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
	public void deleteRefIncType(RefIncTypeVO objRefIncType) throws BusinessException{
		ejbRefIncType.deleteRefIncType(objRefIncType);
	}

	/**
	 * Metodo: Obtiene un RefIncType dado el identificador del mismo
	 * @param id identificador del RefIncType a ser consultado
	 * @return RefIncType con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el RefIncType con el id, ver códigos de excepción.
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
	public RefIncTypeVO getRefIncTypeByID(Long id) throws BusinessException{
		return ejbRefIncType.getRefIncTypeByID(id);
	}

	/**
	 * Metodo: Obtiene todos los RefIncType almacenados en la persistencia
	 * @return lista con los RefIncType existentes, una lista vacia en caso que no exista ninguno.
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
	public List<RefIncTypeVO> getAllRefIncTypes() throws BusinessException{
		return ejbRefIncType.getAllRefIncTypes();
	}

	/**
	 * Metodo: persiste la información de un TransportCompany
	 * @param obj objeto que encapsula la información necesaria para construir el TransportCompany,
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
	public void createTransportCompany(TransportCompanyVO objTransportCompany) throws BusinessException{
		ejbRefTrans.createTransportCompany(objTransportCompany);
	}

	/**
	 * Metodo: actualiza la información de un TransportCompany
	 * @param obj objeto que encapsula la información del TransportCompany a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo TransportCompany
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
	public void updateTransportCompany(TransportCompanyVO objTransportCompany) throws BusinessException{
		ejbRefTrans.updateTransportCompany(objTransportCompany);
	}

	/**
	 * Metodo: borra un TransportCompany de la persistencia
	 * @param obj objeto que encapsula la información del TransportCompany, solo se requiere la propiedad id
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
	public void deleteTransportCompany(TransportCompanyVO objTransportCompany) throws BusinessException{
		ejbRefTrans.deleteTransportCompany(objTransportCompany);
	}

	/**
	 * Metodo: Obtiene un TransportCompany dado el identificador del mismo
	 * @param id identificador del TransportCompany a ser consultado
	 * @return TransportCompany con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el TransportCompany con el id, ver códigos de excepción.
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
	public TransportCompanyVO getTransportCompanyByID(Long id) throws BusinessException{
		return ejbRefTrans.getTransportCompanyByID(id);
	}

	/**
	 * Metodo: Obtiene todos los TransportCompany almacenados en la persistencia
	 * @return lista con los TransportCompany existentes, una lista vacia en caso que no exista ninguno.
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
	@Override
	public List<TransportCompanyVO> getTransportCompaniesByCountryId(Long countryId) throws BusinessException{
		return ejbRefTrans.getTransportCompaniesByCountryId(countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getActiveTransportCompanys()
	 */
	@Override
	public TransportCompanyDTO getActiveTransportCompaniesByCountryId(Long countryId, RequestCollectionInfoDTO requestCollInfo) throws BusinessException{
		return ejbRefTrans.getActiveTransportCompaniesByCountryId(countryId, requestCollInfo);
	}

	/**
	 * Metodo: Obtiene un TransportCompany dado el código de la misma
	 * @param code código de la TransportCompany a ser consultada
	 * @return TransportCompany con el código especificado, nulo en caso que no
	 * se encuentre compañía transportadora con ese código
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
	public TransportCompanyVO getTransportCompanyByCode(String code)
	throws BusinessException {
		return ejbRefTrans.getTransportCompanyByCode(code);
	}

	/**
	 * Metodo: Obtiene un TransportCompany dado el código de la misma
	 * @param code código de la TransportCompany a ser consultada
	 * @return TransportCompany con el código especificado, nulo en caso que no
	 * se encuentre compañía transportadora con ese código
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
	@Override
	public TransportCompanyVO getTransportCompanyByCodeAndCountryId(String code, Long countryId)
	throws BusinessException {
		return ejbRefTrans.getTransportCompanyByCodeAndCountryId(code, countryId);
	}

	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado dado el código o identificado de este
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @return  List<ReferenceVO> correspondiente al estado de remisión. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndByCountry(
			ReferenceStatusVO refStatus,Long country) throws BusinessException {
		return ejbRef.getReferencesByReferenceStatusAndByCountry(refStatus,country);
	}

	/**
	 * Metodo: Permite consultar las remisiones asociadas a un estado y unas bodegas de entrada y salida
	 * @param refStatus - ReferenceStatusVO Debe contener el ID o el código del estado
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente al estado de remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su estado y bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceStatusAndWhAndByCountry(
			ReferenceStatusVO refStatus, WarehouseVO whSource,
			WarehouseVO whTarget,Long country) throws BusinessException {
		return ejbRef.getReferencesByReferenceStatusAndWhAndByCountry(refStatus,whSource,whTarget,country);
	}

	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y salida
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceAndTargetWareHouseAndByCountry(
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return ejbRef.getReferencesBySourceAndTargetWareHouseAndByCountry(whSource,whTarget,country);
	}

	/**
	 * Metodo: Obtiene la información de los RefInconsistencyVO almacenados en la persistencia por remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<RefInconsistencyVO> correspondiente a la remisión especificada. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de RefInconsistency por remisión
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	public List<RefInconsistencyVO> getRefInconsistencysByReferenceID(Long refID)
	throws BusinessException {
		return ejbRefInc.getRefInconsistencysByReferenceID(refID);
	}

	/**
	 * Metodo: Obtiene la información de los ReferenceModificationVO almacenados en la persistencia asosiada a la remisión
	 * @param refID - Long identificador de la remisión
	 * @return List<ReferenceModificationVO> corresóndiente a la remisión; vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de los ReferenceModification por remisión
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfandino
	 */
	public List<ReferenceModificationVO> getReferenceModificationsByReferenceID(
			Long refID) throws BusinessException {
		return ejbRefMod.getReferenceModificationsByReferenceID(refID);
	}

	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndByCountry(WarehouseVO whSource,Long country)
	throws BusinessException {
		return ejbRef.getReferencesBySourceWareHouseAndByCountry(whSource,country);
	}

	/**
	 * Metodo: Permite consultar las remisiones a bodegas de salida
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(
			WarehouseVO whSource,Long country) throws BusinessException {
		return ejbRef.getReferencesBySourceWareHouseAndRefStatusInconsistentAndByCountry(whSource,country);
	}
	
	/**
	 * Metodo: Permite consultar las remisiones segun el filtro ingresado
	 * @param referencesFilterToPrintDTO - ReferencesFilterToPrintDTO Contiene los filtros de 
	 * idReferences: El numero de Remision
	 * whSource: La bodega de origen
	 * whTarget: La bodega Destino
	 * referenceStatusId: El estado de la Remision
	 * @return  NotSerializedElementInSelectReferenceToPrintPaginationResponse correspondiente a las remisiones segun el filtro. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * @author cduarte
	 */
	public NotSerializedElementInSelectReferenceToPrintPaginationResponse getAllReferencesByIdSourceTargetWareHouseStatus(
			FilterReferencesToPrintDTO referencesFilterToPrintDTO, RequestCollectionInfo requestCollectionInfo)
			throws BusinessException{
				return ejbRef.getAllReferencesByIdSourceTargetWareHouseStatus(referencesFilterToPrintDTO,requestCollectionInfo);
	}

	/**
	 * Metodo: Permite consultar las remisiones a bodegas de entrada y su estado es inconsistente
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega
	 * @param country - Long identificador del pa�s
	 * @return  List<ReferenceVO> correspondiente a las bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por sus bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByTargetWareHouseAndByCountry(WarehouseVO whTarget,Long country)
	throws BusinessException {
		return ejbRef.getReferencesByTargetWareHouseAndByCountry(whTarget,country);
	}

	/**
	 * Metodo: Registra el envio de una remision creada
	 * @param referencesShipment ReferenceShipmentDTO - El objeto DTO que encapsula la data del envio
	 * de una remision dada. 
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author garciniegas 
	 */

	public void registerReferenceShipment(ReferenceShipmentDTO referencesShipment,
			UserVO user) throws BusinessException
			{
		ejbRef.registerReferenceShipment( referencesShipment,user );
			}

	/**
	 * Metodo: Regresa un <b>posible</b> conjunto de remisiones definidas por un fitro basado
	 * en el nm de remision y el codigo de pais
	 * @param referenceId Long - El identificador de la remision
	 * @param countryCode Long - El codigo del pais
	 * @throws BusinessException en caso de error al tratar de listar las remisiones	 
	 * @author garciniegas 
	 */

	public List<ReferenceVO>getReferenceByIdAndCountryCode( Long referenceId,Long countryCode )throws BusinessException{
		return ejbRef.getReferenceByIdAndCountryCode(referenceId, countryCode);
	}

	/**
	 * Metodo: Valida que la cantidad de elementos que se desea mover de una bodega este disponible
	 * CU INV 30
	 * @param referencesElementItems items de la remision
	 * @param sourceWareHouseId Id de la bodega de origen de la remision
	 * @throws BusinessException en caso de error al tratar de confirmar la remision
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * <code>sdii_mouvement_elements_actual_quantity_lower</code> La cantidad de elementos a trasladar es superior a la disponible<br>
	 * @author jnova
	 */
	public void validateElementsInWHQuantities(List<ReferenceElementItemVO> referencesElementItems , Long sourceWareHouseId) throws BusinessException{
		ejbRef.validateElementsInWHQuantities(referencesElementItems, sourceWareHouseId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#registerReferenceDeliveryAndElementMovement(java.lang.Long, java.util.Date, java.lang.Long, java.lang.String)
	 */
	@Override
	public void registerReferenceDeliveryAndElementMovement(Long referenceId,
			Date deliveryDate, Long targetEmployeeId, String comments, Double sentUnits, Long userId)
	throws BusinessException {
		ejbRef.registerReferenceDeliveryAndElementMovement(referenceId, deliveryDate, targetEmployeeId, comments, sentUnits, userId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceVO> getReferenceByIdAndWarhouseTarget(Long referenceId, Long warehouseId )throws BusinessException{
		return ejbRef.getReferenceByIdAndWarhouseTarget(referenceId, warehouseId);
	}

	/**
	 * Metodo: Consulta las remisiones filtradas por la bodega de origen,bodega destino, 
	 * id de la remision y con estado inconsistente 
	 * Caso de uso INV 37
	 * @param whSource identificador de la bodega de origen
	 * @param whTarget Identificador de la bodega de destino
	 * @param reference Identificador de remision
	 * @param country Identificador del pais
	 * @return List<ReferenceVO> Lista que cumple con el filtro enviado por el usuario
	 * @throws BusinessException En caso de error al ejecutar la operacion
	 * @author garciniegas
	 */
	public List<ReferenceVO> getReferenceByComplexFilterToFindInconsistenceReferences( 
			WarehouseVO whSource,WarehouseVO whTarget,
			ReferenceVO reference,Long country )throws BusinessException
			{
		return ejbRef.getReferenceByComplexFilterToFindInconsistenceReferences(whSource, whTarget, reference, country);
			}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public NotSerializedElementInReferencePaginationResponse getNotSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return ejbRef.getNotSerializedElementInReference(reference, requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public SerializedElementInReferencePaginationResponse getSerializedElementInReference(
			ReferenceVO reference, RequestCollectionInfo requestCollectionInfo) throws BusinessException {
		return ejbRef.getSerializedElementInReference(reference, requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceByIdAndWarhouseTarget(java.lang.Long, java.lang.Long)
	 */
	public List<ReferenceModificationVO> getReferenceModificationsInReference(ReferenceVO refID)throws BusinessException
	{   
		return ejbRef.getReferenceModificationsByReferenceID(refID);
		//return new java.util.ArrayList<ReferenceModificationVO>();
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceDetailsById(java.lang.Long,)
	 */
	public ReferenceVO getReferenceDetailsById(Long referenceId)throws BusinessException{
		return ejbRef.getReferenceDetailsById(referenceId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceDetailsById(java.lang.Long,)
	 */
	public List<RefInconsistencyVO> getReferenceInconsistencyByReferenceID(
			Long id) throws BusinessException {
		return ejbRef.getReferenceInconsistencyByReferenceID(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void saveReferenceDeliveries(Long referenceId ,List<DeliveryVO> deliveriesList ,List<SpecialCommentVO> specialCommentsList)throws BusinessException {
		ejbRef.saveReferenceDeliveries(referenceId, deliveriesList, specialCommentsList);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void addReferenceElementItemToReference(
			List<ReferenceElementItemVO> items, ReferenceVO reference)
	throws BusinessException {
		ejbRef.addReferenceElementItemToReference(items, reference);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void closeReferenceInconsistencyStatus(
			List<Long> inconsistenciesListIds) throws BusinessException {
		ejbRef.closeReferenceInconsistencyStatus(inconsistenciesListIds);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void modifyReferenceElementItemCuantityByReferenceAndItemList(
			List<ReferenceElementItemVO> items, ReferenceVO reference,Boolean finished,UserVO user)
	throws BusinessException {
		ejbRef.modifyReferenceElementItemCuantityByReferenceAndItemList( items, reference,finished ,user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveReferenceDeliveries(java.lang.Long,java.util.List<co.com.directv.sdii.model.vo.DeliveryVO>,java.util.List<co.com.directv.sdii.model.vo.SpecialCommentVO>)
	 */
	public void deleteReferenceElementItemInReference(List<Long> itemsId)
	throws BusinessException {
		ejbRef.deleteReferenceElementItemInReference( itemsId );
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#confirmReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		ejbRef.confirmReferenceElementsReception(referenceId,user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#confirmNotSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmNotSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		ejbRef.confirmNotSerializeReferenceElementsReception(referenceId,user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#confirmSerializeReferenceElementsReception(java.lang.Long,co.com.directv.sdii.model.vo.UserVO)
	 */
	public void confirmSerializeReferenceElementsReception(Long referenceId,UserVO user)throws BusinessException {
		ejbRef.confirmSerializeReferenceElementsReception(referenceId,user);
	}

	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-35
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID o el código de la bodega; nulo si no hace parte de la consulta
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountry(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return ejbRef.getReferencesByReferenceAndWhAndByCountry(ref,whSource,whTarget,country);
	}

	/**
	 * Metodo: Permite consultar las remisiones asociadas a un número de remisión y bodegas
	 * CU Inv-33
	 * @param ref - Long Identificador de la referencia; nulo si no hace parte de la consulta
	 * @param whSource - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta 
	 * @param whTarget - WarehouseVO Debe contener el ID de la bodega; nulo si no hace parte de la consulta
	 * @para refStatus - ReferenceStatusVO Debe contener el ID del estadoo; nulo si no hace parte de la consulta
	 * @return  List<ReferenceVO> correspondiente a la remisión con bodegas especificadas. Vacio en otro caso
	 * @throws BusinessException en caso de error al ejecutar la consulta de las Reference por su identificador y bodegas
	 *  <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
	 * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
	 * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
	 * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
	 * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
	 * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
	 * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author gfadnino
	 */
	public ReferenceResponse getReferencesByReferenceAndWhAndRefStatusRefStatus(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget,
			ReferenceStatusVO refStatus,Long userId, RequestCollectionInfo requestCollectionInfo ) throws BusinessException {
		return ejbRef.getReferencesByReferenceAndWhAndRefStatusRefStatus(
				ref, whSource, whTarget,refStatus,userId, requestCollectionInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByConfirmationDateAndByCountry(java.lang.Long, java.lang.Long)
	 */
	public ReferenceResponse getReferencesByConfirmationDateAndByCountry(Long dealer,Long country, RequestCollectionInfo requestCollectionInfo) throws BusinessException{
		return ejbRef.getReferencesByConfirmationDateAndByCountry(dealer, country,requestCollectionInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByConfirmationDate(java.lang.Long)
	 */
	public List<ReferenceVO>getReferenceByIdAndWarehouseTargetToAddInconsistence( Long referenceId,Long warehouseId )throws BusinessException
	{
		return ejbRef.getReferenceByIdAndWarehouseTarget( referenceId, warehouseId); 
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByConfirmationDate(java.lang.Long)
	 */
	public List<RefConfirmationVO> getConfirmationsByReferenceId( Long referenceId ) throws BusinessException
	{
		return ejbRef.getConfirmationsByReferenceId( referenceId );
	}

	public List<RefConfirmationVO> getConfirmationsByReferenceIdAndElementId( Long referenceId,Long elementId ) throws BusinessException
	{
		return ejbRef.getConfirmationsByReferenceIdAndElementId( referenceId, elementId);
	}

	public List<ReferenceVO>getReferenceByIdAndWarehouseSourceAndWareHouseTarget( Long referenceId,Long whSourceId,Long whTargetId )throws BusinessException
	{
		return ejbRef.getReferenceByIdAndWarehouseSourceAndWareHouseTarget(referenceId, whSourceId,whTargetId);
	}

	public List<ReferenceVO>getReferenceByIdAndWarehouseSource( Long referenceId,Long warehouseId )throws BusinessException
	{
		return ejbRef.getReferenceByIdAndWarehouseSource(referenceId, warehouseId);
	}


	public List<ReferenceVO> getReferencesByElementId(@WebParam(name="elementId")Long elementId) throws BusinessException
	{
		return ejbRef.getReferencesByElementId( elementId );		 
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(Long ref,
			WarehouseVO whSource, WarehouseVO whTarget,Long country)
			throws BusinessException {
		return ejbRef.getReferencesByReferenceAndWhAndByCountryAndCreatedStatus(ref,whSource,whTarget,country);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return ejbRef.getReferencesByReferenceAndWhAndByCountryAndCreatedStatusAndCreationstatus(ref,  whSource,  whTarget,  country);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return ejbRef.getReferencesByReferenceAndWhAndByCountryAndSendedOrPartialConfirmed(ref,  whSource,  whTarget,  country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return ejbRef.getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndCreatedStatusSameWhDealerOwner(ref, whSource, whTarget, country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(
			Long referenceId, Long sourceWarehouseId) throws BusinessException {
		return ejbRef.getReferencesByRefIdOrWhSourceIdAndDifferentDealerWhs(referenceId, sourceWarehouseId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getCreatedReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public ReferenceResponse getCreatedReferencesByFilter (ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbRef.getCreatedReferencesByFilter (referenceDTO,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#generateReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public ReferenceVO generateReference(ReferenceVO reference)
			throws BusinessException {
		return ejbRef.generateReference (reference);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#saveQtyRefCtrlItems(co.com.directv.sdii.model.vo.ReferenceVO, java.util.List)
	 */
	@Override
	public void saveQtyRefCtrlItems(ReferenceVO reference,
			List<RefQuantityControlItemVO> qtyCtrlItems)
			throws BusinessException {
		ejbRefQtyCtrl.saveQtyRefCtrlItems(reference,qtyCtrlItems);
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#validateExistenceOfReferenceQuantityDiferences(java.lang.Long)
	 */
	@Override
	public boolean validateExistenceOfReferenceQuantityDifferences(Long referenceId) throws BusinessException {
		return ejbRefQtyCtrl.validateExistenceOfReferenceQuantityDifferences(referenceId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesPreloadByCreationProcess(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefQuantityControlItemsResponse getReferencesPreloadByCreationProcess(
			Long idWhSource, RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return ejbRefQtyCtrl.getReferencesPreloadByCreationProcess(idWhSource,requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesPreloadByID(java.lang.Long)
	 */
	@Override
	public RefQuantityControlItemVO getReferencesPreloadByID(Long idRefQtyCtrl)
			throws BusinessException {
		return ejbRefQtyCtrl.getRefQuantityControlItemByID(idRefQtyCtrl);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#logicDeleteReference(co.com.directv.sdii.model.vo.ReferenceVO)
	 */
	@Override
	public void logicDeleteReference(ReferenceVO ref) throws BusinessException {
		ejbRef.logicDeleteReference(ref);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getPreloadedReferences(java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReferenceResponse getPreloadedReferences(Long sourceWhId, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getPreloadedReferences(sourceWhId, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getRefQtyCtrlItemsByReference(co.com.directv.sdii.model.vo.RefQuantityControlItemVO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public RefQuantityControlItemsResponse getRefQtyCtrlItemsByReference(RefQuantityControlItemVO refId,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefQtyCtrl.getRefQtyCtrlItemsByReference(refId, requestCollInfo);
	}
	
	//INICIO - CU-INV-36 Registro de inconsistencias en remisiones
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(java.lang.Long, co.com.directv.sdii.model.vo.WarehouseVO, co.com.directv.sdii.model.vo.WarehouseVO, java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(
			Long ref, WarehouseVO whSource, WarehouseVO whTarget, Long country)
			throws BusinessException {
		return ejbRef.getReferencesByRefIdOrSourceWhIdOrTargetWhIdAndDifferentDealerWhs(ref, whSource, whTarget, country);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceInconsistenciesOpenedByReferenceId(java.lang.Long)
	 */
	public RefInconsistencyResponse getReferenceInconsistenciesOpenedByReferenceId(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefInc.getReferenceInconsistenciesOpenedByReferenceId(refID, requestCollInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferenceInconsistenciesClosedByReferenceId(java.lang.Long)
	 */
	public RefInconsistencyResponse getReferenceInconsistenciesClosedByReferenceId(Long refID, RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefInc.getReferenceInconsistenciesClosedByReferenceId(refID, requestCollInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReportedElementsByRefInconsistencyId(java.lang.Long, boolean, boolean)
	 */
	public List<ReportedElementVO> getReportedElementsByRefInconsistencyId(Long refInconsistencyId, boolean incluirSobrantes, boolean incluirFaltantes) throws BusinessException {
		return reportedElementFacadeBean.getReportedElementsByRefInconsistencyId(refInconsistencyId, incluirSobrantes, incluirFaltantes);
	}
	
	/*
	 * 	(non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#createRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO, boolean, boolean)
	 */
	public void saveRefInconsistency(RefInconsistencyVO refInconsistency, List<ReportedElementVO> reportedElements, Long userId) throws BusinessException {
		ejbRefInc.saveRefInconsistency(refInconsistency, reportedElements, userId);
	}

	//FIN - CU-INV-36 Registro de inconsistencias en remisiones
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#isReferenceFromSameDealer(java.lang.Long)
	 */
	@Override
	public boolean isReferenceFromSameDealer(Long referenceId) throws BusinessException {
		return ejbRef.isReferenceFromSameDealer(referenceId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#sendReference(co.com.directv.sdii.model.vo.ReferenceVO, boolean, boolean)
	 */
	@Override
	public void sendReference(ReferenceVO referenceVO, boolean isBetweenDifDealers,boolean validateQuantityControl, Long userId) throws BusinessException {
		this.ejbRef.sendReference(referenceVO, isBetweenDifDealers,validateQuantityControl, userId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getDependentReferences(java.lang.Long)
	 */
	@Override
	public List<ReferenceVO> getDependentReferences(Long refIncId) throws BusinessException {
		return ejbRefInc.getGeneratedReferencesByRefIncId(refIncId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getAddedElements(java.lang.Long)
	 */
	@Override
	public List<ReferenceElementItemVO> getAddedElements(Long refInconsistencyId) throws BusinessException {
		return referenceElementItemFacade.getAddedElements(refInconsistencyId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getReferencesByFilter(co.com.directv.sdii.model.dto.ReferencesFilterDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public ReferenceResponse getReferencesByFilter( ReferencesFilterDTO referenceDTO,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRef.getReferencesByFilter(referenceDTO, requestCollInfo);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#validateReferenceReceiveData(java.lang.Long)
	 */
	@Override
	public boolean validateReferenceReceiveData(Long referenceId) throws BusinessException {
		return ejbRef.validateReferenceReceiveData(referenceId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#createRefRecieveData(co.com.directv.sdii.model.vo.RefRecieveDataVO)
	 */
	@Override
	public void createRefRecieveData(RefRecieveDataVO refRecieveDataVO, Long userId) throws BusinessException {
		ejbRef.createRefRecieveData(refRecieveDataVO, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#closeRefInconsistency(co.com.directv.sdii.model.vo.RefInconsistencyVO)
	 */
	@Override
	public Long closeRefInconsistency(RefInconsistencyVO refInconsistencyVO, Long userId)
			throws BusinessException {
		return ejbRef.closeRefInconsistency(refInconsistencyVO, userId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#getAllTransportCompaniesByCountryId(java.lang.Long, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@Override
	public TransportCompanyDTO getAllTransportCompaniesByCountryId(
			Long countryId, RequestCollectionInfoDTO requestCollInfo)
			throws BusinessException {
		return ejbRefTrans.getAllTransportCompaniesByCountryId(countryId, requestCollInfo);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#isTargetACrewOfSourceWh(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean isReferenceRequiresValidationProcess(Long sourceWhId, Long targetWhId) throws BusinessException {
		return ejbRef.isReferenceRequiresValidationProcess(sourceWhId, targetWhId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IReferenceWS#validateReportedElementsForLessElementsInRefInc(java.util.List)
	 */
	@Override
	public void validateReportedElementsForLessElementsInRefInc(
			List<ReportedElementForValidationDTO> elementsToValidate)
			throws BusinessException {
		ejbRefInc.validateReportedElementsForLessElementsInRefInc(elementsToValidate);
	}
	
}

package co.com.directv.sdii.ws.business.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.ImpLogConfirmationFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ImportLogItemFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.ItemStatusFacadeBeanLocal;
import co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal;
import co.com.directv.sdii.model.dto.ImportLogElementsFilterDTO;
import co.com.directv.sdii.model.dto.ModifyImportLogItemDTO;
import co.com.directv.sdii.model.pojo.collection.ImportLogItemResponse;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ImpLogConfirmationVO;
import co.com.directv.sdii.model.vo.ImportLogItemVO;
import co.com.directv.sdii.model.vo.ImportLogVO;
import co.com.directv.sdii.model.vo.ItemStatusVO;
import co.com.directv.sdii.model.vo.MeasureUnitVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.ws.business.stock.IImportLogItemWS;

/**
 * Servicio web que expone las operaciones relacionadas con ImportLog
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.ImportLogFacadeBeanLocal
 */
@MTOM
@WebService(serviceName="ImportLogItemService",
		endpointInterface="co.com.directv.sdii.ws.business.stock.IImportLogItemWS",
		targetNamespace="http://stock.business.ws.sdii.directv.com.co/",
		portName="ImportLogItemPort")
@Stateless()
public class ImportLogItemWS implements IImportLogItemWS{
	
	@EJB
    private ImportLogItemFacadeBeanLocal ejbRefItem;
	@EJB
    private MeasureUnitFacadeBeanLocal ejbRefMUnit;
	@EJB
    private ItemStatusFacadeBeanLocal ejbRefIteStatus;
	@EJB
    private ImpLogConfirmationFacadeBeanLocal ejbRefCon;	
	
	/**
	 * Metodo: persiste la información de un ImportLogItem
	 * @param obj objeto que encapsula la información necesaria para construir el ImportLogItem,
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
	@Override
	public void createImportLogItem(ImportLogItemVO objImportLogItem) throws BusinessException{
		ejbRefItem.createImportLogItem(objImportLogItem);
	}
	
	/**
	 * Metodo: actualiza la información de un ImportLogItem
	 * @param obj objeto que encapsula la información del ImportLogItem a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ImportLogItem
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
	public void updateImportLogItem(ImportLogItemVO objImportLogItem) throws BusinessException{
		ejbRefItem.updateImportLogItem(objImportLogItem);
	}
	
	/**
	 * Metodo: borra un ImportLogItem de la persistencia
	 * @param obj objeto que encapsula la información del ImportLogItem, solo se requiere la propiedad id
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
	public void deleteImportLogItem(ImportLogItemVO objImportLogItem) throws BusinessException{
		ejbRefItem.deleteImportLogItem(objImportLogItem);
	}
	
	/**
	 * Metodo: Obtiene un ImportLogItem dado el identificador del mismo
	 * @param id identificador del ImportLogItem a ser consultado
	 * @return ImportLogItem con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ImportLogItem con el id, ver códigos de excepción.
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
	public ImportLogItemVO getImportLogItemByID(Long id) throws BusinessException{
		return ejbRefItem.getImportLogItemByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los ImportLogItem almacenados en la persistencia
	 * @return lista con los ImportLogItem existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException{
		return ejbRefItem.getAllImportLogItems();
	}
	
	/**
	 * Metodo: persiste la información de un MeasureUnit
	 * @param obj objeto que encapsula la información necesaria para construir el MeasureUnit,
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
	@Override
	public void createMeasureUnit(MeasureUnitVO objMeasureUnit) throws BusinessException{
		ejbRefMUnit.createMeasureUnit(objMeasureUnit);
	}
	
	/**
	 * Metodo: actualiza la información de un MeasureUnit
	 * @param obj objeto que encapsula la información del MeasureUnit a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo MeasureUnit
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
	public void updateMeasureUnit(MeasureUnitVO objMeasureUnit) throws BusinessException{
		ejbRefMUnit.updateMeasureUnit(objMeasureUnit);
	}
	
	/**
	 * Metodo: borra un MeasureUnit de la persistencia
	 * @param obj objeto que encapsula la información del MeasureUnit, solo se requiere la propiedad id
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
	public void deleteMeasureUnit(MeasureUnitVO objMeasureUnit) throws BusinessException{
		ejbRefMUnit.deleteMeasureUnit(objMeasureUnit);
	}
	
	/**
	 * Metodo: Obtiene un MeasureUnit dado el identificador del mismo
	 * @param id identificador del MeasureUnit a ser consultado
	 * @return MeasureUnit con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el MeasureUnit con el id, ver códigos de excepción.
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
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el objeto por el id especificado<br>
	 * @author jjimenezh
	 */
	@Override
	public MeasureUnitVO getMeasureUnitByID(Long id) throws BusinessException{
		return ejbRefMUnit.getMeasureUnitByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los MeasureUnit almacenados en la persistencia
	 * @return lista con los MeasureUnit existentes, una lista vacia en caso que no exista ninguno.
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
	public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException{
		return ejbRefMUnit.getAllMeasureUnits();
	}

	/**
	 * Metodo: Obtiene un MeasureUnit dado el código del mismo
	 * @param code código del MeasureUnit a ser consultado
	 * @return MeasureUnit con el código especificado, nulo en caso que no exista
	 * @throws BusinessException en caso de error al ejecutar la operación,
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
	public MeasureUnitVO getMeasureUnitByCode(String code)
			throws BusinessException {
		return ejbRefMUnit.getMeasureUnitByCode(code);
	}
	
	/**
	 * Metodo: persiste la información de un ItemStatus
	 * @param obj objeto que encapsula la información necesaria para construir el ItemStatus,
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
	@Override
	public void createItemStatus(ItemStatusVO objItemStatus) throws BusinessException{
		ejbRefIteStatus.createItemStatus(objItemStatus);
	}
	
	/**
	 * Metodo: actualiza la información de un ItemStatus
	 * @param obj objeto que encapsula la información del ItemStatus a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ItemStatus
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
	public void updateItemStatus(ItemStatusVO objItemStatus) throws BusinessException{
		ejbRefIteStatus.updateItemStatus(objItemStatus);
	}
	
	/**
	 * Metodo: borra un ItemStatus de la persistencia
	 * @param obj objeto que encapsula la información del ItemStatus, solo se requiere la propiedad id
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
	public void deleteItemStatus(ItemStatusVO objItemStatus) throws BusinessException{
		ejbRefIteStatus.deleteItemStatus(objItemStatus);
	}
	
	/**
	 * Metodo: Obtiene un ItemStatus dado el identificador del mismo
	 * @param id identificador del ItemStatus a ser consultado
	 * @return ItemStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ItemStatus con el id, ver códigos de excepción.
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
	public ItemStatusVO getItemStatusByID(Long id) throws BusinessException{
		return ejbRefIteStatus.getItemStatusByID(id);
	}
	
	
	/**
	 * Metodo: Obtiene un ItemStatus dado el identificador del mismo
	 * @param id identificador del ItemStatus a ser consultado
	 * @return ItemStatus con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ItemStatus con el id, ver códigos de excepción.
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
	public ItemStatusVO getItemStatusByCode(String itemStatusCode) throws BusinessException{
		return ejbRefIteStatus.getItemStatusByCode(itemStatusCode);
	}
	
	/**
	 * Metodo: Obtiene todos los ItemStatus almacenados en la persistencia
	 * @return lista con los ItemStatus existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ItemStatusVO> getAllItemStatuss() throws BusinessException{
		return ejbRefIteStatus.getAllItemStatuss();
	}
	
	/**
	 * Metodo: persiste la información de un ImpLogConfirmation
	 * @param obj objeto que encapsula la información necesaria para construir el ImpLogConfirmation,
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
	@Override
	public void createImpLogConfirmation(ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException{
		ejbRefCon.createImpLogConfirmation(objImpLogConfirmation);
	}
	
	/**
	 * Metodo: actualiza la información de un ImpLogConfirmation
	 * @param obj objeto que encapsula la información del ImpLogConfirmation a actualizar,
	 * debe venir especificada la propiedad id, en caso que no venga, se generará un nuevo ImpLogConfirmation
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
	public void updateImpLogConfirmation(ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException{
		ejbRefCon.updateImpLogConfirmation(objImpLogConfirmation);
	}
	
	/**
	 * Metodo: borra un ImpLogConfirmation de la persistencia
	 * @param obj objeto que encapsula la información del ImpLogConfirmation, solo se requiere la propiedad id
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
	public void deleteImpLogConfirmation(ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException{
		ejbRefCon.deleteImpLogConfirmation(objImpLogConfirmation);
	}
	
	/**
	 * Metodo: Obtiene un ImpLogConfirmation dado el identificador del mismo
	 * @param id identificador del ImpLogConfirmation a ser consultado
	 * @return ImpLogConfirmation con el id especificado, se lanza una exepción en caso que no se
	 * encuentre el ImpLogConfirmation con el id, ver códigos de excepción.
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
	public ImpLogConfirmationVO getImpLogConfirmationByID(Long id) throws BusinessException{
		return ejbRefCon.getImpLogConfirmationByID(id);
	}
	
	/**
	 * Metodo: Obtiene todos los ImpLogConfirmation almacenados en la persistencia
	 * @return lista con los ImpLogConfirmation existentes, una lista vacia en caso que no exista ninguno.
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
	public List<ImpLogConfirmationVO> getAllImpLogConfirmations() throws BusinessException{
		return ejbRefCon.getAllImpLogConfirmations();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getImportLogItemsByImportLogIdAndIsSerialized(java.lang.Long, boolean)
	 */
	/*@Override
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(Long importLogId, boolean isSerialized,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefItem.getImportLogItemsByImportLogIdAndIsSerialized(importLogId, isSerialized, requestCollInfo);
	}*/

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#confirmImportLogItemsAndByCountry(java.util.List, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void confirmImportLogItemsAndByCountry(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, DealerVO dealer)
			throws BusinessException {
		ejbRefItem.confirmSerializedAndNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, dealer);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#confirmSerializedAndNotSerializedImportLogItems(java.util.List, java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void confirmSerializedAndNotSerializedImportLogItems(
			List<ImportLogItemVO> impLogItems2Confirm, Long userId,
			Long logisticOperatorDealerId, DealerVO dealer)
			throws BusinessException {
		ejbRefItem.confirmSerializedAndNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, dealer);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
	 */
	@Override
	public void confirmNotSerializedImportLogItems(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, boolean isWHQ)
	throws BusinessException {
		ejbRefItem.confirmNotSerializedImportLogItems(impLogItems2Confirm, userId, logisticOperatorDealerId, isWHQ);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#reportInconsistentItemsAndByCountry(java.util.List, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public void reportInconsistentItems(
			List<ImportLogItemVO> inconsistentLogItems, String comment, Long incTypeId,
			Long userId) throws BusinessException {
		ejbRefItem.reportInconsistentItems(inconsistentLogItems, comment, incTypeId, userId);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getImportLogItemsByImportLogId(java.lang.Long)
	 */
	@Override
	public ImportLogItemResponse getImportLogItemsByImportLogId(Long importLogId, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefItem.getImportLogItemsByImportLogId(importLogId, requestCollInfo);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getImportLogItemsByImportLogId(java.lang.Long)
	 */
	@Override
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(ImportLogElementsFilterDTO filterImportLogElements, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefItem.getImportLogItemsByImportLogIdAndIsSerialized(filterImportLogElements, requestCollInfo);
	}
	
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getImportLogItemsByImportLogWithConfirmations(java.lang.Long)
	 */
	@Override
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(Long idImportLog,RequestCollectionInfo requestCollInfo) throws BusinessException {
		return ejbRefItem.getImportLogItemsByImportLogWithConfirmations(idImportLog,requestCollInfo);		
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#confirmSerializedElementsImportLogAndByCountry(java.lang.Long, java.util.List, java.lang.Long)
	 */
	@Override
	public void confirmSerializedElementsImportLogAndByCountry(Long pImpLogId, List<ImportLogItemVO> pImpLogItems,Long country) throws BusinessException {
		ejbRefItem.confirmSerializedElementsImportLog(pImpLogId, pImpLogItems,country);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#reportInconsistentSerializedImpLogElements(java.lang.Long, java.util.List, java.lang.String, java.lang.Long)
	 */
	@Override
	public void reportInconsistentSerializedImpLogElementsAndByCountry(Long pImpLogId, List<ImportLogItemVO> impLogItems, String comment, Long pUserId, Long incType,Long country)	throws BusinessException {
		ejbRefItem.reportInconsistentSerializedImpLogElements(pImpLogId, impLogItems, comment, pUserId, incType, null);
	}

	/**
	 * Metodo: Obtiene MeasureUnitVO activos
	 * @return List<MeasureUnitVO> en estado activo; vacio en otro caso
	 * unidad con ese código
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta por estado
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
	public List<MeasureUnitVO> getMeasureUnitsByStatus()
			throws BusinessException {
		return ejbRefMUnit.getMeasureUnitsByStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getMeasureUnitsByStatusPage(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MeasureUnitResponse getMeasureUnitsByStatusPage(RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbRefMUnit.getMeasureUnitsByStatus(requestCollInfo);
	}
	


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.stock.IImportLogItemWS#getMeasureUnitsByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(String code,RequestCollectionInfo requestCollInfo)throws BusinessException{
		return ejbRefMUnit.getMeasureUnitsByAllStatusPage(code,requestCollInfo);
	}
	

	
	@Override
	public ImportLogItemResponse getImportLogItemsByCriteria(ModifyImportLogItemDTO modifyImportLogItemCriteria, RequestCollectionInfo requestCollInfo) throws BusinessException{
		return ejbRefItem.getImportLogByCriteria(modifyImportLogItemCriteria, requestCollInfo);
	}
	
	@Override
	public void updateQuantityItemImportLogNotSerializedElements(List<ImportLogItemVO> importLogItemVO)  throws BusinessException{
		ejbRefItem.updateQuantityItemImportLogNotSerializedElements(importLogItemVO);
	}
	
	@Override
	public void confirmNotSerializedImportLogItemsCompany(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, long company, String Cod_WHDestiny)throws BusinessException{
		ejbRefItem.confirmNotSerializedImportLogItemsCompany(impLogItems2Confirm, userId, logisticOperatorDealerId, company, Cod_WHDestiny);
	}
	
	@Override
	public void confirmNotSerializedImportLogItemsLogisticOperator(List<ImportLogItemVO> impLogItems2Confirm, Long userId, Long logisticOperatorDealerId, long logisticOperator, String Cod_WHDestiny)throws BusinessException{
		ejbRefItem.confirmNotSerializedImportLogItemsLogisticOperator(impLogItems2Confirm, userId, logisticOperatorDealerId, logisticOperator, Cod_WHDestiny);
	}

			
}

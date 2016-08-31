 package co.com.directv.sdii.ws.business.stock;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
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

/**
 * Servicio web que expone las operaciones relacionadas con ImportLogItem
 * 
 * Fecha de Creación: 28/06/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a> * @version 1.0
 * 
 */
@WebService(name="ImportLogItemWS",targetNamespace="http://stock.business.ws.sdii.directv.com.co/")
public interface IImportLogItemWS {

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
	@WebMethod(operationName="createImportLogItem", action="createImportLogItem")
	public void createImportLogItem(@WebParam(name="objImportLogItem")ImportLogItemVO objImportLogItem) throws BusinessException;
	
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
	@WebMethod(operationName="updateImportLogItem", action="updateImportLogItem")
	public void updateImportLogItem(@WebParam(name="objImportLogItem")ImportLogItemVO objImportLogItem) throws BusinessException;
	
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
	@WebMethod(operationName="deleteImportLogItem", action="deleteImportLogItem")
	public void deleteImportLogItem(@WebParam(name="objImportLogItem")ImportLogItemVO objImportLogItem) throws BusinessException;
	
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
	@WebMethod(operationName="getImportLogItemByID", action="getImportLogItemByID")
	public ImportLogItemVO getImportLogItemByID(@WebParam(name="id")Long id) throws BusinessException;
	
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
	@WebMethod(operationName="getAllImportLogItems", action="getAllImportLogItems")
	public List<ImportLogItemVO> getAllImportLogItems() throws BusinessException;
	
	/**
	 * Metodo:CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Operación para confirmar los elementos de un registro de importación<br>
	 * Las reglas del negocio son las siguientes:<br><br>
	 * 
	 * • OPCION 1 - Confirmar el recibido de todos los elementos <br>
	 * (para cuando las cantidades recibidas de todos los elementos no serializados coinciden con
	 *  las cantidades esperadas)<br>
	 *  En este caso se debe enviar desde la vista en el campo confirmedQuantity el mismo número 
	 *  de elementos que tiene el campo amountExpected<br><br>
	 * 
	 * • OPCION 2 - Confirmar el recibido total de un elemento <br>
	 * (para cuando las cantidades recibidas de un elemento coinciden con la cantidad esperada)<br>
	 * En este caso dentro de la lista solo vienen los elementos a confirmar y el campo
	 * confirmedQuantity tiene el mismo valor que el campo amountExpected<br><br>
	 * 
	 * • OPCION 3 - Confirmar el recibido parcial de un elemento <br>
	 * (para cuando el usuario ingresa una cantidad de elementos recibidos, pero que todavía no completan la cantidad esperada)<br>
	 * En este caso se envía una cantidad diferente en el campo confirmedQuantity al campo amountExpected<br><br>
	 * 
	 * <b>Por cada Elemento de la lista se evaluará:</b><br><br>
	 * 
	 * 1. Si el elemento tiene en el campo <code>confirmedQuantity</code> el mismo valor que el campo
	 * <code>amountExpected</code>:<br>
	 * • Si no había confirmación parcial de un elemento, adiciona una confirmación igual a la cantidad esperada  “Confirmado Parcial” en la tabla IMP_LOG_CONFIRMATIONS<br>
	 * • Si ya había confirmación parcial del elemento, adiciona una nueva confirmación parcial para ese elemento de tal forma que la suma de las confirmaciones parciales sea igual a la cantidad esperada de ese elemento<br>
	 * • Modifica el estado del los elementos no serializados del registro de Importación a recepcionado es decir cambia el valor de la columna ITEM_STATUS_ID en la tabla IMPORT_LOG_ITEMS<br><br>
	 * 
	 * 2. Si el elemento tiene en el campo <code>confirmedQuantity</code> el una cantidad diferente al campo <code>amountExpected</code>:<br>
	 * • Se realiza la siguiente validación:<br>
	 *    -La cantidad ingresada en la cantidad confirmada no puede superar la cantidad esperada.<br>
	 *    -La suma de las confirmaciones parciales no puede superar la cantidad esperada<br>
	 * • Se adiciona una confirmación parcial con la cantidad ingresada por el usuario<br> 
	 * • Si la suma de las confirmaciones parciales del elemento  es igual a la cantidad esperada de ese elemento modifica el estado del elemento a recepcionado<br>
	 * • Si la suma de las confirmaciones parciales del elemento no  es igual a la cantidad esperada de ese elemento modifica el estado del elemento a “Confirmado Parcial”<br><br>
	 *
	 * <b> Una vez terminada la iteración sobre la lista de ítems, se ejecutan las siguientes tareas:</b><br><br>
	 * • Si todos los elementos del Registro de Importación ya han sido confirmados, modifica el estado del regisro de Importación a “Recepcionado” y el Sistema usa el caso de uso INV-08 para informar al Analista de Logística que el Registro de Importación ha sido recepcionado<br>
	 * • Si faltan elementos del registro de Importación por confirmar modifica el estado del registro de Importación a “Confirmado parcial”<br>
	 * • Se invoca el caso de uso INV-06 para mover todos los elementos no serializados, que no habían sido confirmados anteriormente, a la bodega de Control de Calidad del Operador logístico<br>
	 * • Se invoca el caso de uso INV-07 para informar al IBS el movimiento de los elementos no serializados que no habían sido confirmados anteriormente, a bodega
	 * @param impLogItems2Confirm lista con los ítems a confirmar, cada ítem debe especificar la cantidad
	 * confirmada del elemento relacionado
	 * @param userId identificador del usuario que realiza la acción
	 * @param logisticOperatorDealerId identificador de la compañía operador logístico
	 * @param country - Long identificador del país
	 * @throws BusinessException En caso de error al realizar la operación<br>
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
	@WebMethod(operationName="confirmImportLogItemsAndByCountry", action="confirmImportLogItemsAndByCountry")
	public void confirmImportLogItemsAndByCountry(
			@WebParam(name="impLogItems2Confirm") List<ImportLogItemVO> impLogItems2Confirm,
			@WebParam(name="userId") Long userId, 
			@WebParam(name="logisticOperatorDealerId") Long logisticOperatorDealerId,
			@WebParam(name="dealer") DealerVO dealer) throws BusinessException;
	
	
	/**
	 * Metodo: Confirma los elementos serializados de un registro de importacion
	 * CU Inv 04
	 * @param List<ImportLogItemVO>  Elementos de un registro de importacion	 
	 * @param Long pImpLogId, id del registro de importacion
	 * @param country - Long identificador del país
	 * @throws BusinessException en caso de error al ejecutar guardar ImportLogItems
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jalopez
	 */
	@WebMethod(operationName="confirmSerializedElementsImportLogAndByCountry", action="confirmSerializedElementsImportLogAndByCountry")
	public void confirmSerializedElementsImportLogAndByCountry(@WebParam(name="pImpLogId")Long pImpLogId, @WebParam(name="pImpLogItems")List<ImportLogItemVO> pImpLogItems,@WebParam(name="country")Long country) throws BusinessException;
	
	/**
	 * Metodo:CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Operación para confirmar los elementos de un registro de importación<br>
	 * Las reglas del negocio son las siguientes:<br><br>
	 * 
	 * 
	 * @param impLogItems2Confirm lista con los ítems a confirmar, cada ítem debe especificar la cantidad
	 * confirmada del elemento relacionado
	 * @param userId identificador del usuario que realiza la acción
	 * @param logisticOperatorDealerId identificador de la compañía operador logístico
	 * @param country - Long identificador del país
	 * @throws BusinessException En caso de error al realizar la operación<br>
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
	@WebMethod(operationName="confirmSerializedAndNotSerializedImportLogItems", action="confirmSerializedAndNotSerializedImportLogItems")
	public void confirmSerializedAndNotSerializedImportLogItems(
			@WebParam(name="impLogItems2Confirm") List<ImportLogItemVO> impLogItems2Confirm, 
			@WebParam(name="userId") Long userId, 
			@WebParam(name="logisticOperatorDealerId") Long logisticOperatorDealerId,
			@WebParam(name="dealer")DealerVO dealer) throws BusinessException;
	
	/**
	 * Metodo: CU Inv - 04 Confirma elementos no serializados de un registro de importación.
	 * @param impLogItems2Confirm
	 * @param userId
	 * @param logisticOperatorDealerId
	 * @param isWHQ
	 * @throws BusinessException
	 */
	public void confirmNotSerializedImportLogItems(
			@WebParam(name="impLogItems2Confirm") List<ImportLogItemVO> impLogItems2Confirm, 
			@WebParam(name="userId") Long userId, 
			@WebParam(name="logisticOperatorDealerId") Long logisticOperatorDealerId, 
			@WebParam(name="isWHQ") boolean isWHQ) throws BusinessException;
	
	
	/**
	 * Metodo: CU INV - 03 Confirmar los elementos No serializados de un registro
	 * de importación<br>
	 * Permite reportar la inconsistencia en un registro de importación<br>
	 * Se deben realizar los siguientes pasos:<br><br>
	 * • Modificar el estado del registro de Importación a “Inconsistente”<br>
	 * • Actualizar los campos de la inconsistencia, y se genera un registro en la tabla import_log_inconsistencies<br>
	 * • Invocar el caso de uso INV-08 para informar al Analista de Logística informando novedad de inconsistencia en el Registro de Importación<br> 
	 * @param inconsistentLogItems Lista con los ítems que se reportan como inconsistentes
	 * @param comment comentario que se persistirá en el registro de inconsistencia
	 * @param userId identificador del usuario que realiza el reporte de la inconsistencia
	 * @param country - Long identificador del país
	 * @throws BusinessException En caso de error al ejecutar la operación<br>
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
	@WebMethod(operationName="reportInconsistentItems", action="reportInconsistentItems")
	public void reportInconsistentItems(@WebParam(name="inconsistentLogItems") List<ImportLogItemVO> inconsistentLogItems,@WebParam(name="comment") String comment, @WebParam(name="incTypeId") Long incTypeId, @WebParam(name="userId") Long userId)throws BusinessException;
	
	
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
	@WebMethod(operationName="createMeasureUnit", action="createMeasureUnit")
	public void createMeasureUnit(@WebParam(name="objMeasureUnit")MeasureUnitVO objMeasureUnit) throws BusinessException;
	
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
	@WebMethod(operationName="updateMeasureUnit", action="updateMeasureUnit")
	public void updateMeasureUnit(@WebParam(name="objMeasureUnit")MeasureUnitVO objMeasureUnit) throws BusinessException;
	
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
	@WebMethod(operationName="deleteMeasureUnit", action="deleteMeasureUnit")
	public void deleteMeasureUnit(@WebParam(name="objMeasureUnit")MeasureUnitVO objMeasureUnit) throws BusinessException;
	
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
	 * <code>sdii_CODE_entity_not_found</code> En caso que no encuentre el tipo de dealer por el id especificado<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName="getMeasureUnitByID", action="getMeasureUnitByID")
	public MeasureUnitVO getMeasureUnitByID(@WebParam(name="id")Long id) throws BusinessException;
	
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
	@WebMethod(operationName="getMeasureUnitByCode", action="getMeasureUnitByCode")
	public MeasureUnitVO getMeasureUnitByCode(@WebParam(name="code")String code) throws BusinessException;
	
	
	
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
	@WebMethod(operationName="getAllMeasureUnits", action="getAllMeasureUnits")
	public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException;
	
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
	@WebMethod(operationName="createItemStatus", action="createItemStatus", exclude = true)
	public void createItemStatus(@WebParam(name="objItemStatus")ItemStatusVO objItemStatus) throws BusinessException;
	
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
	@WebMethod(operationName="updateItemStatus", action="updateItemStatus", exclude = true)
	public void updateItemStatus(@WebParam(name="objItemStatus")ItemStatusVO objItemStatus) throws BusinessException;
	
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
	@WebMethod(operationName="deleteItemStatus", action="deleteItemStatus", exclude = true)
	public void deleteItemStatus(@WebParam(name="objItemStatus")ItemStatusVO objItemStatus) throws BusinessException;
	
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
	@WebMethod(operationName="getItemStatusByID", action="getItemStatusByID", exclude = true)
	public ItemStatusVO getItemStatusByID(@WebParam(name="id")Long id) throws BusinessException;
	
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
	@WebMethod(operationName="getItemStatusByCode", action="getItemStatusByCode")
	public ItemStatusVO getItemStatusByCode(@WebParam(name="itemStatusCode")String itemStatusCode) throws BusinessException;
	
	
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
	@WebMethod(operationName="getAllItemStatuss", action="getAllItemStatuss")
	public List<ItemStatusVO> getAllItemStatuss() throws BusinessException;
	
	
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
	@WebMethod(operationName="createImpLogConfirmation", action="createImpLogConfirmation")
	public void createImpLogConfirmation(@WebParam(name="objImpLogConfirmation")ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException;
	
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
	@WebMethod(operationName="updateImpLogConfirmation", action="updateImpLogConfirmation")
	public void updateImpLogConfirmation(@WebParam(name="objImpLogConfirmation")ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException;
	
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
	@WebMethod(operationName="deleteImpLogConfirmation", action="deleteImpLogConfirmation", exclude = true)
	public void deleteImpLogConfirmation(@WebParam(name="objImpLogConfirmation")ImpLogConfirmationVO objImpLogConfirmation) throws BusinessException;
	
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
	@WebMethod(operationName="getImpLogConfirmationByID", action="getImpLogConfirmationByID")
	public ImpLogConfirmationVO getImpLogConfirmationByID(@WebParam(name="id")Long id) throws BusinessException;
	
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
	@WebMethod(operationName="getAllImpLogConfirmations", action="getAllImpLogConfirmations")
	public List<ImpLogConfirmationVO> getAllImpLogConfirmations() throws BusinessException;
	
	/**
	 * Metodo: Obtiene todos los ImportLogItem relacionados con un registro de importación
	 * @param importLogId Long ID del registro de importacion
	 * @param RequestCollectionInfo requestCollInfo, parametros para la paginacion
	 * @return ImportLogItemResponse, lista con los ImportLogItem existentes, una lista vacia en caso que no exista ninguno.
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
	 * @author jnova
	 */
	@WebMethod(operationName="getImportLogItemsByImportLogId", action="getImportLogItemsByImportLogId")
	public ImportLogItemResponse getImportLogItemsByImportLogId(@WebParam(name="importLogId")Long importLogId, @WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	

	/**
	 * Metodo: Retorna un listado de import log items con sus confirmaciones
	 * CU Inv 04
	 * @param Long idImportLog, id del registro de importacion
	 * @throws BusinessException en caso de error al ejecutar guardar ImportLogItems
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jalopez
	 */
	@WebMethod(operationName="getImportLogItemsByImportLogWithConfirmations", action="getImportLogItemsByImportLogWithConfirmations")
	public ImportLogItemResponse getImportLogItemsByImportLogWithConfirmations(@WebParam(name="idImportLog")Long idImportLog,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;
	
	
	/**
	 * Metodo: Crear la inconsistencia del registro de importacion
	 * CU INV - 04
	 * @param pImpLogId Long, id del registro de importacion
	 * @param impLogItems List<ImportLogItemVO>, items del registro de importacion
	 * @param comment String, Comentario de la inconsistencia
	 * @param pUserId Long, usuario que creo el registro de inconsistencia
	 * @param Long incType, id con el tipo de inconsistencia
	 * @param country - Long identificador del país
	 * @throws BusinessException en caso de error al ejecutar guardar ImportLogItems
	 * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 * @author jalopez
	 */
	@WebMethod(operationName="reportInconsistentSerializedImpLogElementsAndByCountry", action="reportInconsistentSerializedImpLogElementsAndByCountry")
	public void reportInconsistentSerializedImpLogElementsAndByCountry(@WebParam(name="pImpLogId")Long pImpLogId, @WebParam(name="impLogItems")List<ImportLogItemVO> impLogItems, @WebParam(name="comment")String comment,@WebParam(name="pUserId")Long pUserId, @WebParam(name="incType")Long incType,@WebParam(name="country")Long country) throws BusinessException;
	
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
	@WebMethod(operationName="getMeasureUnitsByStatus", action="getMeasureUnitsByStatus")
	public List<MeasureUnitVO> getMeasureUnitsByStatus()throws BusinessException;
	
	/**
	 *  Metodo: Obtiene MeasureUnitVO activos
	 * @param requestCollInfo con la información de la paginación
	 * @return MeasureUnitResponse con el resultado de la consulta
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
	@WebMethod(operationName="getMeasureUnitsByStatusPage", action="getMeasureUnitsByStatusPage")
	public MeasureUnitResponse getMeasureUnitsByStatusPage(@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;
	
	/**
	 *  Metodo: Obtiene MeasureUnitVO en todos los estados
	 * @param requestCollInfo con la información de la paginación
	 * @param code - String código de la unidad de medida
	 * @return MeasureUnitResponse con el resultado de la consulta
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
	@WebMethod(operationName="getMeasureUnitsByAllStatusPage", action="getMeasureUnitsByAllStatusPage")
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(@WebParam(name="code")String code,@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * 
	 * @param modifyImportLogItemCriteria
	 * @param requestCollInfo
	 * @return
	 * @throws BusinessException
	 */
	@WebMethod(operationName="getImportLogItemsByCriteria", action="getImportLogItemsByCriteria")
	public ImportLogItemResponse getImportLogItemsByCriteria(@WebParam(name="modifyImportLogItemCriteria")ModifyImportLogItemDTO modifyImportLogItemCriteria,
			@WebParam(name="requestCollInfo")RequestCollectionInfo requestCollInfo) throws BusinessException;

	/**
	 * 
	 * @param importLogItemVO
	 * @throws BusinessException
	 */
	@WebMethod(operationName="updateQuantityItemImportLogNotSerializedElements", action="updateQuantityItemImportLogNotSerializedElements")
	public void updateQuantityItemImportLogNotSerializedElements(@WebParam(name="importLogItemVO")List<ImportLogItemVO> importLogItemVO) throws BusinessException;

	@WebMethod(operationName="confirmNotSerializedImportLogItemsCompany", action="confirmNotSerializedImportLogItemsCompany")
	public void confirmNotSerializedImportLogItemsCompany(
			@WebParam(name="impLogItems2Confirm")List<ImportLogItemVO> impLogItems2Confirm,
			@WebParam(name="userId")Long userId,
			@WebParam(name="logisticOperatorDealerId")Long logisticOperatorDealerId, 
			@WebParam(name="company")long company, 
			@WebParam(name="Cod_WHDestiny")String Cod_WHDestiny) throws BusinessException;

	
	@WebMethod(operationName="confirmNotSerializedImportLogItemsLogisticOperator", action="confirmNotSerializedImportLogItemsLogisticOperator")	
	public void confirmNotSerializedImportLogItemsLogisticOperator(
			@WebParam(name="impLogItems2Confirm")List<ImportLogItemVO> impLogItems2Confirm, 
			@WebParam(name="userId")Long userId, 
			@WebParam(name="logisticOperatorDealerId")Long logisticOperatorDealerId, 
			@WebParam(name="logisticOperator")long logisticOperator, 
			@WebParam(name="cod_WHDestiny")String cod_WHDestiny)
	throws BusinessException;

	
	/**
	 * Metodo: Obtiene todos los ImportLogItem relacionados con el registro de importación y que pueden ser o nó serializados
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
	@WebMethod(operationName="getImportLogItemsByImportLogIdAndIsSerialized", action="getImportLogItemsByImportLogIdAndIsSerialized")
	public ImportLogItemResponse getImportLogItemsByImportLogIdAndIsSerialized(
			@WebParam(name = "filterImportLogElements") ImportLogElementsFilterDTO filterImportLogElements, 
			@WebParam(name = "requestCollInfo") RequestCollectionInfo requestCollInfo) throws BusinessException;
		
}

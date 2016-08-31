/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.directv.sdii.ws.business.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigTiposServicioFacadeLocal;
import co.com.directv.sdii.facade.config.ServiceCategoryFacadeBeanLocal;
import co.com.directv.sdii.facade.config.ServiceTypesFacadeBeanLocal;
import co.com.directv.sdii.facade.core.tray.TrayWorkOrderManagmentFacadeLocal;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceCategoryVO;
import co.com.directv.sdii.model.vo.ServiceStatusVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.model.vo.ServiceVO;

/**
 * Servicio que expone todos los metodos referentes a la administracion de tipos
 * de servicios
 * 
 * Fecha de Creaci�n: 25/03/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0    
 */
@MTOM
@WebService()
@Stateless()
public class ConfigTiposServicioWS {

	@EJB
	private TrayWorkOrderManagmentFacadeLocal trayWOFacade;
	@EJB
	private ConfigTiposServicioFacadeLocal ejbRef;
	@EJB
	private ServiceTypesFacadeBeanLocal serviceTypesBean;
	@EJB
	private ServiceCategoryFacadeBeanLocal serviceCategoryBean;
	
	
	
	/**
	 * Obtiene un servicio por el id especificado
	 * @param id Id del servicio a consultar
	 * @return Servicio correspondiente al id especificado, nulo en caso que no se encuentre servicio por el id
	 * especificado 
	 * @throws BusinessException
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getServiceByID", action = "getServiceByID")
	public ServiceVO getServiceByID(@WebParam(name = "id") Long id)
			throws BusinessException {
		try {
			return ejbRef.getServiceByID(id);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Obtiene un servicio por el codigo especificado
	 * @param code Codigo del servicio a consultar
	 * @return Servicio correspondiente al codigo especificado, nulo en caso que no se encuentre un servicio
	 * con el código definido
	 * @throws BusinessException en caso de error al obtener el servicio por el código, 
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getServiceByCode", action = "getServiceByCode")
	public ServiceVO getServiceByCode(@WebParam(name = "code") String code)
			throws BusinessException {
		try {
			return ejbRef.getServiceByCode(code);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Obtiene un listado de servicios que contienen el nombre especificado
	 * @param name Nombre de los servicios 
	 * @return Listado de servicios que contienen el nombre especificado, lista vacia en el caso
	 * que no existan servicios con ese nombre.
	 * @throws BusinessException
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getServiceByName", action = "getServiceByName")
	public List<ServiceVO> getServiceByName(@WebParam(name = "name") String name)
			throws BusinessException {
		try {
			return ejbRef.getServiceByName(name);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Obtiene todos los servicios existentes en el sistema
	 * @return Listado de los servicios del sistema, una lista vacia en caso que no existan servicios en el
	 * sistema
	 * @throws BusinessException en caso de error al consultar los servicios del sistema
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getAllServices", action = "getAllServices")
	public List<ServiceVO> getAllServices() throws BusinessException {
		try {
			return ejbRef.getAll();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}
	
	@WebMethod(operationName = "getAllByServiceCodeOpening", action = "getAllByServiceCodeOpening")
	public List<ServiceVO> getAllByServiceCodeOpening(@WebParam(name = "serviceCodeOpening")boolean serviceCodeOpening) throws BusinessException {
    	return ejbRef.getAllByServiceCodeOpening(serviceCodeOpening);
    }
	
	@WebMethod(operationName = "getActiveServices", action = "getActiveServices")
	public List<ServiceVO> getActiveServices() throws BusinessException{
		return ejbRef.getActiveServices();
	}

	/**
	 * Obtiene los servicios correspondientes a un dealer especificado
	 * @param dealer Dealer a consultar los servicios que tiene asociados
	 * @return Listado de servicios correspondientes al dealer especificado, una lista vacia
	 * en caso que no existan servicios asociados al dealer especificado
	 * @throws BusinessException en caso de error al obtener la lista de servicios
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getServicesByDealer", action = "getServicesByDealer")
	public List<ServiceVO> getServicesByDealer(
			@WebParam(name = "dealer") DealerVO dealer)
			throws BusinessException {
		try {
			return ejbRef.getServicesByDealer(dealer);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Crea un servicio en el sistema
	 * @param obj Objeto con la informacion basica del servicio
	 * @throws BusinessException en caso de error al crear el servicio,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "createService", action = "createService")
	public void createService(@WebParam(name = "obj") ServiceVO obj, @WebParam(name = "userId") Long userId)
			throws BusinessException {
		try {
			ejbRef.createService(obj, userId);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Actualiza un servicio en el sistema
	 * @param obj Objeto con la informacion basica del servicio
	 * @throws BusinessException en caso de error al actualizar el servicio
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "updateService", action = "updateService")
	public void updateService(@WebParam(name = "obj") ServiceVO obj)
			throws BusinessException {
		try {
			ejbRef.updateService(obj);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Elimina un servicio en el sistema
	 * @param obj Objeto con la informacion basica del servicio
	 * @throws BusinessException en caso de error al borrar el servicio,
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "deleteService", action = "deleteService")
	public void deleteService(@WebParam(name = "obj") ServiceVO obj)
			throws BusinessException {
		try {
			ejbRef.deleteService(obj);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}

	/**
	 * Obtiene todos los tipos de servicios del sistema
	 * @return Listado con los tipos de servicio del sistema, una lista vacia en caso que no
	 * existan servicios en el sistema
	 * @throws BusinessException en caso de error al obtener los tipos de servicio
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getAllServiceType", action = "getAllServiceType")
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException {
		try {
			return serviceTypesBean.getAllServiceType();
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
            	throw (BusinessException)ex;
            }else{
                throw new BusinessException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex);
            }
		}
	}
	
	/**
	 * Obtiene todos las categorias de servicio por tipo de servicio especifico
	 * @param id Id del tipo de servicio
	 * @return Listado de las categorias de servicio con el tipo de servicio especificado, una lista vacia en
	 * caso que no existan servicios asociados a la categoría definida
	 * @throws BusinessException en caso de error al obtener los servicios por el identificador de la categoría
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
	 * <code>sdii_CODE_params_null_or_missed</code> En caso que no se encuentren los parámetros necesarios para la ejecución de la operación<br>
	 */
	@WebMethod(operationName = "getAllServiceCategoryByTypeId", action = "getAllServiceCategoryByTypeId")
	public List<ServiceCategoryVO> getAllServiceCategoryByTypeId(Long id) throws BusinessException{
		try {
			return serviceCategoryBean.getAllServiceCategoryByTypeId(id);
		} catch (BusinessException ex) {
			throw ex;
		} catch (Throwable ex) {
			throw new BusinessException(ex.getMessage());
		}
	}
	
	/**
	 * Metodo: Obtiene todos los estados del servicio
	 * @return Lista con los estados del servicio, una lista vacia en caso que no existan estados de servicio
	 * @throws BusinessException En caso de error
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
	@WebMethod(operationName = "getAllServicesStatus", action = "getAllServicesStatus")
	public List<ServiceStatusVO> getAllServicesStatus() throws BusinessException{
		return ejbRef.getAllServicesStatus();
	}
	
	@WebMethod(operationName = "getServicesByTypeId", action = "getServicesByTypeId")
	public List<ServiceVO> getServicesByTypeId(@WebParam(name = "typeId") Long typeId) throws BusinessException {
		List<Long> typesId=new ArrayList<Long>();
		typesId.add(typeId);
		List<ServiceCategoryVO> serviceCategoryVOs=trayWOFacade.getServiceCategoryByTypesId(typesId);
		List<Long> serviceCategoryIds=new ArrayList<Long>();
		for(ServiceCategoryVO it:serviceCategoryVOs){
			serviceCategoryIds.add(it.getId());
		}
		return trayWOFacade.getActiveServicesByServiceCategories(serviceCategoryIds);
	}
	
}

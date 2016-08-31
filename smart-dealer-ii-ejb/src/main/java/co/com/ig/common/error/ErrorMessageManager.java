/**
 * Creado 7/07/2010 9:52:44
 */
package co.com.ig.common.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import co.com.directv.sdii.common.enumerations.EJBRemoteJNDINameEnum;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.ig.common.error.ejb.business.ErrorMessageBusinessRemote;
import co.com.ig.common.error.utils.BeanContextUtils;
import co.com.ig.common.error.vo.ErrorMessageVO;
import co.com.ig.common.error.vo.ErrorReasonVO;

/**
 * provee el acceso a los componentes 
 * 
 * Fecha de Creación: 7/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public final class ErrorMessageManager {

	private static ErrorMessageManager mySelf;
	
	private final static Log log = LogFactory.getLog(ErrorMessageManager.class);
	
	private Map<String, ErrorMessageVO> errorMessageMapByErrorKey;
	
	private Map<String, ErrorReasonVO> errorReasonMapByReasonCode;
	
	private List<ErrorMessageVO> errorMessageList;
	
	private List<ErrorReasonVO> errorReasonList;
	
	private ErrorMessageBusinessRemote errorMessageBusiness;
	
	public static ErrorMessageManager getInstance(){
		if(mySelf == null){
			mySelf = new ErrorMessageManager();
		}
		return mySelf;
	}
	
	private ErrorMessageManager(){
		errorMessageMapByErrorKey = new HashMap<String, ErrorMessageVO>();
		errorReasonMapByReasonCode = new HashMap<String, ErrorReasonVO>();
		errorMessageList = new ArrayList<ErrorMessageVO>();
		errorReasonList = new ArrayList<ErrorReasonVO>();
		try {
			errorMessageBusiness = BeanContextUtils.getInstance().lookupEjb(ErrorMessageBusinessRemote.class, EJBRemoteJNDINameEnum.ErrorMessageBusinessLocal.getCodeEntity());
		} catch (PropertiesException e) {
			log.error("No se pudo obtener el bean ErrorMessageBusinessRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ErrorMessageBusinessRemote del contexto");
		}
		if(errorMessageBusiness == null){
			log.error("No se pudo obtener el bean ErrorMessageBusinessRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean ErrorMessageBusinessRemote del contexto");
		}
	}
	
	/**
	 * Metodo: Persiste la información de un nuevo mensaje de error
	 * @param errorMessage mensaje de error a ser persistido
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void createErrorMessage(ErrorMessageVO errorMessage){
		errorMessageBusiness.createErrorMessage(errorMessage);
		forceCacheReload();
	}
	
	/**
	 * Metodo: Actualiza la información de un mensaje de error en la persistencia
	 * @param errorMessage mensaje de error a ser actualizado
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void updateErrorMessage(ErrorMessageVO errorMessage){
		errorMessageBusiness.updateErrorMessage(errorMessage);
		forceCacheReload();
	}
	
	/**
	 * Metodo: borra un mensaje de error de la persistencia
	 * @param errorMessage mensaje a ser borrado, requiere la propiedad id
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public void deleteErrorMessage(ErrorMessageVO errorMessage){
		errorMessageBusiness.deleteErrorMessage(errorMessage);
		forceCacheReload();
	}
	
	/**
	 * Metodo: Obtiene la información de un mensaje de error dado el error key
	 * @param errorKey llave del error por el cual se consultará
	 * @return objeto con la información del mensaje de error encapsulada, nulo en caso que no encuentre
	 * el mensaje por la llave especificada
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public ErrorMessageVO getErrorMessageByErrorKey(String errorKey){
		if(errorMessageMapByErrorKey.isEmpty()){
			forceCacheReload();
		}
		ErrorMessageVO errorMessageVO = errorMessageMapByErrorKey.get(errorKey);
		if(errorMessageVO == null){
			log.error("No se encontró el errorMessage por el errorKey: \"" + errorKey +"\"");
			errorMessageVO = new ErrorMessageVO();
			errorMessageVO.setComponentId(1L);
			errorMessageVO.setErrorKey(errorKey);
			errorMessageVO.setErrorMessage("Error al consultar el mensaje de error:: consulte al administrador");
			errorMessageVO.setErrorCode("BL49");
		}
		return errorMessageVO;
	}
	
	/**
	 * Metodo: Obtiene una lista de mensajes de error
	 * @return lista de mensajes de error existentes en el sistema, una lista vacia en caso que no existan
	 * @throws DAOServiceException en caso de error al ejecutaro la operación
	 * @throws DAOSQLException en caso de error al ejecutaro la operación
	 * @author jjimenezh
	 */
	public List<ErrorMessageVO> getAllErrorMessages(){
		if(errorMessageList.isEmpty()){
			errorMessageList = errorMessageBusiness.getAllErrorMessages();
			loadErrorMessageMap(errorMessageList);
		}
		return errorMessageList;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		errorMessageBusiness.setSessionFactory(sessionFactory);
	}
	
	private void forceCacheReload(){
		errorMessageList = errorMessageBusiness.getAllErrorMessages();
		errorReasonList = errorMessageBusiness.getAllErrorReasons();
		loadErrorMessageMap(errorMessageList);
		loadErrorReasonMap(errorReasonList);
	}

	private void loadErrorMessageMap(List<ErrorMessageVO> anErrorMessageList) {
		errorMessageMapByErrorKey.clear();
		for (ErrorMessageVO errorMessageVO : anErrorMessageList) {
			errorMessageMapByErrorKey.put(errorMessageVO.getErrorKey(), errorMessageVO);
		}
	}
	
	private void loadErrorReasonMap(List<ErrorReasonVO> anErrorReasonList) {
		errorReasonMapByReasonCode.clear();
		for (ErrorReasonVO errorReasonVO : anErrorReasonList) {
			errorReasonMapByReasonCode.put(errorReasonVO.getReasonCode(), errorReasonVO);
		}
	}
	
	/**
	 * Metodo: Consulta en el arreglo cargado en cache una ErrorReason 
	 * a partir de su codigo
	 * @param reasonCode
	 * @return ErrorReasonVO ErrorReason encontrada
	 * @author jforero 11/09/2010
	 */
	public ErrorReasonVO getErrorReasonByReasonCode(String reasonCode){
		if(errorReasonMapByReasonCode.isEmpty()){
			forceCacheReload();
		}
		ErrorReasonVO errorReasonVo = errorReasonMapByReasonCode.get(reasonCode);
		if(errorReasonVo == null){
			log.error("No se encontró el ErrorReason por el reasonCode: \"" + reasonCode +"\"");
			errorReasonVo = new ErrorReasonVO();
			errorReasonVo.setReasonCode(reasonCode);
			errorReasonVo.setId(1L);
			errorReasonVo.setReasonName("Error al consultar el ErrorReason:: consulte al administrador");
		}
		return errorReasonVo;
	}
	
	
}

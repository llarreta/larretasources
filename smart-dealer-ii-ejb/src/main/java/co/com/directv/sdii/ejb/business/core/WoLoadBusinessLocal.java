/**
 * Creado 2/11/2010 14:18:23
 */
package co.com.directv.sdii.ejb.business.core;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.WoLoad;
import co.com.directv.sdii.model.vo.WoLoadDetailVO;
import co.com.directv.sdii.model.vo.WoLoadVO;

/**
 * Encapsula la lógica encargada de registrar los resultados del cargue de Work Orders
 * 
 * Fecha de Creación: 2/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface WoLoadBusinessLocal {

	
	/**
	 * Registra la información del cargue de una Work order
	 * 
	 * @param woLoad información del cargue a ser generada
	 * @return
	 * @throws BusinessException
	 */
	public WoLoadVO createWoLoad(WoLoadVO woLoad) throws BusinessException;
	
	/**
	 * Registra la información del cargue de una Work order
	 * @param woCount número de work orders a ser cargadas
	 * @param countryCode Código del país de donde se obtuvieron las Work Orders
	 * @return Objeto persistido con la información del cargue
	 * @throws BusinessException en caso de error en la creación
	 */
	public WoLoadVO createWoLoad(Long woCount, String countryCode) throws BusinessException;
	
	
	/**
	 * Actualiza la información del cargue de una Work order
	 * 
	 * @param woLoad información del cargue a ser generada
	 * @return
	 * @throws BusinessException
	 */
	public WoLoadVO updateWoLoad(WoLoadVO woLoad) throws BusinessException;
	
	/**
	 * Registra la información del cargue de una Work order
	 * 
	 * @param woLoad información del cargue a ser generada
	 * @return
	 * @throws BusinessException
	 */
	public WoLoadDetailVO createWoLoadDetail(WoLoadDetailVO woLoadDetail) throws BusinessException;
	
	
	/**
	 * Envía el correo con los resultados del cargue de work orders
	 * @param woLoad
	 * @param loadDetail
	 * @throws BusinessException
	 */
	public void sendWoLoadMail(WoLoadVO woLoad, List<WoLoadDetailVO> loadDetail)throws BusinessException;
	
	/** Correo que se envia cuando ocurre un error en cualquier metodo de la clase
	 * TrayManagementBusinessHelper
	 * 
	 * @param woCode
	 * @param countryId
	 * @param countryName
	 * @param errorCode
	 * @param attDate
	 * @param message
	 * @param processCode
	 * @param processName
	 */
	public void sendTrayManagementErrorMail(String woCode,Long countryId, String countryName, String errorCode, Date attDate, String message, String processCode, String processName, String idDoc, User ... user);

	/**
	 * 
	 * Metodo: Obtiene el ultimo WO LOAD
	 * @param String countryCode
	 * @return WoLoad
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author jalopez
	 */
	 public WoLoad getLastWoLoad(String countryCode) throws BusinessException;
}
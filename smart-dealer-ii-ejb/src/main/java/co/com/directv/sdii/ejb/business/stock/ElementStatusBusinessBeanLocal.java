package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ElementStatus;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.vo.ElementStatusVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad ElementStatus.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ElementStatusBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto ElementStatusVO
	 * @param obj objeto que encapsula la información de un ElementStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createElementStatus(ElementStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un ElementStatusVO
	 * @param obj objeto que encapsula la información de un ElementStatusVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateElementStatus(ElementStatusVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un ElementStatusVO
	 * @param obj información del ElementStatusVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteElementStatus(ElementStatusVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un ElementStatusVO por su identificador
	 * @param id identificador del ElementStatusVO a ser consultado
	 * @return objeto con la información del ElementStatusVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public ElementStatusVO getElementStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los ElementStatusVO almacenados en la persistencia
	 * @return Lista con los ElementStatusVO existentes, una lista vacia en caso que no existan ElementStatusVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<ElementStatusVO> getAllElementStatuss() throws BusinessException;

	/**
	 * Obtiene el estado de un elemento segun la bodega a la que se movera
	 * @param warehouseId
	 * @return
	 * @throws BusinessException 
	 */
	public ElementStatus estadoElementoSegunBodegaDestino(Warehouse warehouse) throws BusinessException;

}

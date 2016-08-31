package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.model.vo.TechnologyVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad CauseAdjustment.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TechnologyBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto TechnologyVO
	 * @param obj objeto que encapsula la información de un TechnologyVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createTechnology(TechnologyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la informacion de tecnologias que sean IRD
	 * @return 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author aharker
	 */
	public List<TechnologyVO> getAllIRDTechnologies() throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un TechnologyVO
	 * @param obj objeto que encapsula la información de un TechnologyVO
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateTechnology(TechnologyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un TechnologyVO
	 * @param obj información del TechnologyVO a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteTechnology(TechnologyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un TechnologyVO por su identificador
	 * @param id identificador del TechnologyVO a ser consultado
	 * @return objeto con la información del TechnologyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public TechnologyVO getTechnologyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los TechnologyVO almacenados en la persistencia
	 * @return Lista con los TechnologyVO existentes, una lista vacia en caso que no existan TechnologyVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<TechnologyVO> getAllTechnologies() throws BusinessException;

	/**
	 * Metodo: Obtiene la información de todos los TechnologyVO almacenados en la persistencia
	 * @return Lista con los TechnologyVO existentes, una lista vacia en caso que no existan TechnologyVO en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<TechnologyVO> getActiveTechnologies() throws BusinessException;
	
	/**
	 * Metodo: obtiene una causa de ajuste dado el código de la misma
	 * @param code código de la causa de ajuste
	 * @return Causa de ajuste dado el código, nulo en caso que no se encuentre
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public TechnologyVO getTechnologyByCode(String code)throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la lista de tecnologias asociadas a una SO para desplegar en la bandeja
	 * @param siId
	 * @return List<TechnologyVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<TechnologyVO> getShippingorderTechnology(Long soId) throws BusinessException;
	
}

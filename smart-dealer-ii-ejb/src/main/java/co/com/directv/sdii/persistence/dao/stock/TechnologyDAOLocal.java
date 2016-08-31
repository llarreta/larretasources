package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Technology;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad Technology
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TechnologyDAOLocal {

	/**
	 * Metodo:  persiste la información de un Technology
	 * @param obj objeto que encapsula la información de un Technology
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createTechnology(Technology obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un Technology
	 * @param obj objeto que encapsula la información de un Technology
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateTechnology(Technology obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un Technology
	 * @param obj información del Technology a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteTechnology(Technology obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un Technology por su identificador
	 * @param id identificador del Technology a ser consultado
	 * @return objeto con la información del Technology dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public Technology getTechnologyByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Technology almacenados en la persistencia
	 * @return Lista con los Technology existentes, una lista vacia en caso que no existan Technology en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<Technology> getAllTechnologies() throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene la informacion de tecnologias que sean IRD
	 * @return 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author aharker
	 */
	public List<Technology> getAllIRDTechnologies() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los Technology almacenados en la persistencia
	 * @return Lista con los Technology existentes, una lista vacia en caso que no existan Technology en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<Technology> getActiveTechnologies() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene una tecnologia por código
	 * @param code código de la tecnologia
	 * @return tecnologia con el código especificado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public Technology getTechnologyByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * Metodo: Obtiene una tecnología por codigo de IBS
	 * @param code codigo ibs de la tecnologia
	 * @return tecnologia con el codigo ibs especificado
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jnova
	 */
	public Technology getTechnologyByIbsCode(String code)throws DAOServiceException, DAOSQLException;
}
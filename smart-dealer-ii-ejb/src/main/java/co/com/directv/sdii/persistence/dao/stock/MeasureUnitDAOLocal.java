package co.com.directv.sdii.persistence.dao.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;

/**
 * 
 * Interface Local para la gestión del CRUD de la
 * Entidad MeasureUnit
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MeasureUnitDAOLocal {

	/**
	 * Metodo:  persiste la información de un MeasureUnit
	 * @param obj objeto que encapsula la información de un MeasureUnit
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: actualiza la información de un MeasureUnit
	 * @param obj objeto que encapsula la información de un MeasureUnit
	 * @throws DAOServiceException en caso de error al ejecutar la operación
	 * @throws DAOSQLException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MeasureUnit
	 * @param obj información del MeasureUnit a ser borrado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteMeasureUnit(MeasureUnit obj) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de un MeasureUnit por su identificador
	 * @param id identificador del MeasureUnit a ser consultado
	 * @return objeto con la información del MeasureUnit dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public MeasureUnit getMeasureUnitByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene la información de todos los MeasureUnit almacenados en la persistencia
	 * @return Lista con los MeasureUnit existentes, una lista vacia en caso que no existan MeasureUnit en el sistema
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public List<MeasureUnit> getAllMeasureUnits() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param code código del objeto a ser consultado
	 * @return Unidad de medida con el código especificado, nulo en caso que no exista la
	 * unidad con ese código
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public MeasureUnit getMeasureUnitByCode(String code)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene un objeto por el nombre especificado
	 * @param code nombre del objeto a ser consultado
	 * @return Unidad de medida con el nombre especificado, nulo en caso que no exista la
	 * unidad con ese nombre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author gfandino
	 */
	public MeasureUnit getMeasureUnitByName(String name)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param status String estado a consultar
	 * @return List<MeasureUnit> correspondiente al estado
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 */
	public List<MeasureUnit> getMeasureUnitsByStatus(String status)throws DAOServiceException, DAOSQLException;
	
	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param status - String estado a consultar
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return MeasureUnitResponse con la información de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 */
	public MeasureUnitResponse getMeasureUnitsByStatus(String status,RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param status - String estado a consultar
	 * @param requestCollInfo - RequestCollectionInfo con la información de la paginación
	 * @return MeasureUnitResponse con la información de la consulta
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la consulta por estado
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 */
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(
			String codeEntity, RequestCollectionInfo requestCollInfo)throws DAOServiceException, DAOSQLException;



}
package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Program;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Program
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ProgramDAOLocal {
	
	/**
     * Crea un Program en el sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createProgram(Program obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un program con el id especificado
     * @param idProgram - Id del programa a consultar
     * @return - Objeto con la informacion basica del Programa consultado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Program getProgramByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un program especificado
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateProgram(Program obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un program del sistema
     * @param obj - Objeto con la informacion basica del programa
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteProgram(Program obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los programas del sistema
     * @return - Listado con los programas que existen en el sistema
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Program> getAll() throws DAOServiceException, DAOSQLException;

    
    /**
     * Obtiene todos los programs por nombre y dealer especifico
     * @param dealerId - Id de la compa�ia 
     * @param name - Nombre del programa
     * @return - Listado con los programas que pertenecen a un dealer y nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    public List<Object[]> getProgramsByNameAndDealerId(Long dealerId, String name) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los programas de un dealer especifico, adicional a esto, se retorna cada una de las cargas
     * que posee cada programa
     * @param - Id de la compa�ia
     * @return - Listado con los programas pertenecientes a una compa�ia especifica
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    public List<Object[]> getProgramsByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene un listado de programas con el estado especificado
     * @param statusId - Id del status de un programa
     * @return Listado de programas con el estado especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Program> getProgramsByStatusId(Long statusId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene un programa con el codigo especificado
     * @param programCode - Codigo del programa a consultar
     * @return Programa con el codigo especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Program getProgramsByCode(String programCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los programs por nombre  
     * @param name - Nombre del programa
     * @return - Listado con los programas con el nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jcasas
     */
    public List<Program> getProgramsByName(String name) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los programs por nombre exacto
     * @param name - Nombre del programa
     * @return - Listado con los programas con el nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    public List<Program> getProgramsByNameExact(String name) throws DAOServiceException, DAOSQLException;
    
    
    /**
     * Obtiene un listado de programas asociados a un dealer en la tabla wo_assignments
     * @param - statusId Estado en el que va a buscar el programa - si va nulo consulta todos
     * @return Listado de programas con el estado especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Program> getProgramsRegisteredByDealerId( Long dealerId ,Long statusId) throws DAOServiceException, DAOSQLException;
    

    /**
     * Obtiene el total de programas asociados a un dealer y a un programa en la tabla wo_assignments
     * @return Listado de programas con el estado especificado
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Integer getTotalProgramsByDealerAndProgram( Long dealerId,Long programId ) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta los programas por estado y por dealer, si el dealer va nulo, no lo tiene en cuenta en el filtro
     * @param dealerId
     * @param statusCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<Program> getProgramsByDealerIdAndStatusCode( Long dealerId ,String statusCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta el maximo id de la tabla de programas
     * @return maximo id de la tabla programas
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public Long getMaxProgramID() throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta los programas por estado y por dealers, si los dealers van nulo, no lo tiene en cuenta en el filtro
     * @param List<Long> dealerIds
     * @param statusCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public List<Program> getProgramsByDealerIdsAndStatusCode(List<Long> dealerIds ,String statusCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene todos los programs por nombre exacto y por dealer
     * @param name - Nombre del programa
     * @param dealerId
     * @return - Listado con los programas con el nombre especifico 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author jnova
     */
    public List<Program> getProgramsByNameExactAndDealerId(String name,Long dealerId) throws DAOServiceException, DAOSQLException;
    
}

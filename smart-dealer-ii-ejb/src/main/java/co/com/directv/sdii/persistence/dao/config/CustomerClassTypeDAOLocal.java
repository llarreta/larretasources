package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.model.vo.CustomerTypeVO;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad CustomerCodeType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CustomerClassTypeDAOLocal {
	
	/**
     * Crea un CustomerCodeType en el sistema
     * @param obj - CustomerCodeType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createCustomerClassType(CustomerClassType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un CustomerClassType con el id especificado
     * @param id - Long
     * @return - CustomerClassType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public CustomerClassType getCustomerClassTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un CustomerClassType especificado
     * @param obj - CustomerClassType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateCustomerClassType(CustomerClassType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un CustomerClassType del sistema
     * @param obj - CustomerClassType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteCustomerClassType(CustomerClassType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los CustomerClassTypes del sistema
     * @return - List<CustomerClassType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<CustomerClassType> getAll(Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta CutsomerCodeType por su nombre
     * @param name - Stirng
     * @return List<CustomerClassType>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<CustomerClassType> getCustomerClassTypeByNameType(String name, Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un nombre de clase de cliente
     * @param name nombre de la clase de cliente
     * @param countryId pais en el cual se realizara la busqueda
     * @return 
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
    public List<CustomerClassType> getCustomerClassTypeByNameClass(String name , Long countryId ) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un nombre de clase de cliente y el nombre de un tipo de cliente
     * @param nameType nombre del tipo de cliente
     * @param nameClass nombre de la clase de cliente
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
    public List<CustomerClassType> getCustomerClassTypeByNameTypeAndNameClass(String nameType, String nameClass, Long countryId) throws DAOServiceException, DAOSQLException;
    /**
     * Metodo: Consulta CutsomerCodeType por su código
     * @param code - String
     * @return List<CustomerClassType>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<CustomerClassType> getCustomerClassTypeByCodeType(String code, Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Consulta CutsomerCodeType por Clase de Cliente
     * @param custClassId - Long
     * @return List<CustomerClassType>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<CustomerClassType> getCustomerClassTypeByCustomerClassId(Long custClassId) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un codigo de clase de cliente y el codigo de un tipo de cliente
     * @param typeCode codigo del tipo de cliente
     * @param classCode codigo de la clase de cliente
     * @param countryId pais en el cual se realiza la consulta
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author Aharker
     */
	public CustomerClassType getCustomerClassTypeByCodeTypeAndClassCode(String typeCode, String classCode, Long countryId) throws DAOServiceException, DAOSQLException ;

	/**
	 * Metodo que busca las cambinaciones de tipo de cliente con clase de cliente dado un codigo de clase de cliente
	 * @param code codigo de la clase de cliente
	 * @param countryId pais en el cual se realiza la consulta
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public List<CustomerClassType> getCustomerClassTypeByCodeClass(String code, Long countryId) throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo que busca la cambinacion de tipo de cliente con clase de cliente dado su id
	 * @param id id de busqueda de la paraja de clase y tipo de cliente
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public CustomerType getCustomerTypeByID(Long id) throws DAOServiceException, DAOSQLException;
	
}

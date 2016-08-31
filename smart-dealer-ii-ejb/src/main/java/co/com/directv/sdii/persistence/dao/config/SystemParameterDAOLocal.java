package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SystemParameter;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad SystemParameter
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SystemParameterDAOLocal {
	
	/**
     * Crea un parametro del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un systemparameter con el id especificado
     * @param id - Long
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public SystemParameter getSystemParameterByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un SystemParameter de acuerdo al codigo especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    //public SystemParameter getSystemParameterByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un systemparameter especificado
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un systemparameter del sistema
     * @param obj - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteSystemParameter(SystemParameter obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los systemparameters del sistema
     * @return - List<SystemParameter>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<SystemParameter> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de los parámetros del sistema regionalizados
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de parámetros del sistema regionalizados
     * @throws BusinessException en caso de error al tratar de obtener los parámetros del sistema
     * @author jjimenezh Agergado por control de cambios 2010-04-26
     */
    public List<SystemParameter> getAllSystemParametersByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un SystemParameter de acuerdo al nombre especificado
     * @param code - String
     * @return - SystemParameter
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<SystemParameter> getSystemParameterByNameAndCountryId(String name, Long countryId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo: Obtiene Id de la Entidad por el Código de la entidad
     * @param className - String
     * @param codeEntity - String
     * @param propertyName - String
     * @return Long
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Long getIdEntityByCodeEntity(String className,String codeEntity, String propertyName)throws DAOServiceException, DAOSQLException;

	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @param countryId - Long
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public SystemParameter getSysParamByCodeAndCountryId(String parameterCode, Long countryId)throws DAOServiceException, DAOSQLException;

	
	/**
	 * Metodo: Obtiene SystemParameter por Código de parámetro y país
	 * @param parameterCode - String
	 * @return SystemParameter
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<SystemParameter> getSysParamsByCode(String parameterCode)throws DAOServiceException, DAOSQLException;
	
	public SystemParameter getSysParamsByCodeAndCountryIdNull(String parameterCode)	throws DAOServiceException, DAOSQLException;
	
	public SystemParameter getSysParamByCodeAndCountryCode(String parameterCode, String countryCode) throws DAOServiceException, DAOSQLException ;
}

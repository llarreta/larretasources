package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ParameterType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ParameterType
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ParameterTypeDAOLocal {
	
	 /**
     * Crea un parametro en el sistema
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un parametertype con el id especificado
     * @param id - Long
     * @return - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ParameterType getParameterTypeByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un parametertype especificado
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un parametertype del sistema
     * @param obj - ParameterType
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteParameterType(ParameterType obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los parametertypes del sistema
     * @return - List<ParameterType>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ParameterType> getAll() throws DAOServiceException, DAOSQLException;

}

package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ResponsibleArea;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ResponsibleArea
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ResponsibleAreaDAOLocal {
	
	/**
     * Crea una ResponsibleArea en el sistema
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
	public void createResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException;

	 /**
     * Obtiene un responsiblearea con el id especificado
     * @param id - Long
     * @return - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ResponsibleArea getResponsibleAreaByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un responsiblearea especificado
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un responsiblearea del sistema
     * @param obj - ResponsibleArea
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteResponsibleArea(ResponsibleArea obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los responsibleareas del sistema
     * @return - List<ResponsibleArea>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ResponsibleArea> getAll() throws DAOServiceException, DAOSQLException;

}

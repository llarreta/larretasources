package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Ibs6Status;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Ibs6Status
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface Ibs6StatusDAOLocal {
	
	/**
     * Crea un Ibs6Status en el sistema
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un Ibs6Status con el id especificado
     * @param id - Long
     * @return - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Ibs6Status getIbs6StatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un Ibs6Status especificado
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un Ibs6Status del sistema
     * @param obj - Ibs6Status
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteIbs6Status(Ibs6Status obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los Ibs6Statuss del sistema
     * @return - List<Ibs6Status>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Ibs6Status> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Consulta un estado de ibs por codigo de ibs
     * @param ibsStateCode
     * @return
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public Ibs6Status getIbs6StatusByIbsStateCode(String ibsStateCode) throws DAOServiceException, DAOSQLException;

}

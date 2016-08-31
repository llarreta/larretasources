package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ProgramStatus;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad ProgramStatus
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ProgramStatusDAOLocal {
	
	 /**
     * Crea un ProgramStatus en el sistema
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un programstatus con el id especificado
     * @param id - Long
     * @return - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public ProgramStatus getProgramStatusByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Actualiza un programstatus especificado
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Elimina un programstatus del sistema
     * @param obj - ProgramStatus
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteProgramStatus(ProgramStatus obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene todos los programstatuss del sistema
     * @return - List<ProgramStatus>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<ProgramStatus> getAll() throws DAOServiceException, DAOSQLException;

}

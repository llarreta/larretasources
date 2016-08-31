package co.com.directv.sdii.persistence.dao.config;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AuditExternalSystemSchedule;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad AuditExternalSystemSchedule
 * 
 * Fecha de Creacion: Mar 09, 2012
 * @author csierra <a href="mailto:csierra@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AuditExternalSystemScheduleDAOLocal {
	
	 /**
     * Crea una AuditExternalSystemSchedule en el sistema
     * @param obj - AuditExternalSystemSchedule
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createAuditExternalSystemSchedule(AuditExternalSystemSchedule obj) throws DAOServiceException, DAOSQLException;

    /**
     * Obtiene un AuditExternalSystemSchedule con el id especificado
     * @param id - Long
     * @return - AuditExternalSystemSchedule
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public AuditExternalSystemSchedule getAuditExternalSystemScheduleByID(Long id) throws DAOServiceException, DAOSQLException;
    
}

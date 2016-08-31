package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllianceFile;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad AllianceFile
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AllianceFileDAOLocal {
	
    /**
     * Permite crear ALLIANCE_FILE
     * @param obj -  AllianceFile
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void createAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar ALLIANCE_FILE por ID
     * @param id - Long
     * @return AllianceFile
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public AllianceFile getAllianceFileByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Permite actualizar ALLIANCE_FILE
     * @param obj - AllianceFile
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void updateAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite eliminar ALLIANCE_FILE
     * @param obj - AllianceFile
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void deleteAllianceFile(AllianceFile obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar todos los ALLIANCE_FILE
     * @return List<AllianceFile>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<AllianceFile> getAll() throws DAOServiceException, DAOSQLException;

	/**
	 * Permite consultar ALLIANCE_FILE por ALLIANCE ID
	 * @param id - Long
	 * @return AllianceFile
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author gfandino
	 */
	public AllianceFile getAllianceFileByAllianceID(Long id)throws DAOServiceException, DAOSQLException;

}

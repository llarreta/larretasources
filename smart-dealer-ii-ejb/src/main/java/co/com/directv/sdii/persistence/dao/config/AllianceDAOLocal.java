package co.com.directv.sdii.persistence.dao.config;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Alliance;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Alliance
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@Local
public interface AllianceDAOLocal {
	
    /**
     * Permite crear ALLIANCE
     * @param obj - Alliance
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void createAlliance(Alliance obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar ALLIANCE por ID
     * @param id - Long id
     * @return Alliance
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public Alliance getAllianceByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Permite actualizar ALLIANCE
     * @param obj - Alliance
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void updateAlliance(Alliance obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite eliminar ALLIANCE
     * @param obj - Alliance
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public void deleteAlliance(Alliance obj) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar todas las ALLIANCE
     * @return List<Alliance>
     * @throws DAOServiceException
     * @throws DAOSQLException
     * @author gfandino
     */
    public List<Alliance> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Lista de allianzas regionalizadas
     * @param countryId identificador del país por el que se realizará el filtro
     * @return Lista de allianzas dado el país
     * @throws BusinessException en caso de error al tratar de consultar las alianzas
     * @author jjimenezh Agregado por control de cambios 2010-04-26
     */
    public List<Alliance> getAllAllianceByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;


    /**
     * Permite consultar ALLIANCE por CODE
     * @param code - String
     * @return Alliance
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public Alliance getAllianceByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar ALLIANCE por NAME
     * @param name - String
     * @return List<Alliance>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Alliance> getAllianceByName(String name) throws DAOServiceException, DAOSQLException;

    /**
     * Permite consultar ALLIANCE por rango de fechas
     * @param init - Date
     * @param end - Date
     * @return List<Alliance>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public List<Alliance> getAllianceByDate(Date init, Date end) throws DAOServiceException, DAOSQLException;

}

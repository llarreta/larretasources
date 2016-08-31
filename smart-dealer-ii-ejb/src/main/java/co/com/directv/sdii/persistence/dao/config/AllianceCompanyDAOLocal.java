package co.com.directv.sdii.persistence.dao.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.AllianceCompany;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad AllianceCompany
 * 
 * Fecha de Creacion: Mar 23, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AllianceCompanyDAOLocal {
	
    /**
     * Metodo: Permite crear una AllianceCompany
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author
     */
    public void createAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Permite consultar una AllianceComapany por su ID
     * @param id - Long
     * @return AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author
     */
    public AllianceCompany getAllianceCompanyByID(Long id) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Permite consultar una AllianceCompany por su código
     * @param code - String
     * @return AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public AllianceCompany getAllianceCompanyByCode(String code) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Permite actualizar una AllianceCompanu
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author
     */
    public void updateAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Permite eliminar una AllianceCompany 
     * @param obj - AllianceCompany
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author
     */
    public void deleteAllianceCompany(AllianceCompany obj) throws DAOServiceException, DAOSQLException;

    /**
     * Metodo: Permite consultar todas las AllianceCompany
     * @return List<AllianceCompany>
     * @throws DAOServiceException
     * @throws DAOSQLException 
     * @author
     */
    public List<AllianceCompany> getAll() throws DAOServiceException, DAOSQLException;
    
    /**
     * Obtiene una lista de allianzas con compañias regionalizada por país
     * @param countryId identificador del país por el que se filtrará
     * @return lista de compañías en alianza regionaizadas
     * @throws BusinessException en caso de error al tratar de obtener la lista
     * @author jjimenezh Agregado por control de cambios 2010-04-26
     */
    public List<AllianceCompany> getAllAllianceCompanyByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

}

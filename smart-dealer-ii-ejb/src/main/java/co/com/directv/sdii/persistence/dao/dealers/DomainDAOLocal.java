package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Domain;

/**
 * 
 * Interface Local para la gestion del DAO de la
 * Entidad Domain
 * 
 * 
 * @author waguilera
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DomainDAOLocal {

	/**
	 * Método encargado de consultar un dominio para realizar procesos de Homologación
	 * @param domainName
	 * @param domainValue
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public Domain getDomainValue(String domainName, String domainValue, Long countryId) throws DAOServiceException, DAOSQLException;
	
	public Domain getDomainValue(String domainName, String domainCode, String countryCode) throws DAOServiceException, DAOSQLException;
    
    /**
     * Método encargado de consultar una lista dominio para realizar procesos de Homologación
     * @param domainName 
     * @param domainValue 
     * @param countryId 
     * @return 
     * @throws DAOServiceException 
     * @throws DAOSQLException <tipo> <descripcion>
     * @author
     */
    public List<Domain> getDomainByDomainNameDomainValueCountryId(String domainName, Long countryId) throws DAOServiceException, DAOSQLException;
	
	
}
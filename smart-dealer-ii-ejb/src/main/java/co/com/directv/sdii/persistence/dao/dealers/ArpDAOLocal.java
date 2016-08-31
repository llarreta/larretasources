package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Arp;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Arp
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ArpDAOLocal {

	/**
	 * 
	 * @param code
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Arp getArpByCode(String code) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public Arp getArpByID(Long id) throws DAOServiceException, DAOSQLException;
	
	/**
	 * 
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	public List<Arp> getAllArp() throws DAOServiceException, DAOSQLException;
	
	/**
	 * Obtiene la lista regionalizada de las arp de cada país
	 * @param countryId identificador del país
	 * @return lista de las arp regionalizadas
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * de arps regionalizadas
	 * @author jjimenezh agregado por control de cambios 2010-04-23
	 */
	public List<Arp> getAllArpByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;

}
/**
 * Creado 25/11/2010 18:17:49
 */
package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SdiiTimeZone;

/**
 * Objeto de la capa de acceso a datos para la consulta de las zonas horarias
 * 
 * Fecha de Creación: 25/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface SdiiTimeZoneDAOLocal {

	
	/**
	 * Metodo: Obtiene la información de la zona horaria por id
	 * @param id identificador de la zona horaria
	 * @return Zona horaria dado el identificador especificado
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public SdiiTimeZone getTimeZoneById(Long id) throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo: Obtiene las zonas horarias registradas en el sistema
	 * @return Lista con las zonas horarias
	 * @throws BusinessException En caso de error al consultar
	 * @author jjimenezh
	 */
	public List<SdiiTimeZone> getAllTimeZones() throws DAOServiceException, DAOSQLException ;
	
	/**
	 * Metodo: Obtiene las zonas horarias dado el identificador del país
	 * @param countryId identificador del país a consultar
	 * @return Lista con las zonas horarias
	 * @throws BusinessException En caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public List<SdiiTimeZone> getTimeZonesByCountryId(Long countryId) throws DAOServiceException, DAOSQLException ;
}

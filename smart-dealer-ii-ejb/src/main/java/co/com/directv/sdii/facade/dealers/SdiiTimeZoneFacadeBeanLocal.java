package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.SdiiTimeZoneVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad SdiiTimeZone.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Nov 25, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SdiiTimeZoneFacadeBeanLocal {

	/**
	 * Metodo: Obtiene la información de la zona horaria por id
	 * @param id identificador de la zona horaria
	 * @return Zona horaria dado el identificador especificado
	 * @throws BusinessException En caso de error
	 * @author jjimenezh
	 */
	public SdiiTimeZoneVO getTimeZoneById(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene las zonas horarias registradas en el sistema
	 * @return Lista con las zonas horarias
	 * @throws BusinessException En caso de error al consultar
	 * @author jjimenezh
	 */
	public List<SdiiTimeZoneVO> getAllTimeZones() throws BusinessException;
	
	/**
	 * Metodo: Obtiene las zonas horarias dado el identificador del país
	 * @param countryId identificador del país a consultar
	 * @return Lista con las zonas horarias
	 * @throws BusinessException En caso de error al ejecutar la consulta
	 * @author jjimenezh
	 */
	public List<SdiiTimeZoneVO> getTimeZonesByCountryId(Long countryId) throws BusinessException;
	
	/**
	 * Método encargado de generar la fecha del sistema con respecto
	 * al identificador del usuario que se recibe como parametro
	 * @param userId
	 * @return
	 * @throws BusinessException
	 * @author waguilera
	 */
	public UserVO getCurrentDateByUserId(Long userId) throws BusinessException;
	    
}

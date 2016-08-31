package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ZoneTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de ZoneTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ZoneTypesFacadeBeanLocal {

	/**
	 * Metodo: <Descripcion>
	 * @param code
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public ZoneTypeVO getZoneTypesByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public ZoneTypeVO getZoneTypesByID(Long id) throws BusinessException;
	
	/**
     * Metodo: obtiene todos los tipos de zona
     * @return lista con los tipos de zona
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
	public List<ZoneTypeVO> getAllZoneTypes() throws BusinessException;
}

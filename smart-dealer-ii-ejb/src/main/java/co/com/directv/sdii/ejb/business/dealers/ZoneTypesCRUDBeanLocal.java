package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ZoneTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad ZoneTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ZoneTypesCRUDBeanLocal {

	public ZoneTypeVO getZoneTypesByCode(String code) throws BusinessException;
	
	public ZoneTypeVO getZoneTypesByID(Long id) throws BusinessException;
	
	public List<ZoneTypeVO> getAllZoneTypes() throws BusinessException;
}

package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ChannelTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad ChannelTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ChannelTypesCRUDBeanLocal {

	public ChannelTypeVO getChannelTypesByCode(String code) throws BusinessException;
	
	public ChannelTypeVO getChannelTypesByID(Long id) throws BusinessException;
	
	public List<ChannelTypeVO> getAllChannelTypes() throws BusinessException;
}

package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ArpVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad Arp.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ArpCRUDBeanLocal {

	public ArpVO getArpByCode(String code) throws BusinessException;
	
	public ArpVO getArpByID(Long id) throws BusinessException;
	
	public List<ArpVO> getAllArp() throws BusinessException;
	
	/**
	 * Obtiene la lista regionalizada de las arp de cada país
	 * @param countryId identificador del país
	 * @return lista de las arp regionalizadas
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * de arps regionalizadas
	 * @author jjimenezh agregado por control de cambios 2010-04-23
	 */
	public List<ArpVO> getAllArpByCountryId(Long countryId) throws BusinessException;
}

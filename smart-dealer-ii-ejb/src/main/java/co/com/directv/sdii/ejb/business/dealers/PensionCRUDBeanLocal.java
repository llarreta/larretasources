package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.PensionVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad Pension.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PensionCRUDBeanLocal {

	public PensionVO getPensionByCode(String code) throws BusinessException;
	
	public PensionVO getPensionByID(Long id) throws BusinessException;
	
	public List<PensionVO> getAllPension() throws BusinessException;
	
	/**
	 * Obtiene las pensiones dado el identificador del país
	 * @param countryId identificador del país
	 * @return lista con objetos de pensión por el país especificado
	 * @throws BusinessException en caso de error al tratar de consultar las pensiones
	 * regionalizadas
	 * @author jjimenezh Se agrega por control de cambios 2010-04-23
	 */
	public List<PensionVO> getAllPensionByCountryId(Long countryId) throws BusinessException;
}

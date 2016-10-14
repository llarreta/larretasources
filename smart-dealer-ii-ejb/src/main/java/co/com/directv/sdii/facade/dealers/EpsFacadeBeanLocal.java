package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EpsVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de Eps
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EpsFacadeBeanLocal {

	/**
	 * 
	 * Obtiene todas las EPSs
	 * @return List<EpsVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public EpsVO getEpsByCode(String code) throws BusinessException;

	/**
	 * 
	 * Obtiene una eps por su C�digo
	 * @param code
	 * @return EpsVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public EpsVO getEpsByID(Long id) throws BusinessException;

	/**
	 * Obtiene una eps por su ID
	 * @param id
	 * @return EpsVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<EpsVO> getAllEps() throws BusinessException;

	/**
	 * Obtiene una lista de las eps ragionalizadas.
	 * @param countryId identificador del país por el que se hará el filtrado
	 * @return Lista de las eps regionalizadas
	 * @throws BusinessException en caso de error al tratar de consultar la lista
	 * @author jjimenezh agregado por control de cambios 2010-04-23
	 */
	public List<EpsVO> getAllEpsByCountryId(Long countryId) throws BusinessException;
}

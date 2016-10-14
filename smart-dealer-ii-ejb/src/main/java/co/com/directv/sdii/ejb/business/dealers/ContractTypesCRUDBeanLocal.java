package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ContractTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad ContractTypes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContractTypesCRUDBeanLocal {

	public ContractTypeVO getContractTypesByCode(String code) throws BusinessException;
	
	public ContractTypeVO getContractTypesByID(Long id) throws BusinessException;
	
	public List<ContractTypeVO> getAllContractTypes() throws BusinessException;
	
	/**
	 * Obtiene un listado de tipos de contrato regionalizado
	 * @param countryId identificador del país
	 * @return lista de los tipos de contrato por el país especificado
	 * @throws BusinessException en caso de error al tratar de obtener la lista
	 * de los países
	 * @author jjimenezh Agergado por control de cambios 2010-04-23
	 */
	public List<ContractTypeVO> getAllContractTypesByCountryId(Long countryId) throws BusinessException;
}

package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerStatusVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de DealerStatus
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerStatusFacadeBeanLocal {

	/**
	 * Metodo: Permite consultar un estado de dealer por su código
	 * @param code - String Código del estado de dealer
	 * @return DealerStatusVO cuyo código corresponde al especificado; nulo en otro caso
	 * @throws BusinessException
	 * @author jalopez
	 */
	public DealerStatusVO getDealerStatusByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar un estado de dealer por su idenrificador
	 * @param id - Long identificador del estado de dealer
	 * @return DealerStatusVO cuyo identificador es el especificado; nulo en otro caso
	 * @throws BusinessException
	 * @author jalopez
	 */
	public DealerStatusVO getDealerStatusByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar los estados de dealer registrados
	 * @return List<DealerStatusVO> Lista de estados de dealer registrados en el sistema; vacio en caso que no existan
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<DealerStatusVO> getAllDealerStatus() throws BusinessException;
}

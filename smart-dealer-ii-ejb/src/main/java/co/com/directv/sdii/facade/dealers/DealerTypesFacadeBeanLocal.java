package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de DealerTypes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerTypesFacadeBeanLocal {

	/**
	 * Metodo: Permite crear un Tipo de dealer
	 * @param obj - DealerTypeVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public void createDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar un tipo de dealer
	 * @param obj - DealerTypeVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void updateDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite eliminar un tipo de dealer
	 * @param obj - DealerTypeVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public void deleteDealerType(DealerTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Consulta un tipo de dealer por su código
	 * @param code - String Código del dealer a consultar
	 * @return DealerTypeVO asociado al código especificado; nulo en otro caso
	 * @throws BusinessException
	 * @author jalopez
	 */
	public DealerTypeVO getDealerTypesByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Consulta un tipo de dealer por su identificador
	 * @param id - Long
	 * @return DealerTypeVO
	 * @throws BusinessException
	 * @author jalopez
	 */
	public DealerTypeVO getDealerTypesByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Consulta los tipos de dealers registrados en el sistema 
	 * @return List<DealerTypeVO> Lista de dealers registrados; vacio en caso de no existir
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<DealerTypeVO> getAllDealerTypes() throws BusinessException;
	
	/**
	 * Metodo: Consulta los tipos de dealers registrados en el sistema y son asignables
	 * @param isAlloc - String
	 * @return List<DealerTypeVO> Lista de dealers registrados asignables; vacio en caso de no existir
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<DealerTypeVO> getDealerTypesByIsAlloc(String isAlloc) throws BusinessException;
}

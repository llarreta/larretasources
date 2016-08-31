package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.TechnologyVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad Technology.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface TechnologyFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto Technology
	 * @param obj - TechnologyVO  objeto que encapsula la información de un TechnologyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createTechnology(TechnologyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un Technology
	 * @param obj - TechnologyVO  objeto que encapsula la información de un TechnologyVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateTechnology(TechnologyVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un Technology
	 * @param obj - TechnologyVO  información del TechnologyVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteTechnology(TechnologyVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un Technology por su identificador
	 * @param id - Long identificador del Technology a ser consultado
	 * @return objeto con la información del TechnologyVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public TechnologyVO getTechnologyByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los Technology almacenados en la persistencia
	 * @return List<TechnologyVO> Lista con los TechnologyVO existentes, una lista vacia en caso que no existan TechnologyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<TechnologyVO> getAllTechnologies() throws BusinessException;

	/**
	 * Metodo: Permite consultar la información de todos los Technology almacenados en la persistencia
	 * @return List<TechnologyVO> Lista con los TechnologyVO existentes, una lista vacia en caso que no existan TechnologyVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<TechnologyVO> getActiveTechnologies() throws BusinessException;

	
	/**
	 * Metodo: obtiene una causa de ajuste dado el código de la misma
	 * @param code código de la causa de ajuste
	 * @return Causa de ajuste dado el código, nulo en caso que no se encuentre
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public TechnologyVO getTechnologyByCode(String code)throws BusinessException;

}

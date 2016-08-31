package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiTypeVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad KpiType.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiTypeFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto KpiType
	 * @param obj - KpiTypeVO  objeto que encapsula la información de un KpiTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiType(KpiTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un KpiType
	 * @param obj - KpiTypeVO  objeto que encapsula la información de un KpiTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiType(KpiTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un KpiType
	 * @param obj - KpiTypeVO  información del KpiTypeVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteKpiType(KpiTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un KpiType por su identificador
	 * @param id - Long identificador del KpiType a ser consultado
	 * @return objeto con la información del KpiTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public KpiTypeVO getKpiTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los KpiType almacenados en la persistencia
	 * @return List<KpiTypeVO> Lista con los KpiTypeVO existentes, una lista vacia en caso que no existan KpiTypeVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<KpiTypeVO> getAllKpiTypes() throws BusinessException;

}

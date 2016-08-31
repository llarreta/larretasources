package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiTypeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad KpiType.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiTypeBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto KpiTypeVO
	 * @param obj objeto que encapsula la información de un KpiTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createKpiType(KpiTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un KpiTypeVO
	 * @param obj objeto que encapsula la información de un KpiTypeVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateKpiType(KpiTypeVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un KpiTypeVO
	 * @param obj información del KpiTypeVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteKpiType(KpiTypeVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un KpiTypeVO por su identificador
	 * @param id identificador del KpiTypeVO a ser consultado
	 * @return objeto con la información del KpiTypeVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public KpiTypeVO getKpiTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los KpiTypeVO almacenados en la persistencia
	 * @return Lista con los KpiTypeVO existentes, una lista vacia en caso que no existan KpiTypeVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<KpiTypeVO> getAllKpiTypes() throws BusinessException;

}

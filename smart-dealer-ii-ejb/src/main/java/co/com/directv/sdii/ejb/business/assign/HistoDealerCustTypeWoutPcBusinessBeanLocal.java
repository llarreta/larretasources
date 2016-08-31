package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerCustTypeWoutPc.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCustTypeWoutPcBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerCustTypeWoutPcVO
	 * @param obj objeto que encapsula la información de un HistoDealerCustTypeWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerCustTypeWoutPcVO
	 * @param obj objeto que encapsula la información de un HistoDealerCustTypeWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerCustTypeWoutPcVO
	 * @param obj información del HistoDealerCustTypeWoutPcVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerCustTypeWoutPcVO por su identificador
	 * @param id identificador del HistoDealerCustTypeWoutPcVO a ser consultado
	 * @return objeto con la información del HistoDealerCustTypeWoutPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerCustTypeWoutPcVO getHistoDealerCustTypeWoutPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerCustTypeWoutPcVO almacenados en la persistencia
	 * @return Lista con los HistoDealerCustTypeWoutPcVO existentes, una lista vacia en caso que no existan HistoDealerCustTypeWoutPcVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCustTypeWoutPcVO> getAllHistoDealerCustTypeWoutPcs() throws BusinessException;

}

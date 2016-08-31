package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad HistoDealerServiceSubCatWithPc.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerServiceSubCatWithPcBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto HistoDealerServiceSubCatWithPcVO
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCatWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un HistoDealerServiceSubCatWithPcVO
	 * @param obj objeto que encapsula la información de un HistoDealerServiceSubCatWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un HistoDealerServiceSubCatWithPcVO
	 * @param obj información del HistoDealerServiceSubCatWithPcVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un HistoDealerServiceSubCatWithPcVO por su identificador
	 * @param id identificador del HistoDealerServiceSubCatWithPcVO a ser consultado
	 * @return objeto con la información del HistoDealerServiceSubCatWithPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public HistoDealerServiceSubCatWithPcVO getHistoDealerServiceSubCatWithPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los HistoDealerServiceSubCatWithPcVO almacenados en la persistencia
	 * @return Lista con los HistoDealerServiceSubCatWithPcVO existentes, una lista vacia en caso que no existan HistoDealerServiceSubCatWithPcVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<HistoDealerServiceSubCatWithPcVO> getAllHistoDealerServiceSubCatWithPcs() throws BusinessException;

}

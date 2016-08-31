package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerServiceSubCatWithPcVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerServiceSubCatWithPc.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerServiceSubCatWithPcFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerServiceSubCatWithPc
	 * @param obj - HistoDealerServiceSubCatWithPcVO  objeto que encapsula la información de un HistoDealerServiceSubCatWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerServiceSubCatWithPc
	 * @param obj - HistoDealerServiceSubCatWithPcVO  objeto que encapsula la información de un HistoDealerServiceSubCatWithPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerServiceSubCatWithPc
	 * @param obj - HistoDealerServiceSubCatWithPcVO  información del HistoDealerServiceSubCatWithPcVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerServiceSubCatWithPc(HistoDealerServiceSubCatWithPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerServiceSubCatWithPc por su identificador
	 * @param id - Long identificador del HistoDealerServiceSubCatWithPc a ser consultado
	 * @return objeto con la información del HistoDealerServiceSubCatWithPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerServiceSubCatWithPcVO getHistoDealerServiceSubCatWithPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerServiceSubCatWithPc almacenados en la persistencia
	 * @return List<HistoDealerServiceSubCatWithPcVO> Lista con los HistoDealerServiceSubCatWithPcVO existentes, una lista vacia en caso que no existan HistoDealerServiceSubCatWithPcVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerServiceSubCatWithPcVO> getAllHistoDealerServiceSubCatWithPcs() throws BusinessException;

}

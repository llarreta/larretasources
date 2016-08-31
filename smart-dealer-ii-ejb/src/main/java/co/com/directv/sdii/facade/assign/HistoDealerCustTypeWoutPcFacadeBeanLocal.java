package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.HistoDealerCustTypeWoutPcVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad HistoDealerCustTypeWoutPc.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface HistoDealerCustTypeWoutPcFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto HistoDealerCustTypeWoutPc
	 * @param obj - HistoDealerCustTypeWoutPcVO  objeto que encapsula la información de un HistoDealerCustTypeWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un HistoDealerCustTypeWoutPc
	 * @param obj - HistoDealerCustTypeWoutPcVO  objeto que encapsula la información de un HistoDealerCustTypeWoutPcVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un HistoDealerCustTypeWoutPc
	 * @param obj - HistoDealerCustTypeWoutPcVO  información del HistoDealerCustTypeWoutPcVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteHistoDealerCustTypeWoutPc(HistoDealerCustTypeWoutPcVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un HistoDealerCustTypeWoutPc por su identificador
	 * @param id - Long identificador del HistoDealerCustTypeWoutPc a ser consultado
	 * @return objeto con la información del HistoDealerCustTypeWoutPcVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public HistoDealerCustTypeWoutPcVO getHistoDealerCustTypeWoutPcByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los HistoDealerCustTypeWoutPc almacenados en la persistencia
	 * @return List<HistoDealerCustTypeWoutPcVO> Lista con los HistoDealerCustTypeWoutPcVO existentes, una lista vacia en caso que no existan HistoDealerCustTypeWoutPcVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<HistoDealerCustTypeWoutPcVO> getAllHistoDealerCustTypeWoutPcs() throws BusinessException;

}

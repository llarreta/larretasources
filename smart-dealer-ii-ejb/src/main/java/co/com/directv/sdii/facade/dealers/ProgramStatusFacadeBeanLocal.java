package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ProgramStatusVO;

/**
 * 
 * Interfaz que define las operaciones del componente de negocio
 * ProgramStatusCRUDBean
 * 
 * Fecha de CreaciÃÂÃÂ³n: 20/05/2010
 * 
 * @author jcasas <a href="jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 */
@Local
public interface ProgramStatusFacadeBeanLocal {

	/**
	 * Crea un estado de programa en el sistema
	 * 
	 * @param obj
	 *            Estado de programa que se crearÃÂÃÂ¡ en el sistema
	 * @throws BusinessException
	 */
	public void createProgramStatus(ProgramStatusVO obj)
			throws BusinessException;

	/**
	 * Obtiene un estado de un programa con el id especificado
	 * 
	 * @param id
	 *            Id del estado del programa
	 * @return Estado de programa con el id especificado
	 * @throws BusinessException
	 */
	public ProgramStatusVO getProgramStatusByID(Long id)
			throws BusinessException;

	/**
	 * Actualiza un estado de programa en el sistema
	 * 
	 * @param obj
	 *            Estado de programa que se actualizarÃÂÃÂ¡ en el sistema
	 * @throws BusinessException
	 */
	public void updateProgramStatus(ProgramStatusVO obj)
			throws BusinessException;

	/**
	 * Elimina un estado de programa en el sistema
	 * 
	 * @param obj
	 *            Estado de programa que se eliminarÃÂÃÂ¡ del sistema
	 * @throws BusinessException
	 */
	public void deleteProgramStatus(ProgramStatusVO obj)
			throws BusinessException;

	/**
	 * Obtiene todos los estados de programa existentes
	 * 
	 * @return Listado de los estados de programa existentes
	 * @throws BusinessException
	 */
	public List<ProgramStatusVO> getAll() throws BusinessException;

}

package co.com.directv.sdii.facade.schedule;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.vo.SchedulerTaskVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de programadores de tareas.
 * 
 * Fecha de Creación: 8/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface SchedulerTaskFacadeLocal {

	/**
	 * Metodo: <Descripcion>
	 * @param state
	 * @param id
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<SchedulerTaskVO> getSchedulerTaskByStateAndId(String state, Long id) throws BusinessException;
	
	/**
	 * Metodo: <Descripcion>
	 * @param obj
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void updateSchedulerTask(SchedulerTaskDTO obj) throws  BusinessException;
	
}

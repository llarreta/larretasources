package co.com.directv.sdii.facade.schedule.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.SchedulerTaskBussinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.schedule.SchedulerTaskFacadeLocal;
import co.com.directv.sdii.model.dto.SchedulerTaskDTO;
import co.com.directv.sdii.model.vo.SchedulerTaskVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del programador de tareas.
 * 
 * Fecha de Creación: 8/05/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="SchedulerTaskFacadeLocal",mappedName="ejb/SchedulerTaskFacadeLocal")
public class SchedulerTaskFacadeBean implements SchedulerTaskFacadeLocal {

    @EJB(name="SchedulerTaskBussinessLocal", beanInterface=SchedulerTaskBussinessLocal.class)
	private SchedulerTaskBussinessLocal schedulerTaskBussiness;

	@Override
	public List<SchedulerTaskVO> getSchedulerTaskByStateAndId(String state, Long id) throws BusinessException {
		return schedulerTaskBussiness.getSchedulerTaskVOByStateAndId(state,id);
	}

	@Override
	public void updateSchedulerTask(SchedulerTaskDTO obj) throws  BusinessException{
		schedulerTaskBussiness.updateSchedulerTask(obj);
	}
	
}

package co.com.directv.sdii.ejb.business.core.impl;


import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SpearRuleStatus;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.vo.WorkOrderVO;
import co.com.directv.sdii.persistence.dao.config.WorkorderStatusDAOLocal;

/**
 * 
 * Implementa las operaciones de la
 * maquina de estados, los cuales validan si es
 * posible el cambio de un estado actual de una
 * work order a otro estado.
 * El flujo de estados, o los posibles estados
 * por los cuales puede pasar una Work order se
 * encuentran realacionados en una tabla en la
 * Base de Datos.
 * 
 * Fecha de Creación: 28/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.core.StateMachineWOBusinessLocal
 * @see 
 */
@Stateless(name="StateMachineWOBusinessLocal",mappedName="ejb/StateMachineWOBusinessLocal")
public class StateMachineWOBusiness extends BusinessBase implements StateMachineWOBusinessLocal {

	@EJB(name="WorkorderStatusDAOLocal",beanInterface=WorkorderStatusDAOLocal.class)
	private WorkorderStatusDAOLocal workOrderDao;
	
	private final static Logger log = UtilsBusiness.getLog4J(CoreWOBusiness.class);
	
	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro y verifica si es posible 
	 * realizar el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * Este metodo sera cambiando por validateStatusChangeWorkOrderByCodes
	 * @param WorkOrderVO workOrder
	 * @param Long statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@Deprecated
	public boolean validateStatusChangeWorkOrder(WorkOrderVO workOrder,Long statusChange) throws BusinessException {
		log.debug("== Inicia validateStatusChangeWorkOrder/StateMachineWOBusiness ==");
		boolean res = true;
		try{
			if(workOrder == null){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			if(statusChange == null || workOrder.getWorkorderStatusByActualStatusId() == null){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			
			if(	workOrder.getWorkorderStatusByActualStatusId().getId() == null ){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			
			SpearRuleStatus spearRule = workOrderDao.getWorkorderStatusBySpearRule(UtilsBusiness.copyObject(WorkOrder.class, workOrder),statusChange);
			if(spearRule == null){
				res = false;
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_STATE_CHANGE_NOT_ALLOWED.getCode(),ErrorBusinessMessages.WORKORDER_STATE_CHANGE_NOT_ALLOWED.getMessage() + " El id del estado actual de la WO es: \"" + workOrder.getWorkorderStatusByActualStatusId().getId() + "\" El id del estado al cual se quiere cambiar es: \"" + statusChange + "\"");
			}
		
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
	        log.debug("== Termina validateStatusChangeWorkOrder/StateMachineWOBusiness ==");
	    }		
		return res;
	}
	
	/**
	 * 
	 * Metodo: realiza la validacion del cambio
	 * de estado actual a otro por medio de los codigos 
	 * de estado y verifica si es posible realizar 
	 * el cambio de estado.
	 * Retorna true si es posible realizar el cambio, de 
	 * lo contrario genera un BusinessException.
	 * @param WorkOrderVO workOrder
	 * @param String statusChange
	 * @return boolean, true si es posible el cambio de estado.
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public boolean validateStatusChangeWorkOrderByCodes(WorkOrderVO workOrder,String statusChange) throws BusinessException {
		log.debug("== Inicia validateStatusChangeWorkOrder/StateMachineWOBusiness ==");
		boolean res = true;
		try{
			if(workOrder == null ){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			if(statusChange == null || statusChange.equals("") || workOrder.getWorkorderStatusByActualStatusId() == null){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			
			if(	workOrder.getWorkorderStatusByActualStatusId().getWoStateCode() == null 
				|| workOrder.getWorkorderStatusByActualStatusId().getWoStateCode().equals("") ){
				throw new BusinessException( ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() );
			}
			
			SpearRuleStatus spearRule = workOrderDao.getWorkorderStatusBySpearRuleByCodes(UtilsBusiness.copyObject(WorkOrder.class, workOrder),statusChange);
			if(spearRule == null){
				res = false;
				throw new BusinessException(ErrorBusinessMessages.WORKORDER_STATE_CHANGE_NOT_ALLOWED.getCode(),ErrorBusinessMessages.WORKORDER_STATE_CHANGE_NOT_ALLOWED.getMessage());
			}
		
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
	        log.debug("== Termina validateStatusChangeWorkOrder/StateMachineWOBusiness ==");
	    }		
		return res;
	}
}

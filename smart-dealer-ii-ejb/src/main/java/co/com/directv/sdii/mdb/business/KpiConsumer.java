package co.com.directv.sdii.mdb.business;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.kpi.job.KPIResultPersistenceManager;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.KpiConfigurationFacadeBeanLocal;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;

public class KpiConsumer implements MessageListener {
	
	private final static Logger log = UtilsBusiness.getLog4J(KpiConsumer.class);
	
    @EJB(name="KpiConfigurationFacadeBeanLocal", beanInterface=KpiConfigurationFacadeBeanLocal.class)
    private KpiConfigurationFacadeBeanLocal kpiConfigurationFacadeBeanLocal;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message msg) {
		log.debug("== Inicia onMessage/MovementInventoryConsumer ==");
		ObjectMessage objMsg = (ObjectMessage) msg;
		KpiCalculateDTO request = null;
		
		try {
			Object o = objMsg.getObject();
			if (o != null) {
				if(o instanceof KpiCalculateDTO){
					request = (KpiCalculateDTO) o;
					log.debug("== Inicia Llamado a inventarios/MovementInventoryConsumer =="+this.hashCode());
					
					log.debug("== Llamado createQueueMovementInventoryByWorkOrder/MovementInventoryConsumer ==");
					log.debug( request.toString() );
					KPIResultPersistenceManager.getInstance().calculateAndPersistDealersIndicator(request);
					/*Se actualiza kla fecha de siguienjte ejecucion del KPI*/
					kpiConfigurationFacadeBeanLocal.calculateAndSaveNextExcecutionDate(((KpiCalculateDTO) o).getIndicatorConfigurationId());
					/*Se actualiza kla fecha de siguienjte ejecucion del KPI*/
				}
			}
		} catch (JMSException e) {
			log.error("== Error onMessage/MovementInventoryConsumer ==", e);
		} catch (BusinessException e) {
			log.error("== Error onMessage/MovementInventoryConsumer ==", e);
		} finally {
			log.debug("== Finaliza onMessage/MovementInventoryConsumer ==");
		}
	}
	
}
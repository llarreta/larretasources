
package co.com.directv.sdii.ejb.business.stock.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.CoreWOBusinessLocal;
import co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.CoreStockBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.ElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.IbsElementsNotificationBusinessLocal;
import co.com.directv.sdii.ejb.business.stock.MovCmdBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementConfigBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementElementBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.MovementQueueHelperLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.WarehouseElementBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.config.WorkOrderDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.DocumentClassDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EventIbsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.EventReasonIbsDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MovCmdStatusDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.PorccessLinkClientSerialLogDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.SerializedDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;

/**
 * Req-0098 - Paralelismo de Inventarios
 * 
 * EJB que implementa los métodos para realizar la configuración de los
 * informes que se realizan a IBS cuando se mueven elementos entre las
 * bodegas.
 * 
 * 
 * Session Bean implementation class MovCmdBusinessBean
 * 
 * @author ialessan - 29/07/2013
 */

@Stateless(name = "MovCmdBusinessBeanLocal", mappedName = "ejb/MovCmdBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovCmdBusinessBean extends BusinessBase implements MovCmdBusinessBeanLocal {
	
    @EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
	
	@EJB private PorccessLinkClientSerialLogDAOLocal porccessLinkClientSerialLogDAOLocal;
	
	@EJB
	private WorkOrderDAOLocal woDao;
	
	@EJB
	private ElementBusinessBeanLocal elementBusinessBean;
	
	@EJB(name="MovCmdQueueDAOLocal", beanInterface=MovCmdQueueDAOLocal.class)
	private MovCmdQueueDAOLocal daoMovCmdQueue;

	@EJB(name="MovCmdLogDAOLocal", beanInterface=MovCmdLogDAOLocal.class)
	private MovCmdLogDAOLocal daoMovCmdLog;

	@EJB(name="MovCmdStatusDAOLocal", beanInterface=MovCmdStatusDAOLocal.class)
	private MovCmdStatusDAOLocal daoMovCmdStatus;
	
	@EJB(name="MovementQueueHelperLocal", beanInterface=MovementQueueHelperLocal.class)
	private MovementQueueHelperLocal helperMovementQueue;
	
	@EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
	private WarehouseBusinessBeanLocal businessWarehouse;

	@EJB(name="UserDAOLocal", beanInterface= UserDAOLocal.class)
	private UserDAOLocal daoUser;
	
	@EJB(name="CoreStockBusinessLocal", beanInterface= CoreStockBusinessLocal.class)
	private CoreStockBusinessLocal businessCoreStock;
	
	@EJB(name="WarehouseElementBusinessBeanLocal", beanInterface= WarehouseElementBusinessBeanLocal.class)
	private WarehouseElementBusinessBeanLocal businessWarehouseElement;
	
	@EJB(name="WarehouseDAOLocal", beanInterface= WarehouseDAOLocal.class)
	private WarehouseDAOLocal daoWarehouse;
	
	@EJB(name="DealersDAOLocal", beanInterface= DealersDAOLocal.class)
	private DealersDAOLocal daoDealers;
	
	@EJB(name="EventIbsDAOLocal", beanInterface= EventIbsDAOLocal.class)
	private EventIbsDAOLocal daoEventIbs;
	
	@EJB(name="SerializedDAOLocal", beanInterface= SerializedDAOLocal.class)
	private SerializedDAOLocal daoSerialized;
	
	@EJB(name="WarehouseElementDAOLocal", beanInterface= WarehouseElementDAOLocal.class)
	private WarehouseElementDAOLocal daoWarehouseElement;
	
	@EJB(name="CoreWOBusinessLocal", beanInterface= CoreWOBusinessLocal.class)
	private CoreWOBusinessLocal businessCoreWO;
	
	@EJB(name="DocumentClassDAOLocal", beanInterface= DocumentClassDAOLocal.class)
	private DocumentClassDAOLocal daoDocumentClass;
	
	@EJB(name="EventReasonIbsDAOLocal", beanInterface= EventReasonIbsDAOLocal.class)
	private EventReasonIbsDAOLocal daoEventReasonIbs;
	
	@EJB(name="MovementElementBusinessBeanLocal", beanInterface= MovementElementBusinessBeanLocal.class)
	private MovementElementBusinessBeanLocal businessMovementElement;
	
	@EJB
	private WarehouseDAOLocal warehouseDao;

	@EJB(name="SecurityBusinessBeanLocal", beanInterface=SecurityBusinessBeanLocal.class)
	private SecurityBusinessBeanLocal businessSecurity;

	
	//@EJB(name="IbsElementsNotificationBusiness", beanInterface=IbsElementsNotificationBusiness.class)
	//private IbsElementsNotificationBusiness ibsElementsNotificationBusiness; ... da error NO es un ejb

	@EJB(name="IbsElementsNotificationBusinessLocal", beanInterface=IbsElementsNotificationBusinessLocal.class)
	private IbsElementsNotificationBusinessLocal IbsElementsNotificationBusiness;
	
	
	
	@EJB(name="MovementConfigBusinessBeanLocal", beanInterface=MovementConfigBusinessBeanLocal.class)
	private MovementConfigBusinessBeanLocal businessMovementConfig;	
	
	private final static Logger log =  UtilsBusiness.getLog4J(MovCmdBusinessBean.class);

	/**
	 * Default constructor. 
	 */
	public MovCmdBusinessBean() {
	}

	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void sendStatusCmdToIBSParallel(MovCmdQueueVO record) throws BusinessException {	
		log.debug("Inicia sendStatusCmdToIBSParallel/MovCmdBusinessBean");
		Long countryId =  record.getCountry().getId();
		try {
			//counsultar usuario para calcular la fecha del proceso
			User user = UtilsBusiness.copyObject(User.class, this.businessSecurity.getIBSAdminUserByCountryId(countryId));
			IbsElementsNotificationBusiness.callServiceInventoryParallel(record, user);
		} catch (BusinessException be){
			log.error("Error sendStatusCmdToIBSParallel/MovCmdBusinessBean",be);
			throw new BusinessException(be.getMessageCode(), be.getMessage(), be);
		} finally{
			log.debug("Finaliza sendStatusCmdToIBSParallel/IbsElementsNotificationBusiness");
		}
		
	}		

}

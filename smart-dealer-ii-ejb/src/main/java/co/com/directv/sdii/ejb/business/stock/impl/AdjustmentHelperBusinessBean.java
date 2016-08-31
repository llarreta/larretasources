/**
 * Created 08/03/2016
 */
package co.com.directv.sdii.ejb.business.stock.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.core.tray.impl.ReportHelperBusinessBean;
import co.com.directv.sdii.ejb.business.stock.AdjustmentHelperBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.stock.AdjustmentModificationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * This class implement helper methods for the adjustment process. 
 * 
 * @author Jorge Ariel Silva
 * @version 1.0
 * 
 */

@Stateless(name="AdjustmentHelperBusinessBeanLocal",mappedName="ejb/AdjustmentHelperBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentHelperBusinessBean extends BusinessBase implements AdjustmentHelperBusinessBeanLocal{
	
	private final static Logger log = UtilsBusiness.getLog4J(ReportHelperBusinessBean.class);
	
	@EJB(name="AdjustmentDAOLocal", beanInterface=AdjustmentDAOLocal.class)
    private AdjustmentDAOLocal daoAdjustment;
	
	@EJB(name = "AdjustmentModificationBusinessBeanLocal", beanInterface = AdjustmentModificationBusinessBeanLocal.class)
	private AdjustmentModificationBusinessBeanLocal adjustmentModificationBusinessBean;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateAdjustment(AdjustmentVO obj,Long userId) throws BusinessException {
        
		log.debug("== Inicia updateAdjustment/AdjustmentHelperBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        try{
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            Adjustment objPojo =  UtilsBusiness.copyObject(Adjustment.class, obj);
            daoAdjustment.updateAdjustment(objPojo);
            adjustmentModificationBusinessBean.createAdjustmentModification(objPojo.getId(), objPojo.getAdjustmentStatus().getCode(), userId);
            
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateAdjustment/AdjustmentHelperBusinessBean ==");
        	throw this.manageException(ex);
        }finally {
            log.debug("== Termina updateAdjustment/AdjustmentHelperBusinessBean ==");
        }
		
	}
	
}

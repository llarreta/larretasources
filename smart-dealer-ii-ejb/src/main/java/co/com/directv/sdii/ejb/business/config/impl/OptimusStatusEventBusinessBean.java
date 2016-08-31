package co.com.directv.sdii.ejb.business.config.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.OptimusStatusEventBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusEventDAOLocal;

@Stateless(name = "OptimusStatusEventBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local({OptimusStatusEventBusinessBeanLocal.class})
public class OptimusStatusEventBusinessBean extends BusinessBase implements OptimusStatusEventBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusEventBusinessBean.class);
	
	@EJB(name = "OptimusStatusEventDAOLocal", beanInterface = OptimusStatusEventDAOLocal.class)
	private OptimusStatusEventDAOLocal optimusStatusEventDao;

	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createOptimusStatusEvent(OptimusStatusEvent optimusStatusEvent, byte[] resquestXML) throws BusinessException {
		log.debug("== Inicia createOptimusStatusEvent/OptimusStatusEventBusinessBean ==");
        try {
        	optimusStatusEventDao.createOptimusStatusEvent(optimusStatusEvent, resquestXML);
        	
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion OptimusStatusEventBusinessBean/createOptimusStatusEvent");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createOptimusStatusEvent/OptimusStatusEventBusinessBean ==");
        }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateOptimusStatusEvent(OptimusStatusEvent optimusStatusEvent) throws BusinessException {
		log.debug("== Inicia updateOptimusStatusEvent/OptimusStatusEventBusinessBean ==");
        try {
            optimusStatusEventDao.updateOptimusStatusEvent(optimusStatusEvent);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion OptimusStatusEventBusinessBean/updateOptimusStatusEvent");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateOptimusStatusEvent/OptimusStatusEventBusinessBean ==");
        }
	}
	
}

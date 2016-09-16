package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WorkOrderCrewAttention;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.WorkOrderCrewAttentionDAOLocal;

@Stateless(name="WorkOrderCrewAttentionDAOLocal",mappedName="ejb/WorkOrderCrewAttentionDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WorkOrderCrewAttentionDAO extends BaseDao implements WorkOrderCrewAttentionDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(WorkOrderCrewAttentionDAO.class);	
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createWorkOrderCrewAttention(WorkOrderCrewAttention obj)
			throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createWorkOrderCrewAttention/WorkOrderCrewAttentionDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error createWorkOrderCrewAttention/WorkOrderCrewAttentionDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWorkOrderCrewAttention/WorkOrderCrewAttentionDAO ==");
        }
		
	}

}

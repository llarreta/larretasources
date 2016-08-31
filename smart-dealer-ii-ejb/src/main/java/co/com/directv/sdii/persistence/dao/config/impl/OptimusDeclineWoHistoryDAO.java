package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusDeclineWoHistory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.OptimusDeclineWoHistoryDAOLocal;

@Stateless(name = "OptimusDeclineWoHistoryDAOLocal",mappedName = "ejb/OptimusDeclineWoHistoryDAOLocal")
public class OptimusDeclineWoHistoryDAO extends BaseDao implements OptimusDeclineWoHistoryDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusDAO.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createOptimusDeclineWoHistory(OptimusDeclineWoHistory obj)
			throws DAOSQLException, DAOServiceException {
		log.debug("== Comienza createOptimusDeclineWoHistory/OptimusDeclineWoHistoryDAO ==");
		
        try {
        	Session session = super.getSession();
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }       	
        	session.save(obj);
        	this.doFlush(session);
        }catch (Throwable ex) {
            log.error("== Error creando OptimusDeclineWoHistory==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createOptimusDeclineWoHistory/OptimusDeclineWoHistoryDAO ==");
        }		
	}


	@Override
	public void updateOptimusDeclineWoHistory(OptimusDeclineWoHistory obj)
			throws DAOSQLException, DAOServiceException {
		// TODO Auto-generated method stub
		
	}

}

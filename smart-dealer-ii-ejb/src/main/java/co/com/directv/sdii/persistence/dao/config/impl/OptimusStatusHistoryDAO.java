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
import co.com.directv.sdii.model.pojo.OptimusStatusHistory;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusHistoryDAOLocal;

@Stateless(name = "OptimusStatusHistoryDAOLocal",mappedName = "ejb/OptimusStatusHistoryDAOLocal")
public class OptimusStatusHistoryDAO extends BaseDao  implements OptimusStatusHistoryDAOLocal{

	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusEventDAO.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createOptimusStatusHistory(OptimusStatusHistory obj) throws DAOSQLException, DAOServiceException {
		log.debug("== Comienza createOptimusStatusHistory/OptimusStatusHistoryDAO ==");
		try{
			Session session = super.getSession();
			
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
		} catch(Throwable ex) {
			log.error("== Error creando OptimusStatusHistory ==", ex);
            throw this.manageException(ex);
		} finally {
			log.debug("== Termina createOptimusStatusHistory/OptimusStatusHistoryDAO ==");
		}
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateOptimusStatusHistory(OptimusStatusHistory obj) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicio updateOptimusStatusHistory/OptimusStatusHistoryDAO ==");
		
		Session session = super.getSession();
		
		try{
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.merge(obj);
            this.doFlush(session);
		} catch(Throwable ex) {
			log.error("== Error actualizando OptimusStatusHistory ==", ex);
            throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateOptimusStatusHistory/OptimusStatusHistoryDAO ==");
		}		
		
	}

	
}

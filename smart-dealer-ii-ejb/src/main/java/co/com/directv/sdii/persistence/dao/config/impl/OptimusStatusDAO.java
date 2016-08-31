package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusDAOLocal;


@Stateless(name = "OptimusStatusDAOLocal",mappedName = "ejb/OptimusStatusDAOLocal")
public class OptimusStatusDAO extends BaseDao implements OptimusStatusDAOLocal{

	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusDAO.class);

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public OptimusStatus getOptimusStatusById(String id) throws DAOSQLException, DAOServiceException {
		log.debug("== Comienza getOptimusStatusByID/OptimusStatusDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + OptimusStatus.class.getName() + " os ");
            queryBuffer.append(" where os.id = :id");
            Query query = session.createQuery(queryBuffer.toString());
            //query.setLong("id", id);
            query.setString("id", id);
            Object obj = query.uniqueResult();
            
            if (obj != null) {
                return (OptimusStatus) obj;
            }
            return null;
        }catch (Throwable ex) {
            log.error("== Error consultando OptimusStatus por ID==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getOptimusStatusByID/OptimusStatusDAO ==");
        }
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public OptimusStatus getOptimusStatusByCode(String code) throws DAOSQLException, DAOServiceException {
        log.debug("== Inicio getOptimusStatusByCode/OptimusStatusDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from " + OptimusStatus.class.getName() + " os ");
            queryBuffer.append(" where os.code = :aCode");
            Query query = session.createQuery(queryBuffer.toString());
            query.setString("aCode", code);
            OptimusStatus optimusStatus = (OptimusStatus) query.uniqueResult();
            
            return optimusStatus;
        }catch (Throwable ex) {
            log.error("== Error consultando OptimusStatus por CODE==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getOptimusStatusByCode/OptimusStatusDAO ==");
        }
	}


}

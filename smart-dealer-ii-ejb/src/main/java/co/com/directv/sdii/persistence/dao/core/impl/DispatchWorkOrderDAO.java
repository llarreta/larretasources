package co.com.directv.sdii.persistence.dao.core.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DispatchWorkOrderLog;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.DispatchWorkOrderDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones de la entidad {@link DispatchWorkOrderLog}
 * 
 * @author fsanabri
 * @version 5.1.2
 * 
 * @see co.com.directv.sdii.model.pojo.DispatchWorkOrderLog
 * @see co.com.directv.sdii.model.hbm.DispatchWorkOrderLog.hbm.xml
 */
@Stateless(name="DispatchWorkOrderDAOLocal",mappedName="ejb/DispatchWorkOrderDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DispatchWorkOrderDAO extends BaseDao implements DispatchWorkOrderDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(DispatchWorkOrderDAO.class);


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.DispatchWorkOrderDAOLocal#createDispatchWorkOrderLog(co.com.directv.sdii.model.pojo.DispatchWorkOrderLog)
	 */
	@Override
	public void createDispatchWorkOrderLog(DispatchWorkOrderLog dispatchWorkOrderLog, byte[] responseXML) throws DAOServiceException, DAOSQLException {
    	log.debug("== Inicia createDispatchWorkOrderLog/DispatchWorkOrderDAO ==");
        Session session = super.getSession();
        
        try {
        	 if (session == null) {
                 throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
             }
        	 
        	dispatchWorkOrderLog.setResponseXml(Hibernate.createBlob(responseXML, session));
            session.persist(dispatchWorkOrderLog);
        } catch (Throwable ex){
			log.error("== Error en  createDispatchWorkOrderLog/DispatchWorkOrderDAO==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina createDispatchWorkOrderLog/DispatchWorkOrderDAO ==");
        }
	}
    
	
}

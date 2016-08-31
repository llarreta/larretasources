package co.com.directv.sdii.persistence.dao.config.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.OptimusStatusEvent;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.OptimusStatusEventDAOLocal;

@Stateless(name = "OptimusStatusEventDAOLocal",mappedName = "ejb/OptimusStatusEventDAOLocal")
public class OptimusStatusEventDAO extends BaseDao implements OptimusStatusEventDAOLocal{

	private final static Logger log = UtilsBusiness.getLog4J(OptimusStatusEventDAO.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createOptimusStatusEvent(OptimusStatusEvent obj , byte[] requestXML) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicio createOptimusStatusEvent/OptimusStatusEventDAO ==");
        try { 
			Session session = super.getSession();
			
			obj.setReqXml(Hibernate.createBlob(requestXML, session));
			
			session.save(obj);
			this.doFlush(session);
			
		} catch (Throwable ex) {
			log.error("== Error creando OptimusStatusEvent ==", ex);
            throw this.manageException(ex);
		} finally {
            log.debug("== Termina createOptimusStatusEvent/OptimusStatusEventDAO ==");
        }
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateOptimusStatusEvent(OptimusStatusEvent obj) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicio updateOptimusStatusEvent/OptimusStatusEventDAO ==");
        try { 
			Session session = super.getSession();
			
			session.merge(obj);
			//this.doFlush(session);
			
		} catch (Throwable ex) {
			log.error("== Error actualizando OptimusStatusEvent ==", ex);
            throw this.manageException(ex);
		} finally {
            log.debug("== Termina updateOptimusStatusEvent/OptimusStatusEventDAO ==");
        }
		
	}

	
}

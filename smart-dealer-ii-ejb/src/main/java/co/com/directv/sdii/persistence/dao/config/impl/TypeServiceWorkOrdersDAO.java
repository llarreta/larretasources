package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

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
import co.com.directv.sdii.model.pojo.TypeServiceWorkOrders;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.TypeServiceWorkOrdersDAOLocal;

/**
 * 
 * Implementacion para operaciones sobre la entidad TypeServiceWorkOrders
 * 
 * Fecha de Creación: 9/05/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="TypeServiceWorkOrdersDAOLocal",mappedName="ejb/TypeServiceWorkOrdersDAOLocal")
public class TypeServiceWorkOrdersDAO extends BaseDao implements TypeServiceWorkOrdersDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(TypeServiceWorkOrdersDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.TypeServiceWorkOrdersDAOLocal#getAllTypeServiceWorkOrders()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<TypeServiceWorkOrders> getAllTypeServiceWorkOrders() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllTypeServiceWorkOrders/TypeServiceWorkOrdersDAO ==");
        Session session = super.getSession();
        try {
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
        	StringBuffer stringBuffer = new StringBuffer();
        	stringBuffer.append("from ");
        	stringBuffer.append(TypeServiceWorkOrders.class.getName());
            Query query = session.createQuery(stringBuffer.toString());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error getAllTypeServiceWorkOrders/TypeServiceWorkOrdersDAO ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAllTypeServiceWorkOrders/TypeServiceWorkOrdersDAO ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.TypeServiceWorkOrdersDAOLocal#getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public TypeServiceWorkOrders getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId(Long serviceTypeId, Long woTypeId) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId/TypeServiceWorkOrdersDAO ==");
        Session session = super.getSession();
        try {
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
        	StringBuffer stringBuffer = new StringBuffer();
        	stringBuffer.append("from ");
        	stringBuffer.append(TypeServiceWorkOrders.class.getName());
        	stringBuffer.append(" tsw where ");
        	stringBuffer.append("serviceType.id = :serviceTypeId ");
        	stringBuffer.append("and workOrderType.id = :woTypeId ");
            Query query = session.createQuery(stringBuffer.toString());
            query.setLong("serviceTypeId", serviceTypeId);
            query.setLong("woTypeId", woTypeId);
            return (TypeServiceWorkOrders) query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId/TypeServiceWorkOrdersDAO ==");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getTypeServiceWorkOrdersByServiceTypeIdAndWoTypeId/TypeServiceWorkOrdersDAO ==");
        }
	}

}

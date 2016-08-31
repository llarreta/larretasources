package co.com.directv.sdii.persistence.dao.config.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ExpirationGrouping;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.ExpirationGroupingDAOLocal;

/**
 * 
 * Implementacion para operaciones CRUD sobre tabla expiration_grouping 
 * 
 * Fecha de Creación: 11/05/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ExpirationGroupingDAOLocal",mappedName="ejb/ExpirationGroupingDAOLocal")
public class ExpirationGroupingDAO extends BaseDao implements ExpirationGroupingDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ExpirationGroupingDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.ExpirationGroupingDAOLocal#getAllExpirationGroupin()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ExpirationGrouping> getAllExpirationGrouping() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllExpirationGroupin/ExpirationGroupingDAO ==");
        try {
        	Session session = this.getSession();
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("from ");
            queryBuffer.append(ExpirationGrouping.class.getName());
            queryBuffer.append(" eg order by eg.id");
            Query query = session.createQuery(queryBuffer.toString());
            return query.list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllExpirationGroupin/ExpirationGroupingDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllExpirationGroupin/ExpirationGroupingDAO ==");
        }
	}
}

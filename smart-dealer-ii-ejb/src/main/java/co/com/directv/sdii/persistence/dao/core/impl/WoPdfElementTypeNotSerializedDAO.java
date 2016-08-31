package co.com.directv.sdii.persistence.dao.core.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoPdfElementTypeNotSerialized;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.impl.CustomerDAO;
import co.com.directv.sdii.persistence.dao.core.WoPdfElementTypeNotSerializedDAOLocal;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad <code>WoPdfElementTypeNotSerialized</code>
 * 
 * Fecha de Creacion: Oct 17, 2012
 * @author ssanabri <a href="mailto:ssanabri@everis.com">e-mail</a>
 * @version 1.0
 * @see co.com.directv.sdii.model.pojo.WoPdfElementTypeNotSerialized
 * @see co.com.directv.sdii.persistence.dao.core.WoPdfElementTypeNotSerializedDAOLocal
 */
@Stateless(name="WoPdfElementTypeNotSerializedDAOLocal",mappedName="ejb/WoPdfElementTypeNotSerializedDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoPdfElementTypeNotSerializedDAO extends BaseDao implements WoPdfElementTypeNotSerializedDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(CustomerDAO.class);

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.core.WoPdfElementTypeNotSerializedDAOLocal#getWoPdfElementTypeNotSerializedByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WoPdfElementTypeNotSerialized> getWoPdfElementTypeNotSerializedsByCountryId(Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWoPdfElementTypeNotSerializedsByCountryId/WoPdfElementTypeNotSerializedDAO ==");
        Session session = super.getSession();
        try {
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
        	
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("FROM ");
        	stringQuery.append(WoPdfElementTypeNotSerialized.class.getName());
        	stringQuery.append(" wopdf WHERE wopdf.country.id = :countryId");
        	stringQuery.append(" ORDER BY wopdf.position ASC");
        	
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("countryId", countryId);
            
        	return query.list();
        }catch (Throwable ex) {
            log.debug("== Error consultando lista de WoPdfElementTypeNotSerialized por país ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getWoPdfElementTypeNotSerializedsByCountryId/WoPdfElementTypeNotSerializedDAO ==");
        }
	}
	
	
	
}

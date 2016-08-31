package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MaritalStatus;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.MaritalStatusDAOLocal;


@Stateless(name="MaritalStatusDAOLocal",mappedName="ejb/MaritalStatusDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MaritalStatusDAO extends BaseDao implements MaritalStatusDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(MaritalStatusDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.MaritalStatusDAOLocal#getAllMaritalStatuss()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MaritalStatus> getAllMaritalStatuss() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllMaritalStatuss/MaritalStatusDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(MaritalStatus.class.getName());
        	stringQuery.append(" maritalStatus");
        	return session.createQuery(stringQuery.toString()).list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllMaritalStatuss/MaritalStatusDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMaritalStatuss/MaritalStatusDAO ==");
        }
	}

   

}

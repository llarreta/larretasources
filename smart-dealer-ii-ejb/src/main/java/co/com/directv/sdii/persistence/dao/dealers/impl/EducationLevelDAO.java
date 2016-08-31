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
import co.com.directv.sdii.model.pojo.EducationLevel;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.EducationLevelDAOLocal;

@Stateless(name="EducationLevelDAOLocal",mappedName="ejb/EducationLevelDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EducationLevelDAO extends BaseDao implements EducationLevelDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(EducationLevelDAO.class);

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.EducationLevelDAOLocal#getAllEducationLevel()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EducationLevel> getAllEducationLevel() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllEducationLevel/EducationLevelDAO ==");
        
        try {
        	Session session = this.getSession();
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(EducationLevel.class.getName());
        	stringQuery.append(" educationLevel");
        	return session.createQuery(stringQuery.toString()).list();
        } catch (Throwable ex) {
            log.debug("== Error en getAllEducationLevel/EducationLevelDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEducationLevel/EducationLevelDAO ==");
        }
	}

}

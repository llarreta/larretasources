package co.com.directv.sdii.persistence.dao.core.impl;

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
import co.com.directv.sdii.model.pojo.WoManagmentElement;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.WoManagmentElementDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad WoManagmentElement
 * 
 * Fecha de Creación: 24/05/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="WoManagmentElementDAOLocal",mappedName="ejb/WoManagmentElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WoManagmentElementDAO extends BaseDao implements WoManagmentElementDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(WoManagmentElementDAO.class);
	
	@Override
	public void createWoManagmentElement(WoManagmentElement obj) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio createWoManagmentElement/CategoryDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error en la operacion createWoManagmentElement/CategoryDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createWoManagmentElement/CategoryDAO ==");
        }
	}
}

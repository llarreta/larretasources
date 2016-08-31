package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.ContactType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.ContactTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de ContactType
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.ContactType
 * @see co.com.directv.sdii.model.hbm.ContactType.hbm.xml
 */
@Stateless(name="ContactTypeDAOLocal",mappedName="ejb/ContactTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContactTypeDAO extends BaseDao implements ContactTypeDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ContactTypeDAO.class);

    /**
     * Consulta la BD y obtiene un tipo de contacto por su ID
     * @param id
     * @return ContactType
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContactType getContactTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactTypeByID/ContactTypeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactType.class.getName());
        	stringQuery.append(" contactType ");
        	stringQuery.append("where contactType.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from "+ContactType.class.getName()+" contactType " +
                    "where contactType.id = :id");*/
            query.setString("id", id.toString());
            return (ContactType)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando el ContactType por ID ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getContactTypeByID/ContactTypeDAO ==");
        }
    }

    /**
     * Consulta la BD y obtiene un tipo de contacto por su Codigo
     * @param code
     * @return ContactType
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ContactType getContactTypeByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getContactTypeByCode/ContactTypeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactType.class.getName());
        	stringQuery.append(" contactType ");
        	stringQuery.append("where contactType.contactTypeCode = :contactTypeCode");
        	Query query = session.createQuery(stringQuery.toString());
            /*Query query = session.createQuery("from "+ContactType.class.getName()+" contactType " +
                    "where contactType.contactTypeCode = :contactTypeCode");*/
            query.setString("contactTypeCode", code);
            return (ContactType)query.uniqueResult();
        } catch (Throwable ex) {
            log.debug("== Error consultando el ContactType por Code ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getContactTypeByCode/ContactTypeDAO ==");
        }
    }

    /**
     * Consulta todos los tipos de contacto
     * @return List<ContactType>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ContactType> getAllContactType() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllContactType/ContactTypeDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(ContactType.class.getName());
        	stringQuery.append(" contactType");
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("from "+ContactType.class.getName()+" contactType").list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los ContactType ==");
            throw manageException(ex);
        } finally {
            log.debug("== Termina getAllContactType/ContactTypeDAO ==");
        }
    }
}

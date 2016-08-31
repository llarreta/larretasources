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

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.InputMethod;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.core.InputMethodDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad InputMethod
 * 
 * Fecha de Creación:Nov 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.InputMethod
 * @see co.com.directv.sdii.model.hbm.InputMethod.hbm.xml
 */
@Stateless(name="InputMethodDAOLocal",mappedName="ejb/InputMethodDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class InputMethodDAO extends BaseDao implements InputMethodDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(InputMethodDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.InputMethodDAOLocal#createInputMethod(co.com.directv.sdii.model.pojo.InputMethod)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createInputMethod/InputMethodDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el InputMethod ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createInputMethod/InputMethodDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.InputMethodDAOLocal#updateInputMethod(co.com.directv.sdii.model.pojo.InputMethod)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateInputMethod/InputMethodDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el InputMethod ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateInputMethod/InputMethodDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see persistence.dao.core.InputMethodDAOLocal#deleteInputMethod(co.com.directv.sdii.model.pojo.InputMethod)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteInputMethod(InputMethod obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteInputMethod/InputMethodDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from InputMethod entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el InputMethod ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInputMethod/InputMethodDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see persistence.dao.core.InputMethodDAOLocal#getInputMethodsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public InputMethod getInputMethodByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getInputMethodByID/InputMethodDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InputMethod.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (InputMethod) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getInputMethodByID/InputMethodDAO ==");
        }
    }

    /* (non-Javadoc)
    * @see persistence.dao.core.InputMethodDAOLocal#getInputMethodsByCode(java.lang.Long)
    */
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   public InputMethod getInputMethodByCode(String code) throws DAOServiceException, DAOSQLException {
       log.debug("== Inicio getInputMethodsByCode/InputMethodDAO ==");
       Session session = super.getSession();

       try {
       	StringBuffer stringQuery = new StringBuffer();
       	stringQuery.append("from ");
       	stringQuery.append(InputMethod.class.getName());
       	stringQuery.append(" entity where entity.inputCode = :aCode");
       	Query query = session.createQuery(stringQuery.toString());
        query.setString("aCode", code);

        return (InputMethod) query.uniqueResult();

       } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
           log.debug("== Termina getInputMethodsByCode/InputMethodDAO ==");
       }
   }
    
    /* (non-Javadoc)
     * @see persistence.dao.core.InputMethodDAOLocal#getAllInputMethods()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<InputMethod> getAllInputMethods() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllInputMethods/InputMethodDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(InputMethod.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllInputMethods/InputMethodDAO ==");
        }
    }

}

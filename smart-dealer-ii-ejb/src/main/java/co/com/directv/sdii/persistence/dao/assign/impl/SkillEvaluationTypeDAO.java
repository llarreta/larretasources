package co.com.directv.sdii.persistence.dao.assign.impl;

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
import co.com.directv.sdii.model.pojo.SkillEvaluationType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SkillEvaluationType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SkillEvaluationType
 * @see co.com.directv.sdii.model.hbm.SkillEvaluationType.hbm.xml
 */
@Stateless(name="SkillEvaluationTypeDAOLocal",mappedName="ejb/SkillEvaluationTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillEvaluationTypeDAO extends BaseDao implements SkillEvaluationTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SkillEvaluationTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal#createSkillEvaluationType(co.com.directv.sdii.model.pojo.SkillEvaluationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSkillEvaluationType/SkillEvaluationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SkillEvaluationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillEvaluationType/SkillEvaluationTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal#updateSkillEvaluationType(co.com.directv.sdii.model.pojo.SkillEvaluationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSkillEvaluationType/SkillEvaluationTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SkillEvaluationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillEvaluationType/SkillEvaluationTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal#deleteSkillEvaluationType(co.com.directv.sdii.model.pojo.SkillEvaluationType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkillEvaluationType(SkillEvaluationType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSkillEvaluationType/SkillEvaluationTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SkillEvaluationType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SkillEvaluationType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillEvaluationType/SkillEvaluationTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal#getSkillEvaluationTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillEvaluationType getSkillEvaluationTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSkillEvaluationTypeByID/SkillEvaluationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillEvaluationType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SkillEvaluationType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSkillEvaluationTypeByID/SkillEvaluationTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillEvaluationTypeByID/SkillEvaluationTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillEvaluationTypeDAOLocal#getAllSkillEvaluationTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillEvaluationType> getAllSkillEvaluationTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSkillEvaluationTypes/SkillEvaluationTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillEvaluationType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSkillEvaluationTypes/SkillEvaluationTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSkillEvaluationTypes/SkillEvaluationTypeDAO ==");
        }
    }

}

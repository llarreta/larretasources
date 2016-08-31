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
import co.com.directv.sdii.model.pojo.SkillModeType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SkillModeType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SkillModeType
 * @see co.com.directv.sdii.model.hbm.SkillModeType.hbm.xml
 */
@Stateless(name="SkillModeTypeDAOLocal",mappedName="ejb/SkillModeTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SkillModeTypeDAO extends BaseDao implements SkillModeTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SkillModeTypeDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#createSkillModeType(co.com.directv.sdii.model.pojo.SkillModeType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSkillModeType/SkillModeTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SkillModeType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSkillModeType/SkillModeTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#updateSkillModeType(co.com.directv.sdii.model.pojo.SkillModeType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSkillModeType/SkillModeTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SkillModeType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSkillModeType/SkillModeTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#deleteSkillModeType(co.com.directv.sdii.model.pojo.SkillModeType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSkillModeType(SkillModeType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSkillModeType/SkillModeTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SkillModeType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SkillModeType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSkillModeType/SkillModeTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#getSkillModeTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SkillModeType getSkillModeTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSkillModeTypeByID/SkillModeTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillModeType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SkillModeType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSkillModeTypeByID/SkillModeTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillModeTypeByID/SkillModeTypeDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#getAllSkillModeTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SkillModeType> getAllSkillModeTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSkillModeTypes/SkillModeTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillModeType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSkillModeTypes/SkillModeTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSkillModeTypes/SkillModeTypeDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SkillModeTypeDAOLocal#getSkillModeTypeByCode(java.lang.String)
	 */
	@Override
	public SkillModeType getSkillModeTypeByCode(String skillModeTypeCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSkillModeTypeByID/SkillModeTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SkillModeType.class.getName());
        	stringQuery.append(" entity where entity.code = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", skillModeTypeCode);
            
            return (SkillModeType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSkillModeTypeByID/SkillModeTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSkillModeTypeByID/SkillModeTypeDAO ==");
        }
	}

}

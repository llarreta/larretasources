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

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.CoverageType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad CoverageType
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.CoverageType
 * @see co.com.directv.sdii.model.hbm.CoverageType.hbm.xml
 */
@Stateless(name="CoverageTypeDAOLocal",mappedName="ejb/CoverageTypeDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CoverageTypeDAO extends BaseDao implements CoverageTypeDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(CoverageTypeDAO.class);
    
    /**
     * objetos cacheados con los tipos de covertura de la aplicación
     */
    private CoverageType coverageTypeOccasional, coverageTypePermanent;
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#createCoverageType(co.com.directv.sdii.model.pojo.CoverageType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createCoverageType/CoverageTypeDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el CoverageType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createCoverageType/CoverageTypeDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#updateCoverageType(co.com.directv.sdii.model.pojo.CoverageType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateCoverageType/CoverageTypeDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el CoverageType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateCoverageType/CoverageTypeDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#deleteCoverageType(co.com.directv.sdii.model.pojo.CoverageType)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCoverageType(CoverageType obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteCoverageType/CoverageTypeDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from CoverageType entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el CoverageType ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteCoverageType/CoverageTypeDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#getCoverageTypesByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CoverageType getCoverageTypeByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCoverageTypeByID/CoverageTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CoverageType.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (CoverageType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getCoverageTypeByID/CoverageTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCoverageTypeByID/CoverageTypeDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#getCoverageTypeOccasional()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CoverageType getCoverageTypeOccasional() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(coverageTypeOccasional == null) {
    		coverageTypeOccasional = getCoverageTypeByCode(CodesBusinessEntityEnum.COVERAGE_TYPE_OCASSIONAL.getCodeEntity());
    	}
    	return coverageTypeOccasional;
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#getCoverageTypePermanent()
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CoverageType getCoverageTypePermanent() throws DAOServiceException, DAOSQLException, PropertiesException {
    	if(coverageTypePermanent == null) {
    		coverageTypePermanent = getCoverageTypeByCode(CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
    	}
    	return coverageTypePermanent;
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#getCoverageTypeByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CoverageType getCoverageTypeByCode(String code) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getCoverageTypeByCode/CoverageTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CoverageType.class.getName());
        	stringQuery.append(" entity where entity.code = :code");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("code", code);

            return (CoverageType) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getCoverageTypeByCode/CoverageTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getCoverageTypeByCode/CoverageTypeDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal#getAllCoverageTypes()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CoverageType> getAllCoverageTypes() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllCoverageTypes/CoverageTypeDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(CoverageType.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllCoverageTypes/CoverageTypeDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllCoverageTypes/CoverageTypeDAO ==");
        }
    }

}

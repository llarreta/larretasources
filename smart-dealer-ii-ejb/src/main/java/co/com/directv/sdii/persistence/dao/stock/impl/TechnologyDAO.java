package co.com.directv.sdii.persistence.dao.stock.impl;

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
import co.com.directv.sdii.model.pojo.Technology;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad Technology
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Technology
 * @see co.com.directv.sdii.model.hbm.Technology.hbm.xml
 */
@Stateless(name="TechnologyDAOLocal",mappedName="ejb/TechnologyDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TechnologyDAO extends BaseDao implements TechnologyDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(TechnologyDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#createTechnology(co.com.directv.sdii.model.pojo.Technology)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createTechnology(Technology obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createTechnology/TechnologyDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error creando el Technology ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createTechnology/TechnologyDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#updateTechnology(co.com.directv.sdii.model.pojo.Technology)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateTechnology(Technology obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateTechnology/TechnologyDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Technology ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateTechnology/TechnologyDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#deleteTechnology(co.com.directv.sdii.model.pojo.Technology)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteTechnology(Technology obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteTechnology/TechnologyDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from Technology entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error eliminando el Technology ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteTechnology/TechnologyDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#getTechnologysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Technology getTechnologyByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getTechnologyByID/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Technology.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (Technology) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTechnologyByID/TechnologyDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#getAllTechnologys()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Technology> getAllTechnologies() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllTechnologys/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Technology.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllTechnologys/TechnologyDAO ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#getActiveTechnologys()
     */
    @Override
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Technology> getActiveTechnologies() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllTechnologies/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Technology.class.getName());
        	stringQuery.append(" tc where tc.isActive = :isActiveCode");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("isActiveCode", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
        	return query.list();
            
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllTechnologies/TechnologyDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#getTechnologyByCode(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Technology getTechnologyByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getTechnologyByCode/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Technology.class.getName());
        	stringQuery.append(" entity where entity.code = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (Technology) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTechnologyByCode/TechnologyDAO ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.TechnologyDAOLocal#getTechnologyByIbsCode(java.lang.String)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Technology getTechnologyByIbsCode(String code)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getTechnologyByIbsCode/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Technology.class.getName());
        	stringQuery.append(" entity where entity.ibsCode = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code);

            return (Technology) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getTechnologyByIbsCode/TechnologyDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getTechnologyByIbsCode/TechnologyDAO ==");
        }
	}


	/**
	 * Metodo: Obtiene la informacion de tecnologias que sean IRD
	 * @return 
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author aharker
	 */
	@Override
	public List<Technology> getAllIRDTechnologies() throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio getAllIRDTechnologies/TechnologyDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select t from ");
        	stringQuery.append(Technology.class.getName());
        	stringQuery.append(" t where t.isIRDTechnology = :isIRDTechnology order by t.id desc ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("isIRDTechnology", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

            return (List<Technology>) query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllIRDTechnologies/TechnologyDAO ==");
        }
	}

}

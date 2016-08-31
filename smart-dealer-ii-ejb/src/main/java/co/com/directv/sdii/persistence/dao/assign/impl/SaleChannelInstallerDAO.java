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
import co.com.directv.sdii.model.pojo.SaleChannelInstaller;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SaleChannelInstaller
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SaleChannelInstaller
 * @see co.com.directv.sdii.model.hbm.SaleChannelInstaller.hbm.xml
 */
@Stateless(name="SaleChannelInstallerDAOLocal",mappedName="ejb/SaleChannelInstallerDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChannelInstallerDAO extends BaseDao implements SaleChannelInstallerDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SaleChannelInstallerDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#createSaleChannelInstaller(co.com.directv.sdii.model.pojo.SaleChannelInstaller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSaleChannelInstaller(SaleChannelInstaller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSaleChannelInstaller/SaleChannelInstallerDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SaleChannelInstaller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChannelInstaller/SaleChannelInstallerDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#updateSaleChannelInstaller(co.com.directv.sdii.model.pojo.SaleChannelInstaller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSaleChannelInstaller(SaleChannelInstaller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSaleChannelInstaller/SaleChannelInstallerDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SaleChannelInstaller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChannelInstaller/SaleChannelInstallerDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#deleteSaleChannelInstaller(co.com.directv.sdii.model.pojo.SaleChannelInstaller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSaleChannelInstaller(SaleChannelInstaller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSaleChannelInstaller/SaleChannelInstallerDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SaleChannelInstaller entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SaleChannelInstaller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChannelInstaller/SaleChannelInstallerDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#getSaleChannelInstallersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChannelInstaller getSaleChannelInstallerByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSaleChannelInstallerByID/SaleChannelInstallerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelInstaller.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SaleChannelInstaller) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSaleChannelInstallerByID/SaleChannelInstallerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChannelInstallerByID/SaleChannelInstallerDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#getAllSaleChannelInstallers()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChannelInstaller> getAllSaleChannelInstallers() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSaleChannelInstallers/SaleChannelInstallerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelInstaller.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSaleChannelInstallers/SaleChannelInstallerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSaleChannelInstallers/SaleChannelInstallerDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelInstallerDAOLocal#deleteInstallersBySaleChannelId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteInstallersBySaleChannelId(Long saleChannelId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteInstallersBySaleChannelId/SaleChannelInstallerDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SaleChannelInstaller entity where entity.saleChanel.id = :anEntityId");
            query.setLong("anEntityId", saleChannelId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SaleChannelInstaller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteInstallersBySaleChannelId/SaleChannelInstallerDAO ==");
        }
	}



	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SaleChannelInstaller> getSaleChannelInstallersBySaleChannelId(
			Long id) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSaleChannelInstallersBySaleChannelId/SaleChannelInstallerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelInstaller.class.getName());
        	stringQuery.append(" entity where entity.saleChanel.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            List<SaleChannelInstaller> result = query.list();
            
            return result;

        } catch (Throwable ex){
			log.error("== Error getSaleChannelInstallersBySaleChannelId/SaleChannelInstallerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChannelInstallersBySaleChannelId/SaleChannelInstallerDAO ==");
        }
	}

}

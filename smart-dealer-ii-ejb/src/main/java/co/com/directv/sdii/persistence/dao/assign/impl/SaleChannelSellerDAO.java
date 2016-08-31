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
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.SaleChannelInstaller;
import co.com.directv.sdii.model.pojo.SaleChannelSeller;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SaleChannelSeller
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SaleChannelSeller
 * @see co.com.directv.sdii.model.hbm.SaleChannelSeller.hbm.xml
 */
@Stateless(name="SaleChannelSellerDAOLocal",mappedName="ejb/SaleChannelSellerDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChannelSellerDAO extends BaseDao implements SaleChannelSellerDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SaleChannelSellerDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#createSaleChannelSeller(co.com.directv.sdii.model.pojo.SaleChannelSeller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSaleChannelSeller/SaleChannelSellerDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SaleChannelSeller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChannelSeller/SaleChannelSellerDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#updateSaleChannelSeller(co.com.directv.sdii.model.pojo.SaleChannelSeller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSaleChannelSeller/SaleChannelSellerDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SaleChannelSeller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChannelSeller/SaleChannelSellerDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#deleteSaleChannelSeller(co.com.directv.sdii.model.pojo.SaleChannelSeller)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSaleChannelSeller(SaleChannelSeller obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSaleChannelSeller/SaleChannelSellerDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SaleChannelSeller entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SaleChannelSeller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChannelSeller/SaleChannelSellerDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#getSaleChannelSellersByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChannelSeller getSaleChannelSellerByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSaleChannelSellerByID/SaleChannelSellerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelSeller.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SaleChannelSeller) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSaleChannelSellerByID/SaleChannelSellerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChannelSellerByID/SaleChannelSellerDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#getAllSaleChannelSellers()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChannelSeller> getAllSaleChannelSellers() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSaleChannelSellers/SaleChannelSellerDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelSeller.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSaleChannelSellers/SaleChannelSellerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSaleChannelSellers/SaleChannelSellerDAO ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#getInstallerDealersBySellerCodeAndCountry(java.lang.Long, java.lang.Long)
     */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Dealer> getInstallerDealersBySellerCodeAndCountry(Long sellerCode, Long countryId) throws DAOServiceException,DAOSQLException {
		 log.debug("== Inicio getSaleChannelSellerByID/SaleChannelSellerDAO ==");
	        Session session = super.getSession();

	        try {
	        	StringBuffer stringQuery = new StringBuffer();
	        	stringQuery.append(" select installer.dealer ");
	        	stringQuery.append("   from " + SaleChannelSeller.class.getName() + " seller,");
	        	stringQuery.append(             SaleChannelInstaller.class.getName() + " installer ");
	        	stringQuery.append(" where seller.status = :sellerActiveStatus ");
	        	stringQuery.append("       and seller.dealer.dealerCode = :sellerCode ");
	        	stringQuery.append("       and seller.saleChanel.country.id = :countryId ");
	        	stringQuery.append("       and installer.status = :installerActive ");
	        	stringQuery.append("       and installer.saleChanel.id = seller.saleChanel.id ");
	        	stringQuery.append("       and installer.dealer.dealerStatus.statusCode = :dealerActiveStatus ");
	        	
	        	Query query = session.createQuery(stringQuery.toString());
	            query.setLong("sellerCode",sellerCode);
	        	query.setLong("countryId",countryId);
	        	query.setString("installerActive", CodesBusinessEntityEnum.SALE_CHANNEL_INSTALLERS_ACTIVE.getCodeEntity());
	        	query.setString("sellerActiveStatus", CodesBusinessEntityEnum.SALE_CHANNEL_SELLERS_ACTIVE.getCodeEntity());
	        	query.setString("dealerActiveStatus", CodesBusinessEntityEnum.DEALER_STATUS_NORMAL.getCodeEntity());

	            return query.list();

	        } catch (Throwable ex){
				log.error("== Error getSaleChannelSellerByID/SaleChannelSellerDAO ==");
				throw this.manageException(ex);
			} finally {
	            log.debug("== Termina getSaleChannelSellerByID/SaleChannelSellerDAO ==");
	        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#deleteSellersBySaleChannelId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSellersBySaleChannelId(Long saleChannelId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio deleteSellersBySaleChannelId/SaleChannelSellerDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SaleChannelSeller entity where entity.saleChanel.id = :anEntityId");
            query.setLong("anEntityId", saleChannelId);
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SaleChannelSeller ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSellersBySaleChannelId/SaleChannelSellerDAO ==");
        }
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SaleChannelSellerDAOLocal#getSaleChannelSellersBySaleChannelId(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<SaleChannelSeller> getSaleChannelSellersBySaleChannelId(
			Long saleChannelId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSaleChannelSellersBySaleChannelId/SaleChannelSellerDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChannelSeller.class.getName());
        	stringQuery.append(" entity where entity.saleChanel.id = :anEntityId ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setLong("anEntityId", saleChannelId);
        	return query.list();
        } catch (Throwable ex){
			log.error("== Error getSaleChannelSellersBySaleChannelId/SaleChannelSellerDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChannelSellersBySaleChannelId/SaleChannelSellerDAO ==");
        }
	}

}

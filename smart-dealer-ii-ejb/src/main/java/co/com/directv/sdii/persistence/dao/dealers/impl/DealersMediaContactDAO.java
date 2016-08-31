package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerMediaContact;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal;

/**
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DealersMediaContact
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealersMediaContact
 * @see co.co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal
 */
@Stateless(name="DealersMediaContactDAOLocal",mappedName="ejb/DealersMediaContactDAOLocal")
public class DealersMediaContactDAO extends BaseDao implements DealersMediaContactDAOLocal {
    
	private final static Logger log = UtilsBusiness.getLog4J(DealersMediaContactDAO.class);
    
    /**
     * Crea un DealerMediaContact
     * @param obj - DealersMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void createDealerMediaContact(DealerMediaContact obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealerMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina createDealerMediaContact/DealersMediaContactDAO ==");
        }
    }

    /**
     * Consulta de DealerMediaContact por ID
     * @param id - Long
     * @return - DealersMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public DealerMediaContact getDealersMediaContactByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealersMediaContactByID/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" where dmc.id = :id");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+DealerMediaContact.class.getName()+" where dmc.id = :id");
            query.setString("id", id.toString());
            Object dealerMedia = query.uniqueResult();

            return (DealerMediaContact) dealerMedia;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealersMediaContactByID/DealersMediaContactDAO ==");
        }
    }

    /**
     *  Actualiza DealerMediaContact especificado
     * @param obj - DealersMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void updateDealersMediaContact(DealerMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateDealersMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina updateDealersMediaContact/DealersMediaContactDAO ==");
        }

    }

    /**
     * Elimina DealerMediaContact del sistema
     * @param obj - DealersMediaContact
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    public void deleteDealersMediaContact(DealerMediaContact obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteDealersMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina deleteDealersMediaContact/DealersMediaContactDAO ==");
        }

    }

    /**
     * Consulta todos los DealerMediaContact
     * @return - List<DealersMediaContact>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	public List<DealerMediaContact> getAllDealersMediaContact() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealersMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            //return session.createQuery("from "+DealerMediaContact.class.getName()).list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersMediaContact/DealersMediaContactDAO ==");
        }
    }

    @SuppressWarnings("unchecked")
	public List<DealerMediaContact> getDealersMediaContactByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllDealersMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        List<DealerMediaContact> list = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" dmc where dmc.dealer.id = :aDmcDealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("from "+DealerMediaContact.class.getName()+" dmc where dmc.dealer.id = :aDmcDealerId");
            query.setLong("aDmcDealerId", dealerId);
            list = query.list();

            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersMediaContact/DealersMediaContactDAO ==");
        }
    }

    public void deleteDealersMediaContactByDealerId(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteDealersMediaContactByDealerId/DealersMediaContactDAO ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" dmc where dmc.dealer.id = :aDmcDealerId");
        	Query query = session.createQuery(stringQuery.toString());
            //Query query = session.createQuery("delete from " + DealerMediaContact.class.getName() + " dmc where dmc.dealer.id = :aDmcDealerId");
            query.setLong("aDmcDealerId", id);
            query.executeUpdate();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina deleteDealersMediaContactByDealerId/DealersMediaContactDAO ==");
        }
    }
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers#getLegalRepresentativeByCountryId(java.lang.Long)
	 */
    public List<DealerMediaContact> getLegalRepresentativeByCountryId(Long countryId) throws DAOServiceException, DAOSQLException{
    	log.debug("== Inicia getAllDealersMediaContact/DealersMediaContactDAO ==");
        Session session = getSession();
        List<DealerMediaContact> list = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select dmc from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" dmc,");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d where ");
        	stringQuery.append("d.dealerType.dealerTypeCode= :dealerTypeCode ");
        	stringQuery.append("and d.postalCode.city.state.country.id= :countryId ");
        	stringQuery.append("and dmc.mediaContactType.mediaCode= :mediaCode ");
        	stringQuery.append("and dmc.dealer.id=d.id ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("dealerTypeCode", CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setString("mediaCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity());
            list = query.list();

            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersMediaContact/DealersMediaContactDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal#getLogicalAnaliticByCountryId(java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerMediaContact> getLogicalAnaliticByCountryId(Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getLogicalAnaliticByCountryId/DealersMediaContactDAO ==");
        Session session = getSession();
        List<DealerMediaContact> list = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("select dmc from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" dmc,");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d where ");
        	stringQuery.append("d.dealerType.dealerTypeCode= :dealerTypeCode ");
        	stringQuery.append("and d.postalCode.city.state.country.id= :countryId ");
        	stringQuery.append("and dmc.mediaContactType.mediaCode= :mediaCode ");
        	stringQuery.append("and dmc.dealer.id=d.id ");
        	Query query = session.createQuery(stringQuery.toString());
        	query.setString("dealerTypeCode", CodesBusinessEntityEnum.CODE_DEALER_TYPE_LOGISTIC_OPERATOR.getCodeEntity());
            query.setLong("countryId", countryId);
            query.setString("mediaCode", CodesBusinessEntityEnum.MEDIA_CONTACT_TYPE_EMAIL_CODE.getCodeEntity());
            list = query.list();

            return list;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getLogicalAnaliticByCountryId/DealersMediaContactDAO ==");
        }
	}
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal#getDealersMediaContactByDealerCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<DealerMediaContact> getDealersMediaContactByDealerCode(Long dealerCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getDealersMediaContactByDealerCode/DealersMediaContactDAO ==");
        Session session = getSession();
        List<DealerMediaContact> list = null;
        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(DealerMediaContact.class.getName());
        	stringQuery.append(" dmc where dmc.dealer.dealerCode = :dealerCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerCode", dealerCode);
            list = query.list();

            return list;
        } catch (Throwable ex){
			log.error("== Error getDealersMediaContactByDealerCode/DealersMediaContactDAO ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealersMediaContactByDealerCode/DealersMediaContactDAO ==");
        }
    }
}

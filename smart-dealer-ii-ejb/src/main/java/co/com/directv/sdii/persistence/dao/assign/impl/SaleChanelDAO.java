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
import co.com.directv.sdii.model.pojo.SaleChanel;
import co.com.directv.sdii.model.pojo.SaleChannelInstaller;
import co.com.directv.sdii.model.pojo.SaleChannelSeller;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad SaleChanel
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.SaleChanel
 * @see co.com.directv.sdii.model.hbm.SaleChanel.hbm.xml
 */
@Stateless(name="SaleChanelDAOLocal",mappedName="ejb/SaleChanelDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleChanelDAO extends BaseDao implements SaleChanelDAOLocal {

    private final static Logger log = UtilsBusiness.getLog4J(SaleChanelDAO.class);
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#createSaleChanel(co.com.directv.sdii.model.pojo.SaleChanel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createSaleChanel/SaleChanelDAO ==");
        Session session = super.getSession();
        try {
            session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error creando el SaleChanel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createSaleChanel/SaleChanelDAO ==");
        }
    }
    
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#updateSaleChanel(co.com.directv.sdii.model.pojo.SaleChanel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio updateSaleChanel/SaleChanelDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error actualizando el SaleChanel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateSaleChanel/SaleChanelDAO ==");
        }
    }
    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#deleteSaleChanel(co.com.directv.sdii.model.pojo.SaleChanel)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSaleChanel(SaleChanel obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio deleteSaleChanel/SaleChanelDAO ==");
        Session session = super.getSession();
        try {
            Query query = session.createQuery("delete from SaleChanel entity where entity.id = :anEntityId");
            query.setLong("anEntityId", obj.getId());
            query.executeUpdate();
            super.doFlush(session);
        } catch (Throwable ex) {
            log.error("== Error eliminando el SaleChanel ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteSaleChanel/SaleChanelDAO ==");
        }
    }
    
     /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#getSaleChanelsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public SaleChanel getSaleChanelByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getSaleChanelByID/SaleChanelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChanel.class.getName());
        	stringQuery.append(" entity where entity.id = :anId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("anId", id);

            return (SaleChanel) query.uniqueResult();

        } catch (Throwable ex){
			log.error("== Error getSaleChanelByID/SaleChanelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChanelByID/SaleChanelDAO ==");
        }
    }

    
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#getAllSaleChanels()
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<SaleChanel> getAllSaleChanels() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllSaleChanels/SaleChanelDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChanel.class.getName());
        	return session.createQuery(stringQuery.toString()).list();
            
        } catch (Throwable ex){
			log.error("== Error getAllSaleChanels/SaleChanelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getAllSaleChanels/SaleChanelDAO ==");
        }
    }



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#getSaleChannelsByFilters(co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SaleChannelResponse getSaleChannelsByFilters(
			GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfo requestCollInfoPojo)
			throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getSaleChannelsByFilters/SaleChanelDAO ==");
        Session session = super.getSession();
        try {
        	//Realizando el query de conteo:
        	Query countQuery = buildGetSaleChannelsByFiltersQuery(request ,"select count(*) from ", session);
        	int totalRowCount = ((Long)countQuery.uniqueResult()).intValue();
        	
        	//Realizando el query real:
        	Query query = buildGetSaleChannelsByFiltersQuery(request ,"from ", session);
        	query.setFirstResult(requestCollInfoPojo.getFirstResult());
        	query.setMaxResults(requestCollInfoPojo.getMaxResults());
        	
        	List<SaleChanel> listPojo = query.list();
        	
        	SaleChannelResponse result = new SaleChannelResponse();
        	result.setChannelPojos(listPojo);
        	populatePaginationInfo(result, requestCollInfoPojo.getPageSize(), requestCollInfoPojo.getPageIndex(), listPojo.size(), totalRowCount);
        	return result;
        } catch (Throwable ex){
			log.error("== Error getSaleChannelsByFilters/SaleChanelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChannelsByFilters/SaleChanelDAO ==");
        }
	}



	private Query buildGetSaleChannelsByFiltersQuery(GetSaleChannelsByFiltersRequestDTO request, String headerQuery, Session session) {
		StringBuffer stringQuery = new StringBuffer(headerQuery);
    	stringQuery.append(SaleChanel.class.getName());
    	stringQuery.append(" sc where sc.country.id = :aCountryId ");
    	
    	boolean saleChannelCodeSpecified = request.getSaleChannelCode() != null && request.getSaleChannelCode().trim().length() > 0;
    	boolean saleChannelNameSpecified = request.getSaleChannelName() != null && request.getSaleChannelName().trim().length() > 0;
    	boolean sellerDealerCodeSpecified = request.getSellerDealerCode() != null && request.getSellerDealerCode().longValue() > 0;
    	boolean installerDealerCodeSpecified = request.getInstallerDealerCode() != null && request.getInstallerDealerCode().longValue() > 0;
    	
    	if(saleChannelCodeSpecified){
    		stringQuery.append(" and upper(sc.code)= :aSaleChannelCode ");
    	}
    	
    	if(saleChannelNameSpecified){
    		stringQuery.append(" and upper(sc.name)= :aSaleChannelName ");
    	}
    	
    	if(sellerDealerCodeSpecified){
    		stringQuery.append(" and sc.id in (select ssc.saleChanel.id from ");
    		stringQuery.append(SaleChannelSeller.class.getName());
    		stringQuery.append(" ssc where ssc.dealer.dealerCode =:aSellerCode) ");
    	}
    	
    	if(installerDealerCodeSpecified){
    		stringQuery.append(" and sc.id in (select isc.saleChanel.id from ");
    		stringQuery.append(SaleChannelInstaller.class.getName());
    		stringQuery.append(" isc where isc.dealer.dealerCode =:anInstallerCode) ");
    	}
    	
    	Query query = session.createQuery(stringQuery.toString());
    	
    	query.setLong("aCountryId", request.getCountryId());
    	
    	if(saleChannelCodeSpecified){
    		query.setString("aSaleChannelCode", request.getSaleChannelCode().toUpperCase());
    	}
    	
    	if(saleChannelNameSpecified){
    		query.setString("aSaleChannelName", request.getSaleChannelName().toUpperCase());
    	}
    	
    	if(sellerDealerCodeSpecified){
    		query.setLong("aSellerCode", request.getSellerDealerCode());
    	}
    	
    	if(installerDealerCodeSpecified){
    		query.setLong("anInstallerCode", request.getInstallerDealerCode());
    	}
    	
    	return query;
	}



	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.assign.SaleChanelDAOLocal#getSaleChanelByCode(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SaleChanel getSaleChanelByCode(String code)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getSaleChanelByCode/SaleChanelDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(SaleChanel.class.getName());
        	stringQuery.append(" entity where upper(entity.code) = :aCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aCode", code.toUpperCase());

            List<SaleChanel> saleChannels = query.list();
            
            if(saleChannels.isEmpty()){
            	return null;
            }
            
            return saleChannels.get(0);

        } catch (Throwable ex){
			log.error("== Error getSaleChanelByCode/SaleChanelDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getSaleChanelByCode/SaleChanelDAO ==");
        }
	}

}

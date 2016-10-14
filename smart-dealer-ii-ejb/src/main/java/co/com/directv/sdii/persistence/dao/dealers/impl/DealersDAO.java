package co.com.directv.sdii.persistence.dao.dealers.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.DealerInfoDTO;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;
import co.com.directv.sdii.model.dto.MessageCoreAllocatorDTO;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.DealerDetail;
import co.com.directv.sdii.model.pojo.DealerType;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de Dealers
 * 
 * Fecha de Creación: Mar 2, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Dealers
 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal
 */
@Stateless(name="DealersDAOLocal",mappedName="ejb/DealersDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealersDAO extends BaseDao implements DealersDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(DealersDAO.class);

    /**
     * Crea una compa�ia en el sistema
     * @param obj - Dealers
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealer(Dealer obj) throws DAOServiceException, DAOSQLException {

        log.debug("== Inicio createDealer/DAODealerBean ==");
        Session session = getSession();
        try {
        	if(obj.getIsSeller()==null) 
        		obj.setIsSeller("S");
        	if(obj.getIsInstaller()==null) 
        		obj.setIsInstaller("S");
            
        	session.save(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina createDealer/DAODealerBean ==");
        }
    }

    /**
     * Obtiene un dealer con el id especificado
     * @param id - Long
     * @return - Dealers
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Dealer getDealerByID(Long id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerByID/DAODealerBean ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.id = '");
        	stringQuery.append(id);
        	stringQuery.append("' ");
        	Object obj = session.createQuery(stringQuery.toString()).uniqueResult();
            if (obj != null) {
                return (Dealer) obj;
            }
            return null;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByID/DAODealerBean ==");
        }
    }

    /**
     * Obtiene los dealers que poseen el nombre especificado
     * @param name - String
     * @return - List<Dealers>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getDealerByName(String name) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getDealerByName/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.dealerName = :dealerName");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("dealerName", name);
            return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByName/DAODealerBean ==");
        }
    }

    /**
     * Actualiza un dealer especificado
     * @param obj - Dealers
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateDealer(Dealer obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia updateDealer/DAODealerBean ==");
        Session session = getSession();

        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina updateDealer/DAODealerBean ==");
        }

    }

    /**
     * Elimina un dealer del sistema
     * @param obj - Dealers
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteDealer(Dealer obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteDealer/DAODealerBean ==");
        Session session = getSession();

        try {
            session.delete(obj);
            this.doFlush(session);
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina deleteDealer/DAODealerBean ==");
        }

    }

    /**
     * Obtiene todos los dealers del sistema
     * @return - List<Dealers>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAll/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" (d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
        	stringQuery.append("d.depotCode, d.dealerCode) from  ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d");
        	Query query = session.createQuery(stringQuery.toString());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAll/DAODealerBean ==");
        }
    }

    /**
     * Obtiene todos los dealers del sistema que se encuentren activos
     * @return - List<Dealers>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActive() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActive/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
        }finally {
            log.debug("== Termina getAllActive/DAODealerBean ==");
        }
    }

    /**
     * Obtiene todos los dealers del sistema que se encuentren activos en un c�digo postal
     * 
     * @param postalCode
     * @return - List<Dealers>
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveByPostalCode(PostalCode postalCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByPostalCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? and ");
        	stringQuery.append("d.postalCode.postalCodeCode = ? ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setString(1, postalCode.getPostalCodeCode());
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByPostalCode/DAODealerBean ==");
        }
    }
    
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getDealerEvenOrOddByCountryId(Long countryId,String codeEvenOrOdd) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByPostalCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select distinct d "); 
        	stringQuery.append("   from " + DealerDetail.class.getName() + " dd  "); 
        	stringQuery.append("          inner join dd.dealer d  ");
        	stringQuery.append("          inner join d.postalCode p  ");
        	stringQuery.append("          inner join p.city c  ");
        	stringQuery.append("          inner join c.state s  ");
        	stringQuery.append("          inner join s.country cu  ");
        	stringQuery.append("  where cu.id = :aCountryId   ");
        	stringQuery.append("        and dd.attendTypeOddEven = :aCodeEvenOrOdd ");       	          
        	
            Query query = session.createQuery(stringQuery.toString());
        	query.setLong("aCountryId", countryId);
            query.setString("aCodeEvenOrOdd", codeEvenOrOdd);
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByPostalCode/DAODealerBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveDealerByUserIdAndDealerTypeCode(java.lang.Long, java.lang.String)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveDealerByUserIdAndDealerTypeCode(Long userId,Long countryId,String dealerTypeCode,String warehouseTypeCode) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getAllActiveDealerByUserIdAndDealerTypeCode/DAODealerBean ==");
        Session session = getSession();
        boolean dealerTypeCodeSpecified = false,warehouseTypeCodeSpecified = false,countryIdSpecified = false;

        try {
        	StringBuffer stringQuery = new StringBuffer();

        	stringQuery.append(" from "+Dealer.class.getName()+" entity ");
            stringQuery.append(" where entity.dealerStatus.statusCode = :aStatusCode "); 
            stringQuery.append(" and (exists(select 1 "); 
            stringQuery.append("               from User u ");
            stringQuery.append("              where u.id = :userId and u.dealer.id is null) ");
            stringQuery.append("      or exists(select 1  ");
            stringQuery.append("               from User u ");
            stringQuery.append("              where u.id = :userId and (u.dealer.id=entity.id or u.dealer.id=entity.dealer.id) )) ");
            
            if(dealerTypeCode != null && dealerTypeCode.trim().length()>0){
            	dealerTypeCodeSpecified=true;
            	stringQuery.append("              and entity.dealerType.dealerTypeCode = :typeLogisticsOperator ");
            }
            
            if(warehouseTypeCode != null && warehouseTypeCode.trim().length()>0){
            	warehouseTypeCodeSpecified=true;
            	stringQuery.append("              and exists(select 1 "); 
            	stringQuery.append("               			   from "+Warehouse.class.getName()+" wh ");
            	stringQuery.append("              			  where wh.dealerId.id=entity.id and wh.warehouseType.whTypeCode = :warehouseTypeCode ) ");	
            	
            }
            
            if(countryId != null && countryId.longValue()>0){
            	countryIdSpecified=true;
            	stringQuery.append("              and entity.postalCode.city.state.country.id = :countryId ");
            }
            
            Query query = session.createQuery(stringQuery.toString());
            query.setString("aStatusCode", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setLong("userId", userId);
            
            if(dealerTypeCodeSpecified)
            	query.setString("typeLogisticsOperator", dealerTypeCode);
            
            if(warehouseTypeCodeSpecified)
            	query.setString("warehouseTypeCode", warehouseTypeCode);
            
            if(countryIdSpecified)
            	query.setLong("countryId", countryId);
            
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveDealerByUserIdAndDealerTypeCode/DAODealerBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveByDealerTypeAndPostalCode(DealerType dealerType, PostalCode postalCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByDealerTypeAndPostalCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? and ");
        	stringQuery.append("d.postalCode.postalCodeCode = ? and ");
        	stringQuery.append("d.dealerType.dealerTypeCode = ?");
            Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setString(1, postalCode.getPostalCodeCode());
            query.setString(2, dealerType.getDealerTypeCode());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByDealerTypeAndPostalCode/DAODealerBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveByDealerTypeAndPostalCodeAndIsAlloc(co.com.directv.sdii.model.pojo.DealerType, co.com.directv.sdii.model.pojo.PostalCode)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveByDealerTypeAndPostalCodeAndIsAlloc(DealerType dealerType, PostalCode postalCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByDealerTypeAndPostalCodeAndIsAlloc/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? and ");
        	stringQuery.append("d.postalCode.postalCodeCode = ? and ");
        	stringQuery.append("d.dealerType.dealerTypeCode = ? and ");
        	stringQuery.append("d.dealerType.isAlloc = ? ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setString(1, postalCode.getPostalCodeCode());
            query.setString(2, dealerType.getDealerTypeCode());
            query.setString(3, CodesBusinessEntityEnum.IS_ALLOCABLE.getCodeEntity());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByDealerTypeAndPostalCodeAndIsAlloc/DAODealerBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveByPostalCodeAndIsAlloc(co.com.directv.sdii.model.pojo.PostalCode)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveByPostalCodeAndIsAlloc(PostalCode postalCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? and ");
        	stringQuery.append("d.postalCode.postalCodeCode = ? and ");
        	stringQuery.append("d.dealerType.isAlloc = ? ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setString(1, postalCode.getPostalCodeCode());
            query.setString(2, CodesBusinessEntityEnum.IS_ALLOCABLE.getCodeEntity());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveByPostalCodeAndIsAlloc(co.com.directv.sdii.model.pojo.PostalCode)
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getAllActiveByPostalCodeAndIsAllocAndAttendsBuildings(PostalCode postalCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = :statusCode and ");
        	stringQuery.append("d.postalCode.postalCodeCode = :postalCodeCode and ");
        	stringQuery.append("d.dealerType.isAlloc = :isAlloc ");
        	stringQuery.append("and d.id in ");
        	stringQuery.append("( select dd.dealerId from ");
        	stringQuery.append( DealerDetail.class.getName() );
        	stringQuery.append(" dd "); 
        	stringQuery.append("where dd.dealer.postalCode.postalCodeCode = :postalCodeCode ");
        	stringQuery.append("and dd.attendBuildings = :attendBuildings ");
        	stringQuery.append(" ) ");        	
        	//  
            Query query = session.createQuery(stringQuery.toString());

            query.setString("statusCode", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity() );
            query.setString("postalCodeCode", postalCode.getPostalCodeCode());
            query.setString("isAlloc", CodesBusinessEntityEnum.IS_ALLOCABLE.getCodeEntity());
            query.setString("attendBuildings", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveWhoAttendsAPostalZoneAndIsAllocAndAttendsBuildings(co.com.directv.sdii.model.pojo.PostalCode)
     */
    @SuppressWarnings("unchecked")
 	public List<Dealer> 
	getAllActiveWhoAttendsAPostalZoneAndIsAllocAndAttendsBuildings(PostalCode postalCode)
	throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select dc.dealer ");
        	stringQuery.append("   from " + DealerCoverage.class.getName() + " dc, ");
        	stringQuery.append(             DealerDetail.class.getName() + " det ");
        	stringQuery.append("  WHERE det.dealer.id = dc.dealer.id ");
        	stringQuery.append("        AND dc.postalCode.id = :postalCodeId ");
        	stringQuery.append("        AND dc.dealer.dealerStatus.statusCode = :statusCode ");
        	stringQuery.append("        AND dc.dealer.dealerType.isAlloc = :isAlloc ");
        	stringQuery.append("        AND det.attendBuildings = :attendBuildings ");
        	stringQuery.append("        AND dc.status = :aDcActiveStatus ");

            Query query = session.createQuery(stringQuery.toString());
            
            query.setLong("postalCodeId", postalCode.getId());
            query.setString("statusCode", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity() );
            query.setString("isAlloc", CodesBusinessEntityEnum.IS_ALLOCABLE.getCodeEntity()); 
            query.setString("attendBuildings", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setString("aDcActiveStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByPostalCodeAndIsAlloc/DAODealerBean ==");
        }	
	}
 	
    /**
     * Obtiene un dealer con el id especificado
     * @param id - String
     * @return - Dealers
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Dealer getDealerByDepotID(String id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerByID/DAODealerBean ==");
        Session session = getSession();
        Dealer result = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.depotCode = :aDepotId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setString("aDepotId", id);
            List<Dealer> dealersList = query.list();

            if (dealersList == null || dealersList.size() <= 0) {
                return null;
            }
            result = dealersList.get(0);
            return result;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByID/DAODealerBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDepotCodeOrDealerCode(java.lang.String, java.lang.Long)
     */
    public Dealer getDealerByDepotCodeOrDealerCode(String depotCode, Long dealerCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerByDepotCodeOrDealerCode/DAODealerBean ==");
        Session session = getSession();
        Dealer obj = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.");

           if(depotCode != null && !depotCode.trim().equalsIgnoreCase("")){
        	   stringQuery.append("depotCode = :aDealerParam");
           }else if(dealerCode != null && dealerCode != 0L){
        	    stringQuery.append("dealerCode = :aDealerParam");
            }

           Query query = session.createQuery(stringQuery.toString());

            if(depotCode != null && !depotCode.trim().equalsIgnoreCase("")){
                query.setString("aDealerParam", depotCode);
            }else if(dealerCode != null && dealerCode != 0L){
                query.setLong("aDealerParam", dealerCode);
            }

            obj = (Dealer) query.uniqueResult();

            return obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByDepotCodeOrDealerCode/DAODealerBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByBranchId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	public List<Dealer> getActiveDealerBranchesByDealerId(Long dealerPrincipalId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getDealerByBranchId/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" (d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
        	stringQuery.append("d.depotCode, d.dealerCode) from "+Dealer.class.getName() + " d where (d.dealer.id = :aPrincipalId or d.id = :aPrincipalId) and d.dealerStatus.statusCode = :aDealerStatus");
        	if( isSeller!= null )
        		stringQuery.append(" and d.isSeller = :aIsSeller ");
        	if( isInstaller!=null )
        		stringQuery.append(" and d.isInstaller = :aIsInstaller ");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aPrincipalId", dealerPrincipalId);
            query.setString("aDealerStatus", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            if( isSeller!=null )
            	query.setString("aIsSeller", isSeller);
            if( isInstaller!=null )
            	query.setString("aIsInstaller", isInstaller);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByBranchId/DAODealerBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDealerCode(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	public Dealer getDealerByDealerCode(Long dealerCode) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerByDealerCode/DAODealerBean ==");
        Session session = getSession();
        Dealer result = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.dealerCode = :aDealerCode");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aDealerCode", dealerCode);
            List<Dealer> dealersList = query.list();

            if (dealersList == null || dealersList.size() <= 0) {
                return null;
            }
            result = dealersList.get(0);
            return result;

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getDealerByDealerCode/DAODealerBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllDealersOnlyBasicInfo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Dealer> getAllDealersOnlyBasicInfo()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealersOnlyBasicInfo/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" (d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
        	stringQuery.append("d.depotCode, d.dealerCode) from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d");
        	Query query = session.createQuery(stringQuery.toString());
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersOnlyBasicInfo/DAODealerBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllDealersByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Dealer> getAllDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealersOnlyBasicInfo/DAODealerBean ==");
        Session session = getSession();

        try {
            StringBuffer sb = new StringBuffer("select new ");
            sb.append(Dealer.class.getName());
            sb.append("(d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
            sb.append("d.depotCode, d.dealerCode) from ");
            sb.append(Dealer.class.getName());
            sb.append(" d where d.postalCode.city.state.country.id = :aCountryId ");
            if( isSeller!=null)
            	sb.append(" and d.isSeller = :aIsSeller ");
            if( isInstaller!=null)
            	sb.append(" and d.isInstaller = :aIsInstaller ");
            sb.append(" order by d.dealerName ");
            Query query = session.createQuery(sb.toString());
            
            query.setLong("aCountryId", countryId);
            if( isSeller!=null)
            	query.setString("aIsSeller",isSeller);
            if( isInstaller!=null)
            	query.setString("aIsInstaller", isInstaller);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersOnlyBasicInfo/DAODealerBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllDealersByCountryId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Dealer> getMajorDealersByCountryId(Long countryId, String isSeller, String isInstaller)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealersOnlyBasicInfo/DAODealerBean ==");
        Session session = getSession();

        try {
            StringBuffer sb = new StringBuffer("select new ");
            sb.append(Dealer.class.getName());
            sb.append("(d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
            sb.append("d.depotCode, d.dealerCode) from ");
            sb.append(Dealer.class.getName());
            sb.append(" d where d.postalCode.city.state.country.id = :aCountryId  ");
            sb.append(" and d.dealer is null ");
            if( isSeller!=null )
            	sb.append(" and d.isSeller = :aIsSeller ");
            if( isInstaller!=null )
            	sb.append(" and d.isInstaller = :aIsInstaller ");
            sb.append(" order by d.dealerName");
            Query query = session.createQuery(sb.toString());
            
            query.setLong("aCountryId", countryId);
            if( isSeller!=null )
            	query.setString("aIsSeller", isSeller);
            if( isInstaller!=null )
            	query.setString("aIsInstaller", isInstaller);
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getAllDealersOnlyBasicInfo/DAODealerBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerIdByDealerCode(java.lang.Long)
	 */
	@Override
	public Long getDealerIdByDealerCode(Long dealerCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerIdByDealerCode/DAODealerBean ==");
        Session session = getSession();

        try {
            StringBuffer sb = new StringBuffer("select d.id ");
            sb.append("from ");
            sb.append(Dealer.class.getName());
            sb.append(" d where d.dealerCode = :aDealerCode");
            Query query = session.createQuery(sb.toString());
            
            query.setLong("aDealerCode", dealerCode);
            Long result = (Long)query.uniqueResult(); 
            return result;
        } catch (Throwable ex){
			log.error("== Error en getDealerIdByDealerCode ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerIdByDealerCode/DAODealerBean ==");
        }
	}

	 
	public Long getDealerIdByDealerCodeAndCountryId(Long dealerCode, Long countryId)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerIdByDealerCode/DAODealerBean ==");
		Session session = getSession();

		try {
			StringBuffer sb = new StringBuffer("select d.id ");
			sb.append("from ");
			sb.append(Dealer.class.getName());
			sb.append(" d where d.dealerCode = :aDealerCode and d.postalCode.city.state.country.id = :aCountryId");
			Query query = session.createQuery(sb.toString());

			query.setLong("aDealerCode", dealerCode);
			query.setLong("aCountryId", countryId);
			Long result = (Long)query.uniqueResult(); 
			return result;
		} catch (Throwable ex){
			log.error("== Error en getDealerIdByDealerCode ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getDealerIdByDealerCode/DAODealerBean ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealersByDealerTypeCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Dealer> getDealersByDealerTypeCode(String dealerTypeCode) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getDealersByDealerTypeCode/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append("from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealerType.dealerTypeCode = :aDealerTypeCode ");
	        Query query = session.createQuery(sb.toString());
	        query.setString("aDealerTypeCode", dealerTypeCode);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealersByDealerTypeCode ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealersByDealerTypeCode/DAODealerBean ==");
	    }
	}
	
	
	@Override
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Dealer> getDealersByWarehouseTypeCode(String warehouseType, Long countryId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getWarehousesByDealerId/WarehouseDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer sb = new StringBuffer();
	        sb.append("from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.postalCode.city.state.country.id = :countryId and d.id in (SELECT wh.dealerId.id  FROM "+Warehouse.class.getName()+" wh WHERE wh.warehouseType.whTypeCode = :whTypeCode ) ");
	        Query query = session.createQuery(sb.toString());
	        query.setString("whTypeCode", warehouseType);
	        query.setLong("countryId", countryId);
	        return query.list();

        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getWarehousesByDealerId/WarehouseDAO ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerBranchesByDealerId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Dealer> getDealerBranchesByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getDealerBranchesByDealerId/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append(" from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealer.id = :aDealerId ");
	        sb.append(" or d.id = :aDealerId ");
	        Query query = session.createQuery(sb.toString());
	        query.setLong("aDealerId", dealerId);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealerBranchesByDealerId ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealerBranchesByDealerId/DAODealerBean ==");
	    }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerBranchesByDealerIdAndFilter(Long dealerId,String isInstaller, String isSeller)
	 */
	@SuppressWarnings("unchecked")
	public List<Dealer> getDealerBranchesByDealerIdAndFilter(Long dealerId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getDealerBranchesByDealerId/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append(" from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealer.id = :aDealerId ");
	        if(isInstaller!=null){
	        	sb.append(" and d.isInstaller = :aIsInstaller ");
	        }
	        if(isSeller!=null){
	        	sb.append(" and d.isSeller = :aIsSeller ");	        	
	        }
	        Query query = session.createQuery(sb.toString());
	        query.setLong("aDealerId", dealerId);
	        if(isInstaller!=null){
	        	query.setString("aIsInstaller", isInstaller);
	        }
	        if(isSeller!=null){
	        	query.setString("aIsSeller", isSeller);
	        }
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealerBranchesByDealerId ==", ex);
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealerBranchesByDealerId/DAODealerBean ==");
	    }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByIDAndType(java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Dealer getDealerByCodeAndType(Long id, String dealerTypeId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio getDealerByCodeAndType/DAODealerBean ==");
        Session session = getSession();
        try {
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append(" from ");
            queryBuffer.append(Dealer.class.getName());
            queryBuffer.append(" dealer where dealer.dealerCode = :id ");
            queryBuffer.append(" and dealer.dealerType.dealerTypeCode = :idTipo ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("id", id);
            query.setString("idTipo", dealerTypeId);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (Dealer) obj;
            }
            return null;
        } catch (Throwable ex){
			log.error("== Error en getDealerByCodeAndType/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByCodeAndType/DAODealerBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealersByDealerTypeCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersByDealerTypeCodeAndCountryId(
			String dealerTypeCode, Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getDealersByDealerTypeCodeAndCountryId/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	    	StringBuffer sb = new StringBuffer("select new ");
            sb.append(Dealer.class.getName());
            sb.append("(d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
            sb.append("d.depotCode, d.dealerCode, d.postalCode) from ");
            sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealerType.dealerTypeCode = :aDealerTypeCode and d.postalCode.city.state.country.id = :aCountryId");
	        Query query = session.createQuery(sb.toString());
	        query.setString("aDealerTypeCode", dealerTypeCode);
	        query.setLong("aCountryId", countryId);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealersByDealerTypeCodeAndCountryId ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealersByDealerTypeCodeAndCountryId/DAODealerBean ==");
	    }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDealerCodeAndCountryId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Dealer getDealerByDealerCodeAndCountryId(Long dealerCode,
			Long countryId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerByDealerCodeAndCountryId/DAODealerBean ==");
		Session session = getSession();

		try {
			StringBuffer sb = new StringBuffer("from ");
			sb.append(Dealer.class.getName());
			sb.append(" d where d.dealerCode = :aDealerCode and d.postalCode.city.state.country.id = :aCountryId");
			Query query = session.createQuery(sb.toString());

			query.setLong("aDealerCode", dealerCode);
			query.setLong("aCountryId", countryId);
			Dealer result = (Dealer)query.uniqueResult(); 
			return result;
		} catch (Throwable ex){
			log.error("== Error en getDealerByDealerCodeAndCountryId ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getDealerByDealerCodeAndCountryId/DAODealerBean ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getAllActiveByCountryIdAndIsAlloc(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getAllActiveByCountryIdAndIsAlloc(Long countryId, String isSeller, String isInstaller)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllActiveByCountryIdAndIsAlloc/DAODealerBean ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from Dealer d ");
        	stringQuery.append("where ");
        	stringQuery.append("d.dealerStatus.statusCode = ? and ");
        	stringQuery.append("d.postalCode.city.state.country.id = ? and ");
        	stringQuery.append("d.dealerType.isAlloc = ? ");
        	if( isSeller!=null )
        		stringQuery.append(" and d.isSeller = :aIsSeller ");
        	if( isInstaller!=null )
        		stringQuery.append(" and d.isInstaller = :aIsInstaller ");
            Query query = session.createQuery(stringQuery.toString());
            query.setString(0, CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            query.setLong(1, countryId);
            query.setString(2, CodesBusinessEntityEnum.IS_ALLOCABLE.getCodeEntity());
            if( isSeller!=null )
            	query.setString("aIsSeller", isSeller);
            if( isInstaller!=null )
            	query.setString("aIsInstaller", isInstaller);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getAllActiveByCountryIdAndIsAlloc/DAODealerBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealersCodesByDealerCode(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Long> getDealersCodesByDealerCode(String dealerCodes)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersCodesByDealerCode/DAODealerBean ==");
        Session session = getSession();
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" SELECT D.DEALER_CODE dealerCode FROM DEALERS D ");
        	stringQuery.append(" where ( "+ dealerCodes + " ) ");
        	Query query = session.createSQLQuery(stringQuery.toString()).addScalar("dealerCode",Hibernate.LONG);
        	List<Long> result=query.list();
        	return result;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getDealersCodesByDealerCode/DAODealerBean ==");
        }
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getPrincipalsDealersByAndCountryId(Long countryId, String isSeller, String isInstaller) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getPrincipalsDealersByAndCountryId/DAODealerBean ==");
		Session session = getSession();

		try {
			StringBuffer sb = new StringBuffer("from ");
			sb.append(Dealer.class.getName());
			sb.append(" d where d.postalCode.city.state.country.id = :aCountryId and d.dealer.id = null and d.dealerStatus.statusCode = :aDealerStatus");
			if( isSeller!=null )
				sb.append(" and d.isSeller = :aIsSeller ");
			if( isInstaller!=null )
				sb.append(" and d.isInstaller = :aIsInstaller ");
			Query query = session.createQuery(sb.toString());			
			query.setLong("aCountryId", countryId);	
			query.setString("aDealerStatus", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
			if( isSeller!=null )
				query.setString("aIsSeller", isSeller);
			if( isInstaller!=null)
				query.setString("aIsInstaller", isInstaller);
			return query.list();
		} catch (Throwable ex){
			log.error("== Error en getPrincipalsDealersByAndCountryId ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getPrincipalsDealersByAndCountryId/DAODealerBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealersByDealerTypeCodeAndCountryIdAnStatusCode(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersByDealerTypeCodeAndCountryIdAnStatusCode(
			String dealerTypeCode, Long countryId, String dealerStatus)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersByDealerTypeCodeAndCountryIdAnStatusCode/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	    	StringBuffer sb = new StringBuffer("select new ");
            sb.append(Dealer.class.getName());
            sb.append("(d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
            sb.append("d.depotCode, d.dealerCode) from ");
            sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealerType.dealerTypeCode = :aDealerTypeCode and d.postalCode.city.state.country.id = :aCountryId ");
	        sb.append("and d.dealerStatus.statusCode = :dealerStatus");
	        Query query = session.createQuery(sb.toString());
	        query.setString("aDealerTypeCode", dealerTypeCode);
	        query.setLong("aCountryId", countryId);
	        query.setString("dealerStatus", dealerStatus);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealersByDealerTypeCodeAndCountryIdAnStatusCode/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealersByDealerTypeCodeAndCountryIdAnStatusCode/DAODealerBean ==");
	    }
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Dealer getDealerByDepotCodeOrDealerCode(Long dealerCode, String depotCode, Long countryId) throws DAOServiceException, DAOSQLException{
        log.debug("== Inicia getDealerByDepotCodeOrDealerCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d where ");
        	stringQuery.append("d.postalCode.city.state.country.id = :countryId ");
        	stringQuery.append("and d.depotCode = :depotCode ");
        	stringQuery.append("and d.dealerCode = :dealerCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("depotCode", depotCode);
            query.setLong("dealerCode", dealerCode);
            return (Dealer) query.uniqueResult();
        } catch (Throwable ex){
			log.error("== Error getDealerByDepotCodeOrDealerCode/DAODealerBean ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getDealerByDepotCodeOrDealerCode/DAODealerBean ==");
        }
    }

	@Override
	@SuppressWarnings("unchecked")
	public List<Dealer> getDealersByDealerTypeId(Long dealerTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersByDealerTypeId/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append("from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealerType.id = :aDealerTypeId ");
	        Query query = session.createQuery(sb.toString());
	        query.setLong("aDealerTypeId", dealerTypeId);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealersByDealerTypeId ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealersByDealerTypeId/DAODealerBean ==");
	    }
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersByCountryIdAndTypeCode(Long countryId,
			String dealerTypeCode) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicia getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" d where ");
        	stringQuery.append("d.postalCode.city.state.country.id = :countryId ");
        	stringQuery.append("and d.dealerType.dealerTypeCode = :dealerTypeCode ");
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("dealerTypeCode", dealerTypeCode);
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Dealer> getDealersForGenerateKpis(Long countryId) throws DAOSQLException, DAOServiceException {
		log.debug("== Inicia getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append(" select dealer ");
        	stringQuery.append("   from " + DealerDetail.class.getName() + " entity inner join entity.dealer dealer ");
        	stringQuery.append("  where dealer.postalCode.city.state.country.id = :countryId ");
        	stringQuery.append("  		and dealer.dealerStatus.statusCode=:dealerStatusActive ");
        	
            Query query = session.createQuery(stringQuery.toString());
            query.setLong("countryId", countryId);
            query.setString("dealerStatusActive", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
            
            return query.list();
        } catch (Throwable ex){
			log.error("== Error getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
			throw this.manageException(ex);
		}finally {
            log.debug("== Termina getDealersByCountryCodeAndTypeCode/DAODealerBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Dealer getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus(Long dealerCode,String dealerTypeCode, Long countryId,String statusCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus/DAODealerBean ==");
        Session session = getSession();
        try {
        	if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("  from " + Dealer.class.getName() + " dealer ");
            queryBuffer.append(" where dealer.dealerCode = :dealerCode ");
            queryBuffer.append("       and dealer.dealerType.dealerTypeCode = :dealerTypeCode ");
            queryBuffer.append("       and dealer.postalCode.city.state.country.id = :countryId  ");
            queryBuffer.append("       and dealer.dealerStatus.statusCode = :statusCode  ");
            Query query = session.createQuery(queryBuffer.toString());
            query.setLong("countryId", countryId);
            query.setLong("dealerCode", dealerCode);
            query.setString("dealerTypeCode", dealerTypeCode);
            query.setString("statusCode", statusCode);
            Object obj = query.uniqueResult();
            if (obj != null) {
                return (Dealer) obj;
            }
            return null;
        } catch (Throwable ex){
			log.error("== Error en getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByDealerCodeAndTypeCodeAndCountryIdAndStatus/DAODealerBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus(java.lang.Long, java.lang.String, java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Dealer> getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus(Long dealerCode, String dealerTypeCode, Long countryId,String statusCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append("  from " + Dealer.class.getName() + " dealer ");
	        sb.append(" where dealer.dealer.dealerCode = :dealerCode ");
	        sb.append("       and dealer.dealerType.dealerTypeCode = :dealerTypeCode ");
	        sb.append("       and dealer.postalCode.city.state.country.id = :countryId  ");
	        sb.append("       and dealer.dealerStatus.statusCode = :statusCode  ");
	        Query query = session.createQuery(sb.toString());
	        query.setLong("countryId", countryId);
            query.setLong("dealerCode", dealerCode);
            query.setString("dealerTypeCode", dealerTypeCode);
            query.setString("statusCode", statusCode);
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealerBranchesByDealerCodeAndBranchesTypesAndCountryAndStatus/DAODealerBean ==");
	    }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDealerCodeAndCountryIdAndStatusCode(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public Dealer getDealerByDealerCodeAndCountryIdAndStatusCode(Long dealerCode, Long countryId, String statusCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerByDealerCodeAndCountryIdAndStatusCode/DAODealerBean ==");
		Session session = getSession();

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from " + Dealer.class.getName() + " d ");
			sb.append(" where d.dealerCode = :aDealerCode ");
			sb.append("       and d.postalCode.city.state.country.id = :aCountryId ");
			sb.append("       and d.dealerStatus.statusCode = :statusCode ");
			Query query = session.createQuery(sb.toString());

			query.setLong("aDealerCode", dealerCode);
			query.setLong("aCountryId", countryId);
			query.setString("statusCode", statusCode);
			Dealer result = (Dealer)query.uniqueResult(); 
			return result;
		} catch (Throwable ex){
			log.error("== Error en getDealerByDealerCodeAndCountryIdAndStatusCode/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
			log.debug("== Termina getDealerByDealerCodeAndCountryIdAndStatusCode/DAODealerBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByDealerCodeAndDepotCode(java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Dealer getDealerByDealerCodeAndDepotCode(Long dealerCode,
			String depotCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerByDealerCodeAndDepotCode/DAODealerBean ==");
        Session session = getSession();
        Dealer obj = null;
        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("from ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" dealers where dealers.depotCode = :depotCode");
    	    stringQuery.append(" and dealers.dealerCode = :dealerCode");
            
            Query query = session.createQuery(stringQuery.toString());

            query.setString("depotCode", depotCode);
            query.setLong("dealerCode", dealerCode);

            obj = (Dealer) query.uniqueResult();

            return obj;
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByDealerCodeAndDepotCode/DAODealerBean ==");
        }
	}
	
	/* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerByBranchId(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
	public List<Dealer> getDealerByBranchId(Long dealerPrincipalId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia getDealerByBranchId/DAODealerBean ==");
        Session session = getSession();

        try {
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append("select new ");
        	stringQuery.append(Dealer.class.getName());
        	stringQuery.append(" (d.id, d.dealerName, d.legalRepresentative, d.nit, d.address, ");
        	stringQuery.append("d.depotCode, d.dealerCode) from "+Dealer.class.getName() + " d where d.dealer.id = :aPrincipalId");
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("aPrincipalId", dealerPrincipalId);

            return query.list();
        } catch (Throwable ex){
			log.error("== Error ==");
			throw this.manageException(ex);
		}  finally {
            log.debug("== Termina getDealerByBranchId/DAODealerBean ==");
        }
    }
    
    /*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.dealers.DealersDAOLocal#getDealerBranchesByDealerId(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<Dealer> getDealerBranchesByDealerId(List<Long> dealerIds) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getDealerBranchesByDealerId/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append(" from ");
	        sb.append(Dealer.class.getName());
	        sb.append(" d where d.dealer.id in ( "+ UtilsBusiness.longListToString(dealerIds, ",") +" )");
	        sb.append(" and d.dealerStatus.statusCode = :aDealerStatus ");
	        
	        Query query = session.createQuery(sb.toString());
	        query.setString("aDealerStatus", CodesBusinessEntityEnum.CODIGO_DEALER_STATUS_ACTIVE.getCodeEntity());
	        return query.list();
	    } catch (Throwable ex){
			log.error("== Error en getDealerBranchesByDealerId ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getDealerBranchesByDealerId/DAODealerBean ==");
	    }
	}

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP+ ordenados por nombre de dealer de forma ascendente
	 * @param getHSPDealers parametro de consulta, principalmente el codigo del pais del dealer
	 * @param ri parametros de paginacion de la consulta
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	@Override
	public DealerInfoResponseDTO getHSPDealers(DealerInfoRequestDTO getHSPDealers, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo ri)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getHSPDealers/DAODealerBean ==");
		Session session = super.getSession();
	    try {
	        StringBuffer sb = new StringBuffer();
	        sb.append(" select d.id id, d.dealer_code ibsCode, d.depot_code depotCode, ds.status_code dealerStateCode, ds.status_name dealerState, d2.dealer_code ibsCodePrincipalDealer, d.score score, ");
	        sb.append(" case when d.dealer_branch_id is null then TO_NUMBER(1) else TO_NUMBER(0) end isPrincipal, CT.CHANNEL_CODE channelCode ");
	        sb.append(" from dealers d inner join dealer_status ds on (ds.id = d.status_id) left join dealers d2 on(d2.id = d.dealer_branch_id) ");
	        sb.append(" inner join postal_codes pc on (pc.id = d.postal_code_id) ");
	        sb.append(" inner join cities ci on (ci.id = pc.city_id) ");
	        sb.append(" inner join states s on (s.id = ci.state_id) ");
	        sb.append(" inner join countries co on (co.id = s.country_id) ");
	        sb.append(" inner join CHANNEL_TYPES ct on CT.ID = D.CHANNEL_TYPE_ID ");
	        sb.append(" WHERE co.country_code = :countryCode ");
	        
	        sb.append(" order by d.dealer_code asc ");
	        Query query = session.createSQLQuery(sb.toString())
	        	.addScalar("ibsCode", Hibernate.STRING)
	        	.addScalar("depotCode", Hibernate.STRING)
	        	.addScalar("dealerStateCode", Hibernate.STRING)
	        	.addScalar("dealerState", Hibernate.STRING)
	        	.addScalar("ibsCodePrincipalDealer", Hibernate.STRING)
	        	.addScalar("score", Hibernate.LONG)
	        	.addScalar("isPrincipal", Hibernate.LONG)
	        	.addScalar("channelCode", Hibernate.STRING)
	        	.setResultTransformer(Transformers.aliasToBean(DealerInfoDTO.class));
	        Long recordQty = 0L;
        	if( ri!=null ){
        		Query countQuery = session.createSQLQuery(" select count(1) from ( " + sb.toString() + " ) ");
        		countQuery.setString("countryCode", getHSPDealers.getCountryCode());
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult( ri.getFirstResult() );
				query.setMaxResults( ri.getMaxResults() );
        	}
        	query.setString("countryCode", getHSPDealers.getCountryCode());
        	List<DealerInfoDTO> returnList=(List<DealerInfoDTO>)query.list();
        	DealerInfoResponseDTO response = new DealerInfoResponseDTO();
        	if( getHSPDealers.getIsCsvOrSoapResponse() == null || !getHSPDealers.getIsCsvOrSoapResponse() ){
				populatePaginationInfo( response, getHSPDealers.getPageSize(), getHSPDealers.getPageIndex(), returnList.size(), recordQty.intValue() );
        	}
        	response.setDealerInfoDTOList(returnList);
	        return response;
	    } catch (Throwable ex){
			log.error("== Error en getHSPDealers/DAODealerBean ==");
			throw this.manageException(ex);
		}  finally {
	        log.debug("== Termina getHSPDealers/DAODealerBean ==");
	    }
	}

}

package co.com.directv.sdii.persistence.dao.config.impl;

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
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.collection.DealerConfCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal;
import co.com.directv.sdii.reports.dto.DealerConfCoverageDTO;


/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * DAO para el procesamiento de operaciones CRUD
 * de la entidad de DealerConfCoverage
 * 
 * Fecha de Creacion: Jue 12, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerConfCoverage
 * @see co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal
 */

@Stateless(name="DealerConfCoverageDAOLocal",mappedName="ejb/DealerConfCoverageDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfCoverageDAO extends BaseDao implements DealerConfCoverageDAOLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(DealerConfCoverageDAO.class);
    
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * Crea una DealerConfCoverage en el sistema
     * @param obj - DealerConfCoverage
     * @throws DAOServiceException
     * @throws DAOSQLException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createDealerConfCoverage(DealerConfCoverage obj) throws DAOServiceException, DAOSQLException 
    {

        log.debug("== Inicio createDealerConfCoverage/DealerConfCoverageDAO ==");
        Session session = super.getSession();
        try {
            if (session == null) {
                throw new DAOServiceException(ErrorBusinessMessages.SESSION_NULL.getCode());
            }
            session.save(obj);
            this.doFlush(session);
        }catch (Throwable ex) {
            log.debug("== Error creando el createDealerConfCoverage ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerConfCoverage/DealerConfCoverageDAO ==");
        }
    }
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteDealerConfCoverageByDealerConfId(Long dealerConfId) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicia deleteDealerConfCoverageByDealerConfId/DealerConfCoverageDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from DealerConfCoverage ");
        	stringQuery.append("where dealerConfigurationId = :dealerConfId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            int result = query.executeUpdate();
            return result;
        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfCoverage ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfCoverageByDealerConfId/DealerConfCoverageDAO ==");
        }
    }

    /*
     * Spira 28731 – Quitar filtros de Departamento y Ciudad al “Cargar Configuración” – solapa de cobertura
     * modificación
     * ialessan
     * marzo 2014
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public DealerConfCoverageResponse getDealerConfCoverageByDealerConfId(  Long dealerConfId, 
    																		//Long cityId,
																		    RequestCollectionInfo requestCollectionInfo

    )throws DAOServiceException, DAOSQLException{
    	
    	log.debug("== Inicia getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId/DealerConfCoverageDAO ==");
		Session session = getSession();
		try {

			String sqlComplement = new StringBuffer("from ")
			.append(DealerConfCoverage.class.getName())
			.append(" dcc where ")
			.append(" dcc.dealerConfigurationId = :dealerConfId ")
			//.append(" and dcc.postalCode.city.id = :cityId ")
			.toString();

			Query query = session.createQuery(sqlComplement);

			query.setLong("dealerConfId", dealerConfId);
			//query.setLong("cityId", cityId);

			// Paginación
			int firstResult = 0;
			int maxResult = 0;
			Long totalRowCount = 0L;
			DealerConfCoverageResponse response = new DealerConfCoverageResponse();
			
			if (requestCollectionInfo != null) {				
				String sqlCount = new StringBuffer("select count(*) ").append(sqlComplement).toString();
				Query countQuery = session.createQuery(sqlCount);
				countQuery.setLong("dealerConfId", dealerConfId);
				//countQuery.setLong("cityId", cityId);				
				totalRowCount = (Long) countQuery.uniqueResult();
				firstResult = requestCollectionInfo.getFirstResult();
				maxResult = requestCollectionInfo.getMaxResults();
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResult);
			}
			
			response.setDealerConfCoverages(query.list());

			if (requestCollectionInfo != null) {
				populatePaginationInfo(	response, 
										requestCollectionInfo.getPageSize(), 
										requestCollectionInfo.getPageIndex(),
										response.getDealerConfCoverages().size(), 
										totalRowCount.intValue());
			}

			return response;
            
        }catch (Throwable ex) {
            log.debug("== Error obteniendo DealerConfConfCoverage ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getDealerConfCoverageByDealerIdCustomerCategoryIdAreaId/DealerConfCoverageDAO ==");
        }    	
    	
    }

	@Override
	public int deleteDealerConfCoverageByDealerConfIdAndCoverageTypeId(Long dealerConfId, Long coverageTypeId) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia deleteDealerConfCoverageByDealerConfIdAndCoverageTypeId/DealerConfCoverageDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from DealerConfCoverage ");
        	stringQuery.append("where dealerConfigurationId = :dealerConfId ");
        	stringQuery.append("and coverageTypeId = :coverageTypeId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            query.setLong("coverageTypeId", coverageTypeId);
            int result = query.executeUpdate();
            return result;

        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfCoverage ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfCoverageByDealerConfIdAndCoverageTypeId/DealerConfCoverageDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal#deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public int deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(Long dealerConfId, Long postalCodeId) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia deleteDealerConfCoverageByDealerConfIdAndPostalCodeId/DealerConfCoverageDAO ==");
        Session session = super.getSession();

        try {
        	StringBuffer stringQuery =  new StringBuffer();
        	stringQuery.append("delete from DealerConfCoverage ");
        	stringQuery.append("where dealerConfigurationId = :dealerConfId ");
        	stringQuery.append("and postalCodeIds = :postalCodeId ");
        
        	Query query = session.createQuery(stringQuery.toString());
            query.setLong("dealerConfId", dealerConfId);
            query.setLong("postalCodeId", postalCodeId);
            int result = query.executeUpdate();
            return result;

        }catch (Throwable ex) {
            log.debug("== Error eliminando DealerConfCoverage ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerConfCoverageByDealerConfIdAndPostalCodeId/DealerConfCoverageDAO ==");
        }
	}    
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getPostalCodesWithoutCoverageByDealerIdAndCityId(java.lang.Long,
	 * java.lang.Long,
	 * co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeResponse getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId(Long dealerConfigurationId, Long cityId, RequestCollectionInfo requestCollectionInfo) throws DAOServiceException, DAOSQLException {

		log.debug("== Inicia getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId/DealerConfCoverageDAO ==");
		Session session = getSession();
		try {

			StringBuffer hqlBuffer = new StringBuffer("from ")
					.append(PostalCode.class.getName())
					.append(" pc where pc.city.id = :cityId ");
			
			if(dealerConfigurationId != null){
				hqlBuffer.append(" and pc.id not in ( select distinct dcc.postalCode.id from ")
						 .append(DealerConfCoverage.class.getName())
						 .append(" dcc where dcc.dealerConfigurationId = :dealerConfigurationId ")
						 .append(" and dcc.postalCode.city.id = :cityId and dcc.status = :status ) ");
			}
			hqlBuffer.append(" order by pc.city.state.stateName, pc.city.cityName, pc.postalCodeName, pc.postalCodeCode");

			String hql = hqlBuffer.toString();
			
			Query query = session.createQuery(hql);
			
			query.setLong("cityId", cityId);
			if(dealerConfigurationId != null){				
				query.setLong("dealerConfigurationId", dealerConfigurationId);
				query.setString("status", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			}

			// Paginación
			int firstResult = 0;
			int maxResult = 0;
			Long totalRowCount = 0L;
			PostalCodeResponse response = new PostalCodeResponse();
			if (requestCollectionInfo != null) {

				String hqlCount = new StringBuffer("select count(pc) ").append(hql).toString();

				Query countQuery = session.createQuery(hqlCount);

				countQuery.setLong("cityId", cityId);
				if(dealerConfigurationId != null){					
					countQuery.setLong("dealerConfigurationId", dealerConfigurationId);
					countQuery.setString("status",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				}

				totalRowCount = (Long) countQuery.uniqueResult();
				firstResult = requestCollectionInfo.getFirstResult();
				maxResult = requestCollectionInfo.getMaxResults();

				query.setFirstResult(firstResult);
				query.setMaxResults(maxResult);

			}
			response.setPostalCodes(query.list());

			if (requestCollectionInfo != null) {
				populatePaginationInfo(response, requestCollectionInfo.getPageSize(), 
						requestCollectionInfo.getPageIndex(),response.getPostalCodes().size(), totalRowCount.intValue());
			}

			return response;

		} catch (Throwable ex) {
			log.error("== Error getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId/DealerConfCoverageDAO == "+ ex.getMessage());
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesWithoutConfCoverageByDealerConfIdAndCityId/DealerConfCoverageDAO ==");
		}
	}

	@Override
	public DealerConfCoverage getDealerConfCoverageBy(Long dealerConfigurationId, Long dealerId, Long postalCodeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealerConfCoverageBy/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerConfCoverage.class.getName());
			stringQuery.append(" dcc where dcc.dealerConfigurationId = :dealerConfigurationId ");
			stringQuery.append(" and dcc.postalCode.id = :postalCodeId ");
			stringQuery.append(" and dcc.dealer.id = :dealerId ");
			stringQuery.append(" and dcc.status = :statusActive ");

			Query query = session.createQuery(stringQuery.toString());

			query.setLong("dealerConfigurationId", dealerConfigurationId);
			query.setLong("dealerId", dealerId);
			query.setLong("postalCodeId", postalCodeId);
			query.setString("statusActive",CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());

			return (DealerConfCoverage) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error getDealerConfCoverageBy/DealerCoverageDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerConfCoverageBy/DealerCoverageDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal#updateDealerConfCoverage(co.com.directv.sdii.model.pojo.DealerConfCoverage)
	 */
	@Override
	public void updateDealerConfCoverage(DealerConfCoverage dealerConfCoverage) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio updateDealerConfCoverage/DealerConfCoverageDAO ==");

		Session session = super.getSession();
		try {
			session.merge(dealerConfCoverage);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error actualizando el DealerConfCoverage ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateDealerConfCoverage/DealerConfCoverageDAO ==");
		}
	}
	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal#getAllDealerCoveragesByCountryAndStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DealerConfCoverageDTO> getAllDealerConfCoveragesByCountryAndStatus(Long country, String status) 
			throws DAOServiceException, DAOSQLException {
		try {
			log.debug("== Inicia getAllDealerConfCoveragesByCountryAndStatus/DealerConfCoverageDAO ==");
			Session session = super.getSession();
		
			StringBuilder query = new StringBuilder();
			query.append(" SELECT BA.BUSINESS_AREA_NAME businessAreaName, CC.NAME customerCategoryName, ");
			query.append(" PC.POSTAL_CODE_NAME postalCodeName, C.CITY_NAME cityName, S.STATE_NAME stateName, ");
			query.append(" D.DEALER_CODE dealerCode, D.DEPOT_CODE depotCode, CT.NAME coverageTypeName ");
			query.append(" FROM DEALER_CONF_COVERAGES DCC ");
			query.append(" INNER JOIN DEALERS D ON D.ID = DCC.DEALER_ID ");
			query.append(" INNER JOIN POSTAL_CODES PC ON PC.ID = DCC.POSTAL_CODE_ID ");
			query.append(" INNER JOIN DEALER_CONFIGURATIONS DC ON DC.ID = DCC.DEALER_CONFIGURATION_ID ");
			query.append(" INNER JOIN BUSINESS_AREAS BA ON BA.ID = DC.BUSINESS_AREA_ID ");
			query.append(" INNER JOIN CUSTOMER_CATEGORY CC ON CC.ID = DC.CUSTOMER_CATEGORY_ID ");
			query.append(" INNER JOIN CITIES C ON C.ID = PC.CITY_ID ");
			query.append(" INNER JOIN STATES S ON S.ID = C.STATE_ID ");
			query.append(" INNER JOIN COVERAGE_TYPES CT ON CT.ID = DCC.COVERAGE_TYPE_ID ");
			query.append(" WHERE DCC.STATUS = :status "); 
			query.append(" AND DCC.COUNTRY_ID = :countryId ");
			
			Query querySQL = session.createSQLQuery(query.toString())
            		.addScalar("businessAreaName", Hibernate.STRING)
            		.addScalar("customerCategoryName", Hibernate.STRING)
            		.addScalar("postalCodeName", Hibernate.STRING)
            		.addScalar("cityName", Hibernate.STRING)
            		.addScalar("stateName", Hibernate.STRING)
            		.addScalar("dealerCode", Hibernate.STRING)
            		.addScalar("depotCode", Hibernate.STRING)
            		.addScalar("coverageTypeName", Hibernate.STRING)
					.setResultTransformer(Transformers.aliasToBean(DealerConfCoverageDTO.class));
			
			querySQL.setParameter("countryId", country, Hibernate.LONG);
            querySQL.setParameter("status", status, Hibernate.STRING );

			return querySQL.list();

		} catch (Throwable ex) {
			log.error("== Error getAllDealerConfCoveragesByCountryAndStatus/DealerConfCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllDealerConfCoveragesByCountryAndStatus/DealerConfCoverageDAO ==");
		}
	}
}
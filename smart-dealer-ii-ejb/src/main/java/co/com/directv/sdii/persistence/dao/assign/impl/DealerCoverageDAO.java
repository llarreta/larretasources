package co.com.directv.sdii.persistence.dao.assign.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerCoverage;
import co.com.directv.sdii.model.pojo.PostalCode;
import co.com.directv.sdii.model.pojo.collection.DealerCoverageResponse;
import co.com.directv.sdii.model.pojo.collection.PostalCodeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD de la entidad DealerCoverage
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.DealerCoverage
 * @see co.com.directv.sdii.model.hbm.DealerCoverage.hbm.xml
 */
@Stateless(name = "DealerCoverageDAOLocal", mappedName = "ejb/DealerCoverageDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCoverageDAO extends BaseDao implements DealerCoverageDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(DealerCoverageDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * createDealerCoverage(co.com.directv.sdii.model.pojo.DealerCoverage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerCoverage(DealerCoverage obj)
			throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio createDealerCoverage/DealerCoverageDAO ==");
		saveAuditEnvers(obj);
		Session session = super.getSession();
		try {
			session.save(obj);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.error("== Error creando el DealerCoverage ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createDealerCoverage/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * updateDealerCoverage(co.com.directv.sdii.model.pojo.DealerCoverage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerCoverage(DealerCoverage obj)
			throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio updateDealerCoverage/DealerCoverageDAO ==");
		saveAuditEnvers(obj);
		Session session = super.getSession();
		try {
			session.merge(obj);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.error("== Error actualizando el DealerCoverage ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateDealerCoverage/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * deleteDealerCoverage(co.com.directv.sdii.model.pojo.DealerCoverage)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerCoverage(DealerCoverage obj)
			throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio deleteDealerCoverage/DealerCoverageDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
					.createQuery("delete from DealerCoverage entity where entity.id = :anEntityId");
			query.setLong("anEntityId", obj.getId());
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.error("== Error eliminando el DealerCoverage ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteDealerCoverage/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getDealerCoveragesByID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerCoverage getDealerCoverageByID(Long id)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getDealerCoverageByID/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerCoverage.class.getName());
			stringQuery.append(" entity where entity.id = :anId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anId", id);

			return (DealerCoverage) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error getDealerCoverageByID/DealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerCoverageByID/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getAllDealerCoverages()
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCoverage> getAllDealerCoverages()
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllDealerCoverages/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerCoverage.class.getName());
			return session.createQuery(stringQuery.toString()).list();

		} catch (Throwable ex) {
			log.error("== Error getAllDealerCoverages/DealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllDealerCoverages/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getDealersByDealerIdOrderByDealerPriority(java.util.List, java.lang.Long,
	 * java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<Dealer> getDealersByDealerIdOrderByDealerPriority(
			List<DealerVO> dealerIds, Long postalCodeId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log
				.debug("== Inicia getDealersByDealerIdOrderByDealerPriority/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append(" select dc.dealer ");
			stringQuery.append("   from " + DealerCoverage.class.getName() + " dc ");
			stringQuery.append("  where dc.country.id = :countryId ");
			stringQuery.append("        and dc.postalCode.id = :postalCodeId ");
			stringQuery.append("        and dc.status = :statusActive ");
			stringQuery.append("        and dc.dealer.id in ( ");
			StringBuffer sb = new StringBuffer();
			for (DealerVO dealer : dealerIds) {
				sb.append(dealer.getId());
				sb.append(",");
			}
			stringQuery.append(StringUtils.removeEnd(sb.toString(), ","));
			stringQuery.append("                             ) ");
			stringQuery.append("  order by dc.dealerPriority asc ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("postalCodeId", postalCodeId);
			query.setLong("countryId", countryId);
			query.setString("statusActive",CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error getDealersByDealerIdOrderByDealerPriority/DealerCoverageDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealersByDealerIdOrderByDealerPriority/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getActiveDealerCoverageByDealerCodePostalCodeAndCountry(java.lang.Long,
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public DealerCoverage getActiveDealerCoverageByDealerCodePostalCodeAndCountry(
			Long dealerCode, Long postalCodeId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("  from " + DealerCoverage.class.getName() + " dc ");
			stringQuery.append(" where dc.country.id = :countryId ");
			stringQuery.append("       and dc.postalCode.id = :postalCodeId ");
			stringQuery.append("       and dc.status = :statusActive ");
			stringQuery.append("       and dc.dealer.dealerCode = :dealerCode ");

			Query query = session.createQuery(stringQuery.toString());

			query.setLong("postalCodeId", postalCodeId);
			query.setLong("countryId", countryId);
			query.setLong("dealerCode", dealerCode);
			query.setString("statusActive",CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());

			return (DealerCoverage) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==",ex);
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getActiveDealerCoverageByDealerCodePostalCodeAndCountry(java.lang.Long,
	 * java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<DealerCoverage> getDealerCoverageByDealerIdPostalCodeIdCountryIdStatusActive(
			Long dealerId, Long postalCodeId, Long countryId,
			String statusActive) throws DAOServiceException, DAOSQLException {
		log
				.debug("== Inicia getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==");
		Session session = super.getSession();
		String strWhereOrAnd = " Where ";

		boolean dealerIdSpecified = false, postalCodeIdSpecified = false, countryIdSpecified = false, statusActiveSpecified = false;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerCoverage.class.getName() + " dc ");

			if (dealerId != null && dealerId.longValue() > 0) {
				stringQuery
						.append(strWhereOrAnd + " dc.dealer.id = :dealerId ");
				strWhereOrAnd = " And ";
				dealerIdSpecified = true;
			}

			if (postalCodeId != null && postalCodeId.longValue() > 0) {
				stringQuery.append(strWhereOrAnd
						+ " dc.postalCode.id = :postalCodeId ");
				strWhereOrAnd = " And ";
				postalCodeIdSpecified = true;
			}

			if (countryId != null && countryId.longValue() > 0) {
				stringQuery.append(strWhereOrAnd
						+ " dc.country.id = :countryId ");
				strWhereOrAnd = " And ";
				countryIdSpecified = true;
			}

			if (statusActive != null && statusActive.trim().length() > 0) {
				stringQuery.append(strWhereOrAnd
						+ " dc.status = :statusActive ");
				strWhereOrAnd = " And ";
				statusActiveSpecified = true;
			}

			Query query = session.createQuery(stringQuery.toString());

			if (postalCodeIdSpecified)
				query.setLong("postalCodeId", postalCodeId);

			if (countryIdSpecified)
				query.setLong("countryId", countryId);

			if (dealerIdSpecified)
				query.setLong("dealerId", dealerId);

			if (statusActiveSpecified)
				// query.setString("statusActive",
				// CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE.getCodeEntity());
				query.setString("statusActive", statusActive);

			return query.list();

		} catch (Throwable ex) {
			log
					.error(
							"== Error getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getActiveDealerCoverageByDealerCodePostalCodeAndCountry/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getAllDealerCoveragesByCountryAndStatus(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DealerCoverage> getAllDealerCoveragesByCountryAndStatus(
			Long country, String status) throws DAOServiceException,
			DAOSQLException {
		log
				.debug("== Inicia getAllDealerCoveragesByCountryAndStatus/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerCoverage.class.getName());
			stringQuery.append(" dc where dc.country.id = :country ");
			stringQuery.append("and dc.status = :status ");

			Query query = session.createQuery(stringQuery.toString());
			query.setLong("country", country);
			query.setString("status", status);

			return query.list();

		} catch (Throwable ex) {
			log
					.error(
							"== Error getAllDealerCoveragesByCountryAndStatus/DealerCoverageDAO ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getAllDealerCoveragesByCountryAndStatus/DealerCoverageDAO ==");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Dealer> getDealersInMicrozoneWithExMode(String executionMode,
			                                            String postalCode, 
			                                            String countryIso2Code, 
			                                            Long dealerId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {

			StringBuilder stringQuery = new StringBuilder();
			stringQuery.append(" select distinct dc.dealer ");
			stringQuery.append("   from " + DealerCoverage.class.getName() + " dc ");
			stringQuery.append(" where dc.postalCode.postalCodeCode = :aPostalCode ");
			stringQuery.append("       and dc.country.countryCode = :aCountryCode ");
			stringQuery.append("       and dc.status = :aStatus ");
			if (executionMode.equalsIgnoreCase(CodesBusinessEntityEnum.EXECUTION_TYPE_ASSIGNED.getCodeEntity())) {
				stringQuery.append(" and (dc.coverageType.code = :covTypePerm");
				stringQuery.append("      or dc.coverageType.code = :covTypeOcas)");
			}
			if (executionMode.equalsIgnoreCase(CodesBusinessEntityEnum.EXECUTION_TYPE_SCHEDULED.getCodeEntity())) {
				stringQuery.append(" and dc.coverageType.code = :covTypePerm");
			}

			if (dealerId != null && dealerId.longValue() > 0) {
				stringQuery.append(" and dc.dealer.id = :aDealerId");
			}

			Query query = session.createQuery(stringQuery.toString());

			query.setString("aPostalCode", postalCode);
			query.setString("aCountryCode", countryIso2Code);
			query.setString("aStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE
					.getCodeEntity());

			if (executionMode.equalsIgnoreCase(CodesBusinessEntityEnum.EXECUTION_TYPE_ASSIGNED.getCodeEntity())) {
				query.setString("covTypePerm",CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
				query.setString("covTypeOcas",CodesBusinessEntityEnum.COVERAGE_TYPE_OCASSIONAL.getCodeEntity());
			}
			if (executionMode.equalsIgnoreCase(CodesBusinessEntityEnum.EXECUTION_TYPE_SCHEDULED.getCodeEntity())) {
				query.setString("covTypePerm",CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
			}
			
			if (dealerId != null && dealerId.longValue() > 0) {
				query.setLong("aDealerId",dealerId);
			}

			List<Dealer> dealerList = query.list();
			return dealerList;

		} catch (Throwable ex) {
			log.error("== Error getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long countDealersInMicrozoneWithTypePerm(String postalCode, 
			                                        String countryIso2Code, 
			                                        Long dealerId)
			throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {

			StringBuilder stringQuery = new StringBuilder();
			stringQuery.append(" select count(distinct dc.dealer.id) ");
			stringQuery.append("   from " + DealerCoverage.class.getName() + " dc ");
			stringQuery.append(" where dc.postalCode.postalCodeCode = :aPostalCode ");
			stringQuery.append("       and dc.country.countryCode = :aCountryCode ");
			stringQuery.append("       and dc.status = :aStatus ");
			stringQuery.append("       and dc.coverageType.code = :covTypePerm");
			
			if (dealerId != null && dealerId.longValue() > 0) {
				stringQuery.append(" and dc.dealer.id = :aDealerId");
			}

			Query query = session.createQuery(stringQuery.toString());

			query.setString("aPostalCode", postalCode);
			query.setString("aCountryCode", countryIso2Code);
			query.setString("aStatus", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            query.setString("covTypePerm",CodesBusinessEntityEnum.COVERAGE_TYPE_PERMANENT.getCodeEntity());
			
			if (dealerId != null && dealerId.longValue() > 0) {
				query.setLong("aDealerId",dealerId);
			}

			return (Long) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealersInMicrozoneWithExMode/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getAllActiveByPostalCode(co.com.directv.sdii.model.pojo.PostalCode)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCoverage> getAllActiveByPostalCode(PostalCode postalCode)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllActiveByPostalCode/DealerCoverageDAO ==");
		Session session = getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from DealerCoverage d where ");
			stringQuery.append("d.dealer.dealerStatus.statusCode = ? and ");
			stringQuery.append("d.postalCode.postalCodeCode = ? ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString(0, CodesBusinessEntityEnum.DEALER_STATUS_NORMAL
					.getCodeEntity());
			query.setString(1, postalCode.getPostalCodeCode());

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error getAllActiveByPostalCode/DealerCoverageDAO == ");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllActiveByPostalCode/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getAllActiveByPostalCodeId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCoverage> getAllActiveByPostalCodeId(Long postalCodeId, String isSeller, String isInstaller)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllActiveByPostalCodeId/DealerCoverageDAO ==");
		Session session = getSession();
		try {
			StringBuffer stringQuery = new StringBuffer(
					"from DealerCoverage d where ").append("d.status = ? and ")
					.append("d.dealer.dealerStatus.statusCode = ? and ")
					.append("d.postalCode.id = ? "); 
			if( isSeller!=null )
				stringQuery.append(" and d.dealer.isSeller = :aIsSeller ");
			if( isInstaller!=null )
				stringQuery.append(" and d.dealer.isInstaller = :aIsInstaller ");
			stringQuery.append("order by d.dealerPriority");

			Query query = session.createQuery(stringQuery.toString());

			query.setString(0, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			query.setString(1, CodesBusinessEntityEnum.DEALER_STATUS_NORMAL.getCodeEntity());
			query.setLong(2, postalCodeId);
			if( isSeller!=null )
				query.setString("aIsSeller", isSeller);
			if( isInstaller!=null )
				query.setString("aIsInstaller", isInstaller);

			return query.list();
		} catch (Throwable ex) {
			log.error("== Error getAllActiveByPostalCodeId/DealerCoverageDAO == ");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllActiveByPostalCodeId/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getMicrozonesByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public PostalCodeResponse getPostalCodesActiveByDealerId(Long dealerId,
			RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getMicrozonesByDealerId/DealerCoverageDAO ==");
		Session session = getSession();

		try {
			StringBuffer stringQuery = new StringBuffer(
					"select d.postalCode from DealerCoverage d where")
					.append(" d.status = :statusCode and")
					.append(" d.dealer.id = :dealerId")
					.append(
							" order by d.postalCode.id ");

			Query query = session.createQuery(stringQuery.toString());
			query.setString("statusCode", CodesBusinessEntityEnum.BOOLEAN_TRUE
					.getCodeEntity());
			query.setLong("dealerId", dealerId);

			// Paginación
			int firstResult = 0;
			int maxResult = 0;
			Long totalRowCount = 0L;
			PostalCodeResponse response = new PostalCodeResponse();
			if (requestCollectionInfo != null) {

				StringBuffer countQueryBuffer = new StringBuffer(
						"select count(d.postalCode) from DealerCoverage d where ")
						.append("d.status = :statusCode and ").append(
								"d.dealer.id = :dealerId ");

				Query countQuery = session.createQuery(countQueryBuffer
						.toString());
				countQuery.setString("statusCode",
						CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				countQuery.setLong("dealerId", dealerId);

				totalRowCount = (Long) countQuery.uniqueResult();
				
				query.setFirstResult(requestCollectionInfo.getFirstResult());
				query.setMaxResults(requestCollectionInfo.getMaxResults());

			}
			response.setPostalCodes(query.list());

			if (requestCollectionInfo != null) {
				populatePaginationInfo(response, requestCollectionInfo
						.getPageSize(), requestCollectionInfo.getPageIndex(),
						response.getPostalCodes().size(), totalRowCount
								.intValue());
			}

			return response;
		} catch (Throwable ex) {
			log.error("== Error getMicrozonesByDealerId/DealerCoverageDAO == ");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getMicrozonesByDealerId/DealerCoverageDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerCoverageResponse getAllActiveByDealerId(Long dealerId,
			RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllActiveByDealerId/DealerCoverageDAO ==");
		Session session = getSession();
		try {

			String sqlComplement = new StringBuffer("from ")
					.append(DealerCoverage.class.getName())
					.append(" d where ")
					.append("d.status = ? and d.dealer.id = ? ")
					.append("order by d.postalCode.city.state.stateName, d.postalCode.city.cityName, d.postalCode.postalCodeName, d.postalCode.postalCodeCode").toString();

			Query query = session.createQuery(sqlComplement);

			query.setString(0, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			query.setLong(1, dealerId);

			// Paginación
			int firstResult = 0;
			int maxResult = 0;
			Long totalRowCount = 0L;
			DealerCoverageResponse response = new DealerCoverageResponse();
			if (requestCollectionInfo != null) {

				String sqlCount = new StringBuffer("select count(*) ").append(sqlComplement).toString();

				Query countQuery = session.createQuery(sqlCount);

				countQuery.setString(0, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
				countQuery.setLong(1, dealerId);

				totalRowCount = (Long) countQuery.uniqueResult();
				firstResult = requestCollectionInfo.getFirstResult();
				maxResult = requestCollectionInfo.getMaxResults();

				query.setFirstResult(firstResult);
				query.setMaxResults(maxResult);

			}
			response.setDealerCoverages(query.list());

			if (requestCollectionInfo != null) {
				populatePaginationInfo(	response, 
										requestCollectionInfo.getPageSize(), 
										requestCollectionInfo.getPageIndex(),
										response.getDealerCoverages().size(), 
										totalRowCount.intValue());
			}

			return response;

		} catch (Throwable ex) {
			log.error("== Error getAllActiveByDealerId/DealerCoverageDAO == ");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllActiveByDealerId/DealerCoverageDAO ==");
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
	public PostalCodeResponse getPostalCodesWithoutCoverageByDealerIdAndCityId(
			Long dealerId, Long cityId,
			RequestCollectionInfo requestCollectionInfo)
			throws DAOServiceException, DAOSQLException {

		log.debug("== Inicia getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageDAO ==");
		Session session = getSession();
		try {

			String hql = new StringBuffer("from ")
					.append(PostalCode.class.getName())
					.append(" pc where pc.city.id = :cityId")
					.append(" and pc.id not in ( select distinct dc.postalCode.id from ")
					.append(DealerCoverage.class.getName())
					.append(" dc where dc.dealer.id = :dealerId")
					.append(" and dc.postalCode.city.id = :cityId and dc.status = :status )")
					.append(" order by pc.city.state.stateName, pc.city.cityName, pc.postalCodeName, pc.postalCodeCode")
					.toString();

			Query query = session.createQuery(hql);

			query.setLong("cityId", cityId);
			query.setLong("dealerId", dealerId);
			query.setString("status", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

			// Paginación
			int firstResult = 0;
			int maxResult = 0;
			Long totalRowCount = 0L;
			PostalCodeResponse response = new PostalCodeResponse();
			if (requestCollectionInfo != null) {

				String hqlCount = new StringBuffer("select count(pc) ").append(
						hql).toString();

				Query countQuery = session.createQuery(hqlCount);

				countQuery.setLong("cityId", cityId);
				countQuery.setLong("dealerId", dealerId);
				countQuery.setString("status",CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

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
			log.error("== Error getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageDAO == ");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getPostalCodesWitoutCoverageByDealerIdAndCityId/DealerCoverageDAO ==");
		}

	}


	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.assign.DealerCoverageDAOLocal#
	 * getDealerCoverageByDealerIdAndPostalCodeId(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerCoverage getDealerCoverageByDealerIdAndPostalCodeId(
			Long dealerId, Long postalCodeId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(DealerCoverage.class.getName());
			stringQuery.append(" dc where dc.postalCode.id = :postalCodeId ");
			stringQuery.append("and dc.status = :statusActive ");
			stringQuery.append("and dc.dealer.id = :dealerId ");

			Query query = session.createQuery(stringQuery.toString());

			query.setLong("postalCodeId", postalCodeId);
			query.setString("statusActive",
					CodesBusinessEntityEnum.DEALER_COVERAGE_STATUS_ACTIVE
							.getCodeEntity());
			query.setLong("dealerId", dealerId);

			return (DealerCoverage) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerCoverageByDealerIdAndPostalCodeId/DealerCoverageDAO ==");
		}
	}

	private void saveAuditEnvers(DealerCoverage obj) {
		if (obj != null) {
			if (obj.getDealer() != null) {
				obj.setDealerId(obj.getDealer().getId());
			}
			if (obj.getUser() != null) {
				obj.setUserId(obj.getUser().getId());
			}
			if (obj.getCoverageType() != null) {
				obj.setCoverageTypeId(obj.getCoverageType().getId());
			}
			if (obj.getCountry() != null) {
				obj.setCountryId(obj.getCountry().getId());
			}
			if (obj.getPostalCode() != null) {
				obj.setPostalCodeIds(obj.getPostalCode().getId());
			}
		}
	}

}

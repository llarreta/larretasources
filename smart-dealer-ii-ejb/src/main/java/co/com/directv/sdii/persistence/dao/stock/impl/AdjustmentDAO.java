package co.com.directv.sdii.persistence.dao.stock.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.stock.WarehouseBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.AdjustmentRequestDTO;
import co.com.directv.sdii.model.dto.AdjustmentResponseDTO;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.AdjustmentElements;
import co.com.directv.sdii.model.pojo.collection.AdjustmentElementsResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.AdjustmentVO;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD de la entidad Adjustment
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.Adjustment
 * @see co.com.directv.sdii.model.hbm.Adjustment.hbm.xml
 */
@Stateless(name = "AdjustmentDAOLocal", mappedName = "ejb/AdjustmentDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdjustmentDAO extends BaseDao implements AdjustmentDAOLocal {

    @EJB(name="WarehouseBusinessBeanLocal", beanInterface=WarehouseBusinessBeanLocal.class)
    private WarehouseBusinessBeanLocal warehouseBusinessBeanLocal;
	
    private static final String MULTIPLE_TARGET= "Multiples ubicaciones";
    
	private final static Logger log = UtilsBusiness
			.getLog4J(AdjustmentDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#createAdjustment
	 * (co.com.directv.sdii.model.pojo.Adjustment)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long createAdjustment(Adjustment obj) throws DAOServiceException,
			DAOSQLException {

		log.debug("== Inicio createAdjustment/AdjustmentDAO ==");
		Session session = super.getSession();
		try {
			session.save(obj);
			this.doFlush(session);
			return obj.getId().longValue();
		} catch (Throwable ex) {
			log.debug("== Error creando el Adjustment ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina createAdjustment/AdjustmentDAO ==");
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private List<AdjustmentResponseDTO> convertToListOfAdjustmentDTO(
			List<AdjustmentVO> responseDB) throws BusinessException {
		List<AdjustmentResponseDTO> response = new ArrayList<AdjustmentResponseDTO>();
		Iterator<AdjustmentVO> iterador = responseDB.iterator();
		while (iterador.hasNext()) {
			AdjustmentVO adjustmentVO = iterador.next();
			if (adjustmentVO != null) {
				AdjustmentResponseDTO adjustmentDTO = new AdjustmentResponseDTO();
				adjustmentDTO.setDocumentNumber(adjustmentVO.getId());
				if (adjustmentVO.getTransferReason() != null
						&& adjustmentVO.getTransferReason().getAdjustmentType() != null) {
					adjustmentDTO.setAdjustmentTypeName(adjustmentVO
							.getTransferReason().getAdjustmentType().getName());
					adjustmentDTO.setAdjustmentTypeId(adjustmentVO
							.getTransferReason().getAdjustmentType().getId());
				}

				if(adjustmentVO.getWarehouseSourceID()!=null && adjustmentVO.getWarehouseSourceID()!=-1L){
					adjustmentDTO.setWareHouseSourceId(adjustmentVO.getWarehouseSourceID());					
				}

				if(adjustmentVO.getWarehouseSourceWhCode()!=null && !adjustmentVO.getWarehouseSourceWhCode().equalsIgnoreCase(MULTIPLE_TARGET)){
					warehouseBusinessBeanLocal.genWareHouseName(adjustmentVO.getWareHouseSource());
					adjustmentDTO.setWareHouseSourceName(adjustmentVO.getWareHouseSource().getWarehouseName());
				}else{
					adjustmentDTO.setWareHouseSourceName(adjustmentVO.getWarehouseSourceWhCode());
				}
				
				
				if(adjustmentVO.getWarehouseTargetID()!=null && adjustmentVO.getWarehouseTargetID()!=-1L){
					adjustmentDTO.setWareHouseDestinationId(adjustmentVO
							.getWarehouseTargetID());					
				}
				
				if(adjustmentVO.getWarehouseTargetWhCode()!=null && !adjustmentVO.getWarehouseTargetWhCode().equalsIgnoreCase(MULTIPLE_TARGET)){
					warehouseBusinessBeanLocal.genWareHouseName(adjustmentVO.getWareHouseTarget());
					adjustmentDTO.setWareHouseDestinationName(adjustmentVO.getWareHouseTarget().getWarehouseName());
				}else{
					adjustmentDTO.setWareHouseDestinationName(adjustmentVO.getWarehouseTargetWhCode());
				}
				if (adjustmentVO.getAdjustmentStatus() != null) {
					adjustmentDTO.setAdjustmentStatus(adjustmentVO
							.getAdjustmentStatus().getName());
				}
				if(adjustmentVO.getCreationUser()!=null){
					adjustmentDTO.setCreationUser(adjustmentVO.getCreationUser().getName());
				}
				if(adjustmentVO.getCreationDate()!=null){
					adjustmentDTO.setCreationDate(adjustmentVO.getCreationDate());
				}
				response.add(adjustmentDTO);
			}
		}
		return response;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public AdjustmentElementsResponse searchAdjustmentsBySearchParameters(
			AdjustmentRequestDTO params, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException, BusinessException {
		log.debug("== Inicio searchAdjustmentsBySearchParameters/AdjustmentDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuffer stringQuery = new StringBuffer();
			StringBuffer filterQuery = new StringBuffer();
			StringBuffer countString = new StringBuffer();
			countString.append("select count(distinct a.id) ");
			stringQuery.append("select new co.com.directv.sdii.model.vo.AdjustmentVO((select as1 from Adjustment as1 where as1.id = a.id), ");
			stringQuery.append("case when count(distinct w2.id)>1 then ");
			stringQuery.append("-1L ");
			stringQuery.append("else ");
			stringQuery.append("min(w2.id) ");
			stringQuery.append("end, ");
			stringQuery.append("case when count(distinct w1.id)>1 then ");
			stringQuery.append("-1L ");
			stringQuery.append("else ");
			stringQuery.append("min(w1.id) ");
			stringQuery.append("end, ");
			stringQuery.append("case when count(distinct w2.id)>1 then ");
			stringQuery.append("'"+MULTIPLE_TARGET+"' ");
			stringQuery.append("else ");
			stringQuery.append("min(w2.whCode) ");
			stringQuery.append("end, ");
			stringQuery.append("case when count(distinct w1.id)>1 then ");
			stringQuery.append("'"+MULTIPLE_TARGET+"' ");
			stringQuery.append("else ");
			stringQuery.append("min(w1.whCode) ");
			stringQuery.append("end, min(w2),min(w1)) ");
			stringQuery.append("from AdjustmentElements ae inner join  ae.adjustment a left join ae.warehouseDestination w1 left join ae.warehouseSource w2 where a.country.id = :countryId ");
			
			List<String> parameterNames = new ArrayList<String>();
			List<Object> parameters = new ArrayList<Object>();
			List<String> parameterTypes = new ArrayList<String>();
			
			parameterNames.add("countryId");
			parameters.add(params.getCountryId());
			parameterTypes.add(params.getCountryId().getClass().getName());		

			if(params!=null){
				if (params.getDocumentNumber() != null && params.getDocumentNumber()!=0L) {
					filterQuery.append(" AND a.id = :numeroDeDocumento ");
					parameterNames.add("numeroDeDocumento");
					parameterTypes.add(params.getDocumentNumber().getClass()
							.getName());
					parameters.add(params.getDocumentNumber());
				}
				if (params.getAdjustmentTypeId() != null) {
						filterQuery
								.append(" AND a.transferReason.adjustmentType.id =:tipoDeAjuste ");
						parameterNames.add("tipoDeAjuste");
						parameters.add(params.getAdjustmentTypeId());
						parameterTypes.add(params.getAdjustmentTypeId().getClass()
								.getName());
				}
					/************************
					 * PARAMETROS DE ORIGEN *
					 ************************/
					if (params.getWareHouseSourceId() != null) {
						filterQuery.append("AND ae.adjustment.id = a.id AND ae.warehouseSource.id=:idBodegaOrigen ");
						parameterNames.add("idBodegaOrigen");
						parameters.add(params.getWareHouseSourceId());
						parameterTypes.add(params.getWareHouseSourceId().getClass().getName());
					}
					if (params.getCrewSourceId() != null) {// Hay cuadrilla, osea
															// que no vale la pena
															// filtrar por sucursal
															// o por dealer, porque
															// este es mas
															// especifico
						filterQuery
								.append("AND ae.adjustment.id = a.id AND ae.warehouseSource.dealerId IS NOT NULL AND ae.warehouseSource.crewId.id=:idCuadrillaOrigen ");
						parameterNames.add("idCuadrillaOrigen");
						parameters.add(params.getCrewSourceId());
						parameterTypes.add(params.getCrewSourceId().getClass()
								.getName());
					}
					if (params.getBranchSourceId() != null) {// Hay sucursal,
																	// osea que no
																	// vale la pena
																	// buscar por
																	// dealer,
																	// porque este
																	// es mas
																	// especifico
							filterQuery.append("AND ae.adjustment.id = a.id AND ae.warehouseSource.dealerId IS NOT NULL AND ae.warehouseSource.dealerId.dealer IS NOT NULL AND ae.warehouseSource.dealerId.id=:idSucursalOrigen ");
							parameterNames.add("idSucursalOrigen");
							parameters.add(params.getBranchSourceId());
							parameterTypes.add(params.getBranchSourceId()
									.getClass().getName());
					}
					if (params.getDealerSourceId() != null) {
								filterQuery
										.append("AND ae.adjustment.id = a.id AND ae.warehouseSource.dealerId IS NOT NULL AND (ae.warehouseSource.dealerId.id=:idDealerOrigen OR ae.warehouseSource.dealerId.dealer.id=:idDealerOrigen) ");
								parameterNames.add("idDealerOrigen");
								parameters.add(params.getDealerSourceId());
								parameterTypes.add(params.getDealerSourceId()
										.getClass().getName());
					}
					if (params.getWareHouseSourceTypeId() != null) {
							filterQuery
									.append("AND ae.warehouseSource.warehouseType.id=:idTipoDeBodegaOrigen ");
							parameterNames.add("idTipoDeBodegaOrigen");
							parameters.add(params.getWareHouseSourceTypeId());
							parameterTypes.add(params.getWareHouseSourceTypeId()
									.getClass().getName());
					}
					/*************************
					 * PARAMETROS DE DESTINO *
					 *************************/
					if (params.getWareHouseDestinationId() != null) {
						filterQuery.append("AND ae.adjustment.id = a.id AND ae.warehouseDestination.id=:idBodegaDestino ");
						parameterNames.add("idBodegaDestino");
						parameters.add(params.getWareHouseDestinationId());
						parameterTypes.add(params.getWareHouseDestinationId().getClass().getName());
					}
					if (params.getCrewDestinationId() != null) {// Hay cuadrilla,
																// osea que no vale
																// la pena filtrar
																// por sucursal o
																// por dealer,
																// porque este es
																// mas especifico
						filterQuery
								.append("AND ae.adjustment.id = a.id AND ae.warehouseDestination.dealerId IS NOT NULL AND ae.warehouseDestination.crewId IS "
										+ "NOT NULL AND ae.warehouseDestination.crewId.id=:idCuadrillaDestino ");
						parameterNames.add("idCuadrillaDestino");
						parameters.add(params.getCrewDestinationId());
						parameterTypes.add(params.getCrewDestinationId().getClass()
								.getName());
					}
					if (params.getBranchDestinationId() != null) {// Hay
																		// sucursal,
																		// osea que
																		// no vale
																		// la pena
																		// buscar
																		// por
																		// dealer,
																		// porque
																		// este es
																		// mas
																		// especifico
							filterQuery
									.append("AND ae.adjustment.id = a.id AND ae.warehouseDestination.dealerId IS NOT NULL AND ae.warehouseDestination.dealerId.dealer IS NOT NULL AND ae.warehouseDestination.dealerId.id=:idSucursalDestino ");
							parameterNames.add("idSucursalDestino");
							parameters.add(params.getBranchDestinationId());
							parameterTypes.add(params.getBranchDestinationId()
									.getClass().getName());
						}
					if (params.getDealerDestinationId() != null) {
								filterQuery
										.append("AND ae.adjustment.id = a.id AND ae.warehouseDestination.dealerId IS NOT NULL AND (ae.warehouseDestination.dealerId.id=:idDealerDestino OR ae.warehouseDestination.dealerId.dealer.id=:idDealerDestino) ");
								parameterNames.add("idDealerDestino");
								parameters.add(params.getDealerDestinationId());
								parameterTypes.add(params.getDealerDestinationId()
										.getClass().getName());
					}
					if (params.getWareHouseDestinationTypeId() != null) {
							filterQuery
									.append("AND ae.warehouseDestination.warehouseType.id=:idTipoDeBodegaDestino ");
							parameterNames.add("idTipoDeBodegaDestino");
							parameters.add(params.getWareHouseDestinationTypeId());
							parameterTypes.add(params
									.getWareHouseDestinationTypeId().getClass()
									.getName());
					}
					//}

					if (params.getCreationDate() != null) {
						filterQuery.append("AND TRUNC(a.creationDate) = TRUNC(:fechaDeCreacion) ");
						parameterNames.add("fechaDeCreacion");
						parameters.add(params.getCreationDate());
						parameterTypes.add(params.getCreationDate().getClass()
								.getName());
					}
					
					if(params.getSerialElement()!=null && !params.getSerialElement().trim().equals("")){
						filterQuery.append(" AND (ae.serialized.serialCode=:serialDeElemento OR EXISTS(SELECT 1 FROM Serialized s WHERE s.id = ae.serialized.serialized.id AND s.serialCode=:serialDeElemento))");
						parameterNames.add("serialDeElemento");
						parameters.add(params.getSerialElement().toUpperCase().trim());
						parameterTypes.add(params.getSerialElement().getClass().getName());
					}
					
					if(params.getAdjustmentStateId()!=null && params.getAdjustmentStateId()!=0L){
						filterQuery.append(" AND a.adjustmentStatus.id = :adjustmentStatusId ");
						parameterNames.add("adjustmentStatusId");
						parameters.add(params.getAdjustmentStateId());
						parameterTypes.add(params.getAdjustmentStateId().getClass().getName());						
					}
			}
			stringQuery.append(filterQuery.toString());
			stringQuery.append(" group by a.id order by a.id desc ");
			String queryNormalString = stringQuery.toString();
			Query query = session.createQuery(queryNormalString);
			Long recordCount = 0L;
			for (int i = 0; i < parameters.size(); ++i) {
				if (Long.class.getName()
						.equalsIgnoreCase(parameterTypes.get(i))
						&& (parameters.get(i) instanceof Long)) {
					query.setLong(parameterNames.get(i), (Long) parameters
							.get(i));
				}
				if (Date.class.getName()
						.equalsIgnoreCase(parameterTypes.get(i))
						&& (parameters.get(i) instanceof Date)) {
					query.setDate(parameterNames.get(i), (Date) parameters
							.get(i));
				}
				if (String.class.getName().equalsIgnoreCase(
						parameterTypes.get(i))
						&& (parameters.get(i) instanceof String)) {
					query.setString(parameterNames.get(i), (String) parameters
							.get(i));
				}

			}
			if (requestCollInfo != null) {
				String queryCountString = countString.toString()+" from AdjustmentElements ae inner join  ae.adjustment a left join ae.warehouseDestination w1 left join ae.warehouseSource w2 where a.country.id = :countryId  "+filterQuery.toString();
				Query queryCount = session.createQuery(queryCountString);
				for (int i = 0; i < parameters.size(); ++i) {
					if (Long.class.getName()
							.equalsIgnoreCase(parameterTypes.get(i))
							&& (parameters.get(i) instanceof Long)) {
						queryCount.setLong(parameterNames.get(i), (Long) parameters
								.get(i));
					}
					if (Date.class.getName()
							.equalsIgnoreCase(parameterTypes.get(i))
							&& (parameters.get(i) instanceof Date)) {
						queryCount.setDate(parameterNames.get(i), (Date) parameters
								.get(i));
					}
					if (String.class.getName().equalsIgnoreCase(
							parameterTypes.get(i))
							&& (parameters.get(i) instanceof String)) {
						queryCount.setString(parameterNames.get(i), (String) parameters
								.get(i));
					}

				}
				recordCount = (Long) queryCount.uniqueResult();
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());
			}
			@SuppressWarnings("unchecked")
			List<AdjustmentVO> result = (List<AdjustmentVO>) query.list();

			List<AdjustmentResponseDTO> resultDTO = convertToListOfAdjustmentDTO(result);

			AdjustmentElementsResponse response = new AdjustmentElementsResponse();

			if (requestCollInfo != null)
				populatePaginationInfo(response, requestCollInfo.getPageSize(),
						requestCollInfo.getPageIndex(), resultDTO.size(),
						recordCount.intValue());
			response.setAdjustmentDTOElements(resultDTO);

			return response;
		} catch (Throwable ex) {
			if(ex instanceof BusinessException){
				throw (BusinessException)ex;
			}
			log.debug("== Error consultando los Adjustment ==");
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina searchAdjustmentsBySearchParameters/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#updateAdjustment
	 * (co.com.directv.sdii.model.pojo.Adjustment)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateAdjustment(Adjustment obj) throws DAOServiceException,
			DAOSQLException {

		log.debug("== Inicio updateAdjustment/AdjustmentDAO ==");
		Session session = super.getSession();
		try {
			session.merge(obj);
			this.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error actualizando el Adjustment ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina updateAdjustment/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#deleteAdjustment
	 * (co.com.directv.sdii.model.pojo.Adjustment)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteAdjustment(Adjustment obj) throws DAOServiceException,
			DAOSQLException {

		log.debug("== Inicio deleteAdjustment/AdjustmentDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
					.createQuery("delete from Adjustment entity where entity.id = :anEntityId");
			query.setLong("anEntityId", obj.getId());
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el Adjustment ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina deleteAdjustment/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#
	 * getAdjustmentsByID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Adjustment getAdjustmentByID(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAdjustmentByID/AdjustmentDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(Adjustment.class.getName());
			stringQuery.append(" entity where entity.id = :anId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anId", id);

			return (Adjustment) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAdjustmentByID/AdjustmentDAO ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Adjustment getAdjustmentByIDMassive(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAdjustmentByID/AdjustmentDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(Adjustment.class.getName());
			stringQuery.append(" entity where entity.id = :anId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anId", id);

			return (Adjustment) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAdjustmentByID/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#
	 * getAllAdjustments()
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Adjustment> getAllAdjustments() throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getAllAdjustments/AdjustmentDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(Adjustment.class.getName());
			return session.createQuery(stringQuery.toString()).list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAllAdjustments/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#
	 * getAllAdjustmentsByTransferReasonId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Adjustment> getAllAdjustmentsByTransferReasonId(
			Long transferReasonId) throws DAOServiceException, DAOSQLException {
		log
				.debug("== Inicia getAllAdjustmentsByTransferReasonId/AdjustmentDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(Adjustment.class.getName());
			stringQuery.append(" entity where ");
			stringQuery
					.append(" entity.transferReason.id = :transferReasonId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("transferReasonId", transferReasonId);
			return query.list();

		} catch (Throwable ex) {
			log
					.error(
							"== Error getAllAdjustmentsByTransferReasonId/AdjustmentDAO ==",
							ex);
			throw this.manageException(ex);
		} finally {
			log
					.debug("== Termina getAllAdjustmentsByTransferReasonId/AdjustmentDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.AdjustmentDAOLocal#
	 * getAllAdjustments()
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Adjustment> getAdjustmentsByCrewIdAndDistinctAdjustmentStatus(List<String> statusCodes,
			Long crewId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getAdjustmentsByCrewIdAndDistinctAdjustmentStatus/AdjustmentDAO ==");
		Session session = super.getSession();

		try {
			
        	StringBuffer stringQuery = new StringBuffer();
        	stringQuery.append( "select AD from " );
        	stringQuery.append( Adjustment.class.getName() +" AD, ");
        	stringQuery.append( AdjustmentElements.class.getName() +" AE ");
        	stringQuery.append( "where " );
        	stringQuery.append( "AD.id = AE.adjustment.id " );
        	stringQuery.append( "and AD.adjustmentStatus.code NOT IN (:aStatusCodes) " );
        	stringQuery.append( "and ( AE.warehouseSource.crewId.id = :aCrewId " );
			stringQuery.append( "or AE.warehouseDestination.crewId.id = :aCrewId ) " );
        	Query query = session.createQuery(stringQuery.toString());
            query.setParameterList("aStatusCodes", statusCodes);
            query.setLong("aCrewId", crewId);

            return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getAdjustmentsByCrewIdAndDistinctAdjustmentStatus/AdjustmentDAO ==");
		}
	}
	
}

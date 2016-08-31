package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.ComparatorUtil;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO;
import co.com.directv.sdii.model.dto.WareHouseElementClientFilterRequestDTO;
import co.com.directv.sdii.model.dto.WareHouseRequestDTO;
import co.com.directv.sdii.model.dto.collection.QuantityWarehouseElementResponse;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.RecordStatus;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WarehouseElement;
import co.com.directv.sdii.model.pojo.WarehouseElementQuantity;
import co.com.directv.sdii.model.pojo.WhElementSearchFilter;
import co.com.directv.sdii.model.pojo.collection.InventoryElementGroupDTOResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.pojo.collection.SerializedWhElementsByCriteriaPaginationResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementCustomerResponse;
import co.com.directv.sdii.model.pojo.collection.WareHouseElementResponse;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal;
import co.com.directv.sdii.reports.dto.FilterSerializedElementDTO;
import co.com.directv.sdii.reports.dto.InventoryElementGroupDTO;
import co.com.directv.sdii.reports.dto.WareHouseElementsReportDTO;
import co.com.directv.sdii.reports.dto.WarehouseSerializedNotSerializedActualDTO;

/**
 * 
 * DAO para el procesamiento de operaciones CRUD de la entidad WarehouseElement
 * 
 * Fecha de Creación: Mar 8, 2010
 * 
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.WarehouseElement
 * @see co.com.directv.sdii.model.hbm.WarehouseElement.hbm.xml
 */
@Stateless(name = "WarehouseElementDAOLocal", mappedName = "ejb/WarehouseElementDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class WarehouseElementDAO extends BaseDao implements WarehouseElementDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(WarehouseElementDAO.class);
	private final static String GREATER = ">=";
	private final static String LOWER = "<";
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	//CC053
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;

	
	// ======================================================================================================================
	// ======================== Métodos OK Según cambios en el modelo de datos de Inventarios ===============================
	// ======================================================================================================================
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#mergeWarehouseElement(co.com.directv.sdii.model.pojo.WarehouseElement)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void mergeWarehouseElement(WarehouseElement obj)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio mergeWarehouseElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			session.merge( obj );
			doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error actualizando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina mergeWarehouseElement/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * createWarehouseElement(co.com.directv.sdii.model.pojo.WarehouseElement)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement createWarehouseElement(WarehouseElement obj)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio createWarehouseElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			session.save(obj);
			this.doFlush(session);
			return obj;
		} catch (Throwable ex) {
			log.debug("== Error creando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina createWarehouseElement/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * updateWarehouseElement(co.com.directv.sdii.model.pojo.WarehouseElement)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateWarehouseElement(WarehouseElement obj)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio updateWarehouseElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			session.update( obj );
			doFlush(session);
			
		} catch (Throwable ex) {
			log.debug("== Error actualizando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina updateWarehouseElement/WarehouseElementDAO ==");
		}
	}


	

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * updateWarehouseElement(co.com.directv.sdii.model.pojo.WarehouseElement)
	 * 
	 * JFP
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Object[]> executeUpdateWarehousePackage(long referenceId, long warehouseTransit,
			long warehouseTarget, long movTypeCodeE, long movTypeCodeS,
			String recordStatusU, String recordStatusH,
			/*AGREGADOS*/
			String statusPending, String typeComunicationHspToIbs,
			String documentClass, long countryId,
			String processCode, String isManagement,
			String MovCmdNoConfig, Long userId
			)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio executeUpdateWarehousePackage/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			session.beginTransaction();

			Query query = session.getNamedQuery("moveWarehouseElements");
			query.setLong("P_ID_REMISION", referenceId);
			query.setLong("P_BODEGA_ORIGEN", warehouseTransit);
			query.setLong("P_BODEGA_DESTINO", warehouseTarget);
			query.setLong("P_TIPO_MOV_ENTRADA", movTypeCodeE);
			query.setLong("P_TIPO_MOV_SALIDA", movTypeCodeS);
			query.setString("P_STATUS_LAST", recordStatusU);
			query.setString("P_STATUS_HIST", recordStatusH);
			query.setString("P_STATUS_PENDING", statusPending);
			query.setString("P_TYPE_COMUNICATION_HSP_TO_IBS", typeComunicationHspToIbs);
			query.setString("P_DOCUMENT_CLASS", documentClass);
			query.setLong("P_COUNTRY_ID", countryId);
			query.setString("P_PROCESS_CODE", processCode);
			query.setString("P_IS_MANAGEMENT", isManagement);
			query.setString("P_MOV_CMD_NO_CONFIG_CODE", MovCmdNoConfig);
			query.setLong("P_USER_ID", userId);
			
			
						
			List<Object[]> items = query.list();			

	        return items;
	        
			
		} catch (Throwable ex) {
			log.debug("== Error ejecutando el executeUpdateWarehousePackage ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina executeUpdateWarehousePackage/WarehouseElementDAO ==");
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * deleteWarehouseElement(co.com.directv.sdii.model.pojo.WarehouseElement)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteWarehouseElement(WarehouseElement obj)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio deleteWarehouseElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from WarehouseElement entity where entity.id = :anEntityId");
			query.setLong("anEntityId", obj.getId());
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina deleteWarehouseElement/WarehouseElementDAO ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteNotSerializedWarehouseElementByElementID(Long elementId)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio deleteNotSerializedWarehouseElementByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from "+WarehouseElement.class.getName()+" entity where entity.notSerialized.elementId = :elementId");
			query.setLong("elementId", elementId);
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina deleteNotSerializedWarehouseElementByElementID/WarehouseElementDAO ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deletetSerializedWarehouseElementByElementID(Long elementId)
	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicio deletetSerializedWarehouseElementByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from "+WarehouseElement.class.getName()+" entity where entity.serialized.elementId = :elementId");
			query.setLong("elementId", elementId);
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el WarehouseElement ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina deletetSerializedWarehouseElementByElementID/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * getWarehouseElementsByID(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWarehouseElementByID(Long id)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWarehouseElementByID/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.id = :anId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anId", id);

			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementByID/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementsDAOLocal#
	 * getAllWarehouseElements()
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getAllWarehouseElements()
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getAllWarehouseElements/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			return session.createQuery(stringQuery.toString()).list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getAllWarehouseElements/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWarehouseElementsByCustomer(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getWarehouseElementsByCustomer(
			Long documentType, String document) throws DAOServiceException,
			DAOSQLException {
		log
		.debug("== Inicia getWarehouseElementsByCustomer/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.warehouseType.whTypeCode = :whType and");
			stringQuery.append(" entity.warehouseId.customerId.documentTypeId =:adocType and");
			stringQuery.append(" entity.warehouseId.customerId.documentNumber =:adoc ");
			stringQuery.append(" order by  entity.movementDate asc");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whType", CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
			query.setLong("adocType", documentType);
			query.setString("adoc", document);
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementsByCustomer/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWarehouseElementsByCustomerIBSCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCode(String ibsCode, RequestCollectionInfo requestCollInfo) throws DAOServiceException, DAOSQLException {
		
		log.debug("== Inicia getWarehouseElementsByCustomerIBSCode/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.warehouseType.whTypeCode = :whType and");
			stringQuery.append(" entity.warehouseId.customerId.customerCode = :aCode ");
			stringQuery.append(" order by  entity.movementDate asc");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
			query.setString("aCode", ibsCode);			
			
        	//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
            	countQuery.setString("aCode", ibsCode);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> wareHouse = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), wareHouse.size(), recordQty.intValue() );
        	response.setWareHouseElements( wareHouse );
        	
			return response;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementsByCustomerIBSCode/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWareHouseElementByActualWhAndWhAndElement(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWareHouseElementByActualWhAndWhAndElement(
			Long whID, String serialCode) throws DAOServiceException,
			DAOSQLException {
		log
		.debug("== Inicia getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :whID and");
			stringQuery.append(" entity.serialized.serialCode = :aSerialCode and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("whID", whID);
			query.setString("aSerialCode", serialCode.toUpperCase());
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber
	 * (java.lang.String, java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber(
			String customerIbsCode, Long documentTypeId,
			String customerDocumentNumber, Long countryId, RequestCollectionInfo requestCollInfo) throws DAOServiceException,DAOSQLException {
		
		log.debug("== Inicia getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			boolean customerIbsCodeSpecified = false, documentTypeIdSpecified = false, customerDocumentNumberSpecified = false;
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.recordStatus.recordStatusCode = :recordStatus and entity.warehouseId.warehouseType.whTypeCode = :whType ");

			if (customerIbsCode != null && customerIbsCode.trim().length() > 0) {
				customerIbsCodeSpecified = true;
				stringQuery.append(" and entity.warehouseId.customerId.customerCode = :aCode");
			}

			if (documentTypeId != null && documentTypeId.longValue() > 0) {
				documentTypeIdSpecified = true;
				stringQuery.append(" and entity.warehouseId.customerId.documentTypeId = :aDocTypeId");
			}

			if (customerDocumentNumber != null
					&& customerDocumentNumber.length() > 0) {
				customerDocumentNumberSpecified = true;
				stringQuery.append(" and entity.warehouseId.customerId.documentNumber = :aDocNumber");

			}
			stringQuery.append(" order by entity.movementDate asc ");
			
			//Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
			countQuery.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());

			if (customerIbsCodeSpecified) {
				query.setString("aCode", customerIbsCode);
				countQuery.setString("aCode", customerIbsCode);
			}
			if (documentTypeIdSpecified) {
				query.setLong("aDocTypeId", documentTypeId);
				countQuery.setLong("aDocTypeId", documentTypeId);
			}
			if (customerDocumentNumberSpecified) {
				query.setString("aDocNumber", customerDocumentNumber);
				countQuery.setString("aDocNumber", customerDocumentNumber);
			}

			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			countQuery.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> warehouseElements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
        	response.setWareHouseElements( warehouseElements );
        	
			return response;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByCustomerIBSCodeDocTypeIdDocNumberTypeWarehouseAndRecordStatus(java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWarehouseElementsByCustomerIBSCodeDocTypeIdDocNumberTypeWarehouseAndRecordStatus(String customerIbsCode, 
				                                                                                   Long documentTypeId,
				                                                                                   String customerDocumentNumber, 
				                                                                                   Long countryId, 
				                                                                                   String whType,
				                                                                                   String recordStatus,
				                                                                                   String whElemMovTypeClass,
				                                                                                   RequestCollectionInfo requestCollInfo) throws DAOServiceException,DAOSQLException {
		
		log.debug("== Inicia getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			boolean customerIbsCodeSpecified = false, 
			        documentTypeIdSpecified = false, 
			        customerDocumentNumberSpecified = false, 
			        whTypeSpecified = false, 
			        recordStatusSpecified = false, 
			        whElemMovTypeClassSpecified = false,
			        countryIdSpecified = false;
			
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			String sqlWhereOrAnd = " where ";
			
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName() + " entity ");

			if (whType != null && whType.trim().length() > 0) {
				whTypeSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.warehouseId.warehouseType.whTypeCode = :whType ");
				sqlWhereOrAnd = " and ";
			}

			if (recordStatus != null && recordStatus.trim().length() > 0) {
				recordStatusSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.recordStatus.recordStatusCode = :recordStatus ");
				sqlWhereOrAnd = " and ";
			}

			if (customerIbsCode != null && customerIbsCode.trim().length() > 0) {
				customerIbsCodeSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.warehouseId.customerId.customerCode = :aCode");
				sqlWhereOrAnd = " and ";
			}

			if (documentTypeId != null && documentTypeId.longValue() > 0) {
				documentTypeIdSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.warehouseId.customerId.documentTypeId = :aDocTypeId");
				sqlWhereOrAnd = " and ";
			}

			if (customerDocumentNumber != null
					&& customerDocumentNumber.length() > 0) {
				customerDocumentNumberSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.warehouseId.customerId.documentNumber = :aDocNumber");
				sqlWhereOrAnd = " and ";
			}
			
			if (whElemMovTypeClass != null
					&& whElemMovTypeClass.length() > 0) {
				whElemMovTypeClassSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.movementType.movClass = :aMovClass");
				sqlWhereOrAnd = " and ";
			}
			
			if (countryId != null && countryId.longValue() > 0) {
				countryIdSpecified = true;
				stringQuery.append(sqlWhereOrAnd + " entity.warehouseId.country.id = :aCountryId");
				sqlWhereOrAnd = " and ";
			}

			stringQuery.append(" order by entity.movementDate asc ");
			
			//Paginacion
        	stringCount.append("select count(*) ");
        	stringCount.append( stringQuery.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
			Query query = session.createQuery(stringQuery.toString());
			
			if (whTypeSpecified) {
				query.setString("whType",whType);
				countQuery.setString("whType",whType);
			}
			if (customerIbsCodeSpecified) {
				query.setString("aCode", customerIbsCode);
				countQuery.setString("aCode", customerIbsCode);
			}
			if (documentTypeIdSpecified) {
				query.setLong("aDocTypeId", documentTypeId);
				countQuery.setLong("aDocTypeId", documentTypeId);
			}
			if (customerDocumentNumberSpecified) {
				query.setString("aDocNumber", customerDocumentNumber);
				countQuery.setString("aDocNumber", customerDocumentNumber);
			}
			if (recordStatusSpecified) {
				query.setString("recordStatus", recordStatus);
				countQuery.setString("recordStatus", recordStatus);
			}
			if (whElemMovTypeClassSpecified) {
				query.setString("aMovClass", whElemMovTypeClass);
				countQuery.setString("aMovClass", whElemMovTypeClass);
			}
			if (countryIdSpecified) {
				query.setLong("aCountryId", countryId);
				countQuery.setLong("aCountryId", countryId);
			}
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> warehouseElements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
        	response.setWareHouseElements( warehouseElements );
        	
			return response;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByCustomerIBSCodeDocTypeIdAndDocNumber/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWarehouseElementsByElementTypeAndWhType(java.lang.Long,
	 * java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWarehouseElementsByElementTypeAndWhType(
			Long dealerID, Long idElementType, String serialCode, Long whType) throws DAOServiceException,
			DAOSQLException {
		log
		.debug("== Inicia getWarehouseElementsByElementTypeAndWhType/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.warehouseType.id = :whTypeID");
			stringQuery.append(" and entity.warehouseId.dealerId.id = :dealerID");
			stringQuery.append(" and entity.serialized.elementId != null");
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			
			if(idElementType!=0){
				stringQuery.append(" and entity.serialized.element.elementType.id = :idElementType");
			}
			if(!serialCode.equals("")&&serialCode.length()!=0){
				stringQuery.append(" and entity.serialized.serialCode = :serialCode ");
			}
			Query query = session.createQuery(stringQuery.toString());
			if(idElementType!=0){
				query.setLong("idElementType", idElementType);
			}
			if(!serialCode.equals("")&&serialCode.length()!=0){
				query.setString("serialCode", serialCode.toUpperCase());
			}
			query.setLong("whTypeID", whType);
			query.setLong("dealerID", dealerID);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementsByElementTypeAndWhType/WarehouseElementDAO ==");
		}
	}

	/**
	 * Metodo: Permite realizar una consulta generica de los elementos que
	 * cumplan con las condiciones del objeto enviado por parametro.
	 * 
	 * @param obj
	 *            WarehouseElement elemento para realizar las comparaciones
	 * @param comparator
	 *            String Compara la cantidad dependiendo del caso mayor o menor
	 * @return List<WarehouseElement> Lista con los elementos que cumplen con
	 *         las condiciones
	 * @throws DAOServiceException
	 *             Error en la consulta en la base de datos
	 * @throws DAOSQLException
	 *             Error en la consulta en la base de datos
	 * @author jnova
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	private List<WarehouseElement> getWareHouseElementByCriteriaToMoveNotSerializedGeneral(
			WarehouseElement obj, String comparator)
			throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicia getWareHouseElementByCriteriaToMoveNotSerializedGeneral/WarehouseElementDAO ==");
		try {
			Session session = super.getSession();

			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select warehouseElement from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			// idBodega
			stringQuery.append("warehouseElement.warehouseId.id = :aWhId ");
			// tipo de elemento No serializado
			stringQuery.append("and warehouseElement.notSerialized.element.isSerialized = :aIsSerialize ");
			// codigo de elemento
			stringQuery.append("and warehouseElement.notSerialized.elementId = :aElementId ");
			// cantidad actual
			stringQuery.append("and warehouseElement.actualQuantity "+ comparator + " :aActualQuantity ");
			// estado del registro
			stringQuery.append("and warehouseElement.recordStatus.recordStatusCode = :aRecordStatus ");

			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWhId", obj.getWarehouseId().getId());
			query.setString("aIsSerialize",CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity());
			query.setLong("aElementId", obj.getNotSerialized().getElementId());
			query.setDouble("aActualQuantity", obj.getActualQuantity());
			query.setString("aRecordStatus", obj.getRecordStatus().getRecordStatusCode());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWareHouseElementByCriteriaToMoveNotSerializedGeneral/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWareHouseElementByCriteriaToMoveNotSerialized
	 * (co.com.directv.sdii.model.pojo.WarehouseElement.WarehouseElement)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWareHouseElementByCriteriaToMoveNotSerializedGreater(
			WarehouseElement obj) throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicia getWareHouseElementByCriteriaToMoveNotSerializedGreater/WarehouseElementDAO ==");

		try {

			// Retorno el primer registro que haya encontrado en caso que haya
			// mas de uno que cumpla
			List<WarehouseElement> response = getWareHouseElementByCriteriaToMoveNotSerializedGeneral(obj, GREATER);
			if (response != null && response.size() > 0)
				return (WarehouseElement) response.get(0);
			else
				return null;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWareHouseElementByCriteriaToMoveNotSerializedGreater/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWareHouseElementByCriteriaToMoveNotSerialized
	 * (co.com.directv.sdii.model.pojo.WarehouseElement.WarehouseElement)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getWareHouseElementByCriteriaToMoveNotSerializedLower(
			WarehouseElement obj) throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicia getWareHouseElementByCriteriaToMoveNotSerializedLower/WarehouseElementDAO ==");

		try {
			List<WarehouseElement> elementsList = getWareHouseElementByCriteriaToMoveNotSerializedGeneral(obj, LOWER);
			if (elementsList != null)
				Collections.sort(elementsList, new ComparatorUtil("getActualQuantity", false));

			return elementsList;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWareHouseElementByCriteriaToMoveNotSerializedLower/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementByElementIdAndWhSource(java.lang.Long, java.lang.String,java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WarehouseElement> getWhElementByElementIdAndWhSource(
			Long warehouseId, String isSerialized, Long elementId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWhElementByElementIdAndWhSource/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			stringQuery.append(" warehouseElement.warehouseId.id = :aWarehouseId and warehouseElement.recordStatus.recordStatusCode = :recordStatus");
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" and warehouseElement.notSerialized.elementId = :aElementId");
			}
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" and warehouseElement.serialized.elementId = :aElementId");
			}
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWarehouseId", warehouseId);
			query.setLong("aElementId", elementId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementByElementIdAndWhSource/WarehouseElementDAO ==");
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WarehouseElement> getWhElementByElementIdAndNotWhType(
			Long elementId, String whType)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWhElementByElementIdAndNotWhType/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			stringQuery.append(" ( warehouseElement.notSerialized.elementId = :elementId OR warehouseElement.serialized.elementId = :elementId ) ");
			stringQuery.append(" AND warehouseElement.warehouseId.warehouseType.whTypeCode <> :whType ");

			Query query = session.createQuery(stringQuery.toString());
			query.setLong("elementId", elementId);
			query.setString("whType", whType);

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementByElementIdAndNotWhType/WarehouseElementDAO ==");
		}
	}	

	/* (non-Javadoc)
	 * 
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementInWHByWarehouseIdAndElementID(java.lang.Long, java.lang.String,java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWhElementInWHByWarehouseIdAndElementID(Long warehouseId, String isSerialized, Long elementId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getWhElementInWHByWarehouseIdAndElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			stringQuery.append(" warehouseElement.recordStatus.recordStatusCode = :aLastRsCode and warehouseElement.warehouseId.id = :aWarehouseId");
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" and warehouseElement.notSerialized.elementId = :aElementId");
			}
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" and warehouseElement.serialized.elementId = :aElementId");
			}
			
			stringQuery.append(" order by warehouseElement.movementDate desc");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aLastRsCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("aWarehouseId", warehouseId);
			query.setLong("aElementId", elementId);

			List<WarehouseElement> whEs = query.list();
			
			if(whEs.isEmpty()){
				return null;
			}
			return (WarehouseElement) whEs.get(0);

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementInWHByWarehouseIdAndElementID/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWareHouseElementByActualWhAndWhAndElement(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWareHouseElementByActualWhAndWhAndElement(Long whID, Long idElement) throws DAOServiceException,	DAOSQLException {
		log.debug("== Inicia getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		List<WarehouseElement> list = null;
		WarehouseElement r = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :whID and");
			stringQuery.append(" entity.notSerialized.element.id =:aNoSerializedID and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			stringQuery.append(" order by entity.movementDate DESC ");

			Query query = session.createQuery(stringQuery.toString());
			query.setLong("whID", whID);
			query.setLong("aNoSerializedID", idElement);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			list = query.list();
			
			if (list != null && !list.isEmpty())
				r = (WarehouseElement) query.list().get(0);
			
			return r;

		} catch (Throwable ex) {
			log.error("== Error en la operacion getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWareHouseElementByActualWhAndWhAndElement(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWareHouseElementByActualWhAndWhAndSerializedElement(Long whID, Long idElement) throws DAOServiceException,	DAOSQLException {
		log.debug("== Inicia getWareHouseElementByActualWhAndWhAndSerializedElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		List<WarehouseElement> list = null;
		WarehouseElement r = null;

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :whID and");
			stringQuery.append(" entity.serialized.element.id =:aSerializedID and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			stringQuery.append(" order by entity.movementDate DESC ");

			Query query = session.createQuery(stringQuery.toString());
			query.setLong("whID", whID);
			query.setLong("aSerializedID", idElement);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			list = query.list();
			
			if (list != null && !list.isEmpty())
				r = (WarehouseElement) query.list().get(0);
			
			return r;

		} catch (Throwable ex) {
			log.error("== Error en la operacion getWareHouseElementByActualWhAndWhAndSerializedElement/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWareHouseElementByActualWhAndWhAndSerializedElement/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#countWHElementByActualElementNotSerialized(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Double countWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode) throws DAOServiceException,	DAOSQLException {
		
		log.debug("== Inicia countWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			boolean islote = lote != null && !lote.equals("");
			boolean isModelCode = modelCode != null && !modelCode.equals("");
			boolean isElementStatusCode = elementStatusCode != null && !elementStatusCode.equals("");
			boolean isMeasureUnitCode = measureUnitCode != null && !measureUnitCode.equals("");
			
			String strCheckSerialized="";
			
			if(isSerialized.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()))
				strCheckSerialized="serialized";
			else
				strCheckSerialized="notSerialized";
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select sum(entity.actualQuantity) from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :aWhID");
			stringQuery.append(" and entity.warehouseId.country.id = :aCountryId");
			stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.typeElementCode =:aElementCode");
			stringQuery.append(" and entity." + strCheckSerialized + ".element.isSerialized =:aIsSerialized");
			
			if( isMeasureUnitCode )
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.measureUnit.unitCode = :aMeasureUnitCode");
			if( isElementStatusCode )
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementStatus.elementStatusCode =:aElementStatusCode");
			if(islote)
				stringQuery.append(" and entity." + strCheckSerialized + ".element.lote = :aLote");
			if(isModelCode)
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.elementModel.modelCode =:aModelCode");
									
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWhID", whID);
			query.setLong("aCountryId", countryId);
			query.setString("aElementCode", elementCode);
			query.setString("aIsSerialized", isSerialized);		
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			if( isMeasureUnitCode )
				query.setString("aMeasureUnitCode", measureUnitCode);
			if( isElementStatusCode )
				query.setString("aElementStatusCode", elementStatusCode);
			if(islote)
				query.setString("aLote", lote);
			if(isModelCode)
				query.setString("aModelCode", modelCode);			
			
			Double result = (Double) query.uniqueResult();
			result = (result == null ? 0D : result);
			return result;

		} catch (Throwable ex) {
			log.error("== Error en la operacion countWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina countWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Date maxDateWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode) throws DAOServiceException,	DAOSQLException {
		
		log.debug("== Inicia maxDateWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			boolean islote = lote != null && !lote.equals("");
			boolean isModelCode = modelCode != null && !modelCode.equals("");
			boolean isElementStatusCode = elementStatusCode != null && !elementStatusCode.equals("");
			boolean isMeasureUnitCode = measureUnitCode != null && !measureUnitCode.equals("");
			
			String strCheckSerialized="";
			
			if(isSerialized.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()))
				strCheckSerialized="serialized";
			else
				strCheckSerialized="notSerialized";
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select max(entity.movementDate) from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :aWhID");
			stringQuery.append(" and entity.warehouseId.country.id = :aCountryId");
			stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.typeElementCode =:aElementCode");
			stringQuery.append(" and entity." + strCheckSerialized + ".element.isSerialized =:aIsSerialized");
			
			if( isMeasureUnitCode )
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.measureUnit.unitCode = :aMeasureUnitCode");
			if( isElementStatusCode )
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementStatus.elementStatusCode =:aElementStatusCode");
			if(islote)
				stringQuery.append(" and entity." + strCheckSerialized + ".element.lote = :aLote");
			if(isModelCode)
				stringQuery.append(" and entity." + strCheckSerialized + ".element.elementType.elementModel.modelCode =:aModelCode");
									
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWhID", whID);
			query.setLong("aCountryId", countryId);
			query.setString("aElementCode", elementCode);
			query.setString("aIsSerialized", isSerialized);		
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			if( isMeasureUnitCode )
				query.setString("aMeasureUnitCode", measureUnitCode);
			if( isElementStatusCode )
				query.setString("aElementStatusCode", elementStatusCode);
			if(islote)
				query.setString("aLote", lote);
			if(isModelCode)
				query.setString("aModelCode", modelCode);			
			
			return (Date) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion maxDateWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina maxDateWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWHElementByActualElementNotSerialized(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Double)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getWHElementByActualElementNotSerialized(Long whID, Long countryId, 
			String lote, String measureUnitCode, String elementCode, String isSerialized, 
			String elementStatusCode, String modelCode, Double actualQuantity) throws DAOServiceException,	DAOSQLException {
		
		log.debug("== Inicia getWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			boolean islote = lote != null && !lote.equals("");
			boolean isModelCode = modelCode != null && !modelCode.equals("");
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :aWhID");
			stringQuery.append(" and entity.warehouseId.country.id = :aCountryId");
			stringQuery.append(" and entity.notSerialized.element.elementType.typeElementCode = :aElementCode");
			stringQuery.append(" and entity.notSerialized.element.isSerialized = :aIsSerialized");
			stringQuery.append(" and entity.notSerialized.element.elementStatus.elementStatusCode = :aElementStatusCode");			
			stringQuery.append(" and entity.notSerialized.element.elementType.measureUnit.unitCode = :aMeasureUnitCode");
			
			if(islote)
				stringQuery.append(" and entity.notSerialized.element.lote = :aLote");
			if(isModelCode)
				stringQuery.append(" and entity.notSerialized.element.elementType.elementModel.modelCode =:aModelCode");
									
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWhID", whID);
			query.setLong("aCountryId", countryId);
			query.setString("aElementCode", elementCode);
			query.setString("aIsSerialized", isSerialized);
			query.setString("aElementStatusCode", elementStatusCode);			
			query.setString("aMeasureUnitCode", measureUnitCode);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());	
			
			if(islote)
				query.setString("aLote", lote);
			if(isModelCode)
				query.setString("aModelCode", modelCode);			
			
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWHElementByActualElementNotSerialized/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWHouseElementByActualElementsCodes(java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWHouseElementByActualElementsCodes(String whCode, 
			Long countryId, String serialCode, String elementCode, String elementStatusCode, 
			String isSerialized, String ird , String linkedSerialCode, String modelCode, String lote, 
			String measureUnitCode, Long actualQuantity) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getWHouseElementByActualElementsCodes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			boolean isLinkedSerial = linkedSerialCode != null && !linkedSerialCode.equals("");
			boolean isIrd = ird != null && !ird.equals("");
			boolean islote = lote != null && !lote.equals("");
			boolean isMeasureUnitCode = measureUnitCode != null && !measureUnitCode.equals("");
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.whCode = :aWhCode");
			stringQuery.append(" and entity.warehouseId.country.id = :aCountryId");
			stringQuery.append(" and entity.serialized.element.elementType.typeElementCode = :aElementCode");
			stringQuery.append(" and entity.serialized.serialCode = :aSerialCode ");
			stringQuery.append(" and entity.serialized.element.isSerialized = :aIsSerialized");
			stringQuery.append(" and entity.serialized.element.elementStatus.elementStatusCode = :aElementStatusCode");
			stringQuery.append(" and entity.serialized.element.elementType.elementModel.modelCode = :aModelCode");
			
			if(isIrd)
				stringQuery.append(" and entity.serialized.ird = :aIrd");
			if(isLinkedSerial)
				stringQuery.append(" and entity.serialized.serialized.serialCode = :aLinkedSerial ");
			if(islote)
				stringQuery.append(" and entity.serialized.element.lote = :aLote");
			if(isMeasureUnitCode)
				stringQuery.append(" and entity.serialized.element.elementType.measureUnit.unitCode = :aMeasureUnitCode");
			
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aWhCode", whCode);
			query.setLong("aCountryId", countryId);
			query.setString("aElementCode", elementCode);
			query.setString("aSerialCode", serialCode.toUpperCase());			
			query.setString("aIsSerialized", isSerialized);
			query.setString("aElementStatusCode", elementStatusCode);
			query.setString("aModelCode", modelCode);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());	

			
			if(isIrd)
				query.setString("aIrd", ird);
			if(isLinkedSerial)
				query.setString("aLinkedSerial", linkedSerialCode.toUpperCase());
			if(islote)
				query.setString("aLote", lote);
			if(isMeasureUnitCode)
				query.setString("aMeasureUnitCode", measureUnitCode);
			
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getWHouseElementByActualElementsCodes/WarehouseElementDAO==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWHouseElementByActualElementsCodes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWareHouseElementByActualWhAndElementID(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public WarehouseElement getWareHouseElementByActualWhAndElementID(Long whID, String serialCode, String ird , Long elementId) throws DAOServiceException,
			DAOSQLException {
		log
		.debug("== Inicia getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			boolean isSerial = !StringUtils.isBlank(serialCode);
			boolean isIrd = !StringUtils.isBlank(ird);
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :whID");
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			if ( elementId != null && !elementId.equals(0L) ){
				stringQuery.append(" and entity.serialized.elementId = :elementId");
			} else if( isSerial ){
				stringQuery.append(" and entity.serialized.serialCode = :serialCode ");
			} else if( isIrd ){
				stringQuery.append(" and entity.serialized.ird = :ird");
			}
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("whID", whID);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());	
			
			if ( elementId != null && !elementId.equals(0L) ){
				query.setLong("elementId", elementId);
			} else if( isSerial ){
				query.setString("serialCode", serialCode.toUpperCase());
			} else if( isIrd ){
				query.setString("ird", ird);
			}
			
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWareHouseElementByActualWhAndWhAndElement/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementByTargetWhIdAndElementTypeIdNotSer(java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWarehouseElementByTargetWhIdAndElementTypeIdNotSer(
			Long targetWhId, Long elementTypeId, Double quantity)  throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWarehouseElementByTargetWhIdAndElementTypeIdNotSer/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :anwhId and entity.notSerialized.element.elementType.id = :anElementTypeId and entity.actualQuantity >= :aQuantity and entity.recordStatus.recordStatusCode = :recordStatusCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anwhId", targetWhId);
			query.setLong("anElementTypeId", elementTypeId);
			query.setDouble("aQuantity", quantity);
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementByTargetWhIdAndElementTypeIdNotSer/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast(Long whId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery
			.append(" entity where entity.warehouseId.id = :anwhId and entity.recordStatus.recordStatusCode = :recordStatusCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anwhId", whId);
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseSerializedNotSerializedActualDTO> getWarehouseElementsByWHIdAndRecordLast(Long whId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicio getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append(" select new "+WarehouseSerializedNotSerializedActualDTO.class.getName() );
			stringQuery.append(" ((select entity2  ");
			stringQuery.append("     from NotSerialized entity2 ");  
			stringQuery.append("    where entity.notSerialized.id=entity2.id) AS notSerialized,");
			stringQuery.append("  (select entity2  ");
			stringQuery.append("     from Serialized entity2 ");  
			stringQuery.append("    where entity.serialized.id=entity2.id) AS serialized, ");
			stringQuery.append("    sum(actualQuantity) AS actualQuantity, ");
			stringQuery.append("    max(entity.movementDate) AS movementDate) ");
			stringQuery.append(" from ");
			stringQuery.append(WarehouseElement.class.getName()).append(" entity  ");
			stringQuery.append(" where entity.warehouseId.id = :anwhId ");
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatusCode ");
			stringQuery.append(" group by entity.notSerialized.id, ");
			stringQuery.append(" entity.serialized.id ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anwhId", whId);
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByWHIdTargetExitDateNullAndRecordLast/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WarehouseElement> getWhElementsByWhSourceIdAndSerializedAndElementIdInWH(
			Long warehouseId, String isSerialized, Long elementId)
			throws DAOServiceException, DAOSQLException {
				log.debug("== Inicia getWhElementsByWhSourceIdAndSerializedAndElementIdInWH/WarehouseElementDAO ==");
		Session session = super.getSession();
		boolean serialized = false;
		boolean notSerialized = false;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			stringQuery.append("warehouseElement.warehouseId.id = :aWarehouseId ");
			if(isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append("and warehouseElement.notSerialized <> NULL ");
				notSerialized = true;
			}
			if(isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append("and warehouseElement.serialized <> NULL ");
				serialized = true;
			}
			if(elementId != null && elementId!=0 && notSerialized){
				stringQuery.append("and warehouseElement.notSerialized.elementId = :aElementId ");
			} else if(elementId != null && elementId!=0&& serialized){
				stringQuery.append("and warehouseElement.serialized.elementId = :aElementId ");
			}
			stringQuery.append("and warehouseElement.recordStatus.recordStatusCode = :recordStatus ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWarehouseId", warehouseId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			if(elementId != null && elementId!=0){
				query.setLong("aElementId", elementId);
			}	
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementsByWhSourceIdAndSerializedAndElementIdInWH/WarehouseElementDAO ==");
		}
	}

	 /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementsTypesInWareHouseByType(java.lang.Long, boolean)
	 */
	public List<ElementType>getElementsTypesInWareHouseByType( Long wareHouseId,boolean serialized )throws DAOServiceException, DAOSQLException
	 {
		 log.debug("== Inicia getElementsTypesInWareHouseByType/WarehouseElementDAO ==");
			Session session = super.getSession();
			try {
				StringBuffer stringQuery = new StringBuffer();
				stringQuery.append("select distinct ");
				stringQuery.append( serialized ? "we.serialized.element.elementType from " :"we.notSerialized.element.elementType from ");
				stringQuery.append(WarehouseElement.class.getName());
				stringQuery.append(" we where we.warehouseId.id= :wareHouseId and we.actualQuantity > 0 ");
             
				if( serialized )
					stringQuery.append("and we.recordStatus.recordStatusCode = :recordStatus ");
				
				Query query = session.createQuery(stringQuery.toString());
				query.setParameter( "wareHouseId" ,wareHouseId);
				
				if( serialized )
					query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
					
			    return query.list();	
			
			} catch (Throwable ex) {
				log.error("== Error getElementsTypesInWareHouseByType/WarehouseElementDAO ==");
				throw this.manageException(ex);
			} finally {
				log.debug("== Termina getElementsInWareHouseByType/WarehouseElementDAO ==");
			}
	 }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getSerializedWhElementsByCriteria(java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long)
	 */
    @SuppressWarnings("unchecked")
	public SerializedWhElementsByCriteriaPaginationResponse getSerializedWhElementsByCriteria(Long warehouseId , Long typeId, Long modelId, RequestCollectionInfo requestCollectionInfo)throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getWhElementsByCriteria/WarehouseElementDAO ==");
		Session session = super.getSession();
		SerializedWhElementsByCriteriaPaginationResponse r = new SerializedWhElementsByCriteriaPaginationResponse();
        StringBuffer stringCountQuery = new StringBuffer("select count(*) ");
        Long totalRowCount = 0L;
        int firstResult = 0;
        int maxResult = 0;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" we where ");
			stringQuery.append("we.serialized.element.elementType.id= :typeId ");
			if( ! (modelId == null) && !( modelId <= 0L ) ){
				stringQuery.append("and we.serialized.element.elementType.elementModel.id= :modelId ");
			}
			stringQuery.append("and we.warehouseId.id= :warehouseId ");
			stringQuery.append("and we.recordStatus.recordStatusCode= :recordStatusCode ");
			stringQuery.append("order by we.movementDate DESC ");
			
            stringCountQuery.append(stringQuery.toString());
			Query query = session.createQuery(stringQuery.toString());
			Query countQuery = session.createQuery(stringCountQuery.toString());
			
			query.setLong("warehouseId", warehouseId);
			query.setLong("typeId", typeId);
			if( ! (modelId == null) && !( modelId <= 0L ) ){
				query.setLong("modelId", modelId);
			}
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

            // Paginación
            if (requestCollectionInfo != null){
            	countQuery.setLong("warehouseId", warehouseId);
            	countQuery.setLong("typeId", typeId);
    			if( ! (modelId == null) && !( modelId <= 0L ) ){
    				countQuery.setLong("modelId", modelId);
    			}
    			countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

            	totalRowCount = (Long) countQuery.uniqueResult();
            	firstResult = requestCollectionInfo.getFirstResult();
            	maxResult = requestCollectionInfo.getMaxResults();
            	query.setFirstResult(firstResult);
            	query.setMaxResults(maxResult);
            }
            r.setCollection(query.list());
            if( requestCollectionInfo != null )
            	populatePaginationInfo(r, requestCollectionInfo.getPageSize(), requestCollectionInfo.getPageIndex(), r.getCollection().size(), totalRowCount.intValue());
			return r;

		} catch (Throwable ex) {
			log.error("== Error getWhElementsByCriteria/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementsByCriteria/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getLastWarehouseElementsByWHIdTarget(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWareHouseElementHistoricalForSerializedElement( Long serializedId, 
			                                                                           RequestCollectionInfo requestCollInfo,
			                                                                           boolean woutMovementTypeOut)throws DAOServiceException, DAOSQLException
	{
		log.debug("== Inicio getWareHouseElementHistoricalForSerializedElement/WarehouseElementDAO ==");
		Session session = super.getSession();
		
		try{
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.elementId = :serializedId " );
			
			if(woutMovementTypeOut){
				stringQuery.append(" and not exists(select 1" );
				stringQuery.append("                  from ").append(WarehouseElement.class.getName()).append(" entitySub ");
				stringQuery.append("                 where entity.id=entitySub.sourceRecord.id ");
				stringQuery.append("                       and entity.serialized.elementId = :serializedId) ");
			}
			
			stringQuery.append(" order by entity.movementDate desc " );
			
			Query query = session.createQuery( stringQuery.toString() );
            query.setParameter("serializedId" , serializedId);
            
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	 
        		stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setParameter( "serializedId" , serializedId);
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}		
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
			List<WarehouseElement> listaPojos = query.list(); 					
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listaPojos.size(), recordQty.intValue() );
        	response.setWareHouseElements( listaPojos );
        	
			return response;			
		} catch (Throwable ex) {
			log.error("== Error en getWareHouseElementHistoricalForSerializedElement/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWareHouseElementHistoricalForSerializedElement/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getLastWarehouseElementsByWHIdTarget(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getLastWarehouseElementsByWHIdTarget(
			Long warehouseId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getLastWarehouseElementsByWHIdTarget/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery
			.append(" entity where entity.warehouseId.id = :aWhId and entity.recordStatus.recordStatusCode = :aLastRSCode and entity.actualQuantity > 0");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aWhId", warehouseId);
			query.setString("aLastRSCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return query.list();
		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getLastWarehouseElementsByWHIdTarget/WarehouseElementDAO ==");
		}
	}	

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementsByWhIdAndElementTypeCodeAndSerials(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWhElementsByWhIdAndElementTypeCodeAndSerials(
			Long warehouseId, Long elementTypeId1, Long elementTypeId2,
			String serialEl1, String serialEl2) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWhElementsByWhIdAndElementTypeCodeAndSerials/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQueryFirstElement = new StringBuffer();
			stringQueryFirstElement.append("from ");
			stringQueryFirstElement.append(WarehouseElement.class.getName());
			stringQueryFirstElement.append(" whe where whe.warehouseId.id = :aWhId and  ");
			stringQueryFirstElement.append(" whe.serialized is not null and whe.serialized.element.elementType.id = :aFisrtEtId and ");
			stringQueryFirstElement.append(" whe.serialized.serialCode = :aFisrtSerCode and whe.recordStatus.recordStatusCode = :aWheStCode");
			Query queryFirstElement = session.createQuery(stringQueryFirstElement.toString());
			queryFirstElement.setLong("aWhId", warehouseId);
			queryFirstElement.setLong("aFisrtEtId", elementTypeId1);
			queryFirstElement.setString("aFisrtSerCode", serialEl1.toUpperCase());
			queryFirstElement.setString("aWheStCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			List<WarehouseElement> resultFirstElement = queryFirstElement.list();
			
			StringBuffer stringQuerySecondElement = new StringBuffer();
			stringQuerySecondElement.append("from ");
			stringQuerySecondElement.append(WarehouseElement.class.getName());
			stringQuerySecondElement.append(" whe where whe.warehouseId.id = :aWhId and  ");
			stringQuerySecondElement.append(" whe.serialized is not null and whe.serialized.element.elementType.id = :aSecEtId and ");
			stringQuerySecondElement.append(" whe.serialized.serialCode = :aSecSerCode and whe.recordStatus.recordStatusCode = :aWheStCode");
			Query querySecondElement = session.createQuery(stringQuerySecondElement.toString());
			querySecondElement.setLong("aWhId", warehouseId);
			querySecondElement.setLong("aSecEtId", elementTypeId2);
			querySecondElement.setString("aSecSerCode", serialEl2.toUpperCase());
			querySecondElement.setString("aWheStCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			List<WarehouseElement> resultSecondElement = querySecondElement.list();
			
			List<WarehouseElement> result = new ArrayList<WarehouseElement>();
			if( resultFirstElement != null && !resultFirstElement.isEmpty() && resultSecondElement != null && !resultSecondElement.isEmpty()){
				result.add( resultFirstElement.get(0) );
				result.add( resultSecondElement.get(0) );
			}
			
			return result;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWhElementsByWhIdAndElementTypeCodeAndSerials/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByDealerAndCrewAndTypeAndSerial(java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWarehouseElementByDealerAndCrewAndTypeAndSerial(
			String dealerCode, Long crewId, String elementTypeCode, String serial)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementDAO ==");
		Session session = super.getSession();
		WarehouseElement result = null;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.recordStatus.recordStatusCode = :recordStatus ");
			boolean dealerIdSpecified = false,crewIdSpecified = false, elementTypeCodeSpecified = false, serialSpecified = false;
			if (elementTypeCode != null && elementTypeCode.trim().length() > 0) {
				elementTypeCodeSpecified = true;
				if (serial != null && !serial.equals("")) {
					stringQuery.append(" and whe.serialized.element.elementType.typeElementCode = :anElTypeCodeSer  ");
				}else{
					stringQuery.append(" and whe.notSerialized.element.elementType.typeElementCode = :anElTypeCodeNotSer ");
				}	
			}
			if (crewId != null && crewId.longValue() > 0) {
				crewIdSpecified = true;
				stringQuery.append(" and whe.warehouseId.crewId.id = :aCrewId ");
			} 
			if (dealerCode != null && !dealerCode.equals("")) {
				dealerIdSpecified = true;
				stringQuery.append(" and whe.warehouseId.dealerId.dealerCode = :aDealerId ");
			}
			if (serial != null && !serial.equals("")) {
				serialSpecified = true;
				stringQuery.append(" and whe.serialized.serialCode = :aSerialCode ");
			}
			Query query = session.createQuery(stringQuery.toString());
			if (elementTypeCodeSpecified) {
				if (serialSpecified){
					query.setString("anElTypeCodeSer", elementTypeCode);
				}else{
					query.setString("anElTypeCodeNotSer", elementTypeCode);
				}
			}
			if (crewIdSpecified) {
				query.setLong("aCrewId", crewId);
			} 
			if (dealerIdSpecified) {
				query.setString("aDealerId", dealerCode);
			}
			if (serialSpecified) {
				query.setString("aSerialCode", serial.toUpperCase());
			}
			
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			result = (WarehouseElement)query.uniqueResult();
		} catch (Throwable ex) {
			log.error("== Error en getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByDealerAndCrewAndTypeAndSerial/WarehouseElementDAO ==");
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementByCountryAndSerial(java.lang.Long, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long existsWarehouseElementByCountryAndSerial(
			String serial, Long countryId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementByCountryAndSerial/WarehouseElementDAO ==");
		Long recordQty = 0L;
		Session session = super.getSession();
		//WarehouseElement result = null;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select count(*) ");
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.recordStatus.recordStatusCode = :recordStatus ");
			boolean countryIdSpecified = false, serialSpecified = false;
			if (countryId != null && countryId.longValue() > 0) {
				countryIdSpecified = true;
				stringQuery.append(" and whe.warehouseId.country.id = :countryId ");
			} 
			if (serial != null && !serial.equals("")) {
				serialSpecified = true;
				stringQuery.append(" and whe.serialized.serialCode = :serialCode ");
			}
			Query query = session.createQuery(stringQuery.toString());
			if (countryIdSpecified) {
				query.setLong("countryId", countryId);
			} 
			if (serialSpecified) {
				query.setString("serialCode", serial.toUpperCase());
			}
			
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			//result = (WarehouseElement)query.uniqueResult();
			recordQty = (Long)query.uniqueResult();
		} catch (Throwable ex) {
			log.error("== Error en getWarehouseElementByCountryAndSerial/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementByCountryAndSerial/WarehouseElementDAO ==");
		}
		return recordQty;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeco.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#
	 * getWarehouseElementBySerialNumAndExitDateNull(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWarehouseElementBySerialNumAndExitDateNull(
			Long elementId,Long wareHouseId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementBySerialNumAndExitDateNull/WarehouseElementDAO ==");

		Session session = this.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.elementId = :aElementId and");
			stringQuery.append(" entity.warehouseId.id = :wareHouseId and ");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");

			Query query = session.createQuery(stringQuery.toString());
			query.setParameter( "aElementId", elementId);
			query.setParameter( "wareHouseId" , wareHouseId);
			query.setParameter( "recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementBySerialNumAndExitDateNull/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByWHIdSource(java.lang.Long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getWarehouseElementsByWHIdSource(Long whId)
	throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicio getWarehouseElementsByWHIdSource/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery
			.append(" entity where entity.warehouseId.id = :anwhId");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anwhId", whId);

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementsByWHIdSource/WarehouseElementDAO ==");
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<WarehouseElement> getWarehouseElementsByWHIdSourceAndLastRecordStatus(Long whId,boolean notLinked)
	throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicio getWarehouseElementsByWHIdSourceAndLastRecordStatus/WarehouseElementDAO ==");
		Session session = super.getSession();
		
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :anwhId ");
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus ");
			
			if(notLinked){
				stringQuery.append(" and not exists(select entitySub.id ");
				stringQuery.append(" 	        	  from WarehouseElement entitySub ");
				stringQuery.append(" 		         where entitySub.warehouseId.id = entity.warehouseId.id "); 
				stringQuery.append(" 			       and entitySub.recordStatus.id = entity.recordStatus.id ");
				stringQuery.append(" 			       and entitySub.serialized.serialized.elementId=entity.serialized.elementId) ");
			}
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("anwhId", whId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementsByWHIdSourceAndLastRecordStatus/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementByWhAndElementType(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWhElementByWhAndElementType(
			Long idWh, Long elementType,String isSerialized,String serial, String recordStatusCode,Long countryId) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWhElementByWhAndElementType/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" we where ");
			if(isSerialized.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
				stringQuery.append("we.serialized.element.elementType.id= :elementType ");
				stringQuery.append("and we.serialized.serialCode = :serial ");
				stringQuery.append("and we.serialized.element.country.id = :countryId ");
			}else{
				stringQuery.append("we.notSerialized.element.elementType.id= :elementType ");
			}
			stringQuery.append("and we.warehouseId.id= :idWh ");
			stringQuery.append("and we.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			if(isSerialized.equals(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity())){
				query.setString("serial", serial.toUpperCase() );
				query.setLong("countryId", countryId );
			}
			query.setLong("idWh", idWh);
			query.setLong("elementType", elementType);
			query.setString("recordStatus", recordStatusCode);
			
			return query.list();

		} catch (Throwable ex) {
			log.error("== Error getWhElementByWhAndElementType/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementByWhAndElementType/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByFilters(co.com.directv.sdii.model.pojo.WhElementSearchFilter, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getWarehouseElementsByFilters(WhElementSearchFilter whElSerachFilter, RequestCollectionInfo requestCollInfo)	throws DAOServiceException, DAOSQLException {

		log.debug("== Inicia getWarehouseElementsByFilters/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			
			if(log.isDebugEnabled()){
				log.debug("Los filtros recibidos: " + whElSerachFilter.toString());
			}
						
			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.recordStatus.recordStatusCode = :aRecordStatusCode and");

			boolean wareHouseIdSpecified = false, dealerIdSpecified = false, branchDealerIdSpecified = false, crewIdSpecified = false;
			boolean warehouseTypeIdSpecified = false, elementTypeCodeSpecified = false, elementModelIdSpecified = false;
			boolean elementTypeIdSpecified = false, serialElementSpecified = false, initialDateSpecified = false, finalDateSpecified = false;
			
			if (whElSerachFilter.getWarehouseTypeId() != null && whElSerachFilter.getWarehouseTypeId().longValue() > 0) {
				warehouseTypeIdSpecified = true;
				stringQuery.append(" whe.warehouseId.warehouseType.id = :aWhTypeId and");
			}

			if (whElSerachFilter.getElementTypeCode() != null && whElSerachFilter.getElementTypeCode().trim().length() > 0) {
				elementTypeCodeSpecified = true;
				
				StringBuffer etQuery = new StringBuffer("from ");
				etQuery.append(ElementType.class.getName());
				etQuery.append(" et where et.typeElementCode = :anElementTypeCode");
				Query etQueryH = session.createQuery(etQuery.toString());
				etQueryH.setString("anElementTypeCode", whElSerachFilter.getElementTypeCode());
				ElementType et = (ElementType)etQueryH.uniqueResult();
				
				if(et == null){
					throw new DAOServiceException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				}
				
				if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(et.getIsSerialized())){
					stringQuery.append(" whe.serialized.element.elementType.typeElementCode = :anElTypeCode and");
				}else{
					stringQuery.append(" whe.notSerialized.element.elementType.typeElementCode = :anElTypeCode and");
				}
			}
			
			if (whElSerachFilter.getElementTypeId() != null && whElSerachFilter.getElementTypeId().longValue() > 0) {
				elementTypeIdSpecified = true;
				StringBuffer etQuery = new StringBuffer("from ");
				etQuery.append(ElementType.class.getName());
				etQuery.append(" et where et.id = :anElementTypeId");
				Query etQueryH = session.createQuery(etQuery.toString());
				etQueryH.setLong("anElementTypeId", whElSerachFilter.getElementTypeId());
				ElementType et = (ElementType)etQueryH.uniqueResult();
				
				if(et == null){
					throw new DAOServiceException(ErrorBusinessMessages.ELEMENT_NOT_EXIST.getCode(),ErrorBusinessMessages.ELEMENT_NOT_EXIST.getMessage());
				}
				
				if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(et.getIsSerialized())){
					stringQuery.append(" whe.notSerialized is null and whe.serialized.element.elementType.id = :anElTypeId and");
				}else{
					stringQuery.append(" whe.serialized is null and whe.notSerialized.element.elementType.id = :anElTypeId and");
				}
			}
			
			if (whElSerachFilter.getSerialElement() != null && whElSerachFilter.getSerialElement().trim().length() > 0) {
				serialElementSpecified = true;
				stringQuery.append(" whe.notSerialized is null and whe.serialized.serialCode = :aSerialCode and");
			}
			
			if (whElSerachFilter.getInitialDate() != null) {
				initialDateSpecified = true;
				stringQuery.append(" whe.movementDate >= :anInitialDate and");
			}
			
			if (whElSerachFilter.getFinalDate() != null) {
				finalDateSpecified = true;
				stringQuery.append(" whe.movementDate <= :aFinalDate and");
			}

			if (whElSerachFilter.getElementModelId() != null && whElSerachFilter.getElementModelId().longValue() > 0) {
				elementModelIdSpecified = true;
				boolean isSerialized = this.elementModelIsSerialized(session, whElSerachFilter.getElementModelId());
				if(isSerialized){
					stringQuery.append(" whe.notSerialized is null and whe.serialized.element.elementType.elementModel.id = :anElModelId and");
				}else{
					stringQuery.append(" whe.serialized is null and whe.notSerialized.element.elementType.elementModel.id = :anElModelId and");
				}
			}

			if (whElSerachFilter.getWareHouseId() != null && whElSerachFilter.getWareHouseId().longValue() > 0) {
				wareHouseIdSpecified = true;
				stringQuery.append(" whe.warehouseId = :aWhId and");
			} else {
				if (whElSerachFilter.getCrewId() != null && whElSerachFilter.getCrewId().longValue() > 0) {
					crewIdSpecified = true;
					stringQuery.append(" whe.warehouseId.crewId.id = :aCrewId and");
				} else if (whElSerachFilter.getBranchDealerId() != null
						&& whElSerachFilter.getBranchDealerId().longValue() > 0) {
					branchDealerIdSpecified = true;
					stringQuery.append(" whe.warehouseId.dealerId.id = :aBranchDealerId and");
				} else if (whElSerachFilter.getDealerId() != null && whElSerachFilter.getDealerId().longValue() > 0) {
					dealerIdSpecified = true;
					stringQuery.append(" whe.warehouseId.dealerId.id = :aDealerId and");
				}
			}
			
			String finalQuery = StringUtils.removeEnd(stringQuery.toString(),"and");
			Query query = session.createQuery(finalQuery);
			
			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append( finalQuery );
			Query countQuery = session.createQuery( stringCount.toString() );

			if(log.isDebugEnabled()){
				log.debug("El query finalmente queda: [" + finalQuery + "]");
			}
			
			query.setString("aRecordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			countQuery.setString("aRecordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			
			if(log.isDebugEnabled()){
				log.debug("El código del record status es: [" + CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity() + "]");
			}
			
			if (warehouseTypeIdSpecified) {
				query.setLong("aWhTypeId", whElSerachFilter.getWarehouseTypeId());
				countQuery.setLong("aWhTypeId", whElSerachFilter.getWarehouseTypeId());
			}

			if (elementTypeCodeSpecified) {
				query.setString("anElTypeCode", whElSerachFilter.getElementTypeCode());
				countQuery.setString("anElTypeCode", whElSerachFilter.getElementTypeCode());
			}
			
			if(elementTypeIdSpecified){
				query.setLong("anElTypeId", whElSerachFilter.getElementTypeId());
				countQuery.setLong("anElTypeId", whElSerachFilter.getElementTypeId());
			}
			
			if(serialElementSpecified){
				query.setString("aSerialCode", whElSerachFilter.getSerialElement().toUpperCase());
				countQuery.setString("aSerialCode", whElSerachFilter.getSerialElement().toUpperCase());
			}
			
			if(initialDateSpecified){
				query.setTimestamp("anInitialDate", whElSerachFilter.getInitialDate());
				countQuery.setTimestamp("anInitialDate", whElSerachFilter.getInitialDate());
			}
			
			if(finalDateSpecified){
				query.setTimestamp("aFinalDate", whElSerachFilter.getFinalDate());
				countQuery.setTimestamp("aFinalDate", whElSerachFilter.getFinalDate());
			}

			if (elementModelIdSpecified) {
				query.setLong("anElModelId", whElSerachFilter.getElementModelId());
				countQuery.setLong("anElModelId", whElSerachFilter.getElementModelId());
			}

			if (wareHouseIdSpecified) {
				query.setLong("aWhId", whElSerachFilter.getWareHouseId());
				countQuery.setLong("aWhId", whElSerachFilter.getWareHouseId());
			} else {
				if (crewIdSpecified) {
					query.setLong("aCrewId", whElSerachFilter.getCrewId());
					countQuery.setLong("aCrewId", whElSerachFilter.getCrewId());
				} else if (branchDealerIdSpecified) {
					query.setLong("aBranchDealerId", whElSerachFilter.getBranchDealerId());
					countQuery.setLong("aBranchDealerId", whElSerachFilter.getBranchDealerId());
				} else if (dealerIdSpecified) {
					query.setLong("aDealerId", whElSerachFilter.getDealerId());
					countQuery.setLong("aDealerId", whElSerachFilter.getDealerId());
				}
			}
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	              	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}

        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> warehouseElements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
        	response.setWareHouseElements( warehouseElements );
			
        	return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getWarehouseElementsByFilters/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByFilters/WarehouseElementDAO ==");
		}

	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementsByCriteriaAndGroupByElementId(java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public WareHouseElementResponse getWhElementsByCriteriaAndGroupByElementId( Long warehouseId, String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo ) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWhElementsByCriteriaAndGroupByElementId/WarehouseElementDAO ==");
		Session session = super.getSession();
		boolean serialized = false;
		boolean notSerialized = false;
		try {
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			//Paginacion
        	stringCount.append("SELECT count (*) from (");
        	
        	if( isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0 ){
        		//Cuerpo de la Consulta
            	stringQuery.append("SELECT wae.NOT_SER_ID, max(wae.MOVEMENT_DATE),sum(wae.ACTUAL_QUANTITY) ");
    			stringQuery.append("FROM WAREHOUSE_ELEMENTS wae ");
    			stringQuery.append("WHERE wae.WAREHOUSE_ID = :aWarehouseId ");
    			stringQuery.append("AND wae.ACTUAL_QUANTITY > 0 ");
    			stringQuery.append("AND wae.RECORD_STATUS_ID = :recordStatusId ");
    			stringQuery.append("AND wae.NOT_SER_ID is not null ");
    			notSerialized = true;
        	}
        	if( isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0 ){
        		stringQuery.append("SELECT wae.SER_ID, max(wae.MOVEMENT_DATE),sum(wae.ACTUAL_QUANTITY),wae.ID ");
    			stringQuery.append("FROM WAREHOUSE_ELEMENTS wae ");
    			stringQuery.append("WHERE wae.WAREHOUSE_ID = :aWarehouseId ");
    			stringQuery.append("AND wae.ACTUAL_QUANTITY > 0 ");
    			stringQuery.append("AND wae.RECORD_STATUS_ID = :recordStatusId ");
    			stringQuery.append("AND wae.SER_ID is not null ");
        		serialized = true;
        	}
        	
			if(elementId != null && elementId > 0 && notSerialized){
				stringQuery.append("AND wae.NOT_SER_ID in (SELECT ELEMENT_ID from NOT_SERIALIZED WHERE ELEMENT_ID = :aElementId) ");
			} else if(elementId != null && elementId > 0 && serialized){
				stringQuery.append("AND wae.SER_ID in (SELECT ELEMENT_ID from SERIALIZED WHERE ELEMENT_ID = :aElementId) ");
			}
			if( notSerialized ){
				stringQuery.append("GROUP BY wae.NOT_SER_ID ");
			} else if( serialized ){
				stringQuery.append("GROUP BY wae.ID,wae.SER_ID ");
			}
			
			Long recordStatusId = CodesBusinessEntityEnum.RECORD_STATUS_LAST.getIdEntity(RecordStatus.class.getName());
			//Paginacion
			stringCount.append( stringQuery.toString() );
			stringCount.append( " )" ); 
			SQLQuery countQuery = session.createSQLQuery( stringCount.toString() );
        	
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			query.setLong("aWarehouseId", warehouseId);
			query.setLong("recordStatusId", recordStatusId);
			//Paginacion
			countQuery.setLong("aWarehouseId", warehouseId);
			countQuery.setLong("recordStatusId", recordStatusId);
			
			if(elementId != null && elementId!=0){
				query.setLong("aElementId", elementId);
				countQuery.setLong("aElementId", elementId);
			}	
			
			//Paginacion
        	Long recordQty = 0l;
        	if( requestCollInfo != null ){	   
        		BigDecimal numberOfRegisters = (BigDecimal) countQuery.uniqueResult();
        		recordQty = numberOfRegisters.longValue();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<Object[]> warehouseElement = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElement.size(), recordQty.intValue() );
        	response.setWarehouseElementObjects( warehouseElement );
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getWhElementsByCriteriaAndGroupByElementId/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementsByCriteriaAndGroupByElementId/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWhElementsByCriteriaAndGroupByElementId(java.lang.Long, java.lang.String, java.lang.Long, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public WareHouseElementResponse getWhElementsByCriteriaAndGroupByElementIdAndWh( Long warehouseId, String isSerialized, Long elementId, RequestCollectionInfo requestCollInfo ) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWhElementsByCriteriaAndGroupByElementIdAndWh/WarehouseElementDAO ==");
		Session session = super.getSession();
		boolean serialized = false;
		boolean notSerialized = false;
		try {
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			//Paginacion
        	stringCount.append("SELECT count (*) from (");
        	
        	if( isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0 ){
        		//Cuerpo de la Consulta
            	stringQuery.append("SELECT wae.NOT_SER_ID, max(wae.MOVEMENT_DATE),sum(wae.ACTUAL_QUANTITY),wae.WAREHOUSE_ID,wae.RECORD_STATUS_ID ");
    			stringQuery.append("FROM WAREHOUSE_ELEMENTS wae ");
    			stringQuery.append("WHERE wae.WAREHOUSE_ID = :aWarehouseId ");
    			stringQuery.append("AND wae.ACTUAL_QUANTITY > 0 ");
    			stringQuery.append("AND wae.RECORD_STATUS_ID = :recordStatusId ");
    			stringQuery.append("AND wae.NOT_SER_ID is not null ");
    			notSerialized = true;
        	}
        	if( isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0 ){
        		stringQuery.append("SELECT wae.SER_ID, max(wae.MOVEMENT_DATE),sum(wae.ACTUAL_QUANTITY),wae.WAREHOUSE_ID,wae.RECORD_STATUS_ID,wae.ID ");
    			stringQuery.append("FROM WAREHOUSE_ELEMENTS wae ");
    			stringQuery.append("WHERE wae.WAREHOUSE_ID = :aWarehouseId ");
    			stringQuery.append("AND wae.ACTUAL_QUANTITY > 0 ");
    			stringQuery.append("AND wae.RECORD_STATUS_ID = :recordStatusId ");
    			stringQuery.append("AND wae.SER_ID is not null ");
        		serialized = true;
        	}
        	
			if(elementId != null && elementId > 0 && notSerialized){
				stringQuery.append("AND wae.NOT_SER_ID in (SELECT ELEMENT_ID from NOT_SERIALIZED WHERE ELEMENT_ID = :aElementId) ");
			} else if(elementId != null && elementId > 0 && serialized){
				stringQuery.append("AND wae.SER_ID in (SELECT ELEMENT_ID from SERIALIZED WHERE ELEMENT_ID = :aElementId) ");
			}
			if( notSerialized ){
				stringQuery.append("GROUP BY wae.NOT_SER_ID,wae.WAREHOUSE_ID,wae.RECORD_STATUS_ID ");
			} else if( serialized ){
				stringQuery.append("GROUP BY wae.ID,wae.SER_ID,wae.WAREHOUSE_ID,wae.RECORD_STATUS_ID ");
			}
			
			Long recordStatusId = CodesBusinessEntityEnum.RECORD_STATUS_LAST.getIdEntity(RecordStatus.class.getName());
			//Paginacion
			stringCount.append( stringQuery.toString() );
			stringCount.append( " )" ); 
			SQLQuery countQuery = session.createSQLQuery( stringCount.toString() );
        	
			SQLQuery query = session.createSQLQuery(stringQuery.toString());
			query.setLong("aWarehouseId", warehouseId);
			query.setLong("recordStatusId", recordStatusId);
			//Paginacion
			countQuery.setLong("aWarehouseId", warehouseId);
			countQuery.setLong("recordStatusId", recordStatusId);
			
			if(elementId != null && elementId!=0){
				query.setLong("aElementId", elementId);
				countQuery.setLong("aElementId", elementId);
			}	
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	   
        		BigDecimal numberOfRegisters = (BigDecimal) countQuery.uniqueResult();
        		recordQty = numberOfRegisters.longValue();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<Object[]> warehouseElement = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElement.size(), recordQty.intValue() );
        	response.setWarehouseElementObjects( warehouseElement );
			
			return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getWhElementsByCriteriaAndGroupByElementIdAndWh/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementsByCriteriaAndGroupByElementIdAndWh/WarehouseElementDAO ==");
		}
	}

	/**
	 * Determina si un tipo de elemento es serializado
	 * 
	 * @param session
	 * @param elementTypeId
	 * @return
	 * @throws DAOServiceException
	 * @throws PropertiesException
	 */
	private boolean elementTypeIsSerialized(Session session, Long elementTypeId) throws DAOServiceException, PropertiesException {
		StringBuffer strBuffer = new StringBuffer("from ");
		strBuffer.append(ElementType.class.getName());
		strBuffer.append(" et where et.id = :anEtId");
		
		Query query = session.createQuery(strBuffer.toString());
		query.setLong("anEtId", elementTypeId);
		
		ElementType et = (ElementType)query.uniqueResult();
		if(et == null){
			throw new DAOServiceException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
		}
		
		boolean isSerialized = false;
		
		if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(et.getIsSerialized())){
			isSerialized = true;
		}
		return isSerialized;
	}

	/**
	 * Determina si un modelo de elemento es serializado
	 * 
	 * @param session
	 * @param elementModelId
	 * @return
	 * @throws DAOServiceException
	 * @throws PropertiesException
	 */
	@SuppressWarnings("unchecked")
	private boolean elementModelIsSerialized(Session session, Long elementModelId) throws DAOServiceException, PropertiesException {
		StringBuffer strBuffer = new StringBuffer("from ");
		strBuffer.append(ElementType.class.getName());
		strBuffer.append(" et where et.elementModel.id = anEmId");
		
		Query query = session.createQuery(strBuffer.toString());
		query.setLong("anEmId", elementModelId);
		List<ElementType> ets = query.list();
		
		if(ets.isEmpty()){
			throw new DAOServiceException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage() + " No se encontró tipo de elemento relacionado con el id de modelo especificado " + elementModelId);
		}
		ElementType et = (ElementType)ets.get(0);
		
		boolean isSerialized = false;
		if(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity().equalsIgnoreCase(et.getIsSerialized())){
			isSerialized = true;
		}
		return isSerialized;
	}

	/**
	 * Metodo: Construye la consulta para calcular la cantidad actual de elementos
	 * @return StringBuffer con el encabezado del query
	 * @author jjimenezh
	 */
	private StringBuffer getQueryHeaderToActualQty(boolean isSerialized) {
		StringBuffer stringQuery = new StringBuffer(); 
		stringQuery.append("select sum(whe.actual_quantity) as equantity, et.id as eelement_type_id, wh.id as wwarehouse_id from ");
		stringQuery.append("warehouse_elements whe, ");
		stringQuery.append("element_types et, ");
		if(isSerialized){
			stringQuery.append("serialized s, ");
		}else{
			stringQuery.append("not_serialized ns, ");
		}
		stringQuery.append("elements e, ");
		stringQuery.append("warehouses wh where "); 
		if(isSerialized){
			stringQuery.append("whe.not_ser_id is null and whe.ser_id = s.element_id and e.id = s.element_id and e.element_type_id = et.id and ");
		}else{
			stringQuery.append("whe.ser_id is null and whe.not_ser_id = ns.element_id and e.id = ns.element_id and e.element_type_id = et.id and ");
		}
		stringQuery.append("whe.record_status_id in (select rs.id from record_status rs where rs.record_status_code = 'U') and ");
		stringQuery.append("( ( whe.mov_type_id in (select mt.id from MOVEMENT_TYPES mt where mt.mov_class = 'E') and ");
		stringQuery.append("whe.WAREHOUSE_ID = wh.id ) or ");
		stringQuery.append("( whe.mov_type_id in (select mt.id from MOVEMENT_TYPES mt where mt.mov_class = 'S') and ");
		stringQuery.append("whe.WAREHOUSE_ID = wh.id ) ) ");
		return stringQuery;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
	public Double getCurrentQuantityInWarehouseByElementType(Long warehouseId,
			Long elementTypeId, Long elementModelId)
	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCurrentQuantityInWarehouseByElementType/WarehouseElementDAO ==");
		StringBuffer stringQuery; 
		try {
			Session session = super.getSession();
			boolean isSerialized = this.elementTypeIsSerialized(session, elementTypeId);
			stringQuery = this.getQueryHeaderToActualQty(isSerialized);
			stringQuery.append("and e.element_type_id = ?  and wh.id = ? ");
			if( elementModelId!=null )
			   stringQuery.append( "and et.element_model_id = ? " );
			stringQuery.append("group by et.id, wh.id");
			SQLQuery sqlQuery = session.createSQLQuery(stringQuery.toString());

			if(log.isDebugEnabled()){
				log.debug("Se ejecutará la consulta para cantidades en bodega con la siguiente consulta: " + stringQuery.toString());
				log.debug("Los parámetros son los siguientes: [warehouseId = "+warehouseId+"],[elementTypeId = "+elementTypeId+"],[elementModelId = "+elementModelId+"]");
			}
			
			sqlQuery.setLong(0, elementTypeId);
			sqlQuery.setLong(1, warehouseId);
			
			if( elementModelId!=null )
			 sqlQuery.setLong(2, elementModelId);

			Object[] result = (Object[]) sqlQuery.uniqueResult();
			Double actualQty = 0D; 
			if(result != null){
				actualQty = ((BigDecimal) result[0]).doubleValue();
			}
			return actualQty;

		} catch(Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCurrentQuantityInWarehouseByElementType/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElementQuantity> getCurrentElementQuantityInWHByFilters(Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId, 
			Long elementTypeId, Long elementModelId, Date initialDate, Date finalDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCurrentElementQuantityInWHByFilters/WarehouseElementDAO ==");
		try {
			
			Session session = super.getSession();
			
			boolean serializedAndNotSerialized = false, isSerialized = false;
			if((elementTypeId == null || elementTypeId.longValue() <= 0) && (elementModelId == null || elementModelId.longValue() <= 0)){
				serializedAndNotSerialized = true;
			}else if(elementTypeId != null && elementTypeId.longValue() > 0){
				isSerialized = elementTypeIsSerialized(session, elementTypeId);
			}else if(elementModelId != null && elementModelId.longValue() > 0){
				isSerialized = elementModelIsSerialized(session, elementModelId);
			}
			List<WarehouseElementQuantity> result;
			
			if(serializedAndNotSerialized){
				result = getCurrentElementQuantityInWHByFiltersAndIsSerialized(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate, true);
				List<WarehouseElementQuantity> result2 = getCurrentElementQuantityInWHByFiltersAndIsSerialized(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate, false);
				result.addAll(result2);
			}else{
				result = getCurrentElementQuantityInWHByFiltersAndIsSerialized(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, initialDate, finalDate, isSerialized);
			}

			return result;
		} catch(Throwable ex) {
			log.error("== Error getCurrentElementQuantityInWHByFilters/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCurrentElementQuantityInWHByFilters/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getQuantityWarehouseElementsSummariesByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS) CC053
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsSummariesByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getQuantityWarehouseElementsSummariesByFilters/WarehouseElementDAO ==");
		Session session = super.getSession();

		//booleanos para almacenar los datos o parametros enviados en el filtro 
		boolean dealerIdCompanySpecified = false,
		dealerIdBranchSpecified = false,
		crewIdSpecified = false,
		whTypeIdSpecified = false,
		whIdSpecified = false,
		elementModelIdSpecified = false,
		typeElementIdSpecified = false,
		movementDateInSpecified = false,
		movementDateOutSpecified = false;

		try {
			
			//Atributos locales para almacenar los filtros recibidos
			//Compañía Principal
			Long dealerIdCompany=quantityWarehouseElementsDTO.getDealerIdCompany();
			
			//Sucursal Principal
			Long dealerIdBranch=quantityWarehouseElementsDTO.getDealerIdBranch();
			
			//Cuadrilla
			Long crewId=quantityWarehouseElementsDTO.getCrewId();
			
			//Tipo de Bodega
			Long whTypeId=quantityWarehouseElementsDTO.getWhTypeId();
			
			//Ubicación (de Bodega)
			Long whId=quantityWarehouseElementsDTO.getWhId();
			
			//Modelo
			Long elementModelId=quantityWarehouseElementsDTO.getElementModelId();
			
			//Tipo de elemento
			Long typeElementId=quantityWarehouseElementsDTO.getTypeElementId();
			
			//Fecha Inicio
			Date movementDateIn=quantityWarehouseElementsDTO.getMovementDateIn();
			
			//Fecha Final
			Date movementDateOut=quantityWarehouseElementsDTO.getMovementDateOut();
			
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringFrom = new StringBuffer();

			//Consulta que retorna la cantidad de elementos
			StringBuffer stringQuery = new StringBuffer(" Select  new "+QuantityWarehouseElementsDTO.class.getName()+" ( ");
			
			stringQuery.append(" case when entity.warehouseId.dealerId.dealer.id=null then entity.warehouseId.dealerId.id else entity.warehouseId.dealerId.dealer.id end AS dealerIdCompany, ");
			stringQuery.append(" (Select COALESCE(d.depotCode,'')||' '||COALESCE(d.dealerName,'') AS NODO1  					From " + Dealer.class.getName() + " d where d.id=decode(entity.warehouseId.dealerId.dealer.id,null,entity.warehouseId.dealerId.id,entity.warehouseId.dealerId.dealer.id)) AS dealerNameCompany, ");
			stringQuery.append(" (Select COALESCE(d.depotCode,'')||' '||COALESCE(d.dealerName,'') AS NODO1  					From " + Dealer.class.getName() + " d where d.id=decode(entity.warehouseId.dealerId.dealer.id,null,null,entity.warehouseId.dealerId.id)) AS dealerNameBranch, ");
			stringQuery.append(" case when entity.warehouseId.dealerId.dealer.id=null then entity.warehouseId.dealerId.dealer.id else entity.warehouseId.dealerId.id end AS dealerIdBranch ");
			stringQuery.append(" ,entity.warehouseId.crewId.id AS crewId "); 
			stringQuery.append(" ,(SELECT CONCAT(employeeCrew.employee.firstName,' ',employeeCrew.employee.lastName) "); 
			stringQuery.append(" 	 FROM " + EmployeeCrew.class.getName() + " employeeCrew "); 
			stringQuery.append("    where employeeCrew.isResponsible = :anIsResponsible ");
			stringQuery.append(" 	  and employeeCrew.crew.id = entity.warehouseId.crewId.id) AS isResponsibleOut ");
			stringQuery.append(" ,entity.warehouseId.id AS whId ");  
			stringQuery.append(" ,entity.warehouseId.warehouseType.id AS whTypeId ");  
			stringQuery.append(" ,entity.warehouseId.warehouseType.whTypeCode AS whTypeCode ");  
			stringQuery.append(" ,entity.warehouseId.warehouseType.whTypeName AS whTypeName ");  
			stringQuery.append(" ,entity.warehouseId.whCode AS whCode ");  
			stringQuery.append(" ,(select s.elementModel.id ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS elementModelId ");
			stringQuery.append(" ,(select s.elementModel.modelName ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS modelName ");
			stringQuery.append(" ,(select s.elementModel.modelCode ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS modelCode ");
			stringQuery.append(" ,(select s.id ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementId ");
			stringQuery.append(" ,(select s.typeElementCode ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementCode ");
			stringQuery.append(" ,(select s.typeElementName ");
			stringQuery.append(" 	from "+ ElementType.class.getName() +" s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementName ");
			stringQuery.append(" ,(select sum(CASE WHEN entity1.movementType.movClass = :aMovTypeEntry THEN "); 
			stringQuery.append(" 		           entity1.movedQuantity ");
			stringQuery.append(" 	  		  ELSE  ");
			stringQuery.append(" 				   (entity1.movedQuantity*-1) ");
			stringQuery.append("      	      END)  ");
			stringQuery.append("     from WarehouseElement entity1 ");
			stringQuery.append("          where entity.warehouseId.id = entity1.warehouseId.id ");
			stringQuery.append(" 				and entity.elementType.id = entity1.elementType.id ");
		    //CC053
			if (movementDateIn!=null){
		    	stringQuery.append(" 			    and trunc(entity1.movementDate) < trunc(:aMovementDateIn) ");
		    }
			stringQuery.append(" 			    )  AS initialQuantity ");
			stringQuery.append(" ,SUM(CASE WHEN entity.movementType.movClass = :aMovTypeEntry THEN "); 
			stringQuery.append(" 		entity.movedQuantity ");
			stringQuery.append(" 	  ELSE  ");
			stringQuery.append(" 		0 ");
			stringQuery.append("      END)  AS inQuantity ");
			stringQuery.append(" ,SUM(CASE WHEN entity.movementType.movClass = :aMovTypeExit THEN "); 
			stringQuery.append(" 		entity.movedQuantity ");
			stringQuery.append("      ELSE  ");
			stringQuery.append(" 		0 ");
			stringQuery.append(" 	  END)  AS outQuantity ");
			stringQuery.append(" ,min(entity.id) AS minWarehouseElementId ");
			stringQuery.append(" ,max(entity.id) AS maxWarehouseElementId  ");
			stringQuery.append(" ,min(entity.movementDate) AS movementDateIn ");
			stringQuery.append(" ,max(entity.movementDate) AS movementDateOut)  ");
			
			String sqlWhereOrAnd = " where ";
			
			stringFrom.append(" from ");
			stringFrom.append(WarehouseElement.class.getName() + " entity ");
            
			//Condiciones de filtro
			if ( dealerIdCompany!= null) {
				dealerIdCompanySpecified = true;
				stringFrom.append(sqlWhereOrAnd + " (entity.warehouseId.dealerId.dealer.id = :aDealerIdCompany or entity.warehouseId.dealerId.id = :aDealerIdCompany)  ");
				sqlWhereOrAnd = " and ";
			} else if ( dealerIdBranch!= null) {
				dealerIdBranchSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " (entity.warehouseId.dealerId.id = :aDealerIdBranch)  ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( crewId!= null) {
				crewIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.crewId.id = :aCrewId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( whTypeId!= null) {
				whTypeIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.warehouseType.id = :aWhTypeId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( whId!= null) {
				whIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.id = :aWhId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( elementModelId!= null) {
				elementModelIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.elementType.elementModel.id = :aElementModelId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( typeElementId!= null) {
				typeElementIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.elementType.id = :aTypeElementId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( movementDateIn!= null) {
				movementDateInSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " trunc(entity.movementDate) >= trunc(:aMovementDateIn) ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( movementDateOut!= null) {
				movementDateOutSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " trunc(entity.movementDate) <= trunc(:aMovementDateOut) ");
				sqlWhereOrAnd = " and ";
			}
			
			//Agrupacion para la sumatoria de las entradas y salidas
			stringFrom.append(" GROUP BY entity.warehouseId.dealerId.dealer.id "); 
			stringFrom.append(" ,entity.warehouseId.dealerId.id "); 
			stringFrom.append(" ,COALESCE(entity.warehouseId.crewId.id,'') ");  
			stringFrom.append(" ,entity.warehouseId.crewId.id ");
			stringFrom.append(" ,entity.warehouseId.id   ");
			stringFrom.append(" ,entity.warehouseId.warehouseType.id ");  
			stringFrom.append(" ,entity.warehouseId.warehouseType.whTypeCode ");  
			stringFrom.append(" ,entity.warehouseId.warehouseType.whTypeName   ");
			stringFrom.append(" ,entity.warehouseId.whCode   ");
			stringFrom.append(" ,entity.elementType.id   ");
			
			//Paginacion
			stringCount.append(" Select count(*) ");
			stringCount.append( stringFrom.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
        	stringQuery.append( stringFrom.toString() );        	
        	Query query = session.createQuery(stringQuery.toString());
			
			query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			query.setString("aMovTypeEntry", CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_ENTRY.getCodeEntity());
			query.setString("aMovTypeExit", CodesBusinessEntityEnum.MOVEMENT_TYPE_TYPE_EXIT.getCodeEntity());
			
			//Asignacion de filtros a la consulta
			if (dealerIdCompanySpecified) {
				query.setLong("aDealerIdCompany",dealerIdCompany);
				countQuery.setLong("aDealerIdCompany",dealerIdCompany);
			}
			
			if (dealerIdBranchSpecified) {
				query.setLong("aDealerIdBranch",dealerIdBranch);
				countQuery.setLong("aDealerIdBranch",dealerIdBranch);
			}
			
			if (crewIdSpecified) {
				query.setLong("aCrewId",crewId);
				countQuery.setLong("aCrewId",crewId);
			}

			if (whTypeIdSpecified) {
				query.setLong("aWhTypeId",whTypeId);
				countQuery.setLong("aWhTypeId",whTypeId);
			}
			
			if (whIdSpecified) {
				query.setLong("aWhId",whId);
				countQuery.setLong("aWhId",whId);
			}
			
			if (elementModelIdSpecified) {
				query.setLong("aElementModelId",elementModelId);
				countQuery.setLong("aElementModelId",elementModelId);
			}
			
			if (typeElementIdSpecified) {
				query.setLong("aTypeElementId",typeElementId);
				countQuery.setLong("aTypeElementId",typeElementId);
			}
			
			if (movementDateInSpecified) {
				query.setDate("aMovementDateIn",UtilsBusiness.obtenerPrimeraHoraDia(movementDateIn));
				countQuery.setDate("aMovementDateIn",UtilsBusiness.obtenerPrimeraHoraDia(movementDateIn));
			}
			
			if (movementDateOutSpecified) {
				query.setDate("aMovementDateOut",UtilsBusiness.obtenerUltimaHoraDia(movementDateOut));
				countQuery.setDate("aMovementDateOut",UtilsBusiness.obtenerUltimaHoraDia(movementDateOut));
			}
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
	        	recordQty = new Long(countQuery.list().size());
	        	
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
       	}
        	
        	QuantityWarehouseElementResponse quantityWarehouseElementResponse = new QuantityWarehouseElementResponse(query.list(), 0);
        	
        	if( requestCollInfo != null )
				populatePaginationInfo( quantityWarehouseElementResponse, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), quantityWarehouseElementResponse.getQuantityWarehouseElementsDTO().size(), recordQty.intValue() );
        	
			return quantityWarehouseElementResponse;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getQuantityWarehouseElementsSummariesByFilters/WarehouseElementDAO ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public QuantityWarehouseElementResponse getQuantityWarehouseElementsDetailsByFilters(QuantityWarehouseElementsDTO quantityWarehouseElementsDTO,RequestCollectionInfoDTO requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getQuantityWarehouseElementsSummariesByFilters/WarehouseElementDAO ==");
		Session session = super.getSession();

		//booleanos para almacenar los datos o parametros enviados en el filtro
		boolean dealerIdCompanySpecified = false,
		dealerIdBranchSpecified = false,
		crewIdSpecified = false,
		whTypeIdSpecified = false,
		whIdSpecified = false,
		elementModelIdSpecified = false,
		typeElementIdSpecified = false,
		movementDateInSpecified = false,
		movementDateOutSpecified = false,
		movClassSpecified = false,
		movTypeIdSpecified = false,
		documentIdSpecified = false,
		documentClassIdSpecified = false;

		try {
			
			//Atributos locales para almacenar los filtros recibidos
			//Compañía Principal
			Long dealerIdCompany=quantityWarehouseElementsDTO.getDealerIdCompany();
			
			//Sucursal Principal
			Long dealerIdBranch=quantityWarehouseElementsDTO.getDealerIdBranch();
			
			//Cuadrilla
			Long crewId=quantityWarehouseElementsDTO.getCrewId();
			
			//Tipo de Bodega
			Long whTypeId=quantityWarehouseElementsDTO.getWhTypeId();
			
			//Ubicación (de Bodega)
			Long whId=quantityWarehouseElementsDTO.getWhId();
			
			//Modelo
			Long elementModelId=quantityWarehouseElementsDTO.getElementModelId();
			
			//Tipo de elemento
			Long typeElementId=quantityWarehouseElementsDTO.getTypeElementId();
			
			//Fecha Inicio
			Date movementDateIn=quantityWarehouseElementsDTO.getMovementDateIn();
			
			//Fecha Final
			Date movementDateOut=quantityWarehouseElementsDTO.getMovementDateOut();

			//mov class
			String movClass=quantityWarehouseElementsDTO.getMovClass();
			
			//mov class
			Long movTypeId=quantityWarehouseElementsDTO.getMovTypeId();
			
			//documentId
			Long documentId=quantityWarehouseElementsDTO.getDocumentId();
			
			//documentId
			Long documentClassId=quantityWarehouseElementsDTO.getDocumentClassId();
			
			//Consulta que retorna el detalle de los elementos
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringFrom = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer(" Select  new "+QuantityWarehouseElementsDTO.class.getName()+" ( ");
			StringBuffer stringOrder = new StringBuffer(" order by entity.id desc ");
			
			stringQuery.append(" case when entity.warehouseId.dealerId.dealer.id=null then entity.warehouseId.dealerId.id else entity.warehouseId.dealerId.dealer.id end AS dealerIdCompany, ");
			stringQuery.append(" (Select COALESCE(d.depotCode,'')||' '||COALESCE(d.dealerName,'') AS NODO1  					From " + Dealer.class.getName() + " d where d.id=decode(entity.warehouseId.dealerId.dealer.id,null,entity.warehouseId.dealerId.id,entity.warehouseId.dealerId.dealer.id)) AS dealerNameCompany, ");
			stringQuery.append(" (Select COALESCE(d.depotCode,'')||' '||COALESCE(d.dealerName,'') AS NODO1  					From " + Dealer.class.getName() + " d where d.id=decode(entity.warehouseId.dealerId.dealer.id,null,null,entity.warehouseId.dealerId.id)) AS dealerNameBranch, ");
			stringQuery.append(" case when entity.warehouseId.dealerId.dealer.id=null then entity.warehouseId.dealerId.dealer.id else entity.warehouseId.dealerId.id end AS dealerIdBranch, ");
			stringQuery.append(" entity.warehouseId.crewId.id AS crewId  , ");
			stringQuery.append(" (SELECT CONCAT(employeeCrew.employee.firstName,' ',employeeCrew.employee.lastName)  	 FROM " + EmployeeCrew.class.getName() + " employeeCrew     where employeeCrew.isResponsible = :anIsResponsible  	  and employeeCrew.crew.id = entity.warehouseId.crewId.id) AS isResponsibleOut  , ");
			stringQuery.append(" entity.warehouseId.id AS whId  , ");
			stringQuery.append(" entity.warehouseId.warehouseType.id AS whTypeId  , ");
			stringQuery.append(" entity.warehouseId.warehouseType.whTypeCode AS whTypeCode  , ");
			stringQuery.append(" entity.warehouseId.warehouseType.whTypeName AS whTypeName  , ");
			stringQuery.append(" entity.warehouseId.whCode AS whCode  , ");
			stringQuery.append(" (select s.elementModel.id ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS elementModelId, ");
			stringQuery.append(" (select s.elementModel.modelName ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS modelName, ");
			stringQuery.append(" (select s.elementModel.modelCode ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS modelCode, ");
			stringQuery.append(" (select s.id ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementId, ");
			stringQuery.append(" (select s.typeElementCode ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementCode, ");
			stringQuery.append(" (select s.typeElementName ");
			stringQuery.append(" 	from " + ElementType.class.getName() + " s "); 
			stringQuery.append(" 	where s.id=entity.elementType.id ) AS typeElementName, ");
			stringQuery.append(" case when entity.movementType.movClass='E' then (entity.movedQuantity) else (-1*entity.movedQuantity) end  AS actualQuantity, ");//actualQuantity
			stringQuery.append(" (select s.serialized.element.id ");
			stringQuery.append("    from " + Serialized.class.getName() + " s ");
			stringQuery.append("   where s.id=entity.serialized.id) AS elementId, ");
			stringQuery.append(" (select serialCode "); 
			stringQuery.append("    from " + Serialized.class.getName() + " s ");
			stringQuery.append("   where s.id=entity.serialized.id) AS serialCode, "); 
			stringQuery.append(" (select ird "); 
			stringQuery.append("    from " + Serialized.class.getName() + " s ");
			stringQuery.append("   where s.id=entity.serialized.id) AS rid, "); 
			stringQuery.append(" (select s.serialCode ");
			stringQuery.append("    from " + Serialized.class.getName() + " s ");
			stringQuery.append("   where s.id=entity.linkedSerId.id) AS serialCodeLinked, ");
//			stringQuery.append(" entity.linkedSerId.serialCode AS serialCodeLinked, ");
			stringQuery.append(" entity.movementType.id AS movTypeId, "); 
			stringQuery.append(" case when entity.movementType.movClass='E' then 'Entrada' else 'Salida' end AS movClass, "); 
			stringQuery.append(" entity.movementType.movTypeName AS movTypeName, ");
			stringQuery.append(" entity.movementDate AS movementDateIn, "); 
			stringQuery.append(" entity.movementDate AS movementDateOut, ");
			stringQuery.append(" entity.id AS minWarehouseElementId, ");
			stringQuery.append(" entity.id AS maxWarehouseElementId, ");
			stringQuery.append(" entity.documentId AS documentId, ");
			stringQuery.append(" entity.documentClass.id AS documentClassId, ");
			stringQuery.append(" (select d.documentClassName  || ' - ' || entity.documentId "); 
			stringQuery.append("    from " + DocumentClass.class.getName() + " d ");
			stringQuery.append("   where d.id=entity.documentClass.id) AS documentClassName, ");
			stringQuery.append(" (select d.documentClassName "); 
			stringQuery.append("    from " + DocumentClass.class.getName() + " d ");
			stringQuery.append("   where d.id=entity.documentClass.id) AS documentName) ");
			String sqlWhereOrAnd = " where ";
			
			stringFrom.append(" from ");
			stringFrom.append(WarehouseElement.class.getName() + " entity ");
            
			if ( dealerIdCompany!= null) {
				dealerIdCompanySpecified = true;
				stringFrom.append(sqlWhereOrAnd + " (entity.warehouseId.dealerId.dealer.id = :aDealerIdCompany or entity.warehouseId.dealerId.id = :aDealerIdCompany)  ");
				sqlWhereOrAnd = " and ";
			}else if ( dealerIdBranch!= null) {
				dealerIdBranchSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " (entity.warehouseId.dealerId.id = :aDealerIdBranch)  ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( crewId!= null) {
				crewIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.crewId.id = :aCrewId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( whTypeId!= null) {
				whTypeIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.warehouseType.id = :aWhTypeId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( whId!= null) {
				whIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.warehouseId.id = :aWhId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( elementModelId!= null) {
				elementModelIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.elementType.elementModel.id = :aElementModelId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( typeElementId!= null) {
				typeElementIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.elementType.id = :aTypeElementId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( movementDateIn!= null) {
				movementDateInSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " trunc(entity.movementDate) >= trunc(:aMovementDateIn) ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( movementDateOut!= null) {
				movementDateOutSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " trunc(entity.movementDate) <= trunc(:aMovementDateOut) ");
				sqlWhereOrAnd = " and ";
			}

			if ( movClass!= null && movClass.trim().length() > 0) {
				movClassSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.movementType.movClass = :aMovClass ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( movTypeId!= null) {
				movTypeIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.movementType.id = :aMovTypeId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( documentId!= null) {
				documentIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.documentId = :aDocumentId ");
				sqlWhereOrAnd = " and ";
			}
			
			if ( documentClassId!= null) {
				documentClassIdSpecified = true;
				stringFrom.append(sqlWhereOrAnd + " entity.documentClass.id = :aDocumentClassId ");
				sqlWhereOrAnd = " and ";
			}
					
			//Paginacion
			stringCount.append(" Select count(*) ");
			stringCount.append( stringFrom.toString() );        	
        	Query countQuery = session.createQuery( stringCount.toString() );
        	
        	stringQuery.append( stringFrom.toString() + stringOrder.toString());        	
        	Query query = session.createQuery(stringQuery.toString());
			
			query.setString("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
			
			//Asignacion de filtros a la consulta
			if (dealerIdCompanySpecified) {
				query.setLong("aDealerIdCompany",dealerIdCompany);
				countQuery.setLong("aDealerIdCompany",dealerIdCompany);
			}
			
			if (dealerIdBranchSpecified) {
				query.setLong("aDealerIdBranch",dealerIdBranch);
				countQuery.setLong("aDealerIdBranch",dealerIdBranch);
			}
			
			if (crewIdSpecified) {
				query.setLong("aCrewId",crewId);
				countQuery.setLong("aCrewId",crewId);
			}

			if (whTypeIdSpecified) {
				query.setLong("aWhTypeId",whTypeId);
				countQuery.setLong("aWhTypeId",whTypeId);
			}
			
			if (whIdSpecified) {
				query.setLong("aWhId",whId);
				countQuery.setLong("aWhId",whId);
			}
			
			if (elementModelIdSpecified) {
				query.setLong("aElementModelId",elementModelId);
				countQuery.setLong("aElementModelId",elementModelId);
			}
			
			if (typeElementIdSpecified) {
				query.setLong("aTypeElementId",typeElementId);
				countQuery.setLong("aTypeElementId",typeElementId);
			}
			
			if (movementDateInSpecified) {
				query.setDate("aMovementDateIn",UtilsBusiness.obtenerPrimeraHoraDia(movementDateIn));
				countQuery.setDate("aMovementDateIn",UtilsBusiness.obtenerPrimeraHoraDia(movementDateIn));
			}
			
			if (movementDateOutSpecified) {
				query.setDate("aMovementDateOut",UtilsBusiness.obtenerUltimaHoraDia(movementDateOut));
				countQuery.setDate("aMovementDateOut",UtilsBusiness.obtenerUltimaHoraDia(movementDateOut));
			}
			
			if (movClassSpecified) {
				query.setString("aMovClass",movClass);
				countQuery.setString("aMovClass",movClass);
			}
			
			if (movTypeIdSpecified) {
				query.setLong("aMovTypeId",movTypeId);
				countQuery.setLong("aMovTypeId",movTypeId);
			}
			
			if (documentIdSpecified) {
				query.setLong("aDocumentId",documentId);
				countQuery.setLong("aDocumentId",documentId);
			}
			
			if (documentClassIdSpecified) {
				query.setLong("aDocumentClassId",documentClassId);
				countQuery.setLong("aDocumentClassId",documentClassId);
			}
			
			//Paginacion
        	Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	QuantityWarehouseElementResponse quantityWarehouseElementResponse = new QuantityWarehouseElementResponse(query.list(),0);
        	
        	if( requestCollInfo != null )
				populatePaginationInfo( quantityWarehouseElementResponse, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), quantityWarehouseElementResponse.getQuantityWarehouseElementsDTO().size(), recordQty.intValue() );
        	
			return quantityWarehouseElementResponse;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getQuantityWarehouseElementsSummariesByFilters/WarehouseElementDAO ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	//CC053
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	//public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElSerachFilter,RequestCollectionInfo requestCollInfo)	throws DAOServiceException, DAOSQLException {
	/*
	 * Reporte generado por work: Reporte Saldo detallado - WAREHOUSE_ELEMENTS_IN_DETAILS - WED - automatico
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public QuantityWarehouseElementResponse getWarehouseElementsByWarehouse(WhElementSearchFilter whElSerachFilter,RequestCollectionInfo requestCollInfo, boolean filterDealer)	throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementsByWarehouse/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {		
			//Consulta que retorna el detalle de los elementos
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringFrom = new StringBuffer();
			//StringBuffer stringQuery = new StringBuffer(" Select  new "+QuantityWarehouseElementsDTO.class.getName()+" ( ");
			StringBuffer stringQuery = new StringBuffer("");
			StringBuffer stringOrder = new StringBuffer(" order by we.id ");
			
			Calendar sysdate = Calendar.getInstance();
			sysdate.setTime(UtilsBusiness.getCurrentTimeZoneDateByUserId( whElSerachFilter.getUserId(), userDao));
				
			stringQuery.append(" SELECT w.id whId, ");
			stringQuery.append(" wt.wh_type_code whTypeCode, ");
			stringQuery.append(" w.wh_code warehouseCode, ");
			stringQuery.append(" decode(we.SER_ID,null,emns.model_code,ems.model_code) modelCode, ");
			stringQuery.append(" decode(d.dealer_branch_id,null,d.id,d.dealer_branch_id) dealerId, ");
			stringQuery.append(" decode(d.dealer_branch_id,null,d.DEPOT_CODE,DS.DEPOT_CODE) ||  ' ' || decode(d.dealer_branch_id,null,d.DEALER_NAME,DS.DEALER_NAME)  company, ");
			stringQuery.append(" decode(d.dealer_branch_id,null,NULL,d.id) branchId, ");
			stringQuery.append(" decode(d.dealer_branch_id,null,NULL,d.DEPOT_CODE || ' ' || d.DEALER_NAME) branch, ");
			stringQuery.append(" w.wh_type_id whTypeId, ");
			stringQuery.append(" wt.wh_type_name whTypeName, ");
			stringQuery.append(" w.CREW_ID AS crewId, ");
			stringQuery.append(" decode(w.CREW_ID,null,'',e.first_name || ' ' || e.last_name) crewName, ");
			stringQuery.append(" (CASE WHEN w.CREW_ID IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || e.first_name || ' ' || e.LAST_NAME || ' - ' || wt.wh_type_name WHEN w.dealer_id IS NOT NULL THEN D.DEPOT_CODE || ' - ' || D.DEALER_NAME || ' - ' || wt.wh_type_name WHEN w.customer_id IS NOT NULL THEN CUS.CUSTOMER_CODE || ' - ' || CUS.FIRST_NAME || ' - ' || CUS.LAST_NAME || ' - ' || wt.wh_type_name END) AS ubicacion, ");
			stringQuery.append(" decode(we.SER_ID,NULL,emns.model_name,ems.model_name) modelName, ");
			stringQuery.append(" decode(we.SER_ID,NULL,EMNS.ID,EMS.ID) modelId, ");
			stringQuery.append(" decode(we.SER_ID,NULL,ETNS.type_element_code,ETS.type_element_code) typeElementCode, ");
			stringQuery.append(" decode(we.SER_ID,NULL,ETNS.TYPE_ELEMENT_NAME,ETS.TYPE_ELEMENT_NAME) elementTypeName, ");
			stringQuery.append(" decode(we.SER_ID,NULL,ETNS.ID,ETS.ID) elementTypeId, ");
			stringQuery.append(" decode(we.SER_ID,NULL,NULL,s.serial_code) serialCode, ");
			stringQuery.append(" decode(we.SER_ID,NULL,NULL,s.ird) rid, ");
			stringQuery.append(" decode(we.SER_ID,NULL,NULL,sl.serial_code) serialCodeLinked, ");
			stringQuery.append(" we.actual_quantity AS actualQuantity, ");
			stringQuery.append(" decode(we.SER_ID,NULL,NULL,TO_CHAR (TRUNC (:aSysdate) - TRUNC (we.MOVEMENT_DATE))) age ");
			stringQuery.append(" FROM WAREHOUSE_ELEMENTS we inner join WAREHOUSES w on(w.id = we.warehouse_id) inner join RECORD_STATUS rs on(we.RECORD_STATUS_ID = rs.ID) ");
			stringQuery.append(" inner join Dealers d on(w.dealer_id = d.id) left join Dealers ds on(D.DEALER_BRANCH_ID = ds.id) inner join warehouse_types wt  on(wt.id = w.wh_type_id) ");
			stringQuery.append(" left join SERIALIZED s  on(WE.SER_ID = S.ELEMENT_ID) left join SERIALIZED sl  on(S.LINKED_SERIAL_CODE = SL.ELEMENT_ID) left join ELEMENTS es  on(ES.ID = S.ELEMENT_ID) ");
			stringQuery.append(" left join ELEMENT_TYPES ets  on(ets.ID = ES.ELEMENT_TYPE_ID) left join ELEMENT_MODELS ems  on(ems.ID = ETS.ELEMENT_MODEL_ID) left join NOT_SERIALIZED ns  on(NS.ELEMENT_ID = WE.NOT_SER_ID) ");
			stringQuery.append(" left join ELEMENTS ens  on(ENS.ID = NS.ELEMENT_ID) left join ELEMENT_TYPES etns  on(etns.ID = EnS.ELEMENT_TYPE_ID) left join ELEMENT_MODELS emns  on(emns.ID = ETnS.ELEMENT_MODEL_ID) ");
			stringQuery.append(" left join EMPLOYEE_CREWS ec ON(ec.crew_id = w.crew_id AND ec.is_responsible = :anIsResponsible) left join EMPLOYEES e ON(e.id=ec.employee_id) ");
			stringQuery.append(" left join CUSTOMERS cus ON(cus.id = w.customer_id) ");
			stringQuery.append(" WHERE rs.RECORD_STATUS_CODE = :recordStatus ");
			
			
			if (filterDealer){
				
				if (whElSerachFilter.getDealerId()!=null){
					stringQuery.append(" and ((decode(d.dealer_branch_id,null,d.id,d.dealer_branch_id) =:aDealerId)) ");
				}
				
				if (whElSerachFilter.getBranchDealerId()!=null){
					stringQuery.append(" and ((d.id=:aDealerIdBranch)) ");
				}
				
			}
			
			if (whElSerachFilter.getCrewId()!=null){
				stringQuery.append(" and (w.crew_id = :aCrewId ) ");
			}
			
			if (whElSerachFilter.getWarehouseTypeId()!=null ){
				stringQuery.append(" and (w.WH_TYPE_ID = :aWhTypeId ) ");
			}
			
			if (whElSerachFilter.getWareHouseId()!=null){
				stringQuery.append(" and (w.id = :aWhId ) ");
			}
			
			if (whElSerachFilter.getElementModelId()!=null) {
				stringQuery.append(" and (decode(we.SER_ID,NULL,EMNS.ID,EMS.ID) = :aElementModelId) ");
			}
			
			
			if (whElSerachFilter.getElementTypeId()!=null) {
				stringQuery.append(" and ((decode(we.SER_ID,NULL,ETNS.ID,ETS.ID) = :aTypeElementId)) ");
        	}
			
			if(whElSerachFilter.getSerialElement() != null){
				stringQuery.append(" and ((decode(we.SER_ID,NULL,NULL,s.serial_code)=:aSerialElement or decode(we.SER_ID,NULL,NULL,sl.serial_code) = :aSerialElement)) ");
			}
			
			
        	//----------------------------------------------------------------
			//Paginacion 
			stringCount.append(" Select count(*) from ( ");
			stringCount.append( stringQuery.toString() + " ) " );        	
        	Query countQuery = session.createSQLQuery( stringCount.toString() );
        	
        	stringQuery.append(stringOrder.toString());
        	//Query query = session.createQuery(stringQuery.toString());
        	Query query = session.createSQLQuery(stringQuery.toString())
			.addScalar("dealerId", Hibernate.LONG)
			.addScalar("company")
			.addScalar("branch")
			.addScalar("whTypeId", Hibernate.LONG)
			.addScalar("whTypeName")
			.addScalar("crewId", Hibernate.LONG)
			.addScalar("crewName")
			.addScalar("ubicacion")
			.addScalar("modelName")
			.addScalar("modelId", Hibernate.LONG)
			.addScalar("elementTypeName")
			.addScalar("elementTypeId", Hibernate.LONG)
			.addScalar("serialCode")
			.addScalar("rid")
			.addScalar("serialCodeLinked")
			.addScalar("actualQuantity", Hibernate.DOUBLE)
			.addScalar("age", Hibernate.LONG)
			.addScalar("whId", Hibernate.LONG)
			.addScalar("whTypeCode")
			.addScalar("warehouseCode")
			.addScalar("modelCode")
			.addScalar("typeElementCode")
			.addScalar("branchId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(WareHouseElementsReportDTO.class));
        	query.setParameter("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity(), Hibernate.STRING);
        	query.setParameter("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
        	query.setParameter("aSysdate", new Timestamp( sysdate.getTimeInMillis() ) , Hibernate.TIMESTAMP);
        	countQuery.setParameter("anIsResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(),Hibernate.STRING);
        	countQuery.setParameter("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity(), Hibernate.STRING);
        	countQuery.setParameter("aSysdate", new Timestamp( sysdate.getTimeInMillis() ) , Hibernate.TIMESTAMP);
        	
        	
        	if (filterDealer){
				
        		if (whElSerachFilter.getDealerId()!=null){
        			query.setParameter("aDealerId", whElSerachFilter.getDealerId(), Hibernate.LONG);
        			countQuery.setParameter("aDealerId", whElSerachFilter.getDealerId(), Hibernate.LONG);
        		}
				
        		if (whElSerachFilter.getBranchDealerId()!=null){
        			query.setParameter("aDealerIdBranch",whElSerachFilter.getBranchDealerId(), Hibernate.LONG);
        			countQuery.setParameter("aDealerIdBranch",whElSerachFilter.getBranchDealerId(), Hibernate.LONG);
        		}
        		
        	}
        	
        	//CC053
        	if (whElSerachFilter.getCrewId()!=null){
				query.setParameter("aCrewId", whElSerachFilter.getCrewId(), Hibernate.LONG);
				countQuery.setParameter("aCrewId",whElSerachFilter.getCrewId(), Hibernate.LONG);
        	}
        	
        	//CC053
        	if (whElSerachFilter.getWarehouseTypeId()!=null ){
				query.setParameter("aWhTypeId",whElSerachFilter.getWarehouseTypeId(), Hibernate.LONG);
				countQuery.setParameter("aWhTypeId",whElSerachFilter.getWarehouseTypeId(), Hibernate.LONG);
        	}
        	
        	//CC053
        	if (whElSerachFilter.getWareHouseId()!=null){
				query.setParameter("aWhId",whElSerachFilter.getWareHouseId(), Hibernate.LONG);
				countQuery.setParameter("aWhId",whElSerachFilter.getWareHouseId(), Hibernate.LONG);
        	}
        	
        	//CC053
        	if (whElSerachFilter.getElementModelId()!=null) {
				query.setParameter("aElementModelId",whElSerachFilter.getElementModelId(), Hibernate.LONG);
				countQuery.setParameter("aElementModelId",whElSerachFilter.getElementModelId(), Hibernate.LONG);
        	}
        	
        	//CC053
        	if (whElSerachFilter.getElementTypeId()!=null) {
				query.setParameter("aTypeElementId",whElSerachFilter.getElementTypeId(), Hibernate.LONG);
				countQuery.setParameter("aTypeElementId",whElSerachFilter.getElementTypeId(), Hibernate.LONG);
        	}
				
			if(whElSerachFilter.getSerialElement() != null){
				query.setParameter("aSerialElement",whElSerachFilter.getSerialElement().toUpperCase(), Hibernate.STRING);
				countQuery.setParameter("aSerialElement",whElSerachFilter.getSerialElement().toUpperCase(), Hibernate.STRING);					
			}
			
			
			//Paginacion
        	Long recordQty = 0l;
        	if( requestCollInfo != null ){	  
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	//-----------------------------------------------------------------
        	long time = System.currentTimeMillis();
        	Timestamp t = new Timestamp(time);
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.'");
        	log.debug("== Inicia CONSULTA getWarehouseElementsByWarehouse/WarehouseElementDAO |"+df.format(t.getTime())+"|==");
        	//-----------------------------------------------------------------
        	
        	List<WareHouseElementsReportDTO> warehouseElements = query.list();
        	
        	//-----------------------------------------------------------------        	
        	time = System.currentTimeMillis();
        	t = new Timestamp(time);        	
        	log.debug("== Fin CONSULTA getWarehouseElementsByWarehouse/WarehouseElementDAO |"+df.format(t.getTime())+"|==");
        	log.debug("== Cantidad de registros traidos por la consulta: "+warehouseElements.size());        	
        	//-----------------------------------------------------------------
        	
        	QuantityWarehouseElementResponse response = new QuantityWarehouseElementResponse(warehouseElements);
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
			return response;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByWarehouse/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getQuantityWarehouseElementsDetailsByFilters(co.com.directv.sdii.model.dto.QuantityWarehouseElementsDTO, co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementCustomerResponse getWarehouseElementsByWarehouseCustomerActual(
			WareHouseElementClientFilterRequestDTO whElSerachFilter,RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {

		log.debug("== Inicia getWarehouseElementsByCustomer/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			boolean customerIdSpecified = false, 
					startDateSpecified = false,
					finalDateSpecified = false,
					serialSpecified = false;
			
			
			Date iniDate = null;
			Date endDate = null;
			
			customerIdSpecified = whElSerachFilter.getCustomerId()!=null && whElSerachFilter.getCustomerId().longValue()>0L;
			startDateSpecified = whElSerachFilter.getStartDate()!=null;
			finalDateSpecified = whElSerachFilter.getEndDate()!=null;
			serialSpecified = whElSerachFilter.getSerial()!=null && whElSerachFilter.getSerial().length()>0;
			
			
			StringBuffer stringCount = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.warehouseType.whTypeCode = :whType and");
			stringQuery.append(" entity.warehouseId.customerId.id = :customerId ");
			if(startDateSpecified) {
				iniDate = UtilsBusiness.getFirstMomentOfDay(whElSerachFilter.getStartDate());
				stringQuery.append(" and trunc(entity.movementDate) >= trunc(:startDate) ");
			}
			
			if(finalDateSpecified) {
				endDate = UtilsBusiness.getLastMomentOfDay(whElSerachFilter.getEndDate());
				stringQuery.append(" and trunc(entity.movementDate) <= trunc(:endDate) "); 
			}
			
			if(serialSpecified) {
				stringQuery.append(" AND ( entity.serialized.serialCode = :elementSerial or ");
				stringQuery.append(" exists(select 1 from Serialized s where s.id=entity.serialized.id and s.serialized.serialCode = :elementSerial)) ");
			}
			stringQuery.append(" AND entity.recordStatus.recordStatusCode = :recordStatusCode ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
			if(customerIdSpecified){
				query.setLong("customerId", whElSerachFilter.getCustomerId());
			}
			if(startDateSpecified) {
				query.setDate("startDate", iniDate);
			}
			
			if(finalDateSpecified) {
				query.setDate("endDate", endDate); 
			}
			
			if(serialSpecified) {
				query.setString("elementSerial", whElSerachFilter.getSerial().toUpperCase());
			}
			if(whElSerachFilter.isActualElements()){
				query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			}else{
				query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
			}

			
			
			stringQuery.append(" order by  entity.movementDate asc");
        	//Paginacion
        	Long recordQty = 0L;
        	if(requestCollInfo != null){	  
            	stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	countQuery.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());
            	if(customerIdSpecified){
            		countQuery.setLong("customerId", whElSerachFilter.getCustomerId());
    			}
            	
            	
    			if(startDateSpecified) {
    				countQuery.setDate("startDate", iniDate);
    			}
    			
    			if(finalDateSpecified) {
    				countQuery.setDate("endDate", endDate); 
    			}
    			
    			if(serialSpecified) {
    				countQuery.setString("elementSerial", whElSerachFilter.getSerial().toUpperCase());
    			}
    			
    			if(whElSerachFilter.isActualElements()){
    				countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
    			}else{
    				countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_HISTORIC.getCodeEntity());
    			}
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
        	}
        	
        	WareHouseElementCustomerResponse response = new WareHouseElementCustomerResponse();
        	List<WarehouseElement> wareHouse = query.list();
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), wareHouse.size(), recordQty.intValue());
        	}
        	response.setWareHouseElements(wareHouse);
        	
			return response;

					} catch (Throwable ex) {
			log.error("== Error == getWarehouseElementsByCustomer/WarehouseElementDAO");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByCustomer/WarehouseElementDAO ==");
		}
	}
	
	
	/**
	 * Obtiene la cantidad actual de elementos serializados en una bodega por filtros
	 * @param dealerId
	 * @param warehouseId
	 * @param crewId
	 * @param warehouseTypeId
	 * @param elementTypeId
	 * @param elementModelId
	 * @param initialDate
	 * @param finalDate
	 * @param isSerialized
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 */
	private List<WarehouseElementQuantity> getCurrentElementQuantityInWHByFiltersAndIsSerialized(
			Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId,
			Long elementTypeId, Long elementModelId, Date initialDate,
			Date finalDate, boolean isSerialized) throws DAOServiceException, DAOSQLException {
		
		StringBuffer stringQuery = new StringBuffer();
		stringQuery = getQueryHeaderToActualQty(isSerialized);
		List<WarehouseElementQuantity> result = this.getCurrentElementQuantityInWHByFiltersAndQuantityType(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, stringQuery, WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_ACTUAL_QUANTITY, initialDate, finalDate);
		stringQuery = getQueryHeaderToInputs(isSerialized);
		List<WarehouseElementQuantity> result2 = this.getCurrentElementQuantityInWHByFiltersAndQuantityType(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, stringQuery, WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_ENTRANCE_QUANTITY, initialDate, finalDate);
		result.addAll(result2);
		stringQuery = getQueryHeaderToOutputs(isSerialized);
		List<WarehouseElementQuantity> result3 = this.getCurrentElementQuantityInWHByFiltersAndQuantityType(dealerId, warehouseId, crewId, warehouseTypeId, elementTypeId, elementModelId, stringQuery, WarehouseElementQuantity.WH_ELEMENT_QTY_TYPE_TOTAL_OUTPUT_QUANTITY, initialDate, finalDate);
		result.addAll(result3);

		return result;
	}


	/**
	 * Retorna el query para calcular el total de las salidas de un elemento por serial o por tipo
	 * 
	 * @param isSerialized
	 * @return
	 */
	private StringBuffer getQueryHeaderToOutputs(boolean isSerialized) {
		StringBuffer stringQuery = new StringBuffer();
		stringQuery.append("select sum(whe.initial_quantity) - sum(whe.actual_quantity) as equantity, et.id as eelement_type_id, wh.id as wwarehouse_id from ");
		stringQuery.append("warehouse_elements whe, ");
		stringQuery.append("element_types et, ");
		if(isSerialized){
			stringQuery.append("serialized s, ");
		}else{
			stringQuery.append("not_serialized ns, ");
		}
		stringQuery.append("elements e, ");
		stringQuery.append("warehouse wh where ");
		if(isSerialized){
			stringQuery.append("whe.not_ser_id is null and whe.ser_id = s.element_id and e.id = s.element_id and e.element_type_id = et.id and ");
		}else{
			stringQuery.append("whe.ser_id is null and whe.not_ser_id = ns.element_id and e.id = ns.element_id and e.element_type_id = et.id and ");
		}
		stringQuery.append("whe.record_status_id in (select rs.id from record_status rs where rs.record_status_code = 'U') and ");
		stringQuery.append("whe.mov_type_id in (select mt.id from MOVEMENT_TYPES mt where mt.mov_class = 'S') and ");
		stringQuery.append("whe.WAREHOUSE_ID = wh.id ");
		return stringQuery;
	}

	/**
	 * Retorna el query para calcular el total de las entradas de un elemento por serial o por tipo
	 * 
	 * @param isSerialized
	 * @return
	 */
	private StringBuffer getQueryHeaderToInputs(boolean isSerialized) {
		StringBuffer stringQuery = new StringBuffer();
		stringQuery.append("select sum(whe.actual_quantity) - sum(whe.initial_quantity) as equantity, et.id as eelement_type_id, wh.id as wwarehouse_id from ");
		stringQuery.append("warehouse_elements whe, ");
		stringQuery.append("element_types et, ");
		if(isSerialized){
			stringQuery.append("serialized s, ");
		}else{
			stringQuery.append("not_serialized ns, ");
		}
		stringQuery.append("elements e, ");
		stringQuery.append("warehouse wh where ");
		if(isSerialized){
			stringQuery.append("whe.not_ser_id is null and whe.ser_id = s.element_id and e.id = s.element_id and e.element_type_id = et.id and ");
		}else{
			stringQuery.append("whe.ser_id is null and whe.not_ser_id = ns.element_id and e.id = ns.element_id and e.element_type_id = et.id and ");
		}
		stringQuery.append("whe.record_status_id in (select rs.id from record_status rs where rs.record_status_code = 'U') and ");
		stringQuery.append("whe.mov_type_id in (select mt.id from MOVEMENT_TYPES mt where mt.mov_class = 'E') and ");
		stringQuery.append("whe.WAREHOUSE_ID = wh.id ");
		return stringQuery;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getCurrentQuantityInWarehouseByElementType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private List<WarehouseElementQuantity> getCurrentElementQuantityInWHByFiltersAndQuantityType(
			Long dealerId, Long warehouseId, Long crewId, Long warehouseTypeId, 
			Long elementTypeId, Long elementModelId, StringBuffer queryHeader, String whElQueryType, Date initialDate, Date finalDate)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getCurrentElementQuantityInWHByFiltersAndQuantityType/WarehouseElementDAO ==");
		StringBuffer stringQuery = new StringBuffer(); 
		try {
			Session session = super.getSession();

			boolean dealerIdSpecified = false, warehouseIdSpecified = false, crewIdSpecified = false;
			boolean warehouseTypeIdSpecified = false, elementTypeIdSpecified = false, elementModelIdSpecified = false;
			boolean initialAndFinalDateSpecified = false;

			stringQuery.append(queryHeader);

			//agregando los filtros especificados
			if(dealerId != null && dealerId.longValue() > 0){
				dealerIdSpecified = true;
				stringQuery.append("and wh.dealer_id is not null and wh.dealer_id = :aDealerId ");
			}

			if(warehouseId != null && warehouseId.longValue() > 0){
				warehouseIdSpecified = true;
				stringQuery.append("and wh.id = :aWhId ");
			}

			if(crewId != null && crewId.longValue() > 0){
				crewIdSpecified = true;
				stringQuery.append("and wh.crew_id is not null and wh.crew_id = :aCrewId ");
			}

			if(warehouseTypeId != null && warehouseTypeId.longValue() > 0){
				warehouseTypeIdSpecified = true;
				stringQuery.append("and wh.wh_type_id = :aWhTypeId ");
			}

			if(elementTypeId != null && elementTypeId.longValue() > 0){
				elementTypeIdSpecified = true;
				stringQuery.append("and e.element_type_id = :anElementTypeId ");
			}

			if(elementModelId != null && elementModelId.longValue() > 0){
				elementModelIdSpecified = true;
				stringQuery.append("and et.element_model_id = :anElementModelId ");
			}

			if(initialDate != null && finalDate != null){
				initialAndFinalDateSpecified = true;
				stringQuery.append("and ( whe.MOVEMENT_DATE between :anInitialDate and :anFinalDate ) ");
			}

			stringQuery.append("group by et.id, wh.id");

			SQLQuery sqlQuery = session.createSQLQuery(stringQuery.toString());

			if(dealerIdSpecified){
				sqlQuery.setLong("aDealerId", dealerId);
			}
			if(warehouseIdSpecified){
				sqlQuery.setLong("aWhId", warehouseId);
			}
			if(crewIdSpecified){
				sqlQuery.setLong("aCrewId", crewId);
			}
			if(warehouseTypeIdSpecified){
				sqlQuery.setLong("aWhTypeId", warehouseTypeId);
			}
			if(elementTypeIdSpecified){
				sqlQuery.setLong("anElementTypeId", elementTypeId);
			}
			if(elementModelIdSpecified){
				sqlQuery.setLong("anElementModelId", elementModelId);
			}

			if(initialAndFinalDateSpecified){
				sqlQuery.setDate("anInitialDate", initialDate);
				sqlQuery.setDate("anFinalDate", finalDate);
			}

			//definiendo los tipos de dato de resultado:
			sqlQuery.addScalar("equantity", Hibernate.DOUBLE);
			sqlQuery.addScalar("eelement_type_id", Hibernate.LONG);
			sqlQuery.addScalar("wwarehouse_id", Hibernate.LONG);

			List<Object[]> resultObj = sqlQuery.list();
			List<WarehouseElementQuantity> result = new ArrayList<WarehouseElementQuantity>();
			WarehouseElementQuantity whElQty;
			Double qty = 0D;
			for (Object[] objects : resultObj) {
				whElQty = new WarehouseElementQuantity();
				qty = new Double((Double)objects[0]);
				if(qty <= 0D){
					qty = 0D;
				}
				whElQty.setQuantity(qty);
				whElQty.setElementTypeId((Long)objects[1]);
				whElQty.setWarehouseId((Long)objects[2]);
				whElQty.setWhElWtyType(whElQueryType);
				result.add(whElQty);
			}
			return result;

		} catch(Throwable ex) {
			log.error("== Error getCurrentElementQuantityInWHByFiltersAndQuantityType/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getCurrentElementQuantityInWHByFiltersAndQuantityType/WarehouseElementDAO ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWHElementByWarehouseAndLastRecord(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WarehouseElement> getWHElementByWarehouseAndLastRecord(Long idWh)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getWHElementByWarehouseAndLastRecord/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.recordStatus.recordStatusCode = :status");
			stringQuery.append(" and entity.warehouseId.id = :idWh");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("status", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("idWh", idWh);

			return query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWHElementByWarehouseAndLastRecord/WarehouseElementDAO ==");
		}
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getNOTSerializedElementsByWHTypeIdDealerId(String whTypeCode, Long dealerId, RequestCollectionInfo requestCollInfo)
																		throws DAOServiceException, DAOSQLException {
		
		
		log.debug("== Inicia getWarehouseElementsByFilters/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
						
			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.notSerialized is not null ");
            stringQuery.append(" and whe.serialized is null ");
            stringQuery.append(" and whe.actualQuantity > 0.0 " );
			stringQuery.append(" and exists (select 1 " );
			stringQuery.append("               from "+Warehouse.class.getName()+" wh " );
			stringQuery.append("               		inner join wh.warehouseType wht " );
			stringQuery.append("              where whe.warehouseId.id=wh.id " );
			stringQuery.append("                    AND wh.crewId is null " );
			stringQuery.append("                    AND wht.whTypeCode = :whTypeCode ");
			stringQuery.append("                    and wh.dealerId.id = :dealerId) ");
			stringQuery.append(" and whe.recordStatus.recordStatusCode = :recordStatusCode ");
						
			String finalQuery = stringQuery.toString();
			Query query = session.createQuery(finalQuery);
			
			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append( finalQuery );
			Query countQuery = session.createQuery( stringCount.toString() );
			
			
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setString("whTypeCode", whTypeCode);
			countQuery.setString("whTypeCode", whTypeCode);
			query.setLong("dealerId", dealerId);
        	countQuery.setLong("dealerId", dealerId);

			//Paginacion
        	Long recordQty = 0l;
        	if( requestCollInfo != null ){	              	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}

        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> warehouseElements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
        	response.setWareHouseElements( warehouseElements );
			
        	return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getWarehouseElementsByFilters/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByFilters/WarehouseElementDAO ==");
		}

	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WareHouseElementResponse getSerializedElementsByWHTypeDealerElementTypeSerialCode(String whTypeCode, Dealer dealer, String elementType, String serialCode, RequestCollectionInfo requestCollInfo )
																		throws DAOServiceException, DAOSQLException {
		
		
		log.debug("== Inicia getSerializedElementsByWHTypeDealerElementTypeSerialCode/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
						
			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.notSerialized is null ");
            stringQuery.append(" and whe.serialized is not null ");
            stringQuery.append(" and whe.actualQuantity > 0.0 " );
            stringQuery.append(" and whe.warehouseId.warehouseType.whTypeCode = :whTypeCode " );
			stringQuery.append(" and whe.warehouseId.dealerId.id = :dealerId ");
			stringQuery.append(" and whe.recordStatus.recordStatusCode = '").append(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()).append("'");
			stringQuery.append(" and whe.serialized.elementId not in (SELECT s.serialized.elementId FROM "+Serialized.class.getName()+"  s where s.serialized.elementId is not null ) ");
			
			
			if ( elementType!=null && elementType.length()>0 ){
				stringQuery.append(" and whe.serialized.element.elementType.typeElementCode = :elementType ");
			}
			if ( serialCode!=null && serialCode.length()>0 ){
				stringQuery.append(" and (whe.serialized.serialCode = :serialCode or exists (select 1 from "+Serialized.class.getName()+" serialized where " +
						" serialized.serialized.serialCode = :serialCode and whe.serialized.elementId = serialized.elementId) )");
			}
						
			String finalQuery = stringQuery.toString();
			Query query = session.createQuery(finalQuery);
			
			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append( finalQuery );
			Query countQuery = session.createQuery( stringCount.toString() );
			
			query.setLong("dealerId", dealer.getId());
        	countQuery.setLong("dealerId", dealer.getId());
        	query.setString("whTypeCode", whTypeCode);
        	countQuery.setString("whTypeCode", whTypeCode);
        	if ( elementType!=null && elementType.length()>0 ){
	        	query.setString("elementType", elementType);
	        	countQuery.setString("elementType", elementType);
        	}
        	if ( serialCode!=null && serialCode.length()>0 ){
	        	query.setString("serialCode", serialCode.toUpperCase());
	        	countQuery.setString("serialCode", serialCode.toUpperCase());
        	}

			//Paginacion
        	Long recordQty = 0l;
        	if( requestCollInfo != null ){	              	
	        	recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}

        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> warehouseElements = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue() );
        	response.setWareHouseElements( warehouseElements );
			
        	return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getSerializedElementsByWHTypeDealerElementTypeSerialCode/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedElementsByWHTypeDealerElementTypeSerialCode/WarehouseElementDAO ==");
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWareHouseElementByserialID(String isSerialized, Long elementId) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getWhElementInWHByWarehouseIdAndElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where ");
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_NOT_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" warehouseElement.notSerialized.elementId = :aElementId");
			}
			if(elementId != null && isSerialized.compareTo(CodesBusinessEntityEnum.ELEMENT_TYPE_SERIALIZED.getCodeEntity()) == 0){
				stringQuery.append(" warehouseElement.serialized.elementId = :aElementId");
			}
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aElementId", elementId);

			List<WarehouseElement> whEs = query.list();
			
			if(whEs.isEmpty()){
				return null;
			}
			return (WarehouseElement) whEs.get(0);

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWhElementInWHByWarehouseIdAndElementID/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementBySerialOrElementType(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public WarehouseElement getWarehouseElementBySerialOrElementType(Long warehouseId, String serial, Long elementTypeId) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementBySerialOrElementType/ElementBusinessBean ==");
		Session session = super.getSession();
		try {
			boolean searchSerialized = !StringUtils.isEmpty(serial);
			
			StringBuffer stringQuery = new StringBuffer("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" warehouseElement where warehouseElement.warehouseId = :warehouseId and ");
			
			if(searchSerialized){
				stringQuery.append(" warehouseElement.serialized.element.elementType.id = :elementTypeId");
				stringQuery.append(" and warehouseElement.serialized.serialCode = :serial ");
			} else {
				stringQuery.append(" warehouseElement.notSerialized.element.elementType.id = :elementTypeId");
			}
			
			stringQuery.append(" and warehouseElement.recordStatus.recordStatusCode = '")
				.append(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()).append("'");
			
			Query query = session.createQuery(stringQuery.toString());
			
			query.setLong("elementTypeId", elementTypeId);
			query.setLong("warehouseId", warehouseId);
			
			if(searchSerialized){
				query.setString("serial", serial.toUpperCase());
			}
			
			WarehouseElement whE = (WarehouseElement) query.uniqueResult();
			return whE;
			
		} catch (Throwable t) {
			log.debug("== Error al tratar de ejecutar la operación getWarehouseElementBySerialOrElementType/ElementBusinessBean ==", t);
			throw this.manageException(t);
		} finally {
			log.debug("== Termina getWarehouseElementBySerialOrElementType/ElementBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementBySerialActive(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWarehouseElementBySerialActive(String serialCode,Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.serialCode = :aSerialCode and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus and");
			stringQuery.append(" entity.serialized.element.country.id = :countryId ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("aSerialCode", serialCode.toUpperCase());
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("countryId", countryId);
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementBySerialActive(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Object[]> getWarehouseElementBySerialActive(List<String> multipleSerialCode,Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append(" SELECT S.SERIAL_CODE, ");
			stringQuery.append("        C.ID, ");
			stringQuery.append("        W.ID ");
			stringQuery.append(" FROM SERIALIZED s inner join WAREHOUSE_ELEMENTS we on(S.ELEMENT_ID=WE.SER_ID) ");
			stringQuery.append("                               inner join RECORD_STATUS r on(R.ID=WE.RECORD_STATUS_ID) ");
			stringQuery.append("                               inner join WAREHOUSES w on(W.ID=WE.WAREHOUSE_ID) ");
			stringQuery.append("                               inner join COUNTRIES c on(c.ID=W.COUNTRY_ID) ");

			boolean isFirstCondition = true;
			int indice = 0;
			for(String msc: multipleSerialCode){
				if(isFirstCondition){
					isFirstCondition = false;
					stringQuery.append(" WHERE ( S.SERIAL_CODE = :aSerialCode"+indice+" ");
				}
				else{
					stringQuery.append(" OR S.SERIAL_CODE = :aSerialCode"+indice+" ");
				}
			}
			if(!isFirstCondition){
				stringQuery.append(" ) AND ( ");
			}
			stringQuery.append(" R.RECORD_STATUS_CODE = :recordStatus ");
			stringQuery.append(" and c.id = :countryId ) ");
			Query query = session.createSQLQuery(stringQuery.toString());
			for(int i=0; i<multipleSerialCode.size(); ++i){
				query.setString("aSerialCode"+indice, multipleSerialCode.get(i).toUpperCase());
			}
			//query.setString("aSerialCode", serialCode);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("countryId", countryId);
			return (List<Object[]>) query.list();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		}
	}
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementBySerialActive(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getWarehouseElementByElementIdActive(Long elementId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.elementId = :elementId and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("elementId", elementId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementBySerialActive/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementQuantityByElementType(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Double getWarehouseElementQuantityByElementType(Long warehouseId, Long elementTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementQuantityByElementType/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer("select sum(entity.actualQuantity) from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.warehouseId.id = :warehouseId and");
			stringQuery.append(" entity.notSerialized.element.elementType.id = :elementTypeId and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("warehouseId", warehouseId);
			query.setLong("elementTypeId", elementTypeId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			Double result = (Double) query.uniqueResult();
			if(result == null){
				result = 0D;
			}
			return  result;

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWarehouseElementQuantityByElementType/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerId(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerId(String elementSerial,Long dealerId,String whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerId/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode = :whTypeCode ");
			stringQuery.append("and whe.warehouseId.dealerId.id = :dealerId ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is not null and whe.warehouseId.crewId is null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whTypeCode", whTypeCode);
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerId", dealerId);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerId/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerId/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerIdAndWhTypes(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerIdAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and ( exists( select 1 from Dealer d where d.id = :dealerId and d.id = whe.warehouseId.dealerId.id )");
			stringQuery.append("or exists ( select 1 from Dealer d where d.dealer.id = :dealerId and d.id = whe.warehouseId.dealerId.dealer.id ) ");
			stringQuery.append("or exists ( select 1 from Crew c where c.dealer.id = :dealerId and c.id = whe.warehouseId.crewId.id ) ) ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerId", dealerId);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerCodeAndWhTypes(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerCodeAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and ( exists( select 1 from Dealer d where d.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.id )");
			stringQuery.append("or exists ( select 1 from Dealer d where d.dealer.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.dealer.id ) ");
			stringQuery.append("or exists ( select 1 from Crew c where c.dealer.dealerCode = :dealerCode and c.id = whe.warehouseId.crewId.id ) ) ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerCode", dealerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerIdInCrewWh(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerIdInCrewWh(String elementSerial,Long dealerId, String whTypeCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode = :whTypeCode ");
			stringQuery.append("and whe.warehouseId.crewId.dealer.id = :dealerId ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is not null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whTypeCode", whTypeCode);
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerId", dealerId);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerIdInCrewWhAndWhTypes(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerIdInCrewWhAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and whe.warehouseId.crewId.dealer.id = :dealerId ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is not null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerId", dealerId);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerIdWhAndWhTypes(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerIdWhAndWhTypes(String elementSerial, Long dealerId, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdWhAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.dealerId.id = :dealerId ");
			stringQuery.append("and  whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is not null and whe.warehouseId.crewId is null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerId", dealerId);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdWhAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdWhAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndDealerCodeInCrewWhAndWhTypes(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndDealerCodeInCrewWhAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and whe.warehouseId.crewId.dealer.dealerCode = :dealerCode ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is not null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerCode", dealerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementByLinkedSerialAndDealerCodeInCrewWhAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia getElementByLinkedSerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and ( exists( select 1 from Dealer d where d.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.id ) ");
			stringQuery.append("or exists ( select 1 from Dealer d where d.dealer.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.dealer.id ) ");
			stringQuery.append("or exists ( select 1 from Crew c where c.dealer.dealerCode = :dealerCode and c.id = whe.warehouseId.crewId.id ) ) ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerCode", dealerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementByLinkedSerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementByLinkedSerialAndDealerCodeInCrewWhAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementByLinkedSerialAndDealerCodeAndWhTypes(java.lang.String, java.lang.Long, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementByLinkedSerialAndDealerCodeAndWhTypes(String elementSerial, Long dealerCode, List<String> whTypeCodes) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementByLinkedSerialAndDealerCodeAndWhTypes/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.warehouseType.whTypeCode in ("+ UtilsBusiness.stringListToStringForQuery(whTypeCodes, ",") +") ");
			stringQuery.append("and ( exists( select 1 from Dealer d where d.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.id ) ");
			stringQuery.append("or exists ( select 1 from Dealer d where d.dealer.dealerCode = :dealerCode and d.id = whe.warehouseId.dealerId.dealer.id ) ");
			stringQuery.append("or exists ( select 1 from Crew c where c.dealer.dealerCode = :dealerCode and c.id = whe.warehouseId.crewId.id ) ) ");
			stringQuery.append("and whe.serialized.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("dealerCode", dealerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementByLinkedSerialAndDealerCodeAndWhTypes/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementByLinkedSerialAndDealerCodeAndWhTypes/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndCustomerId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndCustomerId(String elementSerial,Long customerId) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.warehouseId.customerId.id = :customerId ");
			stringQuery.append("and whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is null and whe.warehouseId.customerId is not null");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setLong("customerId", customerId);
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByCustomerIdSerialAndDatesRange(java.lang.Long, java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public WareHouseElementResponse getWarehouseElementsByCustomerIdSerialAndDatesRange(
			WareHouseElementClientFilterRequestDTO request, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		Session session = super.getSession();

		boolean customerIdSpecified = false,
                serialSpecified = false,
                startDateSpecified = false,
                endDateSpecified = false;
		
		String strWhereOrAnd = " WHERE ";
		
		try {
			
			Date startDate = null;
        	Date endDate = null;
        	StringBuffer stringOrder = new StringBuffer();
			StringBuffer stringQuery = new StringBuffer("from " + WarehouseElement.class.getName() + " whe ");
			
			if(request.getSerial()!=null && request.getSerial().trim().length()>0) {
				serialSpecified = true;
				stringQuery.append(strWhereOrAnd + "( whe.serialized.serialCode = :elementSerial or ");
				stringQuery.append(" exists(select 1 from Serialized s where s.id=whe.serialized.id and s.serialized.serialCode = :elementSerial)) ");
				strWhereOrAnd = " AND ";
			}
			
			if(request.getCustomerId()!=null && request.getCustomerId().longValue()>0) {
				customerIdSpecified = true;
				stringQuery.append(strWhereOrAnd + " whe.warehouseId.customerId.id = :customerId ");
				strWhereOrAnd = " AND ";
			}
			
			if(request.getStartDate()!=null) {
				startDate = UtilsBusiness.getFirstMomentOfDay(request.getStartDate());
	        	startDateSpecified = true;
				stringQuery.append(strWhereOrAnd + " trunc(whe.movementDate) >= trunc(:startDate) ");
				strWhereOrAnd = " AND ";
			}
			
			if(request.getEndDate()!=null) {
				endDate = UtilsBusiness.getLastMomentOfDay(request.getEndDate());
				endDateSpecified = true;
				stringQuery.append(strWhereOrAnd + " trunc(whe.movementDate) <= trunc(:endDate) ");
				strWhereOrAnd = " AND ";
			}
			if(request.isActualElements()){
				stringQuery.append(" AND whe.recordStatus.recordStatusCode = :recordStatusCode ");
			}
			
			stringOrder.append(" order by whe.id desc ");
			
			Query query = session.createQuery(stringQuery.toString() + stringOrder.toString());
			
			if(customerIdSpecified) {
				query.setLong("customerId", request.getCustomerId());
			}
			
			if(serialSpecified) {
				query.setString("elementSerial", request.getSerial().toUpperCase());
			}
			
			if(startDateSpecified) {
				query.setDate("startDate", startDate);
			}
			
			if(endDateSpecified) {
				query.setDate("endDate", endDate);
			}
			
			if(request.isActualElements()){
				query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			}
			
			//wjimenez hace falta este parámetro?
			//query.setString("whType",CodesBusinessEntityEnum.WAREHOUSE_TYPE_CLIENTE.getCodeEntity());

			//Paginacion
        	Long recordQty = 0l;
        	if( requestCollInfo != null ){
        		StringBuffer stringCount = new StringBuffer();
            	stringCount.append("select count(*) ");
            	stringCount.append( stringQuery.toString() );        	
            	Query countQuery = session.createQuery( stringCount.toString() );
            	
            	if(customerIdSpecified) {
            		countQuery.setLong("customerId", request.getCustomerId());
    			}
    			
    			if(serialSpecified) {
    				countQuery.setString("elementSerial", request.getSerial().toUpperCase());
    			}
    			
    			if(startDateSpecified) {
    				countQuery.setDate("startDate", startDate);
    			}
    			
    			if(endDateSpecified) {
    				countQuery.setDate("endDate", endDate);
    			}
    			if(request.isActualElements()){
    				countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
    			}
    			
	        	recordQty = (Long)countQuery.uniqueResult();
				query.setFirstResult( requestCollInfo.getFirstResult() );
				query.setMaxResults( requestCollInfo.getMaxResults() );				
        	}
        	
        	WareHouseElementResponse response = new WareHouseElementResponse();
        	List<WarehouseElement> wareHouse = query.list();
        	if( requestCollInfo != null )
				populatePaginationInfo( response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), wareHouse.size(), recordQty.intValue() );
        	response.setWareHouseElements( wareHouse );
        	
			return response;
			
		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndDealerIdInCrewWh/WarehouseElementDAO ==");
		}
	}
	
	

	@Override
	public WarehouseElement getWarehouseElementSerializedLastByElementID(Long elementId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.id = :elementId and");
			stringQuery.append(" entity.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("elementId", elementId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getQuantityWarehouseElementByDealersAndWarehouseType(java.util.List, java.util.List)
	 */
	@Override
	public Long getQuantityWarehouseElementByDealersAndWarehouseType(List<Long> dealerId,List<String> whTypeCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			
			StringBuffer stringQuery = new StringBuffer();
			
			stringQuery.append(" select count(distinct whe.id) ");
			stringQuery.append("   from ").append(WarehouseElement.class.getName()).append(" whe  ");         
			stringQuery.append("  inner join whe.warehouseId w   ");       
			stringQuery.append("  inner join w.crewId c     ");     
			stringQuery.append("  inner join c.dealer d, ");
			stringQuery.append("  ").append(Warehouse.class.getName()).append(" ws inner join ws.dealerId ds ");
			stringQuery.append(" where w.warehouseType.isActive = :whIsActive ");
			stringQuery.append(" and ws.warehouseType.isActive = :whIsActive ");
			stringQuery.append(" and w.isActive = :whActiveStatus ");
			stringQuery.append(" and whe.recordStatus.recordStatusCode = :recordStatus ");
			stringQuery.append(" and ws.crewId is null ");
			stringQuery.append(" and ws.warehouseType.id = w.warehouseType.id ");         
			stringQuery.append(" and ds.id = d.id  ");
			
			if(whTypeCode != null && whTypeCode.size()>0)
				stringQuery.append("        and w.warehouseType.whTypeCode in("+UtilsBusiness.stringListToString(whTypeCode,",")+") ");
			
			if(dealerId != null && dealerId.size()>0)
				stringQuery.append("        and d.id in("+UtilsBusiness.longListToString(dealerId,",")+") ");
			      
			Query query = session.createQuery(stringQuery.toString());
			query.setString("whIsActive", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_ACTIVE.getCodeEntity());
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setString("whActiveStatus" , CodesBusinessEntityEnum.WAREHOUSE_STATUS_ACTIVE.getCodeEntity() );
			
			return (Long) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementLastByElementID/WarehouseElementDAO ==");
		}
	}
	
	
	
	public WareHouseElementResponse getSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
		throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getSerializedElementsLastByCriteria/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {
			boolean isDealer = (wareHouseRequestDTO.getDealerId() != null && wareHouseRequestDTO.getDealerId().longValue()>0);
			boolean isCrew = (wareHouseRequestDTO.getCrewId() != null && wareHouseRequestDTO.getCrewId().longValue()>0);
			boolean isCustomer = (wareHouseRequestDTO.getCustomerId() != null && wareHouseRequestDTO.getCustomerId().longValue()>0);
			boolean isWarehouse = (wareHouseRequestDTO.getWarehouseId() != null && wareHouseRequestDTO.getWarehouseId().longValue()>0);
			boolean isWarehouseType = (wareHouseRequestDTO.getWarehouseTypeId() != null && wareHouseRequestDTO.getWarehouseTypeId().longValue()>0);
			boolean isSerialCode = (wareHouseRequestDTO.getSerialCode() != null && !wareHouseRequestDTO.getSerialCode().equals(""));
			boolean isElementType = (wareHouseRequestDTO.getElementTypeId() != null && wareHouseRequestDTO.getElementTypeId().longValue()>0);
			

			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.notSerialized is null ");
			stringQuery.append(" and whe.serialized is not null ");
			stringQuery.append(" and whe.actualQuantity > 0.0 " );
			stringQuery.append(" and whe.warehouseId.warehouseType.isVirtual =:isVirtual " );
			//stringQuery.append(" and whe.warehouseId.dealerId.postalCode.city.state.country.id = :countryId " );
			stringQuery.append(" and whe.recordStatus.recordStatusCode = '").append(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()).append("'");
			stringQuery.append(" and not exists (SELECT s.elementId FROM " + Serialized.class.getName() + " s where s.serialized.elementId = whe.serialized.elementId ) ");
			
			if(isCustomer){
				stringQuery.append(" and whe.warehouseId.customerId.country.id = :countryId " );
				stringQuery.append(" and whe.warehouseId.customerId.id = :customerId ");
			}
			
			if(isDealer || isCrew){
				stringQuery.append(" and whe.warehouseId.dealerId.postalCode.city.state.country.id = :countryId " );
			}
			
			if(isDealer){
				stringQuery.append(" and (whe.warehouseId.dealerId.id = :dealerId or whe.warehouseId.dealerId.dealer.id = :dealerId) ");
			}
			
			if(isWarehouse){
				stringQuery.append(" and whe.warehouseId.id = :warehouseId ");
			}
			
			if(isWarehouseType){
				stringQuery.append(" and whe.warehouseId.warehouseType.id = :warehouseTypeId " );
			}
			
			if(isCrew){
				stringQuery.append(" and whe.warehouseId.crewId.id = :crewId " );
			}
			if(isSerialCode){
				stringQuery.append(" and (whe.serialized.serialCode = :serialCode OR EXISTS(select 1 from Serialized sVin where sVin.serialCode=:serialCode and sVin.elementId=whe.serialized.serialized.elementId))  ");
			}
			
			if(isElementType){
				stringQuery.append(" and whe.serialized.element.elementType.id = :elementTypeId ");
			}

			String finalQuery = stringQuery.toString();
			Query query = session.createQuery(finalQuery);

			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append( finalQuery );
			Query countQuery = session.createQuery( stringCount.toString() );

			//query.setLong("countryId", wareHouseRequestDTO.getCountryId());
			//countQuery.setLong("countryId", wareHouseRequestDTO.getCountryId());
			query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
			countQuery.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());

			if(isDealer || isCrew || isCustomer){
				query.setLong("countryId", wareHouseRequestDTO.getCountryId());
				countQuery.setLong("countryId", wareHouseRequestDTO.getCountryId());
				
			}
			
			if(isCustomer){
				query.setLong("customerId", wareHouseRequestDTO.getCustomerId());
				countQuery.setLong("customerId", wareHouseRequestDTO.getCustomerId());
			}
			
			if(isDealer){
				query.setLong("dealerId", wareHouseRequestDTO.getDealerId());
				countQuery.setLong("dealerId", wareHouseRequestDTO.getDealerId());
			}
			
			if(isWarehouse){
				query.setLong("warehouseId", wareHouseRequestDTO.getWarehouseId());
				countQuery.setLong("warehouseId", wareHouseRequestDTO.getWarehouseId());
			}
			
			if(isWarehouseType){
				query.setLong("warehouseTypeId", wareHouseRequestDTO.getWarehouseTypeId());
				countQuery.setLong("warehouseTypeId", wareHouseRequestDTO.getWarehouseTypeId());
			}
			
			if(isCrew){
				query.setLong("crewId", wareHouseRequestDTO.getCrewId());
				countQuery.setLong("crewId", wareHouseRequestDTO.getCrewId());
			}
			
			if(isSerialCode){
				query.setString("serialCode", wareHouseRequestDTO.getSerialCode().toUpperCase());
				countQuery.setString("serialCode", wareHouseRequestDTO.getSerialCode().toUpperCase());
			}
			
			if(isElementType){
				query.setLong("elementTypeId", wareHouseRequestDTO.getElementTypeId());
				countQuery.setLong("elementTypeId", wareHouseRequestDTO.getElementTypeId());
			}

			//Paginacion
			Long recordQty = 0l;
			if( wareHouseRequestDTO.getRequestCollInfo() != null ){	              	
				recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult(wareHouseRequestDTO.getRequestCollInfo().getFirstResult() );
				query.setMaxResults( wareHouseRequestDTO.getRequestCollInfo().getMaxResults() );				
			}

			WareHouseElementResponse response = new WareHouseElementResponse();
			List<WarehouseElement> warehouseElements = query.list();
			if( wareHouseRequestDTO.getRequestCollInfo() != null )
				populatePaginationInfo( response, wareHouseRequestDTO.getRequestCollInfo().getPageSize(), wareHouseRequestDTO.getRequestCollInfo().getPageIndex(), warehouseElements.size(), recordQty.intValue() );
			response.setWareHouseElements( warehouseElements );

			return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getSerializedElementsLastByCriteria/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getSerializedElementsLastByCriteria/WarehouseElementDAO ==");
		}

	}
	
	
	public WareHouseElementResponse getNotSerializedElementsLastByCriteria(WareHouseRequestDTO wareHouseRequestDTO)
		throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getNotSerializedElementsLastByCriteria/WarehouseElementDAO ==");
		Session session = super.getSession();
		try {

			boolean isDealer = (wareHouseRequestDTO.getDealerId() != null && wareHouseRequestDTO.getDealerId().longValue() > 0);
			boolean isCrew = (wareHouseRequestDTO.getCrewId() != null && wareHouseRequestDTO.getCrewId().longValue()>0);
			boolean isCustomer = (wareHouseRequestDTO.getCustomerId() != null && wareHouseRequestDTO.getCustomerId().longValue()>0);
			boolean isWarehouse = (wareHouseRequestDTO.getWarehouseId() != null && wareHouseRequestDTO.getWarehouseId().longValue()>0);
			boolean isWarehouseType = (wareHouseRequestDTO.getWarehouseTypeId() != null && wareHouseRequestDTO.getWarehouseTypeId().longValue()>0);
			boolean isElementTypeId = (wareHouseRequestDTO.getElementTypeId() != null && wareHouseRequestDTO.getElementTypeId().longValue()>0);

			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.serialized is null ");
			stringQuery.append(" and whe.notSerialized is not null ");
			stringQuery.append(" and whe.actualQuantity > 0.0 " );
			stringQuery.append(" and whe.warehouseId.warehouseType.isVirtual =:isVirtual " );
			//stringQuery.append(" and whe.warehouseId.dealerId.postalCode.city.state.country.id = :countryId " );
			stringQuery.append(" and whe.recordStatus.recordStatusCode = '").append(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()).append("'");

			if(isCustomer){
				stringQuery.append(" and whe.warehouseId.customerId.country.id = :countryId " );
				stringQuery.append(" and whe.warehouseId.customerId.id = :customerId ");
			}
			
			if(isDealer || isCrew){
				stringQuery.append(" and whe.warehouseId.dealerId.postalCode.city.state.country.id = :countryId " );
			}
			
			if(isDealer){
				stringQuery.append(" and (whe.warehouseId.dealerId.id = :dealerId or whe.warehouseId.dealerId.dealer.id = :dealerId) ");
			}

			if(isWarehouse){
				stringQuery.append(" and whe.warehouseId.id = :warehouseId ");
			}

			if(isWarehouseType){
				stringQuery.append(" and whe.warehouseId.warehouseType.id = :warehouseTypeId " );
			}

			if(isCrew){
				stringQuery.append(" and whe.warehouseId.crewId.id = :crewId " );
			}
			
			if(isElementTypeId){
				stringQuery.append(" and whe.notSerialized.element.elementType.id = :elementTypeId " );
			}

			String finalQuery = stringQuery.toString();
			Query query = session.createQuery(finalQuery);

			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append( finalQuery );
			Query countQuery = session.createQuery( stringCount.toString() );

			//query.setLong("countryId", wareHouseRequestDTO.getCountryId());
			//countQuery.setLong("countryId", wareHouseRequestDTO.getCountryId());
			query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
			countQuery.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());

			if(isDealer || isCrew || isCustomer){
				query.setLong("countryId", wareHouseRequestDTO.getCountryId());
				countQuery.setLong("countryId", wareHouseRequestDTO.getCountryId());
				
			}
			
			if(isCustomer){
				query.setLong("customerId", wareHouseRequestDTO.getCustomerId());
				countQuery.setLong("customerId", wareHouseRequestDTO.getCustomerId());
			}
			
			if(isDealer){
				query.setLong("dealerId", wareHouseRequestDTO.getDealerId());
				countQuery.setLong("dealerId", wareHouseRequestDTO.getDealerId());
			}

			if(isWarehouse){
				query.setLong("warehouseId", wareHouseRequestDTO.getWarehouseId());
				countQuery.setLong("warehouseId", wareHouseRequestDTO.getWarehouseId());
			}

			if(isWarehouseType){
				query.setLong("warehouseTypeId", wareHouseRequestDTO.getWarehouseTypeId());
				countQuery.setLong("warehouseTypeId", wareHouseRequestDTO.getWarehouseTypeId());
			}

			if(isCrew){
				query.setLong("crewId", wareHouseRequestDTO.getCrewId());
				countQuery.setLong("crewId", wareHouseRequestDTO.getCrewId());
			}
			
			if(isElementTypeId){
				query.setLong("elementTypeId", wareHouseRequestDTO.getElementTypeId());
				countQuery.setLong("elementTypeId", wareHouseRequestDTO.getElementTypeId());
			}

			//Paginacion
			Long recordQty = 0l;
			if( wareHouseRequestDTO.getRequestCollInfo() != null ){	              	
				recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult( wareHouseRequestDTO.getRequestCollInfo().getFirstResult() );
				query.setMaxResults( wareHouseRequestDTO.getRequestCollInfo().getMaxResults() );				
			}

			WareHouseElementResponse response = new WareHouseElementResponse();
			List<WarehouseElement> warehouseElements = query.list();
			if( wareHouseRequestDTO.getRequestCollInfo() != null )
				populatePaginationInfo( response, wareHouseRequestDTO.getRequestCollInfo().getPageSize(), wareHouseRequestDTO.getRequestCollInfo().getPageIndex(), warehouseElements.size(), recordQty.intValue() );
			response.setWareHouseElements( warehouseElements );

			return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getNotSerializedElementsLastByCriteria/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getNotSerializedElementsLastByCriteria/WarehouseElementDAO ==");
		}

	}
	
	@Override
	public WareHouseElementResponse getWarehouseElementsByFiltersAndIsSerializedLast(
			FilterSerializedElementDTO filterSerializedElement,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicia getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementDAO ==");
		try {
			Session session = super.getSession();
			boolean isDealer = (filterSerializedElement.getDealerId() != null && filterSerializedElement.getDealerId().longValue()>0);
			boolean isBranch = (filterSerializedElement.getBranchId() != null && filterSerializedElement.getBranchId().longValue()>0);
			boolean isWarehouseType = (filterSerializedElement.getWarehouseTypeId() != null && filterSerializedElement.getWarehouseTypeId().longValue()>0);
			boolean isWarehouse = (filterSerializedElement.getWarehouseId() != null && filterSerializedElement.getWarehouseId().longValue()>0);
			boolean isElementType = (filterSerializedElement.getElementTypeId()!=null&&filterSerializedElement.getElementTypeId().longValue()>0);
			boolean isSerialCode = (filterSerializedElement.getSerialCode() != null && !filterSerializedElement.getSerialCode().equals(""));
			

			StringBuffer stringQuery = new StringBuffer();				
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.notSerialized is null ");
			stringQuery.append(" and whe.serialized is not null ");
			stringQuery.append(" and whe.actualQuantity > 0.0 ");
			stringQuery.append(" and whe.warehouseId.warehouseType.isVirtual =:isVirtual ");
			stringQuery.append(" and whe.warehouseId.dealerId.postalCode.city.state.country.id = :countryId ");
			stringQuery.append(" and whe.recordStatus.recordStatusCode = '").append(CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity()).append("'");
			stringQuery.append(" and not exists (SELECT s.elementId FROM "+Serialized.class.getName()+"  s where s.serialized.elementId = whe.serialized.elementId ) ");
			
			
			if(isDealer){
				stringQuery.append(" and (whe.warehouseId.dealerId.id = :dealerId or whe.warehouseId.dealerId.dealer.id = :dealerId) ");
			}
			

			if(isBranch){
				stringQuery.append(" and whe.warehouseId.dealerId.id = :branchId ");
			}
			
			if(isWarehouse){
				stringQuery.append(" and whe.warehouseId.id = :warehouseId ");
			}
			
			if(isWarehouseType){
				stringQuery.append(" and whe.warehouseId.warehouseType.id = :warehouseTypeId ");
			}
			
			if(isElementType){
				stringQuery.append(" and whe.serialized.element.elementType.id = :elementTypeId ");
			}
			
			
			if(isSerialCode){
				stringQuery.append(" and (whe.serialized.serialCode= trim(:serialCode) OR EXISTS (select 1 from co.com.directv.sdii.model.pojo.Serialized s2 where s2.elementId = whe.serialized.serialized.elementId and s2.serialCode= trim(:serialCode)))  ");
			}

			String finalQuery = stringQuery.toString();
			Query query = session.createQuery(finalQuery);

			//Paginacion
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) ");	
			stringCount.append(finalQuery);
			Query countQuery = session.createQuery(stringCount.toString());

			query.setLong("countryId", filterSerializedElement.getCountryId());
			countQuery.setLong("countryId", filterSerializedElement.getCountryId());
			query.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());
			countQuery.setString("isVirtual", CodesBusinessEntityEnum.WAREHOUSE_TYPE_STATUS_NOT_VIRTUAL.getCodeEntity());

			
			if(isDealer){
				query.setLong("dealerId", filterSerializedElement.getDealerId());
				countQuery.setLong("dealerId", filterSerializedElement.getDealerId());
			}
			
			if(isBranch){
				query.setLong("branchId", filterSerializedElement.getBranchId());
				countQuery.setLong("branchId", filterSerializedElement.getBranchId());
			}
			
			if(isWarehouse){
				query.setLong("warehouseId", filterSerializedElement.getWarehouseId());
				countQuery.setLong("warehouseId", filterSerializedElement.getWarehouseId());
			}
			
			if(isWarehouseType){
				query.setLong("warehouseTypeId", filterSerializedElement.getWarehouseTypeId());
				countQuery.setLong("warehouseTypeId", filterSerializedElement.getWarehouseTypeId());
			}
			
			if(isElementType){
				query.setLong("elementTypeId", filterSerializedElement.getElementTypeId());
				countQuery.setLong("elementTypeId", filterSerializedElement.getElementTypeId());
			}
			if(isSerialCode){
				query.setString("serialCode", filterSerializedElement.getSerialCode().toUpperCase());
				countQuery.setString("serialCode", filterSerializedElement.getSerialCode().toUpperCase());
			}

			//Paginacion
			Long recordQty = 0L;
			if(requestCollInfo != null){	              	
				recordQty = (Long)countQuery.uniqueResult();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
			}

			WareHouseElementResponse response = new WareHouseElementResponse();
			List<WarehouseElement> warehouseElements = query.list();
			if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), warehouseElements.size(), recordQty.intValue());
			}
			response.setWareHouseElements(warehouseElements);

			return response;
		} catch (Throwable ex) {
			log.error("== Error en la operacion getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementDAO  ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByFiltersAndIsSerializedLast/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementBySerialAndCustomerCode(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementBySerialAndCustomerCode(String elementSerial, String customerCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.customerId.customerCode = :customerCode ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setString("customerCode", customerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getElementByLinkedSerialAndCustomerCode(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WarehouseElement getElementByLinkedSerialAndCustomerCode(String elementSerial, String customerCode) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select whe from  ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.serialized.serialized.serialCode = :elementSerial ");
			stringQuery.append("and whe.warehouseId.customerId.customerCode = :customerCode ");
			stringQuery.append("and whe.warehouseId.dealerId is null and whe.warehouseId.crewId is null ");
			stringQuery.append("and whe.recordStatus.recordStatusCode = :aRecordStatus ");
			
			Query query = session.createQuery(stringQuery.toString());
			query.setString("elementSerial", elementSerial.toUpperCase());
			query.setString("customerCode", customerCode);
			query.setString("aRecordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error en la operacion getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getElementBySerialAndCustomerCode/WarehouseElementDAO ==");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
		public WarehouseElement getWhSerializedElementsLastByElementID(
			Long elementId) throws DAOServiceException, DAOSQLException {
		log
		.debug("== Inicia getWhSerializedElementsQAWhAndLogisticOpe/WarehouseElementDAO ==");
		Session session = super.getSession();

		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" entity where entity.serialized.elementId = :elementId ");
			stringQuery.append(" and entity.recordStatus.recordStatusCode = :recordStatus");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("elementId", elementId);
			query.setString("recordStatus", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			return (WarehouseElement) query.uniqueResult();

		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina getWhSerializedElementsQAWhAndLogisticOpe/WarehouseElementDAO ==");
		}
	}
	
	/**
	 * Metodo: Consulta warehouseelements por pais y elementID
	 * @param serial Numero de serie
	 * @param countryId
	 * @return WarehouseElement Elementos encontrado
	 * @throws DAOServiceException
	 * @throws DAOSQLException 
	 */
	@Override
	public Long existsWarehouseElementByCountryAndElementID(
			Long elementID)
			throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
		Long recordQty = 0L;
		Session session = super.getSession();
		//WarehouseElement result = null;
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select count(*) ");
			stringQuery.append("from ");
			stringQuery.append(WarehouseElement.class.getName()+" whe ");
			boolean elementIdSpecified = false;
			
			if (elementID != null && elementID.longValue() > 0L) {
				elementIdSpecified = true;
				stringQuery.append(" where whe.serialized.elementId = :elementId ");
			}
			Query query = session.createQuery(stringQuery.toString());
			
			if (elementIdSpecified) {
				query.setLong("elementId", elementID);
			}
			
			recordQty = (Long)query.uniqueResult();
		} catch (Throwable ex) {
			log.error("== Error en existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
		}
		return recordQty;
	}

	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupInventory(Country country, List<String> listWarehouseTypes, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		Session session = super.getSession();
		try {
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) from (");	
			String wareHouseTypes = UtilsBusiness.stringListToOrInQuery(listWarehouseTypes, "WT.WH_TYPE_CODE");
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("	SELECT * FROM (	");
			stringQuery.append("	SELECT T1.CODIGO_ELEMENTO elementcode,	");
			stringQuery.append("	                 T1.NOMBRE_ELEMENTO elementName,	");
			stringQuery.append("	                 T1.TIPO_BODEGA warehouseTypeName,	");
			stringQuery.append("	                 T1.DEALER_NAME dealerName,	");
			stringQuery.append("	                 T1.warehouseName warehouseName,	");
			stringQuery.append("	                 COUNT (1) quantity,	");
			stringQuery.append("	                 'S' isSer	");
			stringQuery.append("	            FROM (SELECT ET.TYPE_ELEMENT_CODE CODIGO_ELEMENTO,	");
			stringQuery.append("	                         ET.TYPE_ELEMENT_NAME NOMBRE_ELEMENTO,	");
			stringQuery.append("	                         WT.WH_TYPE_NAME TIPO_BODEGA,	");
			stringQuery.append("	                         DEL.DEALER_NAME DEALER_NAME,	");
			stringQuery.append("	                         CASE	");
			stringQuery.append("	                            WHEN CR.ID IS NOT NULL	");
			stringQuery.append("	                            THEN	");
			stringQuery.append("	                                  DEL.DEPOT_CODE	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || DEL.DEALER_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || EMP.FIRST_NAME	");
			stringQuery.append("	                               || ' '	");
			stringQuery.append("	                               || EMP.LAST_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || WT.WH_TYPE_NAME	");
			stringQuery.append("	                            WHEN DEL.ID IS NOT NULL	");
			stringQuery.append("	                            THEN	");
			stringQuery.append("	                                  DEL.DEPOT_CODE	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || DEL.DEALER_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || WT.WH_TYPE_NAME	");
			stringQuery.append("	                         END	");
			stringQuery.append("	                            warehouseName	");
			stringQuery.append("	                    FROM WAREHOUSE_ELEMENTS WE	");
			stringQuery.append("	                         INNER JOIN WAREHOUSES WA	");
			stringQuery.append("	                            ON WA.ID = WE.WAREHOUSE_ID	");
			stringQuery.append("	                         INNER JOIN DEALERS DEL	");
			stringQuery.append("	                            ON DEL.ID = WA.DEALER_ID	");
			stringQuery.append("	                         INNER JOIN WAREHOUSE_TYPES WT	");
			stringQuery.append("	                            ON WT.ID = WA.WH_TYPE_ID	");
			stringQuery.append("	                         INNER JOIN SERIALIZED SER	");
			stringQuery.append("	                            ON WE.SER_ID = SER.ELEMENT_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENTS EL	");
			stringQuery.append("	                            ON EL.ID = SER.ELEMENT_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_TYPES ET	");
			stringQuery.append("	                            ON EL.ELEMENT_TYPE_ID = ET.ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_MODELS EM	");
			stringQuery.append("	                            ON EM.ID = ET.ELEMENT_MODEL_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_CLASSES EC	");
			stringQuery.append("	                            ON EC.ID = EM.ELEMENT_CLASS_ID	");
			stringQuery.append("	                         INNER JOIN RECORD_STATUS RS ON RS.ID = WE.RECORD_STATUS_ID	");
			stringQuery.append("	           				 INNER JOIN COUNTRIES COUNTRY  ON COUNTRY.ID = WA.COUNTRY_ID	");
			stringQuery.append("	                         LEFT OUTER JOIN CREWS cr	");
			stringQuery.append("	                            ON cr.id = WA.CREW_ID	");
			stringQuery.append("	                         LEFT OUTER JOIN EMPLOYEE_CREWS ec	");
			stringQuery.append("	                            ON (EC.CREW_ID = cr.id	");
			stringQuery.append("	                                AND EC.IS_RESPONSIBLE = :isResponsible)	");
			stringQuery.append("	                         LEFT OUTER JOIN EMPLOYEES emP	");
			stringQuery.append("	                            ON emP.id = EC.EMPLOYEE_ID	");
			stringQuery.append("	                   WHERE EL.IS_SERIALIZED = :isSerialized AND COUNTRY.ID = :countryId AND RS.RECORD_STATUS_CODE = :recordStatusCode	");
			stringQuery.append("	                   AND "+ wareHouseTypes +") T1	");
			stringQuery.append("	        GROUP BY T1.CODIGO_ELEMENTO,	");
			stringQuery.append("	                 T1.NOMBRE_ELEMENTO,	");
			stringQuery.append("	                 T1.TIPO_BODEGA,	");
			stringQuery.append("	                 T1.DEALER_NAME,	");
			stringQuery.append("	                 T1.WAREHOUSENAME	");
			stringQuery.append("	UNION ALL	");
			stringQuery.append("	SELECT ET.TYPE_ELEMENT_CODE elementcode,	");
			stringQuery.append("	       ET.TYPE_ELEMENT_NAME elementName,	");
			stringQuery.append("	       WT.WH_TYPE_NAME warehouseTypeName,	");
			stringQuery.append("	       DEL.DEALER_NAME dealerName,	");
			stringQuery.append("	       CASE	");
			stringQuery.append("	          WHEN CR.ID IS NOT NULL	");
			stringQuery.append("	          THEN	");
			stringQuery.append("	                DEL.DEPOT_CODE	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || DEL.DEALER_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || EMP.FIRST_NAME	");
			stringQuery.append("	             || ' '	");
			stringQuery.append("	             || EMP.LAST_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || WT.WH_TYPE_NAME	");
			stringQuery.append("	          WHEN DEL.ID IS NOT NULL	");
			stringQuery.append("	          THEN	");
			stringQuery.append("	                DEL.DEPOT_CODE	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || DEL.DEALER_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || WT.WH_TYPE_NAME	");
			stringQuery.append("	       END	");
			stringQuery.append("	          warehouseName,	");
			stringQuery.append("	          WE.ACTUAL_QUANTITY quantity,	");
			stringQuery.append("	          'N' isSer	");
			stringQuery.append("	  FROM WAREHOUSE_ELEMENTS WE	");
			stringQuery.append("	       INNER JOIN WAREHOUSES WA	");
			stringQuery.append("	          ON WA.ID = WE.WAREHOUSE_ID	");
			stringQuery.append("	       INNER JOIN DEALERS DEL	");
			stringQuery.append("	          ON DEL.ID = WA.DEALER_ID	");
			stringQuery.append("	       INNER JOIN WAREHOUSE_TYPES WT	");
			stringQuery.append("	          ON WT.ID = WA.WH_TYPE_ID	");
			stringQuery.append("	       INNER JOIN NOT_SERIALIZED nSER	");
			stringQuery.append("	          ON WE.NOT_SER_ID = nSER.ELEMENT_ID	");
			stringQuery.append("	       INNER JOIN ELEMENTS EL	");
			stringQuery.append("	          ON EL.ID = nSER.ELEMENT_ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_TYPES ET	");
			stringQuery.append("	          ON EL.ELEMENT_TYPE_ID = ET.ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_MODELS EM	");
			stringQuery.append("	          ON EM.ID = ET.ELEMENT_MODEL_ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_CLASSES EC	");
			stringQuery.append("	          ON EC.ID = EM.ELEMENT_CLASS_ID	");
			stringQuery.append("	       INNER JOIN RECORD_STATUS RS ON RS.ID = WE.RECORD_STATUS_ID	");
			stringQuery.append("	       INNER JOIN COUNTRIES COUNTRY  ON COUNTRY.ID = WA.COUNTRY_ID	");
			stringQuery.append("	       LEFT OUTER JOIN CREWS cr	");
			stringQuery.append("	          ON cr.id = WA.CREW_ID	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEE_CREWS ec	");
			stringQuery.append("	          ON (EC.CREW_ID = cr.id AND EC.IS_RESPONSIBLE = :isResponsible)	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEES emP	");
			stringQuery.append("	          ON emP.id = EC.EMPLOYEE_ID	");
			stringQuery.append("	 WHERE EL.IS_SERIALIZED = :isNotSerialized AND COUNTRY.ID = :countryId AND RS.RECORD_STATUS_CODE = :recordStatusCode	");
			stringQuery.append("     AND "+ wareHouseTypes);
			stringQuery.append("	)	");
			stringQuery.append("	ORDER BY dealerName, warehouseTypeName, elementName	");
			Query query = session.createSQLQuery(stringQuery.toString())
			.addScalar("elementcode")
			.addScalar("elementName")
			.addScalar("warehouseTypeName")
			.addScalar("dealerName")
			.addScalar("warehouseName")
			.addScalar("quantity", Hibernate.DOUBLE)
			.addScalar("isSer", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(InventoryElementGroupDTO.class));
			
			query.setString("isResponsible", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
			query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
			query.setString("isNotSerialized", CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("countryId", country.getId());
			
			Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append( stringQuery.toString()+")");        	
            	Query countQuery = session.createSQLQuery( stringCount.toString() );
            	countQuery.setString("isResponsible", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
            	countQuery.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
            	countQuery.setString("isNotSerialized", CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
            	countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
            	countQuery.setLong("countryId", country.getId());
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
        	}
			List<InventoryElementGroupDTO> listInventoryElementGroupDTO = query.list();	
			
			InventoryElementGroupDTOResponse response = new InventoryElementGroupDTOResponse();
        	
			response.setElementsGroup(listInventoryElementGroupDTO);
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listInventoryElementGroupDTO.size(), recordQty.intValue() );
        	}
        	
            return response;
			
		} catch (Throwable ex) {
			log.error("== Error en existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
		}
	}
	
	@Override
	public InventoryElementGroupDTOResponse getElementGroupInventoryWithDate(Country country, List<String> listWarehouseTypes, RequestCollectionInfo requestCollInfo)
			throws DAOServiceException, DAOSQLException {
		Session session = super.getSession();
		try {
			StringBuffer stringCount = new StringBuffer();
			stringCount.append("select count(*) from (");	
			String wareHouseTypes = UtilsBusiness.stringListToOrInQuery(listWarehouseTypes, "WT.WH_TYPE_CODE");
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("	SELECT * FROM (	");
			stringQuery.append("	SELECT T1.CODIGO_ELEMENTO elementcode,	");
			stringQuery.append("	                 T1.NOMBRE_ELEMENTO elementName,	");
			stringQuery.append("	                 T1.TIPO_BODEGA warehouseTypeName,	");
			stringQuery.append("	                 T1.DEALER_NAME dealerName,	");
			stringQuery.append("	                 T1.warehouseName warehouseName,	");
			stringQuery.append("	                 T1.MOVEMENTDATE MOVEMENTDATE,	");
			stringQuery.append("	                 COUNT (1) quantity,	");
			stringQuery.append("	                 'S' isSer	");
			stringQuery.append("	            FROM (SELECT ET.TYPE_ELEMENT_CODE CODIGO_ELEMENTO,	");
			stringQuery.append("	                         ET.TYPE_ELEMENT_NAME NOMBRE_ELEMENTO,	");
			stringQuery.append("	                         WT.WH_TYPE_NAME TIPO_BODEGA,	");
			stringQuery.append("	                         DEL.DEALER_NAME DEALER_NAME,	");
			stringQuery.append("	                          TRUNC (WE.MOVEMENT_DATE) MOVEMENTDATE,	");
			stringQuery.append("	                         CASE	");
			stringQuery.append("	                            WHEN CR.ID IS NOT NULL	");
			stringQuery.append("	                            THEN	");
			stringQuery.append("	                                  DEL.DEPOT_CODE	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || DEL.DEALER_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || EMP.FIRST_NAME	");
			stringQuery.append("	                               || ' '	");
			stringQuery.append("	                               || EMP.LAST_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || WT.WH_TYPE_NAME	");
			stringQuery.append("	                            WHEN DEL.ID IS NOT NULL	");
			stringQuery.append("	                            THEN	");
			stringQuery.append("	                                  DEL.DEPOT_CODE	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || DEL.DEALER_NAME	");
			stringQuery.append("	                               || ' - '	");
			stringQuery.append("	                               || WT.WH_TYPE_NAME	");
			stringQuery.append("	                         END	");
			stringQuery.append("	                            warehouseName	");
			stringQuery.append("	                    FROM WAREHOUSE_ELEMENTS WE	");
			stringQuery.append("	                         INNER JOIN WAREHOUSES WA	");
			stringQuery.append("	                            ON WA.ID = WE.WAREHOUSE_ID	");
			stringQuery.append("	                         INNER JOIN DEALERS DEL	");
			stringQuery.append("	                            ON DEL.ID = WA.DEALER_ID	");
			stringQuery.append("	                         INNER JOIN WAREHOUSE_TYPES WT	");
			stringQuery.append("	                            ON WT.ID = WA.WH_TYPE_ID	");
			stringQuery.append("	                         INNER JOIN SERIALIZED SER	");
			stringQuery.append("	                            ON WE.SER_ID = SER.ELEMENT_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENTS EL	");
			stringQuery.append("	                            ON EL.ID = SER.ELEMENT_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_TYPES ET	");
			stringQuery.append("	                            ON EL.ELEMENT_TYPE_ID = ET.ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_MODELS EM	");
			stringQuery.append("	                            ON EM.ID = ET.ELEMENT_MODEL_ID	");
			stringQuery.append("	                         INNER JOIN ELEMENT_CLASSES EC	");
			stringQuery.append("	                            ON EC.ID = EM.ELEMENT_CLASS_ID	");
			stringQuery.append("	                         INNER JOIN RECORD_STATUS RS ON RS.ID = WE.RECORD_STATUS_ID	");
			stringQuery.append("	           				 INNER JOIN COUNTRIES COUNTRY  ON COUNTRY.ID = WA.COUNTRY_ID	");
			stringQuery.append("	                         LEFT OUTER JOIN CREWS cr	");
			stringQuery.append("	                            ON cr.id = WA.CREW_ID	");
			stringQuery.append("	                         LEFT OUTER JOIN EMPLOYEE_CREWS ec	");
			stringQuery.append("	                            ON (EC.CREW_ID = cr.id	");
			stringQuery.append("	                                AND EC.IS_RESPONSIBLE = :isResponsible)	");
			stringQuery.append("	                         LEFT OUTER JOIN EMPLOYEES emP	");
			stringQuery.append("	                            ON emP.id = EC.EMPLOYEE_ID	");
			stringQuery.append("	                   WHERE EL.IS_SERIALIZED = :isSerialized AND COUNTRY.ID = :countryId AND RS.RECORD_STATUS_CODE = :recordStatusCode	");
			stringQuery.append("	                   AND "+ wareHouseTypes +") T1	");
			stringQuery.append("	        GROUP BY T1.CODIGO_ELEMENTO,	");
			stringQuery.append("	                 T1.NOMBRE_ELEMENTO,	");
			stringQuery.append("	                 T1.TIPO_BODEGA,	");
			stringQuery.append("	                 T1.DEALER_NAME,	");
			stringQuery.append("	                 T1.WAREHOUSENAME,	");
			stringQuery.append("	                 T1.MOVEMENTDATE	");
			stringQuery.append("	UNION ALL	");
			stringQuery.append("	SELECT ET.TYPE_ELEMENT_CODE elementcode,	");
			stringQuery.append("	       ET.TYPE_ELEMENT_NAME elementName,	");
			stringQuery.append("	       WT.WH_TYPE_NAME warehouseTypeName,	");
			stringQuery.append("	       DEL.DEALER_NAME dealerName,	");
			stringQuery.append("	       CASE	");
			stringQuery.append("	          WHEN CR.ID IS NOT NULL	");
			stringQuery.append("	          THEN	");
			stringQuery.append("	                DEL.DEPOT_CODE	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || DEL.DEALER_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || EMP.FIRST_NAME	");
			stringQuery.append("	             || ' '	");
			stringQuery.append("	             || EMP.LAST_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || WT.WH_TYPE_NAME	");
			stringQuery.append("	          WHEN DEL.ID IS NOT NULL	");
			stringQuery.append("	          THEN	");
			stringQuery.append("	                DEL.DEPOT_CODE	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || DEL.DEALER_NAME	");
			stringQuery.append("	             || ' - '	");
			stringQuery.append("	             || WT.WH_TYPE_NAME	");
			stringQuery.append("	       END	");
			stringQuery.append("	          warehouseName,	");
			stringQuery.append("	          TRUNC (WE.MOVEMENT_DATE) MOVEMENTDATE,	");
			stringQuery.append("	          WE.ACTUAL_QUANTITY quantity,	");
			stringQuery.append("	          'N' isSer	");
			stringQuery.append("	  FROM WAREHOUSE_ELEMENTS WE	");
			stringQuery.append("	       INNER JOIN WAREHOUSES WA	");
			stringQuery.append("	          ON WA.ID = WE.WAREHOUSE_ID	");
			stringQuery.append("	       INNER JOIN DEALERS DEL	");
			stringQuery.append("	          ON DEL.ID = WA.DEALER_ID	");
			stringQuery.append("	       INNER JOIN WAREHOUSE_TYPES WT	");
			stringQuery.append("	          ON WT.ID = WA.WH_TYPE_ID	");
			stringQuery.append("	       INNER JOIN NOT_SERIALIZED nSER	");
			stringQuery.append("	          ON WE.NOT_SER_ID = nSER.ELEMENT_ID	");
			stringQuery.append("	       INNER JOIN ELEMENTS EL	");
			stringQuery.append("	          ON EL.ID = nSER.ELEMENT_ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_TYPES ET	");
			stringQuery.append("	          ON EL.ELEMENT_TYPE_ID = ET.ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_MODELS EM	");
			stringQuery.append("	          ON EM.ID = ET.ELEMENT_MODEL_ID	");
			stringQuery.append("	       INNER JOIN ELEMENT_CLASSES EC	");
			stringQuery.append("	          ON EC.ID = EM.ELEMENT_CLASS_ID	");
			stringQuery.append("	       INNER JOIN RECORD_STATUS RS ON RS.ID = WE.RECORD_STATUS_ID	");
			stringQuery.append("	       INNER JOIN COUNTRIES COUNTRY  ON COUNTRY.ID = WA.COUNTRY_ID	");
			stringQuery.append("	       LEFT OUTER JOIN CREWS cr	");
			stringQuery.append("	          ON cr.id = WA.CREW_ID	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEE_CREWS ec	");
			stringQuery.append("	          ON (EC.CREW_ID = cr.id AND EC.IS_RESPONSIBLE = :isResponsible)	");
			stringQuery.append("	       LEFT OUTER JOIN EMPLOYEES emP	");
			stringQuery.append("	          ON emP.id = EC.EMPLOYEE_ID	");
			stringQuery.append("	 WHERE EL.IS_SERIALIZED = :isNotSerialized AND COUNTRY.ID = :countryId AND RS.RECORD_STATUS_CODE = :recordStatusCode	");
			stringQuery.append("     AND "+ wareHouseTypes);
			stringQuery.append("	)	");
			stringQuery.append("	ORDER BY dealerName, warehouseTypeName, elementName, MOVEMENTDATE asc	");
			Query query = session.createSQLQuery(stringQuery.toString())
			.addScalar("elementcode")
			.addScalar("elementName")
			.addScalar("warehouseTypeName")
			.addScalar("dealerName")
			.addScalar("warehouseName")
			.addScalar("quantity", Hibernate.DOUBLE)
			.addScalar("isSer", Hibernate.STRING)
			.addScalar("movementDate", Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(InventoryElementGroupDTO.class));
			
			query.setString("isResponsible", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
			query.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
			query.setString("isNotSerialized", CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
			query.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("countryId", country.getId());
			
			Long recordQty = 0L;
        	if( requestCollInfo != null ){	  
            	stringCount.append( stringQuery.toString()+")");        	
            	Query countQuery = session.createSQLQuery( stringCount.toString() );
            	countQuery.setString("isResponsible", CodesBusinessEntityEnum.EMPLOYEE_CREW_RESPONSABLE.getCodeEntity());
            	countQuery.setString("isSerialized", CodesBusinessEntityEnum.ELEMENT_IS_SERIALIZED.getCodeEntity());
            	countQuery.setString("isNotSerialized", CodesBusinessEntityEnum.ELEMENT_IS_NOT_SERIALIZED.getCodeEntity());
            	countQuery.setString("recordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
            	countQuery.setLong("countryId", country.getId());
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();			
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
        	}
			List<InventoryElementGroupDTO> listInventoryElementGroupDTO = query.list();	
			
			InventoryElementGroupDTOResponse response = new InventoryElementGroupDTOResponse();
        	
			response.setElementsGroup(listInventoryElementGroupDTO);
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), listInventoryElementGroupDTO.size(), recordQty.intValue() );
        	}
        	
            return response;
			
		} catch (Throwable ex) {
			log.error("== Error en existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina existsWarehouseElementByCountryAndElementID/WarehouseElementDAO ==");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.WarehouseElementDAOLocal#getWarehouseElementsByCustomerIdAndElementTypeId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean existElementInCustomer(
			Long customerId, String serialCode)throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia getWarehouseElementsByCustomerIdAndElementTypeId/WarehouseElementDAO ==");
		try {
			Session session = super.getSession();
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select count (whe.id) from ");
			stringQuery.append(WarehouseElement.class.getName());
			stringQuery.append(" whe where whe.recordStatus.recordStatusCode = :aRecordStatusCode ");
			stringQuery.append(" and whe.warehouseId.customerId is not null and whe.warehouseId.customerId.id = :aCustomerId and whe.serialized.serialCode = :serialCode");
			Query query = session.createQuery(stringQuery.toString());	
			query.setString("aRecordStatusCode", CodesBusinessEntityEnum.RECORD_STATUS_LAST.getCodeEntity());
			query.setLong("aCustomerId", customerId);
			query.setString("serialCode", serialCode);
			return ((Long)query.uniqueResult())>0L;
		} catch (Throwable ex) {
			log.error("== Error ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getWarehouseElementsByCustomerIdAndElementTypeId/WarehouseElementDAO ==");
		}
	}
	
}

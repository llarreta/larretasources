package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.collection.MovCmdQueueDTOResponse;
import co.com.directv.sdii.model.pojo.Adjustment;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Customer;
import co.com.directv.sdii.model.pojo.DocumentClass;
import co.com.directv.sdii.model.pojo.IbsElementStatus;
import co.com.directv.sdii.model.pojo.ImportLog;
import co.com.directv.sdii.model.pojo.MovCmdConfig;
import co.com.directv.sdii.model.pojo.MovCmdQueue;
import co.com.directv.sdii.model.pojo.MovCmdStatus;
import co.com.directv.sdii.model.pojo.Reference;
import co.com.directv.sdii.model.pojo.Serialized;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.pojo.Warehouse;
import co.com.directv.sdii.model.pojo.WorkOrder;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal;
import co.com.directv.sdii.reports.dto.MovCmdQueueDTO;
import co.com.directv.sdii.reports.dto.MovCmdQueueFilterDTO;

/**
 * A data access object (DAO) providing persistence and search support for
 * MovCmdQueue entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see co.com.directv.sdii.persistence.MovCmdQueue
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="MovCmdQueueDAOLocal",mappedName="ejb/MovCmdQueueDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovCmdQueueDAO extends BaseDao implements MovCmdQueueDAOLocal {
	private static final Log log = LogFactory.getLog(MovCmdQueueDAO.class);
	// property constants
	public static final String CREATION_DATE = "creationDate";
	public static final String EXECUTE_DATE = "executeDate";

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#save(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(MovCmdQueue transientInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio save/MovCmdQueueDAO ==");
		try {
			Session session = this.getSession();
			session.save(transientInstance);
			this.doFlush(session);
		} catch (Throwable re) {
			log.error("== Error save/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina save/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#delete(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(MovCmdQueue persistentInstance) throws DAOServiceException, DAOSQLException  {
    	log.debug("== Inicio delete/MovCmdQueueDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from MovCmdQueue entity where entity.id = :anEntityId");
			query.setLong("anEntityId", persistentInstance.getId());
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el MovCmdQueue ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina delete/MovCmdQueueDAO ==");
		}
	}
    
    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#delete(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteSerializedMovCmdQueueByElementId(Long serializedElementID) throws DAOServiceException, DAOSQLException  {
    	log.debug("== Inicio deleteSerializedMovCmdQueueByElementId/MovCmdQueueDAO ==");
		Session session = super.getSession();
		try {
			Query query = session
			.createQuery("delete from "+MovCmdQueue.class.getName()+" entity where entity.serialized.elementId = :serializedElementID");
			query.setLong("serializedElementID", serializedElementID);
			query.executeUpdate();
			super.doFlush(session);
		} catch (Throwable ex) {
			log.debug("== Error eliminando el MovCmdQueue ==");
			throw this.manageException(ex);
		} finally {
			log
			.debug("== Termina deleteSerializedMovCmdQueueByElementId/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findById(java.math.BigDecimal)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdQueue findById(Long id) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findById/MovCmdQueueDAO ==");
		try {
			MovCmdQueue instance = (MovCmdQueue) getSession().get(
					"co.com.directv.sdii.model.pojo.MovCmdQueue", id);
			return instance;
		} catch (Throwable re) {
			log.error("== Error findById/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findById/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findByExample(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueue> findByExample(MovCmdQueue instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findByExample/MovCmdQueueDAO ==");
		try {
			List<MovCmdQueue> results = getSession().createCriteria("co.com.directv.sdii.model.pojo.MovCmdQueue").add(Example.create(instance)).list();
			return results;
		} catch (Throwable re) {
			log.error("== Error findByExample/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByExample/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findByProperty(java.lang.String, java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueue> findByProperty(String propertyName, Object value) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findByProperty/MovCmdQueueDAO ==");
		try {
			String queryString = "from MovCmdQueue as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findByProperty/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByProperty/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findByCreationDate(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueue> findByCreationDate(Object creationDate) throws DAOServiceException, DAOSQLException  {
		return findByProperty(CREATION_DATE, creationDate);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findByExecuteDate(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueue> findByExecuteDate(Object executeDate) throws DAOServiceException, DAOSQLException  {
		return findByProperty(EXECUTE_DATE, executeDate);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#findAll()
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<MovCmdQueue> findAll() throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio findAll/MovCmdQueueDAO ==");
		try {
			String queryString = "from MovCmdQueue";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
			log.error("== Error findAll/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findAll/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#merge(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdQueue merge(MovCmdQueue detachedInstance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio merge/MovCmdQueueDAO ==");
		try {
			MovCmdQueue result = (MovCmdQueue) getSession().merge(
					detachedInstance);
			return result;
		} catch (Throwable re) {
			log.error("== Error merge/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina merge/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#attachDirty(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachDirty(MovCmdQueue instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachDirty/MovCmdQueueDAO ==");
		try {
			getSession().saveOrUpdate(instance);
		} catch (Throwable re) {
			log.error("== Error attachDirty/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachDirty/MovCmdQueueDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdQueueDAOLocal#attachClean(co.com.directv.sdii.model.pojo.MovCmdQueue)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachClean(MovCmdQueue instance) throws DAOServiceException, DAOSQLException  {
        log.debug("== Inicio attachClean/MovCmdQueueDAO ==");
		try {
			getSession().lock(instance, LockMode.NONE);
		} catch (Throwable re) {
			log.error("== Error attachClean/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachClean/MovCmdQueueDAO ==");
		}
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal#findByStatus(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatus(String statusCode) throws DAOServiceException, DAOSQLException  {
    	List<MovCmdQueue> results = null;
        log.debug("== Inicio findByStatus/MovCmdQueueDAO ==");
		try {
			String queryString = "from MovCmdQueue as model where model.movCmdStatus.cmdStatusCode = ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setString(0, statusCode);
			results = queryObject.list(); 
			return results;
		} catch (Throwable re) {
			log.error("== Error findByStatus/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByStatus/MovCmdQueueDAO ==");
		}
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal#findByStatusAndPage(java.lang.String, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPage(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException  {
    	List<MovCmdQueue> results = null;
        log.debug("== Inicio findByStatusAndPage/MovCmdQueueDAO ==");
		try {
			if(log.isDebugEnabled()){
    			log.debug("Se consultarán elementos para notificar cambios de estado, la cantidad es: " + pageSize);
    		}
			StringBuffer queryString = new StringBuffer();
			queryString.append(" from " + MovCmdQueue.class.getName() + " as model ");
			queryString.append(" where model.movCmdStatus.cmdStatusCode = :aCmdStatusCode ");
			queryString.append("   and model.country.id = :aCountryId ");
			queryString.append(" order by model.id ");
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setString("aCmdStatusCode", statusCode); //MOVEMENT_COMAND_STATUS  2	02	Pendiente
			queryObject.setLong("aCountryId", countryId);
			queryObject.setMaxResults(pageSize.intValue());
			results = queryObject.list(); 
			return results;
		} catch (Throwable re) {
			log.error("== Error findByStatusAndPage/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByStatusAndPage/MovCmdQueueDAO ==");
		}
    }

    
    
	/**
	 * Req-0098 - Paralelismo de Inventarios
	 * Metodo: permite almacenar los datos devueltos en una lista de objetos MovCmdQueue
	 * @param request Lista de objectos retornados por la consulta findByStatusAndPageParallel
	 * @return List<ResponseSearchKpiResultsDTO> lista que encapsula los datos de la consulta 
	 * @author ialessan
	 */
    
	private List<MovCmdQueue> fillMovCmdQueueList(List<Object[]> request){
		log.debug("== Inicio fillMovCmdQueueList/MovCmdQueueDAO ==");
		List<MovCmdQueue> response=new ArrayList<MovCmdQueue>();
		for (Object[] object : request) {
			/*
			Long dealerId=((BigDecimal)object[0]).longValue(); 
			Long dealerCode=((BigDecimal)object[1]).longValue();
			String depotCode=((String)object[2]);
			String dealerName=((String)object[3]);
			String codeKpi="";
			String mounth="";
			String year="";
			Double valueKpi=0D;
			Double valueGoal=0D;
			*/
			/*
				private Long id;
				private MovCmdConfig movCmdConfig;
				private Serialized serialized;
				private MovCmdStatus movCmdStatus;
				private Date creationDate;
				private Date executeDate;
				private Warehouse sourceWarehouse;
				private Warehouse targetWarehouse;
				private String typeComunication;
				private Serialized serializedLinked;
				private DocumentClass documentClass;
				private Adjustment adjustment;
				private Reference reference;
				private ImportLog importLog;
				private WorkOrder workOrder;
				private String isManagment;
				private User managmentUserId;
				private String commentManagment;
				private Customer customer;
				private Country country;
				private String typeWorkOrderForMovInv;
				private Long historyEventId;
			 */
			
			Long id= (Long)object[0];
			
			//MovCmdConfig 	movCmdConfig;//MOV_CMD_CONFIG_ID
			Long MOV_CMD_CONFIG_ID=(Long)object[1];
			
			//Serialized 		serialized;//
			Long SER_ID=(Long)object[2];
			
			//MovCmdStatus 	movCmdStatus;//
			Long MOV_CMD_STATUS_ID=(Long)object[3];
			
			//Date 			creationDate;//
			Date CREATION_DATE_=(Date)object[4];
			
			//Date 			executeDate;//
			Date EXECUTE_DATE_=(Date)object[5];
			
			//Warehouse 		sourceWarehouse;//
			Long SOURCE_WH_ID=(Long)object[6];
			
			//Warehouse 		targetWarehouse;//
			Long TARGET_WH_ID=(Long)object[7];
			
			//String 			typeComunication;//
			String TYPE_COMUNICATION=(String)object[8];
			
			//Serialized 		serializedLinked;//
			Long SER_ID_LINKED=(Long)object[9];
			
			//DocumentClass 	documentClass;//
			Long DOCUMENT_CLASS_ID=(Long)object[10];
			
			//Adjustment 		adjustment;//
			Long ADJUSMENT_ID=(Long)object[11];
			
			//Reference 		reference;//
			Long REFERENCE_ID=(Long)object[12];
			
			//ImportLog 		importLog;//
			Long IMPORT_LOG_ID=(Long)object[13];
			
			//WorkOrder 		workOrder;//
			Long  WORK_ORDER_ID=(Long)object[14];
			
			//String 			isManagment;//
			String  IS_MANAGEMENT=(String)object[15];
			
			//User 			managmentUserId;//
			Long MANAGEMENT_USER_ID=(Long)object[16];
			
			//String 			commentManagment;//
			String COMMENT_MANAGEMENT=(String)object[17];			
			
			//Customer 		customer;//
			Long CUSTOMER_ID=(Long)object[18];
			
			//Country 		country;//
			Long COUNTRY_ID=(Long)object[19];
			
			//String 			typeWorkOrderForMovInv;//
			String TYPE_WORK_ORDER_FOR_MOV_INV=(String)object[20];
			
			//Long 			historyEventId;
			Long HISTORY_EVENT_ID=(Long)object[21];

			//ResponseSearchKpiResultsDTO responseSearchKpiResultsDTO = new ResponseSearchKpiResultsDTO(dealerId,dealerCode,depotCode,dealerName,codeKpi,mounth,year,valueKpi,valueGoal);
			MovCmdQueue movCmdQueue = new MovCmdQueue();
			movCmdQueue.setMovCmdConfig(new MovCmdConfig());
			movCmdQueue.setSerialized(new Serialized());
			movCmdQueue.setMovCmdStatus(new MovCmdStatus());
			movCmdQueue.setSourceWarehouse(new Warehouse());
			movCmdQueue.setTargetWarehouse(new Warehouse());
			movCmdQueue.setSerializedLinked(new Serialized());
			movCmdQueue.setDocumentClass(new DocumentClass());
			movCmdQueue.setAdjustment(new Adjustment());
			movCmdQueue.setReference(new Reference());
			movCmdQueue.setImportLog(new ImportLog());
			movCmdQueue.setWorkOrder(new WorkOrder());
			movCmdQueue.setManagmentUserId(new User());
			movCmdQueue.setCustomer(new Customer());
			movCmdQueue.setCountry(new Country());
			
			
			movCmdQueue.setId(id);
			movCmdQueue.getMovCmdConfig().setId(MOV_CMD_CONFIG_ID); 
			movCmdQueue.getSerialized().setElementId(SER_ID); //MOVEMENT_COMAND_QUEUES.SER_ID = SERIALIZED.ELEMENT_ID
			movCmdQueue.getMovCmdStatus().setId(MOV_CMD_STATUS_ID);
			movCmdQueue.setCreationDate(CREATION_DATE_);
			movCmdQueue.setExecuteDate(EXECUTE_DATE_);
			movCmdQueue.getSourceWarehouse().setId(SOURCE_WH_ID);
			movCmdQueue.getTargetWarehouse().setId(TARGET_WH_ID);
			movCmdQueue.setTypeComunication(TYPE_COMUNICATION);
			movCmdQueue.getSerializedLinked().setElementId(SER_ID_LINKED);
			movCmdQueue.getDocumentClass().setId(DOCUMENT_CLASS_ID);
			movCmdQueue.getAdjustment().setId(ADJUSMENT_ID);
			movCmdQueue.getReference().setId(REFERENCE_ID);
			movCmdQueue.getImportLog().setId(IMPORT_LOG_ID);
			movCmdQueue.getWorkOrder().setId(WORK_ORDER_ID);
			movCmdQueue.setIsManagment(IS_MANAGEMENT);
			movCmdQueue.getManagmentUserId().setId(MANAGEMENT_USER_ID);
			movCmdQueue.setCommentManagment(COMMENT_MANAGEMENT);
			movCmdQueue.getCustomer().setId(CUSTOMER_ID);
			movCmdQueue.getCountry().setId(COUNTRY_ID);
			movCmdQueue.setTypeWorkOrderForMovInv(TYPE_WORK_ORDER_FOR_MOV_INV);
			movCmdQueue.setHistoryEventId(HISTORY_EVENT_ID);
			response.add(movCmdQueue);
		}
		log.debug("== Termina fillMovCmdQueueList/MovCmdQueueDAO ==");
		return response;
		
	}
	
	/*
	 * Req-0098 - Paralelismo de Inventarios
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallelSQL(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException  {
    	//List<MovCmdQueue> results = null;
    	List<MovCmdQueue> MovCmdQueueList = null;
        log.debug("== Inicio findByStatusAndPageParallelSQL/MovCmdQueueDAO ==");
		try {
			if(log.isDebugEnabled()){
    			log.debug("Se consultarán elementos para notificar cambios de estado, la cantidad es: " + pageSize);
    		}
			
			StringBuffer queryString = new StringBuffer();/*
			queryString.append(" from " + MovCmdQueue.class.getName() + " as model ");
			queryString.append(" where model.movCmdStatus.cmdStatusCode = :aCmdStatusCode ");
			queryString.append(" and model.country.id = :aCountryId ");
			queryString.append(" order by model.id "); VER SI ES NECESARIO EL ORDER BY!
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setString("aCmdStatusCode", statusCode); //MOVEMENT_COMAND_STATUS  2	02	Pendiente
			queryObject.setLong("aCountryId", countryId);
			queryObject.setMaxResults(pageSize.intValue());
			results = queryObject.list();
			*/
			
			//Criteria criteria =getSession().createCriteria("co.com.directv.sdii.model.pojo.MovCmdQueue");
			
			
			//queryBuffer.append(" select new co.com.directv.sdii.model.pojo.MovCmdQueue ( ");
			queryString.append(" SELECT *                                                         ");
			queryString.append(" FROM MOVEMENT_COMAND_QUEUES MCQ                                  ");
			queryString.append(" INNER JOIN (                                                     ");
			queryString.append("               SELECT min(MCQ2.ID) MIN_ID                         ");
			queryString.append("               FROM MOVEMENT_COMAND_QUEUES MCQ2                   ");
			queryString.append("               WHERE  MCQ2.MOV_CMD_STATUS_ID = :aCmdStatusCode    ");
			queryString.append(" 			   AND    MCQ2.COUNTRY_ID        = :aCountryId        ");			
			queryString.append("               GROUP BY MCQ2.SER_ID) MINIMOS_ID                   ");
			queryString.append(" ON MCQ.ID = MINIMOS_ID.MIN_ID                                    ");

			//Query queryObject = getSession().createQuery(queryString.toString());
			SQLQuery queryObject = getSession().createSQLQuery(queryString.toString())
					.addScalar("ID", Hibernate.LONG)
					.addScalar("MOV_CMD_CONFIG_ID", Hibernate.LONG)
					.addScalar("SER_ID", Hibernate.LONG)
					.addScalar("MOV_CMD_STATUS_ID", Hibernate.LONG)
					.addScalar("CREATION_DATE", Hibernate.DATE)
					.addScalar("EXECUTE_DATE", Hibernate.DATE)
					.addScalar("SOURCE_WH_ID", Hibernate.LONG)
					.addScalar("TARGET_WH_ID", Hibernate.LONG)
					.addScalar("TYPE_COMUNICATION", Hibernate.STRING)
					.addScalar("SER_ID_LINKED", Hibernate.LONG)
					.addScalar("DOCUMENT_CLASS_ID", Hibernate.LONG)
					.addScalar("ADJUSMENT_ID", Hibernate.LONG)
					.addScalar("REFERENCE_ID", Hibernate.LONG)
					.addScalar("IMPORT_LOG_ID", Hibernate.LONG)
					.addScalar("WORK_ORDER_ID", Hibernate.LONG)
					.addScalar("IS_MANAGEMENT", Hibernate.STRING)
					.addScalar("MANAGEMENT_USER_ID", Hibernate.LONG)
					.addScalar("COMMENT_MANAGEMENT", Hibernate.STRING)
					.addScalar("CUSTOMER_ID", Hibernate.LONG)
					.addScalar("COUNTRY_ID", Hibernate.LONG)
					.addScalar("TYPE_WORK_ORDER_FOR_MOV_INV", Hibernate.STRING)
					.addScalar("HISTORY_EVENT_ID", Hibernate.LONG);
			
			queryObject.setString("aCmdStatusCode", statusCode); //MOVEMENT_COMAND_STATUS  2	02	Pendiente
			queryObject.setLong("aCountryId", countryId);
			queryObject.setMaxResults(pageSize.intValue());
			//results = queryObject.list();
			//results= (List<MovCmdQueue>)queryObject.list();
			List<Object[]> results = queryObject.list();
			MovCmdQueueList = fillMovCmdQueueList(results);		
			return MovCmdQueueList;
		} catch (Throwable re) {
			log.error("== Error findByStatusAndPageParallelSQL/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
			log.debug("== Termina findByStatusAndPageParallelSQL/MovCmdQueueDAO ==");
		}
    }    
    
	
	/*
	 * Req-0098 - Paralelismo de Inventarios
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallel(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException  {
    	List<MovCmdQueue> results = null;
        log.debug("== Inicio findByStatusAndPageParallel/MovCmdQueueDAO ==");
		try {
			if(log.isDebugEnabled()){
    			log.debug("Se consultarán elementos para notificar cambios de estado, la cantidad es: " + pageSize);
    		}
			StringBuffer queryString = new StringBuffer();
			
			queryString.append(" from " + MovCmdQueue.class.getName() + " as model                    		    ");
		  //queryString.append(" inner join (    select  min(model2.id) 									    ");
		    queryString.append(" where model.id in (    select  min(model2.id) 									");
		  //queryString.append(" where model.id in (    :aIdMinimosPorGrupo )          							");
			
			queryString.append("                 from "+ MovCmdQueue.class.getName() + " as model2 				");
			queryString.append("                 where model2.movCmdStatus.cmdStatusCode = :aCmdStatusCode 		");
			queryString.append("                 and model2.country.id = :aCountryId 							");
			queryString.append("                 group by model2.serialized.elementId ) 						");
		  //queryString.append(" order by model.id ");
			
			Query queryObject = getSession().createQuery(queryString.toString());
			queryObject.setString("aCmdStatusCode", statusCode); //MOVEMENT_COMAND_STATUS  2	02	Pendiente
			queryObject.setLong("aCountryId", countryId);
			queryObject.setMaxResults(pageSize.intValue());
			results = queryObject.list(); 
			return results;
			
		} catch (Throwable re) {
			log.error("== Error findByStatusAndPageParallel/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByStatusAndPageParallel/MovCmdQueueDAO ==");
		}
    }    
    
	
	/*
	 * Req-0098 - Paralelismo de Inventarios
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovCmdQueue> findByStatusAndPageParallel2(String statusCode,Long pageSize,Long countryId) throws DAOServiceException, DAOSQLException  {
    	List<MovCmdQueue> results = new ArrayList();
    	Date startDate = new Date ();
    	//Timestamp fechayhora = new Timestamp(date.getTime());
    	
        log.debug("== Inicio findByStatusAndPageParallel2/MovCmdQueueDAO a las "+  new Timestamp(startDate.getTime())+" ==");
		try {
			if(log.isDebugEnabled()){
    			log.debug("Se consultarán elementos para notificar movimientos de inventario, la cantidad es: " + pageSize);
    		}
			
			List<Object[]> minIdPerGroupList = findMinIdPerGroup( statusCode, countryId);
			if (!minIdPerGroupList.isEmpty() ){
				StringBuffer queryString = new StringBuffer();				
				queryString.append(" from " + MovCmdQueue.class.getName() + " as model                    		    ");
			    queryString.append(" where model.id in (    :aIdMinimosPorGrupo )          							");
				Query queryObject = getSession().createQuery(queryString.toString());				
				queryObject.setParameterList("aIdMinimosPorGrupo", minIdPerGroupList);				
				queryObject.setMaxResults(pageSize.intValue());							
				results = queryObject.list();
			}
			
			log.debug("Se encontraron "+results.size()+" registros.");
			return results;
			
		} catch (Throwable re) {
			log.error("== Error findByStatusAndPageParallel2/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
			Date endDate = new Date();
	        log.debug("== Termina findByStatusAndPageParallel2/MovCmdQueueDAO a las "+  new Timestamp(endDate.getTime()) + " y dif: "+ (endDate.getTime() - startDate.getTime())  + " milisegundos ==");
		}
    }    

	
	/*
	 * Req-0098 - Paralelismo de Inventarios
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Object[]> findMinIdPerGroup(String statusCode,Long countryId) throws DAOServiceException, DAOSQLException {
    	Date date = new Date ();
        log.debug("== Inicio findMinIdPerGroup/MovCmdQueueDAO a las "+  new Timestamp(date.getTime())+" ==");
        
		try {
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("               SELECT min(MCQ2.ID) MIN_ID                         ");
			queryString.append("               FROM MOVEMENT_COMAND_QUEUES MCQ2                   ");
			queryString.append("               WHERE  MCQ2.MOV_CMD_STATUS_ID = :aCmdStatusCode    ");
			queryString.append(" 			   AND    MCQ2.COUNTRY_ID        = :aCountryId        ");			
			queryString.append("               GROUP  BY MCQ2.SER_ID                              ");

			SQLQuery queryObject = getSession().createSQLQuery(queryString.toString()).addScalar("MIN_ID", Hibernate.LONG);

			queryObject.setString("aCmdStatusCode", statusCode); //MOVEMENT_COMAND_STATUS  2	02	Pendiente
			queryObject.setLong("aCountryId", countryId);
			
			List<Object[]> results = queryObject.list();
			
            return results;
				
		} catch (Throwable re) {
			log.error("== Error findMinIdPerGroup/MovCmdQueueDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findMinIdPerGroup/MovCmdQueueDAO a las "+  new Timestamp(date.getTime())+" ==");
		}
    }    
    
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public MovCmdQueue getMovCmdQueueByElementId(boolean isSerialized, Long elementId) throws DAOServiceException, DAOSQLException  {
    	log.debug("== Inicia getMovCmdQueueByElementId/MovCmdQueueDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(MovCmdQueue.class.getName());
			stringQuery.append(" mcq where ");
			if(!isSerialized){
				stringQuery.append(" mcq.notSerialized.elementId = :aElementId");
			}
			if(isSerialized){
				stringQuery.append(" mcq.serialized.elementId = :aElementId");
			}
			
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("aElementId", elementId);

			List<MovCmdQueue> whEs = query.list();
			
			if(whEs.isEmpty()){
				return null;
			}
			return (MovCmdQueue) whEs.get(0);

		} catch (Throwable ex) {
			log.error("== Error getMovCmdQueueByElementId/MovCmdQueueDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getMovCmdQueueByElementId/MovCmdQueueDAO ==");
		}
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.persistence.dao.stock.MovCmdQueueDAOLocal#getIbsElementStatusByCodeAndCountry(java.lang.String, java.lang.String)
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public IbsElementStatus getIbsElementStatusByCodeAndCountry(String ibsElementStatusCode,String countryCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicia getIbsElementStatusByCode/MovCmdQueueDAO ==");
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("from ");
			stringQuery.append(IbsElementStatus.class.getName());
			stringQuery.append(" entity where ");
			stringQuery.append(" entity.ibsElementStatusCode = :ibsElementStatusCode ");
			stringQuery.append("and entity.country.countryCode = :countryCode ");
			Query query = session.createQuery(stringQuery.toString());
			query.setString("ibsElementStatusCode", ibsElementStatusCode);
			query.setString("countryCode", countryCode);
			return (IbsElementStatus) query.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error getIbsElementStatusByCode/MovCmdQueueDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina getIbsElementStatusByCode/MovCmdQueueDAO ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdQueueDTOResponse getMovementQueueHspToIbsByFilter(MovCmdQueueFilterDTO dto, Long countryId,
			RequestCollectionInfo requestCollInfo) throws DAOServiceException,
			DAOSQLException {		log.debug("== Inicio getMovementQueueHspToIbsByFilter/MovCmdQueueDAO ==");
        Session session = super.getSession();
        
        try {        	
        	StringBuffer stringCount = new StringBuffer();
        	StringBuffer stringQuery = new StringBuffer();
        	StringBuffer stringBody = new StringBuffer();
        	
        	
        	boolean isSerialSpecificated = dto.getSerialCode()!=null && dto.getSerialCode().length()>0;
        	boolean isStatusSpecificated = dto.getStatus() !=null && dto.getStatus().length()>0;
        	boolean isCommentSpecificated = dto.getIsManagment() !=null && dto.getIsManagment().length()>0;
        	boolean isDocumentTypeSpecificated = dto.getDocumentType() !=null && dto.getDocumentType().length()>0;
        	boolean isDocumentNumberSpecificated = dto.getDocumentNumber() !=null && dto.getDocumentNumber().longValue()>0;
        	boolean isCustomerSpecificated = dto.getCustomerCodeIbs() !=null && dto.getCustomerCodeIbs().longValue()>0;
        	boolean isCreationDateIni = dto.getInitialDateCreation() !=null;
        	boolean isCreationDateFin = dto.getFinalDateCreation() !=null;
        
    		stringBody.append("select");
    		stringBody.append("  mcq.ID as id,");
    		stringBody.append("  mcs.CMD_STATUS_NAME as status,");
    		stringBody.append("  mcq.CREATION_DATE as creationDate,");
    		stringBody.append("  mcq.EXECUTE_DATE as executeDate,");
    		stringBody.append("  ser2.SERIAL_CODE as serial,");
    		stringBody.append("  ser1.SERIAL_CODE as serialLinked, ");
    		stringBody.append("  WH_SOURCE.ID as whIdSource,");
    		stringBody.append("  (select");
    		stringBody.append("   wh.ID ");
    		stringBody.append("  from");
    		stringBody.append("   WAREHOUSES wh ");
    		stringBody.append("  where");
    		stringBody.append("   wh.ID=mcq.TARGET_WH_ID) as whIdTarget,");
    		stringBody.append("  nvl(dc.DOCUMENT_CLASS_NAME,");
    		stringBody.append("  '')||case ");
    		stringBody.append("  when dc.DOCUMENT_CLASS_NAME is null then ' ' ");
    		stringBody.append("  else ' / ' end||nvl(mcq.IMPORT_LOG_ID,");
    		stringBody.append("  '')||nvl(mcq.REFERENCE_ID,");
    		stringBody.append("  '')||nvl(mcq.ADJUSMENT_ID,");
    		stringBody.append("  '')||nvl(wo.WO_CODE,");
    		stringBody.append("  '') as typeNumberDocument,");
    		stringBody.append("  mcq.COMMENT_MANAGEMENT as comments,");
    		stringBody.append("  mcs.CMD_STATUS_CODE as statusCode,");
    		stringBody.append("  (select");
    		stringBody.append("   mcl.DESCRIPTION ");
    		stringBody.append("  from");
    		stringBody.append("   MOVEMENT_COMAND_LOGS mcl ");
    		stringBody.append("  where");
    		stringBody.append("   mcl.ID=(");
    		stringBody.append("    select");
    		stringBody.append("     max(mcl1.ID) ");
    		stringBody.append("    from");
    		stringBody.append("     MOVEMENT_COMAND_LOGS mcl1 ");
    		stringBody.append("    where");
    		stringBody.append("     mcl1.MOV_CMD_QUEUE_ID=mcq.ID");
    		stringBody.append("   )");
    		stringBody.append("  ) as lastObservation, mcq.IS_MANAGEMENT as isManagment,");
    		stringBody.append("(CASE WHEN WH_SOURCE.CREW_ID IS NOT NULL THEN ");
    		stringBody.append("            dealer_source.depot_code || ' - ' || dealer_source.DEALER_NAME || ' - ' || (select e.first_name from EMPLOYEES e where e.id = (select ec.employee_id from EMPLOYEE_CREWS ec where ec.crew_id = WH_SOURCE.CREW_ID and ec.is_responsible = :isResponsible)) || ' ' || (select e.last_name from employees e where e.id = (select ec.employee_id from EMPLOYEE_CREWS ec where ec.crew_id = WH_SOURCE.CREW_ID and ec.is_responsible = :isResponsible)) || ' - ' || wht_source.wh_type_name ");
    		stringBody.append("      WHEN WH_SOURCE.dealer_id IS NOT NULL THEN ");
    		stringBody.append("            dealer_source.depot_code || ' - ' || dealer_source.DEALER_NAME || ' - ' || wht_source.wh_type_name ");
    		stringBody.append("      WHEN WH_SOURCE.customer_id IS NOT NULL THEN ");
    		stringBody.append("            cus_wh_source.CUSTOMER_CODE || ' - ' || cus_wh_source.FIRST_NAME || ' - ' || cus_wh_source.LAST_NAME || ' - ' || wht_source.wh_type_name END) AS warehouseNameSource,");
    		stringBody.append("(CASE WHEN WH_TARGET.CREW_ID IS NOT NULL THEN ");
    		stringBody.append("            dealer_target.depot_code || ' - ' || dealer_target.DEALER_NAME || ' - ' || (select e.first_name from EMPLOYEES e where e.id = (select ec.employee_id from EMPLOYEE_CREWS ec where ec.crew_id = WH_TARGET.CREW_ID and ec.is_responsible = :isResponsible)) || ' ' || (select e.last_name from employees e where e.id = (select ec.employee_id from EMPLOYEE_CREWS ec where ec.crew_id = WH_TARGET.CREW_ID and ec.is_responsible = :isResponsible)) || ' - ' || WHT_TARGET.wh_type_name ");
    		stringBody.append("      WHEN WH_TARGET.dealer_id IS NOT NULL THEN ");
    		stringBody.append("            dealer_target.depot_code || ' - ' || dealer_target.DEALER_NAME || ' - ' || WHT_TARGET.wh_type_name ");
    		stringBody.append("      WHEN WH_TARGET.customer_id IS NOT NULL THEN ");
    		stringBody.append("            cus_wh_target.CUSTOMER_CODE || ' - ' || cus_wh_target.FIRST_NAME || ' - ' || cus_wh_target.LAST_NAME || ' - ' || WHT_TARGET.wh_type_name END) AS warehouseNameTarget");
    		stringBody.append(" from");
    		stringBody.append("  MOVEMENT_COMAND_QUEUES mcq ");
    		stringBody.append(" left outer join");
    		stringBody.append("  SERIALIZED ser1 ");
    		stringBody.append("   on mcq.SER_ID_LINKED=ser1.ELEMENT_ID ");
    		stringBody.append(" left outer join");
    		stringBody.append("  SERIALIZED ser2 ");
    		stringBody.append("   on mcq.SER_ID=ser2.ELEMENT_ID ");
    		stringBody.append(" left outer join");
    		stringBody.append("  DOCUMENT_CLASSES dc ");
    		stringBody.append("   on mcq.DOCUMENT_CLASS_ID=dc.ID ");
    		stringBody.append(" left outer join");
    		stringBody.append("  WORK_ORDERS wo ");
    		stringBody.append("   on mcq.WORK_ORDER_ID=wo.ID ");
    		stringBody.append(" left outer join");
    		stringBody.append("  CUSTOMERS CUS ");
    		stringBody.append("   on mcq.CUSTOMER_ID=CUS.ID");
    		stringBody.append(" left outer join");
    		stringBody.append("  WAREHOUSES WH_SOURCE ");
    		stringBody.append("   on WH_SOURCE.ID=mcq.SOURCE_WH_ID");
    		stringBody.append(" left outer join");
    		stringBody.append("  warehouse_types WHT_SOURCE ");
    		stringBody.append("   on wh_source.wh_type_id=wht_source.id");
    		stringBody.append(" left outer join");
    		stringBody.append("  CUSTOMERS cus_wh_source ");
    		stringBody.append("   on cus_wh_source.id=wh_source.customer_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  WAREHOUSES WH_TARGET ");
    		stringBody.append("   on WH_TARGET.ID=mcq.target_wh_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  warehouse_types WHT_TARGET");
    		stringBody.append("   on WH_TARGET.wh_type_id=WHT_TARGET.id");
    		stringBody.append(" left outer join");
    		stringBody.append("  CUSTOMERS cus_wh_target ");
    		stringBody.append("   on cus_wh_target.id=WH_TARGET.customer_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  adjustments adj ");
    		stringBody.append("   on adj.id=mcq.adjusment_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  references ref ");
    		stringBody.append("   on ref.id=mcq.reference_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  IMPORT_LOGS il ");
    		stringBody.append("   on il.id=mcq.import_log_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  dealers dealer_source ");
    		stringBody.append("   on dealer_source.id=WH_SOURCE.dealer_id");
    		stringBody.append(" left outer join");
    		stringBody.append("  dealers dealer_target ");
    		stringBody.append("   on dealer_target.id=WH_TARGET.dealer_id,");
    		stringBody.append("  MOVEMENT_COMAND_STATUS mcs ");
        	stringBody.append(" where");
        	stringBody.append("  mcq.MOV_CMD_STATUS_ID=mcs.ID ");
        	stringBody.append("  and mcq.TYPE_COMUNICATION=:typeComunication");
        	stringBody.append("  and mcq.COUNTRY_ID=:countryId");
        	stringBody.append("  AND ((ser1.serial_code = UPPER(TRIM(:serialCode)) OR ser2.serial_code = UPPER(TRIM(:serialCode))) or :serialCode is null)");
        	stringBody.append("  AND (mcs.cmd_status_code = :cmdStatusCode or :cmdStatusCode is null)");
        	stringBody.append("  AND (mcq.is_management = :isManagment or :isManagment is null)");
        	stringBody.append("  AND (adj.id = :documentNumber or ref.id = :documentNumber or il.id = :documentNumber or wo.wo_code = :documentNumber or :documentNumber is null)");
        	stringBody.append("  AND (cus.customer_code = :customerCode or :customerCode is null)");
        	stringBody.append("  AND (TRUNC(mcq.creation_date) >= :creationDateIni or :creationDateIni is null)");
        	stringBody.append("  AND (TRUNC(mcq.creation_date) <= :creationDateFin or :creationDateFin is null )");
        	stringBody.append("  AND (dc.document_class_code = :documentClassCode or :documentClassCode is null) ");
        	stringBody.append(" order by");
        	stringBody.append("  mcq.ID desc ");
        	
        	//Paginacion
        	stringCount.append("select count(*) from ( ");
        	stringCount.append(stringBody.toString() + " ) ");
        	Query countQuery = session.createSQLQuery(stringCount.toString());
        	
        	stringQuery.append(stringBody.toString());
        	
        	Query query = session.createSQLQuery(stringQuery.toString())
			.addScalar("id", Hibernate.LONG)
			.addScalar("status", Hibernate.STRING)
			.addScalar("statusCode", Hibernate.STRING)
			.addScalar("creationDate", Hibernate.TIMESTAMP)
			.addScalar("executeDate", Hibernate.TIMESTAMP)
			.addScalar("serial", Hibernate.STRING)
			.addScalar("serialLinked", Hibernate.STRING)
			.addScalar("warehouseNameSource", Hibernate.STRING)
			.addScalar("warehouseNameTarget", Hibernate.STRING)
			.addScalar("typeNumberDocument", Hibernate.STRING)
			.addScalar("comments", Hibernate.STRING)
			.addScalar("isManagment", Hibernate.STRING)
			.addScalar("lastObservation", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(MovCmdQueueDTO.class));
        	query.setParameter("typeComunication", dto.getProcessType(), Hibernate.STRING);
        	query.setParameter("countryId", countryId, Hibernate.LONG);
        	query.setParameter("isResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
        	
        	if(!isSerialSpecificated){
        		query.setParameter("serialCode", null, Hibernate.STRING);
        	}else{
        		query.setParameter("serialCode", dto.getSerialCode(), Hibernate.STRING);
        	}

        	if(!isStatusSpecificated){
        		query.setParameter("cmdStatusCode", null, Hibernate.STRING);
        	}else{
        		query.setParameter("cmdStatusCode", dto.getStatus(), Hibernate.STRING);
        	}
        	
        	if(!isCommentSpecificated){
        		query.setParameter("isManagment", null, Hibernate.STRING);
        	}else{
        		query.setParameter("isManagment", dto.getIsManagment(), Hibernate.STRING);
        	}
        	
        	if(!isDocumentTypeSpecificated){
        		query.setParameter("documentClassCode", null, Hibernate.STRING);
        	}else{
        		query.setParameter("documentClassCode", dto.getDocumentType(), Hibernate.STRING);
        	}
        	if(!isDocumentNumberSpecificated){
        		query.setParameter("documentNumber", null, Hibernate.LONG);
        	}else{
        		query.setParameter("documentNumber", dto.getDocumentNumber(), Hibernate.LONG);
        	}
        	
        	if(!isCustomerSpecificated){
        		query.setParameter("customerCode", null, Hibernate.LONG);
        	}else{
        		query.setParameter("customerCode", dto.getCustomerCodeIbs(), Hibernate.LONG);
        	}
        	
        	if(!isCreationDateIni){
        		query.setParameter("creationDateIni", null, Hibernate.DATE);
        	}else{
        		query.setParameter("creationDateIni", dto.getInitialDateCreation(), Hibernate.DATE);
        	}
        	
        	if(!isCreationDateFin){
        		query.setParameter("creationDateFin", null, Hibernate.DATE);
        	}else{
        		query.setParameter("creationDateFin", dto.getFinalDateCreation(), Hibernate.DATE);
        	}
        	
        	//Paginacion
        	Long recordQty = 0L;
        	if(requestCollInfo != null){
        		countQuery.setParameter("typeComunication", dto.getProcessType(), Hibernate.STRING);
        		countQuery.setParameter("countryId", countryId, Hibernate.LONG);
        		countQuery.setParameter("isResponsible", CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), Hibernate.STRING);
            	
            	
            	if(!isSerialSpecificated){
            		countQuery.setParameter("serialCode", null, Hibernate.STRING);
            	}else{
            		countQuery.setParameter("serialCode", dto.getSerialCode(), Hibernate.STRING);
            	}

            	if(!isStatusSpecificated){
            		countQuery.setParameter("cmdStatusCode", null, Hibernate.STRING);
            	}else{
            		countQuery.setParameter("cmdStatusCode", dto.getStatus(), Hibernate.STRING);
            	}
            	
            	if(!isCommentSpecificated){
            		countQuery.setParameter("isManagment", null, Hibernate.STRING);
            	}else{
            		countQuery.setParameter("isManagment", dto.getStatus(), Hibernate.STRING);
            	}
            	
            	if(!isDocumentTypeSpecificated){
            		countQuery.setParameter("documentClassCode", null, Hibernate.STRING);
            	}else{
            		countQuery.setParameter("documentClassCode", dto.getDocumentType(), Hibernate.STRING);
            	}
            	if(!isDocumentNumberSpecificated){
            		countQuery.setParameter("documentNumber", null, Hibernate.LONG);
            	}else{
            		countQuery.setParameter("documentNumber", dto.getDocumentNumber(), Hibernate.LONG);
            	}
            	
            	if(!isCustomerSpecificated){
            		countQuery.setParameter("customerCode", null, Hibernate.LONG);
            	}else{
            		countQuery.setParameter("customerCode", dto.getCustomerCodeIbs(), Hibernate.LONG);
            	}
            	
            	if(!isCreationDateIni){
            		countQuery.setParameter("creationDateIni", null, Hibernate.DATE);
            	}else{
            		countQuery.setParameter("creationDateIni", dto.getInitialDateCreation(), Hibernate.DATE);
            	}
            	
            	if(!isCreationDateFin){
            		countQuery.setParameter("creationDateFin", null, Hibernate.DATE);
            	}else{
            		countQuery.setParameter("creationDateFin", dto.getFinalDateCreation(), Hibernate.DATE);
            	}
            	
	        	recordQty = ((BigDecimal)countQuery.uniqueResult()).longValue();	
				query.setFirstResult(requestCollInfo.getFirstResult());
				query.setMaxResults(requestCollInfo.getMaxResults());				
        	}
            
        	MovCmdQueueDTOResponse response = new MovCmdQueueDTOResponse();
        	List<MovCmdQueueDTO> movCmdQueueList = query.list();        	
        	if(requestCollInfo != null){
				populatePaginationInfo(response, requestCollInfo.getPageSize(), requestCollInfo.getPageIndex(), movCmdQueueList.size(), recordQty.intValue());
        	}
        	response.setMovementlist(movCmdQueueList);
        	
        	return response;            
        } catch (Throwable ex){
			log.error("== Error en getMovementQueueHspToIbsByFilter/MovCmdQueueDAO ==");
			throw this.manageException(ex);
		} finally {
            log.debug("== Termina getMovementQueueHspToIbsByFilter/MovCmdQueueDAO ==");
        }	
	}
	
	
	
	
	
	@Override
    public void updateMovCmdQueue(MovCmdQueue obj) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio updateMovCmdQueue/MovCmdQueueDAO ==");
        Session session = super.getSession();
        try {
            session.merge(obj);
            this.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el updateMovCmdQueue/MovCmdQueueDAO ==");
            throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMovCmdQueue/MovCmdQueueDAO ==");
        }
    }

	@Override
	public Long countHistoryEvent(Long customerId, Long historyEventId, Long countryId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicia countHistoryEvent/MovCmdQueueDAO ==");
		if(historyEventId==null){
			return 0L;
		}
		Session session = super.getSession();
		try {
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select count(*) ");
			stringQuery.append("from ");
			stringQuery.append(MovCmdQueue.class.getName());
			stringQuery.append(" entity where ");
			stringQuery.append(" entity.customer.id = :customerId ");
			stringQuery.append(" and entity.country.id = :countryId ");
			stringQuery.append(" and entity.historyEventId = :historyEventId ");
			stringQuery.append(" and entity.typeComunication = :typeComunication ");
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("customerId", customerId);
			query.setLong("countryId", countryId);
			query.setLong("historyEventId", historyEventId);
			query.setString("typeComunication", CodesBusinessEntityEnum.TYPE_COMUNICATION_IBS_TO_HSP.getCodeEntity());
			return (Long) query.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error countHistoryEvent/MovCmdQueueDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina countHistoryEvent/MovCmdQueueDAO ==");
		}
	}
	
	public Long validateProcessForWorkOrder(Long workOrderId, Long elementId, Long elementIdLinked) 
		throws DAOServiceException, DAOSQLException{
		log.debug("== Inicia validateProcessForWorkOrder/MovCmdQueueDAO ==");
		Session session = super.getSession();
		try{
			
			boolean isSpecificatedElementId = elementId  != null && elementId.longValue() >0L;
			boolean isSpecificatedElementIdLinked = elementIdLinked  != null && elementIdLinked.longValue() >0L;
			
			StringBuffer stringQuery = new StringBuffer();
			stringQuery.append("select count(*) ");
			stringQuery.append("from ");
			stringQuery.append(MovCmdQueue.class.getName());
			stringQuery.append(" entity where ");
			stringQuery.append(" entity.typeComunication = :typeComunication ");
			stringQuery.append(" and entity.workOrder.id = :workOrderId ");
			stringQuery.append(" and entity.movCmdStatus.cmdStatusCode = :cmdStatusCode ");
			if(isSpecificatedElementId){
				stringQuery.append(" and (entity.serialized.elementId = :elementId or entity.serializedLinked.elementId = :elementId) ");
			}
			if(isSpecificatedElementIdLinked){
				stringQuery.append(" and (entity.serialized.elementId = :elementIdLinked or entity.serializedLinked.elementId = :elementIdLinked) ");
			}
			Query query = session.createQuery(stringQuery.toString());
			query.setLong("workOrderId", workOrderId);
			query.setString("typeComunication", CodesBusinessEntityEnum.TYPE_COMUNICATION_IBS_TO_HSP.getCodeEntity());
			query.setString("cmdStatusCode", CodesBusinessEntityEnum.MOV_CMD_STATUS_PROCESSED.getCodeEntity());
			if(isSpecificatedElementId){				
				query.setLong("elementId", elementId);
			}
			if(isSpecificatedElementIdLinked){
				query.setLong("elementIdLinked", elementIdLinked);
			}
			return (Long) query.uniqueResult();
		}catch (Throwable ex) {
			log.error("== Error validateProcessForWorkOrder/MovCmdQueueDAO ==");
			throw this.manageException(ex);
		} finally {
			log.debug("== Termina validateProcessForWorkOrder/MovCmdQueueDAO ==");
		}
	}
}
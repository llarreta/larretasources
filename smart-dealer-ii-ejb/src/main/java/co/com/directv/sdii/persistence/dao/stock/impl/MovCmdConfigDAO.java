package co.com.directv.sdii.persistence.dao.stock.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.dto.ElementMovementDTO;
import co.com.directv.sdii.model.dto.MovementElementDTO;
import co.com.directv.sdii.model.pojo.MovCmdConfig;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.stock.MovCmdConfigDAOLocal;

/**
 * A data access object (DAO) providing persistence and search support for
 * MovCmdConfig entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see co.com.directv.sdii.persistence.MovCmdConfig
 * @author MyEclipse Persistence Tools
 */

@Stateless(name="MovCmdConfigDAOLocal",mappedName="ejb/MovCmdConfigDAOLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MovCmdConfigDAO extends BaseDao implements MovCmdConfigDAOLocal {
	private static final Logger log = UtilsBusiness.getLog4J(MovCmdConfigDAO.class);
	// property constants
	public static final String CONFIG_DATE = "configDate";

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#save(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(MovCmdConfig transientInstance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio save/MovCmdConfigDAO ==");
		try {
			getSession().save(transientInstance);
		} catch (Throwable re) {
	        log.error("== Error save/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina save/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#delete(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(MovCmdConfig persistentInstance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio delete/MovCmdConfigDAO ==");
		try {
			getSession().delete(persistentInstance);
		} catch (Throwable re) {
	        log.error("== Error delete/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina delete/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#findById(java.math.BigDecimal)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MovCmdConfig findById(java.math.BigDecimal id) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findById/MovCmdConfigDAO ==");
		try {
			MovCmdConfig instance = (MovCmdConfig) getSession().get("co.com.directv.sdii.model.pojo.MovCmdConfig", id);
			return instance;
		} catch (Throwable re) {
	        log.error("== Error findById/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findById/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#findByExample(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MovCmdConfig> findByExample(MovCmdConfig instance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findByExample/MovCmdConfigDAO ==");
		try {
			List<MovCmdConfig> results = getSession().createCriteria("co.com.directv.sdii.model.pojo.MovCmdConfig").add(Example.create(instance)).list();
			return results;
		} catch (Throwable re) {
	        log.error("== Error findByExample/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByExample/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#findByProperty(java.lang.String, java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MovCmdConfig> findByProperty(String propertyName, Object value) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findByProperty/MovCmdConfigDAO ==");
		try {
			String queryString = "from MovCmdConfig as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (Throwable re) {
	        log.error("== Error findByProperty/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findByProperty/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#findByConfigDate(java.lang.Object)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MovCmdConfig> findByConfigDate(Object configDate) throws DAOServiceException, DAOSQLException {
		return findByProperty(CONFIG_DATE, configDate);
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#findAll()
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MovCmdConfig> findAll() throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio findAll/MovCmdConfigDAO ==");
		try {
			String queryString = "from MovCmdConfig";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (Throwable re) {
	        log.error("== Error findAll/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina findAll/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#merge(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public MovCmdConfig merge(MovCmdConfig detachedInstance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio merge/MovCmdConfigDAO ==");
		try {
			MovCmdConfig result = (MovCmdConfig) getSession().merge(
					detachedInstance);
			return result;
		} catch (Throwable re) {
	        log.error("== Error merge/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina merge/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#attachDirty(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachDirty(MovCmdConfig instance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio attachDirty/MovCmdConfigDAO ==");
		try {
			getSession().saveOrUpdate(instance);
		} catch (Throwable re) {
	        log.error("== Error attachDirty/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachDirty/MovCmdConfigDAO ==");
		}
	}

    /* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.impl.MovCmdConfigDAOLocal#attachClean(co.com.directv.sdii.model.pojo.MovCmdConfig)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void attachClean(MovCmdConfig instance) throws DAOServiceException, DAOSQLException {
        log.debug("== Inicio attachClean/MovCmdConfigDAO ==");
		try {
			getSession().lock(instance, LockMode.NONE);
		} catch (Throwable re) {
	        log.error("== Error attachClean/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina attachClean/MovCmdConfigDAO ==");
		}
	}


	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.stock.MovCmdConfigDAOLocal#getMovCmdConfigBySerializedCriteria(co.com.directv.sdii.model.pojo.Serialized)
	 */
	@Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Long getMovCmdConfigByElementMovementDTOCriteria(ElementMovementDTO dto) throws DAOServiceException,
			DAOSQLException {
        log.debug("== Inicio getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
		try {
			String trueValue = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
			String decoCode = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity();
			String scCode = CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity();
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT ID ");
			queryString.append("FROM MOVEMENT_COMAND_CONFIGURATIONS CONFIG ");
			queryString.append("WHERE STATUS = :cmdStatusConfigActive ");
			queryString.append("AND NVL(COUNTRY_ID,0) = NVL(:countryId,0) ");
			queryString.append("AND NVL(DECO,'N')=DECODE(:elementCode,:decoCode,'S','N') ");
			queryString.append("AND NVL(SC,'N')=DECODE(:linkedCode,:scCode,'S','N') ");
			queryString.append("AND NVL(IS_PREPAID,'N')=DECODE(:isPrepaid,'S','S','N') ");
			queryString.append("AND NVL(SOURCE_WH_TYPE_ID,0) = NVL(:sourceTypeId,0) ");
			queryString.append("AND NVL(TARGET_WH_TYPE_ID,0) = NVL(:targetTypeId,0) ");
			queryString.append("AND NVL(ITEM_STATUS_ID,0) = NVL(:itemStatusId,0) ");
			queryString.append("AND EXISTS (SELECT 1 FROM MOVEMENT_ELEMENT_PROCESSES PROCESS WHERE PROCESS.MOV_ELEMENT_PROCESS_CODE = :processCode AND CONFIG.MOV_PROCESS_ID = PROCESS.ID)  ");
			
			SQLQuery queryObject = getSession().createSQLQuery(queryString.toString());
			
			queryObject.setString("cmdStatusConfigActive", trueValue);
			if(dto.getMovCmdQueueVO().getTargetWarehouse()!=null){
				queryObject.setLong("countryId", dto.getMovCmdQueueVO().getTargetWarehouse().getCountry().getId());
			}else{
				queryObject.setLong("countryId", dto.getCountryID());
			}
			
			queryObject.setString("decoCode", decoCode);
			queryObject.setString("scCode", scCode);
			
			String isSc = null;
			String isDeco = null;
			//Valida que el principal sea un deco
			if( dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase( decoCode ) ){
				isDeco = dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			}
			
			queryObject.setString("elementCode", isDeco );
			//En el caso que el principal sea una tarjeta
			if( dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase( scCode ) ){
				isSc = dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			//Valida que el principal tenga un vinculado
			}else if( dto.getMovCmdQueueVO().getSerialized().getSerialized() != null ){
				isSc = dto.getMovCmdQueueVO().getSerialized().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			} 
			queryObject.setString("linkedCode", isSc);
			
			queryObject.setString("isPrepaid", dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel());
			if( dto.getRealSourceWh() != null ){
				queryObject.setLong("sourceTypeId", dto.getRealSourceWh().getWarehouseType().getId());
			} else {
				queryObject.setLong("sourceTypeId", 0L);
			}
			if( dto.getRealTargetWh() != null ){
				queryObject.setLong("targetTypeId", dto.getRealTargetWh().getWarehouseType().getId());
			} else{
				queryObject.setLong("targetTypeId", 0L);
			}
			if( dto.getItemStatus() != null ){
				queryObject.setLong("itemStatusId", dto.getItemStatus().getId());
			} else {
				queryObject.setLong("itemStatusId", 0L);
			}
			queryObject.setString("processCode", dto.getProcessCode());
			
			Object result = queryObject.uniqueResult();
			Long movConfigId = null;
			if( result != null )
				movConfigId = ( (BigDecimal) queryObject.uniqueResult() ).longValue();	
			return  movConfigId;
		} catch (Throwable re) {
	        log.error("== Error getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
		}
	}

	@Override
	public Long getMovCmdConfigByElementMovementDTOCriteria(
			MovementElementDTO dto) throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
		try {
			String trueValue = CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity();
			String decoCode = CodesBusinessEntityEnum.ELEMENT_CLASS_DECODER.getCodeEntity();
			String scCode = CodesBusinessEntityEnum.ELEMENT_CLASS_CARD.getCodeEntity();
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT ID ");
			queryString.append("FROM MOVEMENT_COMAND_CONFIGURATIONS CONFIG ");
			queryString.append("WHERE STATUS = :cmdStatusConfigActive ");
			queryString.append("AND NVL(COUNTRY_ID,0) = NVL(:countryId,0) ");
			queryString.append("AND NVL(DECO,'N')=DECODE(:elementCode,:decoCode,'S','N') ");
			queryString.append("AND NVL(SC,'N')=DECODE(:linkedCode,:scCode,'S','N') ");
			queryString.append("AND NVL(IS_PREPAID,'N')=DECODE(:isPrepaid,'S','S','N') ");
			queryString.append("AND NVL(SOURCE_WH_TYPE_ID,0) = NVL(:sourceTypeId,0) ");
			queryString.append("AND NVL(TARGET_WH_TYPE_ID,0) = NVL(:targetTypeId,0) ");
			
			//Se elimina estas sentencias porque se elimino este campo de la tabla de configuración
			//waguilera
			//queryString.append("AND NVL(ITEM_STATUS_ID,0) = NVL(:itemStatusId,0) ");
			queryString.append("AND EXISTS (SELECT 1 FROM MOVEMENT_ELEMENT_PROCESSES PROCESS WHERE PROCESS.MOV_ELEMENT_PROCESS_CODE = :processCode AND CONFIG.MOV_PROCESS_ID = PROCESS.ID)  ");
			
			SQLQuery queryObject = getSession().createSQLQuery(queryString.toString());
			
			queryObject.setString("cmdStatusConfigActive", trueValue);
			if(dto.getMovCmdQueueVO().getTargetWarehouse()!=null){
				queryObject.setLong("countryId", dto.getMovCmdQueueVO().getTargetWarehouse().getCountry().getId());
			}else{
				if(dto.getMovCmdQueueVO().getSourceWarehouse()!=null){
					queryObject.setLong("countryId", dto.getMovCmdQueueVO().getSourceWarehouse().getCountry().getId());
				}else{
					queryObject.setLong("countryId", 0L);
				}
			}
			
			queryObject.setString("decoCode", decoCode);
			queryObject.setString("scCode", scCode);
			
			String isSc = null;
			String isDeco = null;
			//Valida que el principal sea un deco
			if(dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(decoCode)){
				isDeco = dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			}
			
			queryObject.setString("elementCode", isDeco);
			//En el caso que el principal sea una tarjeta
			if(dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode().equalsIgnoreCase(scCode)){
				isSc = dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			//Valida que el principal tenga un vinculado
			}else if(dto.getMovCmdQueueVO().getSerialized().getSerialized() != null){
				isSc = dto.getMovCmdQueueVO().getSerialized().getSerialized().getElement().getElementType().getElementModel().getElementClass().getElementClassCode();
			} 
			queryObject.setString("linkedCode", isSc);
			
			queryObject.setString("isPrepaid", dto.getMovCmdQueueVO().getSerialized().getElement().getElementType().getElementModel().getIsPrepaidModel());
			if(dto.getSourceWh() != null){
				queryObject.setLong("sourceTypeId", dto.getSourceWh().getWarehouseType().getId());
			} else {
				queryObject.setLong("sourceTypeId", 0L);
			}
			if(dto.getTargetWs() != null){
				queryObject.setLong("targetTypeId", dto.getTargetWs().getWarehouseType().getId());
			} else{
				queryObject.setLong("targetTypeId", 0L);
			}
			
			//Se elimina estas sentencias porque se eliminó este campo de la tabla de configuración
			//waguilera
//			if(dto.getItemStatus() != null){
//				queryObject.setLong("itemStatusId", dto.getItemStatus().getId());
//			} else {
//				queryObject.setLong("itemStatusId", 0L);
//			}
			
			queryObject.setString("processCode", dto.getProcessCode());
			
			List<Object> listResult = queryObject.list();
			Long movConfigId = null;
			if(listResult != null && listResult.size()>1){
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN487.getCode(), ErrorBusinessMessages.STOCK_IN487.getMessage());
			}
			if(listResult != null && listResult.size() == 1){
				movConfigId = new Long(listResult.get(0).toString());
			}
			return  movConfigId;
		} catch (Throwable re) {
	        log.error("== Error getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
            throw this.manageException(re);
		} finally {
	        log.debug("== Termina getMovCmdConfigByElementMovementDTOCriteria/MovCmdConfigDAO ==");
		}
	}
	
}
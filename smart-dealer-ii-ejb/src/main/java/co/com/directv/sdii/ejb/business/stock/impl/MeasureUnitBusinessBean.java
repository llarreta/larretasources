package co.com.directv.sdii.ejb.business.stock.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ElementType;
import co.com.directv.sdii.model.pojo.MeasureUnit;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MeasureUnitVO;
import co.com.directv.sdii.persistence.dao.stock.ElementTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD MeasureUnit
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.stock.MeasureUnitDAOLocal
 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal
 */
@Stateless(name="MeasureUnitBusinessBeanLocal",mappedName="ejb/MeasureUnitBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MeasureUnitBusinessBean extends BusinessBase implements MeasureUnitBusinessBeanLocal {

    @EJB(name="MeasureUnitDAOLocal", beanInterface=MeasureUnitDAOLocal.class)
    private MeasureUnitDAOLocal daoMeasureUnit;
    
    @EJB(name="ElementTypeDAOLocal", beanInterface=ElementTypeDAOLocal.class)
	private ElementTypeDAOLocal daoElementType;
    
    private final static Logger log = UtilsBusiness.getLog4J(MeasureUnitBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getAllMeasureUnits()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException {
        log.debug("== Inicia getAllMeasureUnits/MeasureUnitBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoMeasureUnit.getAllMeasureUnits(), MeasureUnitVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllMeasureUnits/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllMeasureUnits/MeasureUnitBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getMeasureUnitsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public MeasureUnitVO getMeasureUnitByID(Long id) throws BusinessException {
        log.debug("== Inicia getMeasureUnitByID/MeasureUnitBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MeasureUnit objPojo = daoMeasureUnit.getMeasureUnitByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(MeasureUnitVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMeasureUnitByID/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMeasureUnitByID/MeasureUnitBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#createMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createMeasureUnit(MeasureUnitVO obj) throws BusinessException {
        log.debug("== Inicia createMeasureUnit/MeasureUnitBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	/*MeasureUnit objPojo = daoMeasureUnit.getMeasureUnitByCode(obj.getUnitCode());
        	if(objPojo != null){
        		log.debug("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}*/
        	MeasureUnit objPojo = daoMeasureUnit.getMeasureUnitByName(obj.getUnitName());
        	if(objPojo != null){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN354.getCode(), ErrorBusinessMessages.STOCK_IN354.getMessage());
        	}
            objPojo =  UtilsBusiness.copyObject(MeasureUnit.class, obj);
            //Se agrega código temporal
            objPojo.setUnitCode(UtilsBusiness.currentTimeToString());
            daoMeasureUnit.createMeasureUnit(objPojo);
            //Se crea el nuevo código
            objPojo.setUnitCode(CodesBusinessEntityEnum.MEASURE_UNIT_CODE_PREFIX.getCodeEntity()+objPojo.getId());
            daoMeasureUnit.updateMeasureUnit(objPojo);
            
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createMeasureUnit/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createMeasureUnit/MeasureUnitBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#updateMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateMeasureUnit(MeasureUnitVO obj) throws BusinessException {
        log.debug("== Inicia updateMeasureUnit/MeasureUnitBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getUnitCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        
        
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
        	MeasureUnit objPojo = daoMeasureUnit.getMeasureUnitByCode(obj.getUnitCode());
        	if(objPojo != null && objPojo.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	MeasureUnit objPojoTmp = daoMeasureUnit.getMeasureUnitByCode(obj.getUnitCode());
        	if(objPojoTmp != null && objPojoTmp.getId().longValue() != obj.getId().longValue()){
        		log.error("El objeto que intenta crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.STOCK_IN354.getCode(), ErrorBusinessMessages.STOCK_IN354.getMessage());
        	}
        	
        	//Validar si tiene tipos de elementos y si esta inactivando el registro.
        	List<ElementType> let =  daoElementType.getElementTypeByMeasureUnitID(objPojo.getId());
        	if (!let.isEmpty() && obj.getIsActive() != null && obj.getIsActive().equals("N")){
        		Object params[] = {let.get(0).getId()};
				throw new BusinessException(ErrorBusinessMessages.STOCK_IN407.getCode(), ErrorBusinessMessages.STOCK_IN407.getMessage(params));
        	}
        	
            objPojo =  UtilsBusiness.copyObject(MeasureUnit.class, obj);
            daoMeasureUnit.updateMeasureUnit(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateMeasureUnit/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateMeasureUnit/MeasureUnitBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#deleteMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteMeasureUnit(MeasureUnitVO obj) throws BusinessException {
        log.debug("== Inicia deleteMeasureUnit/MeasureUnitBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MeasureUnit objPojo =  UtilsBusiness.copyObject(MeasureUnit.class, obj);
            daoMeasureUnit.deleteMeasureUnit(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteMeasureUnit/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteMeasureUnit/MeasureUnitBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getMeasureUnitByCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MeasureUnitVO getMeasureUnitByCode(String code)
			throws BusinessException {
		log.debug("== Inicia getMeasureUnitByCode/MeasureUnitBusinessBean ==");
        UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            MeasureUnit objPojo = daoMeasureUnit.getMeasureUnitByCode(code);
            if(objPojo == null){
            	return null;
            }
            return UtilsBusiness.copyObject(MeasureUnitVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMeasureUnitByCode/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMeasureUnitByCode/MeasureUnitBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getMeasureUnitsByStatus()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MeasureUnitVO> getMeasureUnitsByStatus()
			throws BusinessException {
		log.debug("== Inicia getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
        try {
        	List<MeasureUnit> listPojo = daoMeasureUnit.getMeasureUnitsByStatus(CodesBusinessEntityEnum.MEASURE_UNIT_STATUS_ACTIVE.getCodeEntity());
        	if(listPojo==null){
        		return null;
        	}
        	List<MeasureUnitVO> listVO = UtilsBusiness.convertList(listPojo, MeasureUnitVO.class);
        	for(MeasureUnitVO tmp : listVO){
        		tmp.setCodeNameToPrint(tmp.getUnitCode()+"-"+tmp.getUnitName());
        	}
            return listVO;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getMeasureUnitsByStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MeasureUnitResponse getMeasureUnitsByStatus(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		log.debug("== Inicia getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
		List<MeasureUnit> listPojo;
        try {
        	MeasureUnitResponse response = daoMeasureUnit.getMeasureUnitsByStatus(CodesBusinessEntityEnum.MEASURE_UNIT_STATUS_ACTIVE.getCodeEntity(),requestCollInfo);
        	listPojo = response.getMeasureUnit();
        	response.setMeasureUnitVO(UtilsBusiness.convertList(listPojo, MeasureUnitVO.class));
        	response.setMeasureUnit(null);
        	for(MeasureUnitVO tmp : response.getMeasureUnitVO()){
        		tmp.setCodeNameToPrint(tmp.getUnitCode()+"-"+tmp.getUnitName());
        	}
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMeasureUnitsByStatus/MeasureUnitBusinessBean ==");
        }
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal#getMeasureUnitsByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		log.debug("== Inicia getMeasureUnitsByAllStatusPage/MeasureUnitBusinessBean ==");
		List<MeasureUnit> listPojo;
        try {
        	MeasureUnitResponse response = daoMeasureUnit.getMeasureUnitsByAllStatusPage(code,requestCollInfo);
        	listPojo = response.getMeasureUnit();
        	response.setMeasureUnitVO(UtilsBusiness.convertList(listPojo, MeasureUnitVO.class));
        	response.setMeasureUnit(null);
        	for(MeasureUnitVO tmp : response.getMeasureUnitVO()){
        		tmp.setCodeNameToPrint(tmp.getUnitCode()+"-"+tmp.getUnitName());
        	}
            return response;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getMeasureUnitsByAllStatusPage/MeasureUnitBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getMeasureUnitsByAllStatusPage/MeasureUnitBusinessBean ==");
        }
	}
}

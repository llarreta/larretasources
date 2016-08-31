package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.ArrayList;
import java.util.Date;
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
import co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfCustomerType;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.model.pojo.DealerCustomerTypesWoutPc;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerCustomerTypesWoutPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerCustomerTypesWoutPcDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal
 */
@Stateless(name="DealerCustomerTypesWoutPcBusinessBeanLocal",mappedName="ejb/DealerCustomerTypesWoutPcBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerCustomerTypesWoutPcBusinessBean extends BusinessBase implements DealerCustomerTypesWoutPcBusinessBeanLocal {

    @EJB(name="DealerCustomerTypesWoutPcDAOLocal", beanInterface=DealerCustomerTypesWoutPcDAOLocal.class)
    private DealerCustomerTypesWoutPcDAOLocal daoDealerCustomerTypesWoutPc;

    @EJB(name="CustomerTypeDAOLocal", beanInterface=CustomerTypeDAOLocal.class)
    private CustomerTypeDAOLocal customerTypeDAOLocal;

    
    @EJB(name="DealerConfigurationDAOLocal", beanInterface=DealerConfigurationDAOLocal.class)
    private DealerConfigurationDAOLocal dealerConfigurationDAOLocal;
    
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerCustomerTypesWoutPcBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#getAllDealerCustomerTypesWoutPcs()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcs() throws BusinessException {
        log.debug("== Inicia getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerCustomerTypesWoutPc.getAllDealerCustomerTypesWoutPcs(), DealerCustomerTypesWoutPcVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerCustomerTypesWoutPcs/DealerCustomerTypesWoutPcBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#getDealerCustomerTypesWoutPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerCustomerTypesWoutPcVO getDealerCustomerTypesWoutPcByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCustomerTypesWoutPc objPojo = daoDealerCustomerTypesWoutPc.getDealerCustomerTypesWoutPcByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerCustomerTypesWoutPcVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerCustomerTypesWoutPcByID/DealerCustomerTypesWoutPcBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#createDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia createDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCustomerTypesWoutPc objPojo =  UtilsBusiness.copyObject(DealerCustomerTypesWoutPc.class, obj);
            daoDealerCustomerTypesWoutPc.createDealerCustomerTypesWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#updateDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerCustomerTypesWoutPc objPojo =  UtilsBusiness.copyObject(DealerCustomerTypesWoutPc.class, obj);
            objPojo.setDateLastChange(UtilsBusiness.fechaActual());
            daoDealerCustomerTypesWoutPc.updateDealerCustomerTypesWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#deleteDealerCustomerTypesWoutPc(co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerCustomerTypesWoutPc(DealerCustomerTypesWoutPcVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerCustomerTypesWoutPc objPojo =  UtilsBusiness.copyObject(DealerCustomerTypesWoutPc.class, obj);
            daoDealerCustomerTypesWoutPc.deleteDealerCustomerTypesWoutPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerCustomerTypesWoutPc/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#getDealersWhoAttendCustomerTypeWoutCoverage(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersWhoAttendCustomerTypeWoutCoverage(
			String countryCode, Long customerTypeClassId)
			throws BusinessException {
		log.debug("== Inicia getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        UtilsBusiness.assertNotNull(customerTypeClassId, ErrorBusinessMessages.ALLOCATOR_AL034.getCode(), ErrorBusinessMessages.ALLOCATOR_AL034.getMessage());
        try {
        	//DealerCustomerTypesWoutPc dealerCustomerTypesWoutPc=daoDealerCustomerTypesWoutPc.getDealerCustomerTypesWoutPcByID(customerTypeClassId);
            List<Dealer> resultpojo =  daoDealerCustomerTypesWoutPc.getDealersWhoAttendCustomerTypeWoutCoverage(countryCode, customerTypeClassId);
            List<DealerVO> result = UtilsBusiness.convertList(resultpojo, DealerVO.class);
            return result;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersWhoAttendCustomerTypeWoutCoverage/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#getAllActiveByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCustomerTypesWoutPcVO> getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		log.debug("== Inicia getAllActiveByDealerId/DealerCustomerTypesWoutPcBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            return UtilsBusiness.convertList(daoDealerCustomerTypesWoutPc.getAllDealerCustomerTypesWoutPcConfigurationByDealerIdAndCountryId(dealerId, countryId), DealerCustomerTypesWoutPcVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllActiveByDealerId/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllActiveByDealerId/DealerCustomerTypesWoutPcBusinessBean ==");
        }
	}

	private List<DealerCustomerTypesWoutPcVO> customerClassTypeLisToDealerCustomerTypesWoutPcVOList(List<CustomerClassType> customerClassTypeList, Long dealerId){
		
		List<DealerCustomerTypesWoutPcVO> customerTypeVOLisToDealerCustomerTypesWoutPcVOList = new ArrayList();
		for (CustomerClassType customerClassType : customerClassTypeList){
			DealerCustomerTypesWoutPcVO dealerCustomerTypesWoutPcVO = new DealerCustomerTypesWoutPcVO();
			dealerCustomerTypesWoutPcVO.setCountry(customerClassType.getCustomerClass().getCountry()); //podia tomarlo de cualquiera de los dos CustomerClass ó CustomerType
			dealerCustomerTypesWoutPcVO.setCountryId(customerClassType.getCustomerClass().getCountry().getId()); //dato redundante pero se mantien la estructura del response lo mejor posible
			dealerCustomerTypesWoutPcVO.setCustomerClassType(customerClassType);
			dealerCustomerTypesWoutPcVO.setDateLastChange(new Date());
			dealerCustomerTypesWoutPcVO.setDealer(new Dealer());
			dealerCustomerTypesWoutPcVO.setDealerId(dealerId);
		    dealerCustomerTypesWoutPcVO.setId(new Long(0)); //idem getAllDealerCustomerTypesWoutPcConfigurationResponse
		  //dealerCustomerTypesWoutPcVO.setStatus(status);
		  //dealerCustomerTypesWoutPcVO.setUser(user);
		    dealerCustomerTypesWoutPcVO.setUserId(new Long(0)); //getAllDealerCustomerTypesWoutPcConfigurationResponse
			
			customerTypeVOLisToDealerCustomerTypesWoutPcVOList.add(dealerCustomerTypesWoutPcVO);
		}
		
		return customerTypeVOLisToDealerCustomerTypesWoutPcVOList;
	}
	
	
	/**
	 * Se encarga de setearle a cada <code>dealerCustomerTypesWoutPcVOList</code> el estado <b>"S"</b> o <b>"N"</b> dependiendo si está configurado
	 * para la combinación <i>"Dealer - Categoria de cliente"</i>. Para esto se recorren todas las DealerConfigurations que corresponden a
	 * la combinación de <i>Dealer-CustomerCategory</i> y aquellos que coincidan con los que se van a enviar en el servicio se marcarán con S
	 * el resto viajarán con N.<br/>
	 * Se espera que la lista que se envia por parámetro no es nula.
	 * @param dealerCustomerTypesWoutPcVOList lista de customer types
	 * @throws BusinessException
	 */
	private void setDealerCustomerTypesWoutPcVOListStatus(List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPcVOList, Long dealerId, Long customerCategoryId, Long businessAreaId)
	throws BusinessException{		
		try{
			DealerConfiguration dealerConfiguration = dealerConfigurationDAOLocal.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, businessAreaId, customerCategoryId);
			
			if(dealerConfiguration == null) return;
			
			for (DealerConfCustomerType dealerConfCustomerType : dealerConfiguration.getDealerConfCustomerTypeSet()){						
				for (DealerCustomerTypesWoutPcVO dealerCustomerTypesWoutPcVO :dealerCustomerTypesWoutPcVOList){							
					if (dealerConfCustomerType.getCustomerClassTypeId().equals(dealerCustomerTypesWoutPcVO.getCustomerClassType().getId())){
						dealerCustomerTypesWoutPcVO.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
						break;
					}else if(!CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerCustomerTypesWoutPcVO.getStatus())){
						dealerCustomerTypesWoutPcVO.setStatus(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
					}
				}
			}
			
		}catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación setDealerCustomerTypesWoutPcVOListStatus/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw new BusinessException(ex.getMessage());		    
	    }
}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Nueva Pantalla de Configuración de Dealers por Tipo de Cliente
	 * Carga solapa del acordeon	: "Tipos de cliente" 
	 * Servicio 					: AssignmentConfigWS
	 * Operación:					: getAllDealerCustomerTypesByCustomerClassId (...)
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal#getAllDealerCustomerTypesByCustomerCategoryId(java.lang.Long)
	 */
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerCustomerTypesWoutPcVO>getAllDealerCustomerTypesByCustomerCategoryId(	Long dealerId, 
																							Long countryId, 
																							Long customerCategoryId,
																							Long businessAreaId) 
    throws BusinessException{
		log.debug("== Inicia getAllDealerCustomerTypesByCustomerClassId/DealerCustomerTypesWoutPcBusinessBean ==");
		UtilsBusiness.assertNotNull(dealerId          , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(countryId		  , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customerCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(businessAreaId	  , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	/*
          	El servicio que carga la información de la solapa “Tipos de cliente” 
          	recibirá como parámetro de entrada la “Categoría de Cliente” seleccionada en el combo desplegable superior. 
			A partir de la “Categoría de Cliente” seleccionada, se recorrerá la relación “Categoría de Cliente” ->”Clase de Cliente” ->”Tipo de Cliente” 
			para obtener la información de Tipos de Cliente a retornar para mostrarse en la solapa.			
			[Tabla: CUSTOMER_CATEGORY -> Tabla: CUSTOMER_CLASSES -> Tabla: CUSTOMER_CLASS_TYPES -> Tabla: CUSTOMER_TYPES]
        	*/        	
        	
        	List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPcVOList  = customerClassTypeLisToDealerCustomerTypesWoutPcVOList(customerTypeDAOLocal.getCustomerTypeByCategory(customerCategoryId, countryId),dealerId);
        	
        	setDealerCustomerTypesWoutPcVOListStatus(dealerCustomerTypesWoutPcVOList, dealerId, customerCategoryId, businessAreaId);
        	
        	return dealerCustomerTypesWoutPcVOList;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerCustomerTypesByCustomerClassId/DealerCustomerTypesWoutPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerCustomerTypesByCustomerClassId/DealerCustomerTypesWoutPcBusinessBean ==");
        }
		
	}
	
}

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
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfService;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.model.pojo.DealerServiceSubCategory;
import co.com.directv.sdii.model.pojo.ServiceCategory;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerServiceSubCategory
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal
 */
@Stateless(name="DealerServiceSubCategoryBusinessBeanLocal",mappedName="ejb/DealerServiceSubCategoryBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerServiceSubCategoryBusinessBean extends BusinessBase implements DealerServiceSubCategoryBusinessBeanLocal {

    @EJB(name="DealerServiceSubCategoryDAOLocal", beanInterface=DealerServiceSubCategoryDAOLocal.class)
    private DealerServiceSubCategoryDAOLocal daoDealerServiceSubCategory;

    
    @EJB(name="ServiceTypeDAOLocal", beanInterface=ServiceTypeDAOLocal.class)
    private ServiceTypeDAOLocal serviceTypeDAOLocal;
    
    @EJB(name="ServiceCategoryDAOLocal", beanInterface=ServiceCategoryDAOLocal.class)
    private ServiceCategoryDAOLocal serviceCategoryDAOLocal;

    @EJB(name="DealerConfigurationDAOLocal", beanInterface=DealerConfigurationDAOLocal.class)
    private DealerConfigurationDAOLocal dealerConfigurationDAOLocal;

    
    
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerServiceSubCategoryBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#getAllDealerServiceSubCategorys()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerServiceSubCategoryVO> getAllDealerServiceSubCategorys() throws BusinessException {
        log.debug("== Inicia getAllDealerServiceSubCategorys/DealerServiceSubCategoryBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerServiceSubCategory.getAllDealerServiceSubCategorys(), DealerServiceSubCategoryVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerServiceSubCategorys/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerServiceSubCategorys/DealerServiceSubCategoryBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#getDealerServiceSubCategorysByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerServiceSubCategoryVO getDealerServiceSubCategoryByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerServiceSubCategoryByID/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerServiceSubCategory objPojo = daoDealerServiceSubCategory.getDealerServiceSubCategoryByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerServiceSubCategoryVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerServiceSubCategoryByID/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerServiceSubCategoryByID/DealerServiceSubCategoryBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#createDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia createDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	obj.setDateLastChange(new Date());
            DealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategory.class, obj);
            daoDealerServiceSubCategory.createDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#updateDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	obj.setDateLastChange(new Date());
            DealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategory.class, obj);
            daoDealerServiceSubCategory.updateDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#deleteDealerServiceSubCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerServiceSubCategory(DealerServiceSubCategoryVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerServiceSubCategory objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategory.class, obj);
            daoDealerServiceSubCategory.deleteDealerServiceSubCategory(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#getDealersWhoAttendServiceCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersWhoAttendServiceCategory(
			String countryCode, String serviceCode) throws BusinessException {
		log.debug("== Inicia getDealersWhoAttendServiceCategory/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.ALLOCATOR_AL043.getCode(), ErrorBusinessMessages.ALLOCATOR_AL043.getMessage());
        try {
            List<Dealer> objPojos =  daoDealerServiceSubCategory.getDealersWhoAttendServiceCategory(countryCode, serviceCode);
            List<DealerVO> objVos = UtilsBusiness.convertList(objPojos, DealerVO.class);
            return objVos;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersWhoAttendServiceCategory/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersWhoAttendServiceCategory/DealerServiceSubCategoryBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#getDealersWhoAttendServiceCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<DealerVO> getDealersWhoAttendServiceSubCategory(
			String countryCode, String serviceCode) throws BusinessException {
		log.debug("== Inicia getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.ALLOCATOR_AL043.getCode(), ErrorBusinessMessages.ALLOCATOR_AL043.getMessage());
        try {
            List<Dealer> objPojos =  daoDealerServiceSubCategory.getDealersWhoAttendServiceSubCategory(countryCode, serviceCode);
            List<DealerVO> objVos = UtilsBusiness.convertList(objPojos, DealerVO.class);
            return objVos;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersWhoAttendServiceSubCategory/DealerServiceSubCategoryBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryBusinessBeanLocal#getAllConfigurationActiveByDealerId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerServiceSubCategoryVO> getAllConfigurationActiveByDealerIdAndCountryId(Long dealerId, Long countryId) throws BusinessException {
		log.debug("== Inicia getAllConfigurationActiveByDealerId/DealerServiceSubCategoryBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerServiceSubCategory.getAllConfigurationActiveByDealerIdAndCountryIdOrderedByServiceType(dealerId, countryId), DealerServiceSubCategoryVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllConfigurationActiveByDealerId/DealerServiceSubCategoryBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllConfigurationActiveByDealerId/DealerServiceSubCategoryBusinessBean ==");
        }
	}
	
	/*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getDealerServiceSubCategoriesWithPcTree(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeVO> getDealerServiceSubCategoriesTree(Long dealerId, Long countryId) throws BusinessException {
    	 log.debug("== Inicia getDealerServiceSubCategoriesTree/DealerServiceSubCategoryBusinessBean ==");
    	 UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 try {
    		 List<DealerServiceSubCategory> dealerServiceSubCategories = daoDealerServiceSubCategory.getAllConfigurationActiveByDealerIdAndCountryIdOrderedByServiceType(dealerId, countryId);
    		 
    		 List<ServiceTypeVO> dealerServiceTree = groupInServiceType(dealerServiceSubCategories);
    		 
    		 return dealerServiceTree;
             
         } catch(Throwable ex){
         	log.error("== Error al tratar de ejecutar la operación getDealerServiceSubCategoriesTree/DealerServiceSubCategoryBusinessBean ==");
         	throw this.manageException(ex);
         } finally {
             log.debug("== Termina getDealerServiceSubCategoriesTree/DealerServiceSubCategoryBusinessBean ==");
         }
    }
    
    private void setServiceTypeVOListStatus ( List<ServiceTypeVO> serviceTypeVOList, Long dealerId, Long businessAreaId, Long customerCategoryId) throws BusinessException {
    	
    	 try {
    		 	//List<DealerConfiguration> dealerConfigurationList= dealerConfigurationDAOLocal.getDealerConfigurationById(dealerId);
    		 	DealerConfiguration dealerConfiguration = dealerConfigurationDAOLocal.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, businessAreaId, customerCategoryId);
    		 	
    		 	if(dealerConfiguration == null){
    		 		return;
    		 	}
    		 	
    		 	for (DealerConfService dealerConfService :dealerConfiguration.getDealerConfServiceSet()){
    		 		for(ServiceTypeVO serviceTypeVO : serviceTypeVOList){
    		 			for (DealerServiceSubCategoryVO dealerServiceSubCategoryVO :serviceTypeVO.getDealerServiceSubCategories()){
    		 				if(dealerConfService.getServiceCategoryId().equals(dealerServiceSubCategoryVO.getServiceCategoryId())){
    		 					dealerServiceSubCategoryVO.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
    		 					break;
    		 				}else if(!CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerServiceSubCategoryVO.getStatus())){
    		 					dealerServiceSubCategoryVO.setStatus(CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity());
    		 				}
    		 			}
    		 		}
    		 	}    		 				
    	  } catch(Throwable ex){
           	log.error("== Error al tratar de ejecutar la operación setServiceTypeVOListStatus/DealerServiceSubCategoryBusinessBean ==");
           	throw new BusinessException(ex.getMessage());
           } finally {
               log.debug("== Termina setServiceTypeVOListStatus/DealerServiceSubCategoryBusinessBean ==");
           }    		 	
    	
    	
    }
    
    private List<ServiceTypeVO> serviceTypeListToServiceTypeVOList( List<ServiceType> serviceTypeList ,Long countryId,Long dealerId) throws BusinessException {
    	
    	try {
    		 List<ServiceTypeVO> serviceTypeVOList= new ArrayList();
    	
    		 // 1 service_type_id, n service_categories
    		 for (ServiceType serviceType :serviceTypeList){
    			 
    			 ServiceTypeVO serviceTypeVO = new ServiceTypeVO();
    			 serviceTypeVO.setId(serviceType.getId());
    			 serviceTypeVO.setServiceTypeCode(serviceType.getServiceTypeCode());
    			 serviceTypeVO.setServiceTypeName(serviceType.getServiceTypeName());
    			 serviceTypeVO.setSuperCategory(serviceType.getSuperCategory());
    			 
    			 List<DealerServiceSubCategoryVO> dealerServiceSubCategories = new ArrayList();
    			 List<ServiceCategory> serviceCategoryList=serviceCategoryDAOLocal.getServiceCategoryByTypeId(serviceType.getId());
    			 for(ServiceCategory serviceCategory : serviceCategoryList){
    				 DealerServiceSubCategoryVO dealerServiceSubCategoryVO = new DealerServiceSubCategoryVO();
    				 dealerServiceSubCategoryVO.setCountry(new Country()); // se necesita?
    				 dealerServiceSubCategoryVO.setCountryId(countryId);
    				 dealerServiceSubCategoryVO.setDateLastChange(new Date());// se necesita?
    				 dealerServiceSubCategoryVO.setDealerId(dealerId);
    				 //dealerServiceSubCategoryVO.setId(id);
    				 dealerServiceSubCategoryVO.setServiceCategory(serviceCategory);    	
    				 dealerServiceSubCategoryVO.setServiceCategoryId(serviceCategory.getId());
    				 dealerServiceSubCategoryVO.setStatus(""); //se seteará despues en S si el dealer ya cuenta con esta configuracion
    				 dealerServiceSubCategoryVO.setUser(new User());// se necesita?
    				 //dealerServiceSubCategoryVO.setUserId(userId);
    				     				 
    				 dealerServiceSubCategories.add(dealerServiceSubCategoryVO);
    			 }
    			 
    			 serviceTypeVO.setDealerServiceSubCategories(dealerServiceSubCategories);
    			 serviceTypeVOList.add(serviceTypeVO);
    		 }       	
    		 
    	     return serviceTypeVOList;
    	  } catch(Throwable ex){
           	log.error("== Error al tratar de ejecutar la operación serviceTypeListToServiceTypeVOList/DealerServiceSubCategoryBusinessBean ==");
           	throw this.manageException(ex);
           } finally {
               log.debug("== Termina serviceTypeListToServiceTypeVOList/DealerServiceSubCategoryBusinessBean ==");
           }
    }
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Nueva Pantalla de Configuración de Dealers por Tipo de Cliente
	 * Carga solapa del acordeon	: "Sub categorías de servicio"
	 * Servicio 					: AssignmentConfigWS
	 * OperaciOn:					: getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId(...)
	 * 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId(java.lang.Long,java.lang.Long, java.lang.Long, java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeVO> getDealerServiceSubCategoriesTreeByBusinessAreaId(	Long dealerId, 
    																				Long countryId,     																
    																				Long businessAreaId,
    																				Long customerCategoryId) 
    throws BusinessException {
    	 log.debug("== Inicia getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId/DealerServiceSubCategoryBusinessBean ==");
    	 UtilsBusiness.assertNotNull(dealerId			, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 UtilsBusiness.assertNotNull(countryId			, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());    	    	 
    	 UtilsBusiness.assertNotNull(businessAreaId 	, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 UtilsBusiness.assertNotNull(customerCategoryId , ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 try {
    		 
    		 /*
				Esta información deberá quedar en una tabla* 
				que agrupe las asociaciones de tipos de servicios, 
				de tal manera de que si en un futuro aparece una nueva área de negocio por BD se pueda ajustar.
				
				* la relación se resolverá con una clave foránea desde SERVICE_TYPES a BUSINESS_AREAS
				El servicio que carga la información de la solapa “Sub categorías de servicio”	
				recibirá como parámetro de entrada  el “Área de Negocio”  seleccionada en el combo desplegable superior. 
				A partir del “Área de Negocio” seleccionada, se recorrerá la relación “Área de Negocio” ”Tipo de servicio” para obtener la información de Sub categorías de Servicio (Tipos de servicio) a retornar para mostrarse en la solapa.

    		  */
    		     	   
    		 //ServiceTypeVO hereda de ServiceType entonces primero busco todos los  ServiceType para la categoria
    		 //se obtienen los tipos de servicios para el area de negocio
    		 List<ServiceType> serviceTypeList = serviceTypeDAOLocal.getServiceTypeByBusinessArea(businessAreaId);    		 
    		 List<ServiceTypeVO> serviceTypeVOList  = serviceTypeListToServiceTypeVOList(serviceTypeList,countryId,dealerId);    		     		 
    		 setServiceTypeVOListStatus(serviceTypeVOList,dealerId, businessAreaId, customerCategoryId);    		 
    		 return serviceTypeVOList;
             
         } catch(Throwable ex){
         	log.error("== Error al tratar de ejecutar la operación getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId/DealerServiceSubCategoryBusinessBean ==");
         	throw this.manageException(ex);
         } finally {
             log.debug("== Termina getDealerServiceSubCategoriesTreeByDealerIdAndCustomerClassIdAndBusinessAreaId/DealerServiceSubCategoryBusinessBean ==");
         }
    }

    /**
     * Metodo: agrupa los DealerServiceSubCategory dentro de un ServiceTypeVO agregandolos a la lista
     * ServiceTypeVO.dealerServiceSubCategories
     * @param dealerServiceSubCategories
     * @return
     * @throws BusinessException
     */
	private List<ServiceTypeVO> groupInServiceType(List<DealerServiceSubCategory> dealerServiceSubCategories) throws BusinessException {
		List<ServiceTypeVO> dealerServiceTree = new ArrayList<ServiceTypeVO>();
		
		if(dealerServiceSubCategories != null) {
			Long lastTypeId = Long.MIN_VALUE;
			List<DealerServiceSubCategoryVO> dealerServiceSubCategoriesVO = new ArrayList<DealerServiceSubCategoryVO>();
			for (DealerServiceSubCategory dealerServiceSubCategory : dealerServiceSubCategories) {
				
				//si cambia el tipo (teniendo en cuenta que la lista viene ordenada)
				if(dealerServiceSubCategory.getServiceCategory() != null 
				   && dealerServiceSubCategory.getServiceCategory().getServiceType() != null) {
					if( !lastTypeId.equals(dealerServiceSubCategory.getServiceCategory().getServiceType().getId()) ) {
						
						lastTypeId = dealerServiceSubCategory.getServiceCategory().getServiceType().getId();
						ServiceType serviceType = dealerServiceSubCategory.getServiceCategory().getServiceType();
						ServiceTypeVO serviceTypeVO = UtilsBusiness.copyObject(ServiceTypeVO.class, serviceType);
						
						dealerServiceSubCategoriesVO = new ArrayList<DealerServiceSubCategoryVO>();
						dealerServiceSubCategoriesVO.add(UtilsBusiness.copyObject(DealerServiceSubCategoryVO.class, dealerServiceSubCategory));
						serviceTypeVO.setDealerServiceSubCategories(dealerServiceSubCategoriesVO);
						
						dealerServiceTree.add(serviceTypeVO);
						
					} else {
						dealerServiceSubCategoriesVO.add(UtilsBusiness.copyObject(DealerServiceSubCategoryVO.class, dealerServiceSubCategory));
					}
				}
			}
		}
		return dealerServiceTree;
	}

}

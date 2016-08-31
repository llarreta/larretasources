package co.com.directv.sdii.ejb.business.assign.impl;

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
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerServiceSubCategoryWithPc;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceCategoryDAOLocal;
import co.com.directv.sdii.reports.dto.DealerServiceSubCategoryWithPcDTO;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * EJB que implementa las operaciones Tipo CRUD DealerServiceSubCategoryWithPc
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerServiceSubCategoryWithPcDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal
 */
@Stateless(name="DealerServiceSubCategoryWithPcBusinessBeanLocal",mappedName="ejb/DealerServiceSubCategoryWithPcBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerServiceSubCategoryWithPcBusinessBean extends BusinessBase implements DealerServiceSubCategoryWithPcBusinessBeanLocal {

    @EJB(name="DealerServiceSubCategoryWithPcDAOLocal", beanInterface=DealerServiceSubCategoryWithPcDAOLocal.class)
    private DealerServiceSubCategoryWithPcDAOLocal daoDealerServiceSubCategoryWithPc;
    
    @EJB(name="ServiceCategoryDAOLocal", beanInterface=ServiceCategoryDAOLocal.class)
    private ServiceCategoryDAOLocal daoServiceCategoryDAOLocal;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealerServiceSubCategoryWithPcBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getAllDealerServiceSubCategoryWithPcs()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DealerServiceSubCategoryWithPcVO> getAllDealerServiceSubCategoryWithPcs() throws BusinessException {
        log.debug("== Inicia getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoDealerServiceSubCategoryWithPc.getAllDealerServiceSubCategoryWithPcs(), DealerServiceSubCategoryWithPcVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerServiceSubCategoryWithPcs/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getDealerServiceSubCategoryWithPcsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public DealerServiceSubCategoryWithPcVO getDealerServiceSubCategoryWithPcByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerServiceSubCategoryWithPc objPojo = daoDealerServiceSubCategoryWithPc.getDealerServiceSubCategoryWithPcByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerServiceSubCategoryWithPcVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerServiceSubCategoryWithPcByID/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#createDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
        log.debug("== Inicia createDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerServiceSubCategoryWithPc objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategoryWithPc.class, obj);
            daoDealerServiceSubCategoryWithPc.createDealerServiceSubCategoryWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#updateDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
        log.debug("== Inicia updateDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	if(! BusinessRuleValidationManager.getInstance().isValid(obj)){
        		log.error("Error de validación de campos requeridos");
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	
            DealerServiceSubCategoryWithPc objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategoryWithPc.class, obj);
            objPojo.setDateLastChange(UtilsBusiness.fechaActual());
            daoDealerServiceSubCategoryWithPc.updateDealerServiceSubCategoryWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
		
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#updateDealerServiceSubCategoriesWithPcConfiguration(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerServiceSubCategoriesWithPcConfiguration(
			List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPc)
			throws BusinessException {
		
		log.debug("== Inicia updateDealerServiceSubCategoriesWithPcConfiguration/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerServiceSubCategoriesWithPc, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	//persistir las modificaciones a las configuraciones
        	for (DealerServiceSubCategoryWithPcVO dealerServiceSubCategoryWithPcVO : dealerServiceSubCategoriesWithPc) {
        		updateDealerServiceSubCategoryWithPc(dealerServiceSubCategoryWithPcVO);
			}
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerServiceSubCategoriesWithPcConfiguration/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerServiceSubCategoriesWithPcConfiguration/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
		
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#deleteDealerServiceSubCategoryWithPc(co.com.directv.sdii.model.vo.DealerServiceSubCategoryWithPcVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteDealerServiceSubCategoryWithPc(DealerServiceSubCategoryWithPcVO obj) throws BusinessException {
        log.debug("== Inicia deleteDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerServiceSubCategoryWithPc objPojo =  UtilsBusiness.copyObject(DealerServiceSubCategoryWithPc.class, obj);
            daoDealerServiceSubCategoryWithPc.deleteDealerServiceSubCategoryWithPc(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealerServiceSubCategoryWithPc/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerServiceSubCategoryBusinessBeanLocal#getDealersWhoAttendServiceCategory(co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO)
	 */	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<DealerVO> getDealersWhoAttendServiceSubCategoryWithCoverage(String countryCode, String serviceCode, String postalCode) throws BusinessException
    {
    	List<Dealer> dealers = null ;
		log.debug("== Inicia getDealersWhoAttendServiceSubCategoryWithCoverage/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(countryCode, ErrorBusinessMessages.ALLOCATOR_AL031.getCode(), ErrorBusinessMessages.ALLOCATOR_AL031.getMessage());
        UtilsBusiness.assertNotNull(serviceCode, ErrorBusinessMessages.ALLOCATOR_AL043.getCode(), ErrorBusinessMessages.ALLOCATOR_AL043.getMessage());
        UtilsBusiness.assertNotNull(postalCode, ErrorBusinessMessages.ALLOCATOR_AL041.getCode(), ErrorBusinessMessages.ALLOCATOR_AL041.getMessage());
        
        try {
        	dealers = daoDealerServiceSubCategoryWithPc.getDealersWhoAttendServiceSubCategoryWithCoverage(countryCode, serviceCode, postalCode);
        	List<DealerVO> dealersVos = UtilsBusiness.convertList(dealers, DealerVO.class);
        	return dealersVos ;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersWhoAttendServiceSubCategoryWithCoverage/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersWhoAttendServiceSubCategoryWithCoverage/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
        
    }


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getDealerServSubCatPcByCountryAndActive(java.lang.Long)
	 */
	@Override
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<DealerServiceSubCategoryWithPcDTO> getDealerServSubCatPcByCountryAndActive(
			Long idCountry) throws BusinessException {
		 log.debug("== Inicia getDealerServSubCatPcByCountryAndActive/DealerServiceSubCategoryWithPcBusinessBean ==");
	        try {
	        	List<DealerServiceSubCategoryWithPcDTO> result =  new ArrayList<DealerServiceSubCategoryWithPcDTO>();
	        	List<DealerServiceSubCategoryWithPcVO> resultVO =  UtilsBusiness.convertList(daoDealerServiceSubCategoryWithPc.getDealerServiceSubCategoryWithPcsByCountryAndStatus(idCountry,CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity()), DealerServiceSubCategoryWithPcVO.class);
	        	
	        	for(DealerServiceSubCategoryWithPcVO tmp : resultVO){
	        		DealerServiceSubCategoryWithPcDTO tmpDTO =  new DealerServiceSubCategoryWithPcDTO();
	        		tmpDTO.setPostalCodeName(tmp.getDealerCoverage().getPostalCode().getPostalCodeName());
	        		tmpDTO.setDealerCode(tmp.getDealerCoverage().getDealer().getDealerCode().toString());
	        		tmpDTO.setDepotCode(tmp.getDealerCoverage().getDealer().getDepotCode());
	        		tmpDTO.setSubCatSerCode(tmp.getServiceCategory().getServiceCategoryCode());
	        		tmpDTO.setSubCatSerName(tmp.getServiceCategory().getServiceCategoryName());
	        		result.add(tmpDTO);
	        	}
	        	
	            return result;

	        } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación getDealerServSubCatPcByCountryAndActive/DealerServiceSubCategoryWithPcBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina getDealerServSubCatPcByCountryAndActive/DealerServiceSubCategoryWithPcBusinessBean ==");
	        }
	}
	
	/*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getDealerServiceSubCategoriesWithPcTree(java.lang.Long)
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ServiceTypeVO> getDealerServiceSubCategoriesWithPcTree(Long dealerCoverageId) throws BusinessException {
    	 log.debug("== Inicia getDealerServiceSubCategoriesWithPcTree/DealerServiceSubCategoryWithPcBusinessBean ==");
    	 UtilsBusiness.assertNotNull(dealerCoverageId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
    	 try {
    		 List<DealerServiceSubCategoryWithPc> dealerServiceSubCategoriesWithPc = daoDealerServiceSubCategoryWithPc.getDealerServiceSubCategoriesWithPcActiveByDealerCoverageIdOrderedByServiceType(dealerCoverageId);
    		 
    		 List<ServiceTypeVO> dealerServiceTree = groupInServiceType(dealerServiceSubCategoriesWithPc);
    		 
    		 return dealerServiceTree;
             
         } catch(Throwable ex){
         	log.error("== Error al tratar de ejecutar la operación getDealerServiceSubCategoriesWithPcTree/DealerServiceSubCategoryWithPcBusinessBean ==");
         	throw this.manageException(ex);
         } finally {
             log.debug("== Termina getDealerServiceSubCategoriesWithPcTree/DealerServiceSubCategoryWithPcBusinessBean ==");
         }
    }

    /**
     * Metodo: agrupa los DealerServiceSubCategoryWithPc dentro de un ServiceTypeVO agregandolos a la lista
     * ServiceTypeVO.dealerServiceSubCategoriesWithPc
     * @param dealerServiceSubCategories
     * @return
     * @throws BusinessException
     */
	private List<ServiceTypeVO> groupInServiceType(List<DealerServiceSubCategoryWithPc> dealerServiceSubCategoriesWithPc) throws BusinessException {
		List<ServiceTypeVO> dealerServiceTree = new ArrayList<ServiceTypeVO>();
		
		if(dealerServiceSubCategoriesWithPc != null) {
			Long lastTypeId = Long.MIN_VALUE;
			List<DealerServiceSubCategoryWithPcVO> dealerServiceSubCategoriesWithPcVO = new ArrayList<DealerServiceSubCategoryWithPcVO>();
			for (DealerServiceSubCategoryWithPc dealerServiceSubCategoryWithPc : dealerServiceSubCategoriesWithPc) {
				
				//si cambia el tipo (teniendo en cuenta que la lista viene ordenada)
				if(dealerServiceSubCategoryWithPc.getServiceCategory() != null 
				   && dealerServiceSubCategoryWithPc.getServiceCategory().getServiceType() != null) {
					if( !lastTypeId.equals(dealerServiceSubCategoryWithPc.getServiceCategory().getServiceType().getId()) ) {
						
						lastTypeId = dealerServiceSubCategoryWithPc.getServiceCategory().getServiceType().getId();
						ServiceType serviceType = dealerServiceSubCategoryWithPc.getServiceCategory().getServiceType();
						ServiceTypeVO serviceTypeVO = UtilsBusiness.copyObject(ServiceTypeVO.class, serviceType);
						
						dealerServiceSubCategoriesWithPcVO = new ArrayList<DealerServiceSubCategoryWithPcVO>();
						dealerServiceSubCategoriesWithPcVO.add(UtilsBusiness.copyObject(DealerServiceSubCategoryWithPcVO.class, dealerServiceSubCategoryWithPc));
						serviceTypeVO.setDealerServiceSubCategoriesWithPc(dealerServiceSubCategoriesWithPcVO);
						
						dealerServiceTree.add(serviceTypeVO);
						
					} else {
						dealerServiceSubCategoriesWithPcVO.add(UtilsBusiness.copyObject(DealerServiceSubCategoryWithPcVO.class, dealerServiceSubCategoryWithPc));
					}
				}
			}
		}
		return dealerServiceTree;
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryWithPcBusinessBeanLocal#getByDealerCoverageIdAndServiceCategotyId(java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DealerServiceSubCategoryWithPcVO getByDealerCoverageIdAndServiceCategotyId(
			Long dealerCoverageId, Long serviceCategoryId)
			throws BusinessException {
		log.debug("== Inicia getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcBusinessBean ==");
        UtilsBusiness.assertNotNull(dealerCoverageId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(serviceCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            DealerServiceSubCategoryWithPc objPojo = daoDealerServiceSubCategoryWithPc.getByDealerCoverageIdAndServiceCategotyId(dealerCoverageId, serviceCategoryId);
            return UtilsBusiness.copyObject(DealerServiceSubCategoryWithPcVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getByDealerCoverageIdAndServiceCategotyId/DealerServiceSubCategoryWithPcBusinessBean ==");
        }
	}

}

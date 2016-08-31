package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CoverageType;
import co.com.directv.sdii.model.pojo.Dealer;
import co.com.directv.sdii.model.pojo.DealerConfCoverage;
import co.com.directv.sdii.model.pojo.DealerConfCustomerType;
import co.com.directv.sdii.model.pojo.DealerConfService;
import co.com.directv.sdii.model.pojo.DealerConfiguration;
import co.com.directv.sdii.model.vo.DealerConfigurationVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.assign.CoverageTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal;
import co.com.directv.sdii.persistence.dao.config.DealerConfCoverageDAOLocal;
import co.com.directv.sdii.persistence.dao.config.DealerConfCustomerTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.DealerConfServiceDAOLocal;

/**
 * Req-0096 - Requerimiento Consolidado Asignador
 * 
 * EJB que implementa las operaciones Tipo CRUD DealerConfiguration.
 * 
 * Fecha de Creación: Mi 18, 2013
 * @author ialessan
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.DealerConfigurationDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.DealerConfigurationBusinessBeanLocal
 */
@Stateless(name="DealerConfigurationBusinessBeanLocal",mappedName="ejb/DealerConfigurationBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DealerConfigurationBusinessBean extends BusinessBase implements DealerConfigurationBusinessBeanLocal {


	@EJB(name="DealerConfigurationDAOLocal", beanInterface=DealerConfigurationDAOLocal.class)
	private DealerConfigurationDAOLocal dealerConfigurationDAO;
	
	@EJB(name="DealerConfCustomerTypeDAOLocal", beanInterface=DealerConfCustomerTypeDAOLocal.class)
	private DealerConfCustomerTypeDAOLocal dealerConfCustomerTypeDAOLocal;
	
	@EJB(name="DealerConfServiceDAOLocal", beanInterface=DealerConfServiceDAOLocal.class)
	private DealerConfServiceDAOLocal dealerConfServiceDAOLocal;
	
	@EJB(name="DealerConfCoverageDAOLocal", beanInterface=DealerConfCoverageDAOLocal.class)
	private DealerConfCoverageDAOLocal dealerConfCoverageDAOLocal;	

    @EJB(name="CoverageTypeDAOLocal", beanInterface=CoverageTypeDAOLocal.class)
    private CoverageTypeDAOLocal daoCoverageType;
	
	
	
    private final static Logger log = UtilsBusiness.getLog4J(DealerConfigurationBusinessBean.class);

	/**
	 * Metodo:  Persiste la información de un objeto DealerConfigurationVO
	 * @param obj objeto que encapsula la información de un DealerConfigurationVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
    public void createDealerConfiguration(DealerConfigurationVO obj) throws BusinessException{
		   try {
			   
			   DealerConfiguration dealerConfiActual = dealerConfigurationDAO.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(obj.getDealerId(),
							 obj.getBusinessAreaId(),
							 obj.getCustomerCategoryId());
			   
			   if (dealerConfiActual == null){	
				   
				   DealerConfiguration dealerConfiguration = new DealerConfiguration();
				   dealerConfiguration.setDealerId(obj.getDealerId());
				   dealerConfiguration.setCustomerCategoryId(obj.getCustomerCategoryId());
				   dealerConfiguration.setBusinessAreaId(obj.getBusinessAreaId());
				   dealerConfiguration.setDateLastChange(new Date());
				   dealerConfiguration.setUserIdLastChange(obj.getUserIdLastChange());
				   dealerConfiguration.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());

				   dealerConfigurationDAO.createDealerConfiguration(dealerConfiguration);
				   dealerConfiActual = dealerConfiguration;
			   }   
			   
			   //TIPOS DE CLIENTE - Se crean los tipos de cliente que estén en estado S, el resto se eliminan.
			   for(DealerCustomerTypesWoutPcVO dealerConfCustType : obj.getDealerCustomerTypesWoutPc()){				   
				   if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerConfCustType.getStatus())){					   
					   DealerConfCustomerType dealerConfCustomerType = new DealerConfCustomerType();
					   dealerConfCustomerType.setDealerConfigurationId(dealerConfiActual.getId());
					   dealerConfCustomerType.setCustomerClassTypeId(dealerConfCustType.getCustomerClassType().getId());
					   dealerConfCustomerType.setDateLastChange(new Date());
					   dealerConfCustomerType.setUserIdLastChange(obj.getUserIdLastChange());
					   dealerConfCustomerType.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
											
					   dealerConfCustomerTypeDAOLocal.createDealerConfCustomerType(dealerConfCustomerType);					   
				   }else{ //borrarlo
					   dealerConfCustomerTypeDAOLocal.deleteDealerConfCustomerTypeByDealerConfIdAndCustClassTypeId(dealerConfiActual.getId(), dealerConfCustType.getCustomerClassType().getId());
				   }
			   }
			   
			   
			   //SERVICIOS - Se crean los servicios que estén en estado S, el resto se eliminan.
			   for(DealerServiceSubCategoryVO dealerServiceSubCatVO : obj.getDealerServiceSubCategories()){
				   if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerServiceSubCatVO.getStatus())){
					   DealerConfService dealerConfService = new DealerConfService();
					   dealerConfService.setDealerConfigurationId(dealerConfiActual.getId());
					   dealerConfService.setServiceCategoryId(dealerServiceSubCatVO.getServiceCategory().getId());
					   dealerConfService.setDateLastChange(new Date());
					   dealerConfService.setUserIdLastChange(obj.getUserIdLastChange());
					   dealerConfService.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
					   
					   dealerConfServiceDAOLocal.createDealerConfService(dealerConfService);
				   } else { //borrarlo
					   dealerConfServiceDAOLocal.deleteDealerConfServiceByDealerConfIdAndServiceCatId(dealerConfiActual.getId(), dealerServiceSubCatVO.getServiceCategory().getId());					   
				   }
			   }
			   
			   //COBERTURA - Se crean los converages que estén en estado S, el resto se eliminan.
			   for(DealerCoverageVO dealerCoverageVO : obj.getDealerCoverage()){
				   if(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity().equals(dealerCoverageVO.getStatus())){
					   dealerConfCoverageDAOLocal.deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(dealerConfiActual.getId(), dealerCoverageVO.getPostalCode().getId());					   
					   
					   DealerConfCoverage dealerConfCoverage = new DealerConfCoverage();
					   dealerConfCoverage.setDealerConfigurationId(dealerConfiActual.getId());
					   dealerConfCoverage.setCountry(dealerCoverageVO.getCountry());
					   dealerConfCoverage.setCountryId(dealerCoverageVO.getCountry().getId());
					   dealerConfCoverage.setDealer(dealerCoverageVO.getDealer());
					   dealerConfCoverage.setDealerId(dealerCoverageVO.getDealer().getId());
					   dealerConfCoverage.setPostalCode(dealerCoverageVO.getPostalCode());
					   dealerConfCoverage.setPostalCodeIds(dealerCoverageVO.getPostalCode().getId());
					   CoverageType coverageType = (dealerCoverageVO.getCoverageType() != null && 
							   CodesBusinessEntityEnum.COVERAGE_TYPE_OCASSIONAL.getCodeEntity().equals(dealerCoverageVO.getCoverageType().getCode())) ? 
									   daoCoverageType.getCoverageTypeOccasional() : daoCoverageType.getCoverageTypePermanent(); 
						dealerConfCoverage.setCoverageType(coverageType);
		    			dealerConfCoverage.setCoverageTypeId(coverageType.getId());
		    			dealerConfCoverage.setDealerPriority(0L);
		    			dealerConfCoverage.setDateLastChange(new Date());
		    			dealerConfCoverage.setUserIdLastChange(obj.getUserIdLastChange());
		    			dealerConfCoverage.setStatus(CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
		    			
		    			dealerConfCoverageDAOLocal.createDealerConfCoverage(dealerConfCoverage);
	        		}else{
	        			dealerConfCoverageDAOLocal.deleteDealerConfCoverageByDealerConfIdAndPostalCodeId(dealerConfiActual.getId(), dealerCoverageVO.getPostalCode().getId());
	        		}
	        	}
		
		   } catch(Throwable ex){
	        	log.error("== Error al tratar de ejecutar la operación createDealerConfiguration/DealerConfigurationBusinessBean ==");
	        	throw this.manageException(ex);
	        } finally {
	            log.debug("== Termina createDealerConfiguration/DealerConfigurationBusinessBean ==");
	        }
			
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal#getDealerConfigurationBy(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public DealerConfigurationVO getDealerConfigurationBy(Long   dealerCode, 
														  String depotCode, 
														  String customerCategoryCode, 
														  String businessAreaCode
	) throws BusinessException{
		log.debug("== Termina getDealerConfigurationBy/DealerConfigurationBusinessBean ==");   
		try { 
			DealerConfiguration dealerconfiguration = dealerConfigurationDAO.getDealerConfigurationBy(dealerCode, depotCode, customerCategoryCode, businessAreaCode);
			return UtilsBusiness.copyObject(DealerConfigurationVO.class, dealerconfiguration);
			   
		} catch(Throwable ex){
	        log.error("== Error al tratar de ejecutar la operación getDealerConfigurationBy/DealerConfigurationBusinessBean ==");
	        throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerConfigurationBy/DealerConfigurationBusinessBean ==");
		}
	}
	
	/*
	 * ialessan
	 * release_4.2.1.5_Spira_28780 - Configuracion Masiva de Cobertura  de  La  compañía  por  Clase de Cliente
	 * marzo 2014 
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal#getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public DealerConfigurationVO getDealerConfigurationByDealerIdAreaIdCustomerCategoryId (Long dealerId, Long customerCategoryId, Long businessAreaId)
			throws BusinessException{
				log.debug("== Termina getDealerConfigurationBy/DealerConfigurationBusinessBean ==");

		    	String paramsRequired = new String("");
		    	
		        if (  dealerId == null 
		            ||customerCategoryId == null         	
		        	||businessAreaId == null 
		        	) 
		        {
		            
		            if (dealerId == null)                	
		            	paramsRequired = paramsRequired.concat( " Dealer Id ");                
		            else if (customerCategoryId == null)
		            	paramsRequired = paramsRequired.concat( " Customer Category Id ");
		            else if (businessAreaId == null)
		            	paramsRequired = paramsRequired.concat( " Business Area Id "); 
		            
		        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage().concat(paramsRequired));
		        }

				
				try { 
										
					DealerConfiguration dealerconfiguration = dealerConfigurationDAO.getDealerConfigurationByDealerIdAreaIdCustomerCategoryId(dealerId, customerCategoryId, businessAreaId);
					return UtilsBusiness.copyObject(DealerConfigurationVO.class, dealerconfiguration);
					   
				} catch(Throwable ex){
			        log.error("== Error al tratar de ejecutar la operación getDealerConfigurationBy/DealerConfigurationBusinessBean ==");
			        throw this.manageException(ex);
				} finally {
					log.debug("== Termina getDealerConfigurationBy/DealerConfigurationBusinessBean ==");
				}
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal#getDealerFromDealerByCustomerType(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public List<DealerVO> getDealerFromDealerByCustomerType(Long customerCategoryId, Long businesAreaId, Long customerClassTypeId, 
    		Long serviceCategoryId, Long postalCodeId, Long countryCode) throws BusinessException{
		log.debug("== Termina getDealerFromDealerByCustomerType/DealerConfigurationBusinessBean ==");   
		try { 
			   
			List<Dealer> dealers = dealerConfigurationDAO.getDealerFromDealerByCustomerType(customerCategoryId, businesAreaId, customerClassTypeId, 
					serviceCategoryId, postalCodeId, countryCode);
			return UtilsBusiness.convertList(dealers, DealerVO.class);
			   
		} catch(Throwable ex){
	        log.error("== Error al tratar de ejecutar la operación getDealerFromDealerByCustomerType/DealerConfigurationBusinessBean ==");
	        throw this.manageException(ex);
		} finally {
			log.debug("== Termina getDealerFromDealerByCustomerType/DealerConfigurationBusinessBean ==");
		}
	}
	
}

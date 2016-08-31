package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerConfigurationBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCoverageBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerCustomerTypesWoutPcBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerDetailBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerServiceSubCategoryBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.assign.DealerWeekCapacityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerConfigurationVO;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * 
 * implementación de las operaciones que permiten la configuración del asignador 
 * 
 * Fecha de Creación: 12/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="AssignmentConfigurationBusinessBeanLocal",mappedName="ejb/AssignmentConfigurationBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AssignmentConfigurationBusinessBean implements
		AssignmentConfigurationBusinessBeanLocal {

	@EJB(name="DealerDetailBusinessBeanLocal", beanInterface=DealerDetailBusinessBeanLocal.class)
	private DealerDetailBusinessBeanLocal businessDealerDetail;
	
	@EJB(name="DealerWeekCapacityBusinessBeanLocal", beanInterface=DealerWeekCapacityBusinessBeanLocal.class)
	private DealerWeekCapacityBusinessBeanLocal businessDealerWeekCapacity;
	
	@EJB(name="DealerCoverageBusinessBeanLocal", beanInterface=DealerCoverageBusinessBeanLocal.class)
	private DealerCoverageBusinessBeanLocal businessDealerCoverage;
	
	@EJB(name="DealerServiceSubCategoryBusinessBeanLocal", beanInterface=DealerServiceSubCategoryBusinessBeanLocal.class)
	private DealerServiceSubCategoryBusinessBeanLocal businessDealerServiceSubCategory;
	
	@EJB(name="DealerCustomerTypesWoutPcBusinessBeanLocal", beanInterface=DealerCustomerTypesWoutPcBusinessBeanLocal.class)
	private DealerCustomerTypesWoutPcBusinessBeanLocal businessDealerCustomerTypesWoutPc;

	@EJB(name="DealerConfigurationBusinessBeanLocal", beanInterface=DealerConfigurationBusinessBeanLocal.class)
	private DealerConfigurationBusinessBeanLocal dealerConfigurationBusinessBean;
	
	

	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal#updateDealerConfiguration(java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerConfiguration(List<DealerCoverageVO> dealerCoverages)
			throws BusinessException {
		businessDealerCoverage.updateDealersCoverage(dealerCoverages);
	}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal#updateDealerConfCoverageConfiguration(java.util.List,java.lang.Long,java.lang.Long,java.lang.Long,java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerConfCoverageConfiguration(  List<DealerCoverageVO> dealerCoverages,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
			) throws BusinessException {
		//businessDealerCoverage.updateDealersCoverage(dealerCoverages);
		businessDealerCoverage.updateDealerConfCoverageConfiguration(   dealerCoverages,
																		dealerId, 
																		customerCategoryId ,
																		businessAreaId,
																		userIdLastChange
				
				);
	}	
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal#updateDealerConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateDealerConfiguration(DealerDetailVO dealerDetail,
			List<DealerWeekCapacityVO> dealerWeekCapacities,
			List<DealerCoverageVO> dealerCoverages,
			List<DealerServiceSubCategoryVO> dealerServiceSubCategories,
			List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc)
			throws BusinessException {

		UtilsBusiness.assertNotNull(dealerDetail, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(dealerWeekCapacities, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
		if(dealerDetail != null){
			if((dealerDetail.getCompanyWoQtyDay() != null && dealerDetail.getCompanyWoQtyDay() > 0) || (dealerDetail.getTechnicianWoQtyDay() != null && dealerDetail.getTechnicianWoQtyDay() > 0) )
				businessDealerDetail.updateDealerDetail(dealerDetail);
			else
				UtilsBusiness.assertNotNull(null, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());				
		}
		
		if(dealerWeekCapacities != null) {
			for (DealerWeekCapacityVO dealerWeekCapacity : dealerWeekCapacities) {
				businessDealerWeekCapacity.updateDealerWeekCapacity(dealerWeekCapacity);
			}
		}
		
		if(dealerCoverages != null)
			businessDealerCoverage.updateDealersCoverage(dealerCoverages);
		
		if(dealerServiceSubCategories != null) {
			for (DealerServiceSubCategoryVO dealerServiceSubCategory : dealerServiceSubCategories) {
				businessDealerServiceSubCategory.updateDealerServiceSubCategory(dealerServiceSubCategory);
			}
		}
		
		if(dealerCustomerTypesWoutPc != null) {
			for (DealerCustomerTypesWoutPcVO dealerCustomerTypesWoutPcVO : dealerCustomerTypesWoutPc) {
				businessDealerCustomerTypesWoutPc.updateDealerCustomerTypesWoutPc(dealerCustomerTypesWoutPcVO);
			}
		}
		
	}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal#updateDealerByCustomerCategoryAndAreaConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List, java.lang.Long, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED) //si falla algunos de los metodos de "createDealerConfiguration" falla la transaccion
	public void updateDealerByCustomerCategoryAndAreaConfiguration(	 List<DealerCoverageVO> dealerCoverages, //info solapa Cobertura
																	 List<DealerServiceSubCategoryVO> dealerServiceSubCategories,//info solapa Sub categorías de servicio
																	 List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc,//info solapa Tipos de cliente
																	 Long userIdLastChange,
																	 Long dealerId,
																	 Long customerCategoryId, //info combo desplegable Categoria de Cliente
																	 Long businessAreaId)     //info combo desplegable Area de Negocio
															 
    throws BusinessException {
		UtilsBusiness.assertNotNull(dealerId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(customerCategoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		UtilsBusiness.assertNotNull(businessAreaId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		

		// el request del servicio ya tendria que traer este VO - DealerConfigurationVO - armado - Se debe cambiar la interfaz del servicio
		DealerConfigurationVO dealerConfigurationVO = new DealerConfigurationVO();
		
		dealerConfigurationVO.setDealerCoverage(dealerCoverages);
		dealerConfigurationVO.setDealerServiceSubCategories(dealerServiceSubCategories);
		dealerConfigurationVO.setDealerCustomerTypesWoutPc(dealerCustomerTypesWoutPc);
		
		dealerConfigurationVO.setDealerId(dealerId);
		dealerConfigurationVO.setCustomerCategoryId(customerCategoryId);
		dealerConfigurationVO.setBusinessAreaId(businessAreaId);
		dealerConfigurationVO.setUserIdLastChange(userIdLastChange);
		
		dealerConfigurationBusinessBean.createDealerConfiguration(dealerConfigurationVO);
		
	}

}

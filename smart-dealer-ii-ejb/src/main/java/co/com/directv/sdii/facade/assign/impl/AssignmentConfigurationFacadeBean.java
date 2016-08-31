package co.com.directv.sdii.facade.assign.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.assign.AssignmentConfigurationBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * 
 * implementación de la fachada con las operaciones que permiten la configuración del asignador
 * 
 * Fecha de Creación: 12/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="AssignmentConfigurationFacadeLocal",mappedName="ejb/AssignmentConfigurationFacadeLocal")
public class AssignmentConfigurationFacadeBean implements AssignmentConfigurationFacadeBeanLocal {

	@EJB(name="AssignmentConfigurationBusinessBeanLocal", beanInterface=AssignmentConfigurationBusinessBeanLocal.class)
    private AssignmentConfigurationBusinessBeanLocal businessAssignmentConfiguration;
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal#updateDealerConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void updateDealerConfiguration(	DealerDetailVO dealerDetail,
											List<DealerWeekCapacityVO> dealerWeekCapacity,
											List<DealerCoverageVO> dealerCoverage,
											List<DealerServiceSubCategoryVO> dealerServiceSubCategories,
											List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc)
			throws BusinessException {
		businessAssignmentConfiguration.updateDealerConfiguration(	dealerDetail, 
																	dealerWeekCapacity, 
																	dealerCoverage,
																	dealerServiceSubCategories, 
																	dealerCustomerTypesWoutPc);

	}
	
	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal#updateDealerByCustomerCategoryAndAreaConfiguration(co.com.directv.sdii.model.vo.DealerDetailVO, java.util.List, java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void updateDealerByCustomerCategoryAndAreaConfiguration(	List<DealerCoverageVO> dealerCoverage,
																	List<DealerServiceSubCategoryVO> dealerServiceSubCategories,
																	List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc,
																	Long userIdLastChange,
																	Long dealerId,
																	Long customerCategoryId, 
																	Long businessAreaId) 
     throws BusinessException {
		
		businessAssignmentConfiguration.updateDealerByCustomerCategoryAndAreaConfiguration(	dealerCoverage,
																							dealerServiceSubCategories, 
																							dealerCustomerTypesWoutPc,
																							userIdLastChange,
																							dealerId,
																							customerCategoryId,
																							businessAreaId);
	}
	

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal#updateDealerConfiguration(java.util.List)
	 */
	@Override
	public void updateDealerConfiguration(List<DealerCoverageVO> dealerCoverage)
			throws BusinessException {
		businessAssignmentConfiguration.updateDealerConfiguration(dealerCoverage);
	}

	/*
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.assign.AssignmentConfigurationFacadeBeanLocal#Req-0096 - Requerimiento Consolidado Asignador(java.util.List)
	 */
	@Override
	public void updateDealerConfCoverageConfiguration(	List<DealerCoverageVO> dealerCoverages,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
   )throws BusinessException {
		//businessAssignmentConfiguration.updateDealerConfiguration(dealerCoverage);
		businessAssignmentConfiguration.updateDealerConfCoverageConfiguration(	dealerCoverages,
																				dealerId, 
																				customerCategoryId ,
																				businessAreaId,
																				userIdLastChange);
	}	
}

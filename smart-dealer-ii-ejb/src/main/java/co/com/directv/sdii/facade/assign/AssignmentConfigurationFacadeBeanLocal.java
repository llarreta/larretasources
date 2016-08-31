package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerCoverageVO;
import co.com.directv.sdii.model.vo.DealerCustomerTypesWoutPcVO;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.model.vo.DealerServiceSubCategoryVO;
import co.com.directv.sdii.model.vo.DealerWeekCapacityVO;

/**
 * 
 * definición de las operaciones que permiten la configuración del asignador 
 * 
 * Fecha de Creación: 12/07/2011
 * @author wjimenez <a href="mailto:wjimenez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface AssignmentConfigurationFacadeBeanLocal {

	/**
	 * Metodo: crea o actualiza la configuración de un dealer 
	 * @param dealerDetail
	 * @param dealerWeekCapacity
	 * @param dealerCoverage
	 * @param dealerServiceSubCategories
	 * @param dealerCustomerTypesWoutPc <tipo> <descripcion>
	 * @throws BusinessException
	 * @author wjimenez
	 */
	public void updateDealerConfiguration(DealerDetailVO dealerDetail,
			List<DealerWeekCapacityVO> dealerWeekCapacity,
			List<DealerCoverageVO> dealerCoverage,
			List<DealerServiceSubCategoryVO> dealerServiceSubCategories,
			List<DealerCustomerTypesWoutPcVO> dealerCustomerTypesWoutPc) throws BusinessException;

	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * 
	 * Metodo: crea o actualiza la configuración de un dealer desde la 
	 * Nueva Pantalla de Configuración de Dealers por Categoria de Cliente
	 * @param dealerCoverage
	 * @param dealerServiceSubCategories
	 * @param dealerCustomerTypesWoutPc <tipo> <descripcion>
	 * @param customerCategoryId
	 * @param businessAreaId
	 * @throws BusinessException
	 * @author ialessan
	 */
	public void updateDealerByCustomerCategoryAndAreaConfiguration(	List<DealerCoverageVO> 				dealerCoverage,
																	List<DealerServiceSubCategoryVO> 	dealerServiceSubCategories,
																	List<DealerCustomerTypesWoutPcVO> 	dealerCustomerTypesWoutPc,
																	Long userIdLastChange,
																	Long dealerId,
																	Long customerCategoryId, 
																	Long businessAreaId) 
  throws BusinessException ;
	
	
	/**
	 * Metodo: ejecuta la actualización de la configuración de los dealerCoverage para la parametrización
	 * detallada de un dealer
	 * @param dealerCoverage
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author wjimenez
	 */
	public void updateDealerConfiguration(List<DealerCoverageVO> dealerCoverage) throws BusinessException;
	
	
	/**
	 * Req-0096 - Requerimiento Consolidado Asignador
	 * Metodo: ejecuta la actualización de la configuración de los dealerConfCoverage para la parametrización
	 * detallada de un dealer
	 * @param dealerCoverage
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author ialessan
	 */
	public void updateDealerConfCoverageConfiguration(	List<DealerCoverageVO> dealerCoverage,
														Long  dealerId, 
														Long  customerCategoryId ,
														Long  businessAreaId,
														Long  userIdLastChange
			) throws BusinessException;

}

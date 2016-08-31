
package co.com.directv.sdii.assign.kpi.job;

import java.util.List;

import org.apache.log4j.Logger;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.assignment.AssignmentFacadeRemote;
import co.com.directv.sdii.assign.kpi.DealerIndicator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.kpi.factory.DealerIndicatorFactory;
import co.com.directv.sdii.assign.kpi.factory.DealerIndicatorFactoryImpl;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.assign.CoreAssignmentFacadeRemote;
import co.com.directv.sdii.model.dto.KpiCalculateDTO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.KpiResultVO;

/**
 * se encarga de administrar la persistencia sobre los resultados del cálculo de
 * los indicadores
 * @author wjimenez
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public final class KPIResultPersistenceManager {

	private final static Logger log = UtilsBusiness.getLog4J(KPIResultPersistenceManager.class);
	
	private static KPIResultPersistenceManager mySelf;
	
	public static KPIResultPersistenceManager getInstance() {
		if (mySelf == null)
			mySelf = new KPIResultPersistenceManager();
		return mySelf;
	}
	
	private KPIResultPersistenceManager(){}
	
	/**
	 * obtiene el resultado de un indicador que se encuentra en persistencia.
	 * 
	 * @param dealerIndicatorDto parámetros para la búsqueda del resultado del indicador.
	 * @return DealerIndicatorResultDTO objeto que encapsula el resultado almacenado del indicador
	 */
	public DealerIndicatorResultDTO getDealerIndicatorResult(DealerIndicatorDTO dealerIndicatorDto) throws BusinessException {
		try {
			CoreAssignmentFacadeRemote assignmentFacade = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
			KpiResultVO kpiResultVO = assignmentFacade.getDealerIndicatorResult(dealerIndicatorDto);
			return kpiResultVO != null ? kpiResultVO.toDealerIndicatorResultDTO() : null;
		} catch (Exception e) {
			String msg = "no se pudo obtener el KPI. " + getExceptionComplementInfo(dealerIndicatorDto) + ". " + e.getMessage(); 
			throw new BusinessException(msg, e);
		}
	}

	/**
	 * calcula y persiste la información del KPI especificado por <code>dealerIndicatorDto</code>
	 * para todas las compañías instaladoras activas en el sistema y que prestan servicios a
	 * clientes.
	 * 
	 * @param dealerIndicatorDto configuración del indicador
	 * @param kpiCode    código del indicador a calcular 
	 * @throws BusinessException 
	 */
	public void calculateAndPersistDealersIndicator(KpiCalculateDTO kpiCalculateDTO) {
		
		DealerIndicatorFactory dealerIndicatorFactory = DealerIndicatorFactoryImpl.getInstance();
		DealerIndicator dealerIndicator = dealerIndicatorFactory.buildDealerIndicator(kpiCalculateDTO.getKpiCode());
		
			if(dealerIndicator != null) {
				
				CoreAssignmentFacadeRemote coreAssignmentFacade = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade();
				AssignmentFacadeRemote assignmentFacade = AssignmentFacadeLocator.getInstance().getAssignmetFacade();
	
				List<DealerVO> dealers = null;
				try {
					dealers = coreAssignmentFacade.getDealersForGenerateKpis(kpiCalculateDTO.getCountryId());
				} catch (Exception e) {
					String msg = "no se pudieron obtener los dealers para el pais con id = " + kpiCalculateDTO.getCountryId();
					log.error(msg);
					return;
					
				}
				
				if(dealers != null) {
					for (DealerVO dealerVO : dealers) {
						kpiCalculateDTO.setDealerId(dealerVO.getId());
						try {
							assignmentFacade.calculateAndPersistDealerIndicator(dealerIndicator, buildingDealerIndicatorDTO(kpiCalculateDTO));
						} catch (BusinessException e) {
							String msg = "no se pudo calcular el kpiConfiguration= " + kpiCalculateDTO.getIndicatorConfigurationId();
							log.error(msg);
						}
					}
				}
				
			} else {
				String msg = "no se pudo construir el objeto DealerIndicator para el indicador " + kpiCalculateDTO.getKpiCode();
				log.error(msg);
			}
	}
	
	private String getExceptionComplementInfo(DealerIndicatorDTO dealerIndicatorDto) {
		return new StringBuffer("kpiId = ")
		.append(dealerIndicatorDto != null ? dealerIndicatorDto.getIndicatorId() : "null")
		.append(", dealerId = ").append(dealerIndicatorDto != null ? dealerIndicatorDto.getDealerId() : "null").toString();
	}
	
	private DealerIndicatorDTO buildingDealerIndicatorDTO(KpiCalculateDTO kpiCalculateDTO) {
		DealerIndicatorDTO dealerIndicatorDTO =  new DealerIndicatorDTO();
		dealerIndicatorDTO.setCountryId( kpiCalculateDTO.getCountryId());
		dealerIndicatorDTO.setDayCount( kpiCalculateDTO.getDayCount().intValue());
		dealerIndicatorDTO.setDealerId(kpiCalculateDTO.getDealerId());
		dealerIndicatorDTO.setIndicatorId( kpiCalculateDTO.getIndicatorId());
		dealerIndicatorDTO.setIndicatorTypeId( kpiCalculateDTO.getIndicatorTypeId());
		dealerIndicatorDTO.setServiceSuperCategoryId( kpiCalculateDTO.getServiceSuperCategoryId());
		dealerIndicatorDTO.setZoneTypeId( kpiCalculateDTO.getZoneTypeId());
		dealerIndicatorDTO.setIndicatorConfigurationId( kpiCalculateDTO.getIndicatorConfigurationId() );
		return dealerIndicatorDTO;
	}
	
}//end KPIResultPersistenceManager

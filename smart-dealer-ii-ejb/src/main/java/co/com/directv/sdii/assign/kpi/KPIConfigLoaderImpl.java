package co.com.directv.sdii.assign.kpi;

import java.util.ArrayList;
import java.util.List;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;
import co.com.directv.sdii.assign.kpi.dto.KPIConfigCriteria;
import co.com.directv.sdii.assign.kpi.dto.KPIConfiguration;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.KpiConfigurationVO;

public final class KPIConfigLoaderImpl implements KPIConfigLoader {

	private static KPIConfigLoaderImpl mySelf;
	
	private KPIConfigLoaderImpl() {
		
	}
	
	public static KPIConfigLoaderImpl getInstance() {
		if(mySelf == null)
			 mySelf = new KPIConfigLoaderImpl();
		return mySelf;
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.kpi.KPIConfigLoader#loadKPIConfiguration(co.com.directv.sdii.assign.kpi.dto.KPIConfigCriteria)
	 */
	@Override
	public KPIConfiguration loadKPIConfiguration(KPIConfigCriteria criteria) throws BusinessException {
		List<KpiConfigurationVO> kpiConfigurations = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getKPIConfigurationsByCountryAndServiceSuperCategoryAndZoneType(criteria.getCountryId(), criteria.getServiceSuperCategoryId(), criteria.getZoneTypeId());
		
		List<DealerIndicatorConfigDTO> diConfigs = new ArrayList<DealerIndicatorConfigDTO>();
		DealerIndicatorConfigDTO diConfig;
		for (KpiConfigurationVO kpiConfigurationVO : kpiConfigurations) {
			diConfig = kpiConfigurationVO.toDealerIndicatorConfigDTO();
			diConfigs.add(diConfig);
		}
		KPIConfiguration result = new KPIConfiguration(criteria, diConfigs);
		return result;
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.assign.kpi.KPIConfigLoader#loadKPIConfiguration(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DealerIndicatorConfigDTO loadKPIConfiguration(String kpiCode,
			String serviceSuperCategoryCode, String countryCode, String zoneTypeCode) throws BusinessException {
		KpiConfigurationVO config = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getKPIConfigurationsByCountryCodeAndServiceSuperCategoryCodeAndZoneTypeCodeAndKpiCode(countryCode, serviceSuperCategoryCode, zoneTypeCode, kpiCode);
		DealerIndicatorConfigDTO result = config.toDealerIndicatorConfigDTO();
		return result;
	}

}

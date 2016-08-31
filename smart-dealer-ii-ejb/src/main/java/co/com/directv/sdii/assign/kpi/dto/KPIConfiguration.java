package co.com.directv.sdii.assign.kpi.dto;

import java.io.Serializable;
import java.util.List;


/**
 * encapsula la información de la configuración de los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPIConfiguration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253726617587088796L;
	private KPIConfigCriteria configInfo;
	private List<DealerIndicatorConfigDTO> indicatorsConfig;

	public KPIConfiguration(){

	}
	
	public KPIConfiguration(KPIConfigCriteria configInfo,
			List<DealerIndicatorConfigDTO> indicatorsConfig) {
		super();
		this.configInfo = configInfo;
		this.indicatorsConfig = indicatorsConfig;
	}

	public KPIConfigCriteria getConfigInfo() {
		return configInfo;
	}



	public void setConfigInfo(KPIConfigCriteria configInfo) {
		this.configInfo = configInfo;
	}



	public List<DealerIndicatorConfigDTO> getIndicatorsConfig() {
		return indicatorsConfig;
	}



	public void setIndicatorsConfig(List<DealerIndicatorConfigDTO> indicatorsConfig) {
		this.indicatorsConfig = indicatorsConfig;
	}



	public void finalize() throws Throwable {

	}
}
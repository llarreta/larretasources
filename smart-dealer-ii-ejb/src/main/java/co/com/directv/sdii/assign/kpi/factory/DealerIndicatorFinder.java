package co.com.directv.sdii.assign.kpi.factory;

import java.util.ResourceBundle;

/**
 * se encarga de encontrar la información necesaria para la construcción de una
 * instancia que calcula algún indicador
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public class DealerIndicatorFinder {

	private static final String KPI_CONFIG_FILE_LOCATION  = "co.com.directv.sdii.assign.config.kpis_config";
	
	public DealerIndicatorFinder(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * obtiene el nombre cualificador de una implementación de indicador dado el
	 * código del mismo
	 * 
	 * @param indicatorCode    código del indicador que se quiere consultar
	 */
	public String getInstanceConfig(String indicatorCode){
		ResourceBundle bundle = ResourceBundle.getBundle(KPI_CONFIG_FILE_LOCATION);
		String result = bundle.getString(indicatorCode);
		return result;
	}
	
}
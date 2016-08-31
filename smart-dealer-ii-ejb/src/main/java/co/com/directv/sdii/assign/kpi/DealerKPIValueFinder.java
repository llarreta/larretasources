package co.com.directv.sdii.assign.kpi;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.exceptions.KpiException;

/**
 * Se encarga de obtener el valor de un KPI dependiendo del tipo de c�lculo:
 * a. en l�nea
 * b. con una frecuencia definida
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public interface DealerKPIValueFinder {

	/**
	 * encuentra el valor de un KPI dados ciertos criterios de b�squeda
	 * 
	 * @param diConfigDto    informaci�n de la configuraci�n de un KPI
	 * @throws KpiException 
	 */
	public DealerIndicatorResultDTO findDealerKPIValue(DealerIndicatorDTO diConfigDto) throws KpiException;

}
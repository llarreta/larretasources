package co.com.directv.sdii.assign.kpi.factory;

import co.com.directv.sdii.assign.kpi.DealerIndicator;

/**
 * define las operaciones para construir las implementaciones específicas que
 * realizan el cálculo de los indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public interface DealerIndicatorFactory {

	/**
	 * construye una clase concreta que calcula el indicador específico
	 * 
	 * @param indicatorCode    código del indicador que se desea construir
	 */
	public DealerIndicator buildDealerIndicator(String indicatorCode);

}
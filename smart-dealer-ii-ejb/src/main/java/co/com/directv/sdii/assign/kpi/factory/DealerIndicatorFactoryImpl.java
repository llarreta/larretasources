package co.com.directv.sdii.assign.kpi.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.kpi.DealerIndicator;

/**
 * implementación de la fábrica de artefactos que realizan el cálculo de
 * indicadores
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:46
 */
public final class DealerIndicatorFactoryImpl implements DealerIndicatorFactory {

	private static Log log = LogFactory.getLog(DealerIndicatorFactoryImpl.class);
	
	private static DealerIndicatorFactory mySelf;
	
	private DealerIndicatorFinder indicatorFinder;

	private DealerIndicatorFactoryImpl(){
		indicatorFinder = new DealerIndicatorFinder();
	}
	
	public static DealerIndicatorFactory getInstance(){
		if(mySelf == null){
			mySelf = new DealerIndicatorFactoryImpl();
		}
		return mySelf;
	}

	/**
	 * construye una clase concreta que calcula el indicador específico
	 * 
	 * @param indicatorCode código del indicador que se desea construir
	 */
	public DealerIndicator buildDealerIndicator(String indicatorCode){
		if(log.isDebugEnabled()){
			log.debug("Se generará la instancia para el indicador con código: " + indicatorCode);
		}
		String kpiClassName = null;
		try {
			kpiClassName = indicatorFinder.getInstanceConfig(indicatorCode);
			DealerIndicator indicator = (DealerIndicator) Class.forName(kpiClassName).newInstance();
			return indicator;
		} catch (Throwable e) {
			String message = "Error al tratar de generar la instancia de la clase para el indicador con código: " + indicatorCode + " el nombre de la clase que se intentó instanciar es: " + kpiClassName;
			log.error(message, e);
			throw new IllegalArgumentException(message);
		} finally{
			if(log.isDebugEnabled()){
				log.debug("Se finaliza la instancia para el indicador con código: " + indicatorCode);
			}
		}
	}
}
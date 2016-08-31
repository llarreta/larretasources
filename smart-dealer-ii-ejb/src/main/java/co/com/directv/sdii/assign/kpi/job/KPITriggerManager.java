package co.com.directv.sdii.assign.kpi.job;

import co.com.directv.sdii.assign.kpi.KPIFacade;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorConfigDTO;

/**
 * Se encarga de administrar los triggers para lanzar la ejecución del job de
 * calcular un indicador para todos los paises, una vez iniciada la aplicación
 * cargará la configuración de todos los indicadores, si están en modo en línea
 * verifica que no exista previamente un trigger que se lance para calcular el
 * indicador, si está en modo de con frecuencia, verifica la periodicidad, si
 * necesita actualizarla lo hará.
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class KPITriggerManager {

	/**
	 * nombre del Job de KPI's
	 */
	private String kpiJobName;
	/**
	 * Clase del Job
	 */
	private Class kpiJobClass;
	
	private KPIFacade kpiFacade;

	public KPITriggerManager(){

	}
	
	/**
	 * inicializa el módulo de ejecución de cálculos de indicadores por país, cargando
	 * las configuraciones de los paises y verificando si han sido actualizados
	 * indicadores y las formas de cálculo
	 */
	public void startupKPIJobModule(){

	}

	/**
	 * Actualiza la frecuencia de ejecución de un job dadas las características
	 * específicas, en caso que se haya actualizado para que sea en línea, se deberá
	 * eliminar e trigger o inactivarlo
	 * 
	 * @param kpiConfig
	 */
	public void updateKPIJobTrigger(DealerIndicatorConfigDTO kpiConfig){

	}

	public KPIFacade getKpiFacade() {
		return kpiFacade;
	}

	public void setKpiFacade(KPIFacade kpiFacade) {
		this.kpiFacade = kpiFacade;
	}

	public String getKpiJobName() {
		return kpiJobName;
	}

	public void setKpiJobName(String kpiJobName) {
		this.kpiJobName = kpiJobName;
	}

	public Class getKpiJobClass() {
		return kpiJobClass;
	}

	public void setKpiJobClass(Class kpiJobClass) {
		this.kpiJobClass = kpiJobClass;
	}
}
package co.com.directv.sdii.assign.kpi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.AssignmentFacadeLocator;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.kpi.factory.DealerIndicatorFactory;
import co.com.directv.sdii.assign.kpi.factory.DealerIndicatorFactoryImpl;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.KpiException;
import co.com.directv.sdii.model.vo.KpiVO;

/**
 * se encarga de calcular en l�nea el valor del KPI para el dealer dadas las
 * caracter�sticas de b�squeda
 * @author jjimenezh
 * @version 1.0,  &nbsp; 26-may-2011 15:11:47
 */
public class OnLineDealerKPIValueFinder implements DealerKPIValueFinder {

	private static Log log = LogFactory.getLog(OnLineDealerKPIValueFinder.class);
	
	private DealerIndicatorFactory diFactory;
	
	public OnLineDealerKPIValueFinder(){
		diFactory = DealerIndicatorFactoryImpl.getInstance();
	}

	public void finalize() throws Throwable {

	}
	/**
	 * encuentra el valor de un KPI dados ciertos criterios de b�squeda
	 * 
	 * @param diConfigDto    informaci�n de la configuraci�n de un KPI
	 * @throws KpiException 
	 */
	public DealerIndicatorResultDTO findDealerKPIValue(DealerIndicatorDTO diConfigDto) throws KpiException{
		if(log.isDebugEnabled()){
			log.debug("Inicia la consulta del valor de un KPI en línea para con los siguientes parámetros: " + diConfigDto.toString());
		}
		try {
			KpiVO kpi = AssignmentFacadeLocator.getInstance().getCoreAssignmentFacade().getKpiById(diConfigDto.getIndicatorId());
			String indicatorCode = kpi.getCode();
			DealerIndicator dealerIndicator = diFactory.buildDealerIndicator(indicatorCode);
			DealerIndicatorResultDTO result = dealerIndicator.calculateIndicator(diConfigDto);
			return result;
		} catch (BusinessException e) {
			log.error("Error al realizar el cálculo del KPI en línea con los siguientes parámentos: " + diConfigDto.toString() + " el error fué: " + e.getMessage(),e);
			e.printStackTrace();
			throw new KpiException(e);
		}finally{
			if(log.isDebugEnabled()){
				log.debug("finaliza la consulta del valor de un KPI en línea para con los siguientes parámetros: " + diConfigDto.toString());
			}
		}
	}
}
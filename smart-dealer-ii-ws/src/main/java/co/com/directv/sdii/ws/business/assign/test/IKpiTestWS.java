package co.com.directv.sdii.ws.business.assign.test;

import javax.jws.WebMethod;
import javax.jws.WebService;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.exceptions.KpiException;

/**
 * 
 * Servicio web que expone las operaciones relacionadas con KPI 
 * 
 * Fecha de Creación: 3/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="KpiTestWS",targetNamespace="http://assign.test.business.ws.sdii.directv.com.co/")
public interface IKpiTestWS {
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI CycleTime
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateCycleTimeIndicator", action="calculateCycleTimeIndicator")
	public DealerIndicatorResultDTO calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI Aging
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateAgingIndicator", action="calculateAgingIndicator")
	public DealerIndicatorResultDTO calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI BackLogInDays
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateBackLogInDaysIndicator", action="calculateBackLogInDaysIndicator")
	public DealerIndicatorResultDTO calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI OnTime
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateOnTimeIndicator", action="calculateOnTimeIndicator")
	public DealerIndicatorResultDTO calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI Retrieval
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateRetrievalIndicator", action="calculateRetrievalIndicator")
	public DealerIndicatorResultDTO calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;
	
	/**
	 * 
	 * Metodo: Invoca el calcular del KPI SoS90
	 * @param dealerIndicatorDto
	 * @return
	 * @throws KpiException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName="calculateSoS90Indicator", action="calculateSoS90Indicator")
	public DealerIndicatorResultDTO calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException;

}

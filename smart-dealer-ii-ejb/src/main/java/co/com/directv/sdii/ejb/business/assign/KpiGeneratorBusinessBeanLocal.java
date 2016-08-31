package co.com.directv.sdii.ejb.business.assign;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * 
 * EJB encargado de realizar los calculos de los KPI 
 * 
 * Fecha de Creación: 2/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface KpiGeneratorBusinessBeanLocal {
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de CyvleTime por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de OnTime por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de BackLog dia por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de SoS90
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de Aging
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los filtros pasados por parametros calcula el indicador de Retrieval por dealer
	 * @param dealerIndicatorDto filtro para realizar el calculo
	 * @param java.util.Date startDate Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @param java.util.Date endDate  Fecha de inicio por la cual se va a filtrar la fecha de atencion
	 * @return Double valor del indicador calculado a partir del filtro enviado
	 * @throws BusinessException En caso de error al calcular el indicador
	 * @author jnova
	 */
	public Double calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto,java.util.Date startDate,java.util.Date endDate) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Dada una lista de dealers, retorna la que tiene menor backlog
	 * @param SkillEvaluationDTO parametros para calcular el skill
	 * @return Lista que contiene el dealer con el backlog menor
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForBalancedCapacitySkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: Dada una lista de dealers, retorna la compañia que cumpla con la condicion que el backlog por dia sea inferior a la meta
	 * o en caso que esto no ocurra, la compañia que mas tiene prioridad de acuerdo al cubrimiento de la compañia
	 * @param SkillEvaluationDTO parametros para calcular el skill
	 * @return Lista de compañias donde se encuentra la que cumple con las condiciones de prioridad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForPrioritizedCapacitySkill(SkillEvaluationDTO parameters) throws BusinessException;
	
	/**
	 * 
	 * Metodo: A partir de los parametro obtiene los dealers que cumplen con la habilidad kpi skill
	 * @param parameters
	 * @return Lista de comañias que cumple con la habilidad
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public List<DealerVO> getDealersForKpiSkill(SkillEvaluationDTO parameters) throws BusinessException;

}

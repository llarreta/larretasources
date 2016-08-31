package co.com.directv.sdii.assign.assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import co.com.directv.sdii.assign.assignment.dto.SkillEvaluationDTO;
import co.com.directv.sdii.assign.assignment.skill.exception.AssignmentSkillException;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.EmailMessageException;
import co.com.directv.sdii.exceptions.PDFException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.exceptions.ServiceLocatorException;
import co.com.directv.sdii.model.vo.DealerVO;

/**
 * Define las operaciones comunes que tendrán las habilidades del módulo de
 * asignador
 * @version 1.0,  &nbsp; 26-may-2011 15:11:45
 */
public abstract class AssignmentSkill {

	private static Log log = LogFactory.getLog(AssignmentSkill.class);
	
	/**
	 * Metodo: convierte la exepción que se obtiene en una administrada por el sistema
	 * @param ex excepción a tratar y convertir en una administrada por el sistema
	 * que el contenedor podrá reconocer para realizar el reverso de la transacción
	 * @param skillName, nombre de la habilidad que genero la excepcion
	 * @return Excepción manejada por el sistema
	 * @author jjimenezh
	 * @author jalopez
	 */
	public AssignmentSkillException manageException(Throwable ex, String skillName){
		
		AssignmentSkillException businessException = new AssignmentSkillException();
		
		if (ex instanceof DAOServiceException) {			
			businessException = new AssignmentSkillException(((DAOServiceException)ex).getMessageCode(), ((DAOServiceException) ex).getMessage(), ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una DAOServiceException ==", businessException);
		} else if (ex instanceof DAOSQLException) {			
			businessException = new AssignmentSkillException(((DAOSQLException)ex).getMessageCode(), ((DAOSQLException) ex).getMessage(),ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una DAOSQLException ==", businessException);
		} else if (ex instanceof PropertiesException){			
			businessException = new AssignmentSkillException(((PropertiesException)ex).getMessageCode(), ((PropertiesException) ex).getMessage(),ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una PropertiesException ==", businessException);
		} else if (ex instanceof EmailMessageException){			
			businessException = new AssignmentSkillException(((EmailMessageException)ex).getMessageCode(), ((EmailMessageException) ex).getMessage(),ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una EmailMessageException ==", businessException);
		} else if (ex instanceof PDFException){			
			businessException = new AssignmentSkillException(((PDFException)ex).getMessageCode(), ((PDFException) ex).getMessage(),ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una PDFException ==", businessException);
		}else if (ex instanceof WebServiceException){			
			businessException = new AssignmentSkillException (ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getCode(),ErrorBusinessMessages.COMUNICATION_IBS_FAIL.getMessageCode()+"["+ex.getMessage()+"]", skillName);
			log.fatal("== Error en la Capa de Negocio debido a una WebServiceException ==", businessException);
		}else if (ex instanceof SOAPFaultException){			
			businessException = new AssignmentSkillException (ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getCode(),ErrorBusinessMessages.NOT_RESPONSE_OBTAINED_SERVICE.getMessageCode()+"["+((SOAPFaultException)ex).getFault().getFaultString()+"]", skillName);
			log.fatal("== Error en la Capa de Negocio debido a una SOAPFaultException ==", businessException);
		}else if (ex instanceof  BusinessException){			
			BusinessException businessException1 = (BusinessException)ex;
			businessException = new AssignmentSkillException(businessException1.getMessageCode(), businessException1.getMessage(), businessException1, skillName, businessException1.getParameters());
			log.error("== Error en la Capa de Negocio debido a una BusinessException ==", businessException);
		}else if (ex instanceof  ServiceLocatorException){			
			businessException = new AssignmentSkillException(((ServiceLocatorException)ex).getMessageCode(), ((ServiceLocatorException) ex).getMessage(),ex, skillName);
			log.error("== Error en la Capa de Negocio debido a una ServiceLocatorException ==", businessException);
		}else{			
			businessException = new AssignmentSkillException(ErrorBusinessMessages.UNKNOW_ERROR.getCode() ,ex.getMessage()+ " "+ UtilsBusiness.dateToString(new Date(), "yyyy/MM/dd HH:mm:ss"), ex, skillName);
			log.error("== Error general ==", businessException);
		}
		return businessException;
	}
	
	/**
	 * Remueve de una lista los dealers que no están en otra, se usa para evaluar las habilidades eliminantes
	 * en donde si un dealer no cumple ciertas condiciones dadas se debe sacar de la lista
	 * @param inputDealerList dealers Lista de dealers base desde donde se empezará a evaluar si están en el subconjunto de los
	 * dealers a remover
	 * @param skillDealerList dealersToRemove dealers que deben ser tenidos en cuenta para el subconjunto de dealers, si en la primera lista no está
	 * uno de los dealers de la segunda será borrado de dicha lista, si por el contrario el dealer de la
	 * segunda lista no está en la primera, este no será agregado, ya que se entiende que pudo ser excluido en una 
	 * evaluación anterior
	 * @return
	 */
	public List<DealerVO> intersectDealerList(List<DealerVO> inputDealerList, final List<DealerVO> skillDealerList){
		//Itera la lista de Dealers removibles para removerlos de la lista de deales
		List<DealerVO> resultList = new ArrayList<DealerVO>();
		resultList.addAll(inputDealerList);
		CollectionUtils.filter(resultList, new Predicate() {
			
			@Override
			public boolean evaluate(Object obj) {
				if (obj instanceof DealerVO) {
					DealerVO dealer = (DealerVO) obj;
					if(! skillDealerList.contains(dealer)){
						return false;
					}
				}
				return true;
			}
		});
		return resultList;
	}
	
	/**
	 * realiza la evaluación de la habilidad dados los par�metros
	 * 
	 * @param parameters    par�metros para realizar la evaluación de la habilidad
	 * @throws AssignmentSkillException
	 */
	public abstract List<DealerVO> doSkillEvaluation(SkillEvaluationDTO parameters)throws AssignmentSkillException;

}
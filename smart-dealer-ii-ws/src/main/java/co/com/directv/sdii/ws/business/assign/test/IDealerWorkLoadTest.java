package co.com.directv.sdii.ws.business.assign.test;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.exceptions.ScheduleException;

/**
 * 
 * Interfaz que expone metodos para calculo de carga de dealer 
 * 
 * Fecha de Creación: 7/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="IDealerWorkLoadTest",targetNamespace="http://assign.test.business.ws.sdii.directv.com.co/")
public interface IDealerWorkLoadTest {
	
	/**
	 * 
	 * Metodo: Invoca directamente el calculo de la carga de un dealer
	 * @param dealerWCCriteria parametros para calculo de la carga
	 * @return
	 * @throws ScheduleException <tipo> <descripcion>
	 * @author jnova
	 */
	@WebMethod(operationName = "getDealerWorkLoad", action = "getDealerWorkLoad")
	public DealerWorkLoad getDealerWorkLoad(@WebParam(name = "dealerWCCriteria") DealerWorkCapacityCriteria dealerWCCriteria) throws ScheduleException;

}

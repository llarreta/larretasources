package co.com.directv.sdii.ws.test;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * 
 * Servicio web para test 
 * 
 * Fecha de CreaciÃ³n: 03/02/2015
 * @author 
 * @version 1.0
 * 
 * @see
 */
@WebService(name="ExternalServiceTestWS",targetNamespace="http://directvla.com.contract/ws/sdii/Test")
public interface IExternalServiceTestWS {
	
	@WebMethod(operationName="getAllServicesPort", action="getAllServicesPort")
	public List<String> getAllServicesPort() throws BusinessException;
}

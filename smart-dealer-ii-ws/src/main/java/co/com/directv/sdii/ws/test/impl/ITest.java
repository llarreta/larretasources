package co.com.directv.sdii.ws.test.impl;

import javax.jws.WebMethod;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;

/**
 * 
 * Servicio web para test 
 * 
 * Fecha de Creación: 13/08/2014
 * @author 
 * @version 1.0
 * 
 * @see
 */
@WebService(name="TestWS",targetNamespace="http://directvla.com.contract/ws/sdii/Test")
public interface ITest {
	@WebMethod(operationName="getServiceClient", action="getServiceClient")
	public  void getServiceClient() throws BusinessException;
	
	@WebMethod(operationName="test6008", action="test6008")
	public  void test6008(String woCode) throws BusinessException;
	
}

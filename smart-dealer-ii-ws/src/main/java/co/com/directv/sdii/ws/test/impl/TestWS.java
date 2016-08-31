package co.com.directv.sdii.ws.test.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal;
import co.com.directv.sdii.exceptions.BusinessException;
//import co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal;
//import co.com.directv.sdii.exceptions.BusinessException;
//import co.com.directv.sdii.model.vo.BusinessTierVersionVO;

/**
 * 
 * Servicio web para consultar las versiones de la capa de negocio. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ws.version.IBusinessTierVersionWS
 */
@MTOM
@WebService(serviceName="TestService",
		endpointInterface="co.com.directv.sdii.ws.test.impl.ITest",				           
		targetNamespace="http://directvla.com.contract/ws/sdii/test",
		portName="TestServicePort")
@Stateless()
public class TestWS implements ITest {

	@EJB
	private BusinessTierVersionFacadelocal facade;
	
	//@Override
	//public BusinessTierVersionVO getBusinessVersion() throws BusinessException {
		//return facade.getBusinessVersion();
	//}
	
	@Override
	@WebMethod(operationName = "getServiceClient", action = "getServiceClient")
	public void getServiceClient() throws BusinessException {
		
		
	}
	
	@Override
	@WebMethod(operationName="test6008", action="test6008")
	public void test6008(String woCode) throws BusinessException{
		
		facade.test6008(woCode);
		
	}
	
}



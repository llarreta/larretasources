package co.com.directv.sdii.ws.test.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.test.ExternalServiceTestFacadeBeanLocal;
import co.com.directv.sdii.ws.test.IExternalServiceTestWS;
//import co.com.directv.sdii.ejb.business.facade.BusinessTierVersionFacadelocal;
//import co.com.directv.sdii.exceptions.BusinessException;
//import co.com.directv.sdii.model.vo.BusinessTierVersionVO;

/**
 * 
 * Servicio web para consultar las versiones de la capa de negocio. 
 * 
 * Fecha de CreaciÃ³n: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ws.version.IBusinessTierVersionWS
 */
@MTOM
@WebService(serviceName="ExternalServiceTestService",
		endpointInterface="co.com.directv.sdii.ws.test.IExternalServiceTestWS",				           
		targetNamespace="http://directvla.com.contract/ws/sdii/test",
		portName="ExternalServiceTestServicePort")
@Stateless()
public class ExternalServiceTestWS implements IExternalServiceTestWS {

	@EJB(name="ExternalServiceTestFacadeBean")
	private ExternalServiceTestFacadeBeanLocal externalServiceTestFacadeBean;
	
	@Override
	@WebMethod(operationName = "getAllServicesPort", action = "getAllServicesPort")
	public List<String> getAllServicesPort() throws BusinessException {
		return externalServiceTestFacadeBean.getAllServicesPort();		
	}

}



package co.com.directv.sdii.ws.business.core.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.file.FileProcessorFacadeBeanLocal;
import co.com.directv.sdii.ws.business.core.IFileProcessorWS;

/**
 * @author cduarte
 *
 */
@MTOM(threshold=3072)
@WebService(serviceName="FileProcessorWSService",
		endpointInterface="co.com.directv.sdii.ws.business.core.IFileProcessorWS",
		targetNamespace="http://core.business.ws.sdii.directv.com.co/",
		portName="FileProcessorWSPort")	
@Stateless()
public class FileProcessorWS   implements IFileProcessorWS {

	@EJB(name="FileProcessorFacadeBeanLocal", beanInterface=FileProcessorFacadeBeanLocal.class)
	private FileProcessorFacadeBeanLocal fileProcessorFacade;

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.core.IFileProcessorWS#processFiles(java.lang.Long)
	 */
	public void processFiles(Long idCountry)  throws BusinessException {
       fileProcessorFacade.processFiles(idCountry) ; 		
	}
		
}
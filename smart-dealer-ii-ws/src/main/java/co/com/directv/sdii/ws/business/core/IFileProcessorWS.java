package co.com.directv.sdii.ws.business.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;

@WebService(name="FileProcessorWS",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IFileProcessorWS {
	
	/**
	 * Permite procesar los archivos segun el parametro pais 
	 * @param idCountry Identificador del pais para el cual queremos procesar los archivos
	 * @throws BusinessException
	 */
	@WebMethod(operationName = "processFiles", action = "processFiles")
	public void processFiles(@WebParam( name = "idCountry") Long idCountry)  throws BusinessException;
	
}

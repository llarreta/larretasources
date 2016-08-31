package co.com.directv.sdii.ejb.business.broker.toa;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.Constantes;
import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.broker.impl.MarkWorkOrderServiceBrokerImpl;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * 
 * Clase encargada de obtner objetos de negocio
 * basados en objetos de de Contratos de Web Services
 * 
 * Fecha de Creación: 18/07/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class MarkWorkOrderServiceTOA {
	
	private final static Logger log = UtilsBusiness.getLog4J(MarkWorkOrderServiceBrokerImpl.class);
	
	private static final String REPORT_TEMPLATES_SUB_DIR = "templates/";
	
	/**
	 * Metodo: Permite construir una instancia de com.directvla.schema.businessdomain.markworkorder.RequiredContractWorkOrderRequest
	 * @param countryCode
	 * @param woCode
	 * @param customerCode
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static  com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeRequest buildRequiredContractWorkOrderRequest(ContractWorkOrderRequestDTO request){
		
		com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeRequest requiredAnnexIBSCustomerCodeRequest = new com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeRequest();
		requiredAnnexIBSCustomerCodeRequest.setRequestMetadata(buildRequestMetadataType(request.getCountryCode()));
		requiredAnnexIBSCustomerCodeRequest.setRequiredAnnexIBSCustomerCode((buildRequiredAnnexIBSCustomerCode(request.getServiceIbsCode(),request.getCustomerCode())));
		return requiredAnnexIBSCustomerCodeRequest;
	}
	
	/**
	 * Metodo: Permite construir una instancia de com.directvla.schema.businessdomain.markworkorder.RequestMetadataType
	 * @param countryCode
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static  com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType buildRequestMetadataType(String countryCode){
		
		com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType requestMetadataType = new com.directvla.schema.util.commondatatypes.v1_0.RequestMetadataType();
		requestMetadataType.setRequestID(countryCode);
		requestMetadataType.setSourceID(countryCode);
		try {
			requestMetadataType.setSystemID(CodesBusinessEntityEnum.SYSTEM_NAME_HSP.getCodeEntity());
		}catch (Throwable ex) { 
	         ex.printStackTrace();
	         log.debug("== Error al tratar de ejecutar la operaciÃ³n buildRequestMetadataType/MarkWorkOrderServiceTOA ==");
	    }finally{
	         log.debug("== Termina la operaciÃ³n buildRequestMetadataType/MarkWorkOrderServiceTOA ==");
	    }
		return requestMetadataType;
		
	}
	
	/**
	 * Metodo: Permite construir una instancia de com.directvla.schema.businessdomain.markworkorder.WorkOrder
	 * @param woCode
	 * @param customerCode
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeType buildRequiredAnnexIBSCustomerCode(String serviceIbsCode,String customerCode){
		
		com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeType requiredAnnexIBSCustomerCodeType = new com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.RequiredAnnexIBSCustomerCodeType();
		requiredAnnexIBSCustomerCodeType.setIBSCustomerCode(Integer.parseInt(customerCode));
		
		if(serviceIbsCode!=null){
			requiredAnnexIBSCustomerCodeType.setServiceId(Integer.parseInt(serviceIbsCode));
		}
		return requiredAnnexIBSCustomerCodeType;
		
	}
	
	/**
	 * Metodo: Permite construir una instancia de com.directvla.schema.businessdomain.markworkorder.GetDownLoadContractWorkOrderRequest
	 * @param countryCode
	 * @param woCode
	 * @param customerCode
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeRequest buildGetDownLoadContractWorkOrderRequest(ContractWorkOrderRequestDTO request){
		
		com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeRequest getAnnexIBSCustomerCodeRequest = new com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeRequest();
		getAnnexIBSCustomerCodeRequest.setRequestMetadata(buildRequestMetadataType(request.getCountryCode()));
		getAnnexIBSCustomerCodeRequest.setGetAnnexIBSCustomerCode((buildGetAnnexIBSCustomerCode(request.getServiceIbsCode(),request.getCustomerCode())));
		return getAnnexIBSCustomerCodeRequest;
		
	}
	
	/**
	 * Metodo: Permite construir una instancia de com.directvla.schema.businessdomain.markworkorder.WorkOrder
	 * @param woCode
	 * @param customerCode
	 * @return <tipo> <descripcion>
	 * @author
	 */
	public static  com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeType buildGetAnnexIBSCustomerCode(String serviceIbsCode,String customerCode){
		
		com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeType getAnnexIBSCustomerCodeType = new com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeType();
		
		getAnnexIBSCustomerCodeType.setIBSCustomerCode(Integer.parseInt(customerCode));
		
		if(serviceIbsCode!=null){
			getAnnexIBSCustomerCodeType.setServiceId(Integer.parseInt(serviceIbsCode));
		}
		return getAnnexIBSCustomerCodeType;
		
	}
	
	public static FileResponseDTO buildFileResponseDTO(com.directvla.contract.businessdomain.hspsupportandreadiness.v1_0.GetAnnexIBSCustomerCodeResponseType getAnnexIBSCustomerCodeResponseType) throws IOException, PropertiesException{
		
		FileResponseDTO response = new FileResponseDTO();
		
		if(getAnnexIBSCustomerCodeResponseType!=null && getAnnexIBSCustomerCodeResponseType.getOut()!=null && getAnnexIBSCustomerCodeResponseType.getOut().getFile()!=null){
			
			if(getAnnexIBSCustomerCodeResponseType.getOut().getType().equals(CodesBusinessEntityEnum.CODE_TYPE_DOCUMENT_CONTRACT_EXTERNAL.getCodeEntity())){
				BufferedOutputStream bos = null;
				String reportsPath = PropertiesReader.getInstance().getAppKey(Constantes.LABEL_RUTA_REPORTS);
				//StringBuffer reportName = UtilsBusiness.generateSingleWOReportName(reportsPath+REPORT_TEMPLATES_SUB_DIR, getAnnexIBSCustomerCodeResponseType.getOut().getName());
				StringBuffer reportName = UtilsBusiness.generateSingleWOReportName("", getAnnexIBSCustomerCodeResponseType.getOut().getName());
				response.setFileName(reportName.toString());
		
				DataSource dataSource = new  ByteArrayDataSource( getAnnexIBSCustomerCodeResponseType.getOut().getFile() , "application/pdf" );
				DataHandler dataHandler = new DataHandler(dataSource); 
				response.setDataHandler(dataHandler);
			}
		}

		return response;
		
	}
	
	/**
	 * 
	 * Metodo: Escribe en el log la operacion
	 * consumida del servicio
	 * @param operation String
	 * @param sourceId String
	 * @author jalopez
	 */
	public static void logOperationInvoke(String operation, String countryCode,String woCode,String serviceIbsCode){
		//Variables Globales
		StringBuilder buffer = new StringBuilder();
		StringBuilder message = new StringBuilder();
		
		//Parametros del errror.
		Object[] params = null;
		params = new Object[2];	
		
		buffer.append(logMetadata(countryCode,woCode,serviceIbsCode));
		
		//Construcccion del mensaje
		params[0] = operation;
		params[1] = buffer.toString();
		message.append("== ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getCode() );
		message.append(" : ");
		message.append( ErrorBusinessMessages.BUSINESS_BL188.getMessage(params) );
		message.append(" ==");
		log.info( message.toString() );
		
	}
	
	/**
	 * 
	 * Metodo: Retorna el log de la informacion
	 * enviada de la Metadata
	 * @param inventoryDTO
	 * @return String
	 * @author jalopez
	 */
	private static String logMetadata(String countryCode,String serviceIbsCode,String customerCode){
		StringBuilder buffer = new StringBuilder();
		buffer.append(" Metadata [");
		buffer.append(" countryCode: "+countryCode);
		buffer.append(" serviceIbsCode: "+serviceIbsCode);
		buffer.append(" customerCode: "+customerCode);
		buffer.append(" ]");
		return buffer.toString();
	}
	
}

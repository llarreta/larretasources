package co.com.directv.sdii.ws.business.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.ws.model.dto.ResponseSendMailDTO;

@WebService(name="MailService",targetNamespace="http://core.business.ws.sdii.directv.com.co/")
public interface IMailWS {

	/**
	 * Envia las workorders por mail donde se le especifican unos nombres de archivo
	 * @author Leonardo Cardozo Cadavid - lcardozo@intergrupo.com
	 * @param fileNames
	 * @param filterCrewId
	 * @throws BusinessException en caso de enviar las workorders por correo electronico
	 * a continuación los códigos de error:<br>
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_report_path_invalid</code> En caso de un error de URL reporte inválida<br>
     * <code>sdii_CODE_invalid_mail</code> En caso de un error de URL reporte inválida<br>
     */
	@WebMethod(operationName = "sendWorkOrdersPDFEmail", action="sendWorkOrdersPDFEmail")
	public ResponseSendMailDTO sendWorkOrdersPDFEmail(@WebParam(name = "fileNames")String[] fileNames, @WebParam(name = "filterCrewId")Long filterCrewId,  @WebParam(name = "countryId")Long countryId) throws BusinessException;
}

package co.com.directv.sdii.common.enumerations;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.persistence.hibernate.ConnectionFactory;
import co.com.ig.common.error.ErrorMessageManager;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de codigos de errores y mensajes de los servicios expuestos a IVR.
 * 
 * Fecha de Creaci�n: 10/08/2010
 * @author jforero <a href="mailto:jforero@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public enum IvrBusinessMessagesEnum {

	WORK_ORDER_NOT_FOUND("sdii_CODE_ivr_work_order_not_found","sdii_MESSAGE_ivr_work_order_not_found","IV100"),
    DEALER_NOT_FOUND("sdii_CODE_ivr_dealer_not_found","sdii_MESSAGE_ivr_dealer_not_found","IV101"),
    SHIPPING_ORDER_NOT_FOUND("sdii_CODE_ivr_shipping_order_not_found","sdii_MESSAGE_ivr_shipping_order_not_found","IV102"),
    WAREHOUSE_ELEMENT_NOT_FOUND("sdii_CODE_ivr_warehouse_element_not_found","sdii_MESSAGE_ivr_warehouse_element_not_found","IV103"),
    ELEMENT_NOT_FOUND("sdii_CODE_ivr_element_not_found","sdii_MESSAGE_ivr_element_not_found","IV104"),
    CREW_WAREHOUSE_NOT_FOUND("sdii_CODE_ivr_crew_warehouse_not_found","sdii_MESSAGE_ivr_crew_warehouse_not_found","IV105"),
    CUSTOMER_WAREHOUSE_NOT_FOUND("sdii_CODE_ivr_customer_warehouse_not_found","sdii_MESSAGE_ivr_customer_warehouse_not_found","IV106"),
    SERIALIZED_NOT_FOUND("sdii_CODE_ivr_serialized_not_found","sdii_MESSAGE_ivr_serialized_not_found","IV107"),
    EXCEPTION_ERROR("sdii_CODE_ivr_exception_error","sdii_MESSAGE_ivr_exception_error","IV109"),
    UNKNOWN_ERROR("sdii_CODE_ivr_unknow_error","sdii_MESSAGE_ivr_unknow_error","IV108"),
    CREW_NOT_FOUND("sdii_CODE_ivr_crew_not_found","sdii_MESSAGE_ivr_crew_not_found","IV110")
    ;

    private String code;
    private String message;
    private String reason;
    private final static Logger log = UtilsBusiness.getLog4J(IvrBusinessMessagesEnum.class);

    IvrBusinessMessagesEnum(String pErrorCode, String pEMessage,String pEReason) {
        this.code = pErrorCode;
        this.message = pEMessage;
        this.reason = pEReason;
    }

    /**
     * 
     * Metodo: Retorna el codigo del error, 
     * obtenido de el archivo de propiedades.
     * Properties File:  BusinessMessages.properties
     * @return String
     * @author jforero 10/08/2010
     */
    public String getCode() {
    	String errorCode = null;
    	try {
    		ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
        	errorCode = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorCode();
        } catch (Exception e) {
            log.error("Error en la lectura de mensajes de error", e);
        }
        return errorCode;
    }

    /**
     *
     * Metodo: Retorna el mensaje del error,
     * obtenido de el archivo de propiedades.
     * Properties File:  BusinessMessages.properties
     * @return String
     * @author jforero 10/08/2010
     */
    public String getMessage() {
    	String errorMessage = null;
        try {
        	ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
        	errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage();
        } catch (Exception e) {
            log.error("Error en la lectura de mensajes de error", e);
        }
        return errorMessage;
    }
    
    /**
    *
    * Metodo: Retorna el codigo+mensaje del error,
    * obtenido de el archivo de propiedades.
    * Properties File:  BusinessMessages.properties
    * @return String
    * @author jforero 10/08/2010
    */
   public String getMessageCode() {
	   StringBuffer buffer = new StringBuffer();
	   String errorCode, errorMessage;
       try {   	       	   
    	   ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
    	   errorCode = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorCode();
    	   errorMessage = ErrorMessageManager.getInstance().getErrorMessageByErrorKey(code).getErrorMessage(); 
    	   buffer.append(errorCode);
           buffer.append(":::");
           buffer.append(errorMessage); 
       } catch (Exception e) {
           log.error("Error en la lectura de mensajes de error", e);
       }
       return buffer.toString();
   }
   
   /**
	 * Metodo: Devuelve la descripcion de la razon por la cual se produjo el error
	 * @return String Descripcion de la razon
	 * @author jforero 11/08/2010
	 */
	public String getReason() {
   	String result = null;
   	try {
   		ErrorMessageManager.getInstance().setSessionFactory(ConnectionFactory.getSessionFactory());
   		result = ErrorMessageManager.getInstance().getErrorReasonByReasonCode(reason).getReasonName();
   	} catch (Exception e) {
           log.error("Error en la lectura de reason de error", e);
       }
       return result;
   }
}

package co.com.directv.sdii.common.enumerations;

import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase de tipo Enum, encargada de realizar el mapping
 * de los wsdl locations de los servicios, los cuales se
 * encuentran ubicados en un archivo de propiedades externo a la 
 * aplicacion.
 * 
 * Fecha de Creacion: 12/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see properties.WsdlLocations.properties
 */
public enum WsdlLocationsEnum {
	
	DEALERS_WSDL_LOCATION("ibs_dealers_wsdl_location","ibs_dealers_qname_namespace","ibs_dealers_qname_service","ibs_dealers_end_point_name","ibs_dealers_end_point_uri"),
	CORE_WSDL_LOCATION("ibs_core_wsdl_location","ibs_core_qname_namespace","ibs_core_qname_service","ibs_core_end_point_name", "ibs_core_end_point_uri"),
	INVENTORY_WSDL_LOCATION("ibs_inventory_wsdl_location","ibs_inventory_qname_namespace","ibs_inventory_qname_service","ibs_inventory_end_point_name", "ibs_inventory_end_point_uri"),
	CUSTOMER_WSDL_LOCATION("ibs_customer_wsdl_location","ibs_customer_qname_namespace","ibs_customer_qname_service","ibs_customer_end_point_name","ibs_customer_end_point_uri"),	
	CUSTOMER_INTERFACE_MANAGMENT_WSDL_LOCATION("ibs_customer_interface_managment_wsdl_location","ibs_customer_interface_managment_qname_namespace","ibs_customer_interface_managment_qname_service","ibs_customer_interface_managment_end_point_name", "ibs_customer_interface_managment_end_point_uri"),	
	MARK_WORK_ORDER_WSDL_LOCATION("ibs_mark_work_order_wsdl_location","ibs_mark_work_order_qname_namespace","ibs_mark_work_order_qname_service","ibs_mark_work_order_end_point_name", "ibs_mark_work_order_end_point_uri"),	
	CUSTOMER_VISTA360_WSDL_LOCATION("ibs_customer_vista360_wsdl_location","ibs_customer_vista360_qname_namespace","ibs_customer_vista360_qname_service","ibs_customer_vista360_end_point_name", "ibs_customer_vista360_end_point_uri"),
	ALLOCATOR_BPM_WSDL_LOCATION("allocator_bpm_wsdl_location","allocator_bpm_qname_namespace","allocator_bpm_qname_service","allocator_bpm_end_point_name", "allocator_bpm_end_point_uri"), 
	ATTENTION_BPM_WSDL_LOCATION("attention_bpm_wsdl_location","attention_bpm_qname_namespace","attention_bpm_qname_service","attention_bpm_end_point_name", "attention_bpm_end_point_uri"),
	CUSTOMER_CONTACTS_WSDL_LOCATION("ibs_contacts_wsdl_location","ibs_contacts_qname_namespace","ibs_contacts_qname_service","ibs_contacts_end_point_name", "ibs_contacts_end_point_uri"),
	ALLOCATOR_SDII_WSDL_LOCATION("allocator_sdii_wsdl_location","allocator_sdii_qname_namespace","allocator_sdii_qname_service","allocator_sdii_end_point_name", "allocator_sdii_end_point_uri"),
	ATTENTION_SDII_WSDL_LOCATION("attention_sdii_wsdl_location","attention_sdii_qname_namespace","attention_sdii_qname_service","attention_sdii_end_point_name", "attention_sdii_end_point_uri"),
	MANAGE_WORK_FORCE_WSDL_LOCATION("ibs_manage_work_force_wsdl_location","ibs_manage_work_force_qname_namespace","ibs_manage_work_force_qname_service","ibs_manage_work_force_end_point_name", "ibs_manage_work_force_end_point_uri"),
	INVENTORY_INTERFACE_WSDL_LOCATION("ibs_inventory_interface_wsdl_location","ibs_inventory_interface_qname_namespace","ibs_inventory_interface_qname_service","ibs_inventory_interface_end_point_name", "ibs_inventory_interface_end_point_uri"),
	RESOURCE_PROVISIONING_WSDL_LOCATION("ibs_resource_provisioning_wsdl_location","ibs_resource_provisioning_qname_namespace","ibs_resource_provisioning_qname_service","ibs_resource_provisioning_end_point_name", "ibs_resource_provisioning_end_point_uri"),
	SHIPPING_ORDER_WSDL_LOCATION("shipping_order_wsdl_location","shipping_order_qname_namespace","shipping_order_qname_service","shipping_order_end_point_name", "shipping_order_end_point_uri"),
	SERVICE_PROBLEM_MANAGEMENT_WSDL_LOCATION("ibs_service_problem_management_wsdl_location","ibs_service_problem_management_qname_namespace","ibs_service_problem_management_qname_service","ibs_service_problem_management_end_point_name", "ibs_service_problem_management_end_point_uri"),
	SPRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION("ibs_inventory_SPRMSupportAndReadiness_wsdl_location","ibs_inventory_SPRMSupportAndReadiness_qname_namespace","ibs_inventory_SPRMSupportAndReadiness_qname_service","ibs_inventory_SPRMSupportAndReadiness_end_point_name", "ibs_inventory_SPRMSupportAndReadiness_end_point_uri"),
	CRMSUPPOT_AND_READINESS_INVENTORY_WSDL_LOCATION("ibs_inventory_CRMSupportAndReadiness_wsdl_location","ibs_inventory_CRMSupportAndReadiness_qname_namespace","ibs_inventory_CRMSupportAndReadiness_qname_service","ibs_inventory_CRMSupportAndReadiness_end_point_name", "ibs_inventory_CRMSupportAndReadiness_end_point_uri"),
	SERVICE_CONFIGURATION_AND_ACTIVATION("ibs_service_configuration_and_activation_wsdl_location","ibs_service_configuration_and_activation_qname_namespace","ibs_service_configuration_and_activation_qname_service","ibs_service_configuration_and_activation_end_point_name", "ibs_service_configuration_and_activation_end_point_uri"),	
	SERVICE_ORDER_HANLING_SERVICE("ibs_service_order_hanling_service_wsdl_location","ibs_service_order_hanling_service_qname_namespace","ibs_service_order_hanling_service_qname_service","ibs_service_order_hanling_service_end_point_name", "ibs_service_order_hanling_service_end_point_uri"),	
	;
	
	
	private String wsdlLocation;
	private String qnameNamespace;
	private String qnameService;
	private String qnameEndPointService;
	private String endPointService;
	
	
	/**
	 * Constructor:
	 * @param pWsdlLocation
	 * @param pQnameNamespace
	 * @param pQameService
	 * @param pQnameEndPointService
	 * @param pEndPointService
	 * @author
	 */
	WsdlLocationsEnum(	String pWsdlLocation,
						String pQnameNamespace,
						String pQameService, 
						String pQnameEndPointService,
						String pEndPointService
						){
		this.wsdlLocation = pWsdlLocation;
		this.qnameNamespace = pQnameNamespace;
		this.qnameService = pQameService;
		this.qnameEndPointService = pQnameEndPointService;
		this.endPointService = pEndPointService;
	}
	
	/**
	 * Metodo: Obtiene el endpoint
	 * @return cadena con el nombre del endpoint
	 * @author
	 */
	public String getQnameEndPointService() throws PropertiesException{
		PropertiesReader reader = null;

		reader = UtilsBusiness.getWsdlLocationsProperties();
		String location = reader.getKey(this.qnameEndPointService);
		this.qnameEndPointService = location != null ? location : this.qnameEndPointService;
		return qnameEndPointService;
	}
	
	/**
    *
    * Metodo: Retorna el wsdl location
    * del servicio.
    * @return String wsdlLocation
    * @author Joan Lopez
    */
   public String getWsdlLocation() throws PropertiesException{
   		PropertiesReader reader = null;
   
       reader = UtilsBusiness.getWsdlLocationsProperties();
       String location = reader.getKey(this.wsdlLocation);
       this.wsdlLocation = location != null ? location : this.wsdlLocation;
       
       return this.wsdlLocation;
   }
   
   /**
   *
   * Metodo: Retorna el namespace del servicio
   * @return String qnameNamespace
   * @author Joan Lopez
   */
  public String getQnameNameSpace() throws PropertiesException{
  		PropertiesReader reader = null;
  
      reader = UtilsBusiness.getWsdlLocationsProperties();
      String qname = reader.getKey(this.qnameNamespace);
      this.qnameNamespace = qname != null ? qname : this.qnameNamespace;
      
      return this.qnameNamespace;
  }
  
  /**
  *
  * Metodo: Retorna el qname del servicio 
  * @return String qnameService
  * @author Joan Lopez
  */
 public String getQnameService() throws PropertiesException{
 		PropertiesReader reader = null;
 
     reader = UtilsBusiness.getWsdlLocationsProperties();
     String qname = reader.getKey(this.qnameService);
     this.qnameService = qname != null ? qname : this.qnameService;
     
     return this.qnameService;
 }
 
 
 /**
 *
 * Metodo: Retorna el endpoint del servicio 
 * @return String endPointService
 * @author ialessan
 */
public String getEndPointService() throws PropertiesException{
		PropertiesReader reader = null;

    reader = UtilsBusiness.getWsdlLocationsProperties();
    String endPointService = reader.getKey(this.endPointService);
    this.endPointService = endPointService != null ? endPointService : this.endPointService;
    
    return this.endPointService;
}
}

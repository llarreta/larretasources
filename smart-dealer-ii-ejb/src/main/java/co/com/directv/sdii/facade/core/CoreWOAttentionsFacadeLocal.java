package co.com.directv.sdii.facade.core;

/**
 * Interfaz que define los métodos de fachada para el proceso de atenciones
 * de una WorkOrder.
 * 
 * @author Jimmy Vélez Muñoz
 */
//@Local
public interface CoreWOAttentionsFacadeLocal {

//	/**
//	 * Valida los datos de la petición de atención de una nueva WorkOrder.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void validarDatosWOAttentions(WOAttentionsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Realiza la atención de un servicio de una WorkOrder. 
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @return ServiceAttendResponseDTO
//	 * @throws BusinessException
//	 */
//	public ServiceAttendResponseDTO atenderService(ServiceAttentionRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Reporta a SDII y a IBS los elementos utilizados en la atención de los servicios de una WO.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void reportarElementosUtilizadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Reporta a SDII y a IBS los elementos recuperados en la atención de los servicios de una WO.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void reportarElementosRecuperadosEnServicios(WOAttentionElementsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Activa los elementos utilizados en la atención de los servicios en IBS.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void activarElementosEnIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Informa los cambios de IRD a IBS.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void informarCambioDeIRDIBS(WOAttentionElementsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Actualiza cambio de estado de la WorkOrder en SDII y en IBS.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void actualizarCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Crea una nueva WorkOrder en SDII cuando existen servicios que no fueron atendidos
//	 * en una WorkOrder que está siendo atendida.
//	 * 
//	 * @author jvelezmu
//	 * @param request
//	 * @throws BusinessException
//	 */
//	public void crearNuevaWO(CreateNewWORequestDTO request) throws BusinessException;
//
//	/**
//	 * Metodo: <Descripcion>
//	 * @param request <tipo> <descripcion>
//	 * @author
//	 * @throws BusinessException 
//	 */
////	public void compensarWOAttention(WOAttentionsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Metodo: Compensa la notificación de elementos serializados en IBS
//	 * @param request información del request
//	 * @param elementsNotified información de los elementos notificados
//	 * @throws BusinessException En caso de error al realizar la notificación
//	 * @author jjimenezh
//	 */
//	public void compensarNotificacionIbsElementosSerializados(WOAttentionsRequestDTO request, ReportarIBSElementosSerializadosResponseDTO elementsNotified) throws BusinessException;
//	
//	/**
//	 * Metodo: Notifica a IBS los elementos serializados usados o recuperados en el curso de la atención del servicio
//	 * @param request información de la atención la WO con los elementos utilizados
//	 * @return Información de los elementos que fueron notificados exitosamente a IBS
//	 * @throws BusinessException En caso de error en la notificación de los elementos a IBS
//	 * @author jjimenezh
//	 */
//	public ReportarIBSElementosSerializadosResponseDTO reportarIBSElementosSerializados(WOAttentionElementsRequestDTO request) throws BusinessException;
//	
//	/**
//	 * Metodo: Notifica a IBS el cambio de estado de la work order atendida
//	 * @param request información de la atención de la Work Order
//	 * @throws BusinessException En caso de error al realizar la notificación a IBS
//	 * @author jjimenezh
//	 */
//	public void notificarIbsCambioDeEstadoWO(WOAttentionsRequestDTO request) throws BusinessException;

}

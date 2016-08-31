/**
 * Creado 2/11/2010 14:18:23
 */
package co.com.directv.sdii.ejb.business.core;

import java.util.Date;

import javax.ejb.Local;

import co.com.directv.sdii.dto.esb.event.publishworkorderevent.PublishWorkOrderEvent;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.WorkOrderDTO;

/**
 * Encapsula la lógica de negocio encargada de generar wo, se usa como intermediario
 * para envolver cada wo en una transacción independiente
 * 
 * Fecha de Creación: 2/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see 
 */
@Local
public interface CoreWOImporterLocal {

	/**
	 * Metodo: Crea las wo desde ibs envolviendo cada WO en una transacción independiente
	 * @param countryCode código del país en donde se realizará la importación de wo
	 * @throws BusinessException En caso de error al realizar la importación
	 * @author jjimenezh, aharker
	 */
	public void addWorkOrder(String countryCode) throws BusinessException;
	
	/**
	 * Control de cambios MigracionRESB 
	 * 
	 * Método que se encarga de actualizar cierta informacion de HSP
	 * a una workOrder consultada en ESB. 
	 * @param countryCode codigo del pais de la workOrder
	 * @param woCode Codigo del a workOrder a actualizar
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public void updateInfoWorkOrderIBSToHSP(String countryCode,String woCode) throws BusinessException;
	
	/**
	 * Metodo que se encarga de realizar la descarga de una work order para el procesamiento en paralelo de core
	 * @param ibsWorkorder objeto proveniente del servicio de descarga
	 * @param workOrderDto DTO a llenar con todos los datos de la Work Order
	 * @param countryCode codigo del pais del proceso de core que se corre
	 * @param idProcess id de la tabla de eventos de procesamiento en paralelo de core y asignador
	 * @param countryId id del pais del proceso de core que se corre
	 * @throws BusinessException
	 * @author Aharker
	 */
	public void addWorkOrder(PublishWorkOrderEvent publishWoEvent, WorkOrderDTO workOrderDto, String countryCode, Long idProcess, 
			Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Permite descargar los contact del cliente para persistirlos en HSP+
	 * @param countryId
	 * @param processDate
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public void downloadWorkOrderContact(Long countryId, Date processDate) throws BusinessException;
	
	
	/**
	 * @author ssanabri
	 * Es el encargado de hacer la conversión de convertir el evento que ha llegado por 
	 * parametro y convertirlo en un objeto de evento interno. Esto se hace para que si se cambia el XSD de 
	 * donde se obtiene el objeto original no afecte al resto de la aplicación. <br/>
	 * Luego de la conversión se crea el evento mediante el método expuesto por el servicio que maneja los eventos.
	 * @param publishWoEvent El objeto que se ha cargado con la información que viene de ESB. Este objeto 
	 * fue generado por medio de JAXB tomando los XSDs que entregó el equipo de ESB.
	 * @param iso3Code el codigo del pais que se obtiene del selector de mensajes. 
	 * @throws BusinessException
	 */
	public void addEvent(PublishWorkOrderEvent publishWoEvent, String iso3Code) throws BusinessException;
}

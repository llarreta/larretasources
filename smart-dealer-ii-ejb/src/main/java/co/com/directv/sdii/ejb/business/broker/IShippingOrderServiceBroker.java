package co.com.directv.sdii.ejb.business.broker;

import co.com.directv.sdii.exceptions.BusinessException;


/**
 * 
 * Interfaz que define las operaciones del servicio
 * de Shipping Order publicado en Ecuador. 
 * 
 * Fecha de Creación: 19/07/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public interface IShippingOrderServiceBroker {
	
	/**
	 * 
	 * Metodo: Invoca la operacion shipOrder del servicio de ShippingORder
	 * @param pSubscriber IBS del cliente
	 * @param pWorkOrder Codigo de la workOrder
	 * @param pSerialNumerSC Serial de la SC a activar
	 * @param pSerialNumerIRD Serial del IRD a activar
	 * @param pComment Comentario de activacion
	 * @param pInstaller Nombre del encargado de la cuadrilla asociada a la WO
	 * @return String respuesta del servicio
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public String shipOrder(int pSubscriber, int pWorkOrder, String pSerialNumerSC, String pSerialNumerIRD, String pComment, String pInstaller) throws BusinessException;

}

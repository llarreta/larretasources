package co.com.directv.sdii.ejb.business.broker;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.ContractWorkOrderRequestDTO;
import co.com.directv.sdii.reports.dto.FileResponseDTO;

/**
 * Encapsula la logica para consumir el servicio MarkWorkOrder
 * 
 * Fecha de Creación: 18/09/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Local
public interface MarkWorkOrderServiceBrokerLocal {

	/**
	 * Metodo: Permite determinar si una workOrder requiere contrato.
	 * @param countryCode .
	 * @param woCode .
	 * @param customerCode .
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public boolean requiredContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException;
	
	/**
	 * Metodo: Permite obtener un flujo de datos si una workOrder requiere contrato
	 * @param countryCode 
	 * @param woCode 
	 * @param customerCode 
	 * @return
	 * @throws BusinessException <tipo> <descripcion> 
	 * @author
	 */
	public FileResponseDTO downLoadContractWorkOrder(ContractWorkOrderRequestDTO request) throws BusinessException;
	
}

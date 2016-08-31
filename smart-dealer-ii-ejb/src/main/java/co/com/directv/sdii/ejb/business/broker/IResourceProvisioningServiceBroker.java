package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.SwopsDTORequest;
import co.com.directv.sdii.model.dto.SwopsDTOResponse;
import co.com.directv.sdii.model.dto.WOAttentionElementsRequestDTO;
import co.com.directv.sdii.model.pojo.SystemParameter;

/**
 * 
 * Interfaz que define las operaciones del
 * servicio ResourceProvisioningService del ESB.
 * 
 * Fecha de Creación: 30/06/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public interface IResourceProvisioningServiceBroker {
	
	/**
	 * Metodo: Reporta a IBS el cambio en seriales de IRD.
	 * @param WOAttentionElementsRequestDTO woAttentionDTO
	 * @throws BusinessException En caso de error al reportar los cambios a IBS
	 * @author jalopez
	 */
	public void addIRDChanges(WOAttentionElementsRequestDTO woAttentionDTO, SystemParameter reasonNew, SystemParameter reasonOld)throws BusinessException;
	
	/**
	 * Metodo: Consulta los swops hecho por cliente y por fecha
	 * @param customerIbs Codigo ibs del cliente del cual se desean obtener los swops 
	 * @param fecha fecha desde la cual se va a afiltrar
	 * @throws BusinessException
	 * @author jnova
	 */
	public List<SwopsDTOResponse> getSwops(SwopsDTORequest swopsDTO)throws BusinessException;
	
	
}

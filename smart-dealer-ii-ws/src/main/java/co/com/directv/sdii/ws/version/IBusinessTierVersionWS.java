package co.com.directv.sdii.ws.version;

import javax.jws.WebMethod;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.BusinessTierVersionVO;

/**
 * 
 * Servicio web para consultar las versiones de la capa de negocio. 
 * 
 * Fecha de Creación: 23/02/2011
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@WebService(name="BusinessTierVersionWS",targetNamespace="http://directvla.com.contract/ws/sdii/Version")
public interface IBusinessTierVersionWS {

	@WebMethod(operationName="getBusinessVersion", action="getBusinessVersion")
	public BusinessTierVersionVO getBusinessVersion() throws BusinessException;
}

package co.com.directv.sdii.ws.business.dealers;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;

@WebService(name="DealersInfoWS",targetNamespace="http://dealersforsd.business.ws.sdii.directv.com.co/")
public interface IDealersInfoWS {

	/**
	 * Metodo encargado de consultar los dealers existentes en HSP
	 * @param requestDealers parametros de entrada, como paginacion, numero de pagina, tamaño de pagina o si debe responder en archivo csv
	 * @return
	 * @author Aharker
	 */
	@WebMethod(operationName="getHSPDealers", action="getHSPDealers")
	public DealerInfoResponseDTO getHSPDealers(@WebParam(name="requestDealers")DealerInfoRequestDTO requestDealers) throws BusinessException;
	
}

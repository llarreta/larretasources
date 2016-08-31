package co.com.directv.sdii.ws.business.dealers;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersFacadeBeanLocal;
import co.com.directv.sdii.model.dto.DealerInfoRequestDTO;
import co.com.directv.sdii.model.dto.DealerInfoResponseDTO;

@MTOM
@WebService(serviceName="DealersInfoWS",
		endpointInterface="co.com.directv.sdii.ws.business.dealers.IDealersInfoWS",		
		targetNamespace="http://dealersforsd.business.ws.sdii.directv.com.co/",
		portName="DealersInfoWSPort")
@Stateless()
public class DealersInfoWS implements IDealersInfoWS {

    @EJB
    private DealersFacadeBeanLocal dealerFacadeBean;
	
	/**
	 * Metodo encargado de consultar los dealers existentes en HSP
	 * @param requestDealers parametros de entrada, como paginacion, numero de pagina, tamaño de pagina o si debe responder en archivo csv
	 * @return
	 * @author Aharker
	 */
	@Override
	public DealerInfoResponseDTO getHSPDealers(DealerInfoRequestDTO getHSPDealers) throws BusinessException {
		return dealerFacadeBean.getHSPDealers(getHSPDealers);
	}

}

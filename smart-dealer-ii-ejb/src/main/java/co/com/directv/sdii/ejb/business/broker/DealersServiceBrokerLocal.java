package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.ws.model.dto.DealersWSDTO;

/**
 * 
 * Interface que define las operaciones de los web services
 * que exponen los servicios de Dealers de IBS.
 * 
 * Fecha de Creación: 14/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealersServiceBrokerLocal {
	
	/**
	 * 
	 * Metodo: Consume la operacion de getDealerInfo expuesta por
	 * el servicio de Dealers en IBS
	 * @param dealerCode Long
	 * @param depotCode String
	 * @param country Country
	 * @return DealersWSDTO
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public DealersWSDTO getDealerInfo(Long dealerCode, String depotCode, Country country) throws BusinessException;;
	
	/**
	 * 
	 * Metodo: Consume la iperacion de getDealerCodes expuesta por
	 * el servicio de Dealers en IBS
	 * @param countryCode String
	 * @return List<DealerVO>
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<DealerVO> getDealerCodes(Country country) throws BusinessException;
}

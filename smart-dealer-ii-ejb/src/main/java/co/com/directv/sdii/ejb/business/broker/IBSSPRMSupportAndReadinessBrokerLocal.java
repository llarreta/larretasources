package co.com.directv.sdii.ejb.business.broker;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MovCmdQueueVO;
import co.com.directv.sdii.ws.model.dto.DealersWSDTO;


/**
 * Interface que define las operaciones de los web services
 * que exponen los servicios de Inventarios PRMSupportAndReadiness de IBS.
 * 
 * Fecha de Creación: 2012/4/10
 * @author jjimenezh <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.impl.IBSSPRMSupportAndReadinessBrokerImpl
 */
@Local
public interface IBSSPRMSupportAndReadinessBrokerLocal {
	
	
	/**
	 * Metodo: Reporta a ibs el movimiento de elementos serializados
	 * @param MovCmdQueueVO record objeto que encapsula la información de un objeto a ser enviado a ibs
	 * @throws BusinessException En caso de error al reportar el movimiento de elemento serializado
	 * @author aharker
	 */
	public boolean moveResourceToStockHandler(MovCmdQueueVO record) throws BusinessException;
	
	/**
	 * Metodo: Reporta a ibs los elementos serializados en la atencion de la desconexion
	 * @param record
	 * @param linkSerial
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author cduarte
	 */
	public boolean receiveReturnedPhysicalResource(MovCmdQueueVO record,boolean linkSerial) throws BusinessException;
 
 
	/**
	 * Este metodo esta enfocado a la invocacion del servicio de RESB de traer los codigos y la informacion basica de Dealers de un pais especifico
	 * @param country este parametro es el pais del cual se desean los codigos de Dealer
	 * @return Lista de dealers del pais especificado
	 * @throws Excepcion de negocio que encapsula el error generado por el servicio
	 * @author Aharker
	 */
	public List<DealerVO> getDealerCodes(Country country) throws BusinessException;
 
	/**
	 * Esta operacion esta enfocada al consumo del servicio de RESB para obtener la informacion completa de un Dealer
	 * @param dealerCode El codigo del dealer que se desea buscar
	 * @param depotCode el codigo Depot del dealer que se desea buscar
	 * @param country El pais al que pertenece el dealer que se esta buscando
	 * @return la informacion completa del dealer encapsulada en un objeto DealersWSDTO
	 * @throws excepciones de invocacion del servicio o de negocio encapsuladas en una excepcion de negocio de HSP+
	 * @author Aharker
	 */
	public DealersWSDTO getDealerInfo(Long dealerCode, String depotCode, Country country) throws BusinessException;
}

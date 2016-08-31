package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad SaleChanel.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChanelBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto SaleChanelVO
	 * @param obj objeto que encapsula la información de un SaleChanelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un SaleChanelVO
	 * @param obj objeto que encapsula la información de un SaleChanelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un SaleChanelVO
	 * @param obj información del SaleChanelVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un SaleChanelVO por su identificador
	 * @param id identificador del SaleChanelVO a ser consultado
	 * @return objeto con la información del SaleChanelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public SaleChanelVO getSaleChanelByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los SaleChanelVO almacenados en la persistencia
	 * @return Lista con los SaleChanelVO existentes, una lista vacia en caso que no existan SaleChanelVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<SaleChanelVO> getAllSaleChanels() throws BusinessException;

	/**
	 * Metodo: Borra la información de un canal de venta dado su identificador
	 * @param saleChannelId identificador del canal de venta
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public void deleteSaleChanelById(Long saleChannelId)throws BusinessException;

	/**
	 * Metodo: Obtiene una lista de canales de venta por los filtros especificados
	 * @param request información de los filtros para la consulta de los canales de venta
	 * @param requestCollectionInfo información de la paginación
	 * @return objeto que encapsula la respuesta paginada
	 * @throws BusinessException en caso de error
	 * @author jjimenezh
	 */
	public SaleChannelResponse getSaleChannelsByFilters(
			GetSaleChannelsByFiltersRequestDTO request,
			RequestCollectionInfoDTO requestCollectionInfo)throws BusinessException;

}

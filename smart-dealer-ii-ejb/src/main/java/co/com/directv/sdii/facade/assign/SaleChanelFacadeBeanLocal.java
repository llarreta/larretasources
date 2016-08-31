package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.dto.collection.RequestCollectionInfoDTO;
import co.com.directv.sdii.model.pojo.collection.SaleChannelResponse;
import co.com.directv.sdii.model.vo.SaleChanelVO;
import co.com.directv.sdii.ws.model.dto.GetSaleChannelsByFiltersRequestDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad SaleChanel.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface SaleChanelFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto SaleChanel
	 * @param obj - SaleChanelVO  objeto que encapsula la información de un SaleChanelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un SaleChanel
	 * @param obj - SaleChanelVO  objeto que encapsula la información de un SaleChanelVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un SaleChanel
	 * @param obj - SaleChanelVO  información del SaleChanelVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteSaleChanel(SaleChanelVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un SaleChanel por su identificador
	 * @param id - Long identificador del SaleChanel a ser consultado
	 * @return objeto con la información del SaleChanelVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public SaleChanelVO getSaleChanelByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los SaleChanel almacenados en la persistencia
	 * @return List<SaleChanelVO> Lista con los SaleChanelVO existentes, una lista vacia en caso que no existan SaleChanelVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<SaleChanelVO> getAllSaleChanels() throws BusinessException;

	/**
	 * Metodo: Borra la información de un canal de venta
	 * @param saleChannelId identificador del canal de venta a ser eliminado
	 * @throws BusinessException en caso de error al tratar de borrar el canal de venta
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

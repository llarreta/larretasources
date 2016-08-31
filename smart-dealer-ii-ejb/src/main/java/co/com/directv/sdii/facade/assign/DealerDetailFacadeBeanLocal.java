package co.com.directv.sdii.facade.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.reports.dto.DealerDetailDTO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad DealerDetail.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerDetailFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto DealerDetail
	 * @param obj - DealerDetailVO  objeto que encapsula la información de un DealerDetailVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un DealerDetail
	 * @param obj - DealerDetailVO  objeto que encapsula la información de un DealerDetailVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un DealerDetail
	 * @param obj - DealerDetailVO  información del DealerDetailVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void deleteDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un DealerDetail por su identificador
	 * @param id - Long identificador del DealerDetail a ser consultado
	 * @return objeto con la información del DealerDetailVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public DealerDetailVO getDealerDetailByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los DealerDetail almacenados en la persistencia
	 * @return List<DealerDetailVO> Lista con los DealerDetailVO existentes, una lista vacia en caso que no existan DealerDetailVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public List<DealerDetailVO> getAllDealerDetails() throws BusinessException;
	
	/**
	 * Método: Obtiene los datos de detalle para los dealer de un país
	 * @param countryId - Long país a consultar
	 * @return List<DealerDetailVO> lista de detalle de dealer
	 * @throws BusinessException en caso de error al consultar los detalles de dealer por país
	 * @author gfandino
	 */
	public List<DealerDetailDTO> getAllDealerDetailsByCountry(Long countryId) throws BusinessException;

}

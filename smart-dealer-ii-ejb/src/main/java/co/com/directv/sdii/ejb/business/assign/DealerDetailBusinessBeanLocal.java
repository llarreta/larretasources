package co.com.directv.sdii.ejb.business.assign;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.DealerDetailVO;
import co.com.directv.sdii.reports.dto.DealerDetailDTO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad DealerDetail.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealerDetailBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto DealerDetailVO
	 * @param obj objeto que encapsula la información de un DealerDetailVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void createDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un DealerDetailVO
	 * @param obj objeto que encapsula la información de un DealerDetailVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author
	 */
	public void updateDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un DealerDetailVO
	 * @param obj información del DealerDetailVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteDealerDetail(DealerDetailVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un DealerDetailVO por su identificador
	 * @param id identificador del DealerDetailVO a ser consultado
	 * @return objeto con la información del DealerDetailVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public DealerDetailVO getDealerDetailByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los DealerDetailVO almacenados en la persistencia
	 * @return Lista con los DealerDetailVO existentes, una lista vacia en caso que no existan DealerDetailVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<DealerDetailVO> getAllDealerDetails() throws BusinessException;
	
	/**
	 * 
	 * Metodo: Obtiene la capacidad de un delaer por dia. Si tiene capacidad por tecnico, hace la multiplicacion de la cantidad
	 * de tecnicos del dealer y la cantidad de WO por tecnico. En caso que tenga capcidad por compania, retorna directamente ese valor
	 * @param dealerId Identificador del dealer
	 * @return Long capacidad del dealer
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jnova
	 */
	public Long getDealerDayCapacity(Long dealerId) throws BusinessException;
	
	/**
	 * Método: Obtiene los datos de detalle para los dealer de un país
	 * @param countryId - Long país a consultar
	 * @return List<DealerDetailDTO> lista de detalle de dealer
	 * @throws BusinessException en caso de error al consultar los detalles de dealer por país
	 * @author gfandino
	 */
	public List<DealerDetailDTO> getAllDealerDetailsByCountry(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Permite setear si el dealer atiende par o impar
	 * @param dealerDetailVO
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author
	 */
	public void generateAttendTypeOddEven(DealerDetailVO dealerDetailVO) throws PropertiesException;

}

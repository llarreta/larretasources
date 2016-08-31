package co.com.directv.sdii.ejb.business.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MeasureUnitVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD de la Entidad MeasureUnit.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MeasureUnitBusinessBeanLocal {

	/**
	 * Metodo:  Persiste la información de un objeto MeasureUnitVO
	 * @param obj objeto que encapsula la información de un MeasureUnitVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void createMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	/**
	 * Metodo: actualiza la información de un MeasureUnitVO
	 * @param obj objeto que encapsula la información de un MeasureUnitVO
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void updateMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	/**
	 * Metodo: Borra de la persistencia la información de un MeasureUnitVO
	 * @param obj información del MeasureUnitVO a ser borrado
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public void deleteMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Obtiene la información de un MeasureUnitVO por su identificador
	 * @param id identificador del MeasureUnitVO a ser consultado
	 * @return objeto con la información del MeasureUnitVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public MeasureUnitVO getMeasureUnitByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Obtiene la información de todos los MeasureUnitVO almacenados en la persistencia
	 * @return Lista con los MeasureUnitVO existentes, una lista vacia en caso que no existan MeasureUnitVO en el sistema
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException;
	
	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param code código del objeto a ser consultado
	 * @return Unidad de medida con el código especificado, nulo en caso que no exista la
	 * unidad con ese código
	 * @throws BusinessException en caso de error al tratar de ejecutar la operación
	 * @author jjimenezh
	 */
	public MeasureUnitVO getMeasureUnitByCode(String code)throws BusinessException;
	
	/**
	 * Metodo: Obtiene MeasureUnitVO activos
	 * @return List<MeasureUnitVO> en estado activo; vacio en otro caso
	 * unidad con ese código
	 * @throws BusinessException en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 */
	public List<MeasureUnitVO> getMeasureUnitsByStatus()throws BusinessException;
	
	/**
	 * Metodo: Obtiene MeasureUnitVO activos
	 * @param requestCollInfo con la información de la paginación
	 * @return MeasureUnitResponse con el resultado de la consulta
	 * @throws BusinessException  en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 */
	public MeasureUnitResponse getMeasureUnitsByStatus(RequestCollectionInfo requestCollInfo)throws BusinessException;

	/**
	 * Metodo: Obtiene MeasureUnitVO todos lso estados
	 * @param code  - String código de la unidad de medida
	 * @param requestCollInfo con la información de la paginación
	 * @return MeasureUnitResponse con el resultado de la consulta
	 * @throws BusinessException  en caso de error al tratar de ejecutar la consulta por estado
	 * @author gfandino
	 * 
	 */
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(
			String code, RequestCollectionInfo requestCollInfo)throws BusinessException;


}

package co.com.directv.sdii.facade.stock;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MeasureUnitVO;

/**
 * 
 * Interfaz de la fachada con las operaciones Tipo CRUD de la Entidad MeasureUnit.
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MeasureUnitFacadeBeanLocal {

	/**
	 * Metodo:  Permite crear en el sistema un objeto MeasureUnit
	 * @param obj - MeasureUnitVO  objeto que encapsula la información de un MeasureUnitVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void createMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite actualizar en el sistema la información de un MeasureUnit
	 * @param obj - MeasureUnitVO  objeto que encapsula la información de un MeasureUnitVO
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void updateMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	/**
	 * Metodo: Permite borra la información de un MeasureUnit
	 * @param obj - MeasureUnitVO  información del MeasureUnitVO a ser borrado
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public void deleteMeasureUnit(MeasureUnitVO obj) throws BusinessException;
	
	
	/**
	 * Metodo: Permite consultar la información de un MeasureUnit por su identificador
	 * @param id - Long identificador del MeasureUnit a ser consultado
	 * @return objeto con la información del MeasureUnitVO dado su identificador, nulo en caso que no se encuentre
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public MeasureUnitVO getMeasureUnitByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar la información de todos los MeasureUnit almacenados en la persistencia
	 * @return List<MeasureUnitVO> Lista con los MeasureUnitVO existentes, una lista vacia en caso que no existan MeasureUnitVO en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación
	 * @author jjimenezh
	 */
	public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException;

	/**
	 * Metodo: Obtiene un objeto por el código especificado
	 * @param code código del objeto a ser consultado
	 * @return Unidad de medida con el código especificado, nulo en caso que no exista la
	 * unidad con ese código
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
	 * Metodo: Obtiene MeasureUnitVO en todos los estados
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

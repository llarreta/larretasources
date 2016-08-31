package co.com.directv.sdii.ejb.business.config;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.AllianceCompanyVO;
import co.com.directv.sdii.model.vo.AllianceFileVO;
import co.com.directv.sdii.model.vo.AllianceVO;
import co.com.directv.sdii.model.vo.ComercialProductVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Alianzas.
 *
 * En la consulta de ALLIANCES se recupera ALLIANCE_COMPANY, COMERCIAL_PRODUCT y la
 * colección de ALLIANCE_FILES.
 *
 * Caso de Uso CFG - 18 - Gestionar Alianzas
 *
 * @author Jimmy Vélez Muñoz
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 */
@Local
public interface ConfigAlianzasBusinessLocal {

	/**
     * Consulta Alliance por el id
     * @param id - Long
     * @return  AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceVO getAllianceByID(Long id) throws BusinessException;

    /**
     * Consulta Alliances por el codigo
     * @param code - String
     * @return AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceVO getAllianceByCode(String code) throws BusinessException;

    /**
     * Consulta Alliances por el nombre
     * @param name - String
     * @return List<AllianceVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceVO> getAllianceByName(String name) throws BusinessException;

    /**
     * Consulta todas las Alliance del sistema
     * @return List<AllianceVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceVO> getAll() throws BusinessException;

     /**
     * Lista de allianzas regionalizadas
     * @param countryId identificador del país por el que se realizará el filtro
     * @return Lista de allianzas dado el país
     * @throws BusinessException en caso de error al tratar de consultar las alianzas
     * @author jjimenezh Agregado por control de cambios 2010-04-26
     */
    public List<AllianceVO> getAllAllianceByCountryId(Long countryId) throws BusinessException;
    
    /**
     * Consulta Alliance en un rango de fechas
     * @param init - Date
     * @param end - Date
     * @return List<AllianceVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceVO> getAllianceByDate(Date init, Date end) throws BusinessException;

    /**
     * Crea una alianza en el sistema
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createAlliance(AllianceVO obj) throws BusinessException;
    
    /**
     * Este método crea un ALLIANCE FILE.
     *
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createAllianceFile(AllianceFileVO obj)throws BusinessException;

    /**
     * Este método consultar un ALLIANCE FILE por ALLIANCE.
     *
     * @param obj - AllianceVO
     * @return AllianceFileVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceFileVO getAllianceFileByAllianceID(AllianceVO obj)throws BusinessException;
    
    /**
     * Actualiza un alliance
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void updateAlliance(AllianceVO obj) throws BusinessException;

    /**
     * Elimina un alliance del sistema
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void deleteAlliance(AllianceVO obj) throws BusinessException;

    /**
     * Completa la lista de las empresas de alianza
     * @return List<AllianceCompanyVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceCompanyVO> populateEmpresas() throws BusinessException;
    
    /**
     * Obtiene una lista de allianzas con compañias regionalizada por país
     * @param countryId identificador del país por el que se filtrará
     * @return lista de compañías en alianza regionaizadas
     * @throws BusinessException en caso de error al tratar de obtener la lista
     * @author jjimenezh Agregado por control de cambios 2010-04-26
     */
    public List<AllianceCompanyVO> getAllAllianceCompanyByCountryId(Long countryId) throws BusinessException; 


    /**
     * Completa la lista de Productos comerciales
     * @retun List<ComercialProductVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<ComercialProductVO> populateProductosComerciales() throws BusinessException;

     /**
     * Obtiene una lista de los productos comerciales dado el identificador del país
     * @param countryId identificador del país por el que se realizará el filtro
     * @return lista de productos comerciales dado el identificador de país
     * @throws BusinessException en caso de error al tratar de consultar por el identificador del país
     */
    public List<ComercialProductVO> getAllComercialProductsByCountryId(Long countryId)throws BusinessException;
    
    /**
     * Obtiene la compañia de la alianza por ID
     * @param id - Long
     * @retun AllianceCompanyVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceCompanyVO getAllianceCompanyByID(Long id) throws BusinessException;

    /**
     * Obtiene La compañia de la alianza por el Codigo
     * @param code - String
     * @return AllianceCompanyVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceCompanyVO getAllianceCompanyByCode(String code) throws BusinessException;

    /**
     * Obtiene el producto comercial por ID
     * @param id - Long
     * @return ComercialProductVO
     * @throws BusinessException
     * @author gfandino
     */
    public ComercialProductVO getComercialProductByID(Long id) throws BusinessException;

    /**
     * Obtiene el Producto Comercial por el Codigo
     * @param code - String
     * @return ComercialProductVO
     * @throws BusinessException
     * @author gfandino
     */
    public ComercialProductVO getComercialProductByCode(String code) throws BusinessException;

	

	
}

package co.com.directv.sdii.facade.config;

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
 * Fecha de Creación: Mar 25, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigAlianzasFacadeLocal {

    /**
     * Este método retorna una instancia de ALLIANCES por ID.
     *
     * @param id - Long
     * @return AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceVO getAllianceByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de ALLIANCES por ALLIANCE_CODE.
     *
     * @param code - String
     * @return AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceVO getAllianceByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de ALLIANCES por ALLIANCE_NAME.
     *
     * @param name - String
     * @return List<AllianceVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceVO> getAllianceByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de Todas las ALLIANCES.
     *
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
     * Este método retorna una lista de las ALLIANCES que se encuentran
     * en el rango de fecha.
     *
     * @param init - Date
     * @param end - Date
     * @return List<AllianceVO>
     * @throws BusinessException
     * @author gfandino
     */
    public List<AllianceVO> getAllianceByDate(Date init, Date end) throws BusinessException;

    /**
     * Este método crea un ALLIANCE.
     *
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createAlliance(AllianceVO obj) throws BusinessException;
    
    /**
     * Este método crea un ALLIANCEFILE.
     *
     * @param obj - AllianceFileVO
     * @throws BusinessException
     * @author gfandino
     */
    public void createAllianceFile(AllianceFileVO obj) throws BusinessException;

     /**
     * Este método consultar un ALLIANCE FILE por ALLIANCE.
     *
     * @param obj - AllianceVO
     * @return AllianceFileVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceFileVO getAllianceFileByAllianceID(AllianceVO obj) throws BusinessException;
    
   
    /**
     * Este método actualiza una ALLIANCE.
     *
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void updateAlliance(AllianceVO obj) throws BusinessException;

    /**
     * Este método elimina un ALLIANCE.
     *
     * @param obj - AllianceVO
     * @throws BusinessException
     * @author gfandino
     */
    public void deleteAlliance(AllianceVO obj) throws BusinessException;

    /**
     * Este método retorna una lista de todas las empresas de alianzas
     *
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
     * Este método retorna una lista de todos los productos comerciales
     *
     * @return List<ComercialProductVO>
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
     * Este método retorna una instancia de AllianceCompany por ID.
     *
     * @param id - Long
     * @return AllianceCompanyVO
     * @throws BusinessException
     * @author gfandino
     */
    public AllianceCompanyVO getAllianceCompanyByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de AllianceCompany por Codigo.
     *
     * @param code - String
     * @return AllianceCompanyVO
     * @throws BusinessException
     */
    public AllianceCompanyVO getAllianceCompanyByCode(String code) throws BusinessException;

     /**
     * Este método retorna una instancia de ComercialProduct por ID.
     *
     * @param id - Long
     * @return ComercialProductVO
     * @throws BusinessException
     * @author gfandino
     */
    public ComercialProductVO getComercialProductByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de ComercialProduct por Codigo.
     *
     * @param code - String
     * @return ComercialProductVO
     * @throws BusinessException
     * @author gfandino
     */
    public ComercialProductVO getComercialProductByCode(String code) throws BusinessException;

}

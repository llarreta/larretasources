package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.CustomerTypeVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Códigos de Tipos de Clientes.
 *
 * Caso de Uso CFG - 06 - Gestionar Códigos de Tipos de Clientes
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigTiposClienteBusinessLocal {

	/** 
     * Metodo: Obtiene el tipo de cliente por el identificador
     * @param id identificador del tipo de cliente
     * @return objeto con la información del tipo de cliente
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public CustomerClassTypeVO getCustomerClassTypeByID(Long id) throws BusinessException;

    /**
     * Metodo de negocio que busca una clase de cliente por id
     * @param id id de la clase que se busca
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    public CustomerClassVO getCustomerClassByID(Long id) throws BusinessException;
    
    /**
     * Metodo que busca una clase de cliente por codigo para un pais dado
     * @param classCode codigo de la clase de cliente que se desea buscar
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    public CustomerClassVO getCustomerClassByCode(String classCode, Long countryId) throws BusinessException;
    
    /**
     * Este método retorna una lista de CUSTOMERS_CODE_TYPES por CUSTOMER_TYPE_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameType(String name, Long countryId) throws BusinessException;
    
    /**
     * Metodo de logica de negocios que busca las combinaciones de tipo de cliente y clase de cliente dado el nombre de la clase
     * @param name nombre de la clase de cliente con la cual se desea buscar
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameClass(String name, Long countryId) throws BusinessException;

    /**
     * Este método retorna una lista de CUSTOMERS_CODE_TYPES por CUSTOMER_TYPE_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeType(String code, Long countryId) throws BusinessException;
    
    /**
     * Metodo que busca la combinacion del tipo de cliente con clase de cliente que corresponda dados un codigo de clase de cliente y un codigo de tipo de cliente
     * @param codeType codigo del tipo de cliente para la busqueda
     * @param codeClass codigo de la clase del cliente para la busqueda
     * @param countryId pais donde se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    public CustomerClassTypeVO getCustomerClassTypeByCodeTypeAndCodeClass(String codeType, String codeClass, Long countryId) throws BusinessException;
    
    /**
     * 
     * @param code
     * @param countryId
     * @return
     * @throws BusinessException
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeClass(String code, Long countryId) throws BusinessException;

    /**
     * Este método retorna una lista de CUSTOMERS_CODE_TYPES por CUSTOMER_CLASS.
     *
     * @param customerClass
     * @return
     * @throws BusinessException
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCustomerClass(CustomerClassVO customerClass) throws BusinessException;

    /**
     * Este método retorna una lista de Todas las CUSTOMERS_CODE_TYPES.
     *
     * @return
     * @throws BusinessException
     */
    public List<CustomerClassTypeVO> getAll(Long countryId) throws BusinessException;

    /**
     * Este método crea un CUSTOMERS_CODE_TYPES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * Este método actualiza una CUSTOMERS_CODE_TYPES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updateCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * Este método elimina un CUSTOMERS_CODE_TYPES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deleteCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * 
     * @return
     * @throws BusinessException
     */
	public List<CustomerClassVO> getAllCustomerClass(Long countryId)throws BusinessException;
		
	public List<CustomerClassTypeVO> getCustomerClassTypeByNameTypeAndClass(String nameType, String nameClass, Long countryId) throws BusinessException;

	public CustomerTypeVO getCustomerTypeByID(Long id)throws BusinessException;

	public void createCustomerType(CustomerTypeVO obj)throws BusinessException;

	public List<CustomerCategoryVO> getAllCustomerCategory()throws BusinessException;
	
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene categorias de clientes
     * @return lista con las categorias de cliente definidas en la propiedad CUSTOMER_CATEGORY_DEALER_CONF
     * , una lista vacia en caso que no exista ninguna categoría de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
     * @author ialessan
     */	
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(Long countryId) throws BusinessException;
	

	public List<CustomerClassVO> getAllCustomerClassByCategoryId(Long countryId, Long categoryId)throws BusinessException;

	public List<CustomerTypeVO> getAllCustomerTypes(Long countryId)throws BusinessException;

	public void updateCustomerType(CustomerTypeVO obj)throws BusinessException;

	public CustomerTypeVO getCustomerTypeByCode(String code, Long countryId)throws BusinessException;
	
	public CustomerCategoryVO getCustomerCategoryByCustomerClassCode(String customerClassCode) throws BusinessException;
}

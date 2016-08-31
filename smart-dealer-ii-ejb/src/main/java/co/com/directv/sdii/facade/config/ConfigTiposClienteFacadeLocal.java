package co.com.directv.sdii.facade.config;

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
public interface ConfigTiposClienteFacadeLocal {

	/** 
     * Metodo: Obtiene el tipo de cliente por el identificador
     * @param id identificador del tipo de cliente
     * @return objeto con la información del tipo de cliente
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public CustomerClassTypeVO getCustomerClassTypeByID(Long id) throws BusinessException;
    
    public CustomerTypeVO getCustomerTypeByID(Long id) throws BusinessException;
    
    public CustomerClassVO getCustomerClassByID(Long id) throws BusinessException;

    /**
     * Metodo: Obtiene el código de tipo cliente por el nombre
     * @param name nombre del código de tipo cliente
     * @return lista con objetos cuyo nombre coincide con el especificado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameType(String name, Long countryId) throws BusinessException;
    
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameClass(String name, Long countryId) throws BusinessException;

    public List<CustomerClassTypeVO> getCustomerClassTypeByNameTypeAndClass(String nameType, String nameClass, Long countryId) throws BusinessException;
    
    /**
     * Metodo: obtiene un código de tipo cliente por el código
     * @param code código de tipo cliente
     * @return lista con los objetos cuyo código coincide con el especificado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public CustomerClassTypeVO getCustomerClassTypeByCode(String codeType, String codeClass, Long countryId) throws BusinessException;

    /**
     * Metodo: Obtiene un código de tipo de cliente por el 
     * @param customerClass
     * @return lista con la información de códigos de tipo cliente
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCustomerClass(CustomerClassVO customerClass) throws BusinessException;

    /**
     * Metodo: obtiene todos los códigos de tipo de cliente
     * @return Lista con los códigos de tipos de cliente que existen en el sistema
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<CustomerClassTypeVO> getAll(Long countryId) throws BusinessException;

    /**
     * Metodo: Crea un código de tipo cliente
     * @param obj código de tipo cliente a persistir
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void createCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * Metodo: actualiza un código de tipo cliente
     * @param obj código de tipo cliente a ser actualizado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void updateCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * Metodo: borra la información de un código de tipo cliente
     * @param obj código de tipo cliente a ser borrado
     * @throws BusinessException en caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void deleteCustomerClassType(CustomerClassTypeVO obj) throws BusinessException;

    /**
     * Metodo: Obtiene todas las clases de cliente
     * @return lista con todas las clases de cliente
     * @throws BusinessException en caso de error al tratar de obtener todas
     * las clases de cliente
     * @author jjimenezh
     */
	public List<CustomerClassVO> getAllCustomerClass(Long countryId)throws BusinessException;
	
	public List<CustomerClassTypeVO> getCustomerClassTypeByCodeClass(String code, Long countryId) throws BusinessException;
	
	public List<CustomerClassTypeVO> getCustomerClassTypeByCodeType(String code, Long countryId) throws BusinessException ;
	
	public CustomerClassTypeVO getCustomerCodeTypeByCodes(String codeType, String codeClass, Long countryId) throws BusinessException;
	
	public List<CustomerClassTypeVO> getCustomerTypeByNameClass(String name, Long countryId) throws BusinessException;

	public List<CustomerTypeVO> getAllCustomerTypes(Long countryId) throws BusinessException;

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
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(Long countryId) throws BusinessException;//, PropertiesException;

	public List<CustomerClassVO> getAllCustomerClassByCategoryId(Long countryId, Long categoryId)throws BusinessException;

	public void updateCustomerType(CustomerTypeVO obj)throws BusinessException;

	public CustomerTypeVO getCustomerTypeByCode(String code, Long countryId)throws BusinessException;
	
	public CustomerCategoryVO getCustomerCategoryByCustomerClassCode(String customerClassCode) throws BusinessException;
}

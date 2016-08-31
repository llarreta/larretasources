package co.com.directv.sdii.ejb.business.config.impl;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.CustomerCategory;
import co.com.directv.sdii.model.pojo.CustomerClass;
import co.com.directv.sdii.model.pojo.CustomerClassType;
import co.com.directv.sdii.model.pojo.CustomerType;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.CustomerTypeVO;
import co.com.directv.sdii.persistence.dao.config.CustomerCategoryDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerClassTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.CustomerTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración SO Tipos Cliente.
 *
 * Caso de Uso CFG - 06 - Gestionar Códigos de Tipos de Clientes
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.config.CustomerCodeTypeDAOLocal
 * @see co.com.directv.sdii.persistence.dao.config.CustomerClassDAOLocal
 */
@Stateless(name="ConfigTiposClienteBusinessLocal",mappedName="ejb/ConfigTiposClienteBusinessLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigTiposClienteBusinessBean extends BusinessBase implements ConfigTiposClienteBusinessLocal {   
    
    @EJB
    private CustomerClassTypeDAOLocal customerClassTypeDAOLocal;
    
    @EJB
    private CustomerCategoryDAOLocal customerCategoryDAOLocal;

    @EJB
    private CustomerTypeDAOLocal customerTypeDAOLocal;
    
    @EJB
    private CustomerClassDAOLocal customerClassDAOLocal;
    
    @EJB(name="CustomerClassDAOLocal",beanInterface=CustomerClassDAOLocal.class)
    private CustomerClassDAOLocal customerClassDAO;
    
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal systemParameterDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(ConfigWOReasonsBusinessBean.class);
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getCustomerCodeTypeByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CustomerClassTypeVO getCustomerClassTypeByID(Long id) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        try {
        	CustomerClassType pojo = customerClassTypeDAOLocal.getCustomerClassTypeByID(id);

            if(pojo == null){
                return null;
            }

            CustomerClassTypeVO vo = UtilsBusiness.copyObject(CustomerClassTypeVO.class, pojo);
            return vo;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        }
    }

    /**
     * Metodo de negocio que busca una clase de cliente por id
     * @param id id de la clase que se busca
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CustomerClassVO getCustomerClassByID(Long id) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        try {
        	
        	CustomerClass pojo = customerClassDAO.getCustomerClassByID(id);

            if(pojo == null){
                return null;
            }

            CustomerClassVO vo = UtilsBusiness.copyObject(CustomerClassVO.class, pojo);
            return vo;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        }
    }
    
    /**
     * Metodo que busca una clase de cliente por codigo para un pais dado
     * @param classCode codigo de la clase de cliente que se desea buscar
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CustomerClassVO getCustomerClassByCode(String code, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        try {
        	
        	CustomerClass pojo = customerClassDAO.getCustomerClassByCode(code, countryId);

            if(pojo == null){
                return null;
            }

            CustomerClassVO vo = UtilsBusiness.copyObject(CustomerClassVO.class, pojo);
            return vo;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByID/ConfigTiposClienteBusinessBean ==");
        }
    }
    
    //public CustomerClassVO getCustomerClassByCode(String classCode) throws BusinessException;
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getCustomerCodeTypeByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameType(String name, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        try {
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByNameType(name, countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        }
    }

    /**
     * Metodo de logica de negocios que busca las combinaciones de tipo de cliente y clase de cliente dado el nombre de la clase
     * @param name nombre de la clase de cliente con la cual se desea buscar
     * @param countryId pais en el cual se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameClass(String name, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        try {
        	customerClassTypeDAOLocal.getCustomerClassTypeByNameClass(name, countryId);
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByNameClass(name, countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameTypeAndClass(String nameType, String nameClass, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        try {
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByNameTypeAndNameClass(nameType, nameClass, countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByName/ConfigTiposClienteBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getCustomerCodeTypeByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeType(String code, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        try {
        	customerClassTypeDAOLocal.getCustomerClassTypeByCodeType(code, countryId);
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByCodeType(code, countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        }
    }
    
    /**
     * Metodo que busca la combinacion del tipo de cliente con clase de cliente que corresponda dados un codigo de clase de cliente y un codigo de tipo de cliente
     * @param codeType codigo del tipo de cliente para la busqueda
     * @param codeClass codigo de la clase del cliente para la busqueda
     * @param countryId pais donde se realizara la busqueda
     * @return
     * @throws BusinessException
     * @author Aharker
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CustomerClassTypeVO getCustomerClassTypeByCodeTypeAndCodeClass(String codeType, String codeClass, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        try {
        	
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " el pais es requerido ");
        	
            CustomerClassTypeVO customerClassTypeVO = UtilsBusiness.copyObject(CustomerClassTypeVO.class, customerClassTypeDAOLocal.getCustomerClassTypeByCodeTypeAndClassCode(codeType, codeClass, countryId));
            return customerClassTypeVO;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        }
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getCustomerClassTypeByCodeClass(java.lang.String, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeClass(String code, Long countryId) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        try {
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByCodeClass(code, countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getCustomerCodeTypeByCustomerClass(co.com.directv.sdii.model.vo.CustomerClassVO)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getCustomerClassTypeByCustomerClass(CustomerClassVO customerClass) throws BusinessException {
        log.debug("== Inicia getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        try {
            CustomerClass customerClassPojo = UtilsBusiness.copyObject(CustomerClass.class, customerClass);

            if(!(customerClassPojo != null && customerClassPojo.getId() != null &&  customerClassPojo.getId() != 0L)){
                log.debug("== Error en la Capa de Negocio debido a una DAOServiceException ==");
            throw new BusinessException("No se asignó la clase del cliente o no se ha especificado el identificador");
            }
            
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getCustomerClassTypeByCustomerClassId(customerClassPojo.getId()),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getCustomerCodeTypeByCustomerClass");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getCustomerCodeTypeByCode/ConfigTiposClienteBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<CustomerClassTypeVO> getAll(Long countryId) throws BusinessException {
        log.debug("== Inicia getAll/ConfigTiposClienteBusinessBean ==");
        try {
        	
        	UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " el pais es requerido ");
        	
            List<CustomerClassTypeVO> vos = UtilsBusiness.convertList(customerClassTypeDAOLocal.getAll(countryId),CustomerClassTypeVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigTiposClienteBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#createCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createCustomerClassType(CustomerClassTypeVO obj) throws BusinessException {
        log.debug("== Inicia createCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        try {

        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El objeto a persistir viene nulo ");
        	UtilsBusiness.assertNotNull(obj.getCustomerClass(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se tienen los datos necesarios de la clase de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerClass().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se tienen los datos necesarios de la clase de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerType(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se tienen los datos necesarios del tipo de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerType().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " no se tienen los datos necesarios del tipo de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerType().getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se tiene el pais del tipo de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerType().getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se tiene el pais del tipo de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerClass().getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se tiene el pais de la clase de cliente ");
        	UtilsBusiness.assertNotNull(obj.getCustomerClass().getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " No se tiene el pais de la clase de cliente ");
        	
            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode() , "No se puede crear el CustomerCodeType, porque no se ha asignado uno o mas parámetros obligatorios");
            }
            
            if(!obj.getCustomerClass().getCountry().getId().equals(obj.getCustomerType().getCountry().getId())){
            	throw new BusinessException("los paises de clase y tipo no coinciden");
            }
            
            CustomerClassType oldCustomerCodeType = customerClassTypeDAOLocal.getCustomerClassTypeByCodeTypeAndClassCode(obj.getCustomerType().getCustomerTypeCode(), obj.getCustomerClass().getCustomerClassCode(), obj.getCustomerType().getCountry().getId());
            
            if( oldCustomerCodeType != null ){
            	throw new BusinessException(ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getCode() , ErrorBusinessMessages.ALREADY_EXISTS_DEALER.getMessage());
            }
            
            CustomerClassType customerClassType = UtilsBusiness.copyObject(CustomerClassType.class, obj);
            customerClassTypeDAOLocal.createCustomerClassType(customerClassType);
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/createCustomerCodeType");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#updateCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateCustomerClassType(CustomerClassTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        try {

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + "No se puede actualizar el CustomerCodeType, porque no se ha asignado uno o mas parámetros obligatorios");
            }

            CustomerClassType customerClassType = UtilsBusiness.copyObject(CustomerClassType.class, obj);
            customerClassTypeDAOLocal.updateCustomerClassType(customerClassType);
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/updateCustomerCodeType");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#deleteCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCustomerClassType(CustomerClassTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        try {
            CustomerClassType customerClassType = UtilsBusiness.copyObject(CustomerClassType.class, obj);
            customerClassTypeDAOLocal.deleteCustomerClassType(customerClassType);
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/deleteCustomerCodeType");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteCustomerCodeType/ConfigTiposClienteBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal#getAllCustomerClass()
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<CustomerClassVO> getAllCustomerClass(Long customerId) throws BusinessException {
		log.debug("== Inicia getAllCustomerClass/ConfigTiposClienteBusinessBean ==");
        try {
            if(customerId==null){
                log.error("== customerId null");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El pais es requerido ");
            }
        	List<CustomerClass> customerClassPojo = customerClassDAO.getAll(customerId);
            List<CustomerClassVO> vos = UtilsBusiness.convertList(customerClassPojo,CustomerClassVO.class);
            return vos;
        } catch (Throwable ex) {
            log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getAllCustomerClass");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllCustomerClass/ConfigTiposClienteBusinessBean ==");
        }
	}
    
	@Override
	public CustomerTypeVO getCustomerTypeByID(Long id) throws BusinessException {
		try{
            if(id==null){
                log.error("== id null");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
			return UtilsBusiness.copyObject(CustomerTypeVO.class, customerClassTypeDAOLocal.getCustomerTypeByID(id));
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion ConfigTiposClienteBusinessBean/getAllCustomerClass");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllCustomerClass/ConfigTiposClienteBusinessBean ==");
		}
	}

	@Override
	public void createCustomerType(CustomerTypeVO obj) throws BusinessException {
		log.debug("== Inicia createCustomerType/ConfigTiposClienteBusinessBean ==");
		try{
			
			UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El objeto a persistir viene nulo ");
			UtilsBusiness.assertNotNull(obj.getCustomerTypeCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El codigo del tipo de cliente es requerido ");
			UtilsBusiness.assertNotNull(obj.getCustomerTypeName(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El nombre del tipo de cliente es requerido ");
			UtilsBusiness.assertNotNull(obj.getCountry(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El pais del tipo de cliente es requerido ");
			UtilsBusiness.assertNotNull(obj.getCountry().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El pais del tipo de cliente es requerido ");
			
			customerTypeDAOLocal.createCustomerType(new CustomerType(obj));
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion createCustomerType ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina createCustomerType/ConfigTiposClienteBusinessBean ==");
		}
	}

	@Override
	public List<CustomerCategoryVO> getAllCustomerCategory()throws BusinessException {
		log.debug("== Inicia getAllCustomerCategory/ConfigTiposClienteBusinessBean ==");
		try{
			return customerCategoryDAOLocal.getAllCustomerCategory();
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllCustomerCategory ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllCustomerCategory/ConfigTiposClienteBusinessBean ==");
		}
	}
	
	
    /**
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene categorias de clientes
     * @return lista con las categorias de cliente definidas en la propiedad CUSTOMER_CATEGORY_DEALER_CONF
     * , una lista vacia en caso que no exista ninguna categoría de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
     * @author ialessan
     * @throws PropertiesException 
     */	
	@Override
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(Long countryId) throws BusinessException{ 
		
	log.debug("== Inicia getCustomerCategoryForDealerConf/ConfigTiposClienteBusinessBean ==");
	//no interesa el pais en este caso, sera para todos los paises				
					
	SystemParameter parameter = null;
	try{	
	       	 parameter = systemParameterDAO.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.CUSTOMER_CATEGORY_DEALER_CONF.getCodeEntity(),countryId);
	        //si customerCassDealerConf is null retornar mensaje "No hay categorías configuradas..."
	            	
	    }catch (PropertiesException propertiesException){
	        log.error("== Error al tratar de obtener propiedad CUSTOMER_CATEGORY_DEALER_CONF ConfigTiposClienteBusinessBean/getCustomerCategoryForDealerConf", propertiesException);
	        throw new BusinessException(propertiesException.getMessageCode(),propertiesException.getMessage(),propertiesException);
	        
        } catch(Throwable ex){
	       log.error("== Error al tratar de ejecutar la operación ConfigTiposClienteBusinessBean/ConfigTiposClienteBusinessBean ==");
	    	throw this.manageException(ex);	
	    }
	            
	            
	    String[] customerCategoryDealerConfArray=parameter.getValue().split(",");
		List<String> customerCategoryDealerConfList=Arrays.asList(customerCategoryDealerConfArray);
		try{
				  return customerCategoryDAOLocal.getCustomerCategoryForDealerConf(customerCategoryDealerConfList);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getCustomerCategoryForDealerConf ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerCategoryForDealerConf/ConfigTiposClienteBusinessBean ==");
		} 
	}
					

	@Override
	public List<CustomerClassVO> getAllCustomerClassByCategoryId(Long countryId, Long categoryId) throws BusinessException {
		log.debug("== Inicia getAllCustomerClassByCategoryId/ConfigTiposClienteBusinessBean ==");
		try{
			
			UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El pais es requerido ");
			UtilsBusiness.assertNotNull(categoryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " la categoria es requerida ");
			
			return customerClassDAOLocal.getCustomerClassByCategoryId(categoryId, countryId);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllCustomerClassByCategoryId ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllCustomerClassByCategoryId/ConfigTiposClienteBusinessBean ==");
		}
	}

	@Override
	public List<CustomerTypeVO> getAllCustomerTypes(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getAllCustomerTypes/ConfigTiposClienteBusinessBean ==");
		try{
			
			UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage() + " El pais es requerido ");
			
			return customerTypeDAOLocal.getAllCustomerType(countryId);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllCustomerTypes ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getAllCustomerTypes/ConfigTiposClienteBusinessBean ==");
		}
	}

	@Override
	public void updateCustomerType(CustomerTypeVO obj) throws BusinessException {
		try{
			log.debug("== inicia updateCustomerType/ConfigTiposClienteBusinessBean ==");
			CustomerType ct = UtilsBusiness.copyObject(CustomerType.class, obj);
			this.customerTypeDAOLocal.updateCustomerType(ct);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion updateCustomerType ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina updateCustomerType/ConfigTiposClienteBusinessBean ==");
		}
	}
    
	@Override
	public CustomerTypeVO getCustomerTypeByCode(String code, Long countryId)throws BusinessException{
		try{
			log.debug("== Inicia getCustomerTypeByCode/ConfigTiposClienteBusinessBean ==");
			return this.customerTypeDAOLocal.getCustomerTypeByCode(code, countryId);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getCustomerTypeByCode ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerTypeByCode/ConfigTiposClienteBusinessBean ==");
		}
	}
	
	@Override
	public CustomerCategoryVO getCustomerCategoryByCustomerClassCode(String customerClassCode) throws BusinessException {
		log.debug("== Inicia getCustomerCategoryByCustomerClassCode/ConfigTiposClienteBusinessBean ==");
		try{
			UtilsBusiness.assertNotNull(customerClassCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage()); 
			CustomerCategory customerCategory = customerCategoryDAOLocal.getCustomerCategoryByCustomerClassCode(customerClassCode);
			return UtilsBusiness.copyObject(CustomerCategoryVO.class, customerCategory);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getCustomerCategoryByCustomerClassCode ");
			throw super.manageException(ex);
		} finally {
			log.debug("== Termina getCustomerCategoryByCustomerClassCode/ConfigTiposClienteBusinessBean ==");
		}
	}
	
}

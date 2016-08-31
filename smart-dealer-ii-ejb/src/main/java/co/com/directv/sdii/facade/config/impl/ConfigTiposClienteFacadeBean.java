package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigTiposClienteBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal;
import co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeRemote;
import co.com.directv.sdii.model.vo.CustomerCategoryVO;
import co.com.directv.sdii.model.vo.CustomerClassTypeVO;
import co.com.directv.sdii.model.vo.CustomerClassVO;
import co.com.directv.sdii.model.vo.CustomerTypeVO;

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
 * @see
 */
@Stateless(name="ConfigTiposClienteFacadeLocal",mappedName="ejb/ConfigTiposClienteFacadeLocal")
@Local({ConfigTiposClienteFacadeLocal.class})
@Remote({ConfigTiposClienteFacadeRemote.class})
public class ConfigTiposClienteFacadeBean implements ConfigTiposClienteFacadeLocal, ConfigTiposClienteFacadeRemote {

    @EJB(name="ConfigTiposClienteBusinessLocal",beanInterface=ConfigTiposClienteBusinessLocal.class)
    private ConfigTiposClienteBusinessLocal business;

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getCustomerCodeTypeByID(java.lang.Long)
     */
    public CustomerClassTypeVO getCustomerClassTypeByID(Long id) throws BusinessException {
        return business.getCustomerClassTypeByID(id);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getCustomerCodeTypeByName(java.lang.String)
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByNameType(String name, Long countryId) throws BusinessException {
        return business.getCustomerClassTypeByNameType(name, countryId);
    }

    public List<CustomerClassTypeVO> getCustomerTypeByNameClass(String name, Long countryId) throws BusinessException {
        return business.getCustomerClassTypeByNameClass(name, countryId);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getCustomerCodeTypeByCode(java.lang.String)
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeType(String code, Long countryId) throws BusinessException {
        return business.getCustomerClassTypeByCodeType(code, countryId);
    }

    public List<CustomerClassTypeVO> getCustomerClassTypeByCodeClass(String code, Long countryId) throws BusinessException {
        return business.getCustomerClassTypeByCodeClass(code, countryId);
    }
    
    public CustomerClassTypeVO getCustomerCodeTypeByCodes(String codeType, String codeClass, Long countryId) throws BusinessException {
        return business.getCustomerClassTypeByCodeTypeAndCodeClass(codeType, codeClass, countryId);
    }
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getCustomerCodeTypeByCustomerClass(co.com.directv.sdii.model.vo.CustomerClassVO)
     */
    public List<CustomerClassTypeVO> getCustomerClassTypeByCustomerClass(CustomerClassVO customerClass) throws BusinessException {
        return business.getCustomerClassTypeByCustomerClass(customerClass);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getAll()
     */
    public List<CustomerClassTypeVO> getAll(Long countryId) throws BusinessException {
        return business.getAll(countryId);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#createCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    public void createCustomerClassType(CustomerClassTypeVO obj) throws BusinessException {
        business.createCustomerClassType(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#updateCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    public void updateCustomerCodeType(CustomerClassTypeVO obj) throws BusinessException {
        business.updateCustomerClassType(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#deleteCustomerCodeType(co.com.directv.sdii.model.vo.CustomerCodeTypeVO)
     */
    public void deleteCustomerClassType(CustomerClassTypeVO obj) throws BusinessException {
        business.deleteCustomerClassType(obj);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigTiposClienteFacadeLocal#getAllCustomerClass()
     */
	@Override
	public List<CustomerClassVO> getAllCustomerClass(Long countryId) throws BusinessException {
		return business.getAllCustomerClass(countryId);
	}
	

	@Override
	public CustomerClassVO getCustomerClassByID(Long id)
			throws BusinessException {
		return business.getCustomerClassByID(id);
	}

	@Override
	public CustomerClassTypeVO getCustomerClassTypeByCode(
			String codeType, String codeClass, Long countryId) throws BusinessException {
		return business.getCustomerClassTypeByCodeTypeAndCodeClass(codeType, codeClass, countryId);
	}

	@Override
	public List<CustomerClassTypeVO> getCustomerClassTypeByNameClass(String name, Long countryId)
			throws BusinessException {
		return business.getCustomerClassTypeByNameClass(name, countryId);
	}

	@Override
	public List<CustomerClassTypeVO> getCustomerClassTypeByNameTypeAndClass(
			String nameType, String nameClass, Long countryId) throws BusinessException {
		return business.getCustomerClassTypeByNameTypeAndClass(nameType, nameClass, countryId);
	}

	@Override
	public CustomerTypeVO getCustomerTypeByID(Long id) throws BusinessException {
		return business.getCustomerTypeByID(id);
	}

	@Override
	public void updateCustomerClassType(CustomerClassTypeVO obj)
			throws BusinessException {
		business.updateCustomerClassType(obj);
	}

	@Override
	public void createCustomerType(CustomerTypeVO obj) throws BusinessException {
		business.createCustomerType(obj);
	}

	@Override
	public List<CustomerCategoryVO> getAllCustomerCategory()
			throws BusinessException {
		return business.getAllCustomerCategory();
	}
    
	@Override
	public CustomerCategoryVO getCustomerCategoryByCustomerClassCode(String customerClassCode) throws BusinessException {
		return business.getCustomerCategoryByCustomerClassCode(customerClassCode);
	}
	
	/*
     * Req-0096 - Requerimiento Consolidado Asignador
     * 
     * Metodo: Obtiene categorias de clientes
     * @return lista con las categorias de cliente definidas en la propiedad CUSTOMER_CATEGORY_DEALER_CONF
     * , una lista vacia en caso que no exista ninguna categoría de cliente
     * @throws BusinessException en caso de error al ejecutar la operación,
     * @author ialessan
	 */
	@Override	
	public List<CustomerCategoryVO> getCustomerCategoryForDealerConf(Long countryId) 
			throws BusinessException{
				  return business.getCustomerCategoryForDealerConf(countryId);
			}

	@Override
	public List<CustomerClassVO> getAllCustomerClassByCategoryId(
			Long countryId, Long categoryId) throws BusinessException {
		return business.getAllCustomerClassByCategoryId(countryId,categoryId);
	}

	@Override
	public List<CustomerTypeVO> getAllCustomerTypes(Long countryId)
			throws BusinessException {
		return business.getAllCustomerTypes(countryId);
	}

	@Override
	public void updateCustomerType(CustomerTypeVO obj) throws BusinessException {
		business.updateCustomerType(obj);
		
	}

	@Override
	public CustomerTypeVO getCustomerTypeByCode(String code, Long countryId)throws BusinessException{
		return business.getCustomerTypeByCode(code, countryId);
	}
	
}

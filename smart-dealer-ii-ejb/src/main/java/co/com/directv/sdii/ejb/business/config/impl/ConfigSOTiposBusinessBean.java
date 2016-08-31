package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ShippingOrderType;
import co.com.directv.sdii.model.vo.ShippingOrderTypeVO;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderTypeDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración Tipos de Shipping Order.
 *
 * Caso de Uso CFG - 19 - Gestionar Códigos de Tipos de Shipping Order
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigSOTiposBusinessLocal",mappedName="ejb/ConfigSOTiposBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigSOTiposBusinessBean extends BusinessBase implements ConfigSOTiposBusinessLocal {

    @EJB(name="ShippingOrderTypeDAOLocal",beanInterface=ShippingOrderTypeDAOLocal.class)
    private ShippingOrderTypeDAOLocal dAOSOTypeBean;

    private final static Logger log = UtilsBusiness.getLog4J(ConfigSOTiposBusinessBean.class);

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#getShippingOrderTypeByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderTypeVO getShippingOrderTypeByID(Long id) throws BusinessException {
      log.debug("== Inicia getShippingOrderTypeByID/ConfigSOTiposBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ShippingOrderType soTypeId = dAOSOTypeBean.getShippingOrderTypeByID(id);
            if (soTypeId != null) {
                ShippingOrderTypeVO soTypeVo = UtilsBusiness.copyObject(ShippingOrderTypeVO.class, soTypeId);
                return soTypeVo;
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/getShippingOrderTypeByID");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina getShippingOrderTypeByID/ConfigSOTiposBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#getShippingOrderTypeByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderTypeVO getShippingOrderTypeByCode(String code) throws BusinessException {
        log.debug("== Inicia getShippingOrderTypeByCode/ConfigAlianzasBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            ShippingOrderType soType = dAOSOTypeBean.getShippingOrderTypeByCode(code);
            if(soType == null)
                return null;

            ShippingOrderTypeVO soTypeVO = UtilsBusiness.copyObject(ShippingOrderTypeVO.class, soType);

            return soTypeVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/getShippingOrderTypeByCode");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina getShippingOrderTypeByCode/ConfigAlianzasBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#getShippingOrderTypeByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderTypeVO> getShippingOrderTypeByName(String name) throws BusinessException {
         log.debug("== Inicia getShippingOrderTypeByName/ConfigSOTiposBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(name, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            List<ShippingOrderTypeVO> listVo = UtilsBusiness.convertList(dAOSOTypeBean.getShippingOrderTypeByName(name), ShippingOrderTypeVO.class);

            return listVo;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/getShippingOrderTypeByName");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina getShippingOrderTypeByName/ConfigSOTiposBusinessBean ==");
        }
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderTypeVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigSOTiposBusinessBean ==");
        try {
            List<ShippingOrderTypeVO> listVo = UtilsBusiness.convertList(dAOSOTypeBean.getAll(), ShippingOrderTypeVO.class);
            return listVo;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/getAll");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina getAll/ConfigSOTiposBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#createShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        log.debug("== Inicio createShippingOrderType/ConfigSOTiposBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            if(getShippingOrderTypeByCode(obj.getShippingOrderCode())!=null){
            	log.debug(ErrorBusinessMessages.SHIPPING_ORDER_TYPE_ALREADY_EXIST.getMessage());
                throw new BusinessException(ErrorBusinessMessages.SHIPPING_ORDER_TYPE_ALREADY_EXIST.getCode(),ErrorBusinessMessages.SHIPPING_ORDER_TYPE_ALREADY_EXIST.getMessage());
            }
            ShippingOrderType sotypePojo = UtilsBusiness.copyObject(ShippingOrderType.class, obj);
            dAOSOTypeBean.createShippingOrderType(sotypePojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/createShippingOrderType");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina createShippingOrderType/ConfigSOTiposBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#updateShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        log.debug("== Inicia updateShippingOrderType/ConfigSOTiposBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            ShippingOrderType soTypePojo = UtilsBusiness.copyObject(ShippingOrderType.class, obj);
            dAOSOTypeBean.updateShippingOrderType(soTypePojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/updateShippingOrderType");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina updateShippingOrderType/ConfigSOTiposBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOTiposBusinessLocal#deleteShippingOrderType(co.com.directv.sdii.model.vo.ShippingOrderTypeVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrderType(ShippingOrderTypeVO obj) throws BusinessException {
        log.debug("== Inicia deleteShippingOrderType/ConfigSOTiposBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ShippingOrderType soTypePojo = UtilsBusiness.copyObject(ShippingOrderType.class, obj);
            dAOSOTypeBean.deleteShippingOrderType(soTypePojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigSOTiposBusinessBean/deleteShippingOrderType");
            throw super.manageException(ex);
        }  finally {
            log.debug("== Termina deleteShippingOrderType/ConfigSOTiposBusinessBean ==");
        }
    }

}

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
import co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ShippingOrderStatus;
import co.com.directv.sdii.model.vo.ShippingOrderStatusVO;
import co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración Estados de Shipping Order.
 *
 * Caso de Uso CFG - 20 - Gestionar Códigos de Estado de Shipping Order
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.config.ShippingOrderStatusDAOLocal
 */
@Stateless(name="ConfigSOEstadosBusinessLocal",mappedName="ejb/ConfigSOEstadosBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigSOEstadosBusinessBean extends BusinessBase implements ConfigSOEstadosBusinessLocal {

    @EJB(name="ShippingOrderStatusDAOLocal",beanInterface=ShippingOrderStatusDAOLocal.class)
    private ShippingOrderStatusDAOLocal dAOSOStatusBean;

    private final static Logger log = UtilsBusiness.getLog4J(ConfigSOEstadosBusinessBean.class);

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#getShippingOrderStatusByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderStatusVO getShippingOrderStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getShippingOrderStatusByID/ConfigSOEstadosBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ShippingOrderStatus soStatusId = dAOSOStatusBean.getShippingOrderStatusByID(id);
            if (soStatusId != null) {
                ShippingOrderStatusVO soStatusVo = UtilsBusiness.copyObject(ShippingOrderStatusVO.class, soStatusId);
                return soStatusVo;
            }
            return null;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getShippingOrderStatusByID/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getShippingOrderStatusByID/ConfigSOEstadosBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#getShippingOrderStatusByCode(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ShippingOrderStatusVO getShippingOrderStatusByCode(String code) throws BusinessException {
        log.debug("== Inicia getShippingOrderStatusByCode/ConfigSOEstadosBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            ShippingOrderStatus soType = dAOSOStatusBean.getShippingOrderStatusByCode(code);
            if(soType == null)
                return null;

            ShippingOrderStatusVO soTypeVO = UtilsBusiness.copyObject(ShippingOrderStatusVO.class, soType);

            return soTypeVO;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getShippingOrderStatusByCode/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrderStatusByCode/ConfigSOEstadosBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#getShippingOrderStatusByName(java.lang.String)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderStatusVO> getShippingOrderStatusByName(String name) throws BusinessException {
         log.debug("== Inicia getShippingOrderStatusByName/ConfigSOEstadosBusinessBean ==");
        try {
            List<ShippingOrderStatusVO> listVo = UtilsBusiness.convertList(dAOSOStatusBean.getShippingOrderStatusByName(name), ShippingOrderStatusVO.class);

            return listVo;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getShippingOrderStatusByName/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getShippingOrderStatusByName/ConfigSOEstadosBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#getAll()
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ShippingOrderStatusVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigSOEstadosBusinessBean ==");
        try {
            List<ShippingOrderStatusVO> listVo = UtilsBusiness.convertList(dAOSOStatusBean.getAll(), ShippingOrderStatusVO.class);
            return listVo;
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion getlAll/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina getAll/ConfigSOEstadosBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#createShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        log.debug("== Inicio createShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            if(getShippingOrderStatusByCode(obj.getSoStatusCode())!=null){
            	log.debug(ErrorBusinessMessages.SHIPPING_ORDER_STATUS_ALREADY_EXIST.getMessage());
                throw new BusinessException(ErrorBusinessMessages.SHIPPING_ORDER_STATUS_ALREADY_EXIST.getCode(),ErrorBusinessMessages.SHIPPING_ORDER_STATUS_ALREADY_EXIST.getMessage());
            }

            ShippingOrderStatus sostatusPojo = UtilsBusiness.copyObject(ShippingOrderStatus.class, obj);
            dAOSOStatusBean.createShippingOrderStatus(sostatusPojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion createShippingOrderStatus/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina createShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        }
    }

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#updateShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        log.debug("== Inicia updateShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());

            if(!BusinessRuleValidationManager.getInstance().isValid(obj)){
            	log.debug(ErrorBusinessMessages.ERROR_DATA.getMessage());
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode(),ErrorBusinessMessages.ERROR_DATA.getMessage());
            }

            ShippingOrderStatus soStatusPojo = UtilsBusiness.copyObject(ShippingOrderStatus.class, obj);
            dAOSOStatusBean.updateShippingOrderStatus(soStatusPojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion updateShippingOrderStatus/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina updateShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        }
    }

     
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigSOEstadosBusinessLocal#deleteShippingOrderStatus(co.com.directv.sdii.model.vo.ShippingOrderStatusVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteShippingOrderStatus(ShippingOrderStatusVO obj) throws BusinessException {
        log.debug("== Inicia deleteShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            ShippingOrderStatus soStatusPojo = UtilsBusiness.copyObject(ShippingOrderStatus.class, obj);
            dAOSOStatusBean.deleteShippingOrderStatus(soStatusPojo);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion deleteShippingOrderStatus/ConfigSOEstadosBusinessBean");
            throw super.manageException(ex);
        }finally {
            log.debug("== Termina deleteShippingOrderStatus/ConfigSOEstadosBusinessBean ==");
        }
    }

}

package co.com.directv.sdii.ejb.business.config.impl;

import java.util.ArrayList;
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
import co.com.directv.sdii.ejb.business.config.ConfigTiposServicioBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.impl.VehiclesCRUDBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Service;
import co.com.directv.sdii.model.pojo.ServiceStatus;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.ServiceStatusVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.persistence.dao.config.ServiceDAOLocal;
import co.com.directv.sdii.persistence.dao.config.ServiceStatusDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración SO Tipos Servicio.
 *
 * Caso de Uso CFG - 03 - Gestionar Códigos y Tipos Servicios
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigTiposServicioBusinessLocal",mappedName="ejb/ConfigTiposServicioBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigTiposServicioBusinessBean extends BusinessBase implements ConfigTiposServicioBusinessLocal {

    private final static Logger log = UtilsBusiness.getLog4J(VehiclesCRUDBean.class);
    
    @EJB(name="ServiceDAOLocal",beanInterface=ServiceDAOLocal.class)
    private ServiceDAOLocal serviceDAO;
    
    @EJB(name="ServiceStatusDAOLocal",beanInterface=ServiceStatusDAOLocal.class)
    private ServiceStatusDAOLocal serviceStatusDAO;
    
//    @EJB(name="ConfigMatrizCapacidadPorServicioBusinessLocal",beanInterface=ConfigMatrizCapacidadPorServicioBusinessLocal.class)
//    private ConfigMatrizCapacidadPorServicioBusinessLocal requiredCapServBusiness;
    

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceVO getServiceByID(Long id) throws BusinessException {
        log.debug("== Inicia getServiceByID/ConfigJornadasBusinessBean ==");
        try {
            if (id == null) {
                log.debug("== Error Parametro id con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            Service sh = serviceDAO.getServiceByID(id);
            if (sh == null) {
                return null;
            }

            ServiceVO shVO = UtilsBusiness.copyObject(ServiceVO.class, sh);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getServiceByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByID/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ServiceVO getServiceByCode(String code) throws BusinessException {
        log.debug("== Inicia getServiceByCode/ConfigJornadasBusinessBean ==");
        try {
            if (code == null) {
                log.debug("== Error Parametro code con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            Service sh = serviceDAO.getServiceByCode(code);
            if (sh == null) {
                return null;
            }

            ServiceVO shVO = UtilsBusiness.copyObject(ServiceVO.class, sh);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getServiceByCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByCode/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceVO> getServiceByName(String name) throws BusinessException {
        log.debug("== Inicia getServiceByName/ConfigJornadasBusinessBean ==");
        try {
            if (name == null) {
                log.debug("== Error Parametro code con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            List<Service> sh = serviceDAO.getServiceByName(name);
            if (sh == null) {
                return null;
            }

            List<ServiceVO> shVO = UtilsBusiness.convertList(sh, ServiceVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getServiceByName");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceByName/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigJornadasBusinessBean ==");
        try {

            List<Service> sh = serviceDAO.getAll();
            if (sh == null) {
                return null;
            }

            List<ServiceVO> shVO = UtilsBusiness.convertList(sh, ServiceVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigJornadasBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceVO> getAllByServiceCodeOpening(boolean serviceCodeOpening) throws BusinessException {
        log.debug("== Inicia getAllByServiceCodeOpening/ConfigJornadasBusinessBean ==");
        try {

            List<Service> sh = serviceDAO.getAllByServiceCodeOpening(serviceCodeOpening);
            if (sh == null) {
                return null;
            }

            List<ServiceVO> shVO = UtilsBusiness.convertList(sh, ServiceVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getAllByServiceCodeOpening");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllByServiceCodeOpening/ConfigJornadasBusinessBean ==");
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceVO> getActiveServices() throws BusinessException {
        log.debug("== Inicia getActiveServices/ConfigJornadasBusinessBean ==");
        try {

        	ServiceStatus activeServiceStatus = serviceStatusDAO.getServiceStatusByCode(CodesBusinessEntityEnum.CODIGO_SERVICE_STATUS_ACTIVE.getCodeEntity());
        	UtilsBusiness.assertNotNull(activeServiceStatus, ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getCode(), ErrorBusinessMessages.SERVICE_DOES_NOT_EXIST.getMessage());
        	
            List<Service> sh = serviceDAO.getServiceByState(activeServiceStatus);
            if (sh == null) {
                return null;
            }

            List<ServiceVO> shVO = UtilsBusiness.convertList(sh, ServiceVO.class);
            return shVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getActiveServices");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getActiveServices/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<ServiceVO> getServicesByDealer(DealerVO dealer) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createService(ServiceVO obj, Long userId) throws BusinessException {
    	log.debug("== Inicia createService/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.debug("== Error: El parametro obj viene establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validaci�n de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }
            
            if(serviceDAO.getServiceByCode(obj.getServiceCode())!= null){
            	log.debug("== Error en la Capa de Negocio debido a una Validaci�n de negocio el c�digo de servicio "+obj.getServiceCode()+" ya existe ==");
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }
            //serviceDAO.createService(UtilsBusiness.copyObject(Service.class, obj));
            //Cambios realizados por gfandino el 28 de julio de 2010
            
            Service serviceTmp = UtilsBusiness.copyObject(Service.class, obj);
            serviceDAO.createService(serviceTmp);
            
            //Fin de lo cambios de gfandino
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/createService");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createService/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateService(ServiceVO obj) throws BusinessException {

        log.debug("== Inicia updateService/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.debug("== Error: El parametro obj viene establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }
            
            Service oldService = serviceDAO.getServiceByCode(obj.getServiceCode()); 
            if(oldService != null && oldService.getId().longValue() != obj.getId().longValue()){
            	log.debug("== Error en la Capa de Negocio debido a una Validación de negocio el código de servicio "+obj.getServiceCode()+" ya existe ==");
            	throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(),ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
            }
            
            serviceDAO.updateService(UtilsBusiness.copyObject(Service.class, obj));
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/updateService");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateService/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteService(ServiceVO obj) throws BusinessException {
        log.debug("== Inicia deleteService/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.debug("== Error: El parametro obj viene establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }
            serviceDAO.deleteService(UtilsBusiness.copyObject(Service.class, obj));
        }  catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/deleteService");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteService/ConfigJornadasBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigTiposServicioBusinessLocal#getAllServicesStatus()
	 */
	@Override
	public List<ServiceStatusVO> getAllServicesStatus()throws BusinessException {
		log.debug("== Inicia getAllServicesStatus/ConfigJornadasBusinessBean ==");
        try {
        	List<ServiceStatusVO> servicesStatus = new ArrayList<ServiceStatusVO>();
        	List<ServiceStatus> servicesStatusPojo = serviceStatusDAO.getAll();
            if (servicesStatusPojo == null) {
                return servicesStatus;
            }
            servicesStatus = UtilsBusiness.convertList(servicesStatusPojo, ServiceStatusVO.class);
            return servicesStatus;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigTiposServicioBusinessBean/getAllServicesStatus");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllServicesStatus/ConfigJornadasBusinessBean ==");
        }
	}
}

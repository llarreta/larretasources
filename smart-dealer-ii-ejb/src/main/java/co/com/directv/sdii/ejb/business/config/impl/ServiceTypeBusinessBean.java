package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ServiceTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceType;
import co.com.directv.sdii.model.vo.ServiceTypeVO;
import co.com.directv.sdii.persistence.dao.config.ServiceTypeDAOLocal;

/**
 * Esta clase implementa las operaciones de negocio para la interfaz
 * ServiceTypeBusinessBeanLocal
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 *
 */
@Stateless(name = "ServiceTypeBusinessBeanLocal", mappedName = "ejb/ServiceTypeBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ServiceTypeBusinessBean extends BusinessBase implements ServiceTypeBusinessBeanLocal {
	
	@EJB(beanInterface=ServiceTypeDAOLocal.class, name="ServiceTypeDAOLocal")
	private ServiceTypeDAOLocal daoServiceType;
	private final static Logger log = UtilsBusiness.getLog4J(ServiceTypeBusinessBean.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ServiceTypeVO> getAllServiceType() throws BusinessException{
		log.debug("== Inicia getAllServiceType/ServiceTypeBusinessBean ==");
        try {
           List<ServiceType> serviceTypes = daoServiceType.getAll();
           if(serviceTypes == null)
        	   return null;
           return UtilsBusiness.convertList(serviceTypes, ServiceTypeVO.class);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ServiceTypeBusinessBean/getAllServiceType");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllServiceType/ServiceTypeBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ServiceTypeBusinessBeanLocal#getServiceTypeByCode(java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ServiceTypeVO getServiceTypeByServiceCode(String serviceCode)
			throws BusinessException {
		log.debug("== Inicia getServiceTypeByServiceCode/ServiceTypeBusinessBean ==");
        try {
           ServiceType serviceType = daoServiceType.getServiceTypeByServiceCode(serviceCode);
           if(serviceType == null)
        	   return null;
           return UtilsBusiness.copyObject(ServiceTypeVO.class, serviceType);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ServiceTypeBusinessBean/getServiceTypeByServiceCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceTypeByServiceCode/ServiceTypeBusinessBean ==");
        }
	}
}

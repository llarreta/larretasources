package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import co.com.directv.sdii.ejb.business.config.ConfigJornadasStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigJornadasStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ServiceHourStatusVO;

/**
 * Session Bean implementation class ConfigJornadasStatusFacadeBean
 */
@Stateless(name="ConfigJornadasStatusFacadeBeanLocal",mappedName="ejb/ConfigJornadasStatusFacadeBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigJornadasStatusFacadeBean implements ConfigJornadasStatusFacadeBeanLocal {

   @EJB(name="ConfigJornadasStatusBusinessBeanLocal", beanInterface=ConfigJornadasStatusBusinessBeanLocal.class)
   private ConfigJornadasStatusBusinessBeanLocal statusBusinessBean;

   @Override
   public List<ServiceHourStatusVO> getAllServiceHourStatus()throws BusinessException {
	   return statusBusinessBean.getAllServiceHourStatus();
   }

}

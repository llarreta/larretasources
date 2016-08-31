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
import co.com.directv.sdii.ejb.business.config.ParameterTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ParameterType;
import co.com.directv.sdii.model.vo.ParameterTypeVO;
import co.com.directv.sdii.persistence.dao.config.ParameterTypeDAOLocal;

/**
 * Esta clase implementa las operaciones de negocio para
 * las operaciones de la interfaz ParameterTypeBusinessBeanLocal
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@Stateless(name = "ParameterTypeBusinessBeanLocal", mappedName = "ejb/ParameterTypeBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ParameterTypeBusinessBean extends BusinessBase implements ParameterTypeBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(ParameterTypeBusinessBean.class);
	
	@EJB(name = "ParameterTypeDAOLocal", beanInterface = ParameterTypeDAOLocal.class)
	private ParameterTypeDAOLocal daoParameterType;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ParameterTypeVO> getAllParametersTypes() throws BusinessException {
		log.debug("== Inicia getServiceHoursByID/ConfigJornadasBusinessBean ==");
        try {
            
        	List<ParameterType> parameterTypeVOs = daoParameterType.getAll();
        	if(parameterTypeVOs == null)
        		return null;
        	
        	return UtilsBusiness.convertList(parameterTypeVOs, ParameterTypeVO.class);

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ParameterTypeBusinessBean/getAllParametersTypes");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getServiceHoursByID/ConfigJornadasBusinessBean ==");
        }
	}

}

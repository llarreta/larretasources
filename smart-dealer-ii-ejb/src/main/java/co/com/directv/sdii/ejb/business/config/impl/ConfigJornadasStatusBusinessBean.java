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
import co.com.directv.sdii.ejb.business.config.ConfigJornadasStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.ServiceHourStatus;
import co.com.directv.sdii.model.vo.ServiceHourStatusVO;
import co.com.directv.sdii.persistence.dao.config.ServiceHourStatusDAOLocal;

/**
 * Esta clase define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Jornadas.
 *
 *
 * @author Jimmy Vélez Muñoz
 */
@Stateless(name="ConfigJornadasStatusBusinessBeanLocal", mappedName="ejb/ConfigJornadasStatusBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigJornadasStatusBusinessBean extends BusinessBase implements ConfigJornadasStatusBusinessBeanLocal {
	private final static Logger log = UtilsBusiness.getLog4J(ConfigJornadasStatusBusinessBean.class);
	
	@EJB(name="ServiceHourStatusDAOLocal", beanInterface=ServiceHourStatusDAOLocal.class)
	private ServiceHourStatusDAOLocal serviceHourStatusDAO;
	
	/**
	 * Obtiene todos los estados de las jornadas del sistema
	 * @return List<ServiceHourStatusVO> Listado de estados de jornadas del sistema
	 * @throws BusinessException 
	 * @author jcasas
	 */
	@Override
	public List<ServiceHourStatusVO> getAllServiceHourStatus() throws BusinessException {
		log.debug("== Inicia getAllServiceHourStatus/ConfigJornadasBusinessBean ==");
		try {
			List<ServiceHourStatus> hourStatus = this.serviceHourStatusDAO.getAll();
			if(hourStatus == null)
				return null;
			return UtilsBusiness.convertList(hourStatus, ServiceHourStatusVO.class);

		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllServiceHourStatus/ConfigJornadasBusinessBean ==");
		}
	}
}

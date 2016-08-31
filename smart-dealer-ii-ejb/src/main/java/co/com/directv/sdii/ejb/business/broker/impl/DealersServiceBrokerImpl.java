package co.com.directv.sdii.ejb.business.broker.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.ejb.business.broker.DealersServiceBrokerLocal;
import co.com.directv.sdii.ejb.business.broker.IServiceBroker;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.ws.model.dto.DealersWSDTO;

/**
 * 
 * Implementación de Broker de servicios asociados con el módulo de Dealers 
 * 
 * Fecha de Creación: 14/04/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.broker.DealersServiceBrokerLocal
 */
@Stateless(name="DealersServiceBrokerLocal",mappedName="ejb/DealersServiceBrokerLocal")
public class DealersServiceBrokerImpl extends IBSWSBase implements DealersServiceBrokerLocal, IServiceBroker {

	private final static Logger log = UtilsBusiness.getLog4J(DealersServiceBrokerImpl.class);
	
	private static final String IS_PRINCIPAL_CODE = "TRUE";
	
	@EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
    private SystemParameterDAOLocal systemParameterDao;
	
	@Override
	public List<DealerVO> getDealerCodes(Country country)
			throws BusinessException {
		return null;
	}

	@Override
	public DealersWSDTO getDealerInfo(Long dealerCode, String depotCode,
			Country country) throws BusinessException {
		return null;
	}

	@Override
	public BusinessException manageServiceException(Throwable e) {
		return null;
	}
	
}

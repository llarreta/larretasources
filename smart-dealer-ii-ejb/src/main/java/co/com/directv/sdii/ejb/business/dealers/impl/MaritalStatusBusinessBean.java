package co.com.directv.sdii.ejb.business.dealers.impl;

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
import co.com.directv.sdii.ejb.business.dealers.MaritalStatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.MaritalStatusVO;
import co.com.directv.sdii.persistence.dao.dealers.MaritalStatusDAOLocal;

@Stateless(name="MaritalStatusBusinessBeanLocal",mappedName="ejb/MaritalStatusBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MaritalStatusBusinessBean extends BusinessBase implements MaritalStatusBusinessBeanLocal {
	
	private final static Logger log = UtilsBusiness.getLog4J(MaritalStatusBusinessBean.class);
	
	@EJB(name="MaritalStatusDAOLocal",beanInterface=MaritalStatusDAOLocal.class)
	private MaritalStatusDAOLocal maritalStatusDAO;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.MaritalStatusBusinessBeanLocal#getAllMaritalStatuss()
	 */
	@Override
	public List<MaritalStatusVO> getAllMaritalStatuss() throws BusinessException {
		log.debug("== Inicia getAllMaritalStatuss/MaritalStatusBusinessBean ==");
		try {
			return  UtilsBusiness.convertList(maritalStatusDAO.getAllMaritalStatuss(), MaritalStatusVO.class);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMaritalStatuss/MaritalStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllMaritalStatuss/MaritalStatusBusinessBean ==");
		}
	}

   

}

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
import co.com.directv.sdii.ejb.business.dealers.EducationLevelBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EducationLevelVO;
import co.com.directv.sdii.persistence.dao.dealers.EducationLevelDAOLocal;

@Stateless(name="EducationLevelBusinessBeanLocal",mappedName="ejb/EducationLevelBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EducationLevelBusinessBean extends BusinessBase implements EducationLevelBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(EducationLevelBusinessBean.class);
	
	@EJB(name="EducationLevelDAOLocal",beanInterface=EducationLevelDAOLocal.class)
	private EducationLevelDAOLocal educationLevelDAO;

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.EducationLevelBusinessBeanLocal#getAllEducationLevel()
	 */
	@Override
	public List<EducationLevelVO> getAllEducationLevel() throws BusinessException {
		log.debug("== Inicia getAllMaritalStatuss/MaritalStatusBusinessBean ==");
		try {
			return  UtilsBusiness.convertList(educationLevelDAO.getAllEducationLevel(), EducationLevelVO.class);
		} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllMaritalStatuss/MaritalStatusBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllMaritalStatuss/MaritalStatusBusinessBean ==");
		}
	}
}

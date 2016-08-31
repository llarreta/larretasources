/**
 * Creado 25/11/2010 18:09:08
 */
package co.com.directv.sdii.ejb.business.dealers.impl;

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
import co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SdiiTimeZone;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.SdiiTimeZoneVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.dealers.SdiiTimeZoneDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;

/**
 * Bean de negocio para las operaciones con zonas horarias
 * 
 * Fecha de Creación: 25/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal
 */
@Stateless(name="SdiiTimeZoneBeanLocal",mappedName="ejb/SdiiTimeZoneBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SdiiTimeZoneBean extends BusinessBase implements
		SdiiTimeZoneBeanLocal {

	private static final Logger log = UtilsBusiness.getLog4J(ArpCRUDBean.class);
	
	@EJB(name="SdiiTimeZoneDAOLocal", beanInterface=SdiiTimeZoneDAOLocal.class)
	private SdiiTimeZoneDAOLocal daoTimeZone;
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal daoUser;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal#getAllTimeZones()
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SdiiTimeZoneVO> getAllTimeZones() throws BusinessException {
		log.debug("== Inicia getAllTimeZones/SdiiTimeZoneBean ==");
        try {
           List<SdiiTimeZone> allTimeZones = daoTimeZone.getAllTimeZones();
           List<SdiiTimeZoneVO> allTimeZonesVo = UtilsBusiness.convertList(allTimeZones, SdiiTimeZoneVO.class);
        	
            return allTimeZonesVo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllTimeZones/SdiiTimeZoneBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllTimeZones/SdiiTimeZoneBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal#getTimeZoneById(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SdiiTimeZoneVO getTimeZoneById(Long id) throws BusinessException {
		log.debug("== Inicia getTimeZoneById/SdiiTimeZoneBean ==");
        try {
        	SdiiTimeZone result = daoTimeZone.getTimeZoneById(id);
        	SdiiTimeZoneVO resultVo = UtilsBusiness.copyObject(SdiiTimeZoneVO.class, result);
            return resultVo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getTimeZoneById/SdiiTimeZoneBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTimeZoneById/SdiiTimeZoneBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal#getTimeZonesByCountryId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SdiiTimeZoneVO> getTimeZonesByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicia getTimeZonesByCountryId/SdiiTimeZoneBean ==");
        try {
        	List<SdiiTimeZone> allTimeZones = daoTimeZone.getTimeZonesByCountryId(countryId);
            List<SdiiTimeZoneVO> allTimeZonesVo = UtilsBusiness.convertList(allTimeZones, SdiiTimeZoneVO.class);
         	
            return allTimeZonesVo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getTimeZonesByCountryId/SdiiTimeZoneBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getTimeZonesByCountryId/SdiiTimeZoneBean ==");
        }
	}

	@Override
	public UserVO getCurrentDateByUserId(Long userId) throws BusinessException {
		log.debug("== Inicia getCurrentDateByUserId/SdiiTimeZoneBean ==");
        try {
        	User user = daoUser.getUserById(userId);
        	if(user == null){
        		throw new BusinessException(ErrorBusinessMessages.USER_NOT_EXIST.getCode(),  ErrorBusinessMessages.USER_NOT_EXIST.getMessage());
        	}
        	UserVO userVO = new UserVO();
        	userVO.setSdiiTimeZone(user.getSdiiTimeZone());
        	userVO.setId(user.getId());
        	userVO.setCurrentDate(UtilsBusiness.getCurrentTimeZoneDateByUserId(userId, daoUser));
            return userVO;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getCurrentDateByUserId/SdiiTimeZoneBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCurrentDateByUserId/SdiiTimeZoneBean ==");
        }
	}

}

/**
 * Creado 25/11/2010 18:35:36
 */
package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.SdiiTimeZoneBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.SdiiTimeZoneFacadeBeanLocal;
import co.com.directv.sdii.model.vo.SdiiTimeZoneVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 25/11/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@Stateless(name="SdiiTimeZoneFacadeBeanLocal",mappedName="ejb/SdiiTimeZoneFacadeBeanLocal")
public class SdiiTimeZoneFacadeBean implements SdiiTimeZoneFacadeBeanLocal {

	@EJB(name="SdiiTimeZoneBeanLocal",beanInterface=SdiiTimeZoneBeanLocal.class)
    private SdiiTimeZoneBeanLocal sdiiTimeZoneBean;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.SdiiTimeZoneFacadeBeanLocal#getAllTimeZones()
	 */
	@Override
	public List<SdiiTimeZoneVO> getAllTimeZones() throws BusinessException {
		return sdiiTimeZoneBean.getAllTimeZones();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.SdiiTimeZoneFacadeBeanLocal#getTimeZoneById(java.lang.Long)
	 */
	@Override
	public SdiiTimeZoneVO getTimeZoneById(Long id) throws BusinessException {
		return sdiiTimeZoneBean.getTimeZoneById(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.SdiiTimeZoneFacadeBeanLocal#getTimeZonesByCountryId(java.lang.Long)
	 */
	@Override
	public List<SdiiTimeZoneVO> getTimeZonesByCountryId(Long countryId)
			throws BusinessException {
		return sdiiTimeZoneBean.getTimeZonesByCountryId(countryId);
	}

	@Override
	public UserVO getCurrentDateByUserId(Long userId)
			throws BusinessException {
		return sdiiTimeZoneBean.getCurrentDateByUserId(userId);
	}

}

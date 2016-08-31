package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.MeasureUnitBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.MeasureUnitResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MeasureUnitVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD MeasureUnit
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.MeasureUnitFacadeLocal
 */
@Stateless(name="MeasureUnitFacadeLocal",mappedName="ejb/MeasureUnitFacadeLocal")
public class MeasureUnitFacadeBean implements MeasureUnitFacadeBeanLocal {

		
    @EJB(name="MeasureUnitBusinessBeanLocal", beanInterface=MeasureUnitBusinessBeanLocal.class)
    private MeasureUnitBusinessBeanLocal businessMeasureUnit;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MeasureUnitFacadeLocal#getAllMeasureUnits()
     */
    public List<MeasureUnitVO> getAllMeasureUnits() throws BusinessException {
    	return businessMeasureUnit.getAllMeasureUnits();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MeasureUnitFacadeLocal#getMeasureUnitsByID(java.lang.Long)
     */
    public MeasureUnitVO getMeasureUnitByID(Long id) throws BusinessException {
    	return businessMeasureUnit.getMeasureUnitByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MeasureUnitFacadeLocal#createMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	public void createMeasureUnit(MeasureUnitVO obj) throws BusinessException {
		businessMeasureUnit.createMeasureUnit(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MeasureUnitFacadeLocal#updateMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	public void updateMeasureUnit(MeasureUnitVO obj) throws BusinessException {
		businessMeasureUnit.updateMeasureUnit(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MeasureUnitFacadeLocal#deleteMeasureUnit(co.com.directv.sdii.model.vo.MeasureUnitVO)
	 */
	public void deleteMeasureUnit(MeasureUnitVO obj) throws BusinessException {
		businessMeasureUnit.deleteMeasureUnit(obj);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal#getMeasureUnitByCode(java.lang.String)
	 */
	public MeasureUnitVO getMeasureUnitByCode(String code)
			throws BusinessException {
		return businessMeasureUnit.getMeasureUnitByCode(code);
	}


	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal#getMeasureUnitsByStatus()
	 */
	public List<MeasureUnitVO> getMeasureUnitsByStatus()
			throws BusinessException {
		return businessMeasureUnit.getMeasureUnitsByStatus();
	}
	
	public MeasureUnitResponse getMeasureUnitsByStatus(RequestCollectionInfo requestCollInfo)
			throws BusinessException {
		return businessMeasureUnit.getMeasureUnitsByStatus(requestCollInfo);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MeasureUnitFacadeBeanLocal#getMeasureUnitsByAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public MeasureUnitResponse getMeasureUnitsByAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessMeasureUnit.getMeasureUnitsByAllStatusPage(code,requestCollInfo);
	}
}

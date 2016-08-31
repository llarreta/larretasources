package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.TechnologyBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.TechnologyFacadeBeanLocal;
import co.com.directv.sdii.model.vo.TechnologyVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD Technology
 * 
 * Fecha de Creación: jul 12, 2010
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.TechnologyFacadeLocal
 */
@Stateless(name="TechnologyFacadeLocal",mappedName="ejb/TechnologyFacadeLocal")
public class TechnologyFacadeBean implements TechnologyFacadeBeanLocal {

		
    @EJB(name="TechnologyBusinessBeanLocal", beanInterface=TechnologyBusinessBeanLocal.class)
    private TechnologyBusinessBeanLocal businessTechnology;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.TechnologyFacadeLocal#getAllTechnologys()
     */
    public List<TechnologyVO> getAllTechnologies() throws BusinessException {
    	return businessTechnology.getAllTechnologies();
    }
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.TechnologyFacadeLocal#getActiveTechnologys()
     */
    public List<TechnologyVO> getActiveTechnologies() throws BusinessException {
    	return businessTechnology.getActiveTechnologies();
    }

    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.TechnologyFacadeLocal#getTechnologysByID(java.lang.Long)
     */
    public TechnologyVO getTechnologyByID(Long id) throws BusinessException {
    	return businessTechnology.getTechnologyByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TechnologyFacadeLocal#createTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	public void createTechnology(TechnologyVO obj) throws BusinessException {
		businessTechnology.createTechnology(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TechnologyFacadeLocal#updateTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	public void updateTechnology(TechnologyVO obj) throws BusinessException {
		businessTechnology.updateTechnology(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.TechnologyFacadeLocal#deleteTechnology(co.com.directv.sdii.model.vo.TechnologyVO)
	 */
	public void deleteTechnology(TechnologyVO obj) throws BusinessException {
		businessTechnology.deleteTechnology(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.TechnologyFacadeBeanLocal#getTechnologyByCode(java.lang.String)
	 */
	public TechnologyVO getTechnologyByCode(String code)
			throws BusinessException {
		return businessTechnology.getTechnologyByCode(code);
	}
}

package co.com.directv.sdii.facade.stock.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.stock.MovementTypeBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal;
import co.com.directv.sdii.model.dto.MovementTypeClassDTO;
import co.com.directv.sdii.model.pojo.collection.MovementTypeResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.MovementTypeVO;

/**
 * Implementación de la fachada de las operaciones Tipo CRUD MovementType
 * 
 * Fecha de Creación: jul 12, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeLocal
 */
@Stateless(name="MovementTypeFacadeLocal",mappedName="ejb/MovementTypeFacadeLocal")
public class MovementTypeFacadeBean implements MovementTypeFacadeBeanLocal {

		
    @EJB(name="MovementTypeBusinessBeanLocal", beanInterface=MovementTypeBusinessBeanLocal.class)
    private MovementTypeBusinessBeanLocal businessMovementType;
    
   
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MovementTypeFacadeLocal#getAllMovementTypes()
     */
    public List<MovementTypeVO> getAllMovementTypes() throws BusinessException {
    	return businessMovementType.getAllMovementTypes();
    }
    
    public List<MovementTypeClassDTO> getAllMovementTypesClass() throws BusinessException {
    	return businessMovementType.getAllMovementTypesClass();
    }
    
    /* (non-Javadoc)
     * @see #{$business_package_name$}.MovementTypeFacadeLocal#getMovementTypesByID(java.lang.Long)
     */
    public MovementTypeVO getMovementTypeByID(Long id) throws BusinessException {
    	return businessMovementType.getMovementTypeByID(id);
    }

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MovementTypeFacadeLocal#createMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	public void createMovementType(MovementTypeVO obj) throws BusinessException {
		businessMovementType.createMovementType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MovementTypeFacadeLocal#updateMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	public void updateMovementType(MovementTypeVO obj) throws BusinessException {
		businessMovementType.updateMovementType(obj);
	}

	/* (non-Javadoc)
	 * @see #{$business_package_name$}.MovementTypeFacadeLocal#deleteMovementType(co.com.directv.sdii.model.vo.MovementTypeVO)
	 */
	public void deleteMovementType(MovementTypeVO obj) throws BusinessException {
		businessMovementType.deleteMovementType(obj);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal#getMovementTypeByCode(java.lang.String)
	 */
	public MovementTypeVO getMovementTypeByCode(String code)
			throws BusinessException {
		return businessMovementType.getMovementTypeByCode(code);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal#getMovementTypesActiveStatus()
	 */
	public List<MovementTypeVO> getMovementTypesActiveStatus()
			throws BusinessException {
		return businessMovementType.getMovementTypesActiveStatus();
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal#getMovementTypesActiveStatus(co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	public MovementTypeResponse getMovementTypesActiveStatus(RequestCollectionInfo requestCollInfo) throws BusinessException{
		return businessMovementType.getMovementTypesActiveStatus(requestCollInfo);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal#getMovementTypesInActiveStatus()
	 */
	public List<MovementTypeVO> getMovementTypesInActiveStatus()
			throws BusinessException {
		return businessMovementType.getMovementTypesInActiveStatus();
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.stock.MovementTypeFacadeBeanLocal#getMovementTypesAllStatusPage(java.lang.String, co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo)
	 */
	@Override
	public MovementTypeResponse getMovementTypesAllStatusPage(String code,
			RequestCollectionInfo requestCollInfo) throws BusinessException {
		return businessMovementType.getMovementTypesAllStatusPage(code,requestCollInfo);
	}

	@Override
	public List<MovementTypeVO> getMovementTypesAtiveByClass(String moveClass)
			throws BusinessException {
		return businessMovementType.getMovementTypesAtiveByClass(moveClass);
	}
	
}

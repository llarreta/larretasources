package co.com.directv.sdii.ejb.business.assign.impl;

import java.util.HashSet;
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
import co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Building;
import co.com.directv.sdii.model.pojo.BuildingAddresses;
import co.com.directv.sdii.model.vo.BuildingVO;
import co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal;
import co.com.directv.sdii.persistence.dao.config.BuildingAddressesDAOLocal;

/**
 * EJB que implementa las operaciones Tipo CRUD Building
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.assign.BuildingDAOLocal
 * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal
 */
@Stateless(name="BuildingBusinessBeanLocal",mappedName="ejb/BuildingBusinessBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BuildingBusinessBean extends BusinessBase implements BuildingBusinessBeanLocal {

    @EJB(name="BuildingDAOLocal", beanInterface=BuildingDAOLocal.class)
    private BuildingDAOLocal daoBuilding;
    
    @EJB(name="BuildingAddressesDAOLocal", beanInterface=BuildingAddressesDAOLocal.class)
    private BuildingAddressesDAOLocal buildingAddressesDAO;
    
    private final static Logger log = UtilsBusiness.getLog4J(BuildingBusinessBean.class);

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal#getAllBuildings()
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<BuildingVO> getAllBuildings() throws BusinessException {
        log.debug("== Inicia getAllBuildings/BuildingBusinessBean ==");
        try {
            return UtilsBusiness.convertList(daoBuilding.getAllBuildings(), BuildingVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllBuildings/BuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllBuildings/BuildingBusinessBean ==");
        }
    }

    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal#getBuildingsByID(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public BuildingVO getBuildingByID(Long id) throws BusinessException {
        log.debug("== Inicia getBuildingByID/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Building objPojo = daoBuilding.getBuildingByID(id);
            if (objPojo == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(BuildingVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getBuildingByID/BuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getBuildingByID/BuildingBusinessBean ==");
        }
    }

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal#createBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createBuilding(BuildingVO obj) throws BusinessException {
        log.debug("== Inicia createBuilding/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	Building buildingPojo = daoBuilding.getBuildingByIBSBuildingCode(obj.getCode());
        	
        	if(buildingPojo != null){
        		log.error("El edificio que está tratando de crear como nuevo ya existe");
        		throw new BusinessException(ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getCode(), ErrorBusinessMessages.OBJECT_ALREADY_EXIST.getMessage());
        	}
        	
        	obj.setImportDate(obj.getLastUpdateDate());
        	buildingPojo = UtilsBusiness.copyObject(Building.class, obj);
        	daoBuilding.createBuilding(buildingPojo);
        	
        	if(buildingPojo.getBuildingAddresses() != null){
        		for(BuildingAddresses ba : buildingPojo.getBuildingAddresses()){
        			ba.setBuildingId(buildingPojo.getId());
        			buildingAddressesDAO.createBuildingAddresses(ba);
        		}
        	}
        	
        	obj.setId(buildingPojo.getId());
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createBuilding/BuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createBuilding/BuildingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal#updateBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateBuilding(BuildingVO obj) throws BusinessException {
        log.debug("== Inicia updateBuilding/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
        	
        	Building buildingPojo = UtilsBusiness.copyObject(Building.class, obj);
        	buildingPojo.setBuildingAddresses(new HashSet<BuildingAddresses>());
        	daoBuilding.updateBuilding(buildingPojo);
        	
        	buildingAddressesDAO.deleteBuildingAddressesByBuildingId(buildingPojo.getId());
        	
        	for(BuildingAddresses ba : obj.getBuildingAddresses()){
        		ba.setBuildingId(buildingPojo.getId());
        		buildingAddressesDAO.createBuildingAddresses(ba);
        	}
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateBuilding/BuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateBuilding/BuildingBusinessBean ==");
        }
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.assign.BuildingBusinessBeanLocal#deleteBuilding(co.com.directv.sdii.model.vo.BuildingVO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteBuilding(BuildingVO obj) throws BusinessException {
        log.debug("== Inicia deleteBuilding/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(obj, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        UtilsBusiness.assertNotNull(obj.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Building objPojo =  UtilsBusiness.copyObject(Building.class, obj);
            daoBuilding.deleteBuilding(objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteBuilding/BuildingBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteBuilding/BuildingBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal#getBuildingByCode(java.lang.String)
	 */
	@Override
	public BuildingVO getBuildingByCode(Long buildingCode) throws BusinessException {
		log.debug("== Inicia getBuildingByCode/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(buildingCode, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            Building objPojo = daoBuilding.getBuildingByIBSBuildingCode(buildingCode);
            return UtilsBusiness.copyObject(BuildingVO.class, objPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getBuildingByCode/BuildingBusinessBean ==" + ex);
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getBuildingByCode/BuildingBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.assign.BuildingBusinessBeanLocal#updateDealerBuilding(java.lang.Long)
	 */
	@Override
	public void updateDealerBuilding(Long countryId) throws BusinessException {
		log.debug("== Inicia updateDealerBuilding/BuildingBusinessBean ==");
        UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        try {
            //LLAMAR AL WEB SERVICE DE ESB PARA OBTENER LA INFORMACIÓN DE LA RELACION DEALER-BUILDING.
        	log.info("--------LLAMAR AL WEB SERVICE DE ESB PARA OBTENER LA INFORMACIÓN DE LA RELACION DEALER-BUILDING. (Sacar log)------");
        	
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealerBuilding/BuildingBusinessBean ==" + ex);
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealerBuilding/BuildingBusinessBean ==");
        }
	}
	
	
}

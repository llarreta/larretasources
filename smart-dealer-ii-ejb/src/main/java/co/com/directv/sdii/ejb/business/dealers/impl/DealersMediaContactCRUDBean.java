package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DealerMediaContact;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;
import co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAOLocal;

/**
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBeanLocal
 * @see co.com.directv.sdii.persistence.dao.dealers.DealersMediaContactDAO
 */
@Stateless(name="DealersMediaContactCRUDBeanLocal",mappedName="ejb/DealersMediaContactCRUDBeanLocal")
public class DealersMediaContactCRUDBean extends BusinessBase implements DealersMediaContactCRUDBeanLocal {

    @EJB(name="DealersMediaContactDAOLocal",beanInterface=DealersMediaContactDAOLocal.class)
    private DealersMediaContactDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(DealersMediaContactCRUDBean.class);
    
    /**
     * Crea un medio de contacto para un Dealer
     * @param obj - DealersMediaContactVO
     * @throws BusinessException 
     */
    public void createDealerMediaContact(DealerMediaContactVO obj) throws BusinessException {
        log.debug("== Inicio createDealerMediaContact/DealersMediaContactCRUDBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            DealerMediaContact dealerPojo = UtilsBusiness.copyObject(DealerMediaContact.class, obj);
            dao.createDealerMediaContact(dealerPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación createDealerMediaContact/DealersMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createDealerMediaContact/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Elimina un medio de contacto de un Dealer
     * @param obj - DealersMediaContactVO
     * @throws BusinessException 
     */
    public void deleteDealersMediaContact(DealerMediaContactVO obj) throws BusinessException {
        log.debug("== Inicio createDealer/DealersMediaContactCRUDBean ==");
        try {
            if (obj == null) {
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            DealerMediaContact dealerPojo = UtilsBusiness.copyObject(DealerMediaContact.class, obj);
            dao.deleteDealersMediaContact(dealerPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación deleteDealersMediaContact/DealersMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteDealersMediaContact/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene todos los medios de contacto del sistema
     * @return - List<DealersMediaContactVO>
     * @throws BusinessException 
     */
    public List<DealerMediaContactVO> getAllDealersMediaContact() throws BusinessException {
        log.debug("== Inicio createDealer/DealersMediaContactCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getAllDealersMediaContact(), DealerMediaContactVO.class);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealersMediaContact/DealersMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealersMediaContact/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un medio de contacto con el id especificado
     * @param id - Long
     * @return - DealersMediaContactVO
     * @throws BusinessException 
     */
    public DealerMediaContactVO getDealersMediaContactByID(Long id) throws BusinessException {
        log.debug("== Inicio createDealer/DealersMediaContactCRUDBean ==");
        try {
            if (id == null) {
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            DealerMediaContact dmc = dao.getDealersMediaContactByID(id);
            if (dmc == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            DealerMediaContactVO dealerPojo = UtilsBusiness.copyObject(DealerMediaContactVO.class, dao.getDealersMediaContactByID(id));
            return dealerPojo;

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealersMediaContactByID/DealersMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealersMediaContactByID/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Actualiza un medio de contacto especifico
     * @param obj - DealersMediaContactVO
     * @throws BusinessException 
     */
    public void updateDealersMediaContact(DealerMediaContactVO obj) throws BusinessException {
        log.debug("== Inicio createDealer/DealersMediaContactCRUDBean ==");
        try {
            if (obj == null) {
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            DealerMediaContact dealerPojo = UtilsBusiness.copyObject(DealerMediaContact.class, obj);
            dao.updateDealersMediaContact(dealerPojo);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación updateDealersMediaContact/DealersMediaContactCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateDealersMediaContact/CrewStatusCRUDBean ==");
        }
    }
    
    public List<DealerMediaContactVO> fillDealerMediaContacts(Long dealerId) throws BusinessException {
		log.debug("== Inicia fillDealerMediaContacts/TrayWorkOrderManagmentBusinessBean ==");
		try{
			List<DealerMediaContact> pojoList = dao.getDealersMediaContactByDealerId( dealerId );
			List<DealerMediaContactVO> response = null;
			if( pojoList != null && !pojoList.isEmpty() ){
				response = new ArrayList<DealerMediaContactVO>();
				for(DealerMediaContact pojo : pojoList){
					DealerMediaContactVO vo = UtilsBusiness.copyObject(DealerMediaContactVO.class, pojo);
					vo.setDealerId( pojo.getDealer().getId() );
					vo.setDealer( null );
					response.add( vo );
				}				
			}
			return response;
		} catch (Throwable e) {
			log.error("== Error al tratar de ejecutar la tarea fillDealerMediaContacts/TrayWorkOrderManagmentBusinessBean ==", e);
	    	throw this.manageException(e);
		} finally {
			log.debug("== Termina fillDealerMediaContacts/TrayWorkOrderManagmentBusinessBean ==");
		}
	}
	
	
}

package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DealerStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.DealerStatus;
import co.com.directv.sdii.model.vo.DealerStatusVO;
import co.com.directv.sdii.persistence.dao.dealers.DealerStatusDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad DealerStatus
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creacion: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.DealerStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.DealerStatusCRUDBeanLocal
 * 
 */
@Stateless(name="DealerStatusCRUDBeanLocal",mappedName="ejb/DealerStatusCRUDBeanLocal")
public class DealerStatusCRUDBean extends BusinessBase implements DealerStatusCRUDBeanLocal {

    @EJB(name="DealerStatusDAOLocal",beanInterface=DealerStatusDAOLocal.class)
    private DealerStatusDAOLocal dao;
    
    private final static Logger log = Logger.getLogger(DealerStatusCRUDBean.class);

    /**
     * Obtiene los status de los Dealers actuales
     * @return - List<DealerStatusVO>
     * @throws BusinessException 
     */
    public List<DealerStatusVO> getAllDealerStatus() throws BusinessException {
        log.debug("== getAllDealerStatus/DealerStatusCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getAllDealerStatus(), DealerStatusVO.class);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getAllDealerStatus/DealerStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllDealerStatus/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un StatusDealer con el codigo especificado
     * @param code - String
     * @return - DealerStatusVO
     * @throws BusinessException 
     */
    public DealerStatusVO getDealerStatusByCode(String code) throws BusinessException {
        log.debug("== Inicia getDealerStatusByCode/DealerStatusCRUDBean ==");

        if (code == null || code.equals("")) {
            throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }
        try {

            DealerStatus dealerStatus = dao.getDealerStatusByCode(code);
            if (dealerStatus == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerStatusVO.class, dealerStatus);

        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerStatusByCode/DealerStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerStatusByCode/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un StatusDealer con el id especificado
     * @param id - Long
     * @return - DealerStatusVO
     * @throws BusinessException 
     */
    public DealerStatusVO getDealerStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getDealerStatusByID/DealerStatusCRUDBean ==");
        if (id == null) {
        	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        }

        try {
            DealerStatus dStatus = dao.getDealerStatusByID(id);
            if (dStatus == null) {
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            return UtilsBusiness.copyObject(DealerStatusVO.class, dStatus);
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerStatusByID/DealerStatusCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDealerStatusByID/CrewStatusCRUDBean ==");
        }
    }
}

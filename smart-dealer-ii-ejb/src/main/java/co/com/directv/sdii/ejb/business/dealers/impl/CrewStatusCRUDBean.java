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
import co.com.directv.sdii.ejb.business.dealers.CrewStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CrewStatus;
import co.com.directv.sdii.model.vo.CrewStatusVO;
import co.com.directv.sdii.persistence.dao.dealers.CrewStatusDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad CrewStatus
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.CrewStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.CrewStatusCRUDBeanLocal
 * 
 */
@Stateless(name="CrewStatusCRUDBeanLocal",mappedName="ejb/CrewStatusCRUDBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CrewStatusCRUDBean extends BusinessBase implements CrewStatusCRUDBeanLocal {

    @EJB(name="CrewStatusDAOLocal",beanInterface=CrewStatusDAOLocal.class)
    private CrewStatusDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(CrewStatusCRUDBean.class);

    /**
     * Consulta todos los CrewStatus
     * @return List<CrewStatus>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CrewStatusVO> getAllCrewStatus() throws BusinessException {
    	log.debug("== Inicia getAllCrewStatus/CrewStatusCRUDBean ==");
    	try {
            List<CrewStatus> listCS = dao.getAllCrewStatus();
            if(listCS == null){
                return null;
            }
            List<CrewStatusVO> list = UtilsBusiness.convertList(listCS, CrewStatusVO.class);
            return list;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getAllCrewStatus/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un estado de cuadrilla con el codigo especificado
     * @param code - String
     * @return - CrewStatus
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CrewStatusVO getCrewStatusByCode(String code) throws BusinessException {
    	log.debug("== Inicia getCrewStatusByCode/CrewStatusCRUDBean ==");
    	try {
            if (code == null || code.equals("")) {
                log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            CrewStatus pojo = this.dao.getCrewStatusByCode(code);
        	if(pojo == null){
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
        	}
            CrewStatusVO csVO = UtilsBusiness.copyObject(CrewStatusVO.class, pojo);
            return csVO;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCrewStatusByCode/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Obtiene un estado de una cuadrilla con el id especificado
     * @param id - Long
     * @return - CrewStatusVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CrewStatusVO getCrewStatusByID(Long id) throws BusinessException {
    	log.debug("== Inicia getCrewStatusByID/CrewStatusCRUDBean ==");
    	try {
            if (id == null) {
            	log.error(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            CrewStatus pojo = this.dao.getCrewStatusByID(id);
        	if(pojo == null){
        		throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
        	}
            CrewStatusVO csVO = UtilsBusiness.copyObject(CrewStatusVO.class, pojo);
            return csVO;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCrewStatusByID/CrewStatusCRUDBean ==");
        }
    }
}

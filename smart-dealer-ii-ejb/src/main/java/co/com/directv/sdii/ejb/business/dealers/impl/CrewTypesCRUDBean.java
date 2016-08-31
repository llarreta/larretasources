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
import co.com.directv.sdii.ejb.business.dealers.CrewTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.CrewType;
import co.com.directv.sdii.model.vo.CrewTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.CrewTypesDAOLocal;

/**
 *
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad CrewTypes
 * Solo implementa operaciones de consulta
 *
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see co.com.directv.sdii.persistence.dao.dealers.CrewTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.CrewTypesCRUDBeanLocal
 *
 */
@Stateless(name="CrewTypesCRUDBeanLocal",mappedName="ejb/CrewTypesCRUDBeanLocal")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CrewTypesCRUDBean extends BusinessBase implements CrewTypesCRUDBeanLocal {

    @EJB(name="CrewTypesDAOLocal",beanInterface=CrewTypesDAOLocal.class)
    private CrewTypesDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(CrewTypesCRUDBean.class);

    /**
     * Retorna un listado de todos los registros
     * de la Entidad CrewTypes
     *
     * @return List<CrewTypesVO>
     * @throws BusinessException 
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<CrewTypeVO> getAllCrewTypes() throws BusinessException {
        log.debug("== Inicia getAllCrewTypes/CrewTypesCRUDBean ==");
        try {
            List<CrewTypeVO> listVO = UtilsBusiness.convertList(dao.getAllCrewTypes(), CrewTypeVO.class);
            return listVO;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCrewTypes/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Retorna el resultado de la consulta por codigo
     * de la Entidad CrewTypes.
     * @param code - String
     * @return CrewTypesVO
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CrewTypeVO getCrewTypesByCode(String code) throws BusinessException {
        log.debug("== Inicia getCrewTypesByID/CrewTypesCRUDBean ==");
        try {
            if (code == null || code.equals("")) {
                log.error("Parametro code nulo. No se puede obtener CrewTypesByCode");
                throw new IllegalArgumentException("Parametro code nulo. No se puede obtener CrewTypesByCode");
            }
            CrewType pojo = dao.getCrewTypesByCode(code);
            
            if(pojo == null){
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            
            CrewTypeVO vo = UtilsBusiness.copyObject(CrewTypeVO.class, pojo);
            return vo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCrewTypesByCode/CrewStatusCRUDBean ==");
        }
    }

    /**
     * Retorna el resultado de la consulta por ID
     * de la Entidad CrewTypes.
     * @param id
     * @return CrewTypesVo
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CrewTypeVO getCrewTypesByID(Long id) throws BusinessException {
        log.debug("== Inicia getCrewTypesByID/CrewTypesCRUDBean ==");
        try {
            if (id == null) {
                log.error("Parametro id nulo. No se puede obtener CrewTypesByID");
                throw new IllegalArgumentException("Parametro id nulo. No se puede obtener CrewTypesByID");
            }
            
            CrewType pojo = dao.getCrewTypesByID(id);
            if(pojo == null){
            	throw new BusinessException(ErrorBusinessMessages.ENTITY_NOT_FOUND.getCode(),ErrorBusinessMessages.ENTITY_NOT_FOUND.getMessage());
            }
            CrewTypeVO vo = UtilsBusiness.copyObject(CrewTypeVO.class, pojo);
            return vo;
        } catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCrewTypesByID/CrewStatusCRUDBean ==");
        }
    }
}

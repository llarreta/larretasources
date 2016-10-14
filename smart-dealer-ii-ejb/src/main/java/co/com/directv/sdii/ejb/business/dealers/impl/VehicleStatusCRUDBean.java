package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.VehicleStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.VehicleStatus;
import co.com.directv.sdii.model.vo.VehicleStatusVO;
import co.com.directv.sdii.persistence.dao.dealers.VehicleStatusDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad VehicleStatus
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.VehicleStatuss.VehicleStatusDAOLocal
 * @see co.com.directv.sdii.ejb.business.VehicleStatuss.VehicleStatusCRUDBeanLocal
 * 
 */
@Stateless(name="VehicleStatusCRUDBeanLocal",mappedName="ejb/VehicleStatusCRUDBeanLocal")
public class VehicleStatusCRUDBean extends BusinessBase implements VehicleStatusCRUDBeanLocal {

    @EJB(name="VehicleStatusDAOLocal",beanInterface=VehicleStatusDAOLocal.class)
    private VehicleStatusDAOLocal dao;
    
    private final static Logger log = UtilsBusiness.getLog4J(VehicleStatusCRUDBean.class);

    /**
     * Retorna un listado de todos los registros
     * de la Entidad VehicleStatus
     *
     * @return List<VehicleStatusVO>
     * @throws BusinessException
     */
    public List<VehicleStatusVO> getAllVehicleStatus() throws BusinessException {
        log.debug("== Inicia getAllVehicleStatus/VehicleStatusCRUDBean ==");
        try {
            List<VehicleStatusVO> listVO = UtilsBusiness.convertList(dao.getAllVehicleStatus(), VehicleStatusVO.class);
            return listVO;
        }  catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehicleStatus/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehicleStatus/VehicleStatusCRUDBean ==");
        }
    }

    /**
     * Reorna el resultado de la consulta por codigo
     * de la Entidad VehicleStatus.
     * @param code
     * @return VehicleStatusVO
     * @throws BusinessException 
     */
    public VehicleStatusVO getVehicleStatusByCode(String code) throws BusinessException {
        log.debug("== Inicia getVehicleStatusByID/VehicleStatusCRUDBean ==");
        try {
            if (code == null || code.equals("")) {
                //throw new BusinessException("Parametro code vacio o nulo");
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            VehicleStatus vStatus = dao.getVehicleStatusByCode(code);
            if (vStatus == null) {
                return null;
            }
            VehicleStatusVO VehicleStatusVo = UtilsBusiness.copyObject(VehicleStatusVO.class, vStatus);
            return VehicleStatusVo;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehicleStatusByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleStatusByID/VehicleStatusCRUDBean ==");
        }
    }

    /**
     * Retorna el resultado de la consulta por ID
     * de la Entidad VehicleStatus.
     * @param id
     * @return VehicleStatusVo
     * @throws BusinessException 
     */
    public VehicleStatusVO getVehicleStatusByID(Long id) throws BusinessException {
        log.debug("== Inicia getVehicleStatusByID/VehicleStatusCRUDBean ==");
        try {

            if (id == null) {
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                //throw new BusinessException("Parametro id nulo");
            }

            VehicleStatus vStatus = dao.getVehicleStatusByID(id);
            if (vStatus == null) {
                return null;
            }
            VehicleStatusVO VehicleStatusVo = UtilsBusiness.copyObject(VehicleStatusVO.class, vStatus);
            return VehicleStatusVo;
        }  catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehicleStatusByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleStatusByID/VehicleStatusCRUDBean ==");
        }
    }
}

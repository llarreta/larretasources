package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.VehicleTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.VehicleType;
import co.com.directv.sdii.model.vo.VehicleTypeVO;
import co.com.directv.sdii.persistence.dao.dealers.VehicleTypesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad VehicleTypes
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.VehicleTypess.VehicleTypesDAOLocal
 * @see co.com.directv.sdii.ejb.business.VehicleTypess.VehicleTypesCRUDBeanLocal
 * 
 */
@Stateless(name="VehicleTypesCRUDBeanLocal",mappedName="ejb/VehicleTypesCRUDBeanLocal")
public class VehicleTypesCRUDBean extends BusinessBase implements VehicleTypesCRUDBeanLocal {

    @EJB(name="VehicleTypesDAOLocal",beanInterface=VehicleTypesDAOLocal.class)
    private VehicleTypesDAOLocal dao;

    private final static Logger log = UtilsBusiness.getLog4J(VehicleTypesCRUDBean.class);

    /**
     *
     * Metodo: Retorna un listado de todos los registros
     * de la Entidad VehicleTypes
     *
     * @return List<VehicleTypesVO>
     * @throws BusinessException 
     * @author jalopez
     */
    public List<VehicleTypeVO> getAllVehicleTypes() throws BusinessException {
        log.debug("== Inicia getAllVehicleTypes/VehicleTypesCRUDBean ==");
        try {
            return UtilsBusiness.convertList(dao.getAllVehicleTypes(), VehicleTypeVO.class);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllVehicleTypes/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllVehicleTypes/VehicleTypesCRUDBean ==");
        }
    }

    /**
     *
     * Metodo: Reorna el resultado de la consulta por codigo
     * de la Entidad VehicleTypes.
     * @param code
     * @return VehicleTypesVO
     * @throws BusinessException 
     */
    public VehicleTypeVO getVehicleTypesByCode(String code) throws BusinessException {
        log.debug("== Inicia getVehicleTypesByCode/VehicleTypesCRUDBean ==");
        try {
            if (code == null || code.equals("")) {
               // throw new BusinessException("Parametro code vacio o nulo");
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            VehicleType vTypes = dao.getVehicleTypesByCode(code);
            if (vTypes == null) {
                return null;
            }
            VehicleTypeVO vehicleTypesVo = UtilsBusiness.copyObject(VehicleTypeVO.class, vTypes);
            return vehicleTypesVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehicleTypesByCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleTypesByID/VehicleTypesCRUDBean ==");
        }
    }

    /**
     *
     * Metodo: Reorna el resultado de la consulta por ID
     * de la Entidad VehicleTypes.
     * @param id
     * @return VehicleTypesVo
     * @throws BusinessException 
     * @author jalopez
     */
    public VehicleTypeVO getVehicleTypesByID(Long id) throws BusinessException {
        log.debug("== Inicia getVehicleTypesByID/VehicleTypesCRUDBean ==");
        try {
            if (id == null) {
                //throw new BusinessException("Parametro id nulo");
            	throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            VehicleType vTypes = dao.getVehicleTypesByID(id);
            if (vTypes == null) {
                return null;
            }
            VehicleTypeVO vehicleTypes = UtilsBusiness.copyObject(VehicleTypeVO.class, vTypes);
            return vehicleTypes;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getVehicleTypesByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getVehicleTypesByID/VehicleTypesCRUDBean ==");
        }
    }
}

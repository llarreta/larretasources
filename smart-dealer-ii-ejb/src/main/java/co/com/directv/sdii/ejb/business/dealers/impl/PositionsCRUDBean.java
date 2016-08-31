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
import co.com.directv.sdii.ejb.business.dealers.PositionsCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.Employee;
import co.com.directv.sdii.model.pojo.Position;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.PositionVO;
import co.com.directv.sdii.persistence.dao.dealers.EmployeeDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.PositionsDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Positions
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail3</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.PositionsDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.PositionsCRUDBeanLocal
 */
@Stateless(name="PositionsCRUDBeanLocal",mappedName="ejb/PositionsCRUDBeanLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PositionsCRUDBean extends BusinessBase implements PositionsCRUDBeanLocal {

    @EJB(name="PositionsDAOLocal",beanInterface=PositionsDAOLocal.class)
    private PositionsDAOLocal daoPositionBean;
    
    @EJB(name="EmployeeDAOLocal",beanInterface=EmployeeDAOLocal.class)
    private EmployeeDAOLocal daoEmployeeBean;
    
    @EJB(name="UserDAOLocal",beanInterface=UserDAOLocal.class)
    private UserDAOLocal daoUserBean;
    
    private final static Logger log = UtilsBusiness.getLog4J(PositionsCRUDBean.class);

    /**
     * Crea un nuevo cargo en el sistema
     * @param obj - Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createPositions(PositionVO obj) throws BusinessException {
        log.debug("== Inicio createPositions/PositionsCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear Position");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if (daoPositionBean.getPositionsByNameAndDealerId(obj.getPositionName(), obj.getDealerId()) != null) {
                log.debug("No se pudo crear el cargo. Ya existe un cargo con el nombre especificado");
                throw new BusinessException(ErrorBusinessMessages.POSITION_BY_NAME_AND_DEALER_ALREADY_EXIST.getCode(), ErrorBusinessMessages.POSITION_BY_NAME_AND_DEALER_ALREADY_EXIST.getMessage());
            }

            if (daoPositionBean.getPositionsByPositionCodeAndDealerId(obj.getPositionCode(), obj.getDealerId()) != null) {
                log.debug("No se pudo crear el cargo. Ya existe un cargo con el Position Code especificado");
                throw new BusinessException(ErrorBusinessMessages.POSITION_BY_CODE_AND_DEALER_ALREADY_EXIST.getCode(), ErrorBusinessMessages.POSITION_BY_CODE_AND_DEALER_ALREADY_EXIST.getMessage());
            }

            this.daoPositionBean.createPositions(UtilsBusiness.copyObject(Position.class, obj));
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion createPositions/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createPositions/PositionsCRUDBean ==");
        }
    }

    /**
     * Elimina un cargo del sistema
     * @param obj - Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deletePositions(PositionVO obj) throws BusinessException {
        log.debug("== Inicio deletePositions/PositionsCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede eliminar Position");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            Position pos = UtilsBusiness.copyObject(Position.class, obj);
            
            List<Employee> employeesByPosition = daoEmployeeBean.getEmployeesByPositionId(obj.getId());
            if(! employeesByPosition.isEmpty()){
            	String message = "No se puede eliminar el cargo con id: " + obj.getId() + " porque existen " + employeesByPosition.size() +  " empleados que lo ocupan, primero debe cambiar el cargo a todos los empleados";
            	log.error(message);
            	throw new BusinessException(ErrorBusinessMessages.POSITION_HAS_RELATED_DATA.getCode(), ErrorBusinessMessages.POSITION_HAS_RELATED_DATA.getMessage() + message);
            }
            
            User userSample = new User();
            userSample.setPosition(pos);
            List<User> usersByPosition = daoUserBean.getUserBySample(userSample);
            if(! usersByPosition.isEmpty()){
            	String message = "No se puede eliminar el cargo con id: " + obj.getId() + " porque existen " + usersByPosition.size() +  " usuarios que lo ocupan, primero debe cambiar el cargo a todos los usuarios";
            	log.error(message);
            	throw new BusinessException(ErrorBusinessMessages.POSITION_HAS_RELATED_DATA.getCode(), ErrorBusinessMessages.POSITION_HAS_RELATED_DATA.getMessage() + message);
            }
            
            this.daoPositionBean.deletePositions(pos);
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion deletePositions/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deletePositions/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene un cargo por el id especificado
     * @param id - Long
     * @return Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PositionVO getPositionsByID(Long id) throws BusinessException {
        log.debug("== Inicio getPositionsByID/PositionsCRUDBean ==");
        try {
            if (id == null) {
                log.debug("Parametro id nulo. No se puede consultar PositionByID");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            Position pos = daoPositionBean.getPositionsByID(id);
            if (pos == null) {
                return null;
            }

            PositionVO pVO = UtilsBusiness.copyObject(PositionVO.class, pos);
            fillRelationshipData(pVO);
            return pVO;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByID/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getPositionsByID/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo
     * @param code - String
     * @return - Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PositionVO getPositionsByPositionCode(String code) throws BusinessException {
        log.debug("== Inicio getPositionsByPositionCode/PositionsCRUDBean ==");
        try {
            if (code == null || code.equals("")) {
                log.debug("Parametro code nulo. No se puede consultar PositionByCode");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            Position pos = daoPositionBean.getPositionsByPositionCode(code);
            if (pos == null) {
                return null;
            }

            PositionVO pVO = UtilsBusiness.copyObject(PositionVO.class, pos);
            fillRelationshipData(pVO);
            return pVO;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByPositionCode/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByPositionCode/PositionsCRUDBean ==");
        }
    }

    /**
     * Actualiza un cargo especifico
     * @param obj - Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updatePositions(PositionVO obj) throws BusinessException {
        log.debug("== Inicio updatePositions/PositionsCRUDBean ==");
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede actualizar Position");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validación de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }

            this.daoPositionBean.updatePositions(UtilsBusiness.copyObject(Position.class, obj));

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion updatePositions/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updatePositions/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene todos los cargos existentes en el sistema
     * @return - List<Positions>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PositionVO> getAllPositions() throws BusinessException {
        log.debug("== Inicio getAllPositions/PositionsCRUDBean ==");
        try {
            List<PositionVO> listPVo = UtilsBusiness.convertList(daoPositionBean.getAllPositions(), PositionVO.class);
            if (listPVo == null) {
                return null;
            }
            for (PositionVO positionVO : listPVo) {
                fillRelationshipData(positionVO);
            }
            return listPVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPositions/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPositions/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene un listado de Cargos de un Dealer especifico
     * @param dealerId - Long
     * @return - List<Positions>
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<PositionVO> getPositionsByDealerId(Long dealerId) throws BusinessException {
        log.debug("== Inicio getPositionsByDealerId/PositionsCRUDBean ==");
        try {
            List<PositionVO> listPVo = UtilsBusiness.convertList(daoPositionBean.getPositionsByDealerId(dealerId), PositionVO.class);
              if (listPVo == null) {
                return null;
            }
            for (PositionVO positionVO : listPVo) {
                fillRelationshipData(positionVO);
            }
            return listPVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByDealerId/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene un cargo por el nombre especificado y el dealer id
     * @param id - Long
     * @Param positionName - String
     * @return Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PositionVO getPositionsByNameAndDealerId(String positionName, Long id) throws BusinessException {
        log.debug("== Inicio getPositionsByNameAndDealerId/PositionsCRUDBean ==");
        try {
            PositionVO pVo = UtilsBusiness.copyObject(PositionVO.class, this.daoPositionBean.getPositionsByNameAndDealerId(positionName, id));
              if (pVo == null) {
                return null;
            }
            fillRelationshipData(pVo);
            return pVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByNameAndDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByNameAndDealerId/PositionsCRUDBean ==");
        }
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo y al dealer especificado
     * @param code - String
     * @param id - Long
     * @return - Positions
     * @throws BusinessException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public PositionVO getPositionsByPositionCodeAndDealerId(String code, Long id) throws BusinessException {
        log.debug("== Inicio getPositionsByPositionCodeAndDealerId/PositionsCRUDBean ==");
        try {
            PositionVO pVo = UtilsBusiness.copyObject(PositionVO.class, this.daoPositionBean.getPositionsByPositionCodeAndDealerId(code, id));
            if(pVo ==null)
                return null;
            fillRelationshipData(pVo);
            return null;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByPositionCodeAndDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByPositionCodeAndDealerId/PositionsCRUDBean ==");
        }
    }

    private void fillRelationshipData(PositionVO pVO) {
         log.debug("== Inicio fillRelationshipData/PositionsCRUDBean ==");
        pVO.setDealerId(pVO.getDealer().getId());
        pVO.setDealer(null);
        log.debug("== Termina fillRelationshipData/PositionsCRUDBean ==");
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PositionVO> getPositionsByName(String positionName)
			throws BusinessException {
		log.debug("== Inicio getPositionsByName/PositionsCRUDBean ==");
        try {
        	List<Position> positionsPojo = daoPositionBean.getPositionsByName(positionName);
            List<PositionVO> listPVo = UtilsBusiness.convertList(positionsPojo, PositionVO.class);
            if (listPVo == null) {
                return null;
            }
            for (PositionVO positionVO : listPVo) {
                fillRelationshipData(positionVO);
            }
            return listPVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByName/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByName/PositionsCRUDBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PositionVO> getPositionsByCodeAndNameAndDealerId(String code,
			String name, Long dealerId) throws BusinessException {
		
    	log.debug("== Inicio getPositionsByCodeAndNameAndDealerId/PositionsCRUDBean ==");
        try {
        	List<Position> positionsPojo = daoPositionBean.getPositionsByCodeAndNameAndDealerId(code, name, dealerId);
            List<PositionVO> listPVo = UtilsBusiness.convertList(positionsPojo, PositionVO.class);
            if (listPVo == null) {
                return null;
            }
            for (PositionVO positionVO : listPVo) {
                fillRelationshipData(positionVO);
            }
            return listPVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getPositionsByCodeAndNameAndDealerId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getPositionsByCodeAndNameAndDealerId/PositionsCRUDBean ==");
        }
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.dealers.PositionsCRUDBeanLocal#getAllPositionsByCountryId(java.lang.Long)
     */
	public List<PositionVO> getAllPositionsByCountryId(Long countryId)
			throws BusinessException {
		log.debug("== Inicio getAllPositionsByCountryId/PositionsCRUDBean ==");
        try {
            List<PositionVO> listPVo = UtilsBusiness.convertList(daoPositionBean.getAllPositionsByCountryId(countryId), PositionVO.class);
            if (listPVo == null) {
                return null;
            }
            for (PositionVO positionVO : listPVo) {
                fillRelationshipData(positionVO);
            }
            return listPVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operacion getAllPositionsByCountryId/MediaContactTypesCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllPositionsByCountryId/PositionsCRUDBean ==");
        }
	}
}

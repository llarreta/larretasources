package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.EmployeesCrewCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeCrew;
import co.com.directv.sdii.model.pojo.WoAssignment;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;
import co.com.directv.sdii.persistence.dao.config.WoAssignmentDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeesCrew
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.dealers.EmployeesCrewDAOLocal
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeesCrewCRUDBeanLocal
 * 
 */
@Stateless(name="EmployeesCrewCRUDBeanLocal",mappedName="ejb/EmployeesCrewCRUDBeanLocal")
public class EmployeesCrewCRUDBean extends BusinessBase implements EmployeesCrewCRUDBeanLocal {

    @EJB(name="EmployeesCrewDAOLocal",beanInterface=EmployeesCrewDAOLocal.class)
    private EmployeesCrewDAOLocal dao;
    @EJB(name="WoAssignmentDAOLocal",beanInterface=WoAssignmentDAOLocal.class)
    private WoAssignmentDAOLocal woAssignmentDAOLocal;
    
    private final static Logger log = UtilsBusiness.getLog4J(EmployeesCrewCRUDBean.class);

    /**
     * Crea una relacion entre empleados y cuadrillas
     * @param obj - EmployeeCrewVO
     * @throws BusinessException
     */
    public void createEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede crear EmployeeCrew");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),"Parametro obj nulo. No se puede crear CrewEmployee");
            }
            dao.createEmployeesCrew(UtilsBusiness.copyObject(EmployeeCrew.class, obj));

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación createEmployeesCrew/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createEmployeesCrew/EmployeesCrewCRUDBean ==");
        }
    }

    /**
     * Elimina una relación entre cuadrillas y empleados
     * @param obj - EmployeeCrewVO
     * @throws BusinessException 
     */
    public void deleteEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede eliminar EmployeeCrew");
                throw new BusinessException("Parametro obj nulo. No se puede eliminar CrewEmployee");
            }
            dao.deleteEmployeesCrew(UtilsBusiness.copyObject(EmployeeCrew.class, obj));

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación deleteEmployeesCrew/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteEmployeesCrew/EmployeesCrewCRUDBean ==");
        }
    }

    /**
     * Obtiene todas las relaciones entre cuadrillas y empleados existentes
     * @return - List<EmployeeCrewVO>
     * @throws BusinessException 
     */
    public List<EmployeeCrewVO> getAllEmployeesCrew() throws BusinessException {
        try {
            List<EmployeeCrewVO> crewEmployeeList = UtilsBusiness.convertList(dao.getAllEmployeesCrew(), EmployeeCrewVO.class);
            return crewEmployeeList;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getAllEmployeesCrew/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllEmployeesCrew/EmployeesCrewCRUDBean ==");
        }
    }

    /**
     * Obtiene un listado de las relaciones de los empleados
     * de una cuadrilla especifica
     * @param id - Long
     * @return - List<EmployeeCrewVO>
     * @throws BusinessException 
     */
    public List<EmployeeCrewVO> getEmployeesCrewByCrewID(Long id) throws BusinessException {
        try {
            List<EmployeeCrewVO> crewEmployeeList = UtilsBusiness.convertList(dao.getEmployeesCrewByCrewID(id), EmployeeCrewVO.class);
            return crewEmployeeList;

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeesCrewByCrewID/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByCrewID/EmployeesCrewCRUDBean ==");
        }
    }

    /**
     * Actualiza una relacion entre cuadrillas y empleados
     * @param obj - EmployeeCrewVO
     * @throws BusinessException
     */
    public void updateEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
        try {
            if (obj == null) {
                log.debug("Parametro obj nulo. No se puede actualizar EmployeeCrew");
                throw new BusinessException("Parametro obj nulo. No se puede actualizar CrewEmployee");
            }
            dao.updateEmployeesCrew(UtilsBusiness.copyObject(EmployeeCrew.class, obj));

        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación updateEmployeesCrew/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeesCrew/EmployeesCrewCRUDBean ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un listado de empleados por
     * cuadrilla filtrando por el condigo del dealer.
     * @param Long dealerId 
     * @return List<EmployeeCrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jalopez
     */    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerID(Long dealerId) throws BusinessException {
    	try {
            if (dealerId == null) {                
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            List<EmployeeCrewVO> resultList = new ArrayList<EmployeeCrewVO>();
            WoAssignment woAssignment=null;
			Object amount = null;
            
            List<EmployeeCrew> res = dao.getEmployeesCrewByDealerID(dealerId, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity());
            List<EmployeeCrewVO> crewEmployeeList = UtilsBusiness.convertList( res, EmployeeCrewVO.class);
            
            for (EmployeeCrewVO employeeCrewVO : crewEmployeeList) {
            	woAssignment = new WoAssignment();
				woAssignment.setDealerId(dealerId);
				woAssignment.setCrewId(employeeCrewVO.getCrew().getId());
				amount = woAssignmentDAOLocal.getAmountWoAssigment(woAssignment);
				employeeCrewVO.setAmountWoAssigment((Long) amount);
				resultList.add(employeeCrewVO);
			}            
            return resultList;
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeesCrewByDealerID/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina updateEmployeesCrew/EmployeesCrewCRUDBean ==");
        }
    }
    
    /**
     * 
     * Metodo: Retorna un listado de empleados por
     * cuadrilla filtrando por el codigo del dealer y tipo de cuadrilla
     * @param Long dealerId
     * @param Long crewType 
     * @return List<EmployeeCrewVO>
     * @throws BusinessException
     * @author jnova
     */    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIDAndCrewType(Long dealerId , Long crewType) throws BusinessException {
    	log.debug("== Inicio getEmployeesCrewByDealerIDAndCrewType/EmployeesCrewCRUDBean ==");
    	try {
            if (dealerId == null || crewType == null) {                
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            List<EmployeeCrewVO> resultList = new ArrayList<EmployeeCrewVO>();
            WoAssignment woAssignment=null;
			Object amount = null;
            
            List<EmployeeCrew> res = dao.getEmployeesCrewByDealerIDAndCrewType(dealerId, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(),crewType);
            List<EmployeeCrewVO> crewEmployeeList = UtilsBusiness.convertList( res, EmployeeCrewVO.class);
            
            for (EmployeeCrewVO employeeCrewVO : crewEmployeeList) {
            	woAssignment = new WoAssignment();
				woAssignment.setDealerId(dealerId);
				woAssignment.setCrewId(employeeCrewVO.getCrew().getId());
				amount = woAssignmentDAOLocal.getAmountWoAssigment(woAssignment);
				employeeCrewVO.setAmountWoAssigment((Long) amount);
				resultList.add(employeeCrewVO);
			}            
            return resultList;
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getEmployeesCrewByDealerIDAndCrewType/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByDealerIDAndCrewType/EmployeesCrewCRUDBean ==");
        }
    }

    /**
     * 
     * Metodo: Retorna un listado de Empleados por cuadrilla
     * filtrando por nombre del empleado, dealer, tipo de cuadrilla
     * y estado de la cuadrilla
     * @param Long dealerId
     * @param String isResponsible
     * @param Long crewType
     * @param Long responsibleName
     * @return List<EmployeeCrewVO>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author Joan Lopez
     */
	public List<EmployeeCrewVO> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible ( Long dealerId, Long crewType, String responsibleName) throws BusinessException {
		log.debug("== Inicio getEmployeesCrewByDealerIdAndCrewTypeAndResponsible/EmployeesCrewCRUDBean ==");
    	try {
            if (dealerId == null || crewType == null) {                
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            if ( responsibleName == null) {                
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            if ( responsibleName.equals("")) {                
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            
            List<EmployeeCrewVO> resultList = new ArrayList<EmployeeCrewVO>();
            WoAssignment woAssignment=null;
			Object amount = null;
			
			List<EmployeeCrew> res = dao.getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(dealerId, CodesBusinessEntityEnum.BOOLEAN_TRUE.getCodeEntity(), crewType, responsibleName);
			List<EmployeeCrewVO> crewEmployeeList = UtilsBusiness.convertList( res, EmployeeCrewVO.class);
			
			 for (EmployeeCrewVO employeeCrewVO : crewEmployeeList) {
            	woAssignment = new WoAssignment();
				woAssignment.setDealerId(dealerId);
				woAssignment.setCrewId(employeeCrewVO.getCrew().getId());
				amount = woAssignmentDAOLocal.getAmountWoAssigment(woAssignment);
				employeeCrewVO.setAmountWoAssigment((Long) amount);
				resultList.add(employeeCrewVO);
			}    
			
            return resultList;
    	} catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaci�n getEmployeesCrewByDealerIdAndCrewTypeAndResponsible/EmployeesCrewCRUDBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getEmployeesCrewByDealerIdAndCrewTypeAndResponsible/EmployeesCrewCRUDBean ==");
        }
	}
}

package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeesCrewCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.dealers.EmployeesCrewFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad EmployeesCrew 
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeesCrewCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeesCrewFacadeBeanLocal
 */
@Stateless(name="EmployeesCrewFacadeBeanLocal",mappedName="ejb/EmployeesCrewFacadeBeanLocal")
public class EmployeesCrewFacadeBean implements EmployeesCrewFacadeBeanLocal {

    @EJB(name="EmployeesCrewCRUDBeanLocal",beanInterface=EmployeesCrewCRUDBeanLocal.class)
    private EmployeesCrewCRUDBeanLocal business;

    /**
     * Crea una relacion entre empleados y cuadrillas
     * @param obj - EmployeeCrewVO
     * @throws BusinessException
     */
    public void createEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
        business.createEmployeesCrew(obj);

    }

    /**
     * Elimina una relación entre cuadrillas y empleados
     * @param obj - EmployeeCrewVO
     * @throws BusinessException
     */
    public void deleteEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
        business.deleteEmployeesCrew(obj);
    }

    /**
     * Obtiene todas las relaciones entre cuadrillas y empleados existentes
     * @return - List<EmployeeCrewVO>
     * @throws BusinessException
     */
    public List<EmployeeCrewVO> getAllEmployeesCrew() throws BusinessException {
        return business.getAllEmployeesCrew();
    }

    /**
     * Obtiene un listado de las relaciones de los empleados
     * de una cuadrilla especifica
     * @param id - Long
     * @return - List<EmployeeCrewVO>
     * @throws BusinessException
     */
    public List<EmployeeCrewVO> getEmployeesCrewByCrewID(Long id) throws BusinessException {
        return business.getEmployeesCrewByCrewID(id);
    }

     /**
     * Actualiza una relacion entre cuadrillas y empleados
     * @param obj - EmployeeCrewVO
     * @throws BusinessException
     */
    public void updateEmployeesCrew(EmployeeCrewVO obj) throws BusinessException {
       business.updateEmployeesCrew(obj);
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
    public List<EmployeeCrewVO> getEmployeesCrewByDealerID(Long dealerId) throws BusinessException{
    	return business.getEmployeesCrewByDealerID(dealerId);
    }
    
    /**
     * 
     * Metodo: Retorna un listado de empleados por
     * cuadrilla filtrando por el condigo del dealer y el tipo de cuadrilla
     * @param Long dealerId
     * @param Long crewType tipo de cuadrilla: dinamica o estatica     
     * @return List<EmployeeCrewVO>
     * @throws BusinessException
     * @author jnova
     */    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIDAndCrewType(Long dealerId , Long crewType) throws BusinessException{
    	return business.getEmployeesCrewByDealerIDAndCrewType(dealerId,crewType);
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
     * @return List<EmployeeCrew>
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author Joan Lopez
     */
	public List<EmployeeCrewVO> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(Long dealerId, Long crewType,	String responsibleName) throws BusinessException {
		return business.getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(dealerId, crewType, responsibleName);
	}
}

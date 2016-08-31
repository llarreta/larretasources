package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de EmployeesCrew
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeesCrewFacadeBeanLocal {

    public void createEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public void updateEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public void deleteEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public List<EmployeeCrewVO> getAllEmployeesCrew() throws BusinessException;

    public List<EmployeeCrewVO> getEmployeesCrewByCrewID(Long id) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerID(Long dealerId) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIDAndCrewType(Long dealerId , Long crewType) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible (	Long dealerId, Long crewType, String responsibleName) throws BusinessException;
}

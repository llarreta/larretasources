package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.EmployeeCrewVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad EmployeesCrew
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeesCrewCRUDBeanLocal {

    public void createEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public void updateEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public void deleteEmployeesCrew(EmployeeCrewVO obj) throws BusinessException;

    public List<EmployeeCrewVO> getAllEmployeesCrew() throws BusinessException;

    public List<EmployeeCrewVO> getEmployeesCrewByCrewID(Long id) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerID(Long dealerId) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIDAndCrewType(Long dealerId , Long crewType) throws BusinessException;
    
    public List<EmployeeCrewVO> getEmployeesCrewByDealerIdAndCrewTypeAndResponsible(Long dealerId, Long crewType, String responsibleName) throws BusinessException;
}

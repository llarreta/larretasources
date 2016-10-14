package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeMediaContactCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeMediaContactFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeMediaContactVO;

/**
 * 
 * Implementacion de la Session Facade de las operaciones a realizar 
 * para el modulo de EmployeeMediaContact 
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeMediaContactCRUDBean
 */
@Stateless(name="EmployeeMediaContactFacadeBeanLocal",mappedName="ejb/EmployeeMediaContactFacadeBeanLocal")
public class EmployeeMediaContactFacadeBean implements EmployeeMediaContactFacadeBeanLocal {

    @EJB(name="EmployeeMediaContactCRUDBeanLocal",beanInterface=EmployeeMediaContactCRUDBeanLocal.class)
    private EmployeeMediaContactCRUDBeanLocal business;

    public void createEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        business.createEmployeeMediaContact(obj);
    }

    public void deleteEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        business.deleteEmployeeMediaContact(obj);
    }

    public List<EmployeeMediaContactVO> getAllEmployeeMediaContact() throws BusinessException {
        return business.getAllEmployeeMediaContact();
    }

    public EmployeeMediaContactVO getEmployeeMediaContactByID(Long id) throws BusinessException {
        return business.getEmployeeMediaContactByID(id);
    }

    public void updateEmployeeMediaContact(EmployeeMediaContactVO obj) throws BusinessException {
        business.updateEmployeeMediaContact(obj);
    }
}

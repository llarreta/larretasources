package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.EmployeeStatusVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad EmployeeStatus.
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeStatusCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal
 */
@Stateless(name="EmployeeStatusFacadeBeanLocal",mappedName="ejb/EmployeeStatusFacadeBeanLocal")
public class EmployeeStatusFacadeBean implements EmployeeStatusFacadeBeanLocal {

    @EJB(name="EmployeeStatusCRUDBeanLocal",beanInterface=EmployeeStatusCRUDBeanLocal.class)
    private EmployeeStatusCRUDBeanLocal business;

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal#getAllEmployeeStatus()
     */
    public List<EmployeeStatusVO> getAllEmployeeStatus() throws BusinessException {
        return business.getAllEmployeeStatus();
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.EmployeeStatusFacadeBeanLocal#getEmployeeStatusById(java.lang.Long)
     */
    public EmployeeStatusVO getEmployeeStatusById(Long id) throws BusinessException {
        return business.getEmployeeStatusById(id);
    }
}

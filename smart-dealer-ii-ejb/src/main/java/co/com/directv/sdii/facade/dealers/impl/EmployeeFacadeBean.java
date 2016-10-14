package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal;
import co.com.directv.sdii.model.pojo.collection.EmployeePaginationResponse;
import co.com.directv.sdii.model.pojo.collection.RequestCollectionInfo;
import co.com.directv.sdii.model.vo.EmployeeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Employee 
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.EmployeeCRUDBean
 */
@Stateless(name="EmployeeFacadeBeanLocal",mappedName="ejb/EmployeeFacadeBeanLocal")
public class EmployeeFacadeBean implements EmployeeFacadeBeanLocal {

    @EJB(name="EmployeeCRUDBeanLocal",beanInterface=EmployeeCRUDBeanLocal.class)
    private EmployeeCRUDBeanLocal businessCrudEmployee;

    /**
     * Crea un empleado en el sistema
     * @param obj - EmployeeVO
     * @throws BusinessException
     */
    public void createEmployee(EmployeeVO obj) throws BusinessException {
        businessCrudEmployee.createEmployee(obj);
    }

    /**
     * Elimina un empleado del sistema
     * @param obj - EmployeeVO
     * @throws BusinessException 
     */
    public void deleteEmployee(EmployeeVO obj) throws BusinessException {
        businessCrudEmployee.deleteEmployee(obj);

    }

    /**
     * Obtiene todos los empleados existentes en el sistema
     * @return - List<EmployeeVO>
     * @throws BusinessException 
     * @author jalopez
     */
    public List<EmployeeVO> getAllEmployee() throws BusinessException {
        return businessCrudEmployee.getAllEmployee();
    }

    /**
     * Obtiene un empleado con el id especificado
     * @param id - Long
     * @return - EmployeeVO
     * @throws BusinessException 
     */
    public EmployeeVO getEmployeeByID(Long id) throws BusinessException {
        return businessCrudEmployee.getEmployeeByID(id);
    }

    /**
     * Actualiza un empleado
     * @param obj - EmployeeVO
     * @throws BusinessException 
     */
    public void updateEmployee(EmployeeVO obj) throws BusinessException {
        businessCrudEmployee.updateEmployee(obj);

    }
    
    /**
     * Obtiene un listado de empleados pertenecientes a un dealer especifico
     * @param id - Long
     * @return - List<EmployeeVO>
     */
    public List<EmployeeVO> getEmployeesByDealerId(Long id) throws BusinessException {
        return businessCrudEmployee.getEmployeesByDealerId(id);
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeesActiveByDealerId(java.lang.Long)
     */
    @Override
	public List<EmployeeVO> getEmployeesActiveByDealerId(Long id)
			throws BusinessException {
    	 return businessCrudEmployee.getEmployeesActiveByDealerId(id);
	}	

    /**
     * Obtiene un empleado por tipo y numero de documento
     */
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber) throws BusinessException{
        return businessCrudEmployee.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber);
    }
    
    /**
     * Obtiene un empleado por tipo y numero de documento,
     * filtrar por el dealer si es diferente de null
     */
    public EmployeeVO getEmployeeByDocTypeAndDocNum(Long idDocType, String documentNumber,Long dealerId) throws BusinessException{
        return businessCrudEmployee.getEmployeeByDocTypeAndDocNum(idDocType, documentNumber,dealerId);
    }
    
    /**
     * Retira un empleado del sistema
     */
	public void retireEmployee(EmployeeVO employeeVO, String description) throws BusinessException {
		businessCrudEmployee.retireEmployee(employeeVO, description);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeByDocumentNumber(java.lang.String)
	 */
	@Override
	public List<EmployeeVO> getEmployeeByDocumentNumber(String documentNumber)
			throws BusinessException {
		return businessCrudEmployee.getEmployeeByDocumentNumber(documentNumber);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeByFirstName(java.lang.String)
	 */
	@Override
	public List<EmployeeVO> getEmployeeByFirstName(String firstName)
			throws BusinessException {
		return businessCrudEmployee.getEmployeeByFirstName(firstName);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeByLastName(java.lang.String)
	 */
	@Override
	public List<EmployeeVO> getEmployeeByLastName(String lastName)
			throws BusinessException {
		return businessCrudEmployee.getEmployeeByLastName(lastName);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeByDocumentTypeId(java.lang.Long)
	 */
	@Override
	public List<EmployeeVO> getEmployeeByDocumentTypeId(Long typeId)throws BusinessException {
		return businessCrudEmployee.getEmployeeByDocumentTypeId(typeId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmployeeVO> getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(
			Long typeId, String documentNumber, String firstName,String lastName) throws BusinessException {
		return businessCrudEmployee.getEmployeeByDocTypeIdAndDocNumberAndFirstNAndLastN(typeId, documentNumber, firstName, lastName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeesDocumentsByDealer(java.lang.Long)
	 */
	public List<EmployeeVO> getEmployeesDocumentsByDealer(Long dealerID) throws BusinessException{
		return this.businessCrudEmployee.getEmployeesDocumentsByDealer(dealerID);
	}   		
	
    /**
     * Obtiene un empleado por tipo y numero de documento,
     * filtrar por el dealer si es diferente de null
     */
	public EmployeePaginationResponse getEmployeeByChriteria(Long typeId, String documentNumber, String firstName,String lastName, String depotCode, Long dealerCode,Long dealerId,Long countryId, RequestCollectionInfo requestCollInfo) throws BusinessException{
        return businessCrudEmployee.getEmployeeByChriteria(typeId, documentNumber, firstName, lastName, depotCode, dealerCode,dealerId,countryId,requestCollInfo);
    }	
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.EmployeeFacadeBeanLocal#getEmployeeDetailsByEmployeeId(java.lang.Long)
     */
    public EmployeeVO getEmployeeDetailsByEmployeeId(Long employeeId) throws BusinessException{
        return businessCrudEmployee.getEmployeeDetailsByEmployeeId(employeeId);
    }

	
}

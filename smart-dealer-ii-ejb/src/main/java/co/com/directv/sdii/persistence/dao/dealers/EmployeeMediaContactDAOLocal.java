package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EmployeeMediaContact;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad EmployeeMediaContact
 * 
 * Fecha de Creación: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface EmployeeMediaContactDAOLocal {

    public void createEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException;

    public EmployeeMediaContact getEmployeeMediaContactByID(Long id) throws DAOServiceException, DAOSQLException;

    public void updateEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException;

    public void deleteEmployeeMediaContact(EmployeeMediaContact obj) throws DAOServiceException, DAOSQLException;

    public List<EmployeeMediaContact> getAllEmployeeMediaContact() throws DAOServiceException, DAOSQLException;

    public List<EmployeeMediaContact> getEmployeeMediaContactByEmployeeId(Long id) throws DAOServiceException, DAOSQLException;

    public void deleteEmployeeMediaContactByEmployeeId(Long employeeId) throws DAOServiceException, DAOSQLException;
    
    /**
     * 
     * Metodo: Obtiene un medio de contacto por id de empleado y codigo de tipo de medio de contacto
     * @param employeeId Identificador del empleado
     * @param mediaContactType Codigo de tipo de medio de contacto
     * @return EmployeeMediaContact medio de contacto que cumple con el filtro
     * @throws DAOServiceException
     * @throws DAOSQLException <tipo> <descripcion>
     * @author jnova
     */
    public EmployeeMediaContact getEmployeeMediaContactByEmployeeIdAndMediaContCode(Long employeeId , String mediaContactType) throws DAOServiceException, DAOSQLException;
	
}

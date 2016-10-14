package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.MembershipType;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad MembershipTypes
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface MembershipTypesDAOLocal {

	
	public MembershipType getMembershipTypesByCode(String code) throws DAOServiceException, DAOSQLException;
	
	public MembershipType getMembershipTypesByID(Long id) throws DAOServiceException, DAOSQLException;
	
	public List<MembershipType> getAllMembershipTypes() throws DAOServiceException, DAOSQLException;

}
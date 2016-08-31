/**
 * Creado 28/05/2010 17:26:25
 */
package co.com.directv.sdii.persistence.dao.security.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.RoleType;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.RoleTypeDAOLocal;

/**
 * Implementa la persistencia en hiberna para la administación de Menús
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal
 * @see co.com.directv.sdii.persistence.dao.BaseDao
 * @see co.com.directv.sdii.model.pojo.Rol
 * @see co.com.directv.sdii.exceptions.DAOServiceException
 * @see co.com.directv.sdii.exceptions.DAOSQLException
 */
@Stateless(name="RoleTypeDAOLocal",mappedName="ejb/RoleTypeDAOLocal")
public class RoleTypeDAO extends BaseDao implements RoleTypeDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(RoleTypeDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#createRol(co.com.directv.sdii.model.pojo.Rol)
	 */
	public void createRoleType(RoleType roleType) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio createRoleType/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.save(roleType);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error guardando el RoleType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createRoleType/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#deleteRol(co.com.directv.sdii.model.pojo.RoleType)
	 */
	@Override
	public void deleteRoleType(RoleType RoleType) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio deleteRoleType/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.delete(RoleType);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el RoleType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteRoleType/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getAllRols()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RoleType> getAllRoleTypes() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllRoleTypes/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(RoleType.class.getName());
            Query query = session.createQuery(queryStr.toString());
            return  query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los RoleType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllRoleTypes/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getRolByCode(java.lang.String)
	 */
	@Override
	public RoleType getRoleTypeByCode(String roleTypeCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRoleTypeByCode/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(RoleType.class.getName());
        	queryStr.append(" roleType where lower(roleType.roleTypeCode) = :rolCode");
            Query query = session.createQuery(queryStr.toString());
            query.setString("rolCode", roleTypeCode.toLowerCase());

            return (RoleType)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el RoleType por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRoleTypeByCode/RolDAO ==");
        }
	}
	
	@Override
	public RoleType getRoleTypeByName(String roleTypeName) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRoleTypeByName/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(RoleType.class.getName());
        	queryStr.append(" roleType where lower(roleType.name) = :name");
            Query query = session.createQuery(queryStr.toString());
            query.setString("name", roleTypeName.toLowerCase());

            return (RoleType)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el RoleType por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRoleTypeByName/RolDAO ==");
        }
	}
	
	@Override
	public RoleType getRoleTypeByNameOrCode(String roleTypeName, String roleTypeCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getRoleTypeByNameOrCode/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(RoleType.class.getName());
        	queryStr.append(" roleType where 1 = 1 ");
        	if(roleTypeName!=null && !roleTypeName.equals(""))
        		queryStr.append(" and  lower(roleType.name) = :name ");
        	if(roleTypeCode!=null && !roleTypeCode.equals(""))
        		queryStr.append(" and  lower(roleType.roleTypeCode) = :rolCode ");
        	
            Query query = session.createQuery(queryStr.toString());
            if(roleTypeName!=null)
            	query.setString("name", roleTypeName.toLowerCase());
            if(roleTypeCode!=null)
            	query.setString("rolCode", roleTypeCode.toLowerCase());

            return (RoleType)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el RoleType por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRoleTypeByNameOrCode/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getRolById(java.lang.Long)
	 */
	@Override
	public RoleType getRoleTypeById(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRolById/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(RoleType.class.getName());
        	queryStr.append("  roleType where roleType.id = :id");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("id", id);

            return (RoleType)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el RoleType por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolById/RolDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#updateRol(co.com.directv.sdii.model.pojo.RoleType)
	 */
	@Override
	public void updateRoleType(RoleType roleType) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateRoleType/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.merge(roleType);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el RoleType ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateRoleType/RolDAO ==");
        }
	}

}

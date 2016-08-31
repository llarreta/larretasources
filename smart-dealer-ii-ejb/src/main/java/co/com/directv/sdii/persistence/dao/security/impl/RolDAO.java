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
import co.com.directv.sdii.model.pojo.Rol;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.RolDAOLocal;

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
@Stateless(name="RolDAOLocal",mappedName="ejb/RolDAOLocal")
public class RolDAO extends BaseDao implements RolDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(RolDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#createRol(co.com.directv.sdii.model.pojo.Rol)
	 */
	@Override
	public void createRol(Rol rol) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio createRol/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.save(rol);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error guardando el Rol ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina RolDAO/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#deleteRol(co.com.directv.sdii.model.pojo.Rol)
	 */
	@Override
	public void deleteRol(Rol rol) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio deleteRol/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.delete(rol);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el Rol ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteRol/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getAllRols()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getAllRoles() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllRoles/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append(" rol ");
            Query query = session.createQuery(queryStr.toString());
            query.setCacheable(true);
            
            return  query.list();
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Rol ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllRoles/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getRolByCode(java.lang.String)
	 */
	@Override
	public Rol getRolByCode(String rolCode) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRolByCode/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append(" rol where lower(rol.rolCode) = :rolCode");
            Query query = session.createQuery(queryStr.toString());
            query.setString("rolCode", rolCode.toLowerCase());
            query.setCacheable(true);
            
            return (Rol)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Rol por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolByCode/RolDAO ==");
        }
	}
	
	@Override
	public Rol getRolByName(String rolName) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRolByCode/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append(" rol where lower(rol.name) = :name");
            Query query = session.createQuery(queryStr.toString());
            query.setString("name", rolName.toLowerCase());
            query.setCacheable(true);

            return (Rol)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Rol por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolByCode/RolDAO ==");
        }
	}
	
	@Override
	public List<Rol> getRolByNameOrCode(String rolName, String rolCode) throws DAOServiceException,DAOSQLException {
		log.debug("== Inicio getRolByNameOrCode/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append(" rol where 1 = 1 ");
        	if(rolName!=null && !rolName.equals(""))
        		queryStr.append(" and  lower(rol.name) = :name ");
        	if(rolCode!=null && !rolCode.equals(""))
        		queryStr.append(" and  lower(rol.rolCode) = :rolCode ");
        	
            Query query = session.createQuery(queryStr.toString());
            if(rolName!=null)
            	query.setString("name", rolName.toLowerCase());
            if(rolCode!=null)
            	query.setString("rolCode", rolCode.toLowerCase());
            query.setCacheable(true);

            return query.list();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Rol por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolByNameOrCode/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getRolById(java.lang.Long)
	 */
	@Override
	public Rol getRolById(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getRolById/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append("  rol where rol.id = :id");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("id", id);
            query.setCacheable(true);

            return (Rol)query.uniqueResult();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Rol por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolById/RolDAO ==");
        }
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#updateRol(co.com.directv.sdii.model.pojo.Rol)
	 */
	public void updateRol(Rol rol) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateRol/RolDAO ==");
        Session session = super.getSession();
        try {
        	session.merge(rol);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Rol ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateRol/RolDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.RolDAOLocal#getRolesByRoleTypeId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<Rol> getRolesByRoleTypeId(Long roleTypeId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getRolesByRoleTypeId/RolDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Rol.class.getName());
        	queryStr.append("  rol where rol.roleType.id = :aRoleTypeId");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("aRoleTypeId", roleTypeId);
            query.setCacheable(true);

            return query.list();
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Rol por tipo de rol ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getRolesByRoleTypeId/RolDAO ==");
        }
	}

}

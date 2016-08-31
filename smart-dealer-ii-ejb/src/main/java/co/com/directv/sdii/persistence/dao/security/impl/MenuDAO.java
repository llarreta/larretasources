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
import co.com.directv.sdii.model.pojo.Menu;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.MenuDAOLocal;

/**
 * Implementa la persistencia en hiberna para la administación de Menús
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal
 * @see co.com.directv.sdii.persistence.dao.BaseDao
 * @see co.com.directv.sdii.model.pojo.Menu
 * @see co.com.directv.sdii.exceptions.DAOServiceException
 * @see co.com.directv.sdii.exceptions.DAOSQLException
 */
@Stateless(name="MenuDAOLocal",mappedName="ejb/MenuDAOLocal")
public class MenuDAO extends BaseDao implements MenuDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(MenuDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#createMenu(co.com.directv.sdii.model.pojo.Menu)
	 */
	@Override
	public void createMenu(Menu menu) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio createMenu/MenuDAO ==");
        Session session = super.getSession();
        try {
        	session.save(menu);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error guardando el Menu ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createMenu/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#deleteMenu(co.com.directv.sdii.model.pojo.Menu)
	 */
	@Override
	public void deleteMenu(Menu menu) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio deleteMenu/MenuDAO ==");
        Session session = super.getSession();
        try {
        	session.delete(menu);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el Menu ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteMenu/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#getAllMenus()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAllMenus() throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getAllMenus/MenuDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Menu.class.getName());
        	queryStr.append(" menu order by menu.menuOrder");
            Query query = session.createQuery(queryStr.toString());
            
            List<Menu> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Menu ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllMenus/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#getMenuByCode(java.lang.String)
	 */
	@Override
	public Menu getMenuByCode(String code) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMenuByCode/MenuDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Menu.class.getName());
        	queryStr.append(" menu where menu.menuCode = :aMenuCode");
            Query query = session.createQuery(queryStr.toString());
            query.setString("aMenuCode", code);
            
            Menu result = (Menu)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Menu por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenuByCode/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#getMenuById(java.lang.Long)
	 */
	@Override
	public Menu getMenuById(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMenuById/MenuDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Menu.class.getName());
        	queryStr.append(" menu where menu.id = :aMenuId");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("aMenuId", id);
            
            Menu result = (Menu)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el Menu por id ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenuById/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#getMenusByFather(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenusByFather(Long fatherId)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMenusByFather/MenuDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(Menu.class.getName());
        	queryStr.append(" menu where menu.fatherMenuId = :aFatherMenuId order by menu.menuOrder");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("aFatherMenuId", fatherId);
            
            List<Menu> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los Menu por identificador del padre ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenusByFather/MenuDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuDAOLocal#updateMenu(co.com.directv.sdii.model.pojo.Menu)
	 */
	@Override
	public void updateMenu(Menu menu) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateMenu/MenuDAO ==");
        Session session = super.getSession();
        try {
        	menu = (Menu)session.merge(menu);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el Menu ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateMenu/MenuDAO ==");
        }
	}

}

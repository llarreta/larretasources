/**
 * Creado 28/05/2010 17:40:16
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
import co.com.directv.sdii.model.pojo.MenuItem;
import co.com.directv.sdii.persistence.dao.BaseDao;
import co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal;

/**
 * Implementa el mencanismo de persistencia en Hibernate para las entidades MenuItem
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.BaseDao
 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal
 * @see co.com.directv.sdii.model.pojo.MenuItem
 * @see co.com.directv.sdii.model.pojo.Menu
 * @see co.com.directv.sdii.exceptions.DAOServiceException
 * @see co.com.directv.sdii.exceptions.DAOSQLException
 */
@Stateless(name="MenuItemDAOLocal",mappedName="ejb/MenuItemDAOLocal")
public class MenuItemDAO extends BaseDao implements MenuItemDAOLocal {

	private final static Logger log = UtilsBusiness.getLog4J(MenuItemDAO.class);
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#createMenuItem(co.com.directv.sdii.model.pojo.MenuItem)
	 */
	@Override
	public void createMenuItem(MenuItem menuItem) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio createMenuItem/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	session.save(menuItem);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error guardando el MenuItem ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createMenuItem/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#deleteMenuItem(co.com.directv.sdii.model.pojo.MenuItem)
	 */
	@Override
	public void deleteMenuItem(MenuItem menuItem) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio deleteMenuItem/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	session.delete(menuItem);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error borrando el MenuItem ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteMenuItem/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#getAllMenuItems()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItem> getAllMenuItems() throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getAllMenuItems/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(MenuItem.class.getName());
        	queryStr.append(" menuItem order by menuItem.menuOrder");
            Query query = session.createQuery(queryStr.toString());
            
            List<MenuItem> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los MenuItems ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllMenuItems/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#getMenuItemByCode(java.lang.String)
	 */
	@Override
	public MenuItem getMenuItemByCode(String code) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMenuItemByCode/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(MenuItem.class.getName());
        	queryStr.append(" menuItem where menuItem.itemCode = :aMenuItemCode");
            Query query = session.createQuery(queryStr.toString());
            query.setString("aMenuItemCode", code);
            
            MenuItem result = (MenuItem)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el MenuItem por código ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenuItemByCode/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#getMenuItemById(java.lang.Long)
	 */
	@Override
	public MenuItem getMenuItemById(Long id) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio getMenuItemById/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(MenuItem.class.getName());
        	queryStr.append(" menuItem where menuItem.id = :aMenuItemId");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("aMenuItemId", id);
            
            MenuItem result = (MenuItem)query.uniqueResult();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando el MenuItem por id ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenuItemById/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#getMenuItemsByMenu(co.com.directv.sdii.model.pojo.Menu)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItem> getMenuItemsByMenu(Menu menu)
			throws DAOServiceException, DAOSQLException {
		log.debug("== Inicio getMenuItemsByMenu/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	StringBuffer queryStr = new StringBuffer("from ");
        	queryStr.append(MenuItem.class.getName());
        	queryStr.append(" menuItem where menuItem.menuId = :aMenuId order by menuItem.menuOrder");
            Query query = session.createQuery(queryStr.toString());
            query.setLong("aMenuId", menu.getId());
            
            List<MenuItem> result = query.list();
            return result;
            
        } catch (Throwable ex) {
            log.debug("== Error consultando todos los MenuItems por menu del padre ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getMenuItemsByMenu/MenuItemDAO ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal#updateMenuItem(co.com.directv.sdii.model.pojo.MenuItem)
	 */
	@Override
	public void updateMenuItem(MenuItem menuItem) throws DAOServiceException,
			DAOSQLException {
		log.debug("== Inicio updateMenuItem/MenuItemDAO ==");
        Session session = super.getSession();
        try {
        	menuItem = (MenuItem)session.merge(menuItem);
        	super.doFlush(session);
        } catch (Throwable ex) {
            log.debug("== Error actualizando el MenuItem ==");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateMenuItem/MenuItemDAO ==");
        }
	}

}

/**
 * Creado 28/05/2010 11:27:16
 */
package co.com.directv.sdii.facade.security.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanRemote;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MenuItemVO;
import co.com.directv.sdii.model.vo.MenuVO;
import co.com.directv.sdii.model.vo.ProfileVO;
import co.com.directv.sdii.model.vo.RolVO;
import co.com.directv.sdii.model.vo.RoleTypeVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Implementa la fachada que expone la funcionalidad del módulo de seguridad
 * 
 * Fecha de Creación: 28/05/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal     
 */
@Stateless(name="SecurityFacadeBeanLocal")
@Local({SecurityFacadeBeanLocal.class})
@Remote({SecurityFacadeBeanRemote.class})
public class SecurityFacadeBean implements SecurityFacadeBeanLocal,SecurityFacadeBeanRemote {

	@EJB(name="SecurityBusinessBeanLocal", beanInterface=SecurityBusinessBeanLocal.class)
	private SecurityBusinessBeanLocal securityBusinessBean;
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createMenu(co.com.directv.sdii.model.vo.MenuVO)
	 */
	@Override
	public void createMenu(MenuVO menu) throws BusinessException {
		securityBusinessBean.createMenu(menu);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
	 */
	@Override
	public void createMenuItem(MenuItemVO menuItem) throws BusinessException {
		securityBusinessBean.createMenuItem(menuItem);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createProfile(co.com.directv.sdii.model.vo.ProfileVO)
	 */
	@Override
	public void createProfile(ProfileVO profile) throws BusinessException {
		securityBusinessBean.createProfile(profile);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createProfiles(java.util.List)
	 */
	@Override
	public void createProfiles(List<ProfileVO> profiles)
			throws BusinessException {
		securityBusinessBean.createProfiles(profiles);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteByRolAndCreateProfiles(java.util.List, co.com.directv.sdii.model.vo.RolVO)
	 */
	public void deleteByRolAndCreateProfiles(List<ProfileVO> profiles, RolVO rol) throws BusinessException{
		securityBusinessBean.deleteByRolAndCreateProfiles(profiles,rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public void createRol(RolVO rol) throws BusinessException {
		securityBusinessBean.createRol(rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#createUser(co.com.directv.sdii.model.vo.UserVO)
	 */
	@Override
	public void createUser(UserVO user) throws BusinessException {
		securityBusinessBean.createUser(user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteMenu(co.com.directv.sdii.model.vo.MenuVO)
	 */
	@Override
	public void deleteMenu(MenuVO menu) throws BusinessException {
		securityBusinessBean.deleteMenu(menu);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
	 */
	@Override
	public void deleteMenuItem(MenuItemVO menuItem) throws BusinessException {
		securityBusinessBean.deleteMenuItem(menuItem);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteProfile(co.com.directv.sdii.model.vo.ProfileVO)
	 */
	@Override
	public void deleteProfile(ProfileVO profile) throws BusinessException {
		securityBusinessBean.deleteProfile(profile);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteProfilesByRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public void deleteProfilesByRol(RolVO rol) throws BusinessException {
		securityBusinessBean.deleteProfilesByRol(rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public void deleteRol(RolVO rol) throws BusinessException {
		securityBusinessBean.deleteRol(rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#deleteUser(co.com.directv.sdii.model.vo.UserVO)
	 */
	@Override
	public void deleteUser(UserVO user) throws BusinessException {
		securityBusinessBean.deleteUser(user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getAllMenuItems()
	 */
	@Override
	public List<MenuItemVO> getAllMenuItems() throws BusinessException {
		return securityBusinessBean.getAllMenuItems();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getAllMenus()
	 */
	@Override
	public List<MenuVO> getAllMenus() throws BusinessException {
		return securityBusinessBean.getAllMenus();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getAllRols()
	 */
	@Override
	public List<RolVO> getAllRoles() throws BusinessException {
		return securityBusinessBean.getAllRoles();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getAllUsers()
	 */
	@Override
	public List<UserVO> getAllUsers() throws BusinessException {
		return securityBusinessBean.getAllUsers();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuByCode(java.lang.String)
	 */
	@Override
	public MenuVO getMenuByCode(String code) throws BusinessException {
		return securityBusinessBean.getMenuByCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuById(java.lang.Long)
	 */
	@Override
	public MenuVO getMenuById(Long id) throws BusinessException {
		return securityBusinessBean.getMenuById(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuItemByCode(java.lang.String)
	 */
	@Override
	public MenuItemVO getMenuItemByCode(String code) throws BusinessException {
		return securityBusinessBean.getMenuItemByCode(code);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuItemById(java.lang.Long)
	 */
	@Override
	public MenuItemVO getMenuItemById(Long id) throws BusinessException {
		return securityBusinessBean.getMenuItemById(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuItemsByMenu(co.com.directv.sdii.model.vo.MenuVO)
	 */
	@Override
	public List<MenuItemVO> getMenuItemsByMenu(MenuVO menu)
			throws BusinessException {
		return securityBusinessBean.getMenuItemsByMenu(menu);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenusByFather(java.lang.Long)
	 */
	@Override
	public List<MenuVO> getMenusByFather(Long fatherId)
			throws BusinessException {
		return securityBusinessBean.getMenusByFather(fatherId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getProfileByRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public List<ProfileVO> getProfileByRol(RolVO rol) throws BusinessException {
		return securityBusinessBean.getProfileByRol(rol);
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getProfileByMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
	 */
	public List<ProfileVO> getProfileByMenuItem(MenuItemVO menuItem)
			throws BusinessException {
		return securityBusinessBean.getProfileByMenuItem(menuItem);
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getProfileByRolAndMenuItem(co.com.directv.sdii.model.vo.RolVO, co.com.directv.sdii.model.vo.MenuItemVO)
	 */
	public ProfileVO getProfileByRolAndMenuItem(RolVO rol, MenuItemVO menuItem)
			throws BusinessException {
		return securityBusinessBean.getProfileByRolAndMenuItem(rol,menuItem);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getRolByCode(java.lang.String)
	 */
	@Override
	public RolVO getRolByCode(String code) throws BusinessException {
		return securityBusinessBean.getRolByCode(code);
	}
	
	@Override
	public RolVO getRolByName(String rolName) throws BusinessException {
		return securityBusinessBean.getRolByName(rolName);
	}
	
	@Override
	public List<RolVO> getRolByNameOrCode(String rolName, String rolCode) throws BusinessException {
		return securityBusinessBean.getRolByNameOrCode(rolName, rolCode);
	}


	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getRolById(java.lang.Long)
	 */
	@Override
	public RolVO getRolById(Long id) throws BusinessException {
		return securityBusinessBean.getRolById(id);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUserById(java.lang.Long)
	 */
	@Override
	public UserVO getUserById(Long id) throws BusinessException {
		return securityBusinessBean.getUserById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUserByIdAndCountry(java.lang.Long, java.lang.Long)
	 */
	@Override
	public UserVO getUserByIdAndCountry(Long id, Long countryId) throws BusinessException {
		return securityBusinessBean.getUserByIdAndCountry(id,countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUserByLoginAndDealer(java.lang.String, co.com.directv.sdii.model.vo.DealerVO)
	 */
	@Override
	public UserVO getUserByLoginAndDealer(String login, DealerVO dealer)
			throws BusinessException {
		return securityBusinessBean.getUserByLoginAndDealer(login, dealer);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUsersByDealer(co.com.directv.sdii.model.vo.DealerVO)
	 */
	@Override
	public List<UserVO> getUsersByDealer(DealerVO dealer)
			throws BusinessException {
		return securityBusinessBean.getUsersByDealer(dealer);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUsersByLogin(java.lang.String)
	 */
	@Override
	public List<UserVO> getUsersByLogin(String login) throws BusinessException {
		return securityBusinessBean.getUsersByLogin(login);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUsersByRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public List<UserVO> getUsersByRol(RolVO rol) throws BusinessException {
		return securityBusinessBean.getUsersByRol(rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUsersByRolAndDealer(co.com.directv.sdii.model.vo.RolVO, co.com.directv.sdii.model.vo.DealerVO)
	 */
	@Override
	public List<UserVO> getUsersByRolAndDealer(RolVO rol, DealerVO dealer)
			throws BusinessException {
		return securityBusinessBean.getUsersByRolAndDealer(rol, dealer);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#updateMenu(co.com.directv.sdii.model.vo.MenuVO)
	 */
	@Override
	public void updateMenu(MenuVO menu) throws BusinessException {
		securityBusinessBean.updateMenu(menu);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#updateMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
	 */
	@Override
	public void updateMenuItem(MenuItemVO menuItem) throws BusinessException {
		securityBusinessBean.updateMenuItem(menuItem);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#updateRol(co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	public void updateRol(RolVO rol) throws BusinessException {
		securityBusinessBean.updateRol(rol);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#updateUser(co.com.directv.sdii.model.vo.UserVO)
	 */
	@Override
	public void updateUser(UserVO user) throws BusinessException {
		securityBusinessBean.updateUser(user);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUserByLoginOrDocNumber(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserVO> getUsersByLoginOrDocNumber(String login, String docNumber) throws BusinessException{
		return securityBusinessBean.getUsersByLoginOrDocNumber(login, docNumber);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#authenticateLDAPUser(java.lang.String, java.lang.String)
	 */
	public UserVO authenticateLDAPUser(String userName, String password, Long countryId)
			throws BusinessException {
		return securityBusinessBean.authenticateLDAPUser(userName, password, countryId);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#authorizeMenuItem(java.lang.String, java.lang.String)
	 */
	public boolean authorizeMenuItem(String menuItemCode, String roleCode)
			throws BusinessException {
		return securityBusinessBean.authorizeMenuItem(menuItemCode, roleCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenuItemsByMenuAndRole(co.com.directv.sdii.model.vo.MenuVO, co.com.directv.sdii.model.vo.RolVO)
	 */
	public List<MenuItemVO> getMenuItemsByMenuAndRole(MenuVO menu, RolVO role)
			throws BusinessException {
		return securityBusinessBean.getMenuItemsByMenuAndRole(menu, role);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getMenusByRole(co.com.directv.sdii.model.vo.RolVO)
	 */
	public List<MenuVO> getMenusByRole(RolVO role) throws BusinessException {
		return securityBusinessBean.getMenusByRole(role);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getAllRoleTypes()
	 */
	public List<RoleTypeVO> getAllRoleTypes() throws BusinessException {
		return securityBusinessBean.getAllRoleTypes();
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getRoleTypesByRoleTypeCode(java.lang.String)
	 */
	public List<RoleTypeVO> getRoleTypesByRoleTypeCode(String roleTypeCode)
			throws BusinessException {
		return securityBusinessBean.getRoleTypesByRoleTypeCode(roleTypeCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getRolesByRoleTypeId(java.lang.Long)
	 */
	public List<RolVO> getRolesByRoleTypeId(Long roleTypeId)
			throws BusinessException {
		return securityBusinessBean.getRolesByRoleTypeId(roleTypeId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal#getUsersByLoginOrDocNumberAndRol(java.lang.String, java.lang.String, co.com.directv.sdii.model.vo.RolVO, java.lang.Long)
	 */
	@Override
	public List<UserVO> getUsersByLoginOrDocNumberAndRol(String login,
			String docNumber, RolVO rol,Long countryId) throws BusinessException {
		return securityBusinessBean.getUsersByLoginOrDocNumberAndRol(login, docNumber, rol,countryId);
	}
}

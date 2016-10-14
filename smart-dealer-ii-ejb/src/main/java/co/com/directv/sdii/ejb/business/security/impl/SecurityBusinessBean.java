package co.com.directv.sdii.ejb.business.security.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.Country;
import co.com.directv.sdii.model.pojo.Menu;
import co.com.directv.sdii.model.pojo.MenuItem;
import co.com.directv.sdii.model.pojo.Profile;
import co.com.directv.sdii.model.pojo.Rol;
import co.com.directv.sdii.model.pojo.RoleType;
import co.com.directv.sdii.model.pojo.SdiiTimeZone;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.pojo.User;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.MenuItemVO;
import co.com.directv.sdii.model.vo.MenuVO;
import co.com.directv.sdii.model.vo.ProfileVO;
import co.com.directv.sdii.model.vo.RolVO;
import co.com.directv.sdii.model.vo.RoleTypeVO;
import co.com.directv.sdii.model.vo.UserVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;
import co.com.directv.sdii.persistence.dao.security.MenuDAOLocal;
import co.com.directv.sdii.persistence.dao.security.MenuItemDAOLocal;
import co.com.directv.sdii.persistence.dao.security.ProfileDAOLocal;
import co.com.directv.sdii.persistence.dao.security.RolDAOLocal;
import co.com.directv.sdii.persistence.dao.security.RoleTypeDAOLocal;
import co.com.directv.sdii.persistence.dao.security.UserDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;
import co.com.intergrupo.ldap.dto.AttributeTypeDTO;
import co.com.intergrupo.ldap.dto.ContactoDTO;
import co.com.intergrupo.ldap.service.exception.SpringLdapAuthenticationException;
import co.com.intergrupo.ldap.service.exception.SpringLdapComunicationException;
import co.com.intergrupo.ldap.service.exception.SpringLdapException;
import co.com.intergrupo.ldap.service.exception.SpringLdapSecurityException;
import co.com.intergrupo.ldap.services.ActiveDirectoryAuthenticatorServices;
import co.com.intergrupo.ldap.services.SpringLdapServices;
import co.com.intergrupo.ldap.vo.PersonVO;


/**
 * Session Bean implementation class SecurityBusinessBean 
 * @author Jimmy VÃ©lez MuÃ±oz
 */
@Stateless(name="SecurityBusinessBean", mappedName = "ejb/SecurityBusinessBean")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SecurityBusinessBean extends BusinessBase implements SecurityBusinessBeanLocal {

	private final static Logger log = UtilsBusiness.getLog4J(SecurityBusinessBean.class);
	
	@EJB(name="UserDAOLocal", beanInterface=UserDAOLocal.class)
	private UserDAOLocal userDao;
	
	@EJB(name="ProfileDAOLocal", beanInterface=ProfileDAOLocal.class)
	private ProfileDAOLocal daoProfile;
	
	@EJB(name="MenuDAOLocal", beanInterface=MenuDAOLocal.class)
	private MenuDAOLocal menuDao;
	
	@EJB(name="MenuItemDAOLocal", beanInterface=MenuItemDAOLocal.class)
	private MenuItemDAOLocal menuItemDao;
	
	@EJB(name="RolDAOLocal", beanInterface=RolDAOLocal.class)
	private RolDAOLocal rolDAO;
	
	@EJB(name="SystemParameterDAOLocal", beanInterface=SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal sysParamDao;
	
	@EJB(name="RoleTypeDAOLocal", beanInterface=RoleTypeDAOLocal.class)
	private RoleTypeDAOLocal roleTypeDao;
	
	@EJB(name="CountriesDAOLocal", beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal countryDao;
	
    /**
     * Default constructor. 
     */
    public SecurityBusinessBean() {
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#createMenu(co.com.directv.sdii.model.vo.MenuVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createMenu(MenuVO menu) throws BusinessException {
    	log.debug("== Inicia createMenu/SecurityBusinessBean ==");
		try {
			
			if(!BusinessRuleValidationManager.getInstance().isValid(menu)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			Menu menuPojo = UtilsBusiness.copyObject(Menu.class, menu);
			menuDao.createMenu(menuPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion createMenu/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina createMenu/SecurityBusinessBean ==");
		}
	}

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#createMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createMenuItem(MenuItemVO menuItem) throws BusinessException {
    	log.debug("== Inicia createMenuItem/SecurityBusinessBean ==");
		try {
			
			if(!BusinessRuleValidationManager.getInstance().isValid(menuItem)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			MenuItem menuItemPojo = UtilsBusiness.copyObject(MenuItem.class, menuItem);
			menuItemDao.createMenuItem(menuItemPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion createMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina createMenuItem/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#createProfile(co.com.directv.sdii.model.vo.ProfileVO)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createProfile(ProfileVO profile) throws BusinessException {
		log.debug("== Inicia createProfile/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(profile, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getRol(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getRol().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getMenuItem(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getMenuItem().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            
            Profile profileTmp = this.daoProfile.getProfileByIDMenuItemAndIDRol(profile.getId().getRol().getId(), profile.getId().getMenuItem().getId());
            if(profileTmp != null){
            	log.debug(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
                throw new BusinessException(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getCode(),ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
            }
            daoProfile.createProfile(UtilsBusiness.copyObject(Profile.class, profile));
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaciÃ³n createProfile/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createProfile/SecurityBusinessBean ==");
        }
		
	}
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteByRolAndCreateProfiles(java.util.List, co.com.directv.sdii.model.vo.RolVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteByRolAndCreateProfiles(List<ProfileVO> profiles, RolVO rol) throws BusinessException{
    	log.debug("== Inicia deleteByRolAndCreateProfiles/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(profiles, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(rol.getRolCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	this.deleteProfilesByRol(rol);
        	this.createProfiles(profiles);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaciÃ³n deleteByRolAndCreateProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteByRolAndCreateProfiles/SecurityBusinessBean ==");
        }
    }
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#createProfiles(java.util.List)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createProfiles(List<ProfileVO> profiles)
			throws BusinessException {
		log.debug("== Inicia createProfiles/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(profiles, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	for(ProfileVO proTmp : profiles){
        		UtilsBusiness.assertNotNull(proTmp.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            	UtilsBusiness.assertNotNull(proTmp.getId().getRol(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            	UtilsBusiness.assertNotNull(proTmp.getId().getRol().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            	UtilsBusiness.assertNotNull(proTmp.getId().getMenuItem(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            	UtilsBusiness.assertNotNull(proTmp.getId().getMenuItem().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                
                Profile profileTmp = this.daoProfile.getProfileByIDMenuItemAndIDRol(proTmp.getId().getRol().getId(), proTmp.getId().getMenuItem().getId());
                if(profileTmp != null){
                	log.debug(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
                    throw new BusinessException(ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getCode(),ErrorBusinessMessages.CODE_OR_NAME_ALREADY_EXIST_FOR_ENTITY.getMessage());
                }
                daoProfile.createProfile(UtilsBusiness.copyObject(Profile.class, proTmp));
        	}
        	
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createProfiles/SecurityBusinessBean ==");
        }
		
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia createRol/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            this.rolDAO.createRol(UtilsBusiness.copyObject(Rol.class, rol));
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina createRol/SecurityBusinessBean ==");
        }
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#createUser(co.com.directv.sdii.model.vo.UserVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void createUser(UserVO user) throws BusinessException {
		log.debug("== Inicia createUser/SecurityBusinessBean ==");
		try {
			if(!BusinessRuleValidationManager.getInstance().isValid(user)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if(!UtilsBusiness.isEmail(user.getEmail())){
				throw new BusinessException(ErrorBusinessMessages.INVALID_EMAIL.getCode(), ErrorBusinessMessages.INVALID_EMAIL.getMessage());
			}
			validateUser(user);
			
			User userPojo = UtilsBusiness.copyObject(User.class, user);
			userDao.createUser(userPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion createUser/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina createUser/SecurityBusinessBean ==");
		}
	}
    
    private void validateUser(UserVO user) throws DAOServiceException, DAOSQLException, BusinessException{
    	User userPojo = new User();
		userPojo.setLogin(user.getLogin());
		List<User> users = userDao.getUserBySample(userPojo);
		
		boolean exist = false;
		
		for (User userInList : users) {
			if(user.getId() != null && user.getId().longValue() == userInList.getId().longValue()){
				continue;
			}
			exist = doUserValidations(userInList, user) ? true : exist;
		}
		
		userPojo.setLogin(null);
		userPojo.setIdNumber(user.getIdNumber());
		users = userDao.getUserBySample(userPojo);
		
		for (User userInList : users) {
			if(user.getId() != null && user.getId().longValue() == userInList.getId().longValue()){
				continue;
			}
			exist = doUserValidations(userInList, user) ? true : exist;
		}
		
		if(exist){
			throw new BusinessException(ErrorBusinessMessages.USER_ALREADY_EXIST.getCode(), ErrorBusinessMessages.USER_ALREADY_EXIST.getMessage());
		}
    }

    private boolean doUserValidations(User userInList, UserVO user) {
		boolean exist = false;
		//2010-12-20 por jjimenezh: Se remueve la validaciÃ³n de diferente dealer, solo se deja la validaciÃ³n de paÃ­s:
		/*
		//Si el usuario no tiene asociado dealer debe tener asociado paÃ­s
		if(userInList.getDealer() == null && user.getDealer() == null){
			if(userInList.getCountry().getId().longValue() == user.getCountry().getId().longValue()){
				exist = true;
			}
		} 
		
		if(userInList.getDealer() != null && user.getDealer() != null && (userInList.getDealer().getId().longValue() == user.getDealer().getId().longValue())){
			exist = true;
		}*/
		
		//2010-12-20 por jjimenezh:Solo se valida que el login sea diferente por paÃ­s
		if(userInList.getCountry().getId().longValue() == user.getCountry().getId().longValue()){
			exist = true;
		}
    	return exist;
	}

	/*
	 * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteMenu(co.com.directv.sdii.model.vo.MenuVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteMenu(MenuVO menu) throws BusinessException {
    	log.debug("== Inicia deleteMenu/SecurityBusinessBean ==");
		try {
			
			UtilsBusiness.assertNotNull(menu, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menu.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Menu menuPojo = UtilsBusiness.copyObject(Menu.class, menu);
			menuDao.deleteMenu(menuPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion deleteMenu/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina deleteMenu/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteMenuItem(MenuItemVO menuItem) throws BusinessException {
    	log.debug("== Inicia deleteMenuItem/SecurityBusinessBean ==");
		try {
			
			UtilsBusiness.assertNotNull(menuItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menuItem.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			MenuItem menuItemPojo = UtilsBusiness.copyObject(MenuItem.class, menuItem);
			menuItemDao.deleteMenuItem(menuItemPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion deleteMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina deleteMenuItem/SecurityBusinessBean ==");
		}
	}

    /* (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteProfile(co.com.directv.sdii.model.vo.ProfileVO)
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProfile(ProfileVO profile) throws BusinessException {
		log.debug("== Inicia deleteProfile/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(profile, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getRol(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getRol().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getMenuItem(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(profile.getId().getMenuItem().getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            
            Profile profileTmp = this.daoProfile.getProfileByIDMenuItemAndIDRol(profile.getId().getRol().getId(), profile.getId().getMenuItem().getId());
            if(profileTmp == null){
            	log.debug(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            }
            daoProfile.deleteProfile(profileTmp);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n deleteProfile/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProfile/SecurityBusinessBean ==");
        }
		
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteProfilesByRol(co.com.directv.sdii.model.vo.RolVO)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteProfilesByRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia deleteProfile/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	List<Profile> perfiles = daoProfile.getProfileByIDRol(rol.getId());
        	for(Profile perfilTmp : perfiles){
        		daoProfile.deleteProfile(perfilTmp);
        	}
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n deleteProfile/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteProfile/SecurityBusinessBean ==");
        }
		
    	
    	
		
	}

	@Override
	public void deleteRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia deleteRol/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
             this.rolDAO.deleteRol(UtilsBusiness.copyObject(Rol.class, rol));
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina deleteRol/SecurityBusinessBean ==");
        }	
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#deleteUser(co.com.directv.sdii.model.vo.UserVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void deleteUser(UserVO user) throws BusinessException {
		log.debug("== Inicia deleteUser/SecurityBusinessBean ==");
		try {
			
			UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User userPojo = UtilsBusiness.copyObject(User.class, user);
			userDao.deleteUser(userPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion deleteUser/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina deleteUser/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getAllMenuItems()
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<MenuItemVO> getAllMenuItems() throws BusinessException {
    	log.debug("== Inicia getAllMenuItems/SecurityBusinessBean ==");
		try {
			List<MenuItem> menuItemsPojo = menuItemDao.getAllMenuItems();
			List<MenuItemVO> menuItems = UtilsBusiness.convertList(menuItemsPojo, MenuItemVO.class);
			return menuItems;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllMenuItems/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllMenuItems/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getAllMenus()
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<MenuVO> getAllMenus() throws BusinessException {
    	log.debug("== Inicia getAllMenus/SecurityBusinessBean ==");
		try {
			List<Menu> menusPojo = menuDao.getAllMenus();
			List<MenuVO> menus = UtilsBusiness.convertList(menusPojo, MenuVO.class);
			return menus;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllMenus/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllMenus/SecurityBusinessBean ==");
		}
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<RolVO> getAllRoles() throws BusinessException {
		log.debug("== Inicia getAllRoles/SecurityBusinessBean ==");
        try {
        	List<Rol> rolesPojo = this.rolDAO.getAllRoles();
        	if(rolesPojo == null || rolesPojo.size()==0){
        		return new ArrayList<RolVO>();
        	}
            return UtilsBusiness.convertList(rolesPojo, RolVO.class);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getAllRoles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllRoles/SecurityBusinessBean ==");
        }	
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getAllUsers()
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<UserVO> getAllUsers() throws BusinessException {
		log.debug("== Inicia getAllUsers/SecurityBusinessBean ==");
		try {
			List<User> usersPojo = userDao.getAllUsers();
			List<UserVO> users = UtilsBusiness.convertList(usersPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllUsers/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllUsers/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuByCode(java.lang.String)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MenuVO getMenuByCode(String code) throws BusinessException {
    	log.debug("== Inicia getMenuByCode/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Menu menuPojo = menuDao.getMenuByCode(code);
			if(menuPojo == null){
				return null;
			}
			
			MenuVO menu = UtilsBusiness.copyObject(MenuVO.class, menuPojo);
			return menu;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuByCode/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuByCode/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuById(java.lang.Long)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MenuVO getMenuById(Long id) throws BusinessException {
    	log.debug("== Inicia getMenuById/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Menu menuPojo = menuDao.getMenuById(id);
			if(menuPojo == null){
				return null;
			}
			MenuVO menu = UtilsBusiness.copyObject(MenuVO.class, menuPojo);
			return menu;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuById/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuById/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuItemByCode(java.lang.String)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MenuItemVO getMenuItemByCode(String code) throws BusinessException {
    	log.debug("== Inicia getMenuItemByCode/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			MenuItem menuItemPojo = menuItemDao.getMenuItemByCode(code);
			if(menuItemPojo == null){
				return null;
			}
			
			MenuItemVO menuItem = UtilsBusiness.copyObject(MenuItemVO.class, menuItemPojo);
			return menuItem;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuItemByCode/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuItemByCode/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuItemById(java.lang.Long)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public MenuItemVO getMenuItemById(Long id) throws BusinessException {
    	log.debug("== Inicia getMenuItemById/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			MenuItem menuItemPojo = menuItemDao.getMenuItemById(id);
			if(menuItemPojo == null){
				return null;
			}
			MenuItemVO menuItem = UtilsBusiness.copyObject(MenuItemVO.class, menuItemPojo);
			return menuItem;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuItemById/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuItemById/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuItemsByMenu(co.com.directv.sdii.model.vo.MenuVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<MenuItemVO> getMenuItemsByMenu(MenuVO menu)
			throws BusinessException {
    	log.debug("== Inicia getMenuItemsByMenu/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(menu, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menu.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Menu menuPojo = UtilsBusiness.copyObject(Menu.class, menu);
			
			List<MenuItem> menuItemsPojo = menuItemDao.getMenuItemsByMenu(menuPojo);
			List<MenuItemVO> menuItems = UtilsBusiness.convertList(menuItemsPojo, MenuItemVO.class);
			return menuItems;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuItemsByMenu/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuItemsByMenu/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenusByFather(java.lang.Long)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<MenuVO> getMenusByFather(Long fatherId)
			throws BusinessException {
    	log.debug("== Inicia getMenusByFather/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(fatherId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			List<Menu> menusPojo = menuDao.getMenusByFather(fatherId);
			List<MenuVO> menus = UtilsBusiness.convertList(menusPojo, MenuVO.class);
			return menus;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenusByFather/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenusByFather/SecurityBusinessBean ==");
		}
	}

	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getProfileByRol(co.com.directv.sdii.model.vo.RolVO)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProfileVO> getProfileByRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia getProfileByRol/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            return UtilsBusiness.convertList(daoProfile.getProfileByIDRol(rol.getId()), ProfileVO.class);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getProfileByRol/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProfileByRol/SecurityBusinessBean ==");
        }
	}
	
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getProfileByMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<ProfileVO> getProfileByMenuItem(MenuItemVO menuItem) throws BusinessException {
		log.debug("== Inicia getProfileByMenuItem/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(menuItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(menuItem.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
            return UtilsBusiness.convertList(daoProfile.getProfileByIDMenuItem(menuItem.getId()), ProfileVO.class);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getProfileByMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProfileByMenuItem/SecurityBusinessBean ==");
        }
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getProfileByRolAndMenuItem(co.com.directv.sdii.model.vo.RolVO, co.com.directv.sdii.model.vo.MenuItemVO)
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ProfileVO getProfileByRolAndMenuItem(RolVO rol,MenuItemVO menuItem) throws BusinessException{
		log.debug("== Inicia getProfileByRolAndMenuItem/SecurityBusinessBean ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(menuItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	UtilsBusiness.assertNotNull(menuItem.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	Profile profilePojo = daoProfile.getProfileByIDMenuItemAndIDRol(rol.getId(), menuItem.getId());
        	if(profilePojo == null){
        		return null;
        	}
            return UtilsBusiness.copyObject(ProfileVO.class, profilePojo);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getProfileByRolAndMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getProfileByRolAndMenuItem/SecurityBusinessBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public RolVO getRolByCode(String code) throws BusinessException {
		log.debug("== Inicia getRolByCode/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(code, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	Rol rolPojo = rolDAO.getRolByCode(code);
        	if(rolPojo==null){
        		return null;
        	}
        	return UtilsBusiness.copyObject(RolVO.class,rolPojo );
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getRolByCode/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolByCode/SecurityBusinessBean ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public RolVO getRolByName(String rolName) throws BusinessException {
		log.debug("== Inicia getRolByCode/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(rolName, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	Rol rolPojo = rolDAO.getRolByName(rolName);
        	if(rolPojo==null){
        		return null;
        	}
        	return UtilsBusiness.copyObject(RolVO.class, rolPojo);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getRolByName/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolByName/SecurityBusinessBean ==");
        }
	}
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<RolVO> getRolByNameOrCode(String rolName, String rolCode) throws BusinessException {
		log.debug("== Inicia getRolByNameOrCode/createRol ==");
        try {
        	if(rolName==null && rolCode==null || "".equals(rolName) && "".equals(rolCode)){
        		throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	}
        	List<Rol> rolPojo =rolDAO.getRolByNameOrCode(rolName, rolCode);
        	if(rolPojo==null){
        		return null;
        	}
        	return UtilsBusiness.convertList(rolPojo, RolVO.class);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n getRolByNameOrCode/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolByNameOrCode/SecurityBusinessBean ==");
        }
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public RolVO getRolById(Long id) throws BusinessException {
		log.debug("== Inicia getRolById/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	Rol rolPojo = rolDAO.getRolById(id);
        	if(rolPojo==null){
        		return null;
        	}
        	return UtilsBusiness.copyObject(RolVO.class, rolPojo);
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operaciÃ³n createProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolById/SecurityBusinessBean ==");
        }
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUserById(java.lang.Long)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public UserVO getUserById(Long id) throws BusinessException {
		log.debug("== Inicia getUserById/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User userPojo = userDao.getUserById(id);
			if(userPojo == null){
				return null;
			}
			
			UserVO user = UtilsBusiness.copyObject(UserVO.class, userPojo);
			return user;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUserById/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUserById/SecurityBusinessBean ==");
		}
	}
    
    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUserByIdAndCountry(java.lang.Long, java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public UserVO getUserByIdAndCountry(Long id, Long countryId) throws BusinessException {
		log.debug("== Inicia getUserByIdAndCountry/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(id, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			if(id != null && id <= 0)
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
		
			User sample = new User();
			sample.setId( id );
			Country country = new Country();
			country.setId( countryId );
			sample.setCountry( country );
			List<User> userList = userDao.getUserBySample(sample);
			if(userList == null || userList.isEmpty()){
				return null;
			}
			return fillUserVO(userList.get(0));
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUserByIdAndCountry/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUserByIdAndCountry/SecurityBusinessBean ==");
		}
	}
    
    /**
     * 
     * Metodo: Le da valor a los atributos necesarios del userVO
     * @param user
     * @return UserVO objeto con informacion necesaria para capa de presentacion 
     * @throws BusinessException <tipo> <descripcion>
     * @author jnova
     */
    private UserVO fillUserVO(User user) throws BusinessException{
    	log.debug("== Inicia fillUserVO/SecurityBusinessBean ==");
		try {
			UserVO vo = new UserVO();
			vo.setId( user.getId() );
			vo.setRol( user.getRol() );
			vo.setLogin( user.getLogin() );
			vo.setSdiiTimeZone(new SdiiTimeZone());
			vo.getSdiiTimeZone().setTimeZoneKey(user.getSdiiTimeZone().getTimeZoneKey());
			if( user.getDealer() != null ){
				DealerVO userDealer = new DealerVO();
				userDealer.setId( user.getDealer().getId() );				
				if( user.getDealer().getDealer() != null ){
					DealerVO branchDealer = new DealerVO();
					branchDealer.setId( user.getDealer().getDealer().getId() );
					userDealer.setDealer( branchDealer );
				}
				vo.setDealer( userDealer );
			}
			//Obtener los menu item de alarmas
			MenuItem menuAlarmStock = menuItemDao.getMenuItemByCode(CodesBusinessEntityEnum.MENU_ITEM_ALARM_STOCK.getCodeEntity());
			MenuItem menuAlarmReference = menuItemDao.getMenuItemByCode(CodesBusinessEntityEnum.MENU_ITEM_ALARM_REFERENCE.getCodeEntity());
			Profile profileAlarmStock = daoProfile.getProfileByIDMenuItemAndIDRol(vo.getRol().getId(), menuAlarmStock.getId());
			if(profileAlarmStock == null){
				vo.setShowPageAlarmStock(Boolean.FALSE);
			}else{
				vo.setShowPageAlarmStock(Boolean.TRUE);
			}
			
			Profile profileAlarmReference =daoProfile.getProfileByIDMenuItemAndIDRol(vo.getRol().getId(), menuAlarmReference.getId());
			if(profileAlarmReference == null){
				vo.setShowPageAlarmReference(Boolean.FALSE);
			}else{
				vo.setShowPageAlarmReference(Boolean.TRUE);
			}
			
			return vo;
		}catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion fillUserVO/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina fillUserVO/SecurityBusinessBean ==");
		}
    }

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUserByLoginAndDealer(java.lang.String, co.com.directv.sdii.model.vo.DealerVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public UserVO getUserByLoginAndDealer(String login, DealerVO dealer)
			throws BusinessException {
		log.debug("== Inicia getUserByLoginAndDealer/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(login, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User sampleUser = new User();
			sampleUser.setLogin(login);
			sampleUser.setDealer(dealer);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			
			if(!userPojo.isEmpty()){
				UserVO user = UtilsBusiness.copyObject(UserVO.class, userPojo.get(0));
				return user;
			}
			return null;
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUserByLoginAndDealer/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUserByLoginAndDealer/SecurityBusinessBean ==");
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByDealer(co.com.directv.sdii.model.vo.DealerVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<UserVO> getUsersByDealer(DealerVO dealer)
			throws BusinessException {
		log.debug("== Inicia getUsersByDealer/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User sampleUser = new User();
			sampleUser.setDealer(dealer);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUsersByDealer/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByDealer/SecurityBusinessBean ==");
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByLogin(java.lang.String)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<UserVO> getUsersByLogin(String login) throws BusinessException {
		log.debug("== Inicia getUsersByLogin/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(login, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User sampleUser = new User();
			sampleUser.setLogin(login);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUsersByLogin/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByLogin/SecurityBusinessBean ==");
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByRol(co.com.directv.sdii.model.vo.RolVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<UserVO> getUsersByRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia getUsersByRol/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User sampleUser = new User();
			sampleUser.setRol(rol);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			if(userPojo== null || userPojo.size()==0){
				return new ArrayList<UserVO>();
			}
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUsersByRol/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByRol/SecurityBusinessBean ==");
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByRolAndDealer(co.com.directv.sdii.model.vo.RolVO, co.com.directv.sdii.model.vo.DealerVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<UserVO> getUsersByRolAndDealer(RolVO rol, DealerVO dealer)
			throws BusinessException {
		log.debug("== Inicia getUsersByRolAndDealer/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dealer, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(dealer.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			User sampleUser = new User();
			sampleUser.setRol(rol);
			sampleUser.setDealer(dealer);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			if(userPojo== null || userPojo.size()==0){
				return new ArrayList<UserVO>();
			}
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUsersByRolAndDealer/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByRolAndDealer/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#updateMenu(co.com.directv.sdii.model.vo.MenuVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateMenu(MenuVO menu) throws BusinessException {
    	log.debug("== Inicia updateMenu/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(menu, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menu.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if(!BusinessRuleValidationManager.getInstance().isValid(menu)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			Menu menuPojo = UtilsBusiness.copyObject(Menu.class, menu);
			menuDao.updateMenu(menuPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion updateMenu/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina updateMenu/SecurityBusinessBean ==");
		}
	}

    /* 
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#updateMenuItem(co.com.directv.sdii.model.vo.MenuItemVO)
     * @author jjimenezh
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateMenuItem(MenuItemVO menuItem) throws BusinessException {
    	log.debug("== Inicia updateMenuItem/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(menuItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menuItem.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if(!BusinessRuleValidationManager.getInstance().isValid(menuItem)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			
			MenuItem menuItemPojo = UtilsBusiness.copyObject(MenuItem.class, menuItem);
			menuItemDao.updateMenuItem(menuItemPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion updateMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina updateMenuItem/SecurityBusinessBean ==");
		}
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateRol(RolVO rol) throws BusinessException {
		log.debug("== Inicia getRolById/createRol ==");
        try {
        	UtilsBusiness.assertNotNull(rol, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(rol.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
        	rolDAO.updateRol(UtilsBusiness.copyObject(Rol.class, rol));
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaciÃ³n createProfiles/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolById/SecurityBusinessBean ==");
        }
		
	}

	/* 
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#updateUser(co.com.directv.sdii.model.vo.UserVO)
	 * @author jjimenezh
	 */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateUser(UserVO user) throws BusinessException {
		log.debug("== Inicia updateUser/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(user, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(),ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(user.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			if(!BusinessRuleValidationManager.getInstance().isValid(user)){
				throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			}
			if(!UtilsBusiness.isEmail(user.getEmail())){
				throw new BusinessException(ErrorBusinessMessages.INVALID_EMAIL.getCode(), ErrorBusinessMessages.INVALID_EMAIL.getMessage());
			}
			validateUser(user);
			
			User userPojo = UtilsBusiness.copyObject(User.class, user);
			userDao.updateUser(userPojo);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion updateUser/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina updateUser/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByLoginOrDocNumber(java.lang.String, java.lang.String)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UserVO> getUsersByLoginOrDocNumber(String login,
			String docNumber) throws BusinessException{
		log.debug("== Inicia getUsersByLoginOrDocNumber/SecurityBusinessBean ==");
		try {
			
			User sampleUser = new User();
			sampleUser.setLogin(login);
			sampleUser.setIdNumber(docNumber);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUsersByDealer/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByLoginOrDocNumber/SecurityBusinessBean ==");
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UserVO authenticateLDAPUser(String userName, String password, Long countryId) throws BusinessException {
		
		log.debug("== Inicia authenticateLDAPUser/SecurityBusinessBean ==");
		try {			
			//Autenticacion con el framework SpringLdap
			
			
			User user = new User();
			Country userCountry = new Country();
			userCountry.setId(countryId);
			user.setCountry(userCountry);
			user.setLogin(userName);
			
			List<User> users = userDao.getUserBySample(user);
			if(users.isEmpty()){
				log.debug("Se lograr autenticar en el directorio activo, pero no existe en SDII el login " + userName + " en el pais con id: " + countryId + " por ello se niega la autenticacion");
				return null;
			}
			user = users.get(0);
			
			//DANI-ÑAPA
			//this.ldapAuthenticate(user.getEmail(), password, countryId);

			if(user.getIsActive().equals("0")){
				log.info("Se lograr autenticar en el directorio activo, pero el usuario en SDII de login " + userName + " en el pais con id: " + countryId + " no se encuentra activo");
				throw new BusinessException(ErrorBusinessMessages.GENERAL_BL192.getCode(), ErrorBusinessMessages.GENERAL_BL192.getMessage());
			}
			UserVO returnUser = UtilsBusiness.copyObject(UserVO.class, user);
			Rol userRol = returnUser.getRol();
			RolVO roleVo = UtilsBusiness.copyObject(RolVO.class, userRol);
			List<MenuVO> menusByRole = this.getMenusByRole(roleVo);
			
			for (MenuVO menuVO : menusByRole) {
				menuVO.setMenuItems(null);
			}
			
			returnUser.setUserMenus(menusByRole);
			return returnUser;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion authenticateLDAPUser/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina authenticateLDAPUser/SecurityBusinessBean ==");
		}
	}
	
	/**
	 * 
	 * Metodo: Autenticacion con LDAP
	 * @param userName
	 * @param password
	 * @param countryId
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException
	 * @throws BusinessException
	 * @throws SpringLdapSecurityException
	 * @throws SpringLdapException
	 * @throws SpringLdapComunicationException
	 * @throws SpringLdapAuthenticationException <tipo> <descripcion>
	 * @author jalopez
	 * @throws SpringLdapComunicationException 
	 * @throws SpringLdapSecurityException 
	 * @throws SpringLdapException 
	 * @throws SpringLdapAuthenticationException 
	 */
	public void ldapAuthenticate(String userName, String password, Long countryId) throws DAOServiceException, DAOSQLException, PropertiesException, BusinessException, SpringLdapException, SpringLdapSecurityException, SpringLdapComunicationException, SpringLdapAuthenticationException{
		
		PersonVO personVO = null;
		AttributeTypeDTO attributeDTO = null;
		
		//Se consultan los poarametros del sistema necesarios para la autenticacion
		SystemParameter serverIpParam = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_SERVER_IP.getCodeEntity(), countryId);
		

		if (serverIpParam == null) {
			log.fatal("No se ha encontrado el parametro del sistema para la ip del servidor LDAP");
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter serverPortParam = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_SERVER_PORT.getCodeEntity(), countryId);
		if (serverPortParam == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el puerto del servidor LDAP");
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapSuffixParam = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_SUFFIX.getCodeEntity(), countryId);
		if (ldapSuffixParam == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el sufijo del servidor LDAP");
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapstartDirectory = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_START_DIRECTORY.getCodeEntity(), countryId);
		if (ldapstartDirectory == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el codigo del parÃ¡metro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_START_DIRECTORY.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapUserBaseDn = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_USER_BASEDN.getCodeEntity(), countryId);
		if (ldapUserBaseDn == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el c0digo del parametro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_USER_BASEDN.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapUserAdmin = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR.getCodeEntity(), countryId);
		if (ldapUserAdmin == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el codigo del parametro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapUserAdminPasswd = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR_PASSWD.getCodeEntity(), countryId);
		if (ldapUserAdminPasswd == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el codigo del parametro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_USER_ADMINISTRATOR_PASSWD.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapsearchBase = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_SEARCH_BASE.getCodeEntity(), countryId);
		if (ldapsearchBase == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el codigo del parametro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_SEARCH_BASE.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		SystemParameter ldapAuthentication = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_AUTHENTICATION.getCodeEntity(), countryId);
		if (ldapAuthentication == null) {
			log.fatal("No se ha encontrado el parametro del sistema para el directorio de entrada al servidor LDAP el codigo del parametro es el: " + CodesBusinessEntityEnum.SYSTEM_PARAM_LDAP_AUTHENTICATION.getCodeEntity());
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		
		Country country = countryDao.getCountriesByID(countryId);
		
		//Variables con los datos para la autenticacion en el LDAP
		String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
		String authentication = "simple";
		String server = serverIpParam.getValue();
		String port = serverPortParam.getValue();
		String baseDn = ldapSuffixParam.getValue();
		String userBaseDn = ldapUserBaseDn.getValue();		
		String userDn = userName;
		String startDirectory = ldapstartDirectory.getValue();
		String ldapSearchBase = ldapsearchBase.getValue();
		String ldapType = ldapAuthentication.getValue();
		boolean connectionProperties = false;
		boolean isLDAP = true;
		
		//Si el la autenticacion es contra un Directorio Activo
		if (ldapType.equalsIgnoreCase( CodesBusinessEntityEnum.BOOLEAN_FALSE.getCodeEntity() )) {
			//Se elimina el dominio del login del usuario puesto que es un Directorio Activo
			int index = userName.indexOf('\\');
			if ( index > 0 ) {
				userDn = userName.substring( index + 1 );
			}
			isLDAP = false;
		}
		
		//Si el LDP no requiere que en la autenticacion se envie todo el baseDN, el parametro del Sistema contiene el valor de NA
		if (userBaseDn.equalsIgnoreCase("NA")) {
			userBaseDn = "";
		}
		//Si el LDP no requiere que se adicione en la autenticacion en el sufijo de nombre, por ejemplo cn=admin
		if (startDirectory.equalsIgnoreCase("NA")) {
			startDirectory = "";
		}
		
		if (baseDn.equalsIgnoreCase("NA")) {
			baseDn = "";
		}
		
		//URL de conexion con el LDAP
		StringBuffer providerUrl = new StringBuffer();	
		providerUrl.append("ldap://");
		providerUrl.append(server);
		providerUrl.append(":");
		providerUrl.append(port);
		
		//Se cargan los datos para la comunicacion con el servicio de conexion con el LDAP 
		//para realizar la busqueda del usuario que intenta autenticarse
		personVO = new PersonVO();
		attributeDTO = new AttributeTypeDTO();
		attributeDTO.setConnectionProperties(connectionProperties);			
		personVO.setUserName( userDn );
		personVO.setSecurityProviderUrl( providerUrl.toString() );
		personVO.setSecuritySuffixDN( startDirectory );
		personVO.setSecurityBaseDN( baseDn );		
		personVO.setSecurityUserDN( ldapUserAdmin.getValue() );
		personVO.setSecurityPasswordDN( ldapUserAdminPasswd.getValue() );		
		personVO.setSearchBaseUserDN( ldapSearchBase );
		if ( isLDAP ) { //Si la autenticacion es contra un LDAP se envian estos parametros
			personVO.setSecurityUserBaseDN( userBaseDn );			
		}
		
		//Consumo del servcio de autenticacion y busqueda del LDAP
	 	SpringLdapServices ldapService = new SpringLdapServices(personVO, connectionProperties,country.getCountryCode());	 	
	 	ContactoDTO contactDTO = ldapService.getContactByLogin( personVO, attributeDTO );
	 		 	
	 	//Se valida que el usuario exista en la BASE del LDAP indicada
	 	if ( contactDTO == null ) {
	 		Object[] params = new Object[2];
			params[0] = providerUrl.toString();
			params[1] = userDn;
			log.fatal("No fue posible recuperar la informacion del uario " + userDn + " en el LDAP, verificar con el administrador que este usuario este creado y configurado para autenticarse en HSP+.");
	 		throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(), ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( params ));
	 	}
	 	
	 	//Se valida que el servicio retorne informacion de la raiz donde se encuentra creado el usuario en el LDAP
	 	if ( contactDTO.getDistinguishedName() == null ) {
	 		Object[] params = new Object[2];
			params[0] = providerUrl.toString();
			params[1] = userDn;
			log.fatal("No se pudo obtener informacion del parametro DistinguishedName del LDAP, verificar que el archivo dtv\\sdii\\properties\\AttributeTypes.properties este mapeado el parametro spring_ldap_distinguishedName");
	 		throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(), ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( params ));
	 	}
	 	
	 	//Se valida la raiz del parametro contengo la estructura requerida
	 	int index = contactDTO.getDistinguishedName().indexOf(',');
	 	if ( index <= 0 ) {
	 		Object[] params = new Object[2];
			params[0] = providerUrl.toString();
			params[1] = userDn;
	 		log.fatal("No se pudo obtener informacion del parametro DistinguishedName del LDAP, verificar que en LDAP este parametro tenga la estructura requerida, Ej., cn=miusuario,OU=Directv,DC=Colombia,DC=com");
	 		throw new BusinessException(ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getCode(), ErrorBusinessMessages.LDAP_ERROR_AUTHENTICATOR.getMessageCode( params ));
	 	}
	 	
	 	//Se obtiene la base de autenticacion del usurio.
	 	String securityBaseDN = contactDTO.getDistinguishedName().substring( index + 1 );
			 	
	 	//se cargan los datos para autenticar el usuario que intenta loguearse al sistema
	 	personVO = new PersonVO();
		personVO.setSecurityInitialContext( contextFactory );
		personVO.setSecurityAuthenticationType( authentication );
		personVO.setSecurityProviderUrl( providerUrl.toString() );
		personVO.setSecuritySuffixDN( startDirectory );
		personVO.setSecurityUserDN( userName );
		personVO.setSecurityPasswordDN( password );		
		personVO.setSecurityBaseDN( securityBaseDN );
		if( isLDAP ) { //Si la autenticacion es contra un LDAP se envia este parametro
			personVO.setSecurityUserBaseDN( contactDTO.getDistinguishedName() );
			personVO.setAuthenticateBaseUserDN(true);
		}
		
		//Consumo del servicio de autenticacion
		ActiveDirectoryAuthenticatorServices service = new ActiveDirectoryAuthenticatorServices();		
		service.authenticate(personVO, connectionProperties);			
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#authorizeMenuItem(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean authorizeMenuItem(String menuItemCode, String roleCode)	throws BusinessException {
		
		log.debug("== Inicia authorizeMenuItem/SecurityBusinessBean ==");
		try {
			MenuItem menuItem = menuItemDao.getMenuItemByCode(menuItemCode);
			UtilsBusiness.assertNotNull(menuItem, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			Rol role = rolDAO.getRolByCode(roleCode);
			UtilsBusiness.assertNotNull(role, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			Profile profile = daoProfile.getProfileByIDMenuItemAndIDRol(role.getId(), menuItem.getId());
			
			if ( profile == null ) {
				return false;
			}
			return true;
			
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion authorizeMenuItem/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina authorizeMenuItem/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenuItemsByMenuAndRole(co.com.directv.sdii.model.vo.MenuVO, co.com.directv.sdii.model.vo.RolVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MenuItemVO> getMenuItemsByMenuAndRole(MenuVO menu, RolVO role)
			throws BusinessException {
		log.debug("== Inicia getMenuItemsByMenuAndRole/SecurityBusinessBean ==");
		try {
			
			UtilsBusiness.assertNotNull(role, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(role.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menu, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(menu.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			List<Profile> profiles = daoProfile.getProfileByIDRol(role.getId());

			List<MenuItem> menuItemsPojo = new ArrayList<MenuItem>();
			
			for (Profile profile : profiles) {
				if(profile.getId().getMenuItem().getMenuId().longValue() == menu.getId().longValue()){
					menuItemsPojo.add(profile.getId().getMenuItem());
				}
			}
			List<MenuItemVO> menuItems = UtilsBusiness.convertList(menuItemsPojo, MenuItemVO.class);
			return menuItems;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenuItemsByMenuAndRole/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenuItemsByMenuAndRole/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getMenusByRole(co.com.directv.sdii.model.vo.RolVO)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<MenuVO> getMenusByRole(RolVO role) throws BusinessException {
		log.debug("== Inicia getMenusByRole/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(role, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			UtilsBusiness.assertNotNull(role.getId(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			List<Profile> profiles = daoProfile.getProfileByIDRol(role.getId());
			Map<Long, Menu> menusById = new HashMap<Long, Menu>();
			Long menuId = null;
			Menu menu;
			for (Profile profile : profiles) {
				menuId = profile.getId().getMenuItem().getMenuId();
				menu = menusById.get(menuId);
				if(menu == null){
					menu = menuDao.getMenuById(menuId);
					menusById.put(menuId, menu);
				}
			}
			List<Menu> menusPojo = new ArrayList<Menu>();
			menusPojo.addAll(menusById.values());
			Collections.sort(menusPojo);
			List<MenuVO> menus = UtilsBusiness.convertList(menusPojo, MenuVO.class);
			return menus;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getMenusByRole/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getMenusByRole/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getAllRoleTypes()
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RoleTypeVO> getAllRoleTypes() throws BusinessException {
		log.debug("== Inicia getAllRoleTypes/SecurityBusinessBean ==");
		try {
			List<RoleType> roleTypesPojo = roleTypeDao.getAllRoleTypes();
			List<RoleTypeVO> roleTypes = UtilsBusiness.convertList(roleTypesPojo, RoleTypeVO.class);
			return roleTypes;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getAllRoleTypes/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getAllRoleTypes/SecurityBusinessBean ==");
		}
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getRoleTypesByRoleTypeCode(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RoleTypeVO> getRoleTypesByRoleTypeCode(String roleTypeCode)
			throws BusinessException {
		log.debug("== Inicia getRoleTypesByRoleTypeCode/SecurityBusinessBean ==");
		try {
			List<RoleType> roleTypesPojo = roleTypeDao.getAllRoleTypes();
			
			if(roleTypeCode.equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_DTV_ADMIN.getCodeEntity())){
				//Se remueve el root
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity());
			}else if(roleTypeCode.equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_DEALER_ADMIN.getCodeEntity())){
				//Se remueve el root
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity());
				//Se remueve el administrador DTV
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_DTV_ADMIN.getCodeEntity());
				//Se remueve el administrador Dealer
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_DEALER_ADMIN.getCodeEntity());
				//Se remueve el administrador Inventarios
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_STOCK_ADMIN.getCodeEntity());
				//Se remueve el reportes
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_REPORTS.getCodeEntity());
				//Se remueve el role KPI's
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_KPI.getCodeEntity());
				//Se remueve el rol analista logistica DTV
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_DEALER_LOGISTICS_ANALYST.getCodeEntity());
				//Se remueve el rol operador logÃ­stico
				removeRoleType(roleTypesPojo, CodesBusinessEntityEnum.ROLE_TYPE_LOGISTICS_OPERATOR.getCodeEntity());
				
			//Todos los demÃ¡s roles no tienen permiso de creaciÃ³n de usuarios, a excepcion del superAdministrador
			}else if(!roleTypeCode.equalsIgnoreCase(CodesBusinessEntityEnum.ROLE_TYPE_ROOT.getCodeEntity())) {
				roleTypesPojo = new ArrayList<RoleType>();
			}
			List<RoleTypeVO> roleTypes = UtilsBusiness.convertList(roleTypesPojo, RoleTypeVO.class);
			return roleTypes;
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getRoleTypesByRoleTypeCode/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getRoleTypesByRoleTypeCode/SecurityBusinessBean ==");
		}
	}

	private void removeRoleType(List<RoleType> roleTypesPojo, final String codeEntity) {
		CollectionUtils.filter(roleTypesPojo, new Predicate() {
			
			@Override
			public boolean evaluate(Object roleType) {
				if(roleType instanceof RoleType){
					if(	((RoleType)roleType).getRoleTypeCode().equalsIgnoreCase(codeEntity)){
						return false;
					}
				}
				return true;
			}
		});
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getRolesByRoleTypeId(java.lang.Long)
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<RolVO> getRolesByRoleTypeId(Long roleTypeId)
			throws BusinessException {
		log.debug("== Inicia getRolesByRoleTypeId/SecurityBusinessBean ==");
        try {
        	List<Rol> rolesPojo = this.rolDAO.getRolesByRoleTypeId(roleTypeId);
        	if(rolesPojo == null || rolesPojo.size()==0){
        		return new ArrayList<RolVO>();
        	}
            return UtilsBusiness.convertList(rolesPojo, RolVO.class);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operaciÃ³n getRolesByRoleTypeId/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolesByRoleTypeId/SecurityBusinessBean ==");
        }	
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUsersByLoginOrDocNumberAndRol(java.lang.String, java.lang.String, co.com.directv.sdii.model.vo.RolVO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<UserVO> getUsersByLoginOrDocNumberAndRol(String login,
			String docNumber, RolVO rol,Long countryId) throws BusinessException {
		log.debug("== Inicia getUsersByLoginOrDocNumberAndRol/SecurityBusinessBean ==");
		try {
			
			User sampleUser = new User();
			sampleUser.setLogin(login);
			sampleUser.setIdNumber(docNumber);
			sampleUser.setRol(rol);
			Country country = new Country();
			country.setId(countryId);
			sampleUser.setCountry(country);
			
			List<User> userPojo = userDao.getUserBySample(sampleUser);
			
			List<UserVO> users = UtilsBusiness.convertList(userPojo, UserVO.class);
			return users;
		} catch (Throwable ex) {
			log.error("== Error getUsersByLoginOrDocNumberAndRol/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUsersByLoginOrDocNumberAndRol/SecurityBusinessBean ==");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getUserByRoleTypeCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<UserVO> getUsersByRoleTypeCodeAndCountryId(String roleTypeCode,
			Long countryId) throws BusinessException {
		log.debug("== Inicia getRolesByRoleTypeId/SecurityBusinessBean ==");
        try {
        	List<User> usersPojo = this.userDao.getUsersByRoleTypeCodeAndCountryId(roleTypeCode, countryId);
        	if(usersPojo == null || usersPojo.size()==0){
        		return new ArrayList<UserVO>();
        	}
            return UtilsBusiness.convertList(usersPojo, UserVO.class);
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getRolesByRoleTypeId/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getRolesByRoleTypeId/SecurityBusinessBean ==");
        }	
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.security.SecurityBusinessBeanLocal#getIBSAdminUserByCountryId(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public UserVO getIBSAdminUserByCountryId(Long countryId) throws BusinessException {
		log.debug("== Inicia getUserByIdAndCountry/SecurityBusinessBean ==");
		try {
			UtilsBusiness.assertNotNull(countryId, ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode(), ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getMessage());
			
			SystemParameter woOwnerUserParam = sysParamDao.getSysParamByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity(), countryId);
			UtilsBusiness.assertNotNull(woOwnerUserParam, ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() + "No existe en sdii el parÃ¡metro de sistema con cÃ³digo \"" + CodesBusinessEntityEnum.SYSTEM_PARAM_WORK_ORDER_OWNER_USER_LOGIN.getCodeEntity() + "\" que identifica el usuario de creaciÃ³n de las work orders el paÃ­s coin id " + countryId + " de la work order" );
			String userId = woOwnerUserParam.getValue();
			
			Country country = countryDao.getCountriesByID(countryId);
			
			User user = new User();
			user.setLogin(userId);
			user.setCountry( country );
			List<User> users = userDao.getUserBySample(user);
			
			if (users.isEmpty()) {
				log.error("No se encontrÃ³ el usuario por el login reportado desde ibs: " + userId + " en el paÃ­s:" + country.getCountryName());
				throw new BusinessException(ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getCode(), ErrorBusinessMessages.IBS_CORE_PARAM_NULL.getMessage());
			}
			user = users.get(0);
			return UtilsBusiness.copyObject(UserVO.class, user);
		} catch (Throwable ex) {
			log.error("== Error al tratar de ejecutar la operacion getUserByIdAndCountry/SecurityBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
			log.debug("== Termina getUserByIdAndCountry/SecurityBusinessBean ==");
		}
	}
}

package co.com.directv.sdii.ejb.business.config.impl;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.SystemParameterBusinessBeanLocal;
import co.com.directv.sdii.ejb.business.config.SystemParameterBusinessBeanRemote;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * Esta clase implementa las operaciones de negocio para
 * las operaciones de la interfaz ParameterTypeBusinessBeanLocal
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 */
@Stateless(name = "SystemParameterBusinessBeanLocal")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local({SystemParameterBusinessBeanLocal.class})
@Remote({SystemParameterBusinessBeanRemote.class})
public class SystemParameterBusinessBean extends BusinessBase implements SystemParameterBusinessBeanLocal,SystemParameterBusinessBeanRemote {

	private final static Logger log = UtilsBusiness.getLog4J(SystemParameterBusinessBean.class);
	
	@EJB(name = "SystemParameterDAOLocal", beanInterface = SystemParameterDAOLocal.class)
	private SystemParameterDAOLocal daoSystemParameter;
	
	/**
	 * 
	 * Metodo: Retorna el id de una entidad
	 * filtrando por el codigo.
	 * @param className String
	 * @param codeEntity String
	 * @param propertyName String
	 * @return Long
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 * @throws BusinessException 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long getIdEntityByCodeEntity(String className,String codeEntity, String propertyName)throws BusinessException {
		log.debug("== Inicia getIdEntityByCodeEntity/SystemParameterBusinessBean ==");
        try {
            
        	return daoSystemParameter.getIdEntityByCodeEntity(className,codeEntity,propertyName);
        	
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion SystemParameterBusinessBean/getIdEntityByCodeEntity");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getIdEntityByCodeEntity/SystemParameterBusinessBean ==");
        }
	}
	
	/**
	 * Devuelve el valor que se utilizara para saber que idioma de las properties se tiene que usar.
	 * @param parameterCode
	 * @param countryCode
	 * @throws BusinessException <tipo> <descripcion>
	 */
	public SystemParameter getSysParamByCodeAndCountryCode(String parameterCode, String countryCode) throws BusinessException{
		log.debug("== Inicia getSysParamByCodeAndCountryCode/SystemParameterBusinessBean ==");
        try {
            return daoSystemParameter.getSysParamByCodeAndCountryCode(parameterCode, countryCode);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion SystemParameterBusinessBean/getSysParamByCodeAndCountryCode");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamByCodeAndCountryCode/SystemParameterBusinessBean ==");
        }	
	}
	
	/**
	 * Devuelve el valor que se utilizara para saber que idioma de las properties se tiene que usar.
	 * @param parameterCode
	 * @throws BusinessException <tipo> <descripcion>
	 */
	public SystemParameter getSysParamsByCodeAndCountryIdNull(String parameterCode) throws BusinessException{
		log.debug("== Inicia getSysParamsByCodeAndCountryIdNull/SystemParameterBusinessBean ==");
        try {
            return daoSystemParameter.getSysParamsByCodeAndCountryIdNull(parameterCode);
        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion SystemParameterBusinessBean/getSysParamsByCodeAndCountryIdNull");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getSysParamsByCodeAndCountryIdNull/SystemParameterBusinessBean ==");
        }	
	}
	
	
	
	
	
	
}

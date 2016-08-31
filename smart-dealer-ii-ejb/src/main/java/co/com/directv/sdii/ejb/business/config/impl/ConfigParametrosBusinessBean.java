package co.com.directv.sdii.ejb.business.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal;
import co.com.directv.sdii.ejb.business.dealers.impl.VehiclesCRUDBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.model.vo.SystemParameterVO;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;
import co.com.directv.sdii.rules.BusinessRuleValidationManager;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del m�dulo de Configuraci�n de Parametros.
 *
 * Caso de Uso CFG - 02 - Gestionar Parametros del Sistema
 *
 * Fecha de Creaci�n: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigParametrosBusinessLocal",mappedName="ejb/ConfigParametrosBusinessLocal")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfigParametrosBusinessBean extends BusinessBase implements ConfigParametrosBusinessLocal {

    @EJB(name="SystemParameterDAOLocal",beanInterface=SystemParameterDAOLocal.class)
    private SystemParameterDAOLocal systemParameterDAO;

    private final static Logger log = UtilsBusiness.getLog4J(VehiclesCRUDBean.class);
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public SystemParameterVO getSystemParameterByID(Long id) throws BusinessException {
        log.debug("== Inicia getSystemParameterByID/ConfigJornadasBusinessBean ==");
        try {
            if (id == null) {
                log.debug("== Error Parametro id con valor establecido en null ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            SystemParameter sp = this.systemParameterDAO.getSystemParameterByID(id);
            if (sp == null) {
                return null;
            }

            SystemParameterVO spVO = UtilsBusiness.copyObject(SystemParameterVO.class, sp);
            return spVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/getSystemParameterByID");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getSystemParameterByID/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<SystemParameterVO> getAll() throws BusinessException {
        log.debug("== Inicia getAll/ConfigJornadasBusinessBean ==");
        try {

            List<SystemParameter> sp = this.systemParameterDAO.getAll();
            if (sp == null) {
                return null;
            }

            List<SystemParameterVO> spVO = UtilsBusiness.convertList(sp, SystemParameterVO.class);
            return spVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/getAll");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAll/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createSystemParameter(SystemParameterVO obj) throws BusinessException {
        log.debug("== Inicia createSystemParameter/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.error("== Parametro obj no puede ser nulo ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validaci�n de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }

            String parameterType = obj.getParameterType().getId().toString();
            String paramValue = obj.getValue();
            
            if (parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_NUMERIC.getCodeEntity())) {
                if (!UtilsBusiness.isNumericValue(paramValue)) {
                    log.error("== El parametro tipo de parametro se especific� Numeric, pero el valor del parametro no es numerico. Value: " + paramValue + "==");
                    throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
                }
            }else if (parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_DATE.getCodeEntity())) { //yyyy-mm-dd
            	String dateStr = obj.getValue(); //evalua que el formato del parametro de la fecha este correcto
        		if(dateStr == null || dateStr.length()!=8){
        			throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
        		}
        		dateStr = dateStr.replace('-', '/');
            	try{ // Se valida que venga con el formato correcto
	    			Integer.parseInt(dateStr.substring(0, 4));
	    			Integer.parseInt(dateStr.substring(5, 7));
	    			Integer.parseInt(dateStr.substring(8, 10));
            	}catch (NumberFormatException e) {
            		throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
				}
            }else if(!parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_TIME.getCodeEntity()) // No es de ningun formato estandar? 
            		 && !parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_STRING.getCodeEntity()) 
            		 && !parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_MCAPACIDAD.getCodeEntity())){
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }

            systemParameterDAO.createSystemParameter(UtilsBusiness.copyObject(SystemParameter.class, obj));


        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/createSystemParameter");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina createSystemParameter/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateSystemParameter(SystemParameterVO obj) throws BusinessException {
        log.debug("== Inicia updateSystemParameter/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.error("== Parametro obj no puede ser nulo ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            if (!BusinessRuleValidationManager.getInstance().isValid(obj)) {
                log.error("== Error en la Capa de Negocio debido a una Validaci�n de negocio ==");
                throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
            }

            String parameterType = obj.getParameterType().getId().toString();
            String paramValue = obj.getValue();
            if (parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_NUMERIC.getCodeEntity())) {
                if (!UtilsBusiness.isNumericValue(paramValue)) {
                    log.error("== El tipo de parametro se especific� Numeric, pero el valor del parametro no es numerico. Value: " + paramValue + "==");
                    throw new BusinessException(ErrorBusinessMessages.ERROR_DATA.getCode());
                }
            }else if (parameterType.equals(CodesBusinessEntityEnum.CODIGO_SYSTEM_PARAM_DATE.getCodeEntity())) {
            	if (!UtilsBusiness.isDateValid(paramValue)) {
            		Object[] params = null;
        			params = new Object[1];	
        			params[0] = UtilsBusiness.DATE_FORMAT_DDMMYYYYHHMMSS;
        			throw new BusinessException(ErrorBusinessMessages.ERROR_INVALID_DATE.getCode(), ErrorBusinessMessages.ERROR_INVALID_DATE.getMessage(params));
                }
            }

            systemParameterDAO.updateSystemParameter(UtilsBusiness.copyObject(SystemParameter.class, obj));

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/updateSystemParameter");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina updateSystemParameter/ConfigJornadasBusinessBean ==");
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteSystemParameter(SystemParameterVO obj) throws BusinessException {
        log.debug("== Inicia deleteSystemParameter/ConfigJornadasBusinessBean ==");
        try {
            if (obj == null) {
                log.error("== Parametro obj no puede ser nulo ==");
                throw new BusinessException(ErrorBusinessMessages.PARAMS_NULL_OR_MISSED.getCode());
            }

            systemParameterDAO.deleteSystemParameter(UtilsBusiness.copyObject(SystemParameter.class, obj));


        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/deleteSystemParameter");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina deleteSystemParameter/ConfigJornadasBusinessBean ==");
        }
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal#getAllSystemParametersByCountryId(java.lang.Long)
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SystemParameterVO> getAllSystemParametersByCountryId(
			Long countryId) throws BusinessException {
		log.debug("== Inicia getAllSystemParametersByCountryId/ConfigJornadasBusinessBean ==");
        try {

            List<SystemParameter> sp = this.systemParameterDAO.getAllSystemParametersByCountryId(countryId);
            if (sp == null) {
                return null;
            }

            List<SystemParameterVO> spVO = UtilsBusiness.convertList(sp, SystemParameterVO.class);
            return spVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/getAllSystemParametersByCountryId");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getAllSystemParametersByCountryId/ConfigJornadasBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal#getSystemParameterByCodeAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public SystemParameterVO getSystemParameterByCodeAndCountryId(String code, Long countryId)throws BusinessException {
		log.debug("== Inicia getSystemParameterByCodeAndCountryId/ConfigJornadasBusinessBean ==");
        try {

            SystemParameter sp = this.systemParameterDAO.getSysParamByCodeAndCountryId(code, countryId);
            if (sp == null) {
                throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage() + " No se encontr� el par�metro del sistema con el c�digo: \"" + code + "\" en el pa�s con id: \"" + countryId + "\"");
            }
            SystemParameterVO spVO = UtilsBusiness.copyObject(SystemParameterVO.class, sp);
            return spVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/getSystemParameterByCodeAndCountryId");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getSystemParameterByCodeAndCountryId/ConfigJornadasBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal#getSystemParameterByNameAndCountryId(java.lang.String, java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SystemParameterVO> getSystemParameterByNameAndCountryId(
			String name, Long countryId) throws BusinessException {
		log.debug("== Inicia getSystemParameterByNameAndCountryId/ConfigJornadasBusinessBean ==");
        try {

            List<SystemParameter> sp = this.systemParameterDAO.getSystemParameterByNameAndCountryId(name, countryId);
            if (sp == null) {
                return null;
            }

            List<SystemParameterVO> spVO = UtilsBusiness.convertList(sp, SystemParameterVO.class);
            return spVO;

        } catch (Throwable ex) {
            log.debug("== Error al tratar de ejecutar la operacion ConfigParametrosBusinessBean/getSystemParameterByNameAndCountryId");
            throw super.manageException(ex);
        } finally {
            log.debug("== Termina getSystemParameterByNameAndCountryId/ConfigJornadasBusinessBean ==");
        }
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.ejb.business.config.ConfigParametrosBusinessLocal#getDealerCodeWoutCoverage(java.lang.Long)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public long getDealerCodeWoutCoverage(Long countryId)
			throws BusinessException {
		try {
			SystemParameterVO dealerWoutCoverageParam = getSystemParameterByCodeAndCountryId(CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_WOUT_COVERAGE.getCodeEntity(), countryId);
			UtilsBusiness.assertNotNull(dealerWoutCoverageParam, ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() 
					 + " no se encontr� valor del par�metro del sistema con el c�digo " + CodesBusinessEntityEnum.SYSTEM_PARAM_DEALER_WOUT_COVERAGE.getCodeEntity() + " en el pa�s con id: " + countryId);
			Long dealerWoutCoverageCode = Long.parseLong(dealerWoutCoverageParam.getValue());
			return dealerWoutCoverageCode;
		} catch (PropertiesException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_NOT_FOUND.getMessage() + " no se encontr� la propiedad sdii_CODE_sys_param_dealer_wout_coverage en el archivo de properties CodesBusinessEntity.properties");
		}
	}
}

/**
 * Creado 15/10/2010 11:34:15
 */
package co.com.directv.sdii.ejb.business.dealers;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.CodesBusinessEntityEnum;
import co.com.directv.sdii.common.enumerations.ErrorBusinessMessages;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.IBSWSBase;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.exceptions.PropertiesException;
import co.com.directv.sdii.model.pojo.SystemParameter;
import co.com.directv.sdii.persistence.dao.config.SystemParameterDAOLocal;

/**
 * Ayuda a al cargue de la configuración de los parámetros de los medios de contacto para consumir los servicios de IBS
 * 
 * Fecha de Creación: 15/10/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class MediaContactConfiguratorHelper {

	private final static Logger log = UtilsBusiness.getLog4J(MediaContactConfiguratorHelper.class);
	
	private static final MediaContactConfiguratorHelper mySelf = new MediaContactConfiguratorHelper();
	
	public static MediaContactConfiguratorHelper getInstance(){
		return mySelf;
	}
	
	/**
	 * Metodo: <Descripcion>
	 * @param ibsWSBase
	 * @param countryId
	 * @throws BusinessException
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @throws PropertiesException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public void loadMediaContactCodes(IBSWSBase ibsWSBase, Long countryId, SystemParameterDAOLocal systemParameterDao) throws BusinessException, DAOServiceException, DAOSQLException, PropertiesException{
		String homeCode, workCode, mobileCode;
		SystemParameter sysParam = getSysParam(CodesBusinessEntityEnum.SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_HOME_TYPE.getCodeEntity(), countryId, systemParameterDao);
		homeCode = sysParam.getValue().trim();
		
		sysParam = getSysParam(CodesBusinessEntityEnum.SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_WORK_TYPE.getCodeEntity(), countryId, systemParameterDao);
		workCode = sysParam.getValue().trim();
		
		sysParam = getSysParam(CodesBusinessEntityEnum.SYSTEM_PARAM_TELEPHONE_MEDIA_CONTACT_MOBILE_TYPE.getCodeEntity(), countryId, systemParameterDao);
		mobileCode = sysParam.getValue().trim();
		
		ibsWSBase.setHomeCode(homeCode);
		ibsWSBase.setMobileCode(mobileCode);
		ibsWSBase.setWorkCode(workCode);
		
	}
	
	/**
	 * Metodo: Obtiene el parámetro del sistema por el código y el país
	 * @param codeEntity código de la entidad que consulta el parámetro del sistema
	 * @param countryId identificador del país
	 * @param systemParameterDao referencia al dao para consultar el parámetro del sistema
	 * @return Parámetro del suistema
	 * @throws BusinessException en caso que no se ecnuentre el parámetro del ssistema
	 * @throws DAOServiceException en caso de error al acceder a los datos
	 * @throws DAOSQLException En caso de error al acceder a la capa de datos
	 * @author jjimenezh
	 */
	private SystemParameter getSysParam(String codeEntity, Long countryId,
			SystemParameterDAOLocal systemParameterDao) throws BusinessException, DAOServiceException, DAOSQLException {
		SystemParameter sysParam = systemParameterDao.getSysParamByCodeAndCountryId(codeEntity, countryId);
		if(sysParam == null){
			log.error("No se encontró configurado el parámetro del sistema con código: \"" + codeEntity + "\" en el país especificado");
			throw new BusinessException(ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getCode(), ErrorBusinessMessages.SYSTEM_PARAMETER_DOESNT_EXIST.getMessage());
		}
		return sysParam;
	}
	
}

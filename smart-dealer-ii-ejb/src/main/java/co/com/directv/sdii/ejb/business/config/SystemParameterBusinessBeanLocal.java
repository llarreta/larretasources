package co.com.directv.sdii.ejb.business.config;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.pojo.SystemParameter;

/**
 * Esta interfaz define las opetaciones para la
 * entidad de ParameterType.
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 *
 */
@Local
public interface SystemParameterBusinessBeanLocal {
		
	/**
	 * Metodo: Retorna el id de una entidad
	 * filtrando por el codigo.
	 * @param className 
	 * @param codeEntity 
	 * @param propertyName 
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public Long getIdEntityByCodeEntity(String className,String codeEntity, String propertyName)throws BusinessException;
	
	/**
	 * Devuelve el valor que se utilizara para saber que idioma de las properties se tiene que usar.
	 * @param parameterCode
	 * @param countryCode
	 * @throws BusinessException <tipo> <descripcion>
	 */
	public SystemParameter getSysParamByCodeAndCountryCode(String parameterCode, String countryCode) throws BusinessException;
	
	/**
	 * Devuelve el valor que se utilizara para saber que idioma de las properties se tiene que usar.
	 * @param parameterCode
	 * @throws BusinessException <tipo> <descripcion>
	 */
	public SystemParameter getSysParamsByCodeAndCountryIdNull(String parameterCode) throws BusinessException;
	
	
}

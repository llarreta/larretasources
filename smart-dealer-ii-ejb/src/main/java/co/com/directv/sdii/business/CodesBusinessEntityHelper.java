package co.com.directv.sdii.business;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.enumerations.EJBRemoteJNDINameEnum;
import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.config.SystemParameterBusinessBeanRemote;
import co.com.directv.sdii.ejb.business.dealers.impl.VehiclesCRUDBean;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.HelperException;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * 
 * Clase encargada de invocar compenentes de negocio(ejb)
 * por medio de lookups.
 * Sun funcion es servir como clase utilitaria realizar consultas 
 * sin la necesidad de crear enterprise java beans para su consumo.
 * Fecha de Creación: 4/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
public class CodesBusinessEntityHelper{
	
	@Resource
	private InitialContext context;
	
	private final static Logger log = UtilsBusiness.getLog4J(VehiclesCRUDBean.class);
	 
	private String className;
	private String codeEntity;
	private String propertyName;
	
	public CodesBusinessEntityHelper(){
		
	}
	/**
	 * 
	 * Constructor: 
	 * @param pClassName String
	 * @param pCodeEntity String
	 * @param pPropertyName String
	 * @author jalopez
	 */	
	public CodesBusinessEntityHelper(String pClassName, String pCodeEntity, String pPropertyName){
		this.className = pClassName;
		this.codeEntity = pCodeEntity;
		this.propertyName = pPropertyName;
	}
	
	/**
	 * 
	 * Metodo: Retorna el id de una entidad por medio de su codigo
	 * @return Long id de la antidad
	 * @throws BusinessException 
	 * @author jalopez
	 * @throws HelperException 
	 */
	public Long getIdEntityByCodeEntity() throws HelperException{
		try{
			
			context=new InitialContext();
			String realEjbJndiName = EJBRemoteJNDINameEnum.SystemParameterBusinessBeanLocal.getCodeEntity()+"#"+SystemParameterBusinessBeanRemote.class.getName();
			SystemParameterBusinessBeanRemote systemParameterBusinessBean = (SystemParameterBusinessBeanRemote)context.lookup(realEjbJndiName);
			return systemParameterBusinessBean.getIdEntityByCodeEntity(className, codeEntity, propertyName);
		 } catch (BusinessException ex) {
            log.debug("== Error en la Capa de Negocio debido a una BusinessException ==", ex);
            throw new HelperException(ex.getMessage(), ex);
         } catch (NamingException e) {
			e.printStackTrace();
		 } catch (PropertiesException e) {
			log.error("No se pudo obtener el bean SystemParameterBusinessBeanRemote del contexto");
			throw new IllegalStateException("No se pudo obtener el bean SystemParameterBusinessBeanRemote del contexto");
		 } finally {
            log.debug("== Termina getIdEntityByCodeEntity/CodesBusinessEntityHelper ==");
         }
		return null;
	}
}

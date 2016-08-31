/**
 * Creado 12/07/2010 10:33:05
 */
package co.com.directv.sdii.model.vo;

import java.io.Serializable;

import co.com.directv.sdii.model.pojo.FtpConfiguration;

/**
 * Objeto que encapsula la información de un AdjustmentElements
 * 
 * Fecha de Creación: 2012/5/16
 * @author aharker <a href="mailto:aharker@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.model.pojo.AdjustmentElements    
 */
public class FtpConfigurationVO extends FtpConfiguration implements Serializable {

	public FtpConfigurationVO(FtpConfiguration parent){
		setFtpConfigurationCode(parent.getFtpConfigurationCode());
		setFtpConfigurationUser(parent.getFtpConfigurationUser());
		setHost(parent.getHost());
		setId(parent.getId());
		setName(parent.getName());
		setPassword(parent.getPassword());
		setPath(parent.getPath());
	}
	
	public static FtpConfiguration convertToFtpConfiguration(FtpConfigurationVO children){
		FtpConfiguration returnValue = new FtpConfiguration();
		returnValue.setFtpConfigurationCode(children.getFtpConfigurationCode());
		returnValue.setFtpConfigurationUser(children.getFtpConfigurationUser());
		returnValue.setHost(children.getHost());
		returnValue.setId(children.getId());
		returnValue.setName(children.getName());
		returnValue.setPassword(children.getPassword());
		returnValue.setPath(children.getPath());
		return returnValue;
	}
	
}

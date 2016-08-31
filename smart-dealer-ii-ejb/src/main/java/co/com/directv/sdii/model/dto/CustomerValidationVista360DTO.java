package co.com.directv.sdii.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <Descripcion> 
 * 
 * Fecha de Creación: 28/06/2012
 * @author cduarte <a href="mailto:cduarte@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
public class CustomerValidationVista360DTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3854436860471500488L;

	private boolean validatePostalCodeDefault;
	
	private boolean validateAllAddresses;
	
	private List<String> listAdressCodesValidate;
	
	public CustomerValidationVista360DTO(){
		this.validatePostalCodeDefault = false;
		this.validateAllAddresses = false;
		this.listAdressCodesValidate = new ArrayList<String>();
	}

	public boolean isValidatePostalCodeDefault() {
		return validatePostalCodeDefault;
	}

	public void setValidatePostalCodeDefault(boolean validatePostalCodeDefault) {
		this.validatePostalCodeDefault = validatePostalCodeDefault;
	}

	public List<String> getListAdressCodesValidate() {
		return listAdressCodesValidate;
	}

	public void setListAdressCodesValidate(List<String> listAdressCodesValidate) {
		this.listAdressCodesValidate = listAdressCodesValidate;
	}

	public boolean isValidateAllAddresses() {
		return validateAllAddresses;
	}

	public void setValidateAllAddresses(boolean validateAllAddresses) {
		this.validateAllAddresses = validateAllAddresses;
	}
	
	
	
}

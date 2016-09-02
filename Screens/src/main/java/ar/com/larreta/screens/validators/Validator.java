package ar.com.larreta.screens.validators;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.validate.ClientValidator;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.services.ResourceMessageService;
import ar.com.larreta.commons.services.impl.ResourceMessageServiceImpl;

@Entity
@Table(name = "validator")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Validator extends ar.com.larreta.commons.domain.Entity implements javax.faces.validator.Validator, ClientValidator {
	
	public static final Validator REQUIRED = new Required();

	protected ResourceMessageService resourceMessageService = (ResourceMessageService) AppManager.getInstance().getBean(ResourceMessageServiceImpl.RESOURCE_MESSAGE_SERVICE);
	
	@Transient
	private FacesContext context;
	@Transient
	private UIComponent component;
	@Transient
	private Object value;
	
	public Validator(){}
	
	public abstract void customValidate(FacesContext context, UIComponent component, Object value) throws ValidatorException;
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		assignParams(context, component, value);
		try {
			customValidate(context, component, value);
		} finally {
			resetParams();	
		}
	}

	private void resetParams() {
		this.context = null;
		this.component = null;
		this.value = null;
	}

	private void assignParams(FacesContext context, UIComponent component, Object value) {
		this.context = context;
		this.component = component;
		this.value = value;
	}
	
	protected String getMessage(String key){
		return resourceMessageService.getMessage(context.getViewRoot().getLocale(), key);
	}
	
	/**
	 * Se retorna nulo ya que no se utiliza
	 */
	@Transient
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Transient
	public String getValidatorId() {
		return getId().toString();
	}

}

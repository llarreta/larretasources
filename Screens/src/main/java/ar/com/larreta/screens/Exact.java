package ar.com.larreta.screens;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.AppObject;
import ar.com.larreta.commons.AppObjectImpl;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.persistence.dao.impl.Equal;
import ar.com.larreta.commons.persistence.dao.impl.Where;
import ar.com.larreta.commons.services.StandardService;

@Entity
@DiscriminatorValue(value = Exact.EXACT)
public class Exact extends FilterMatchMode {
	
	@Transient
	private AppObject appObject = new AppObjectImpl(Exact.class);
	
	public static final String EXACT = "exact";

	private String entityClass;

	@Basic
	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public Where toWhere(LoadArguments args) {
		return new Equal(args, getDescription(), getValue());
	}

	@Transient
	public Collection getItems(){
		try {
			Class type = getClass().getClassLoader().loadClass(entityClass);
			StandardService service = AppManager.getInstance().getStandardService();
			return service.load(type);
		} catch (Exception e){
			appObject.getLog().error(e);
		}
		return null;
	}
	
}

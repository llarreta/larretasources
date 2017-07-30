package ar.com.larreta.mystic.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.larreta.tools.BeanUtils;
import ar.com.larreta.tools.SpringUtils;

@MappedSuperclass
@Where(clause=Entity.NOT_DELETED)
public class Entity implements Serializable {
	
	public static final String NOT_DELETED = "deleted IS NULL";
	public static final String ID = "id";
	
	protected Long id;
	protected Date deleted;
	
	@Autowired
	private BeanUtils beanUtils;
	
	@Id
	@GeneratedValue
	public Long getId(){
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	public Date getDeleted(){
		return deleted;
	}

	public void setDeleted(Date deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object!=null) {
			Entity entity = (Entity) object;
			return entity.getId().equals(getId());
		}
		return Boolean.FALSE;
	} 

	@Override
	public int hashCode() {
		if (getId()==null){
			return super.hashCode();
		}
		return getId().hashCode();
	}
	
	/**
	 * Util para redefinir el tipo de entidad a utilizar durante la persistencia
	 * Si es null asume que el tipo de entidad es el definido en el encabezado de la clase 
	 */
	@Transient
	public String getPersistEntityName(){
		return null;
	}
	
	protected void writeToAll(Collection source, String property, Serializable value){
		if (beanUtils==null){
			beanUtils = (BeanUtils) SpringUtils.getBean(BeanUtils.class);
		}
		beanUtils.writeToAll(source, property, value);
	}

}

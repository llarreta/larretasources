package ar.com.larreta.commons.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Where;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.persistence.dao.impl.MainEntity;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@MappedSuperclass
//Solamente trae los que no fueron borrados logicamente
@Where(clause="deleted IS NULL")
@XmlRootElement
public abstract class Entity implements Serializable {
	
	public static final String ID = "id";
	
	private static final Logger LOGGER = Logger.getLogger(Entity.class);
	
	protected Long id;
	protected Date deleted;

	@Id
	public Long getId() {
		try {
			if (id==null){
				id = AppManager.getInstance().getAppConfig().getLockApp().nextIdentifier();
			}
			return id;
		} catch (Exception e){
			LOGGER.error("Ocurrio un error obteniendo id", e);
		}
		return null;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Basic
	public Date getDeleted(){
		return deleted;
	}

	public void setDeleted(Date deleted){
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object object) {
		if (getEntityClassName().equals(MainEntity.getEntityName(object.getClass()))) {
			Entity entity = (Entity) object;
			return entity.getId().equals(getId());
		}
		return Boolean.FALSE;
	} 

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Transient
	protected JRBeanCollectionDataSource getJasperReportDataSource(Collection data){
		return new JRBeanCollectionDataSource(data);
	}
	
	@Transient
	public String getEntityClassName(){
		return MainEntity.getEntityName(this.getClass());
	}
	
	
	
}

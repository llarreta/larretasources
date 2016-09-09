package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.log4j.Logger;

@Entity
@Table(name = "multiCheckBox")
@DiscriminatorValue(value = "multiCheckBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class MultiCheckBox extends ListSelector {

	private static Logger logger = Logger.getLogger(MultiCheckBox.class);

	public void setBindingPropertyValue(Object value){
		super.setBindingPropertyValue(value);
	}
	
}

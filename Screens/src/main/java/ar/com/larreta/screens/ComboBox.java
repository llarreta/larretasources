package ar.com.larreta.screens;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

@Entity
@Table(name = "comboBox")
@DiscriminatorValue(value = "comboBox")
@PrimaryKeyJoinColumn(name=ar.com.larreta.commons.domain.Entity.ID)
public class ComboBox extends ListSelector {

	private static Logger logger = Logger.getLogger(ComboBox.class);
	
	@Transient
	public void addVoidItem() {
		ListSelectorItem item = new ListSelectorItem();
		item.setOrder(0);
		item.setItemLabel(ScreenUtils.generateMessage("app.selection.void"));
		item.setNoSelectionOption(Boolean.TRUE.toString());
		addItem(item);
	}
	
}

package ar.com.larreta.screens.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.AuthenticatedSecurityMatcher;
import ar.com.larreta.commons.domain.Message;
import ar.com.larreta.commons.domain.ParametricEntity;
import ar.com.larreta.commons.domain.PermitAllSecurityMatcher;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.screens.Ajax;
import ar.com.larreta.screens.Attribute;
import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.ComboBoxItem;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MultiBox;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;

@Component
public class MessageSaver extends ABMSaver {

	public MessageSaver() {
		super();
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;

		index = screen.addInput(index, "app.pattern", "pattern");
		
		PanelGrid panelGridAuthenticated = new PanelGrid(2);
		PanelGrid panelGridPermitAll = new PanelGrid(2);
		PanelGrid panelGridRoles = new PanelGrid(2);

		PanelGrid custom = new PanelGrid();

		index = addComboBox(custom.getIdValue(), screen, index, panelGridAuthenticated.getId(), panelGridPermitAll.getId(), panelGridRoles.getId());
		
		panelGridAuthenticated.setRendered(Boolean.TRUE);
		panelGridPermitAll.setRendered(Boolean.TRUE);
		panelGridRoles.setRendered(Boolean.TRUE);
		custom.add(0, panelGridAuthenticated);
		custom.add(1, panelGridPermitAll);
		custom.add(2, panelGridRoles);
		
		index = screen.addInBody(index, custom);
		
		panelGridAuthenticated.add(new Label("app.securityMatcher.authenticated.description"));
		panelGridPermitAll.add(new Label("app.securityMatcher.permitall.description"));
		
		MultiBox multiBox = new MultiBox();
		multiBox.setSourceCaption("app.securityMatcher.roles.avaiables");
		multiBox.setTargetCaption("app.securityMatcher.roles.selected");
		multiBox.setEntityType(Role.class.getName());
		multiBox.setBindingObject(CreateScreen.DATA_VIEW_SELECTED);
		multiBox.setBindingProperty("roles");
		multiBox.setPropertyItemLabel(ParametricEntity.DESCRIPTION);
		panelGridRoles.add(multiBox);
		
		index++;
		
	}

	protected Integer addComboBox(String update, CreateScreen screen, Integer index, Long panelAuthenticated, Long panelPermitAll, Long panelRoles) {
		ComboBox comboBox = new ComboBox();
		comboBox.setBindingObject(createScreen.DATA_VIEW_SELECTED);
		comboBox.setBindingProperty("securityMatcherType");
		comboBox.addComboBoxItem(getComboBoxItem(comboBox, AuthenticatedSecurityMatcher.AUTHENTICATED, "securityMatcher.authenticated", 1));
		comboBox.addComboBoxItem(getComboBoxItem(comboBox, PermitAllSecurityMatcher.PERMIT_ALL, "securityMatcher.permitAll", 2));
		comboBox.addComboBoxItem(getComboBoxItem(comboBox, RolesSecurityMatcher.ROLES, "securityMatcher.roles", 3));
		
		comboBox.setChangeListener(SecurityMatcherTypeListener.class.getName());
		
		Ajax ajax = new Ajax();
		ajax.setChangeEvent();
		ajax.setEventElementId(comboBox.getId().toString());
		ajax.setUpdate(update);
		comboBox.add(3, ajax);
		
		index = screen.addInBody(index, new Label("app.securityMatcherType"));
		index = screen.addInBody(index, comboBox);
		return index;
	}

	protected void addAttribute(ComboBox comboBox, Integer order, String attributeName, Long value) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		attribute.setValue(value.toString());
		comboBox.add(order, attribute);
	}

	protected ComboBoxItem getComboBoxItem(ComboBox comboBox, String value, String message, Integer order) {
		ComboBoxItem item = new ComboBoxItem();
		item.setValue(value);
		item.setItemLabel(ScreenUtils.generateMessage(message));
		item.setComboBox(comboBox);
		item.setOrder(order);
		return item;
	}

	@Override
	public Class getABMClass() {
		return Message.class;
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("pattern", 	"app.pattern", 	"tableElement.pattern",  	"40%"));
		screen.getTable().addColumn(1, screen.getColumnWithLabelProperty("securityMatcherType", 	"app.securityMatcherType", 	"tableElement.securityMatcherType",  	"40%"));
	}

}

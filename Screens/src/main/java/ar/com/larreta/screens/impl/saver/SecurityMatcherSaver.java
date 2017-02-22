package ar.com.larreta.screens.impl.saver;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.AuthenticatedSecurityMatcher;
import ar.com.larreta.commons.domain.ParametricEntity;
import ar.com.larreta.commons.domain.PermitAllSecurityMatcher;
import ar.com.larreta.commons.domain.Role;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.screens.Ajax;
import ar.com.larreta.screens.Attribute;
import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.ListSelectorItem;
import ar.com.larreta.screens.MultiBox;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.validators.Validator;

@Component
public class SecurityMatcherSaver extends ABMSaver {

	private static final Logger LOGGER = Logger.getLogger(SecurityMatcherSaver.class);
	
	public static final String PANEL_ROLES_ID = "panelRolesId";
	public static final String PANEL_PERMIT_ALL_ID = "panelPermitAllId";
	public static final String PANEL_AUTHENTICATED_ID = "panelAuthenticatedId";
	
	public SecurityMatcherSaver() {
		super();
		mainScreen = new MainScreen(getABMClass()) {
			@Override
			public Long getCreateScreenId() {
				return screenConstantIds.getIdentifier(RolesSecurityMatcher.class.getSimpleName() + "Create");
			}

			@Override
			protected void makeColumns() {
				SecurityMatcherSaver.this.makeColumn(this);
			}
		};
		
		createScreen = new CreateScreen(RolesSecurityMatcher.class) {
			@Override
			public Long getNextScreenId() {
				return screenConstantIds.getIdentifier(SecurityMatcher.class.getSimpleName() + "Main");
			}

			@Override
			protected void makeBody() {
				SecurityMatcherSaver.this.makeBody(this);
			}
		};
		
		createScreen.setInitActionListenerName(InitSecurityMatcherTypeListener.class.getName());
		updateScreen.setInitActionListenerName(InitSecurityMatcherTypeListener.class.getName());
		updateScreen.setPreActionListenerName(SecurityMatcherUpdateListener.class.getName());
	}
	
	@Override
	public Class getABMClass() {
		return SecurityMatcher.class;
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().addColumn(0, screen.getColumnWithContainsFilter("pattern", 	"app.pattern", 	"tableElement.pattern",  	"40%"));
		screen.getTable().addColumn(1, screen.getColumnWithLabelProperty("securityMatcherType", 	"app.securityMatcherType", 	"tableElement.securityMatcherType",  	"40%"));
	}
	
	@Override
	protected void makeBody(CreateScreen screen) {
		Integer index = -1;

		index = screen.addInput(index, "app.pattern", "pattern", Validator.REQUIRED);
		
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
		multiBox.addValidator(Validator.LIST_SELECTOR_REQUIRED);
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

		comboBox.addItem(getComboBoxItem(comboBox, AuthenticatedSecurityMatcher.AUTHENTICATED, "securityMatcher.authenticated", 1));
		comboBox.addItem(getComboBoxItem(comboBox, PermitAllSecurityMatcher.PERMIT_ALL, "securityMatcher.permitAll", 2));
		comboBox.addItem(getComboBoxItem(comboBox, RolesSecurityMatcher.ROLES, "securityMatcher.roles", 3));
		
		comboBox.setChangeListener(SecurityMatcherTypeListener.class.getName());
		
		addAttribute(comboBox, 0, PANEL_AUTHENTICATED_ID,  panelAuthenticated);
		addAttribute(comboBox, 1, PANEL_PERMIT_ALL_ID,  panelPermitAll);
		addAttribute(comboBox, 2, PANEL_ROLES_ID,  panelRoles);
		
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

	protected ListSelectorItem getComboBoxItem(ComboBox comboBox, String value, String message, Integer order) {
		ListSelectorItem item = new ListSelectorItem();
		item.setValue(value);
		item.setItemLabel(ScreenUtils.generateMessage(message));
		item.setListSelector(comboBox);
		item.setOrder(order);
		return item;
	}

}

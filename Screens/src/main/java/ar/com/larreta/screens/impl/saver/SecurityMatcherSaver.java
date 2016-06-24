package ar.com.larreta.screens.impl.saver;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.domain.AuthenticatedSecurityMatcher;
import ar.com.larreta.commons.domain.PermitAllSecurityMatcher;
import ar.com.larreta.commons.domain.RolesSecurityMatcher;
import ar.com.larreta.commons.domain.SecurityMatcher;
import ar.com.larreta.screens.Ajax;
import ar.com.larreta.screens.Attribute;
import ar.com.larreta.screens.ComboBox;
import ar.com.larreta.screens.ComboBoxItem;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.ScreenImplementationsIds;
import ar.com.larreta.screens.impl.UpdateScreen;

public class SecurityMatcherSaver extends ABMSaver {
	public static final String PANEL_ROLES_ID = "panelRolesId";
	public static final String PANEL_PERMIT_ALL_ID = "panelPermitAllId";
	public static final String PANEL_AUTHENTICATED_ID = "panelAuthenticatedId";
	
	private Class abmClass = SecurityMatcher.class;
	
	public SecurityMatcherSaver() {
		super();

		mainScreen = new MainScreen(ScreenImplementationsIds.SECURITY_MATCHER_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("pattern", 	"app.pattern", 	"tableElement.pattern",  	"40%"));
				table.addColumn(1, getColumnWithLabelProperty("securityMatcherType", 	"app.securityMatcherType", 	"tableElement.securityMatcherType",  	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ScreenImplementationsIds.SECURITY_MATCHER_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ScreenImplementationsIds.SECURITY_MATCHER_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ScreenImplementationsIds.SECURITY_MATCHER_CREATE, RolesSecurityMatcher.class) {
			
			@Override
			public void initialize() {
				super.initialize();
				setInitActionListenerName(InitSecurityMatcherTypeListener.class.getName());
			}

			@Override
			protected void makeBody() {
				SecurityMatcherSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.SECURITY_MATCHER_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ScreenImplementationsIds.SECURITY_MATCHER_UPDATE, abmClass) {

			@Override
			public void initialize() {
				super.initialize();
				setInitActionListenerName(InitSecurityMatcherTypeListener.class.getName());
			}
			
			@Override
			protected void makeBody() {
				SecurityMatcherSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ScreenImplementationsIds.SECURITY_MATCHER_MAIN;
			}
		};
		
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
		
		panelGridAuthenticated.add(new Label("panelGridAuthenticated"));
		panelGridPermitAll.add(new Label("panelGridPermitAll"));
		panelGridRoles.add(new Label("panelGridRoles"));
		
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

	protected ComboBoxItem getComboBoxItem(ComboBox comboBox, String value, String message, Integer order) {
		ComboBoxItem item = new ComboBoxItem();
		item.setValue(value);
		item.setItemLabel(ScreenUtils.generateMessage(message));
		item.setComboBox(comboBox);
		item.setOrder(order);
		return item;
	}

}

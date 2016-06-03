package ar.com.larreta.screens.initializer;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.domain.ResourceMessage;
import ar.com.larreta.commons.domain.User;
import ar.com.larreta.screens.AjaxButton;
import ar.com.larreta.screens.Column;
import ar.com.larreta.screens.CommandLink;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.PanelGrid;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.ScreenElement;
import ar.com.larreta.screens.ScreenUtils;
import ar.com.larreta.screens.SubMenu;
import ar.com.larreta.screens.SubmitButton;
import ar.com.larreta.screens.Table;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		ScreensService screensService = (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
		screensService.save(getInitialScreen());
		screensService.save(getUpdateScreen());
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}
	
	
	private Screen getUpdateScreen() {
		Screen screen = new Screen();
		screen.setEntityClass(User.class.getName());
		screen.setId(new Long(2));

		addStyleSheets(screen);

		screen.add(0, getHeader());
		
		Form form = new Form();
		
		PanelGrid panelGrid = new PanelGrid();
		panelGrid.setColumns("2");
		form.add(panelGrid);
		
		panelGrid.add(0, new Label("app.nick"));
		Input inputNick = new Input();
		inputNick.setBindingObject("#{dataView.selected}");
		inputNick.setBindingProperty("nick");
		panelGrid.add(1, inputNick);
		
		panelGrid.add(2, new Label("app.email"));
		Input inputEmail = new Input();
		inputEmail.setBindingObject("#{dataView.selected}");
		inputEmail.setBindingProperty("email");
		panelGrid.add(3, inputEmail);

		SubmitButton submitButton = new SubmitButton();
		submitButton.setAction("preCreate");
		submitButton.setValue("app.confirm");
		submitButton.setIcon("ui-icon-check");
		submitButton.setNextScreenId(new Long(2));
		form.add(submitButton);
		
		SubmitButton returnButton = new SubmitButton();
		returnButton.setAction("starting");
		returnButton.setValue("app.back");
		returnButton.setIcon("ui-icon-check");
		returnButton.setNextScreenId(new Long(1));
		form.add(returnButton);
		
		screen.add(1, form);
		screen.add(2, getFooter());
		
		return screen;
	}
	
	private Screen getInitialScreen() {
		Screen screen = new Screen();
		screen.setId(new Long(1));
		screen.setEntityClass(ResourceMessage.class.getName());
		
		screen.setTitleMessage("app.titleApp");
		
		addStyleSheets(screen);
		
		screen.add(0, getHeader());
		
		Form form = new Form();
		
		SubmitButton submitButton = new SubmitButton();
		submitButton.setAction("create");
		submitButton.setValue("app.create");
		submitButton.setIcon("ui-icon-plusthick");
		submitButton.setNextScreenId(new Long(2));
		form.add(submitButton);
		
		Table table = new Table();
		table.setLazyProperties("country,language");
		form.add(table);
		
		table.addColumn(0, getColumnWithLabelProperty("country", 	ScreenUtils.generateMessage("app.country"), 	"tableElement.country",  	"10%"));
		table.addColumn(1, getColumnWithLabelProperty("language", 	ScreenUtils.generateMessage("app.language"), "tableElement.language", 	"10%"));
		table.addColumn(2, getColumnWithLabelProperty("key", 		ScreenUtils.generateMessage("app.key"), 		"tableElement.key", 		"25%"));
		table.addColumn(3, getColumnWithLabelProperty("textString", 		ScreenUtils.generateMessage("app.text"), 	"tableElement.text", 		"25%"));
		table.addColumn(4, getColumnWithButtons());
		
		screen.add(1, form);
		screen.add(2, getFooter());
		
		return screen;
	}

	private void addStyleSheets(Screen screen) {
		screen.addStyleSheet("css", 			"main.css");
		screen.addStyleSheet("smarttrace/css", 	"bootstrap.min.css");
		screen.addStyleSheet("css", 			"font-awesome.css");
		screen.addStyleSheet("css", 			"animate.css");
		screen.addStyleSheet("css", 			"socicon.css");
		screen.addStyleSheet("css", 			"font-awesome-animation.min.css");
	}
	
	public Column getColumnWithLabelProperty(String property, String header, String sort, String width){
		Column column = new Column();
		column.setHeaderText(header);
		column.setSortBy(sort);
		column.setWidth(width);
		
		Label label = new Label(StringUtils.EMPTY);
		label.setBindingObject(ScreenElement.TABLE_ELEMENT);
		label.setBindingProperty(property);
		column.add(label);
		
		return column;
	}
	
	public Column getColumnWithButtons(){
		Column column = new Column();
		
		PanelGrid panelGrid = new PanelGrid();
		panelGrid.setColumns("2");
		
		SubmitButton updateButton = new SubmitButton();
		updateButton.setAction("update");
		updateButton.setIcon("ui-icon-pencil");
		updateButton.setValue("app.modify");
		updateButton.setInmediate(Boolean.TRUE);
		
		AjaxButton deleteButton = new AjaxButton();
		deleteButton.setActionListener("#{controller.delete}");
		deleteButton.setIcon("ui-icon-trash");
		deleteButton.setValue("app.delete");
		deleteButton.setInmediate(Boolean.TRUE);
		
		panelGrid.add(updateButton);
		panelGrid.add(deleteButton);
		column.add(panelGrid);
		
		return column;	
	}
	
	
	

	private Div getFooter() {
		Div div = new Div();
		div.setStyleClass("ui-grid-col-12");
		Div interior = new Div();
		div.setStyleClass("footer-standar");
		div.add(0, interior);
		GraphicImage graphicImage = new GraphicImage("images", "logosmarttrace-blanco.png");
		graphicImage.setStyleClass("logo-footer");
		interior.add(graphicImage);
		interior.add(getLink("socicon socicon-twitter sc-2x social-networks-icons twitter-position"));
		interior.add(getLink("socicon socicon-facebook sc-2x social-networks-icons facebook-position"));
		interior.add(getLink("socicon socicon-google sc-2x social-networks-icons google-position"));
		Label label = new Label();
		label.setValue("loggin.labelRights");
		label.setStyleClass("label-rights");
		interior.add(label);
		return div;
	}

	private CommandLink getLink(String style) {
		CommandLink link = new CommandLink();
		link.setStyleClass(style);
		return link;
	}

	private Form getHeader() {
		Form form = new Form();
		Div div = new Div();
		form.add(div);
		GraphicImage image = new GraphicImage("images", "logo-barra-menu.png");
		image.setStyleClass("logo-main-menu");
		div.add(0, image);
		MenuBar menuBar = new MenuBar();
		menuBar.setStyleClass("main-bar-1");
		MenuItem item = new MenuItem();
		item.setValueMessage("main-bar.init");
		item.setStyleClass("menu-item-default");
		item.setUrl("/app/home");
		menuBar.add(item);
		SubMenu subMenu = new SubMenu();
		subMenu.setLabelMessage("main-bar.basicConfigurations");
		subMenu.setStyleClass("sub-menu-main");
		subMenu.add(item);
		menuBar.add(subMenu);
		div.add(1, menuBar);
		return form;
	}


}

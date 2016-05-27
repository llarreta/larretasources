package ar.com.larreta.screens.initializer;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.Column;
import ar.com.larreta.screens.CommandLink;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Input;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.OutputPanel;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.SubMenu;
import ar.com.larreta.screens.SubmitButton;
import ar.com.larreta.screens.Table;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreenFrameworkInitializer extends GenericServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		ScreensService screensService = (ScreensService) AppManager.getInstance().getBean(ScreenServiceImpl.SCREEN_SERVICE);
		screensService.save(getScreen());
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// No hacemos nada
	}
	
	private Screen getScreen() {
		Screen screen = new Screen();
		screen.setId(new Long(1));
		
		screen.setTitleMessage("app.titleApp");
		
		screen.addStyleSheet("css", 			"main.css");
		screen.addStyleSheet("smarttrace/css", 	"bootstrap.min.css");
		screen.addStyleSheet("css", 			"font-awesome.css");
		screen.addStyleSheet("css", 			"animate.css");
		screen.addStyleSheet("css", 			"socicon.css");
		screen.addStyleSheet("css", 			"font-awesome-animation.min.css");
		
		Form form = new Form();
		OutputPanel outputPanel = new OutputPanel();
		form.add(outputPanel);
		outputPanel.setStyleClass("box-message-test");
		outputPanel.add(new Label("app.description"));
		outputPanel.add(new Input());
		SubmitButton button = new SubmitButton();
		button.setAction("confirm");
		button.setValue("app.login.confirm");
		button.setIcon("ui-icon-check");
		outputPanel.add(button);
		
		screen.add(0, getHeader());
		screen.add(1, form);
		
		Table table = new Table();
		Column column = new Column();
		column.setHeaderText("app.user.id");
		column.setSortBy("actualItem.id");
		column.setWidth("50%");

		column.add(new Label("Prueba"));
		
		table.addColumn(column);
		
		screen.add(2, table);
		screen.add(3, getFooter());
		
		return screen;
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

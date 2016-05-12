package ar.com.larreta.screens.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.webflow.execution.RequestContext;

import ar.com.larreta.commons.controllers.HomeController;
import ar.com.larreta.commons.exceptions.NotServiceAssignedException;
import ar.com.larreta.commons.services.StandardService;
import ar.com.larreta.commons.services.UserService;
import ar.com.larreta.commons.services.impl.UserServiceImpl;
import ar.com.larreta.screens.CommandLink;
import ar.com.larreta.screens.Div;
import ar.com.larreta.screens.Form;
import ar.com.larreta.screens.GraphicImage;
import ar.com.larreta.screens.Label;
import ar.com.larreta.screens.MenuBar;
import ar.com.larreta.screens.MenuItem;
import ar.com.larreta.screens.Screen;
import ar.com.larreta.screens.SubMenu;
import ar.com.larreta.screens.services.ScreensService;
import ar.com.larreta.screens.services.impl.ScreenServiceImpl;

public class ScreensControllerImpl extends HomeController {

	public ScreensService getService() throws NotServiceAssignedException {
		return (ScreensService) super.getService();
	}

	@Autowired
	@Qualifier(ScreenServiceImpl.SCREEN_SERVICE)
	public void setService(ScreensService standardService) {
		super.setService(standardService);
	}

	@Override
	public void home(RequestContext flowRequestContext) {
		try {
			Screen screen = (Screen) getService().getScreen(new Long(1));
			
			/*Screen screen = new Screen();
			
			screen.setTitle("app.titleApp");
			
			screen.addStyleSheet("css", 			"main.css");
			screen.addStyleSheet("smarttrace/css", 	"bootstrap.min.css");
			screen.addStyleSheet("css", 			"font-awesome.css");
			screen.addStyleSheet("css", 			"animate.css");
			screen.addStyleSheet("css", 			"socicon.css");
			screen.addStyleSheet("css", 			"font-awesome-animation.min.css");
			
			screen.setHeader(getHeader());
			screen.setFooter(getFooter());
			
			OutputPanel outputPanel = new OutputPanel();
			outputPanel.setStyleClass("box-message-test");
			outputPanel.add(new Label("app.description"));
			outputPanel.add(new Input());
			SubmitButton button = new SubmitButton();
			button.setAction("confirm");
			button.setValue("app.login.confirm");
			button.setIcon("ui-icon-check");
			outputPanel.add(button);
			
			screen.add(outputPanel);*/
			
			flowRequestContext.getFlowScope().put("screen", screen);
		} catch (Exception e){
			getLog().error("Ocurrio un error", e);
		}
	}

	private Div getFooter() {
		Div div = new Div();
		div.setStyleClass("ui-grid-col-12");
		Div interior = new Div();
		div.setStyleClass("footer-standar");
		div.add(interior);
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
		div.add(image);
		MenuBar menuBar = new MenuBar();
		menuBar.setStyleClass("main-bar-1");
		MenuItem item = new MenuItem();
		item.setValue("main-bar.init");
		item.setStyleClass("menu-item-default");
		item.setUrl("/app/home");
		menuBar.add(item);
		SubMenu subMenu = new SubMenu();
		subMenu.setLabel("main-bar.basicConfigurations");
		subMenu.setStyleClass("sub-menu-main");
		subMenu.add(item);
		menuBar.add(subMenu);
		div.add(menuBar);
		return form;
	}



}

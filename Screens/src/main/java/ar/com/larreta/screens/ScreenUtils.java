package ar.com.larreta.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.screens.impl.Footer;
import ar.com.larreta.screens.impl.Header;
import ar.com.larreta.screens.impl.MainMenu;
import ar.com.larreta.screens.impl.saver.ABMSaver;

public class ScreenUtils {

	private static Logger logger = Logger.getLogger(ScreenUtils.class);
	
	private static final String SEPARATOR = ",";
	
	private static final String OPEN_EXP = "#{";
	private static final String CLOSE_EXP = "}";
	
	private static final String OPEN_MSG = "msg['";
	private static final String CLOSE_MSG = "']";
	
	//FIXME: Considerar la posibilidad de que en el mismo mensaje vengan varias expresiones a resolver
	public static String messaging(String exp){
		if (!StringUtils.isEmpty(exp)){
			Integer openMessage = exp.indexOf(OPEN_EXP + OPEN_MSG);
			if (openMessage>=0){
				Integer closeMessage = exp.indexOf(CLOSE_MSG + CLOSE_EXP);
				if ((closeMessage>=0) && (openMessage<closeMessage)){
					exp = exp.substring(0, openMessage) 
							+ AppManager.getInstance().getResourceBundle().getString(exp.substring(openMessage + OPEN_EXP.length() + OPEN_MSG.length(), closeMessage)) 
						+ exp.substring(closeMessage + CLOSE_MSG.length() + CLOSE_EXP.length());
				}
			}
		}
		return exp;
	}
	
	/**
	 * Evalua una expresion en el contexto de faces
	 * para obtener el valor que esta representa
	 * En caso de no ser una expresion, 
	 * entonces retorna el mismo valor pasado por parametro
	 * @param exp
	 * @return
	 */
	public static Object evaluate(String exp){
		if (!StringUtils.isEmpty(exp)){
			exp = messaging(exp);
			//FIXME: Evaluar solo la expresion entre lo que se abre y lo que se cierra
			if (exp.indexOf(OPEN_EXP)>=0){
				FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), exp , Object.class);
				return  FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), exp , Object.class);
			}
		}
		return exp;
	}
	
	/**
	 * Pone las abreviaturas correspondientes de una expression
	 * @param value
	 * @return
	 */
	public static String generateExpression(String value){
		if (!StringUtils.isEmpty(value)){
			return OPEN_EXP + value + CLOSE_EXP;
		}
		return value;
	}
	
	public static String generateMessage(String value){
		if (!StringUtils.isEmpty(value)){
			return generateExpression(OPEN_MSG + value + CLOSE_MSG);
		}
		return value;
	}
	
	public static MethodExpression createMethod(String expression, Class expectedReturnType,  Class[] expectedParamTypes){
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(
					FacesContext.getCurrentInstance().getELContext(), expression, expectedReturnType,  expectedParamTypes);
	}
	
	public static MethodExpression createSimpleMethod(String expression){
		return createMethod(expression, String.class, null);
	}
	
	/**
	 * Escapea los valores
	 * @param value
	 * @return
	 */
	public static Object fixValue(Object value){
		if (value instanceof String) {
			return StringEscapeUtils.escapeHtml4((String) value);
		}
		return value;
	}
	
	public static Class getClass(String className){
		try {
			return ScreenUtils.class.getClassLoader().loadClass(className);
		} catch (Exception e){
			logger.error("Ocurrio un error obteniendo clase a partir del nombre", e);
		}
		return null;
	}
	
	public static Object getObject(String className){
		Class classInstance = getClass(className);
		if (classInstance!=null){
			try {
				return classInstance.newInstance();
			} catch (Exception e){
				logger.error("Ocurrio un error obteniendo objeto a partir de clase", e);
			}
		}
		return null;
	}
	
	/**
	 * Corta un tecto segun la aparicion del separador ,
	 * @param textTosplit
	 * @return
	 */
	public static List<String> split(String textTosplit){
		if (!StringUtils.isEmpty(textTosplit)){
			return Arrays.asList(textTosplit.split(SEPARATOR));
		}
		return null;
	}
	
	/**
	 * Obtienen un atributo de un evento
	 * @param actionEvent
	 * @param eventAttribute
	 * @return
	 */
	public static Object getEventAttribute(FacesEvent actionEvent, String eventAttribute) {
		if ((actionEvent==null) || (actionEvent.getComponent()==null) || (actionEvent.getComponent().getAttributes()==null)){
			return null;
		}
		return actionEvent.getComponent().getAttributes().get(eventAttribute);
	}
	
	public static MainMenu getMainMenu(){
		return (MainMenu) getScreenElement(MainMenu.MAIN_MENU, MainMenu.class);
	}

	public static Header getHeader(){
		return (Header) getScreenElement(Header.HEADER, Header.class);
	}

	public static Footer getFooter(){
		return (Footer) getScreenElement(Footer.FOOTER, Footer.class);
	}

	
	public static Object getScreenElement(String defaultName, Class toSearch) {
		try{
			ApplicationContext context = AppManager.getInstance().getAppContext();
			Collection names = Arrays.asList(context.getBeanNamesForType(toSearch));
			if (names.size()>1){
				Iterator it = names.iterator();
				while (it.hasNext()) {
					String name = (String) it.next();
					if (!defaultName.equals(name)){
						return context.getBean(name);
					}
				}
			}
			return context.getBean((String) names.iterator().next());
		} catch (Exception e){
			logger.error(e);
		}
		return null;
	}
	
	public static Collection<ABMSaver> getSavers(){
		Collection<ABMSaver> savers = new ArrayList<ABMSaver>();
		
		ApplicationContext context = AppManager.getInstance().getAppContext();
		Collection names = Arrays.asList(context.getBeanNamesForType(ABMSaver.class));
		Iterator<String> it = names.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			savers.add((ABMSaver) context.getBean(name));
		}
		
		return savers;
	}
	
	
}

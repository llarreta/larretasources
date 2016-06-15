package ar.com.larreta.screens;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import ar.com.larreta.commons.AppManager;
import ar.com.larreta.commons.faces.EntityConverter;

public class ScreenUtils {

	private static Logger logger = Logger.getLogger(ScreenUtils.class);
	
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
	
}

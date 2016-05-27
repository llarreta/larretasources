package ar.com.larreta.screens;

import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;

import ar.com.larreta.commons.AppManager;

public class ScreenUtils {

	private static final String OPEN_EXP = "#{";
	private static final String CLOSE_EXP = "}";
	
	private static final String OPEN_MSG = "msg['";
	private static final String CLOSE_MSG = "']";
	
	private static FacesContext facesContext = FacesContext.getCurrentInstance();
	
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
				return  facesContext.getApplication().evaluateExpressionGet(facesContext, exp , Object.class);
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
	
}

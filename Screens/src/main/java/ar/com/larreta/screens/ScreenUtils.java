package ar.com.larreta.screens;

import javax.faces.context.FacesContext;

public class ScreenUtils {

	private static final String CLOSE_EXP = "}";
	private static final String OPEN_EXP = "#{";
	
	private static FacesContext facesContext = FacesContext.getCurrentInstance();
	
	/**
	 * Evalua una expresion en el contexto de faces
	 * para obtener el valor que se representa
	 * @param exp
	 * @return
	 */
	public static Object evaluate(String exp){
		Object returned = facesContext.getApplication().evaluateExpressionGet(facesContext, OPEN_EXP + exp + CLOSE_EXP , Object.class);
		return returned;
	}
	
}

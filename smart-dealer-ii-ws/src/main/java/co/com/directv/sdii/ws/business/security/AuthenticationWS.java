/**
 * Creado 22/07/2010 10:13:55
 */
package co.com.directv.sdii.ws.business.security;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.security.SecurityFacadeBeanLocal;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Servicio web que expone la funcionalidad de autenticación con directorio activo
 * 
 * Fecha de Creación: 22/07/2010
 * @author jjimenezh <a href="mailto:jjimenezh@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see     
 */
@MTOM
@WebService()
@Stateless()
public class AuthenticationWS {

	@EJB
	private SecurityFacadeBeanLocal ejbRef;
	
	@WebMethod(operationName = "authenticateUser", action="authenticateUser")
	public UserVO authenticateUser(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "countryId") Long countryId) throws BusinessException{
		return ejbRef.authenticateLDAPUser(userName, password, countryId);
	}
	
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.ws.business.dealers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CitiesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.CountriesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.PostalCodesFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.SdiiTimeZoneFacadeBeanLocal;
import co.com.directv.sdii.facade.dealers.StatesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CityVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.SdiiTimeZoneVO;
import co.com.directv.sdii.model.vo.StateVO;
import co.com.directv.sdii.model.vo.UserVO;

/**
 * Servicio que expone todos los metodos referentes a la administracion de ubicacion geografica
 * 
 * Fecha de Creación: 25/03/2010
 * @author jcasas <a href="mailto:jcasas@intergrupo.com">e-mail</a>
 * @version 1.0    
 */
@MTOM
@WebService()
@Stateless()
public class LocationsWS {
    
    @EJB
    CountriesFacadeBeanLocal countriesFacade;
    
    @EJB
    StatesFacadeBeanLocal statesFacade;
    
    @EJB
    CitiesFacadeBeanLocal citiesFacade;
    
    @EJB
    PostalCodesFacadeBeanLocal postalCodesFacade;

    @EJB
    SdiiTimeZoneFacadeBeanLocal timeZoneFacadeBean;

    /**
	 * Método: Retorna un listado de todos los registros
	 * de la Entidad Countries
	 * @return List<CountryVO> Listado de paises existentes en el sistema; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los paises del sistema<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br> 
	 * @author jalopez
	 */
	@WebMethod(operationName = "getAllCountries", action = "getAllCountries")
	public List<CountryVO> getAllCountries() throws BusinessException {
		return countriesFacade.getAllCountries();
	}

	/**
	 * Método: Retorna el resultado de la consulta por codigo
	 * de la Entidad Countries.
	 * @param code - String Codigo del pais
	 * @return CountriesVO País cuyo código corresponde al especificado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta del país por código<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getCountriesByCode", action = "getCountriesByCode")
	public CountryVO getCountriesByCode(@WebParam(name = "code") String code)
			throws BusinessException {
		return countriesFacade.getCountriesByCode(code);
	}

	/**
	 * Método: Retorna el resultado de la consulta por ID
	 * de la Entidad Countries.
	 * @param id - Long Id del pais
	 * @return CountriesVO País cuyo identificador corresponde al especificado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta del país por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez 
	 */
	@WebMethod(operationName = "getCountriesByID", action = "getCountriesByID")
	public CountryVO getCountriesByID(@WebParam(name = "id") Long id)
			throws BusinessException {
		return countriesFacade.getCountriesByID(id);
	}

    /**
	 * Método: Obtiene todos los estados del sistema
	 * @return List<StateVO> Listado de con los estados del sistema; vacio en caso de no existir
	 * @throws BusinessException  en caso de error al ejecutar la operación de consulta de todos los estados del sistema<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getAllStates", action = "getAllStates")
	public List<StateVO> getAllStates() throws BusinessException {
		return statesFacade.getAllStates();
	}

	/**
	 * Método: Obtiene un estado por el codigo especificado
	 * @param code - String Codigo del estado
	 * @return StateVO Objeto con la informacion basica de la ciudad; nulo en caso de no existir 
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de un estado por su código <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getStatesByCode", action = "getStatesByCode")
	public StateVO getStatesByCode(@WebParam(name = "code") String code)
			throws BusinessException {
		return statesFacade.getStatesByCode(code);
	}

	/**
	 * Método: Obtiene un estado por el id especificado
	 * @param id - LongId del estado a consultar
	 * @return StateVO Objeto con la informacion basica de un estado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de un estado por su identificador <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getStatesByID", action = "getStatesByID")
	public StateVO getStatesByID(@WebParam(name = "id") Long id)
			throws BusinessException {
		return statesFacade.getStatesByID(id);
	}

    /**
	 * Método: Obtiene un listado de los estados correspondientes a un pais
	 * @param id - Long Id del pais a consultar
	 * @return List<StateVO> Listado de los estados correspondientes a un pais; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de estados por el 
	 * identificador del país <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getStatesByCountryID", action = "getStatesByCountryID")
	public List<StateVO> getStatesByCountryID(
			@WebParam(name = "countryId") Long countryId)
			throws BusinessException {
		return statesFacade.getStatesByCountryID(countryId);
	}

    /**
	 * Método: Obtiene todas las ciudades existentes en el sistema
	 * @return List<CityVO> Listado de ciudades existentes en el sistema; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de todas las ciudades del sistema<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br> 
	 * @author jalopez
	 */
	@WebMethod(operationName = "getAllCities", action = "getAllCities")
	public List<CityVO> getAllCities() throws BusinessException {
		return citiesFacade.getAllCities();
	}

    /**
     * Método: Obtiene un listado de ciudades pertenecientes a un estado
     * @param stateId - Long Id del estado
     * @return List<CityVO> Listado de ciudades de un estado especifico; vacio en caso de no existir
     * @throws BusinessException en caso de error al ejecutar la operación de consulta de las ciudades de un estado <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
     * @author jalopez
     */
	@WebMethod(operationName = "getCitiesByStateId", action = "getCitiesByStateId")
	public List<CityVO> getCitiesByStateId(
			@WebParam(name = "stateId") Long stateId) throws BusinessException {
		return citiesFacade.getCitiesByStateId(stateId);
	}

	/**
	 * Método: Obtiene una ciudad por el codigo especificado
	 * @param code - String Codigo de la ciudad
	 * @return CityVO Objeto con la informacion basica de la ciudad; nulo en caso de no existir 
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de una ciudad por su código <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getCitiesByCode", action = "getCitiesByCode")
	public CityVO getCitiesByCode(@WebParam(name = "code") String code)
			throws BusinessException {
		return citiesFacade.getCitiesByCode(code);
	}

	/**
	 * Método: Obtiene una ciudad con el id especificado
	 * @param id - Long Id de la ciudad a consultar
	 * @return CityVO Ciudad con el id especificado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de una ciudad por su identificador<br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getCitiesByID", action = "getCitiesByID")
	public CityVO getCitiesByID(@WebParam(name = "id") Long id)
			throws BusinessException {
		return citiesFacade.getCitiesByID(id);
	}

    /**
	 * Método: Retorna un listado de todos los registros
	 * de la Entidad PostalCodes
	 * @return List<PostalCodesVO> Lista de todos los códigos postales registrados en el sistema; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de todos los códigos postales <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
        @WebMethod(operationName = "getAllPostalCodes", action="getAllPostalCodes")
	public List<PostalCodeVO> getAllPostalCodes() throws BusinessException {
		return postalCodesFacade.getAllPostalCodes();
	}

     /**
	 * Método: Retorna un listado de todos los registros
	 * de la Entidad PostalCodes
	 * @return List<PostalCodesVO> Lista de todos los Códigos postales asociados a una Ciudad; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de los códigos postales de una ciudad <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getPostalCodesByCityId", action = "getPostalCodesByCityId")
	public List<PostalCodeVO> getPostalCodesByCityId(
			@WebParam(name = "cityId") Long cityId) throws BusinessException {
		return postalCodesFacade.getPostalCodesByCityId(cityId);
	}

	/**
	 * Método: Retorna el resultado de la consulta por codigo
	 * de la Entidad PostalCodes.
	 * @param code - String Código del Código postal
	 * @return PostalCodesVO Codigo postal cuyo código corresponde al especificado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de código postal por su código <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getPostalCodesByCode", action = "getPostalCodesByCode")
	public PostalCodeVO getPostalCodesByCode(
			@WebParam(name = "code") String code) throws BusinessException {
		return postalCodesFacade.getPostalCodesByCode(code);
	}

	/**
	 * Método: Retorna el resultado de la consulta por ID
	 * de la Entidad PostalCodes.
	 * @param id - Long Idenrificador del Código postal
	 * @return PostalCodesVO Código postal cuyo identificador corresponde al especificado; nulo en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consyulta de código postal por su identificador <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br> 
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getPostalCodesByID", action = "getPostalCodesByID")
	public PostalCodeVO getPostalCodesByID(@WebParam(name = "id") Long id)
			throws BusinessException {
		return postalCodesFacade.getPostalCodesByID(id);
	}

	/**
	 * Metodo: Retorna la consulta de los códigos postales dado un nombre
	 * @param name - String Nombre del Código Postal que se va a consultar
	 * @return List<PostalCodeVO> Conjunto de los código postales cuyo nombre correspe al especificado; vacio en caso de no existir
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de código postal por su nombre <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jalopez
	 */
	@WebMethod(operationName = "getPostalCodesByName", action = "getPostalCodesByName")
	public List<PostalCodeVO> getPostalCodesByName(
			@WebParam(name = "name") String name) throws BusinessException {
		return postalCodesFacade.getPostalCodesByName(name);
	}
	/*
	 * @WebMethod(operationName = "createPostalCode", action="createPostalCode")
	 * public void createPostalCode(@WebParam(name = "postalcode")PostalCodeVO
	 * obj) throws BusinessException{ postalCodesFacade.createPostalCode(obj); }
	 * 
	 * @WebMethod(operationName = "updatePostalCode", action="updatePostalCode")
	 * public void updatePostalCode(@WebParam(name = "postalcode")PostalCodeVO
	 * obj) throws BusinessException{ postalCodesFacade.updatePostalCode(obj); }
	 * 
	 * @WebMethod(operationName = "deletePostalCode", action="deletePostalCode")
	 * public void deletePostalCode(@WebParam(name = "postalcode")PostalCodeVO
	 * obj) throws BusinessException{ postalCodesFacade.deletePostalCode(obj); }
	 * //
	 */
	
	
	/**
	 * Metodo: Consulta la información de las zonas horarias registradas en el sistema
	 * @return Lista con las zonas horarias registradas en el sistema
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de todas las zonas horarias <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getAllTimeZones", action = "getAllTimeZones")
	public List<SdiiTimeZoneVO> getAllTimeZones() throws BusinessException {
		return timeZoneFacadeBean.getAllTimeZones();
	}

	
	/**
	 * Metodo: Consulta la información de una zona horaria dado su identificador
	 * @param id identificador de la zona horaria
	 * @return información de la zona horaria
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta dezona horaria por su id <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getTimeZoneById", action = "getTimeZoneById")
	public SdiiTimeZoneVO getTimeZoneById(@WebParam(name = "id") Long id) throws BusinessException {
		return timeZoneFacadeBean.getTimeZoneById(id);
	}

	
	/**
	 * Metodo: <Descripcion>
	 * @param countryId
	 * @return
	 * @throws BusinessException en caso de error al ejecutar la operación de consulta de zona horaria por país <br> Códigos:
     * <code>sdii_CODE_constraint_violation</code> En caso que se viole una restricción definida en la persistencia<br>
     * <code>sdii_CODE_error_data</code> En caso que se viole una regla de datos de la persistencia<br>
     * <code>sdii_CODE_generic_jdbc</code> En caso de error genérico relacionado con el componente de conexión a la base de datos<br>
     * <code>sdii_CODE_jdbc_connection</code> En caso de error al tratar de conectarse con la base de datos<br>
     * <code>sdii_CODE_sql_grammar</code> En caso de error relacionado con la gramática para ejecutar el comando en la base de datos<br>
     * <code>sdii_CODE_error_service</code> En caso de error al tratar de obtener el valor de una propiedad o un error inesperado en la capa de acceso a datos<br>
     * <code>sdii_CODE_unknow_error</code> En caso de un error inesperado en la capa de negocio<br>
     * <code>sdii_CODE_lock_acquisition</code> En caso de un error por bloqueo en la base de datos<br>
	 * @author jjimenezh
	 */
	@WebMethod(operationName = "getTimeZonesByCountryId", action = "getTimeZonesByCountryId")
	public List<SdiiTimeZoneVO> getTimeZonesByCountryId(@WebParam(name = "countryId") Long countryId)
			throws BusinessException {
		return timeZoneFacadeBean.getTimeZonesByCountryId(countryId);
	}
	
	/**
	 * Método encargado de generar la fecha del sistema con respecto
	 * al identificador del usuario que se recibe como parametro
	 * @param userId
	 * @return 
	 * @throws BusinessException
	 * @author waguilera
	 */
	@WebMethod(operationName = "getCurrentDateByUserId", action="getCurrentDateByUserId")
    public UserVO getCurrentDateByUserId(@WebParam(name = "userId")Long userId) throws BusinessException {
    	return timeZoneFacadeBean.getCurrentDateByUserId(userId);
    }
}

package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de PostalCodes
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PostalCodesFacadeBeanLocal {

	/**
     * Metodo: Obtiene el código postal pos código
     * @param code código del código postal
     * @return código postal o nulo en caso que no se encuentre
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public PostalCodeVO getPostalCodesByCode(String code) throws BusinessException;

    /**
     * Metodo: Obtiene el código postal por identificador
     * @param id identificador del código postal
     * @return código postal
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public PostalCodeVO getPostalCodesByID(Long id) throws BusinessException;

    /**
     * Metodo: obtiene todos los códigos postales
     * @return lista con todos los códigos postales
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<PostalCodeVO> getAllPostalCodes() throws BusinessException;

    /**
     * Metodo: Obtiene los códigos postales por ciudad
     * @param cityId identificador de ciudad
     * @return
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<PostalCodeVO> getPostalCodesByCityId(Long cityId)throws BusinessException;

    /**
     * Metodo: obtiene los códigos postales por nombre
     * @param name nombre de los códigos postales
     * @return lista con los códigos postales
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public List<PostalCodeVO> getPostalCodesByName(String name) throws BusinessException;

    /**
     * Metodo: Crea un codigo postal
     * @param obj código postal a crear
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void createPostalCode(PostalCodeVO obj) throws BusinessException;

    /**
     * Metodo: Actualiza un código postal
     * @param obj código postal a actualizar
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void updatePostalCode(PostalCodeVO obj) throws BusinessException;

    /**
     * Metodo: Borra un código postal
     * @param obj código postal a actualizar
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
    public void deletePostalCode(PostalCodeVO obj) throws BusinessException;

    /**
     * Metodo: Obtiene todos los códigos postales por país
     * @param countryId identificador del país
     * @return lista con todos los códigos postales de ese país
     * @throws BusinessException En caso de error al ejecutar la operación
     * @author jjimenezh
     */
	public List<PostalCodeVO> getAllPostalCodesByCountryId(Long countryId) throws BusinessException;
	
	/**
	 * Metodo: Consultar PostalCodes por codigo postal y por
	 * el codigo del pais
	 * @param postalCode String
	 * @param countryCode String
	 * @return PostalCode
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PostalCodeVO getPostalCodesByCodeByCountryCode(String postalCode, String countryCode) throws BusinessException;

	/**
	 * Metodo: Permite consultar un código postal por código y código de país 
	 * @param code código del postalCode
	 * @param countryCode código del país
	 * @return código postal dados los códigos
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jjimenezh
	 */
	public PostalCodeVO getPostalCodeByCodeAndCountryCode(String code,
			String countryCode) throws BusinessException;
}

package co.com.directv.sdii.ejb.business.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CityVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * Esta interfaz define los contratos a utilizar para los servicios web
 * del módulo de Configuración de Códigos Postales.
 *
 * En la consulta de un POSTAL_CODES se debe recuperar la referencia a la CITIES y
 * la colección a DEALERS_PARTICIPATION.
 *
 * Caso de Uso CFG - 07 - Gestionar Codigos Postales
 *
 * @author Jimmy Vélez Muñoz
 */
@Local
public interface ConfigPostalCodesBusinessLocal {

    /**
     * Este método retorna una instancia de POSTAL_CODES por ID.
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public PostalCodeVO getPostalCodeByID(Long id) throws BusinessException;

    /**
     * Este método retorna una instancia de POSTAL_CODES por POSTAL_CODE_CODE.
     *
     * @param code
     * @return
     * @throws BusinessException
     */
    public PostalCodeVO getPostalCodeByCode(String code) throws BusinessException;

    /**
     * Este método retorna una lista de POSTAL_CODES por POSTAL_CODE_NAME.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<PostalCodeVO> getPostalCodeByName(String name) throws BusinessException;

    /**
     * Este método retorna una lista de POSTAL_CODES por CITY.
     *
     * @param name
     * @return
     * @throws BusinessException
     */
    public List<PostalCodeVO> getPostalCodeByCity(CityVO city) throws BusinessException;

    /**
     * Este método retorna una lista de Todas los POSTAL_CODES.
     *
     * @return
     * @throws BusinessException
     */
    public List<PostalCodeVO> getAll() throws BusinessException;

    /**
     * Este método crea un POSTAL_CODES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void createPostalCode(PostalCodeVO obj) throws BusinessException;

    /**
     * Este método actualiza una POSTAL_CODES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void updatePostalCode(PostalCodeVO obj) throws BusinessException;

    /**
     * Este método elimina un POSTAL_CODES.
     *
     * @param obj
     * @throws BusinessException
     */
    public void deletePostalCode(PostalCodeVO obj) throws BusinessException;
}

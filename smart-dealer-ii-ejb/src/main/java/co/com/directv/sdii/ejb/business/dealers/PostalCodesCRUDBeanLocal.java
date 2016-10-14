package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad PostalCodes.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PostalCodesCRUDBeanLocal {

    public PostalCodeVO getPostalCodesByCode(String code) throws BusinessException;

    public PostalCodeVO getPostalCodesByID(Long id) throws BusinessException;

    public List<PostalCodeVO> getAllPostalCodes() throws BusinessException;

    public List<PostalCodeVO> getPostalCodesByCityId(Long cityId)throws BusinessException;

    public List<PostalCodeVO> getPostalCodesByName(String name) throws BusinessException;

    public void createPostalCode(PostalCodeVO obj) throws BusinessException;

    public void updatePostalCode(PostalCodeVO obj) throws BusinessException;

    public void deletePostalCode(PostalCodeVO obj) throws BusinessException;

	public List<PostalCodeVO> getAllPostalCodesByCountryId(Long countryId) throws BusinessException;
	
	public PostalCodeVO getPostalCodesByCodeByCountryCode(String postalCode,String countryCode) throws BusinessException;

	public PostalCodeVO getPostalCodesByCodeByCountryId(String postalCode, Long countryId)throws BusinessException;
}

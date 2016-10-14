package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.facade.dealers.PostalCodesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.PostalCodeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad PostalCodes 
 * 
 * Fecha de Creación: Mar 15, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.PostalCodesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.PostalCodesFacadeBeanLocal
 */
@Stateless(name="PostalCodesFacadeBeanLocal",mappedName="ejb/PostalCodesFacadeBeanLocal")
public class PostalCodesFacadeBean implements PostalCodesFacadeBeanLocal {
	
	@EJB(name="PostalCodesCRUDBeanLocal",beanInterface=PostalCodesCRUDBeanLocal.class)
	private PostalCodesCRUDBeanLocal business;

	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad PostalCodes 
	 * @return List<PostalCodesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public List<PostalCodeVO> getAllPostalCodes() throws BusinessException {
		return business.getAllPostalCodes();
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad PostalCodes.
	 * @param code
	 * @return PostalCodesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PostalCodeVO getPostalCodesByCode(String code) throws BusinessException {
		return business.getPostalCodesByCode(code);
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad PostalCodes.
	 * @param id
	 * @return PostalCodesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	public PostalCodeVO getPostalCodesByID(Long id) throws BusinessException {
		return business.getPostalCodesByID(id);
	}

    public List<PostalCodeVO> getPostalCodesByCityId(Long cityId) throws BusinessException {
        return business.getPostalCodesByCityId(cityId);
    }

    public List<PostalCodeVO> getPostalCodesByName(String name) throws BusinessException {
        return business.getPostalCodesByName(name);
    }

    public void createPostalCode(PostalCodeVO obj) throws BusinessException {
        business.createPostalCode(obj);
    }

    public void updatePostalCode(PostalCodeVO obj) throws BusinessException {
        business.updatePostalCode(obj);
    }

    public void deletePostalCode(PostalCodeVO obj) throws BusinessException {
        business.deletePostalCode(obj);
    }

	@Override
	public List<PostalCodeVO> getAllPostalCodesByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllPostalCodesByCountryId(countryId);
	}
	
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
	public PostalCodeVO getPostalCodesByCodeByCountryCode(String postalCode, String countryCode) throws BusinessException {		
		return business.getPostalCodesByCodeByCountryCode(postalCode, countryCode);
	}

	@Override
	public PostalCodeVO getPostalCodeByCodeAndCountryCode(String code,
			String countryCode) throws BusinessException {
		return business.getPostalCodesByCodeByCountryCode(code, countryCode);
	}
		

}

package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.CountriesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.persistence.dao.dealers.CountriesDAOLocal;

/**
 * 
 * EJB que implementa las operaciones Tipo CRUD (Read) de la
 * Entidad Countries
 * Solo implementa operaciones de consulta
 * 
 * Fecha de Creacion: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.persistence.dao.Countriess.CountriesDAOLocal
 * @see co.com.directv.sdii.ejb.business.Countriess.CountriesCRUDBeanLocal
 * 
 */
@Stateless(name="CountriesCRUDBeanLocal",mappedName="ejb/CountriesCRUDBeanLocal")
public class CountriesCRUDBean extends BusinessBase implements CountriesCRUDBeanLocal {

	@EJB(name="CountriesDAOLocal",beanInterface=CountriesDAOLocal.class)
	private CountriesDAOLocal dao;

	 private final static Logger log = UtilsBusiness.getLog4J(CountriesCRUDBean.class);
	
	/**
	 * 
	 * Metodo: Retorna un listado de todos los registros
	 * de la Entidad Countries 
	 * 
	 * @return List<CountriesVO>
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS) 
	public List<CountryVO> getAllCountries() throws BusinessException {
		log.debug("== Inicia getAllCountries/CountriesCRUDBean ==");
		try{
			List<CountryVO> listVO = UtilsBusiness.convertList(dao.getAllCountries(), CountryVO.class);
			return listVO;
		}catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getAllCountries/CountriesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por codigo
	 * de la Entidad Countries.
	 * @param code
	 * @return CountriesVO
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public CountryVO getCountriesByCode(String code) throws BusinessException {
		log.debug("== Inicia getCountriesByID/CountriesCRUDBean ==");
        try {
        	CountryVO CountriesVo = UtilsBusiness.copyObject(CountryVO.class, dao.getCountriesByCode(code));
            return CountriesVo;
        }catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getCountriesByID/CountriesCRUDBean ==");
        }
	}

	/**
	 * 
	 * Metodo: Reorna el resultado de la consulta por ID
	 * de la Entidad Countries.
	 * @param id
	 * @return CountriesVo
	 * @throws BusinessException <tipo> <descripcion>
	 * @author jalopez
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public CountryVO getCountriesByID(Long id) throws BusinessException {
		log.debug("== Inicia getCountriesByID/CountriesCRUDBean ==");
        try {
        	CountryVO CountriesVo = UtilsBusiness.copyObject(CountryVO.class, dao.getCountriesByID(id));
            return CountriesVo;
        } catch(Throwable ex){
        	log.debug("== Error al tratar de ejecutar la operación getDealerParticipationByID/ConfigMatrizCoberturaBusinessBean ==");
        	throw this.manageException(ex);
        }  finally {
            log.debug("== Termina getCountriesByID/CountriesCRUDBean ==");
        }
	}

	
	


}

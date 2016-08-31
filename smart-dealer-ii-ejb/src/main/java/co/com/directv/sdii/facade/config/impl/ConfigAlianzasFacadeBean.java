package co.com.directv.sdii.facade.config.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ConfigAlianzasBusinessLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal;
import co.com.directv.sdii.model.vo.AllianceCompanyVO;
import co.com.directv.sdii.model.vo.AllianceFileVO;
import co.com.directv.sdii.model.vo.AllianceVO;
import co.com.directv.sdii.model.vo.ComercialProductVO;

/**
 * Clase que implementa el contrato de operaciones para los servicios web
 * del módulo de Configuración de Alianzas.
 *
 * Caso de Uso CFG - 18 - Gestionar Alianzas
 *
 * Fecha de Creación: Mar 25, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @author gfandino <a href="mailto:gfandino@intergrupo.com">e-mail</a>
 * @version 1.0
 *
 * @see
 */
@Stateless(name="ConfigAlianzasFacadeLocal",mappedName="ejb/ConfigAlianzasFacadeLocal")
public class ConfigAlianzasFacadeBean implements ConfigAlianzasFacadeLocal {

    @EJB(name="ConfigAlianzasBusinessLocal",beanInterface=ConfigAlianzasBusinessLocal.class)
    private ConfigAlianzasBusinessLocal business;

   
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceByID(java.lang.Long)
     */
    public AllianceVO getAllianceByID(Long id) throws BusinessException {
        return business.getAllianceByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceByCode(java.lang.String)
     */
    public AllianceVO getAllianceByCode(String code) throws BusinessException {
        return business.getAllianceByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceByName(java.lang.String)
     */
    public List<AllianceVO> getAllianceByName(String name) throws BusinessException {
        return business.getAllianceByName(name);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAll()
     */
    public List<AllianceVO> getAll() throws BusinessException {
        return business.getAll();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceByDate(java.util.Date, java.util.Date)
     */
    public List<AllianceVO> getAllianceByDate(Date init, Date end) throws BusinessException {
        return business.getAllianceByDate(init, end);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#createAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    public void createAlliance(AllianceVO obj) throws BusinessException {
        business.createAlliance(obj);
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#createAllianceFile(co.com.directv.sdii.model.vo.AllianceFileVO)
     */
    public void createAllianceFile(AllianceFileVO obj) throws BusinessException {
        business.createAllianceFile(obj);
    }
    
    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceFileByAllianceID(co.com.directv.sdii.model.vo.AllianceVO)
     */
    public AllianceFileVO getAllianceFileByAllianceID(AllianceVO obj) throws BusinessException {
        return business.getAllianceFileByAllianceID(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#updateAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    public void updateAlliance(AllianceVO obj) throws BusinessException {
        business.updateAlliance(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#deleteAlliance(co.com.directv.sdii.model.vo.AllianceVO)
     */
    public void deleteAlliance(AllianceVO obj) throws BusinessException {
        business.deleteAlliance(obj);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#populateEmpresas()
     */
    public List<AllianceCompanyVO> populateEmpresas() throws BusinessException {
        return business.populateEmpresas();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#populateProductosComerciales()
     */
    public List<ComercialProductVO> populateProductosComerciales() throws BusinessException {
        return business.populateProductosComerciales();
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceCompanyByID(java.lang.Long)
     */
    public AllianceCompanyVO getAllianceCompanyByID(Long id) throws BusinessException {
        return business.getAllianceCompanyByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllianceCompanyByCode(java.lang.String)
     */
    public AllianceCompanyVO getAllianceCompanyByCode(String code) throws BusinessException {
        return business.getAllianceCompanyByCode(code);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getComercialProductByID(java.lang.Long)
     */
    public ComercialProductVO getComercialProductByID(Long id) throws BusinessException {
        return business.getComercialProductByID(id);
    }

    /* (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getComercialProductByCode(java.lang.String)
     */
    public ComercialProductVO getComercialProductByCode(String code) throws BusinessException {
        return business.getComercialProductByCode(code);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllAllianceByCountryId(java.lang.Long)
     */
	public List<AllianceVO> getAllAllianceByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllAllianceByCountryId(countryId);
	}

	@Override
	public List<AllianceCompanyVO> getAllAllianceCompanyByCountryId(
			Long countryId) throws BusinessException {
		return business.getAllAllianceCompanyByCountryId(countryId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.config.ConfigAlianzasFacadeLocal#getAllComercialProductsByCountryId(java.lang.Long)
	 */
	public List<ComercialProductVO> getAllComercialProductsByCountryId(
			Long countryId) throws BusinessException {
		return business.getAllComercialProductsByCountryId(countryId);
	}
 
}

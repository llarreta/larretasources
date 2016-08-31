package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.PositionsCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.PositionsFacadeBeanLocal;
import co.com.directv.sdii.model.vo.PositionVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Positions
 * 
 * Fecha de Creaci�n: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="PositionsFacadeBeanLocal",mappedName="ejb/PositionsFacadeBeanLocal")
public class PositionsFacadeBean implements PositionsFacadeBeanLocal {

    @EJB(name="PositionsCRUDBeanLocal",beanInterface=PositionsCRUDBeanLocal.class)
    private PositionsCRUDBeanLocal business;

    /**
     * Crea un nuevo cargo en el sistema
     * @param obj - Positions
     * @throws BusinessException
     */
    public void createPositions(PositionVO obj) throws BusinessException {
        business.createPositions(obj);
    }

    /**
     * Elimina un cargo del sistema
     * @param obj - Positions
     * @throws BusinessException
     */
    public void deletePositions(PositionVO obj) throws BusinessException {
        business.deletePositions(obj);
    }

    /**
     * Obtiene un cargo por el id especificado
     * @param id - Long
     * @return Positions
     * @throws BusinessException
     */
    public PositionVO getPositionsByID(Long id) throws BusinessException {
        return business.getPositionsByID(id);
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo
     * @param code - String
     * @return - Positions
     * @throws BusinessException
     */
    public PositionVO getPositionsByPositionCode(String code) throws BusinessException {
        return business.getPositionsByPositionCode(code);
    }

    /**
     * Actualiza un cargo especifico
     * @param obj - Positions
     * @throws BusinessException
     */
    public void updatePositions(PositionVO obj) throws BusinessException {
        business.updatePositions(obj);
    }

    /**
     * Obtiene todos los cargos existentes en el sistema
     * @return - List<Positions>
     * @throws BusinessException
     */
    public List<PositionVO> getAllPositions() throws BusinessException {
        return business.getAllPositions();
    }

    /**
     * Obtiene un listado de Cargos de un Dealer especifico
     * @param dealerId - Long
     * @return - List<Positions>
     * @throws BusinessException
     */
    public List<PositionVO> getPositionsByDealerId(Long dealerId) throws BusinessException {
        return this.business.getPositionsByDealerId(dealerId);
    }

    /**
     * Obtiene un cargo por el nombre especificado y el dealer id
     * @param id - Long
     * @Param positionName - String
     * @return Positions
     * @throws BusinessException
     */
    public PositionVO getPositionsByNameAndDealerId(String positionName, Long id) throws BusinessException {
        return this.business.getPositionsByNameAndDealerId(positionName, id);
    }

    /**
     * Obtiene un cargo de acuerdo con el codigo del mismo y al dealer especificado
     * @param code - String
     * @param id - Long
     * @return - Positions
     * @throws BusinessException
     */
    public PositionVO getPositionsByPositionCodeAndDealerId(String code, Long id) throws BusinessException {
        return this.business.getPositionsByPositionCodeAndDealerId(code, id);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.PositionsFacadeBeanLocal#getPositionsByName(java.lang.String)
     */
	public List<PositionVO> getPositionsByName(String positionName)
			throws BusinessException {
		
		return business.getPositionsByName(positionName);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.PositionsFacadeBeanLocal#getPositionsByCodeAndNameAndDealerId(java.lang.String, java.lang.String, java.lang.Long)
	 */
	public List<PositionVO> getPositionsByCodeAndNameAndDealerId(String code, String name, Long dealerId) throws BusinessException {
		return business.getPositionsByCodeAndNameAndDealerId(code, name, dealerId);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.facade.dealers.PositionsFacadeBeanLocal#getAllPositionsByCountryId(java.lang.Long)
	 */
	public List<PositionVO> getAllPositionsByCountryId(Long countryId)
			throws BusinessException {
		return business.getAllPositionsByCountryId(countryId);
	}
}

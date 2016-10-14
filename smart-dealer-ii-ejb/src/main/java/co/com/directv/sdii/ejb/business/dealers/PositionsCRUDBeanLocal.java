package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.PositionVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Create,Read, Update, Delete) de la
 * Entidad Positions
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PositionsCRUDBeanLocal {

    public void createPositions(PositionVO obj) throws BusinessException;

    public PositionVO getPositionsByID(Long id) throws BusinessException;

    public PositionVO getPositionsByPositionCode(String code) throws BusinessException;

    public void updatePositions(PositionVO obj) throws BusinessException;

    public void deletePositions(PositionVO obj) throws BusinessException;

    public List<PositionVO> getAllPositions() throws BusinessException;

    /**
	 * Obtiene la lista de cargos por el identificador del país
	 * @param countryId identificador del país
	 * @return Lista de cargos de acuerdo con el país
	 * @throws BusinessException en caso de error al tratar de obtener la lista de cargos
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
	public List<PositionVO> getAllPositionsByCountryId(Long countryId) throws BusinessException;
    
    public List<PositionVO> getPositionsByDealerId(Long dealerId) throws BusinessException;

    public PositionVO getPositionsByNameAndDealerId(String positionName, Long id) throws BusinessException;

    public PositionVO getPositionsByPositionCodeAndDealerId(String code, Long id) throws BusinessException;

	public List<PositionVO> getPositionsByName(String positionName) throws BusinessException;

	public List<PositionVO> getPositionsByCodeAndNameAndDealerId(String code, String name, Long dealerId)throws BusinessException;
}

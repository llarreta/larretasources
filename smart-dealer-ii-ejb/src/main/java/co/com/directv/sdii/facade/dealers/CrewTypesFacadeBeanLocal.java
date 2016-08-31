package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.CrewTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de CrewTypes.
 * Operaciones de solo consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface CrewTypesFacadeBeanLocal {

    public CrewTypeVO getCrewTypesByCode(String code) throws BusinessException;

    public CrewTypeVO getCrewTypesByID(Long id) throws BusinessException;

    public List<CrewTypeVO> getAllCrewTypes() throws BusinessException;
}

package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.Position;

/**
 * 
 * Interface Local para la gestion del CRUD de la
 * Entidad Positions
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface PositionsDAOLocal {

    public void createPositions(Position obj) throws DAOServiceException,
            DAOSQLException;

    public Position getPositionsByID(Long id) throws DAOServiceException,
            DAOSQLException;

    public Position getPositionsByPositionCode(String code)
            throws DAOServiceException, DAOSQLException;

    public void updatePositions(Position obj) throws DAOServiceException,
            DAOSQLException;

    public void deletePositions(Position obj) throws DAOServiceException,
            DAOSQLException;

    public List<Position> getAllPositions() throws DAOServiceException,
            DAOSQLException;

    /**
	 * Obtiene la lista de cargos por el identificador del país
	 * @param countryId identificador del país
	 * @return Lista de cargos de acuerdo con el país
	 * @throws BusinessException en caso de error al tratar de obtener la lista de cargos
	 * @author jjimenezh Agregado por control de cambios 2010-04-23
	 */
    public List<Position> getAllPositionsByCountryId(Long countryId) throws DAOServiceException,
    DAOSQLException;

    
    public List<Position> getPositionsByName(String positionName) throws DAOServiceException, DAOSQLException;

    public List<Position> getPositionsByDealerId(Long dealerId) throws DAOServiceException, DAOSQLException;

    public Position getPositionsByNameAndDealerId(String positionName, Long dealerId) throws DAOServiceException, DAOSQLException;

    public Position getPositionsByPositionCodeAndDealerId(String code, Long dealerId) throws DAOServiceException, DAOSQLException ;

	public List<Position> getPositionsByCodeAndNameAndDealerId(String code, String name, Long dealerId)throws DAOServiceException, DAOSQLException;
}

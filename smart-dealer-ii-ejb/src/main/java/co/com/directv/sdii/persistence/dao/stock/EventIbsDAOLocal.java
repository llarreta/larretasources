package co.com.directv.sdii.persistence.dao.stock;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.EventIbs;

@Local
public interface EventIbsDAOLocal {

	/**
	 * Operacion encargada de obtener una configuracion con respecto al reason y al evento
	 * @param eventCode
	 * @param countryId
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author waguilera
	 */
	public EventIbs getEventbyCode(String eventCode, Long countryId) throws DAOServiceException, DAOSQLException;
	
	
}
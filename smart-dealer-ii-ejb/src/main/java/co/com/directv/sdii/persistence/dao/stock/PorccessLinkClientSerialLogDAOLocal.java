package co.com.directv.sdii.persistence.dao.stock;
import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.vo.PorccessLinkClientSerialLogVO;

@Local
public interface PorccessLinkClientSerialLogDAOLocal {

	/**
	 * Metodo encargado de generar un log del intento de vinculacion de seriales en cliente en core.
	 * @param obj log a guardar
	 * @throws DAOServiceException
	 * @throws DAOSQLException
	 * @author Aharker
	 */
	public void createPorccessLinkClientSerialLog(PorccessLinkClientSerialLogVO obj) throws DAOServiceException, DAOSQLException;
	
}

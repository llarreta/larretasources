package co.com.directv.sdii.persistence.dao.core;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.WoPdfAnnex;

/**
 * 
 * Interface Local para la gestión WoPdfAnnex
 * 
 * Fecha de Creación: Dic 3, 2010
 * @author waltuzarra
 * @version 1.0
 * 
 * @see
 */
@Local
public interface WoPdfAnnexDAOLocal {

	/**
	 * Metodo: Obtiene la información anexos a agregar a una wo que se solicite imprimir
	 * @param id identificador del WoLoad a ser consultado
	 * @return objeto con la información del WoLoad dado su identificador, nulo en caso que no se encuentre
	 * @throws DAOServiceException en caso de error al tratar de ejecutar la operación
	 * @throws DAOSQLException en caso de error al tratar de ejecutar la operación
	 * @author
	 */
	public List<WoPdfAnnex> searchWoPdfAnnexByCriteria(String whereCondition) throws DAOServiceException, DAOSQLException ;

}
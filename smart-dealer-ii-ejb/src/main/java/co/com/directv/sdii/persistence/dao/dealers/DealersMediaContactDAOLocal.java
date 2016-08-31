package co.com.directv.sdii.persistence.dao.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.DealerMediaContact;

/**
 * Interface Local para la gestion del CRUD de la
 * Entidad DealersMediaContact 
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DealersMediaContactDAOLocal {

	/**
	 * Method: Creacion de DealersMediaContact
	 */
	public void createDealerMediaContact(DealerMediaContact obj)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Method: Consulta de DealersMediaContact por ID
	 */
	public DealerMediaContact getDealersMediaContactByID(Long id)
			throws DAOServiceException, DAOSQLException;

	
	/**
	 * Method: actualizacion de DealersMediaContact
	 */
	public void updateDealersMediaContact(DealerMediaContact obj)
			throws DAOServiceException, DAOSQLException;

	/**
	 * Method:eliminacion de DealersMediaContact
	 */
	public void deleteDealersMediaContact(DealerMediaContact obj)throws DAOServiceException, DAOSQLException;

	/**
	 * Method:consulta de todos los DealersMediaContact sin filtro
	 */
	public List<DealerMediaContact> getAllDealersMediaContact()
			throws DAOServiceException, DAOSQLException;

    public List<DealerMediaContact> getDealersMediaContactByDealerId(Long dealerId)
                        throws DAOServiceException, DAOSQLException;

    public void deleteDealersMediaContactByDealerId(Long id)throws DAOServiceException, DAOSQLException;
        
   /**
    * Metodo que consulta los tipos de contacto del operador logistico de un pais
    * @param countryId Long ID del pais en donde se esta buscando el operador logistico
    * @return List<DealerMediaContact> Lista de tipos de contacto de un dealer 
    * @throws DAOServiceException
    * @throws DAOSQLException
    * @author jnova
    */
    public List<DealerMediaContact> getLegalRepresentativeByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;
    
    /**
     * Metodo que consulta los analistas de logistica de un país
     * @param countryId Long ID del pais en donde se esta buscando el operador logistico
     * @return List<DealerMediaContact> Lista de tipos de contacto de un dealer 
     * @throws DAOServiceException en caso de error en la consulta de analistas de logiscica
     * @throws DAOSQLException en caso de error en la consulta de analistas de logiscica
     * @author gfandino
     */
     public List<DealerMediaContact> getLogicalAnaliticByCountryId(Long countryId) throws DAOServiceException, DAOSQLException;
     
     /**
      * 
      * Metodo: Consulta los medios de contacto de un dealer por codigo de dealer
      * @param dealerCode Codigo de dealer
      * @return List<DealerMediaContact> lista de medios de contacto del dealer
      * @throws DAOServiceException
      * @throws DAOSQLException <tipo> <descripcion>
      * @author jnova
      */
     public List<DealerMediaContact> getDealersMediaContactByDealerCode(Long dealerCode) throws DAOServiceException, DAOSQLException;
}
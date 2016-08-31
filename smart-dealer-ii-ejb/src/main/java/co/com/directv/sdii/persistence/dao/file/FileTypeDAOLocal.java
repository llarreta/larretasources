package co.com.directv.sdii.persistence.dao.file;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.DAOSQLException;
import co.com.directv.sdii.exceptions.DAOServiceException;
import co.com.directv.sdii.model.pojo.FileType;

/**
 * Interface Local para la gestion del CRUD de la entidad FileType 
 * 
 * Fecha de Creacion: Jun 29, 2011
 * @author rdelarosa <a href="mailto:rdelarosa@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface FileTypeDAOLocal {
    /**
     * Almacena un empleado en el sistema
     * @param employee - Objeto con la información básica del empleado
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */	
    public void save(FileType transientInstance) throws DAOServiceException, DAOSQLException ;

    /**
     * Obtiene un FileType por su identificador unico
     * @param id - Long
     * @return FileType
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
	public FileType findById(Long id)  throws DAOServiceException, DAOSQLException ;
    
    /**
     * Obtiene un FileType por su codigo unico
     * @param strTypeCode - String Codigo del tipo de archivo que estamos buscando 
     * @return FileType
     * @throws DAOServiceException
     * @throws DAOSQLException 
     */
	public FileType findByCode(String strTypeCode) throws DAOServiceException, DAOSQLException ;

	/**
	 * Metodo: <Descripcion>
	 * @return
	 * @throws DAOServiceException
	 * @throws DAOSQLException <tipo> <descripcion>
	 * @author
	 */
	public List<FileType> getAllFileTypes()throws DAOServiceException, DAOSQLException;

    /*
	public void delete(FileType persistentInstance);
	
	public List findByExample(FileType instance) ;

	public List findByProperty(String propertyName, Object value) ;

	public List findByName(Object name) ;



	public List findByLocation(Object location) ;

	public List findAll() ;

	public FileType merge(FileType detachedInstance) ;

	public void attachDirty(FileType instance) ;

	public void attachClean(FileType instance) ;
	*/
}

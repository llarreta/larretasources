package co.com.directv.sdii.facade.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ContactTypeVO;

/**
 * 
 * Interfaz que define la Session Facade de las operaciones 
 * a realizar para el modulo de ContactType
 * Solo operaciones de consulta.
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ContactTypeFacadeBeanLocal {

	/**
	 * Metodo: Permite consultar un tipo de contacto por su código
	 * @param code - String código del tipo de contacto que se va a consultar
	 * @return ContactTypeVO cuyo código es el especificado; nulo en otro caso
	 * @throws BusinessException 
	 * @author jalopez
	 */
	public ContactTypeVO getContactTypeByCode(String code) throws BusinessException;
	
	/**
	 * Metodo: Permite consultar un tipo de contacto por su identificador
	 * @param id - Long Identificador del tipo de contacto que se va a consultar
	 * @return ContactTypeVO cuyo identificador corresponde al especificado; nulo en otro caso
	 * @throws BusinessException
	 * @author jalopez
	 */
	public ContactTypeVO getContactTypeByID(Long id) throws BusinessException;
	
	/**
	 * Metodo: ermite consultar todos los tipos de contacto
	 * @return List<ContactTypeVO> Tipos de contacto registrados en el sistema; vacio en caso de no existir
	 * @throws BusinessException
	 * @author jalopez
	 */
	public List<ContactTypeVO> getAllContactType() throws BusinessException;
}

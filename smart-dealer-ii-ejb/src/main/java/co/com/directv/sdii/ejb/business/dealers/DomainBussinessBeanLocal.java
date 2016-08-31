package co.com.directv.sdii.ejb.business.dealers;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DomainVO;

/**
 * 
 * Interfaz de las operaciones Tipo CRUD (Read) de la
 * Entidad Arp.
 * Solo operaciones de consulta.
 * 
 * Fecha de Creaci�n: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface DomainBussinessBeanLocal {

	public DomainVO getDomainValue(String domainName, String domainValue, Long countryId) throws BusinessException;
	
	public DomainVO getDomainValue(String domainName, String domainCode, String countryCode) throws BusinessException;
	
	/**
	 * Metodo: Método encargado de consultar una lista dominio para realizar procesos de Homologación
	 * @param domainName
	 * @param countryId
	 * @return
	 * @throws BusinessException <tipo> <descripcion>
	 * @author
	 */
	public List<DomainVO> getDomainByDomainNameDomainValueCountryId(String domainName, Long countryId) throws BusinessException;
}

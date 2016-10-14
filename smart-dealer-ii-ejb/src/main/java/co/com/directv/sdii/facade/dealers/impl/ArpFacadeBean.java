package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ArpCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ArpFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ArpVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad Arp 
 * 
 * Fecha de Creación: Mar 8, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.ArpCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.ArpFacadeBeanLocal
 */
@Stateless(name="ArpFacadeBeanLocal",mappedName="ejb/ArpFacadeBeanLocal")
public class ArpFacadeBean implements ArpFacadeBeanLocal {

    @EJB(name="ArpCRUDBeanLocal",beanInterface=ArpCRUDBeanLocal.class)
    private ArpCRUDBeanLocal business;

    /**
     * Obtiene un listado de las ARP creadas en el sistema
     * @return - List<ArpVO>
     * @throws BusinessException 
     * @author jalopez
     */
    public List<ArpVO> getAllArp() throws BusinessException {
        return business.getAllArp();
    }

    /**
     * Obtiene un registro de ARP dependiendo del id
     * @param code - String
     * @return - Entidad ARPVO
     * @throws BusinessException 
     * @author jalopez
     */
    public ArpVO getArpByCode(String code) throws BusinessException {
        return business.getArpByCode(code);
    }

    /**
     * Obtiene un registro de ARP dependiendo del id
     * @param id - Long
     * @return - Entidad - ArpVO
     * @throws BusinessException 
     * @author jalopez
     */
    public ArpVO getArpByID(Long id) throws BusinessException {
        return business.getArpByID(id);
    }

    /*
     * (non-Javadoc)
     * @see co.com.directv.sdii.facade.dealers.ArpFacadeBeanLocal#getAllArpByCountryId(java.lang.Long)
     */
	public List<ArpVO> getAllArpByCountryId(Long countryId)
			throws BusinessException {
		List<ArpVO> result = business.getAllArpByCountryId(countryId);
		return result;
	}
}

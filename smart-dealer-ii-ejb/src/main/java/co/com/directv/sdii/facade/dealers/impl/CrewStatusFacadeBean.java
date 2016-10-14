package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CrewStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CrewStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CrewStatusVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad CrewStatus 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CrewStatusCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewStatusFacadeBeanLocal
 */
@Stateless(name="CrewStatusFacadeBeanLocal",mappedName="ejb/CrewStatusFacadeBeanLocal")
public class CrewStatusFacadeBean implements CrewStatusFacadeBeanLocal {

    @EJB(name="CrewStatusCRUDBeanLocal",beanInterface=CrewStatusCRUDBeanLocal.class)
    private CrewStatusCRUDBeanLocal business;


    public List<CrewStatusVO> getAllCrewStatus() throws BusinessException {
       return business.getAllCrewStatus();
    }


    public CrewStatusVO getCrewStatusByCode(String code) throws BusinessException {
       return business.getCrewStatusByCode(code);
    }


    public CrewStatusVO getCrewStatusByID(Long id) throws BusinessException {
        return business.getCrewStatusByID(id);
    }
}

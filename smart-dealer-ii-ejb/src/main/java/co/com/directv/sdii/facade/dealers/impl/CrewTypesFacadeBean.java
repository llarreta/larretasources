package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.CrewTypesCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.CrewTypesFacadeBeanLocal;
import co.com.directv.sdii.model.vo.CrewTypeVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad CrewTypes 
 * 
 * Fecha de Creación: Mar 5, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.CrewTypesCRUDBeanLocal
 * @see co.com.directv.sdii.facade.dealers.CrewTypesFacadeBeanLocal
 */
@Stateless(name="CrewTypesFacadeBeanLocal",mappedName="ejb/CrewTypesFacadeBeanLocal")
public class CrewTypesFacadeBean implements CrewTypesFacadeBeanLocal {

    @EJB(name="CrewTypesCRUDBeanLocal",beanInterface=CrewTypesCRUDBeanLocal.class)
    private CrewTypesCRUDBeanLocal business;

    /**
     * Retorna un listado de todos los registros
     * de la Entidad CrewTypes
     *
     * @return List<CrewTypesVO>
     * @throws BusinessException
     */
    public List<CrewTypeVO> getAllCrewTypes() throws BusinessException {
        return business.getAllCrewTypes();
    }

    /**
     * Retorna el resultado de la consulta por codigo
     * de la Entidad CrewTypes.
     * @param code - String
     * @return CrewTypesVO
     * @throws BusinessException
     */
    public CrewTypeVO getCrewTypesByCode(String code) throws BusinessException {
        return business.getCrewTypesByCode(code);
    }

    /**
     * Retorna el resultado de la consulta por ID
     * de la Entidad CrewTypes.
     * @param id
     * @return CrewTypesVo
     * @throws BusinessException
     */
    public CrewTypeVO getCrewTypesByID(Long id) throws BusinessException {
        return business.getCrewTypesByID(id);
    }
}

package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.ProgramCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ProgramFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ProgramVO;

/**
 * 
 * Implementa las operaciones de la entidad de
 * Programs 
 * 
 * Fecha de Creación: 14/05/2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Stateless(name="ProgramFacadeBeanLocal", mappedName="ejb/ProgramFacadeBeanLocal")
public class ProgramFacadeBean implements ProgramFacadeBeanLocal {
	
	@EJB(name="ProgramCRUDBeanLocal", beanInterface=ProgramCRUDBeanLocal.class)
	private ProgramCRUDBeanLocal programCRUDBean;

	@Override
	public List<ProgramVO> getAll() throws BusinessException {
		return programCRUDBean.getAll();
	}

	@Override
	public List<ProgramVO> getProgramsByDealerId(Long dealerId,boolean getAll) throws BusinessException {
		return programCRUDBean.getProgramsByDealerId(dealerId,getAll);
	}

	@Override
	public List<ProgramVO> getProgramsByNameAndDealerId(Long dealerId, String name) throws BusinessException {
		return programCRUDBean.getProgramsByNameAndDealerId(dealerId, name);
	}

	@Override
	public void createProgram(ProgramVO obj) throws BusinessException {
		programCRUDBean.createProgram(obj);
	}

	@Override
	public void deleteProgram(ProgramVO obj) throws BusinessException {
		programCRUDBean.deleteProgram(obj);
		
	}

	@Override
	public ProgramVO getProgramByID(Long id) throws BusinessException {
		return programCRUDBean.getProgramByID(id);
	}

	@Override
	public ProgramVO getProgramsByCode(String ProgramVOCode)
			throws BusinessException {
		return programCRUDBean.getProgramsByCode(ProgramVOCode);
	}

	@Override
	public List<ProgramVO> getProgramsByName(String name)
			throws BusinessException {
		return programCRUDBean.getProgramsByName(name);
	}

	@Override
	public List<ProgramVO> getProgramsByStatusId(Long statusId)
			throws BusinessException {
		return programCRUDBean.getProgramsByStatusId(statusId);
	}

	@Override
	public void updateProgram(ProgramVO obj) throws BusinessException {
		programCRUDBean.updateProgram(obj);
		
	} 
}

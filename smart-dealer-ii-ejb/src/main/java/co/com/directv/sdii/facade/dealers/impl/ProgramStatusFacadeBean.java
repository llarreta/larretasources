package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.ProgramStatusCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.ProgramStatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.ProgramStatusVO;

/**
 * Session Bean implementation class ProgramStatusFacadeBean
 */
@Stateless(name="ProgramStatusFacadeBeanLocal", mappedName="ejb/ProgramStatusFacadeBeanLocal")
public class ProgramStatusFacadeBean implements ProgramStatusFacadeBeanLocal {

	@EJB(beanInterface=ProgramStatusCRUDBeanLocal.class, name="ProgramStatusCRUDBeanLocal")
	private ProgramStatusCRUDBeanLocal statusCRUDBean;
	
	@Override
	public void createProgramStatus(ProgramStatusVO obj)
			throws BusinessException {
		statusCRUDBean.createProgramStatus(obj);
		
	}

	@Override
	public void deleteProgramStatus(ProgramStatusVO obj)
			throws BusinessException {
		statusCRUDBean.deleteProgramStatus(obj);
		
	}

	@Override
	public List<ProgramStatusVO> getAll() throws BusinessException {
		return statusCRUDBean.getAll();
	}

	@Override
	public ProgramStatusVO getProgramStatusByID(Long id)
			throws BusinessException {
		return statusCRUDBean.getProgramStatusByID(id);
	}

	@Override
	public void updateProgramStatus(ProgramStatusVO obj)
			throws BusinessException {
		statusCRUDBean.updateProgramStatus(obj);
	}

    

}

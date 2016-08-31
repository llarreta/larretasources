package co.com.directv.sdii.ejb.business.dealers;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ProgramVO;

@Local
public interface ProgramCRUDBeanLocal {
	public void createProgram(ProgramVO obj) throws BusinessException;

    public ProgramVO getProgramByID(Long id) throws BusinessException;

    public void updateProgram(ProgramVO obj) throws BusinessException;

    public void deleteProgram(ProgramVO obj) throws BusinessException;

    public List<ProgramVO> getAll() throws BusinessException;

    public List<ProgramVO> getProgramsByNameAndDealerId(Long dealerId, String name) throws BusinessException;
    
    public List<ProgramVO> getProgramsByDealerId(Long dealerId,boolean getAll) throws BusinessException;
    
    public List<ProgramVO> getProgramsByStatusId(Long statusId) throws BusinessException;
    
    public ProgramVO getProgramsByCode(String ProgramVOCode) throws BusinessException;
    
    public List<ProgramVO> getProgramsByName(String name) throws BusinessException;

}

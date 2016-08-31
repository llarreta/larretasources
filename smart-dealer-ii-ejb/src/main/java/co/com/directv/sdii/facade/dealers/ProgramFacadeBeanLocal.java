package co.com.directv.sdii.facade.dealers;
import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.ProgramVO;

/**
 * 
 * Interfaz que define las operaciones del componente de 
 * negocio  ProgramFacadeBean
 * 
 * Fecha de Creación: 14/05/2010
 * @author jcasas <a href="jcasas@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@Local
public interface ProgramFacadeBeanLocal {
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

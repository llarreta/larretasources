/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.directv.sdii.facade.config;

import java.util.List;

import javax.ejb.Local;

import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;

/**
 *
 * @author jcasas
 */
@Local
public interface Ibs6StatusFacadeBeanLocal {

    public void createIbs6Status(Ibs6StatusVO obj) throws BusinessException;

    public Ibs6StatusVO getIbs6StatusByID(Long id) throws BusinessException;

    public void updateIbs6Status(Ibs6StatusVO obj) throws BusinessException;

    public void deleteIbs6Status(Ibs6StatusVO obj) throws BusinessException;

    public List<Ibs6StatusVO> getAll() throws BusinessException;
}

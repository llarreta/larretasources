/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.com.directv.sdii.facade.config.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.config.Ibs6StatusBusinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.config.Ibs6StatusFacadeBeanLocal;
import co.com.directv.sdii.model.vo.Ibs6StatusVO;

/**
 * Fachada para consumir los metodos de IBS6Status
 * 
 * @author Jose Andres Casas
 */
@Stateless(name = "Ibs6StatusFacadeBeanLocal", mappedName = "ejb/Ibs6StatusFacadeBeanLocal")
public class Ibs6StatusFacadeBean implements Ibs6StatusFacadeBeanLocal {
	@EJB(name = "Ibs6StatusBusinessBeanLocal", beanInterface = Ibs6StatusBusinessBeanLocal.class)
	private Ibs6StatusBusinessBeanLocal ibs6StatusBusinessBeanBean;

	public void createIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		this.ibs6StatusBusinessBeanBean.createIbs6Status(obj);
	}

	public Ibs6StatusVO getIbs6StatusByID(Long id) throws BusinessException {
		return this.ibs6StatusBusinessBeanBean.getIbs6StatusByID(id);
	}

	public void updateIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		this.ibs6StatusBusinessBeanBean.updateIbs6Status(obj);
	}

	public void deleteIbs6Status(Ibs6StatusVO obj) throws BusinessException {
		this.ibs6StatusBusinessBeanBean.deleteIbs6Status(obj);
	}

	public List<Ibs6StatusVO> getAll() throws BusinessException {
		return this.ibs6StatusBusinessBeanBean.getAll();
	}

}

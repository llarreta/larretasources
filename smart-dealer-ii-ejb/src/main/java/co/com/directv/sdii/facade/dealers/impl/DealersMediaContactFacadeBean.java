package co.com.directv.sdii.facade.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.dealers.DealersMediaContactFacadeBeanLocal;
import co.com.directv.sdii.model.vo.DealerMediaContactVO;

/**
 * 
 * Facade para la gestion de las operaciones del CRUD
 * de la entidad DealersMediaContact
 * 
 * Fecha de Creaci�n: Mar 3, 2010
 * @author jalopez <a href="mailto:jalopez@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see co.com.directv.sdii.ejb.business.dealers.DealersMediaContactCRUDBean
 * @see co.com.directv.sdii.facade.dealers.DealersMediaContactFacadeBeanLocal
 */
@Stateless(name="DealersMediaContactFacadeBeanLocal",mappedName="ejb/DealersMediaContactFacadeBeanLocal")
public class DealersMediaContactFacadeBean implements DealersMediaContactFacadeBeanLocal {

    @EJB(name="DealersMediaContactCRUDBeanLocal",beanInterface=DealersMediaContactCRUDBeanLocal.class)
    private DealersMediaContactCRUDBeanLocal business;
    
    /**
    *
    * Metodo: <Descripcion>
    * @param obj
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   public void createDealerMediaContact(DealerMediaContactVO obj) throws BusinessException {
       business.createDealerMediaContact(obj);

   }

   /**
    *
    * Metodo: <Descripcion>
    * @param obj
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   public void deleteDealersMediaContact(DealerMediaContactVO obj) throws BusinessException {
       business.deleteDealersMediaContact(obj);
   }

   /**
    *
    * Metodo: <Descripcion>
    * @return
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   public List<DealerMediaContactVO> getAllDealersMediaContact() throws BusinessException {
       return business.getAllDealersMediaContact();
   }

   /**
    *
    * Metodo: <Descripcion>
    * @param id
    * @return
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   public DealerMediaContactVO getDealersMediaContactByID(Long id) throws BusinessException {
       return business.getDealersMediaContactByID(id);
   }

   /**
    *
    * Metodo: <Descripcion>
    * @param obj
    * @throws BusinessException <tipo> <descripcion>
    * @author jalopez
    */
   public void updateDealersMediaContact(DealerMediaContactVO obj) throws BusinessException {
       business.updateDealersMediaContact(obj);
   }
}

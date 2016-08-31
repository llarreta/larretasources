package co.com.directv.sdii.ejb.business.dealers.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.UtilsBusiness;
import co.com.directv.sdii.ejb.business.BusinessBase;
import co.com.directv.sdii.ejb.business.dealers.DomainBussinessBeanLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.model.vo.DomainVO;
import co.com.directv.sdii.persistence.dao.dealers.DomainDAOLocal;


@Stateless(name="DomainBussinessBeanLocal",mappedName="ejb/DomainBussinessBeanLocal")
public class DomainBussinessBean extends BusinessBase implements DomainBussinessBeanLocal {

   
    
    @EJB(name="DomainDAOLocal", beanInterface=DomainDAOLocal.class)
    private DomainDAOLocal daoDomain;
    
    private final static Logger log = UtilsBusiness.getLog4J(DomainBussinessBean.class);

	@Override
	public DomainVO getDomainValue(String domainName, String domainValue,
			Long countryId) throws BusinessException {
		log.debug("== Inicia getDomainValue/DomainBussinessBean ==");
        try {
            DomainVO domain = UtilsBusiness.copyObject(DomainVO.class, daoDomain.getDomainValue(domainName, domainValue, countryId));
            return domain;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDomainValue/DomainBussinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDomainValue/DomainBussinessBean ==");
        }
	}

	@Override
	public DomainVO getDomainValue(String domainName, String domainCode,
			String countryCode) throws BusinessException {
		log.debug("== Inicia getDomainValue/DomainBussinessBean ==");
        try {
            DomainVO domain = UtilsBusiness.copyObject(DomainVO.class, daoDomain.getDomainValue(domainName, domainCode, countryCode));
            return domain;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDomainValue/DomainBussinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDomainValue/DomainBussinessBean ==");
        }
	}    
	
	@Override
	public List<DomainVO> getDomainByDomainNameDomainValueCountryId(String domainName, Long countryId) throws BusinessException {
		log.debug("== Inicia getDomainByDomainNameDomainValueCountryId/DomainBussinessBean ==");
        try {
        	List<DomainVO> domain = UtilsBusiness.convertList(daoDomain.getDomainByDomainNameDomainValueCountryId(domainName, countryId),DomainVO.class);
            return domain;
        }catch(Throwable ex){
        	log.error("== Error al tratar de ejecutar la operación getDomainByDomainNameDomainValueCountryId/DomainBussinessBean ==");
        	throw this.manageException(ex);
        } finally {
            log.debug("== Termina getDomainByDomainNameDomainValueCountryId/DomainBussinessBean ==");
        }
	}    
    
}

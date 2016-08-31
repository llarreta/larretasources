package co.com.directv.sdii.facade.allocator.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import co.com.directv.sdii.assign.schedule.dto.ScheduleColorsConfig;
import co.com.directv.sdii.ejb.business.allocator.AllocatorBeanHelperLocal;
import co.com.directv.sdii.exceptions.BusinessException;
import co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal;
import co.com.directv.sdii.facade.allocator.AllocatorFacadeRemote;
import co.com.directv.sdii.model.vo.AllocatorEventMasterVO;
import co.com.directv.sdii.model.vo.CountryVO;
import co.com.directv.sdii.model.vo.CustomerVO;
import co.com.directv.sdii.model.vo.DealerVO;
import co.com.directv.sdii.model.vo.PostalCodeVO;
import co.com.directv.sdii.model.vo.ServiceHourVO;
import co.com.directv.sdii.model.vo.ServiceVO;
import co.com.directv.sdii.model.vo.WorkOrderVO;

/**
 * Session Bean implementation class AllocatorFacadeBean
 */
@Stateless(name="AllocatorFacadeLocal")
@Local({AllocatorFacadeLocal.class})
@Remote({AllocatorFacadeRemote.class})
public class AllocatorFacadeBean implements AllocatorFacadeLocal, AllocatorFacadeRemote {

	@EJB(name="AllocatorBeanHelperLocal", beanInterface=AllocatorBeanHelperLocal.class)
	private AllocatorBeanHelperLocal allocatorBean;
	
	/**
     * Default constructor. 
     */
    public AllocatorFacadeBean() {
    }
    
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#getCapacityDaysParam(co.com.directv.sdii.model.vo.CountryVO)
	 */
	public Long getCapacityDaysParam(CountryVO country) throws BusinessException {
		return allocatorBean.getCapacityDaysParam(country);
	}
	
	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#assignDealer(co.com.directv.sdii.model.vo.WorkOrderVO)
	 */
	@Override
	public DealerVO assignDealer(WorkOrderVO workOrder, AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException {
		return allocatorBean.assignDealer(workOrder,allocatorEventMasterVO);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#getDealerByBuildingAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
	public DealerVO getDealerByBuildingAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealerByBuildingAttend(dealers,customer, postalCode,
				services, date, serviceHour,buildingCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#getDealerByLastServiceAttend(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
	public List<DealerVO> getDealerByLastServiceAttend(List<DealerVO> dealers,
			CustomerVO customer, PostalCodeVO postalCode,
			List<ServiceVO> services, Date date, ServiceHourVO serviceHour,
			String buildingCode) throws BusinessException {
		return allocatorBean.getDealerByLastServiceAttend(dealers,customer, postalCode,
				services, date, serviceHour,buildingCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#getDealersByCustomerTypeAndPostalCode(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
//	public List<DealerVO> getDealersByCustomerTypeAndPostalCode(
//			List<DealerVO> dealers, CustomerVO customer,
//			PostalCodeVO postalCode, List<ServiceVO> services, Date date,
//			ServiceHourVO serviceHour, String buildingCode)
//			throws BusinessException {
//		return allocatorBean.getDealersByCustomerTypeAndPostalCode(dealers, customer,
//				postalCode, services, date,serviceHour, buildingCode);
//	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#getDealersByServicesAndPostalCode(java.util.List, co.com.directv.sdii.model.vo.CustomerVO, co.com.directv.sdii.model.vo.PostalCodeVO, java.util.List, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO, java.lang.Long)
	 */
//	public List<DealerVO> getDealersByServicesAndPostalCode(
//			List<DealerVO> dealers, CustomerVO customer,
//			PostalCodeVO postalCode, List<ServiceVO> services, Date date,
//			ServiceHourVO serviceHour, String buildingCode)
//			throws BusinessException {
//		return allocatorBean.getDealersByServicesAndPostalCode(dealers, customer,
//				postalCode, services,  date,serviceHour,  buildingCode);
//	}

//	/* (non-Javadoc)
//	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#doWorkorderValidations(co.com.directv.sdii.model.vo.WorkOrderVO, java.util.Date, co.com.directv.sdii.model.vo.ServiceHourVO)
//	 */
//	@Override
//	public void doWorkorderValidations(WorkOrderVO wo, Date agendaDate,
//			ServiceHourVO serviceHour) throws BusinessException {
//		allocatorBean.doWorkorderValidations(wo, agendaDate, serviceHour);
//		
//	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#changeProcessStatus2WorkOrder(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String)
	 */
	@Override
	public void changeProcessStatus2WorkOrder(WorkOrderVO workOrder,
			String processStatusCode) throws BusinessException {
		allocatorBean.changeProcessStatus2WorkOrder(workOrder, processStatusCode);
		
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#changeProcessStatus2WorkOrders(java.util.List, java.lang.String)
	 */
	@Override
	public void changeProcessStatus2WorkOrders(List<WorkOrderVO> workOrders,
			String processStatusCode) throws BusinessException {
		allocatorBean.changeProcessStatus2WorkOrders(workOrders, processStatusCode);
	}

	/* (non-Javadoc)
	 * @see co.com.directv.sdii.facade.allocator.AllocatorFacadeLocal#reportAllocatorEvent(co.com.directv.sdii.model.vo.WorkOrderVO, java.lang.String, java.lang.String)
	 */
	@Override
	public void reportAllocatorEvent(WorkOrderVO workorder, String eventCode,
			String eventMessage, AllocatorEventMasterVO allocatorEventMasterVO) throws BusinessException {
		allocatorBean.reportAllocatorEvent(workorder, eventCode, eventMessage,allocatorEventMasterVO);
	}
	
	/**
	 * Metodo: Implementacion de la Logica de asignador para un pais dado
	 * @param countryId id del pais para el que se ejecuta el proceso de asignador
	 * @throws BusinessException excepcion encapsulada en caso de fallar el proceso de asignador para dejar los eventos en la tabla de procesamiento
	 * 								en paralelo de core y asignador
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void allocWorkOrders(Long countryId) throws BusinessException { 
		allocatorBean.allocWorkOrders(countryId);
	}
	
	public ScheduleColorsConfig loadScheduleColorsConfig(Long serviceSuperCategoryByID,Long countryId) throws BusinessException{
		return allocatorBean.loadScheduleColorsConfig(serviceSuperCategoryByID,countryId);
	}

}

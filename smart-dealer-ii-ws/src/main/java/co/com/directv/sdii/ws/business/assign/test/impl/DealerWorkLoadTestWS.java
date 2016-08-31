package co.com.directv.sdii.ws.business.assign.test.impl;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.schedule.DealerWorkLoadCalculator;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkCapacityCriteria;
import co.com.directv.sdii.assign.schedule.dto.DealerWorkLoad;
import co.com.directv.sdii.exceptions.ScheduleException;
import co.com.directv.sdii.ws.business.assign.test.IDealerWorkLoadTest;

/**
 * 
 * Implementacion para test de calculo de carga de dealer 
 * 
 * Fecha de Creación: 7/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM
@WebService(serviceName="DealerWorkLoadTestService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.test.IDealerWorkLoadTest",
		targetNamespace="http://assign.test.business.ws.sdii.directv.com.co/",
		portName="DealerWorkLoadTestWSPort")
@Stateless()
public class DealerWorkLoadTestWS implements IDealerWorkLoadTest {

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IDealerWorkLoadTest#getDealerWorkLoad(co.com.directv.sdii.assign.schedule.DealerWorkCapacityCriteria)
	 */
	@Override
	public DealerWorkLoad getDealerWorkLoad(DealerWorkCapacityCriteria dealerWCCriteria)throws ScheduleException {
		DealerWorkLoadCalculator dealerWorkLoadCalculator = new DealerWorkLoadCalculator(dealerWCCriteria);
		dealerWorkLoadCalculator.run();
		return dealerWorkLoadCalculator.getDealerWorkLoad();
	}

}

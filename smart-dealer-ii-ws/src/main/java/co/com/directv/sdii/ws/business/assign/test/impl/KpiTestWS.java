package co.com.directv.sdii.ws.business.assign.test.impl;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO;
import co.com.directv.sdii.assign.kpi.dto.DealerIndicatorResultDTO;
import co.com.directv.sdii.assign.kpi.impl.AgingIndicator;
import co.com.directv.sdii.assign.kpi.impl.BackLogInDaysIndicator;
import co.com.directv.sdii.assign.kpi.impl.CycleTimeIndicator;
import co.com.directv.sdii.assign.kpi.impl.OnTimeIndicator;
import co.com.directv.sdii.assign.kpi.impl.RetrievalIndicator;
import co.com.directv.sdii.assign.kpi.impl.SoS90Indicator;
import co.com.directv.sdii.exceptions.KpiException;
import co.com.directv.sdii.ws.business.assign.test.IKpiTestWS;

/**
 * 
 * Servicio web que expone el calculo de los KPI 
 * 
 * Fecha de Creación: 3/06/2011
 * @author jnova <a href="mailto:jnova@intergrupo.com">e-mail</a>
 * @version 1.0
 * 
 * @see
 */
@MTOM
@WebService(serviceName="KpiTestWSService",
		endpointInterface="co.com.directv.sdii.ws.business.assign.test.IKpiTestWS",
		targetNamespace="http://assign.test.business.ws.sdii.directv.com.co/",
		portName="KpiTestWSPort")
@Stateless()
@TransactionManagement(TransactionManagementType.CONTAINER)
public class KpiTestWS implements IKpiTestWS {

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateCycleTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateCycleTimeIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		CycleTimeIndicator indicatorReference = new CycleTimeIndicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateAgingIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateAgingIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		AgingIndicator indicatorReference = new AgingIndicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateBackLogInDaysIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateBackLogInDaysIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		BackLogInDaysIndicator indicatorReference = new BackLogInDaysIndicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateOnTimeIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateOnTimeIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		OnTimeIndicator indicatorReference = new OnTimeIndicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateRetrievalIndicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateRetrievalIndicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		RetrievalIndicator indicatorReference = new RetrievalIndicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

	/*
	 * (non-Javadoc)
	 * @see co.com.directv.sdii.ws.business.assign.test.IKpiTestWS#calculateSoS90Indicator(co.com.directv.sdii.assign.kpi.dto.DealerIndicatorDTO)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public DealerIndicatorResultDTO calculateSoS90Indicator(DealerIndicatorDTO dealerIndicatorDto) throws KpiException {
		SoS90Indicator indicatorReference = new SoS90Indicator();
		return indicatorReference.calculateIndicator(dealerIndicatorDto);
	}

}

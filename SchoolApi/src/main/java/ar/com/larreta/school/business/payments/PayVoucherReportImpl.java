package ar.com.larreta.school.business.payments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import ar.com.larreta.mystic.exceptions.PersistenceException;
import ar.com.larreta.mystic.query.Persister;
import ar.com.larreta.reports.PDF;
import ar.com.larreta.school.persistence.Payment;
import ar.com.larreta.stepper.exceptions.BusinessException;
import ar.com.larreta.stepper.impl.StepImpl;
import ar.com.larreta.stepper.messages.TargetedBody;
import ar.com.larreta.tools.Base64;

@Service(PayVoucherReport.NAME)
@Transactional
public class PayVoucherReportImpl extends StepImpl implements PayVoucherReport {

	@Value("classpath:ar/com/larreta/school/reports/payVoucher.jrxml")
	private Resource template;
	
	@Autowired
	private Base64 base64;
	
	@Override
	public Serializable execute(Serializable source, Serializable target, Object... args) throws BusinessException, PersistenceException {
		TargetedBody body = (TargetedBody) source;
		
		Persister persister = applicationContext.getBean(Persister.class);
		Payment payment = (Payment) persister.get(Payment.class, body.getTarget());
		
		PDF pdf = applicationContext.getBean(PDF.class);
		ByteArrayOutputStream stream = null;
		try {
			stream = pdf.getOutputStream(template, Arrays.asList(payment));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return base64.encode(stream.toByteArray());
	}

}

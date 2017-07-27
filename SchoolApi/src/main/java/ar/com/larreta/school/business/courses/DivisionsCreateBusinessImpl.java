package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Division;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(DivisionsCreateBusiness.BUSINESS_NAME)
@Transactional
public class DivisionsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Division> implements DivisionsCreateBusiness {

}

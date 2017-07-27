package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Division;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(DivisionsLoadBusiness.BUSINESS_NAME)
@Transactional
public class DivisionsBusinessImpl extends LoadBusinessImpl<ParametricData, Division> implements LevelsLoadBusiness {

}

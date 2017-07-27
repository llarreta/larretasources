package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Year;
import ar.com.larreta.stepper.impl.CreateBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(YearsCreateBusiness.BUSINESS_NAME)
@Transactional
public class YearsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Year> implements YearsCreateBusiness {

}

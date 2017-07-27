package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.school.persistence.Year;
import ar.com.larreta.stepper.impl.LoadBusinessImpl;
import ar.com.larreta.stepper.messages.ParametricData;

@Service(YearsLoadBusiness.BUSINESS_NAME)
@Transactional
public class YearsLoadBusinessImpl extends LoadBusinessImpl<ParametricData, Year> implements YearsLoadBusiness {

}

package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Year;

@Service(YearsCreateBusiness.BUSINESS_NAME)
@Transactional
public class YearsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Year> implements YearsCreateBusiness {

}

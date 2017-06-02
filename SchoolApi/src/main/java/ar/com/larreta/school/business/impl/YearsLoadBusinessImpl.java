package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.rest.messages.ParametricBody;
import ar.com.larreta.school.business.YearsLoadBusiness;
import ar.com.larreta.school.persistence.Year;

@Service(YearsLoadBusiness.BUSINESS_NAME)
@Transactional
public class YearsLoadBusinessImpl extends LoadBusinessImpl<ParametricBody, Year> implements YearsLoadBusiness {

}

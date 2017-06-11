package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Division;

@Service(DivisionsLoadBusiness.BUSINESS_NAME)
@Transactional
public class DivisionsBusinessImpl extends LoadBusinessImpl<ParametricData, Division> implements LevelsLoadBusiness {

}

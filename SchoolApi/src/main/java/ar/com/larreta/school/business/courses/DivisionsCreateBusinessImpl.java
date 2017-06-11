package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Division;

@Service(DivisionsCreateBusiness.BUSINESS_NAME)
@Transactional
public class DivisionsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Division> implements DivisionsCreateBusiness {

}

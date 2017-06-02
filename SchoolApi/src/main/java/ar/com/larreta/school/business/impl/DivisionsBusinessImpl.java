package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.rest.messages.ParametricBody;
import ar.com.larreta.school.business.DivisionsLoadBusiness;
import ar.com.larreta.school.business.LevelsLoadBusiness;
import ar.com.larreta.school.persistence.Division;

@Service(DivisionsLoadBusiness.BUSINESS_NAME)
@Transactional
public class DivisionsBusinessImpl extends LoadBusinessImpl<ParametricBody, Division> implements LevelsLoadBusiness {

}

package ar.com.larreta.school.business.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.LoadBusinessImpl;
import ar.com.larreta.rest.messages.ParametricBody;
import ar.com.larreta.school.business.LevelsLoadBusiness;
import ar.com.larreta.school.persistence.Level;

@Service(LevelsLoadBusiness.BUSINESS_NAME)
@Transactional
public class LevelsLoadBusinessImpl extends LoadBusinessImpl<ParametricBody, Level> implements LevelsLoadBusiness {

}

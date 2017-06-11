package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.LoadParametricEntityBusinessImpl;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Level;

@Service(LevelsLoadBusiness.BUSINESS_NAME)
@Transactional
public class LevelsLoadBusinessImpl extends LoadParametricEntityBusinessImpl<ParametricData, Level> implements LevelsLoadBusiness {

}

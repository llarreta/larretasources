package ar.com.larreta.school.business.courses;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.rest.business.impl.CreateBusinessImpl;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Level;

@Service(LevelsCreateBusiness.BUSINESS_NAME)
@Transactional
public class LevelsCreateBusinessImpl extends CreateBusinessImpl<ParametricData, Level> implements LevelsCreateBusiness {


}

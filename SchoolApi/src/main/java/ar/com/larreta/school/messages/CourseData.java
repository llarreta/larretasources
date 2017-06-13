package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.ParametricData;

@Component @Scope("prototype")
public class CourseData extends Body {

	private Long id;

	private ParametricData level;

	private ParametricData year;

	private ParametricData division;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ParametricData getLevel() {
		return level;
	}

	public void setLevel(ParametricData level) {
		this.level = level;
	}

	public ParametricData getYear() {
		return year;
	}

	public void setYear(ParametricData year) {
		this.year = year;
	}

	public ParametricData getDivision() {
		return division;
	}

	public void setDivision(ParametricData division) {
		this.division = division;
	}
	
}

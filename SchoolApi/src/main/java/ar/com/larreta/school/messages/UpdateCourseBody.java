package ar.com.larreta.school.messages;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.school.persistence.Division;
import ar.com.larreta.school.persistence.Level;
import ar.com.larreta.school.persistence.Year;
import ar.com.larreta.validators.annotations.Exist;
import ar.com.larreta.validators.annotations.NotExist;
import ar.com.larreta.validators.annotations.NotNull;
import ar.com.larreta.validators.annotations.OnlyOne;

@Component @Scope("prototype")
public class UpdateCourseBody extends Body {

	@NotNull(message="id.required", avaiableActions={"update"})
	private Long id;

	@NotNull(message="level.required")
	@OnlyOne(message="level.onlyone", field="id", linkedField="description", required=true)
	@NotExist(message="level.existent", entityType=Level.class, field="description", linkedField="description")
	@Exist(message="level.inexistent", entityType=Level.class, field="id", linkedField="id")
	private ParametricData level;

	@NotNull(message="year.required")
	@OnlyOne(message="year.onlyone", field="id", linkedField="description", required=true)
	@NotExist(message="year.existent", entityType=Year.class, field="description", linkedField="description")
	@Exist(message="year.inexistent", entityType=Year.class, field="id", linkedField="id")
	private ParametricData year;

	@NotNull(message="division.required")
	@OnlyOne(message="division.onlyone", field="id", linkedField="description", required=true)
	@NotExist(message="division.existent", entityType=Division.class, field="description", linkedField="description")
	@Exist(message="division.inexistent", entityType=Division.class, field="id", linkedField="id")
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

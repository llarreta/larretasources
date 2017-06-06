package ar.com.larreta.school.messages;

import ar.com.larreta.rest.messages.Body;
import ar.com.larreta.rest.messages.ParametricData;
import ar.com.larreta.validators.annotations.NotNull;
import ar.com.larreta.validators.annotations.OnlyOne;

public class UpdateCourseBody extends Body {

	@NotNull(message="id.required", avaiableActions={"update"})
	private Long id;

	@NotNull(message="level.required")
	@OnlyOne(message="level.onlyone", field="id", linkedField="description", required=true)
	private ParametricData level;

	@NotNull(message="year.required")
	@OnlyOne(message="year.onlyone", field="id", linkedField="description", required=true)
	private ParametricData year;

	@NotNull(message="division.required")
	@OnlyOne(message="division.onlyone", field="id", linkedField="description", required=true)
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

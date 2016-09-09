package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.DocumentType;
import ar.com.larreta.colegio.domain.Responsible;
import ar.com.larreta.colegio.domain.Student;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;
import ar.com.larreta.screens.validators.Validator;

@Component
public class ResponsibleSaver extends ABMSaver {

	public ResponsibleSaver() {
		super();
		updateScreen.setLazyProperties("documentType,students.course.level,students.course.year,students.course.division");
	}

	@Override
	public Class getABMClass() {
		return Responsible.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.colegio.surname", "surname", Validator.REQUIRED);
		index = screen.addInput(index, "app.colegio.name", "name", Validator.REQUIRED);
		index = screen.addCombo(index, "app.colegio.documentType", 		"documentType", 		DocumentType.class.getName(), Validator.REQUIRED);
		index = screen.addInput(index, "app.colegio.documentNumber", "documentNumber", Validator.REQUIRED);
		index = screen.addInput(index, "app.colegio.cbu", "cbu");
		index = screen.addInput(index, "app.colegio.cuil", "cuil");
		index = screen.addMultiBox(index, "app.colegio.students.avaiables", "app.colegio.students.assigned", "students", 
										Student.class.getName(), "info", "course,course.level,course.year,course.division");
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("surname", 	"app.colegio.surname", 		"tableElement.surname", 	"40%"));
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("name", 		"app.colegio.name", 		"tableElement.name", 		"40%"));
	}

}

package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Course;
import ar.com.larreta.colegio.domain.DocumentType;
import ar.com.larreta.colegio.domain.Responsible;
import ar.com.larreta.colegio.domain.Student;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

@Component
public class StudentSaver extends ABMSaver {

	public StudentSaver() {
		super();
		updateScreen.setLazyProperties("documentType,course");
		updateScreen.setLazyCollections("responsibles");
	}

	@Override
	public Class getABMClass() {
		return Student.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.colegio.surname", "surname");
		index = screen.addInput(index, "app.colegio.name", "name");
		index = screen.addCombo(index, "app.colegio.documentType", 		"documentType", 		DocumentType.class.getName());
		index = screen.addInput(index, "app.colegio.documentNumber", "documentNumber");
		index = screen.addCombo(index, "app.colegio.course", 		"course", 		Course.class.getName(), "level,year,division");
		index = screen.addMultiBox(index, "app.colegio.responsibles.avaiables", "app.colegio.responsibles.assigned", "responsibles", Responsible.class.getName(), "info", "documentType");
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("surname", 	"app.colegio.surname", 		"tableElement.surname", 	"40%"));
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("name", 		"app.colegio.name", 		"tableElement.name", 		"40%"));
	}

}

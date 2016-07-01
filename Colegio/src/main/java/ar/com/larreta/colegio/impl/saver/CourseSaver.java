package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.colegio.domain.Course;
import ar.com.larreta.colegio.domain.Division;
import ar.com.larreta.colegio.domain.Level;
import ar.com.larreta.colegio.domain.Year;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

@Component
public class CourseSaver extends ABMSaver {

	@Override
	public Class getABMClass() {
		return Course.class;
	}

	@Override
	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addCombo(index, "app.colegio.level", 		"level", 		Level.class.getName());
		index = screen.addCombo(index, "app.colegio.year", 			"year", 		Year.class.getName());
		index = screen.addCombo(index, "app.colegio.division", 		"division", 	Division.class.getName());
		
	}

	@Override
	protected void makeColumn(MainScreen screen) {
		screen.getTable().setLazyProperties("level,year,division");
		screen.getTable().addColumn(0, screen.getColumnWithLabelProperty("level", 		"app.level", 			"tableElement.level", 	""));
		screen.getTable().addColumn(1, screen.getColumnWithLabelProperty("year", 		"app.year", 			"tableElement.year", 	""));
		screen.getTable().addColumn(2, screen.getColumnWithLabelProperty("division", 	"app.division", 		"tableElement.division", 	""));
	}

}

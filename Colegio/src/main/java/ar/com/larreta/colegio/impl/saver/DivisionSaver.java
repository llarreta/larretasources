package ar.com.larreta.colegio.impl.saver;

import org.springframework.stereotype.Component;

import ar.com.larreta.commons.domain.Country;
import ar.com.larreta.screens.impl.CreateScreen;
import ar.com.larreta.screens.impl.MainScreen;
import ar.com.larreta.screens.impl.UpdateScreen;
import ar.com.larreta.screens.impl.saver.ABMSaver;

@Component
public class DivisionSaver extends ABMSaver {
	private Class abmClass = Country.class;
	
	public DivisionSaver() {
		super();

		mainScreen = new MainScreen(ColegioIds.DIVISION_MAIN, abmClass) {
			
			@Override
			protected void makeColumns() {
				table.addColumn(0, getColumnWithLabelProperty("abbreviation", 	"app.abbreviation", 	"tableElement.abbreviation",  	"40%"));
				table.addColumn(1, getColumnWithLabelProperty("description", 	"app.description", 		"tableElement.description", 	"40%"));
			}
			
			@Override
			public Long getCreateScreenId() {
				return ColegioIds.DIVISION_CREATE;
			}

			@Override
			public Long getUpdateScreenId() {
				return ColegioIds.DIVISION_UPDATE;
			}
		};
		
		
		createScreen = new CreateScreen(ColegioIds.DIVISION_CREATE, abmClass) {
			
			@Override
			protected void makeBody() {
				DivisionSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ColegioIds.DIVISION_MAIN;
			}
		};
		
		updateScreen = new UpdateScreen(ColegioIds.DIVISION_UPDATE, abmClass) {
			
			@Override
			protected void makeBody() {
				DivisionSaver.this.makeBody(this);
			}
			
			@Override
			public Long getNextScreenId() {
				return ColegioIds.DIVISION_MAIN;
			}
		};
		
	}

	protected void makeBody(CreateScreen screen) {
		Integer index = -1;
		index = screen.addInput(index, "app.abbreviation", "abbreviation");
		index = screen.addInput(index, "app.description", "description");
	}


}

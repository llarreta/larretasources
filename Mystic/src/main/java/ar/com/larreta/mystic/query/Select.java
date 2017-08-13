package ar.com.larreta.mystic.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ar.com.larreta.tools.Const;

@Component @Scope(Const.PROTOTYPE)
public class Select extends Query {
	@Autowired
	public void setInstruction(SelectInstruction instruction) {
		super.setInstruction(instruction);
	}
}

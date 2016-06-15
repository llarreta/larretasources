package ar.com.larreta.commons.services.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ar.com.larreta.commons.domain.Message;
import ar.com.larreta.commons.exceptions.NotImplementedException;
import ar.com.larreta.commons.persistence.dao.args.LoadArguments;
import ar.com.larreta.commons.services.MessageService;

@Service
@Transactional
public class MessageServiceImpl extends StandardServiceImpl implements MessageService {
	
	public Collection load() throws NotImplementedException {
		try {
			LoadArguments args = new LoadArguments(Message.class);
			args.addProjectedProperties("from");
			args.addDescOrder("date");
			return dao.load(args);
		} catch (Exception e){
			getLog().error("Ocurrio un error en el load", e);
		}
		return null;
	}

}

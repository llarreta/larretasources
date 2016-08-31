package co.com.directv.sdii.model.dto;

import org.apache.log4j.Logger;

import co.com.directv.sdii.common.util.PropertiesReader;
import co.com.directv.sdii.exceptions.PropertiesException;

/**
 * Modela un mensaje de codigos de error
 * @author Leonardo Cardozo Cadavid
 */
public final class Message {

    private String messageCode;
    private final static Logger log = Logger.getLogger(Message.class);


    private Message(String messageCode){
        this.messageCode= messageCode;
    }

    public final static Message getMessage(String messageCode) {
        return new Message(messageCode);
    }

    /**
     * Lee el archivo de propiedades y retorna el mensaje
     * @return String
     * @throws PropertiesException
     */
    public String getMessageDesc() throws PropertiesException {
        if(messageCode!=null)
            return PropertiesReader.getInstance().getKey("sdii_MSG_DEALER_"+messageCode);
        log.warn("No hay un codigo de error indentificado para "+messageCode);
        return "";
    }


    public String getMessageCode() {
        return messageCode;
    }


    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

 

}

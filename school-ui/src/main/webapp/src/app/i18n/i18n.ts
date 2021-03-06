import { Logger } from '../Logger/logger';

export class I18n{    
    private static readonly MESSAGES={ 
        message: {
            commonsName: {
                ES: "Nombre",
                US: "Name"
            },
            commonsSurname: {
                ES: "Apellido",
                US: "Surname"
            },
            commonsEmail: {
                ES: "Email",
                US: "Email"
            },
            commonsDocumentType: {
                ES: "Tipo de documento",
                US: "Document Type"
            },
            commonsDocumentNumber: {
                ES: "Numero de Documento",
                US: "Document Number"
            },
            commonsLevel: {
                ES: "Nivel",
                US: "Level"
            },
            commonsDivision: {
                ES: "Division",
                US: "Division"
            },
            commonsYear: {
                ES: "Año",
                US: "Year"
            },
            DEFAULT: {
                ES: "No existe la key I18n."
            }
        }
    };

    public static getMessage(code, language){
        let key = this.MESSAGES.message[code];
        if(key != null){
            let messageLanguage = key[language];
            if(messageLanguage != null){
                return messageLanguage;
            }else{
                return this.MESSAGES.message.DEFAULT.ES
            }
        }else{
            Logger.warn("No existe la key: " + code + " en I18N...");
            return this.MESSAGES.message.DEFAULT.ES;
        }
    }
}
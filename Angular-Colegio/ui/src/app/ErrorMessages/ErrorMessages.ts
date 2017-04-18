export class ErrorMessages{    
    private static readonly MESSAGES={ 
        error: {
            BAD: {
                ES: "Ocurrio un error inesperado, uno de los campos llego vacio. Codigo de error:BAD. Contactese con el administrador."
            },
            DEFAULT: {
                ES: "Ocurrio un error inesperado en el servidor. Contactese con el administrador."
            }
        }
    };

    public static getMessageError(codeError, language){
        let error = this.MESSAGES.error[codeError];
        if(error != null){
            let messageLanguage = error[language];
            if(messageLanguage != null){
                return messageLanguage;
            }else{
                return this.MESSAGES.error.DEFAULT.ES
            }
        }else{
            return this.MESSAGES.error.DEFAULT.ES;
        }
    }
}
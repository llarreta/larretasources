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

    public static getMessageError(codeStatus){
        switch(codeStatus){
            case "BAD-ES":
                return this.MESSAGES.error.BAD.ES;
            
            default: 
                return this.MESSAGES.error.DEFAULT.ES;
        }
    }
}
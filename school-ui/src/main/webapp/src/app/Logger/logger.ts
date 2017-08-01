export class Logger{    

    private warn: boolean = true;
    private info: boolean = true;
    private error: boolean = true;
    private debug: boolean = true;

    public static warn(text){
        if(this.warn){
            console.warn(text);
        }
    }

    public static info(text){
        if(this.info){
            console.info(text);
        }
    }

    public static error(text){
        if(this.error){
            console.error(text);
        }
    }

    public static debug(text){
        if(this.debug){
            console.debug(text);
        }
    }

}
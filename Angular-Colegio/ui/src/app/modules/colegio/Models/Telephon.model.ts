export class Telephone {

    public telephoneType: number;
    public telephone: TelephoneNumber;
    
    constructor() {}
    
    public createInstanceTelephoneNumber(){
        this.telephone = new TelephoneNumber();
    }

}

class TelephoneNumber {

    public id: number;
    public number: string;

    constructor() {}
    
}
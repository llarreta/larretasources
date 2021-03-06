import { Entity } from './Entity.model';

export class Telephone extends Entity{

    public telephoneType: number;
    public telephone: TelephoneNumber;
    
    constructor() {
        super();
    }
    
    public createInstanceTelephoneNumber(){
        this.telephone = new TelephoneNumber();
    }

}

class TelephoneNumber {

    public id: number;
    public number: string;

    constructor() {}
    
}
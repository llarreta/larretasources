import { Entity } from './Entity.model';

export class Email extends Entity{

    public emailType: number;
    public email: EmailAddress;
    
    constructor() {
        super();
    }
    
    public createInstanceEmailAddress(){
        this.email = new EmailAddress();
    }

}

class EmailAddress {

    public id: number;
    public address: string;

    constructor() {}
    
}
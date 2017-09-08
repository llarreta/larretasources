export class Email {

    public emailType: number;
    public email: EmailAddress;
    
    constructor() {}
    
    public createInstanceEmailAddress(){
        this.email = new EmailAddress();
    }

}

class EmailAddress {

    public id: number;
    public address: string;

    constructor() {}
    
}
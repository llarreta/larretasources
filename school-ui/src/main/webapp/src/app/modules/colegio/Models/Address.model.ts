export class AddressP {

    public addressType: number;
    public address: Address;
    
    constructor() {}
    
    public createInstanceAddress(){

        this.address = new Address();
        
    }

}

class Address {

    public id: number;
    public street: string;   
    public number: number;
    public floor: number;
    public department: string;
    public country: number;
    public state: number;
    public location: number;
    public postalCode: string;

    constructor() {}
    
}
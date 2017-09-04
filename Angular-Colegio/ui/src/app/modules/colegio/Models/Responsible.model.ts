import { Email } from './Email.model';
import { Telephone } from './Telephon.model';

export class ResponsibleP{
    
    public responsibleType: number;
    public responsible: Responsible;

    constructor() {}

    public createInstanceResponsible(){
        
        this.responsible = new Responsible();
        
    }
    
}

class Responsible{

    public id: number;
    public name: string;
    public surname: string;
    public documentType: number;
    public documentNumber: string;
    public photo: any;
    public birthdate: Date;
    public nationality: number;
    public profession: string;
    public emails: Array<Email>;
    public telephones: Array<Telephone>;
    public cbu: string;
    public cuil: string;
    
    constructor() {}

}
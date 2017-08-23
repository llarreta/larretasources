import { Entity } from './Entity.model';
import { Course } from './Course.model';
import { PaymentPlan } from './PaymentPlan.model';
import { ObligationStatus } from './ObligationStatus.model';
import { Responsible } from './Responsible.model';
import { Email } from './Email.model';
import { Telephone } from './Telephon.model';
import { AddressP } from './Address.model';

export class Student extends Entity{

    public name: string;
    public surname: string;
    public documentType: number;
    public documentNumber: string;
    public photo: any;
    public course: Course; 
    public birthdate: Date;
    public nationality: number;
    public code: string;
    public paymentPlans: Array<number>;
    public emails: Array<Email>;
    public telephones: Array<Telephone>;
    public addresses: Array<AddressP>;

    public obligationsStatus: Array<number>;
    public responsibles: Array<Responsible>;

    constructor() {
        super();
    }
}
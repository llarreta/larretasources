import { Entity } from './Entity.model';
import { Course } from './Course.model';
import { PaymentPlan } from './PaymentPlan.model';
import { ObligationStatus } from './ObligationStatus.model';
import { Responsible } from './Responsible.model';

export class Student extends Entity{

    public name: string;
    public surname: string;
    public documentType: string;
    public documentNumber: number;
    public email: string;
    public course: Course; 
    public paymentPlans: Array<PaymentPlan>;
    public obligationsStatus: Array<ObligationStatus>;
    public responsibles: Array<Responsible>;
    
    constructor() {
        super();
    }
}
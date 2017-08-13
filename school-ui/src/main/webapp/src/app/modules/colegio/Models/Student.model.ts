import { Entity } from './Entity.model';
import { Course } from './Course.model';
import { PaymentPlan } from './PaymentPlan.model';
import { ObligationStatus } from './ObligationStatus.model';
import { Responsible } from './Responsible.model';

export class Student extends Entity{

    public name: string;
    public surname: string;
    public documentType: number;
    public documentNumber: string;
    public email: string;
    public course: Course; 
    public paymentPlans: Array<number>;
    public obligationsStatus: Array<number>;
    public responsibles: Array<Responsible>;
    public photo: any;
    
    constructor() {
        super();
    }
}
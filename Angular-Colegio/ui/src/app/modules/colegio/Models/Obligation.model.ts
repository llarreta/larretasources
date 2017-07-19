import { Entity } from './Entity.model';
import { Detail } from './Detail.model';
import { PaymentPlan } from './PaymentPlan.model';

export class Obligation extends Entity{

    public dueDate: Date;
    public details: Array<Detail>;
    public paymentPlan: PaymentPlan;

    constructor() {
        super();
    }
}
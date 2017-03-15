import { Entity } from './Entity.model';
import { Price } from './Price.model';
import { PaymentPlan } from './PaymentPlan.model';

export class Obligation extends Entity{

    public dueDate: Date;
    public prices: Array<Price>;
    public paymentPlan: PaymentPlan;

    constructor() {
        super();
    }
}
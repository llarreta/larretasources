import { Entity } from './Entity.model';
import { Obligation } from './Obligation.model';

export class PaymentPlan extends Entity{

    public obligations: Array<Obligation>;

    constructor() {
        super();
    }
}
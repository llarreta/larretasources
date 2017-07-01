import { Entity } from './Entity.model';

export class PaymentRecord extends Entity{

    public student: number;
    public paidOff: boolean;
    public productGroup: number;
    public dueDate: Date;

    constructor() {
        super();
    }
}
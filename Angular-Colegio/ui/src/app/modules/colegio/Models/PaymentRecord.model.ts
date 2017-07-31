import { Entity } from './Entity.model';
import { Detail } from './Detail.model';

export class PaymentRecord extends Entity{

    public student: number;
    public paidOff: boolean;
    public productGroup: number;
    public dueDate: Date;
    public details: Array<Detail>;

    constructor() {
        super();
    }
}
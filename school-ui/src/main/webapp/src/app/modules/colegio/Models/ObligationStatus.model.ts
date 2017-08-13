import { Entity } from './Entity.model';
import { Student } from './Student.model';
import { Obligation } from './Obligation.model';

export class ObligationStatus extends Entity{

    public obligation: Obligation;
    public student: Student;
    public paidOff: boolean;

    constructor() {
        super();
    }
}
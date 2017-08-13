import { Entity } from './Entity.model';
import { Student } from './Student.model';

export class Responsible extends Entity{

    public cbu: string;
    public cuil: number;
    public students: Array<Student>;

    constructor() {
        super();
    }
}
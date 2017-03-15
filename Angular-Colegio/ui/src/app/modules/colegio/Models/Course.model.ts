import { Entity } from './Entity.model';

export class Course extends Entity{

    public level: string;
    public year: string;
    public division: string;

    constructor() {
        super();
    }
}
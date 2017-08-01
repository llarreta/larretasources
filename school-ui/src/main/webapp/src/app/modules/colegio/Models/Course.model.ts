import { Entity } from './Entity.model';
import { Level } from './Level.model';
import { Year } from './Year.model';
import { Division } from './Division.model';

export class Course extends Entity{

    public level: Level;
    public year: Year;
    public division: Division;

    constructor() {
        super();
    }
}
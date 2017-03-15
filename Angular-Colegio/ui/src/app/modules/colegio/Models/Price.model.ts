import { Entity } from './Entity.model';
import { Detail } from './Detail.model';

export class Price extends Entity{

    public validityStartDate: Date;
    public value: number;
    public details: Array<Detail>;

    constructor() {
        super();
    }
}
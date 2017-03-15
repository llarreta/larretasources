import { Entity } from './Entity.model';
import { LittleDetail } from './LittleDetail.model';
import { Price } from './Price.model';

export class Detail extends Entity{

    public value: number;
    public littleDetails: Array<LittleDetail>;

    constructor() {
        super();
    }
    
}
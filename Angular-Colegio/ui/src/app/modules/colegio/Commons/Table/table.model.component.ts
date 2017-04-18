import { ColumnModel } from './column.model.component';

export class TableModel{

    public values: any;
    public columns: Array<ColumnModel>;
    public paginator: number;
    public valuesForView: any;
    public globalFilter: boolean;
    public inputGlobalFilter: string;

    constructor() {}

    public isGlobalFilterActive(){
        return ((this.inputGlobalFilter != null) && (this.inputGlobalFilter != ""));
    }
}


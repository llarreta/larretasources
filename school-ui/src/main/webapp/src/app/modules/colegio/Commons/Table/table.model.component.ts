import { ColumnModel } from './column.model.component';

export class TableModel{

    public values: Array<string>;
    public columns: Array<ColumnModel>;
    public paginator: number;
    public globalFilter: boolean;
    public inputGlobalFilter: string;
    public emptyMessage: string;

    constructor() {}

    public isGlobalFilterActive(){
        return ((this.inputGlobalFilter != null) && (this.inputGlobalFilter != ""));
    }
}


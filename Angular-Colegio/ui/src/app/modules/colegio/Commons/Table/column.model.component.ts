import { ButtonTableModel } from './button.table.model.component';

export class ColumnModel{

    public key: string;
    public header: string;
    public filterText: boolean;
    public sortable: boolean;
    public filterSelectOption: boolean;
    public inputFilter: string;
    public optionsFilterSelect: Array<String>;
    public classStyle: string;
    public columnButton: boolean;
    public buttons: Array<ButtonTableModel>;
    private filterActive: boolean;
    
    constructor() {}

    public isFilterActive(){
        return ((this.inputFilter != null) && (this.inputFilter != ""));
    }
}


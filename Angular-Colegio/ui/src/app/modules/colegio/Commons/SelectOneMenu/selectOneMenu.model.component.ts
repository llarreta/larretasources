import { OptionModel } from './option.model.component';

export class SelectOneMenuModel{

    public id: string;
    public nonSelectionOptionMessage: string;
    public listOptions: Array<OptionModel>;
    public optionSelected: OptionModel;
    public messageErrorEmpty: string;
    public required: boolean;

    constructor() {}
}


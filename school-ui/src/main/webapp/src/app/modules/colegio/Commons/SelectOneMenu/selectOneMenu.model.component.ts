import { OptionModel } from './option.model.component';
import { CommonComponentModel } from '../CommonComponent/CommonComponent.model';

export class SelectOneMenuModel extends CommonComponentModel{

    public id: string;
    public nonSelectionOptionMessage: string;
    public listOptions: Array<OptionModel>;
    public optionSelected: OptionModel;
    public messageErrorEmpty: string;

    constructor() {
        super();
    }
}


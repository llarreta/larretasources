import { CommonComponentModel } from '../CommonComponent/CommonComponent.model';

export class InputModel extends CommonComponentModel{

    public id: string;
    public type: string;
    public labelContent: string;
    public value: string;
    public maxNumber: number;
    public minNumber: number;
    public maxCharacter: number;
    public minCharacter: number;
    public validateText: string;
    public maskText: string;
    public messageErrorValidation: string;
    public messageErrorEmpty: string;
    public messageErrorMaxCharacter: string;
    public messageErrorMinCharacter: string;
    public messageErrorMaxNumber: string;
    public messageErrorMinNumber: string;
    public childComponent: CommonComponentModel; 
    public validationActivate: boolean;
    public disabled: boolean = false;
    
    constructor() {
        super();
    }
}


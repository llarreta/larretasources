import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';
import { SelectOneMenuModel } from '../Commons/SelectOneMenu/selectOneMenu.model.component';
import { OptionModel } from '../Commons/SelectOneMenu/option.model.component';

@Component({
  selector: 'colegio-init',
  templateUrl: './src/app/modules/colegio/Init/init.component.html'
})
export class InitComponent implements OnInit{

  nombreInput: InputModel;
  textoCapturado: string;
  optionSelect: string;
  tipoDNI: SelectOneMenuModel;

  constructor() { }

  ngOnInit() {
    this.nombreInput = new InputModel();
    this.nombreInput.id="nombre";
    this.nombreInput.labelContent="Nombre";
    this.nombreInput.messageErrorEmpty="El campo no puede ser vacio.";
    this.nombreInput.messageErrorValidation="El nombre ingresado es incorrecto.";
    this.nombreInput.type="text";
    this.nombreInput.required=true;
    this.textoCapturado = "";

    this.tipoDNI = new SelectOneMenuModel();
    this.tipoDNI.id = "tipodni";
    this.tipoDNI.listOptions = new Array<OptionModel>();
    
    let option1: OptionModel = new OptionModel();
    option1.id = 1;
    option1.label = "Opcion1";

    let option2: OptionModel = new OptionModel();
    option2.id = 2;
    option2.label = "Opcion2";

    let option3: OptionModel = new OptionModel();
    option3.id = 3;
    option3.label = "Opcion3";

    this.tipoDNI.listOptions.push(option1);
    this.tipoDNI.listOptions.push(option2);
    this.tipoDNI.listOptions.push(option3);
    this.tipoDNI.messageErrorEmpty = "El campo no puede ser vacio.";
    this.tipoDNI.nonSelectionOptionMessage = "El campo seleccionado es invalido?";
    this.tipoDNI.nonSelectionOptionMessage = "Seleccione algo";
    this.tipoDNI.required = true;

  }

  setTextoCapturado(inputModel: InputModel){
    this.nombreInput = inputModel;
  }

  setOptionSelect(select: SelectOneMenuModel){
    this.tipoDNI = select;
  }
}
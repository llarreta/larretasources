import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';

@Component({
  selector: 'colegio-init',
  templateUrl: './src/app/modules/colegio/Init/init.component.html'
})
export class InitComponent implements OnInit{

  nombreInput: InputModel;
  textoCapturado: string;

  constructor() { }

  ngOnInit() {
    this.nombreInput = new InputModel();
    this.nombreInput.id="nombre";
    this.nombreInput.isErrorValidation=false;
    this.nombreInput.labelContent="Nombre";
    this.nombreInput.messageErrorEmpty="El campo no puede ser vacio.";
    this.nombreInput.messageErrorValidation="El nombre ingresado es incorrecto.";
    this.nombreInput.type="text";
    this.nombreInput.required=true;
    this.textoCapturado = "Nacho";
  }

  setTextoCapturado(texto: string){
    this.textoCapturado = texto;
  }
}
import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { InputModel } from './input.model.component';

@Component({
  selector: 'input-commons',
  templateUrl: './src/app/modules/colegio/Commons/Input/input.component.html',
  styleUrls: ['./src/app/modules/colegio/Commons/Input/input.component.css'],
})
export class InputCommonsComponent implements OnInit{

  @Input()
  inputModel: InputModel;
  @Output() 
  valueModel = new EventEmitter();

  isOnFocusLabel: boolean;
  isValueEmpty: boolean;
  isErrorEmpty: boolean;
  isAllOK: boolean;

  constructor() {}

  ngOnInit() {
    this.isOnFocusLabel = false;
    this.isValueEmpty = true;
    this.isAllOK = false;
    this.loadConditions();
    this.checkValue();
  }

  onFocus(){
    this.isOnFocusLabel = true;
  }

  onBlur(){
    this.loadConditions();
    this.isOnFocusLabel = false;
    this.changeValueModel();
  }

  loadConditions(){
    console.log("value= " + this.inputModel.value);
    if((this.inputModel.value == null) || (this.inputModel.value == "")){
      this.isValueEmpty = true;
    }else{
      this.isValueEmpty = false;
    }
  }

  onChange(){
    console.log("onChange value " + this.inputModel.value);
    this.loadConditions();
    if(this.inputModel.required && this.isValueEmpty){
      console.log("isErrorEmpty = true");
      this.isErrorEmpty = true;
    }
    if(this.inputModel.required && !this.isValueEmpty){
      console.log("isErrorEmpty = false");
      this.isErrorEmpty = false;
    }
    if(!this.isErrorEmpty && !this.inputModel.isErrorValidation){
      console.log("onChange isAllOK = true");
      this.isAllOK = true;
    }else{
      console.log("isAllOK = false");
      this.isAllOK = false;
    }
    this.changeValueModel();
  }  

  checkValue(){
    this.loadConditions();  
    if((this.inputModel.required && !this.isValueEmpty) 
      && (!this.inputModel.isErrorValidation)){
      console.log("checkValue isAllOK = true");
      this.isAllOK = true;
    }
  }

  changeValueModel() {
      console.log('newvalue', this.inputModel.value)
      if(this.inputModel.type == "text" || this.inputModel.type == "email"){  
        this.valueModel.emit(this.inputModel.value);
      }
      if(this.inputModel.type == "number"){
        this.valueModel.emit(Number(this.inputModel.value));
      }
  }

}
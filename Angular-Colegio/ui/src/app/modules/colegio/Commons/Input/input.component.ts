import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'input-commons',
  templateUrl: './src/app/modules/colegio/Commons/Input/input.component.html',
  styleUrls: ['./src/app/modules/colegio/Commons/Input/input.component.css'],
})
export class InputCommonsComponent implements OnInit{

  @Input()
  type: string;
  @Input()
  id: string;
  @Input()
  labelContent: string;
  @Input()
  isErrorValidation: boolean;
  @Input()
  messageErrorValidation: string;
  @Input()
  messageErrorEmpty: string;
  @Input()
  required: boolean;
  @Input()
  value: string;
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
    console.log("value= " + this.value);
    if((this.value == null) || (this.value == "")){
      this.isValueEmpty = true;
    }else{
      this.isValueEmpty = false;
    }
  }

  onChange(){
    console.log("onChange value " + this.value);
    this.loadConditions();
    if(this.required && this.isValueEmpty){
      console.log("isErrorEmpty = true");
      this.isErrorEmpty = true;
    }
    if(this.required && !this.isValueEmpty){
      console.log("isErrorEmpty = false");
      this.isErrorEmpty = false;
    }
    if(!this.isErrorEmpty && !this.isErrorValidation){
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
    if((this.required && !this.isValueEmpty) 
      && (!this.isErrorValidation)){
      console.log("checkValue isAllOK = true");
      this.isAllOK = true;
    }
  }

  changeValueModel() {
      console.log('newvalue', this.value)
      if(this.type == "text" || this.type == "email"){  
        this.valueModel.emit(this.value);
      }
      if(this.type == "number"){
        this.valueModel.emit(Number(this.value));
      }
  }

}
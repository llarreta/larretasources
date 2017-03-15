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
  isErrorEmpty: boolean;
  @Input()
  messageErrorValidation: string;
  @Input()
  messageErrorEmpty: string;
  @Input()
  required: string;
  @Output()
  contentString: EventEmitter<string>;
  @Output()
  contentNumber: EventEmitter<number>;

  value: string;
  isOnFocusLabel: boolean;
  isValueEmpty: boolean;
  
  constructor() {}

  ngOnInit() {
    this.contentString = new EventEmitter<string>();
    this.contentNumber = new EventEmitter<number>();
    this.isOnFocusLabel = false;
    this.isValueEmpty = true;
  }

  onFocus(){
    this.isOnFocusLabel = true;
  }

  onBlur(){
    this.loadConditions();
    this.isOnFocusLabel = false;
  }

  loadConditions(){
    console.log("value= " + this.value);
    if((this.value == null) || (this.value == "")){
      this.isValueEmpty = true;
    }else{
      this.isValueEmpty = false;
    }
  }

}
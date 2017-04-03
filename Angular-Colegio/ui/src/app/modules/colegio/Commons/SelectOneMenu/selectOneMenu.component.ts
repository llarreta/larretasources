import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { SelectOneMenuModel } from './selectOneMenu.model.component';
import { OptionModel } from './option.model.component';

@Component({
  selector: 'select-one-menu-commons',
  templateUrl: './src/app/modules/colegio/Commons/SelectOneMenu/selectOneMenu.component.html',
  styleUrls: ['./src/app/modules/colegio/Commons/SelectOneMenu/selectOneMenu.component.css'],
})
export class SelectOneMenuCommonsComponent implements OnInit{

  @Input()
  selectOneMenuModel: SelectOneMenuModel;
  @Output() 
  valueModel = new EventEmitter();

  isActive: boolean;
  isErrorEmpty: boolean;
  isAllOK: boolean;

  constructor() {}

  ngOnInit(){
    this.isActive = false;
    this.isAllOK = false;
    this.isErrorEmpty = false;

    let defaultOption: OptionModel = new OptionModel();
    defaultOption.id = 0;
    defaultOption.label = this.selectOneMenuModel.nonSelectionOptionMessage;
    if((this.selectOneMenuModel.listOptions != null) 
      && (this.selectOneMenuModel.listOptions.length > 0)){
      let auxOptions: Array<OptionModel> = new Array<OptionModel>();
      auxOptions.push(defaultOption);
      for(let i: number = 0; i < this.selectOneMenuModel.listOptions.length; i++){
        auxOptions.push(this.selectOneMenuModel.listOptions[i]);
      }
      this.selectOneMenuModel.listOptions = auxOptions;
    }else{
        this.selectOneMenuModel.listOptions = new Array<OptionModel>();
        this.selectOneMenuModel.listOptions.push(defaultOption);
    }
    if(this.selectOneMenuModel.optionSelected == null){
      this.selectOneMenuModel.optionSelected = defaultOption;
    }
  }

  onClickSelect(){
    this.isActive = !this.isActive;
  }

  onBlurSelect(e){
    this.isActive = false;
  }

  loadOption(option: OptionModel){
    this.selectOneMenuModel.optionSelected = option;
    if(this.selectOneMenuModel.required && option.id == 0){
      this.isErrorEmpty = true;
      this.isAllOK = false;
    }else{
      this.isErrorEmpty = false;
    }
    if(option.id != 0){
      this.isAllOK = true;
      this.isErrorEmpty = false;
    }
    this.changeValueModel();
  }
  
  changeValueModel() {
    this.valueModel.emit(this.selectOneMenuModel);
  }

}
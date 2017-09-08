import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
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
  isErrorEmpty: boolean;
  isErrorValidation: boolean;
  isErrorMaxCharacter: boolean;
  isErrorMinCharacter: boolean;
  isErrorMaxNumber: boolean;
  isErrorMinNumber: boolean;
  isErrorType: boolean;

  isErrorValidationEnabled: boolean;
  isErrorMaxCharacterEnabled: boolean;
  isErrorMinCharacterEnabled: boolean;
  isErrorMaxNumberEnabled: boolean;
  isErrorMinNumberEnabled: boolean;
  isMaskEnabled: boolean;

  constructor() {}

  ngOnInit() {
    this.isOnFocusLabel = false;
    if(this.inputModel != null){
      if(this.inputModel.validationActivate){ 
        this.checkValidationsEnabled();
        if(this.inputModel.value != null){
          this.checkValue();
        }
      }
    }
  }

  checkValidationsEnabled(){
    if(this.inputModel.maxCharacter != null){
      this.isErrorMaxCharacterEnabled = true;
    }else{
      this.isErrorMaxCharacterEnabled = false;
    }
    if(this.inputModel.minCharacter != null){
      this.isErrorMinCharacterEnabled = true;
    }else{
      this.isErrorMinCharacterEnabled = false;
    }
    if(this.inputModel.maxNumber != null){
      this.isErrorMaxNumberEnabled = true;
    }else{
      this.isErrorMaxNumberEnabled = false;
    }
    if(this.inputModel.minNumber != null){
      this.isErrorMinNumberEnabled = true;
    }else{
      this.isErrorMinNumberEnabled = false;
    }
    if(this.inputModel.validateText != null){
      this.isErrorValidationEnabled = true;
    }else{
      this.isErrorValidationEnabled = false;
    }
    if(this.inputModel.mask != null){
      this.isMaskEnabled = true;
    }else{
      this.isMaskEnabled = false;
    }
  }

  onFocus(){
    this.isOnFocusLabel = true;
  }

  onBlur(){
    if(this.inputModel.validationActivate){
      this.checkValue();
      this.changeValueModel();
    }
    this.isOnFocusLabel = false;
  }

  loadConditions(){
    this.checkEmpty();
    this.checkValidation();
    this.checkMaxCharacter();
    this.checkMinCharacter();
    this.checkType();
    if(this.inputModel.type == "number"){ 
      this.checkMaxNumber();
      this.checkMinNumber();
    }
  }

  checkEmpty(){
    if((this.inputModel.required) && 
      ((this.inputModel.value == null) || (this.inputModel.value == ""))){
      this.isErrorEmpty = true;
    }else{
      this.isErrorEmpty = false;
    }
  }

  checkValidation(){
    if((this.isErrorValidationEnabled) && (!this.inputModel.value.match(this.inputModel.validateText))){
      this.isErrorValidation = true;
    }else{
      this.isErrorValidation = false;
    }
  }

  checkMaxCharacter(){
    if((this.inputModel.value != null) && (this.isErrorMaxCharacterEnabled) 
      && (this.inputModel.value.length > this.inputModel.maxCharacter)){
      this.isErrorMaxCharacter = true;
    }else{
      this.isErrorMaxCharacter = false;
    }
  }

  checkMinCharacter(){
    if((this.isErrorMinCharacterEnabled) && (this.inputModel.value != null) && (this.inputModel.value.length > 0) 
       && (this.inputModel.value.length <= this.inputModel.minCharacter)){
      this.isErrorMinCharacter = true;
    }else{
      this.isErrorMinCharacter = false;
    }
  }

  checkMaxNumber(){
    if((this.isErrorMaxNumberEnabled) && (Number(this.inputModel.value) > this.inputModel.maxNumber)){
      this.isErrorMaxNumber = true;
    }else{
      this.isErrorMaxNumber = false;
    }
  }

  checkType(){
    if(this.inputModel.type == "number"){
      let texto: string = this.getTextWithOutMask();
      if(texto != null){  
        if(!texto.match("^[0-9]*([,][0-9]{1,2})?$")){  
          this.isErrorType = true;
        }else{
          console.log("Error type false");
          this.isErrorType = false;
        }
      }
    }
    if(this.inputModel.type == "mail"){
      if(this.validateEmail(this.inputModel.value)){
        this.isErrorType = false;
      }else{
        this.isErrorType = true;
      }
    }
    if(this.inputModel.type == "dni"){
      console.debug("Validando input dni");
      if(this.validateDni(this.inputModel.value)){
        console.debug("Validando input dni false");
        this.isErrorType = false;
      }else{
        console.debug("Validando input true");
        this.isErrorType = true;
      }
    }
    if(this.inputModel.type == "telphone"){
      console.debug("Validando input Telphone");
      if(this.validateThelphone(this.inputModel.value)){
        console.debug("Validando input thelphone false");
        this.isErrorType = false;
      }else{
        console.debug("Validando input true");
        this.isErrorType = true;
      }
    }
  }

  validateDni(dni) {
      var re: RegExp = new RegExp("^([0-9]{2})([.][0-9]{3})([.][0-9]{3})$");
      return re.test(dni);
  }

  validateThelphone(telphone) {
    var re: RegExp = new RegExp("^([0-9]{2})([-][0-9]{4})([-][0-9]{4})$");
    return re.test(telphone);
  }

  validateEmail(email) {
      var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
  }

  checkMinNumber(){
    if((this.isErrorMinNumberEnabled) && (Number(this.inputModel.value) < this.inputModel.minNumber)){
      this.isErrorMinNumber = true;
    }else{
      this.isErrorMinNumber = false;
    }
  }

  onChange(){
    if(this.inputModel.validationActivate){
      this.loadConditions();
      this.checkIsAllOK();
    }
    this.changeValueModel();
  }  

  checkIsAllOK(){
    if(
      ((!this.inputModel.required) || (this.inputModel.required && !this.isErrorEmpty))
      && ((!this.isErrorMaxCharacterEnabled) || (this.isErrorMaxCharacterEnabled && !this.isErrorMaxCharacter))
      && ((!this.isErrorMinCharacterEnabled) || (this.isErrorMinCharacterEnabled && !this.isErrorMinCharacter))
      && ((!this.isErrorMaxNumberEnabled) || (this.isErrorMaxNumberEnabled && !this.isErrorMaxNumber))
      && ((!this.isErrorMinNumberEnabled) || (this.isErrorMinNumberEnabled && !this.isErrorMinNumber))
      && ((!this.isErrorValidationEnabled) || (this.isErrorValidationEnabled && !this.isErrorValidation))
      && (!this.isErrorType)
      ){
        this.inputModel.isAllOK = true;
    }else{
      this.inputModel.isAllOK = false;
    }
  }

  checkValue(){
    if(this.inputModel.validationActivate){    
      this.loadConditions();    
      this.checkIsAllOK();
    }
  }

  changeValueModel() {
    this.valueModel.emit(this.inputModel);
  }

  getTextWithOutMask(){
    return String(this.inputModel.value);
  }

}
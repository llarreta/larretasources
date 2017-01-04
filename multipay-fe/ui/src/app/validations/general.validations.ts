import { AbstractControl, ValidatorFn } from '@angular/forms';

export namespace GeneralValidations {

  /**
   * Validate that the input contains a number
   */
  export function containsNumber(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExp = new RegExp('[0-9]');
      return regExp.test(value) ? null : { 'containsNumber': { value } };
    };
  }

  /**
   *  Validate that the input contains a capital letter and a lower case letter.   
   */
  export function containsCapitalAndLowerCaseLetter(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExpMin = new RegExp('[a-z]');
      const regExpMay = new RegExp('[A-Z]');
      return (regExpMin.test(value) && regExpMay.test(value)) ? null : { 'containsCapitalAndLowerCaseLetter': { value } };
    };
  }

  /**
   *  Validate that the input contains only alphanumeric characters.   
   */
  export function alphanumeric(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExpAlpanum = new RegExp('^[a-zA-Z0-9]*$');
      return (regExpAlpanum.test(value)) ? null : { 'alphanumeric': { value } };
    };
  }

  /**
   *  Validate that the input contains only alphabetic characters.   
   */
  export function alphabetic(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExpAlphab = new RegExp('^[a-zA-ZáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ ]*$');
      return (regExpAlphab.test(value)) ? null : { 'alphabetic': { value } };
    };
  }

  /**
   *  Validate that the input contains only numberic characters.   
   */
  export function numeric(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExpNum = new RegExp('^[0-9]*$');
      return (regExpNum.test(value)) ? null : { 'numeric': { value } };
    };
  }

  /**
   * Validate that the length of the input has a minimum 
   * @param length: number - The lenght to validate
   */
  export function minLength(length: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      return (value !== undefined && value.length >= length) ? null : { 'minlength': { value } };
    };
  }

  /**
  * Validate that the length of the input has a maximum 
  * @param length: number - The lenght to validate
  */
  export function maxLength(length: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      return (value !== undefined && value.length <= length) ? null : { 'maxlength': { value } };
    };
  }

  /**
   *  Validate that the input have email format.   
   */
  export function email(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExEmail = new RegExp('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$')
      return (regExEmail.test(value)) ? null : { 'email': { value } };
    };
  }

  /**
   *  Validate that the input Date have 'yyyy-dd-mm' format.   
   */
  export function yyyyMMddDate(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const value = control.value;
      const regExYyyyMmDd = new RegExp('^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$')
      return (regExYyyyMmDd.test(value)) ? null : { 'dateOfBirth': { value } };
    };
  }

}
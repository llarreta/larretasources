import { Directive, forwardRef } from '@angular/core';
import { Validator, NG_VALIDATORS, AbstractControl } from '@angular/forms';
import { UserService } from '../services/user.service';

function validateUsername(us: UserService) {
  return (c: AbstractControl) => {
    const value = c.value;
    
    if (c.errors === null && value !== "" && value !== undefined) {
      us.existsUserByUsername(value).subscribe((x) => {
        let res;

        if (x.responseObject.body.existsUser) {
          res =  { 'uniqueUsername': false };
          c.setErrors(res);
        } 
      });
    }

  };
}

@Directive({
  selector: '[uniqueUsername][formControlName],[uniqueUsername][formControl],[uniqueUsername][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => UniqueUsernameValidation), multi: true }
  ]
})
export class UniqueUsernameValidation implements Validator {

  validator: Function;

  constructor(us: UserService) {
    this.validator = validateUsername(us);
  }

  validate(c: AbstractControl): { [key: string]: any } {
    return this.validator(c);
  }

}
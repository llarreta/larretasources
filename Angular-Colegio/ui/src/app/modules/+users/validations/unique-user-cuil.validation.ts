import { Directive, forwardRef } from '@angular/core';
import { Validator, NG_VALIDATORS, AbstractControl } from '@angular/forms';
import { UserService } from '../services/user.service';

function validateCuil(us: UserService) {

  return (c: AbstractControl) => {

    const value = c.value;

    if (c.errors === null && value !== "" && value !== undefined) {
      us.existsUserByCuil(value).subscribe((x) => {
        if (x.responseObject.body.existsUser) {
          c.setErrors({ 'uniqueCuil': false });
        }
      });
    }

  };
}

@Directive({
  selector: '[uniqueCuil][formControlName],[uniqueCuil][formControl],[uniqueCuil][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => UniqueCuilValidation), multi: true }
  ]
})
export class UniqueCuilValidation implements Validator {

  validator: Function;

  constructor(us: UserService) {
    this.validator = validateCuil(us);
  }

  validate(c: AbstractControl): { [key: string]: any } {
    return this.validator(c);
  }

}
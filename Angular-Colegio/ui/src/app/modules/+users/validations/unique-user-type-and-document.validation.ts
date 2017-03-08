import { Directive, forwardRef, Input } from '@angular/core';
import { Validator, NG_VALIDATORS, AbstractControl, FormGroup } from '@angular/forms';
import { UserService } from '../services/user.service';

function validateTypeAndDocument(us: UserService, docType: string) {

  return (c: AbstractControl) => {

    const value = c.value;

    if (c.errors === null && value !== "" && value !== undefined) {
      
      // Validate if exists user.
      us.existsUserByTypeAndDocument(docType, value).subscribe((x) => {
        if (x.responseObject.body.existsUser) {
          c.setErrors({ 'uniqueTypeAndDocument': false });
        }
      });
    }

  };
}

@Directive({
  selector: '[uniqueDocTypeAndDocument][formControlName],[uniqueDocTypeAndDocument][formControl],[uniqueDocTypeAndDocument][ngModel]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => UniqueDocTypeAndDocumentValidation), multi: true }
  ]
})
export class UniqueDocTypeAndDocumentValidation implements Validator {

  private docType: string;

  @Input()
  set uniqueDocTypeAndDocument(uniqueDocTypeAndDocument: string){
    this.docType = uniqueDocTypeAndDocument;
  }
  

  validator: Function;

  constructor(us: UserService) {
    this.validator = validateTypeAndDocument(us, this.docType);
  }

  validate(c: AbstractControl): { [key: string]: any } {
    return this.validator(c);
  }

}
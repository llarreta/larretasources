import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { User } from '../models/user.model';
import { UserCreationForm } from './user-creation.form';

@Component({
  selector: 'users-creation',
  templateUrl: './src/app/modules/+users/creation/user-creation.component.html'
})
export class UserCreationComponent {

  user: User = new User();
  userProfileForm: FormGroup;

  documentTypes: string[] = ['DNI', 'LE', 'LC'];
  docTypeDefault = 'Seleccione';

  phoneTypes: string[] = ['Personal', 'Casa', 'Trabajo'];
  phoneTypeDefault = 'Seleccione';

  constructor(public userCreationForm: UserCreationForm) { }

  ngOnInit(): void {
    
    this.userProfileForm = this.userCreationForm.buildForm(this.user);

    this.userProfileForm.valueChanges.subscribe((data) => {
      this.userCreationForm.onValueChanged(this.userProfileForm, data);
    });

    this.user.documentType = this.docTypeDefault;
    this.user.phoneType = this.phoneTypeDefault;

  }

  hasError(field: string): boolean{
    return this.userProfileForm.get(field).dirty && !this.userProfileForm.get(field).valid;
  }

  showMessage(field: string): string{
      if(this.userProfileForm.get(field).errors === null) return "";
      
      let key: string = Object.keys(this.userProfileForm.get(field).errors)[0];

      return this.userCreationForm.validationMessages[field][key];

  }

  showCheck(field: string, validation: string): string {
      if (this.userProfileForm.get(field).errors !== null && this.userProfileForm.get(field).errors[validation]) {
        return 'icons-check-error';
      } else {
        return 'ok-check';
      }
    }
 

  /**
   * Sets and modify value of 'Document Type' Select.
   */
  selectDocumentType(index: number) {
    if (index == -1) {
      this.user.documentType = this.docTypeDefault;
    } else {
      this.user.documentType = this.documentTypes[index];
    }
  }

  /**
   * Sets and modify value of 'Phone Type' Select.
   */
  selectPhoneType(index: number) {
    if (index == -1) {
      this.user.phoneType = this.phoneTypeDefault;
    } else {
      this.user.phoneType = this.phoneTypes[index];
    }
  }
  
}
import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { GeneralValidations } from '../../../validations/general.validations';
import { User } from '../models/user.model';
import { UserService } from '../services/user.service';

@Injectable()
export class UserCreationForm {

    private f: Function;

    constructor(private fb: FormBuilder, private userService: UserService) {}

    public errors: any = {
        'username': {
            'required': false,
            'alphanumeric': false,
            'uniqueUsername': false
        },
        'password': {
            'required': false,
            'minlength': false,
            'maxlength': true,
            'containsNumber': false,
            'containsCapitalAndLowerCaseLetter': false
        },
        'name': {
            'required': false,
            'alphabetic': false
        },
        'documentNumber': {
            'required': false,
            'numeric': false,
            'uniqueTypeAndDocument': false
        },
        'cuil': {
            'required': false,
            'numeric': false,
            'uniqueCuil': false
        },
        'email': {
            'required': false,
            'email': false
        },
        'dateOfBirth': {
            'required': false,
            'yyyyMMddDate': false
        },
        'phoneNumber': {
            'required': false,
            'numeric': false
        }
    };

    public validationMessages: any = {
        'username': {
            'required': 'El nombre de usuario es requerido.',
            'alphanumeric': 'El usuario debe ser alfanumerico.',
            'uniqueUsername': 'El usuario ya esta en uso.'
        },
        'password': {
            'required': 'La contraseña es requerida.',
            'minlength': 'Password must be at least 8 characters long.',
            'maxlength': 'La contraseña no puede contener mas de 20 caracteres.',
            'containsNumber': 'Password must contain a number.',
            'containsCapitalAndLowerCaseLetter': 'Password must contain mayusc and minusc.'
        },
        'name': {
            'required': 'El Nombre y Apellido son requeridos.',
            'alphabetic': 'El Nombre y Apellido debe ser alfabético.'
        },
        'documentNumber': {
            'required': 'El Número de Documento es requerido.',
            'numeric': 'El Número de Documento debe ser numérico.',
            'uniqueTypeAndDocument': 'Existe un usuario con el Tipo y Número de Documento.'
        },
        'cuil': {
            'required': 'El cuil es requerido.',
            'numeric': 'El cuil debe ser numérico.',
            'uniqueCuil': 'Existe un usuario con el cuil ingresado.'
        },
        'email': {
            'required': 'El email es requerido.',
            'email': 'El formato del email es erróneo.'
        },
        'dateOfBirth': {
            'required': 'La Fecha de Nacimiento es requerida.',
            'yyyyMMddDate': 'El formato de la Fecha de Nacimiento es erróneo.'
        },
        'phoneNumber': {
            'required': 'El Número de Teléfono es requerido.',
            'numeric': 'El Número de Teléfono debe ser numérico.'
        }
    };

    buildForm(user: User): FormGroup {
        return this.fb.group({
            'username': ['', [
                Validators.required,
                GeneralValidations.alphanumeric()
            ]],
            'password': ['', [
                Validators.required,
                GeneralValidations.maxLength(20),
                GeneralValidations.minLength(8),
                GeneralValidations.containsNumber(),
                GeneralValidations.containsCapitalAndLowerCaseLetter()
            ]],
            'name': ['', [
                Validators.required,
                GeneralValidations.alphabetic()
            ]],
            'documentNumber': ['', [
                Validators.required,
                GeneralValidations.numeric()
            ]],
            'cuil': ['', [
                Validators.required,
                GeneralValidations.numeric()
            ]],
            'email': ['', [
                Validators.required,
                GeneralValidations.email()
            ]],
            'dateOfBirth': ['', [
                Validators.required,
                GeneralValidations.yyyyMMddDate()
            ]],
            'phoneType': ['', [
                Validators.required
            ]],
            'phoneNumber': ['', [
                Validators.required,
                GeneralValidations.numeric()
            ]]
        });
    }

    onValueChanged(userProfileForm: FormGroup, data?: any) {
        if (!userProfileForm) { return; }

        for (const key in data) {

            const control = userProfileForm.get(key);

            if (control.dirty) {

                for (const validationKey in this.validationMessages[key]) {

                    const error = (control.errors == null) ? undefined : control.errors[validationKey];

                    if (error !== undefined) {
                        this.errors[key][validationKey] = false;
                    } else {
                        this.errors[key][validationKey] = true;
                    }

                }
            }
        }

    }


}
import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Country } from '../models/country.model';

@Injectable()
export class CountryService {

    constructor(private http: HttpRequest) { }

    loadCountrys(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "countrys/load", token);
    }

}
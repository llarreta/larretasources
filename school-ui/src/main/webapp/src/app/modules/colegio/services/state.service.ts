import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { State } from '../models/state.model';

@Injectable()
export class StateService {

    constructor(private http: HttpRequest) { }

    loadStatesByCountry(idCountry: number): Observable<any> {
        var body = {
            "idRelated": idCountry
        };
        var token = "";
        return this.http.post(body, "states/load", token);
    }

}
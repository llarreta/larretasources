import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';

@Injectable()
export class ScholarshipService {

    constructor(private http: HttpRequest) { }

    load(): Observable<any> {
        var body = {
        };
        var token = "";
        return this.http.post(body, "scholarships/load", token);
    }

}
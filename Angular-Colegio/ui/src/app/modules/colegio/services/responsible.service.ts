import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';

@Injectable()
export class ResponsibleService {

    constructor(private http: HttpRequest) { }

    getBySurname(surname: string): Observable<any> {
        var body = {
            "name": null,
            "surname": surname
        };
        var token = "";
        return this.http.post(body, "students/searchResponsible", token);
    }

}
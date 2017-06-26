import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Year } from '../models/year.model';

@Injectable()
export class YearService {

    constructor(private http: HttpRequest) { }

    //createYear(year: Year): Observable<any> {
    //    return this.http.post(year, "years/create");
    //}

    //updateYear(year: Year): Observable<any> {
    //    return this.http.post(year, "years/update");
    //}

    //deleteYear(year: Year): Observable<any> {
    //    return this.http.post(year, "years/delete");
    //}

    loadYears(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "years/load", token);
    }

}
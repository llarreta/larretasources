import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Division } from '../models/division.model';

@Injectable()
export class DivisionService {

    constructor(private http: HttpRequest) { }

    //createDivision(division: Division): Observable<any> {
    //    return this.http.post(division, "divisions/create");
    //}

    //updateDivision(division: Division): Observable<any> {
    //    return this.http.post(division, "divisions/update");
    //}

    //deleteDivision(division: Division): Observable<any> {
    //    return this.http.post(division, "divisions/delete");
    //}

    loadDivisions(): Observable<any> {
        var body = {};
        return this.http.post(body, "divisions/load");
    }

}
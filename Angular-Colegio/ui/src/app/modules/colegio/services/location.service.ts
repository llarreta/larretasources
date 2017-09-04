import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Location } from '../models/location.model';

@Injectable()
export class LocationService {

    constructor(private http: HttpRequest) { }

    loadLocationByState(idState: number): Observable<any> {
        var body = {
            "idRelated": idState
        };
        var token = "";
        return this.http.post(body, "locations/load", token);
    }

}
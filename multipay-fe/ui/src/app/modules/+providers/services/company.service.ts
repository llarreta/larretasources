import { Injectable } from '@angular/core';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { HttpRequest, Modules } from '../../../services/index'
import { DefaultRequest } from '../../../models/default-request.model'


@Injectable()
export class CompanyService {

    constructor(private http: HttpRequest) { }

    findCompany(input: string): Observable<any> {
        let data = { root: input, userId: "admin" };
        const dr = this.http.createRequest(Modules.ModuloDePago, "mpay/api/prov/findCompany", data);
        return this.http.post(dr);
    }

}
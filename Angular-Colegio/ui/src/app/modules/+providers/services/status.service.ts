import { Injectable } from '@angular/core';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { HttpRequest, Modules } from '../../../services/index'
import { DefaultRequest } from '../../../models/default-request.model'


@Injectable()
export class StatusService {

    constructor(private http: HttpRequest) { }

    getPendingStates(): Observable<any> {
        const dr = this.http.createRequest(Modules.ModuloDePago, "pendingStates");
        return this.http.post(dr);
    }

    getHistoricalStates(): Observable<any> {
        const dr = this.http.createRequest(Modules.ModuloDePago, "historicalStates");
        return this.http.post(dr);
    }
}
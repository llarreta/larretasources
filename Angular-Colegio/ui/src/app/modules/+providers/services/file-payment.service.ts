import { Injectable } from '@angular/core';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { HttpRequest, Modules } from '../../../services/index'
import { DefaultRequest } from '../../../models/default-request.model'

@Injectable()
export class FilePaymentService {

    constructor(private http: HttpRequest) { }

    progressBar(): Rx.Observable<any>{
        const dr: DefaultRequest = this.http.createRequest(Modules.PagoProveedores, "status");
        const request = this.http.post(dr);

        const interval = Rx.Observable.interval(2000);
        const concat = interval.concatMapTo(request, (time, resp) => resp);
        return concat;
    }

}
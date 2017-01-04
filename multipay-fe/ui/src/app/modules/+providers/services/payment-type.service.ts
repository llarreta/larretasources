import { Injectable } from '@angular/core';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { HttpRequest, Modules } from '../../../services/index'
import { DefaultRequest } from '../../../models/default-request.model'

@Injectable()
export class PaymentTypeService {

    constructor(private http: HttpRequest) { }

    getPaymentTypes(): Observable<any> {
        const dr = this.http.createRequest(Modules.ModuloDePago, "paymentTypes");
        return this.http.post(dr);
    }
}
import { Injectable } from '@angular/core';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { HttpRequest, Modules } from '../../../services/index'
import { DefaultRequest } from '../../../models/default-request.model'

@Injectable()
export class PaymentListService {

    constructor(private http: HttpRequest) { }

    getHistoricalPayments(data?: any): Rx.Observable<any>{
        const dr: DefaultRequest = this.http.createRequest(Modules.ModuloDePago, "historicalList",data);
        const request = this.http.post(dr);
        return request;
    }

    getPendingPayments(data?: any): Rx.Observable<any> {
        const dr: DefaultRequest = this.http.createRequest(Modules.ModuloDePago, "pendingList",data);
        const request = this.http.post(dr);
        return request;
    }

    getPaymentDetails(idList: string): Rx.Observable<any>{
        const data: any = {id: idList};
        const dr: DefaultRequest = this.http.createRequest(Modules.ModuloDePago, "detallePago", data);
        const request = this.http.post(dr);
        return request;
    }

    getCustomerNames(): Rx.Observable<any>{
        const dr: DefaultRequest = this.http.createRequest(Modules.ModuloDePago, "customerNames");
        const request = this.http.post(dr);
        return request;
    }

}
import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { PaymentPlan } from '../models/paymentPlan.model';

@Injectable()
export class PaymentPlanService {

    constructor(private http: HttpRequest) { }

    createPaymentPlan(paymentPlan: PaymentPlan): Observable<any> {
        var token = "";
        return this.http.post(paymentPlan, "paymentPlans/create", token);
    }

    updatePaymentPlan(paymentPlan: PaymentPlan): Observable<any> {
        var token = "";
        return this.http.post(paymentPlan, "paymentPlans/update", token);
    }

    deletePaymentPlan(paymentPlan: PaymentPlan): Observable<any> {
        var body = {
                    "target":paymentPlan.id
                };
        var token = "";
        return this.http.post(body, "paymentPlans/delete", token);
    }

    loadPaymentPlans(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "paymentPlans/load", token);
    }

    loadPaymentRecords(id: number): Observable<any> {
        var body = {
                        "target": id
                    };
        var token = "";
        return this.http.post(body, "payments/obligationsStatus", token);
    }

}
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpRequest, Modules } from '../../../services/index';

@Injectable()
export class PaymentRecordService {

    constructor(private http: HttpRequest) { }

    loadPaymentRecords(id: number): Observable<any> {
        var body = {
                        "target": id
                    };
        var token = "";
        return this.http.post(body, "payments/obligationsStatus", token);
    }

    getPaymentReportFromCourse(idCourse: number): Observable<any> {
        var body = {
                        "target": idCourse
                    };
        var token = "";
        return this.http.post(body, "payments/paidObligationReport", token);
    }

}
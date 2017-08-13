import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpRequest, Modules } from '../../../services/index';

@Injectable()
export class PaymentRecordService {

    constructor(private http: HttpRequest) { }

    loadUnpaidObligations(idStudent: number): Observable<any> {
        var body = {
                        "target": idStudent
                    };
        var token = "";
        return this.http.post(body, "payments/unpaidObligations", token);
    }

    loadPaidObligations(idStudent: number): Observable<any> {
        var body = {
                        "target": idStudent
                    };
        var token = "";
        return this.http.post(body, "payments/paidObligations", token);
    }

    payObligation(value: number, personWhoPaysId: number, obligationId: number) : Observable<any>{
        var body = {
                        "value": value,
                        "personWhoPays": personWhoPaysId,
                        "paymentUnits": [
                                            {
                                            "value": value,
                                            "personBenefiting": personWhoPaysId,
                                            "product": obligationId,
                                            "paymentDirection": 1,
                                            "paymentEntity": 1,
                                            "paidOff": true
                                            }
                        ],
                        "paymentDate": new Date()
                    };
        var token = "";
        return this.http.post(body, "payments/pay", token);
    }

    getPayVoucher(idPaymentRecord: number): Observable<any>{
        var body = {
                        "target": idPaymentRecord
                    };
        var token = "";
        return this.http.post(body, "payments/payVoucherReport", token);
    }

    getPaymentReportFromCourse(idCourse: number): Observable<any> {
        var body = {
                        "target": idCourse
                    };
        var token = "";
        return this.http.post(body, "payments/paidObligationsReport", token);
    }

}
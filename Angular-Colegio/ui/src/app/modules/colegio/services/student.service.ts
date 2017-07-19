import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Student } from '../models/student.model';
import { PaymentPlan } from '../models/paymentPlan.model';

@Injectable()
export class StudentService {

    constructor(private http: HttpRequest) { }

    createStudent(student: Student): Observable<any> {
        var token = "";
        var body = {
                    "id": student.id,
                    "name": student.name,
                    "surname": student.surname,
                    "documentType": student.documentType,
                    "documentNumber": student.documentNumber,
                    "photo": null,
                    "course": student.course,
                    "paymentPlans": student.paymentPlans,
                    "email": student.email
                };
        return this.http.post(body, "students/create", token);
    }

    updateStudent(student: Student): Observable<any> {
        var token = "";
        var body = {
                    "id": student.id,
                    "name": student.name,
                    "surname": student.surname,
                    "documentType": student.documentType,
                    "documentNumber": student.documentNumber,
                    "photo": null,
                    "course": student.course,
                    "paymentPlans": student.paymentPlans
                };
        return this.http.post(body, "students/update", token);
    }

    deleteStudent(student: Student): Observable<any> {
        var body = {
                    "target":student.id
                };
        var token = "";
        return this.http.post(body, "students/delete", token);
    }

    loadStudents(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "students/load", token);
    }

}
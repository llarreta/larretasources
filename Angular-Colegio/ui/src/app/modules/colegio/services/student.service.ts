import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Logger } from '../../../Logger/logger';

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
                    "photo": student.photo,
                    "course": student.course.id,
                    "paymentPlans": student.paymentPlans,
                    "emails": student.emails,
                    "telephones": student.telephones,
                    "addresses": student.addresses
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
                    "photo": student.photo,
                    "course": student.course.id,
                    "paymentPlans": student.paymentPlans,
                    "emails": student.emails,
                    "telephones": student.telephones,
                    "addresses": student.addresses
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

    loadStudents(lastResult: number, maxResult: number): Observable<any> {
        Logger.debug("loadStudents Service: " + lastResult);
        var body = {
                        "firstResult": lastResult,
                        "maxResults": maxResult,
                        "result": null
                    };
        var token = "";
        return this.http.post(body, "students/load", token);
    }

}
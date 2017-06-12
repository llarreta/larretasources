import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Student } from '../models/student.model';

@Injectable()
export class StudentService {

    constructor(private http: HttpRequest) { }

    createStudent(student: Student): Observable<any> {
        return this.http.post(student, "students/create");
    }

    updateStudent(student: Student): Observable<any> {
        return this.http.post(student, "students/update");
    }

    deleteStudent(student: Student): Observable<any> {
        return this.http.post(student, "students/delete");
    }

    loadStudents(): Observable<any> {
        var body = {};
        return this.http.post(body, "students/load");
    }

}
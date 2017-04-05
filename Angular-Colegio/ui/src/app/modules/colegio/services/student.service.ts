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
        let data = 
        { 
            name: student.name, 
            surname: student.surname,
            documentNumber: ""+student.documentNumber    
        };
        return this.http.post(data, "create");
    }

}
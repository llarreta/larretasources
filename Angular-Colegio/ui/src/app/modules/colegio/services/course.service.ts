import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Course } from '../models/course.model';

@Injectable()
export class CourseService {

    constructor(private http: HttpRequest) { }

    createCourse(course: Course): Observable<any> {
        return this.http.post(course, "courses/create");
    }

    updateCourse(course: Course): Observable<any> {
        return this.http.post(course, "courses/update");
    }

    deleteCourse(course: Course): Observable<any> {
        var body = {
                    "target":course.id
                };
        return this.http.post(body, "courses/delete");
    }

    loadCourses(): Observable<any> {
        var body = {};
        return this.http.post(body, "courses/load");
    }

}
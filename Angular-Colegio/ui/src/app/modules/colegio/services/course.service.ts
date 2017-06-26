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
        var body = {
                    "level":{
                                "id":course.level.id
                            },
                    "year":{
                                "id":course.year.id
                            },
                    "division":{
                                 "id":course.division.id
                                }
                };
        var token = "";
        return this.http.post(body, "courses/create", token);
    }

    updateCourse(course: Course): Observable<any> {
        var body = {
                    "id":course.id,
                    "level":{
                                "id":course.level.id
                            },
                    "year":{
                                "id":course.year.id
                            },
                    "division":{
                                 "id":course.division.id
                                }
                };
        var token = "";
        return this.http.post(body, "courses/update", token);
    }

    deleteCourse(course: Course): Observable<any> {
        var body = {
                    "target":course.id
                };
        var token = "";
        return this.http.post(body, "courses/delete", token);
    }

    loadCourses(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "courses/load", token);
    }

}
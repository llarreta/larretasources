import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { Level } from '../models/level.model';

@Injectable()
export class LevelService {

    constructor(private http: HttpRequest) { }

    //createLevel(level: Level): Observable<any> {
    //    return this.http.post(level, "levels/create");
    //}

    //updateLevel(level: Level): Observable<any> {
    //    return this.http.post(level, "levels/update");
    //}

    //deleteLevel(level: Level): Observable<any> {
    //    return this.http.post(level, "levels/delete");
    //}

    loadLevels(): Observable<any> {
        var body = {};
        return this.http.post(body, "levels/load");
    }

}
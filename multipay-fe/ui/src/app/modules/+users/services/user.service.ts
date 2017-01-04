import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';

@Injectable()
export class UserService {

    constructor(private http: HttpRequest) { }

    existsUserByUsername(name: string): Observable<any> {
        let data = { userName: name };
        const dr = this.http.createRequest(Modules.ModuloDeUsuario, "existsUserByUsername", data);
        return this.http.post(dr);
    }

    existsUserByCuil(cuil: number): Observable<any> {
        let data = { cuil: cuil };
        const dr = this.http.createRequest(Modules.ModuloDeUsuario, "existsUserByCuil", data);
        return this.http.post(dr);
    }

    existsUserByTypeAndDocument(docType: string, docNumber: number): Observable<any> {
        let data = { documentType: docType, documentNumber: docNumber };
        const dr = this.http.createRequest(Modules.ModuloDeUsuario, "existsUserByTypeAndDocument", data);
        return this.http.post(dr);
    }

}
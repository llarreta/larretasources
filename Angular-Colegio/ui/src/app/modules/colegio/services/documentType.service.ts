import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

import { Observable } from 'rxjs';

import { HttpRequest, Modules } from '../../../services/index';
import { DefaultRequest } from '../../../models/default-request.model';
import { DocumentType } from '../models/DocumentType.model';

@Injectable()
export class DocumentTypeService {

    constructor(private http: HttpRequest) { }

    //createDocumentType(documentType: DocumentType): Observable<any> {
    //    return this.http.post(documentType, "documentTypes/create");
    //}

    //updateDocumentType(documentType: DocumentType): Observable<any> {
    //    return this.http.post(documentType, "documentTypes/update");
    //}

    //deleteDocumentType(documentType: DocumentType): Observable<any> {
    //    return this.http.post(documentType, "documentTypes/delete");
    //}

    loadDocumentTypes(): Observable<any> {
        var body = {};
        var token = "";
        return this.http.post(body, "documentTypes/load", token);
    }

}
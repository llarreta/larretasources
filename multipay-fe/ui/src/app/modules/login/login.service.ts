import { Injectable } from '@angular/core';

import { HttpRequest, Modules } from '../../services/index';
import { DefaultRequest } from '../../models/default-request.model';

@Injectable()
export class LoginService {

    constructor(private http: HttpRequest) { }

    login(data: any){
        const dr = this.http.createRequest(Modules.Login, "login", data);
        let post = this.http.post(dr);
    }

}
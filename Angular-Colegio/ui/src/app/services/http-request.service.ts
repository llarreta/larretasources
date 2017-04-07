import { Http, Headers, Response, RequestOptions } from '@angular/http';
import { Injectable, Inject } from '@angular/core';

import { DefaultRequest } from '../models/default-request.model'

import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';

import { CookieService } from 'angular2-cookie/core';


@Injectable()
export class HttpRequest {

    private cookieName: string = "test";

    constructor(private http: Http,
        @Inject("Config") private config: any,
        private cookieService: CookieService) { }

    post(data, url): Observable<any> {
        let fullURL = this.config.API_URL + url
        console.info("Post: " + fullURL);
        let dataString = JSON.stringify(data); // Stringify
        console.info("Data:", dataString);
        console.info("Options", this.getOptions());
        return this.http.post(fullURL, dataString, this.getOptions())
            .catch(this.onCatch());
    }

    private getOptions(): RequestOptions {
		let headers;
		headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "*" });
		let options = new RequestOptions({ headers: headers });
        return options;
    }

    /**
     * Interceptor para captura genérica de errores http
     * */
    private onCatch() {
        return (res: Response) => {
        // Security errors
        if (res.status === 401 || res.status === 403) {
            // redirigir al usuario para pedir credenciales
            //this.router.navigate(['user/login']);
            console.log("Acceso denegado...");
        }
        // To Do: Gestión común de otros errores...
        return Observable.throw(res);
        };
    }

}
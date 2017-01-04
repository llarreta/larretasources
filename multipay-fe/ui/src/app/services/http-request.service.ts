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

    post(data: DefaultRequest): Observable<any> {
        let fullURL = this.config.API_URL
        if (this.config.MOCK) {
            fullURL = fullURL + data.sei;
        }
        console.info("Post: " + fullURL);
        let dataString = JSON.stringify(data); // Stringify
        console.info("Data:", dataString);
        console.info("Options", this.getOptions());
        return this.http.post(fullURL, dataString, this.getOptions())
            .map(this.extractData)
            .catch(this.error);
    }

    private getOptions(): RequestOptions {
		let headers;
		
		if (this.config.MOCK) {
			headers = new Headers({ 'Content-Type': 'application/json'});
		}else{
			headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "*" });
		}
		
        let options = new RequestOptions({ headers: headers });
        return options;
    }

    private extractData(res: Response) {
        let body = res.json();
        return body || {};
    }

    private error(error: any) {
        return Observable.throw(error.json().error || 'Server error');
    }

    /**
     * Create the default requestt object, and set the module name, endpoint and body (optional);
     */
    createRequest(moduleName: string, endpoint: string, data?: any): DefaultRequest {
        let cookie = this.cookieService.getObject(this.cookieName);
        console.log(cookie);
        return new DefaultRequest(
            "string",
            "string",
            moduleName,
            endpoint,
            data
        );
    }
}
import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Hero } from '../Entities/heroes';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';

@Injectable()
export class HeroService {

    private headers: Headers;

    constructor (private http: Http){
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.headers.append('Accept', 'application/json');
    }

    getHeroes (): Observable<Hero[]> {
        return this.http.get("app/Mock/heroes.json")
                        .map(this.extractData)
                        .catch(this.handleError);
    }
    
    addHero (name: string): Observable<Hero> {
        let body = JSON.stringify({ name });
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(this.heroesUrl, body, options)
                        .map(this.extractData)
                        .catch(this.handleError);   
    }

    private extractData(res: Response) {
        let body = res.json();
        return body.data || { };
    }
    
    private handleError (error: any) {
        // In a real world app, we might use a remote logging infrastructure
        // We'd also dig deeper into the error to get a better message
        let errMsg = (error.message) ? error.message :
        error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
    
}
import {Injectable} from '@angular/core';
import {Http, Headers} from '@angular/http';
import {AuthenticationService} from "../authentication/authentication.service";
import {Observable} from "rxjs/Observable";

@Injectable()
export class HttpService {

  constructor(private http: Http, private auth: AuthenticationService) {}

  public get(url: string): Observable<any> {
    return this.http.get(url, {headers: this.auth.getRequestHeader()});
  }

  public post(url: string, data: any): Observable<any> {
    return this.http.post(url, data, {headers: this.auth.getRequestHeader()});
  }
}

import { Injectable } from '@angular/core';
import { Http, Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/catch';
import "rxjs/add/operator/mergeMap";
import "rxjs/add/operator/do";
import {isNullOrUndefined} from "util";

@Injectable()
export class AuthenticationService {
  private static readonly apiUrl = 'http://10.64.0.41:8080/clktime';
  private static readonly tokenUrl = AuthenticationService.apiUrl + '/login/token';
  private static readonly userPath = AuthenticationService.apiUrl + '/test';
  private static readonly clientId ='clktime-app';
  private static readonly clientSecret ='ortec-secret';

  private static readonly authHeaders = new Headers({
    "Content-Type": "application/x-www-form-urlencoded",
    "Accept": "application/json",
    "Authorization": "Basic " + btoa(AuthenticationService.clientId + ":" + AuthenticationService.clientSecret)
  });

  constructor(private http: Http) {}

  public authenticate(username: string, password: string): Observable<any> {
    let client = "username=" +username + "&password=" +password + "&grant_type=password&scope=read%20write&" +
      "client_secret="+AuthenticationService.clientSecret+"&client_id="+AuthenticationService.clientId;

    return this.http.post(AuthenticationService.tokenUrl, client, {headers: AuthenticationService.authHeaders})
      .map(response => response.json())
      .do(json => {
        localStorage.setItem('token', json.access_token);
        localStorage.setItem('expiration_date', ((Date.now() / 1000) + json.expires_in));
      })
      .mergeMap(json => this.http.get(AuthenticationService.userPath, {headers: this.getRequestHeader()}))
      .do(request => localStorage.setItem('user', request.json()));
  }

  public validToken(): boolean {
    return !isNullOrUndefined(localStorage.getItem('token')) &&
      Date.now() / 1000 < +localStorage.getItem('expiration_date');
  }

  public getRequestHeader(): Headers {
    return new Headers({"Authorization": "Bearer " + localStorage.getItem('token')})
  }

  public logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('expiration_date');
    localStorage.removeItem('user');
  }
}

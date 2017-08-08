import { Injectable } from '@angular/core';
import { Http, Headers} from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { Router } from '@angular/router';
import {User} from "../../models/User";
import {error} from "selenium-webdriver";
import {ObservableInput} from "rxjs/Observable";

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

  constructor(private http: Http, private router: Router) {}

  public authenticate(username: string,
                      password: string,
                      onSuccess: () => void,
                      onFailure: (err: any, caught: Observable<any>) => Observable<any>): void
  {
    let client = "username=" +username + "&password=" +password + "&grant_type=password&scope=read%20write&" +
      "client_secret="+AuthenticationService.clientSecret+"&client_id="+AuthenticationService.clientId;

    this.http.post(AuthenticationService.tokenUrl, client, {headers: AuthenticationService.authHeaders})
      .catch(onFailure)
      .flatMap(response => {
        localStorage.setItem('token', response.json().access_token);
        return this.http.get(AuthenticationService.userPath, {headers: this.getRequestHeader()});
      })
      .do(request => {
        localStorage.setItem('user', request.json())
        onSuccess();
      })
      .subscribe();
  }

  public getRequestHeader(): Headers {
    return new Headers({"Authorization": "Bearer " + localStorage.getItem('token')})
  }

  public logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }
}

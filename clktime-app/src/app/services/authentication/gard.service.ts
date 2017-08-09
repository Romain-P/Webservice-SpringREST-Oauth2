import { Injectable } from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {}

  public canActivate(): boolean {
    if (this.authService.validToken())
      return true;

    this.router.navigateByUrl("/login");
    return false;
  }
}

@Injectable()
export class LoggedGuard implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {}

  public canActivate(): boolean {
    if (!this.authService.validToken())
      return true;

    this.router.navigateByUrl("/pages/dashboard");
    return false;
  }
}

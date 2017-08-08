import {Component} from '@angular/core';
import {FormGroup, AbstractControl, FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import { Router } from '@angular/router';
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'login',
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {

  public form:FormGroup;
  public email:AbstractControl;
  public password:AbstractControl;
  public submitted:boolean = false;

  constructor(fb:FormBuilder, private loginService:AuthenticationService, private router: Router) {
    this.form = fb.group({
      'email': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
    });

    this.email = this.form.controls['email'];
    this.password = this.form.controls['password'];
  }

  public onSubmit(values: Object):void {
    this.submitted = true;
    this.loginService.authenticate(values['email'], values['password'],
      () => this.router.navigateByUrl("/dashboard"), this.onFailure);
  }

  public onFailure(error: any) {
    let msg = error.message ? error.message
      : error.status ? `${error.status} - ${error.statusText}` : 'Server error';

    alert(msg);

    return Observable.throw(msg);
  }
}

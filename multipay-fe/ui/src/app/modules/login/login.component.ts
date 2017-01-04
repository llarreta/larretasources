import { Component, ViewEncapsulation } from '@angular/core';
import { LoginService } from './login.service'

@Component({
  templateUrl: './src/app/modules/login/login.component.html'
})

export class LoginComponent {

  constructor(private loginService: LoginService) { }

  login() {
    let data = { username: "gonzalo", password: "m" };
    this.loginService.login(data);

    //post.subscribe(data => this.log.info(data));
  }

}
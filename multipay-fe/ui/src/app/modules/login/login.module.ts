import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { LoginService } from './login.service';
import { LoginComponent } from './login.component';

@NgModule({
    imports:
    [
        CommonModule,
        NgbModule.forRoot(),
    ],
    providers: [
        LoginService,
    ],
    declarations: [
        LoginComponent
    ]
})
export class LoginModule { }
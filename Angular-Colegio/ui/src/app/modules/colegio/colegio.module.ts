import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Router
import { ProvidersRouter } from './colegio.router';

// Components
import { InitComponent } from './Init/init.component';
import { StudentComponent } from './Student/List/student.component';
import { InputCommonsComponent } from './Commons/Input/input.component';

@NgModule({
    imports:
    [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule.forRoot(),
        ProvidersRouter
    ],
    declarations:
    [
        InitComponent,
        StudentComponent,
        InputCommonsComponent,
    ],
    providers:
    [
    ]
})
export class ColegioModule { }

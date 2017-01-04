import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Router
import { UsersRouter } from './users.router';

// Components
import { UsersComponent } from './users.component';

import { UserCreationComponent } from './creation/user-creation.component';
import { UserCreationForm } from './creation/user-creation.form';

import { UserManagementComponent } from './management/user-management.component';

import { UserTokenComponent } from './token/user-token.component';

import { UserSignaturesComponent } from './signatures/user-signatures.component';

// Services
import { UserService } from './services/user.service';

// Directives
import { UniqueUsernameValidation } from './validations/unique-username.validation';
import { UniqueDocTypeAndDocumentValidation } from  './validations/unique-user-type-and-document.validation';


@NgModule({
    imports:
    [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule.forRoot(),
        UsersRouter
    ],
    declarations: 
    [
        UsersComponent,
        
        UserCreationComponent,

        UserManagementComponent,

        UserTokenComponent,

        UserSignaturesComponent,

        UniqueUsernameValidation,
        UniqueDocTypeAndDocumentValidation
    ],
    providers: 
    [
        UserService,
        UserCreationForm
    ]
})
export class UsersModule { }

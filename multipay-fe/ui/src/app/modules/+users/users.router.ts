import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UsersComponent } from './users.component';
import { UserCreationComponent } from './creation/user-creation.component';
import { UserManagementComponent } from './management/user-management.component';
import { UserTokenComponent } from './token/user-token.component';
import { UserSignaturesComponent } from './signatures/user-signatures.component';

const routes: Routes = [
      { path: 'gestion', component: UserManagementComponent },
      { path: 'alta', component: UserCreationComponent },
      { path: 'firmas', component: UserSignaturesComponent },
      { path: 'token', component: UserTokenComponent },
      { path: '', redirectTo: 'gestion', pathMatch: 'full' }
];

export const UsersRouter: ModuleWithProviders = RouterModule.forChild(routes);
import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Modules
//import { ProvidersModule } from './modules/+providers/providers.module';

// Shared Components
import { LoginComponent } from './modules/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'pp', loadChildren: 'app/modules/+providers/providers.module#ProvidersModule'},
  { path: 'au', loadChildren: 'app/modules/+users/users.module#UsersModule'}
];

export const AppRouter = RouterModule.forRoot(routes, { useHash: true });

import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Modules
//import { ProvidersModule } from './modules/+providers/providers.module';

// Shared Components
import { LoginComponent } from './modules/login/login.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'colegio', loadChildren: 'app/modules/colegio/colegio.module#ColegioModule'}
];

export const AppRouter = RouterModule.forRoot(routes, { useHash: true });

import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Modules
//import { ProvidersModule } from './modules/+providers/providers.module';

export const routes: Routes = [
  { path: '', redirectTo: 'colegio', pathMatch: 'full' },
  { path: 'colegio', loadChildren: 'app/modules/colegio/colegio.module#ColegioModule'}
];

export const AppRouter = RouterModule.forRoot(routes, { useHash: true });

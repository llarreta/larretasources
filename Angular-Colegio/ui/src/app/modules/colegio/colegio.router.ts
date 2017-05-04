import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { InitComponent } from './Init/init.component';
import { StudentComponent } from './Student/List/student.component';

const routes: Routes = [
    { path: '', component: InitComponent },
    { path: 'students', component: StudentComponent }
];

export const ProvidersRouter: ModuleWithProviders = RouterModule.forChild(routes);
import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { InitComponent } from './Init/init.component';
import { StudentComponent } from './Student/List/student.component';
import { CourseComponent } from './Course/List/course.component';
import { PaymentPlanComponent } from './PaymentPlan/List/paymentPlan.component';
import { PaymentRecordComponent } from './PaymentRecord/paymentRecord.component';

const routes: Routes = [
    { path: 'home', component: InitComponent },
    { path: '', component: InitComponent },
    { path: 'students', component: StudentComponent },
    { path: 'courses', component: CourseComponent },
    { path: 'paymentPlan', component: PaymentPlanComponent},
    { path: 'paymentRecord', component: PaymentRecordComponent}
];

export const ProvidersRouter: ModuleWithProviders = RouterModule.forChild(routes);
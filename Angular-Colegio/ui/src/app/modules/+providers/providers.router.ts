import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProvidersComponent } from './providers.component';
import { FilePaymentComponent } from './file-payment/file-payment.component';
import { ManualPaymentComponent } from './manual-payment/manual-payment.component';
import { PaymentListComponent } from './payment-list/payment-list.component';

const routes: Routes = [
    { path: '', component: ProvidersComponent },
    { path: 'archivo', component: FilePaymentComponent },
    { path: 'manual', component: ManualPaymentComponent },
    { path: 'lista', component: PaymentListComponent },
    { path: '', redirectTo: 'lista', pathMatch: 'full' }
];

export const ProvidersRouter: ModuleWithProviders = RouterModule.forChild(routes);
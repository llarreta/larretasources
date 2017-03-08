import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Router
import { ProvidersRouter } from './providers.router';

// Components
import { ProvidersComponent } from './providers.component';

import { FilePaymentComponent } from './file-payment/file-payment.component';
import { FilePaymentDetailComponent } from './file-payment/file-payment-detail.component';

import { ManualPaymentComponent } from './manual-payment/manual-payment.component';

import { PaymentListComponent } from './payment-list/payment-list.component';
import { HistoricPaymentListComponent } from './payment-list/historic-payment-list.component';
import { PendingPaymentListComponent } from './payment-list/pending-payment-list.component';


// Services
import { CompanyService } from './services/company.service';
import { FilePaymentService } from './services/file-payment.service';
import { PaymentListService } from './services/payment-list.service';
import { PaymentTypeService } from './services/payment-type.service';
import { ProviderService } from './services/provider.service';
import { StatusService } from './services/status.service';

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
        ProvidersComponent,

        FilePaymentComponent,
        FilePaymentDetailComponent,

        ManualPaymentComponent,

        PaymentListComponent,
        HistoricPaymentListComponent,
        PendingPaymentListComponent
    ],
    providers:
    [
        CompanyService,
        FilePaymentService,
        PaymentListService,
        PaymentTypeService,
        ProviderService,
        StatusService
    ]
})
export class ProvidersModule { }

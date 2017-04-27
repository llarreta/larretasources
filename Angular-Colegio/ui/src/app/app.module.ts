import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule, JsonpModule } from '@angular/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

// App Components
import { AppComponent } from './app.component';

// Router
import { AppRouter } from './app.router';

// Configuration
import { ConfigurationLoader } from './services/configuration-loader.service';

// Services
import { CookieService } from 'angular2-cookie/services/cookies.service';
import { HttpRequest } from './services/http-request.service';



// Shared Components
import { AppNavComponent } from './shared/app-nav/app-nav.component';
import { AppFooterComponent } from './shared/app-footer/app-footer.component';
import { FeedbackInfoComponent } from './shared/feedbacks/feedback-info.component';
import { FeedbackWarningComponent } from './shared/feedbacks/feedback-warning.component';
import { FeedbackErrorComponent } from './shared/feedbacks/feedback-error.component';
import { FeedbackSuccessComponent } from './shared/feedbacks/feedback-success.component';
import { AutocompleteComponent } from './shared/autocomplete/autocomplete.component';
import { ShowHidePass } from './shared/show-hide-pass/show-hide-pass.component';

import { ImageUploadModule } from './shared/image-uploader/image-upload.module';

import { ColegioModule } from './modules/colegio/colegio.module';

import {DataScrollerModule} from 'primeng/primeng';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRouter,
    HttpModule,
    JsonpModule,
    NgbModule.forRoot(),
    ReactiveFormsModule,
    ImageUploadModule.forRoot(),
    ColegioModule,
    DataScrollerModule
  ],
  providers: [
    CookieService,
    HttpRequest,
  ],
  declarations: [
    AppComponent,
    AppNavComponent,
    AppFooterComponent,
    FeedbackInfoComponent,
    FeedbackWarningComponent,
    FeedbackErrorComponent,
    FeedbackSuccessComponent,
    AutocompleteComponent,
    ShowHidePass,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }


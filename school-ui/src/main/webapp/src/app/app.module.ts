import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpModule, JsonpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

// App Components
import { AppComponent } from './app.component';

// Router
import { AppRouter } from './app.router';

// Configuration
import { ConfigurationLoader } from './services/configuration-loader.service';

// Services
import { HttpRequest } from './services/http-request.service';

// Shared Components
import { AppNavComponent } from './shared/app-nav/app-nav.component';
import { AppFooterComponent } from './shared/app-footer/app-footer.component';

import { ColegioModule } from './modules/colegio/colegio.module';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRouter,
    HttpModule,
    JsonpModule,
    ReactiveFormsModule,
    ColegioModule,
    BrowserAnimationsModule,
    NoopAnimationsModule
  ],
  providers: [
    HttpRequest,
  ],
  declarations: [
    AppComponent,
    AppNavComponent,
    AppFooterComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }


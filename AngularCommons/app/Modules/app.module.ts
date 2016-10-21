import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

//Simulador de servicios en memoria
import { InMemoryWebApiModule } from 'angular-in-memory-web-api/in-memory-web-api.module';
import { InMemoryDataService }  from '../Mock/in-memory-data.service';

import { AppComponent }  from '../Components/main-component/app.component';
import { HeaderComponent } from '../Components/header/header.component';
import { BodyComponent } from '../Components/body/body.component';
import { FooterComponent } from '../Components/footer/footer.component';
import { HeroSearchComponent }  from '../Components/hero-search/hero-search.component';
import { HeroDetailComponent } from '../Components/heroe-detail/heroe-detail.component';
import { HeroeList } from '../Components/heroes-list/heroe-list.component';
import { DashboardComponent } from '../Components/dashboard/dashboard.component';
import { MenuLeftComponent } from '../Components/menu-left/menu-left.component';
import { ContentBodyComponent } from '../Components/content-body/content-body.component';
import { LoadAppExampleComponent } from '../Components/load-app-example-component/load-app-example-component.component';

import { HeroService } from '../services/hero.service';
import { AppRoutingModule }     from './app-routing.module';
import '../rxjs-operators';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    AppRoutingModule,
    NgbModule.forRoot(),
  ],
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroeList,
    DashboardComponent,
    HeroSearchComponent,
    HeaderComponent,
    BodyComponent,
    FooterComponent,
    MenuLeftComponent,
    ContentBodyComponent,
    LoadAppExampleComponent
  ],
  providers: [
    HeroService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }

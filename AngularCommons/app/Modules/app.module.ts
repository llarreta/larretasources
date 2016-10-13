import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

//Simulador de servicios en memoria
import { InMemoryWebApiModule } from 'angular-in-memory-web-api/in-memory-web-api.module';
import { InMemoryDataService }  from '../Mock/in-memory-data.service';

import { AppComponent }  from '../Components/main-component/app.component';
import { HeroDetailComponent } from '../Components/heroe-detail/heroe-detail.component';
import { HeroeList } from '../Components/heroes-list/heroe-list.component';
import { DashboardComponent } from '../Components/dashboard/dashboard.component';
import { HeroService } from '../services/hero.service';
import { AppRoutingModule }     from './app-routing.module';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroeList,
    DashboardComponent
  ],
  providers: [
    HeroService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }

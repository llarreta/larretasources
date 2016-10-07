import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppComponent }  from './app.component';
import { HeroDetailComponent } from './Components/heroe-detail/heroe-detail.component';
import { HeroeList } from './Components/heroes-list/heroe-list.component';
import { HttpModule, JsonpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot([
      {
        path: 'heroes',
        component: HeroeList
      }
    ]),
    HttpModule,
    JsonpModule
  ],
  declarations: [
    AppComponent,
    HeroDetailComponent,
    HeroeList
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }

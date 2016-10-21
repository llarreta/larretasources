import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HeroDetailComponent } from '../Components/heroe-detail/heroe-detail.component';
import { HeroeList } from '../Components/heroes-list/heroe-list.component';
import { DashboardComponent } from '../Components/dashboard/dashboard.component';
import { LoadAppExampleComponent } from '../Components/load-app-example-component/load-app-example-component.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard',  component: DashboardComponent },
  { path: 'detail/:id', component: HeroDetailComponent },
  { path: 'heroes',     component: HeroeList },
  { path: 'example',     component: LoadAppExampleComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

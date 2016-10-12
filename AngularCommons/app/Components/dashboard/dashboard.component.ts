import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Hero } from '../../Entities/heroes';
import { HeroService } from '../../Services/hero.service';

@Component({
  moduleId: module.id,
  selector: 'my-dashboard',
  providers: [HeroService],
  templateUrl: 'dashboard.html',
  styleUrls: ['dashboard.css']
})
export class DashboardComponent implements OnInit {

  heroes: Hero[] = [];

  constructor(
    private router: Router,
    private heroService: HeroService) {
  }



    ngOnInit(){
      this.getHeroes();
    }

    private getHeroes(){
      this.heroService.getHeroes()
        .then(heroes => this.heroes = heroes.slice(1, 5));
    }
  
  gotoDetail(hero: Hero): void {
    let link = ['/detail', hero.id];
    this.router.navigate(link);
  }

}

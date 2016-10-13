import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Hero } from "../../Entities/heroes";
import { HeroService } from '../../services/hero.service';
import { HeroDetailComponent } from "../../Components/heroe-detail/heroe-detail.component.ts";

@Component({
  moduleId: module.id,
  selector: 'heroe-list',
  providers: [HeroService],
  templateUrl: 'heroe-list.html',
  styleUrls:  ['heroe-list.css']
})

export class HeroeList implements OnInit{ 

    errorMessage : string;

    constructor(
      private router: Router, 
      private heroService : HeroService
      ){}

    ngOnInit(){
      this.getHeroes();
    }

    private getHeroes(){
      this.heroService.getHeroes().then(heroes => this.heroes = heroes);
    }

    add(name: string): void {
      name = name.trim();
      if (!name) { return; }
      this.heroService.create(name)
        .then(hero => {
          this.heroes.push(hero);
          this.selectedHero = null;
        });
    }

    delete(hero: Hero): void {
      this.heroService
          .delete(hero.id)
          .then(() => {
            this.heroes = this.heroes.filter(h => h !== hero);
            if (this.selectedHero === hero) { this.selectedHero = null; }
          });
    }


    title: string = "Tour of Heroes";
    heroes: Hero[];
    selectedHero: Hero;

    onSelect(hero: Hero): void {
        this.selectedHero = hero;
    }

    gotoDetail(): void {
      this.router.navigate(['/detail', this.selectedHero.id]);
    }

}
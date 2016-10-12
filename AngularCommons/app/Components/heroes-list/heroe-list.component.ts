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

    addHero (name: string) {
      if (!name) { return; }
      //this.heroService.addHero(name)
      //                .subscribe(
      //                  hero  => this.heroes.push(hero),
      //                  error =>  this.errorMessage = <any>error);
      //this.heroService.addHeroes().then(heroes => this.heroes = heroes);
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
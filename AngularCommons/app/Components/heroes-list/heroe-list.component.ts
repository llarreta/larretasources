import { Component, OnInit } from '@angular/core';
import { Hero } from "../../Entities/heroes";
import { HeroService } from '../../services/hero.service';
import { HeroDetailComponent } from "../../Components/heroe-detail/heroe-detail.component.ts";

@Component({
  selector: 'heroe-list',
  providers: [HeroService] ,
  templateUrl: 'app/Components/heroes-list/heroe-list.html',
  styleUrls:  ['app/Components/heroes-list/heroe-list.css']
})

export class HeroeList implements OnInit{ 

    errorMessage : string;

    constructor(private heroService : HeroService){}

    ngOnInit(){
      this.getHeroes();
    }

    private getHeroes(){
      this.heroService.getHeroes()
                     .subscribe(
                       heroes => this.heroes = heroes,
                       error =>  this.errorMessage = <any>error);
    }

    addHero (name: string) {
      if (!name) { return; }
      this.heroService.addHero(name)
                      .subscribe(
                        hero  => this.heroes.push(hero),
                        error =>  this.errorMessage = <any>error);
    }


    title: string = "Tour of Heroes";
    heroes: Hero[];
    selectedHero: Hero;

    onSelect(hero: Hero): void {
        this.selectedHero = hero;
    }


}

import { Component, OnInit } from '@angular/core';
import { Hero } from "./Entities/heroes";
import { HeroService } from './services/hero.service';
import { HeroDetailComponent } from "./Components/heroe-detail/heroe-detail.component.ts";
// Add the RxJS Observable operators we need in this app.
import './rxjs-operators';


@Component({
  selector: 'init-angular-app',
  providers: [HeroService] ,
  templateUrl: 'app/app.component.html',
  styleUrls:  ['app/app.component.css']
})

export class AppComponent implements OnInit{ 

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

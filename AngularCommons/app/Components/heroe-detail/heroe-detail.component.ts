import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';
import { HeroService } from '../../services/hero.service';
import { Hero } from "../../Entities/heroes";


@Component({
  moduleId: module.id,
  selector: 'my-hero-detail',
  providers: [HeroService],
  templateUrl: 'heroe-detail.html',
  styleUrls: ['heroe-detail.css']
})
export class HeroDetailComponent implements OnInit {

  ngOnInit(): void {
    this.route.params.forEach((params: Params) => {
      let id = +params['id'];
      this.heroService.getHero(id)
        .then(hero => this.hero = hero);
    });
  }

  constructor(
    private heroService: HeroService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  @Input()
  hero: Hero;

  goBack(): void {
    this.location.back();
  }
  
  save(): void {
    this.heroService.update(this.hero)
      .then(() => this.goBack());
  }
}
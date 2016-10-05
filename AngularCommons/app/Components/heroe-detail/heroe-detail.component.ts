import { Component, Input } from '@angular/core';
import { Hero } from "../../Entities/heroes";

@Component({
  selector: 'my-hero-detail',
  templateUrl: 'app/Components/heroe-detail/heroe-detail.html',
  styleUrls: ['app/Components/heroe-detail/heroe-detail.css']
})
export class HeroDetailComponent {

  @Input()
  hero: Hero;


}
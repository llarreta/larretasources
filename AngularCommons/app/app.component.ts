import { Component, OnInit } from '@angular/core';
import { Hero } from "./Entities/heroes";
import { HeroeList } from "./Components/heroes-list/heroe-list.component.ts";

@Component({
  selector: 'app-commons',
  templateUrl: 'app/app.component.html',
  styleUrls:  ['app/app.component.css']
})

export class AppComponent implements OnInit{ 

  title:string = "Hola app";

  ngOnInit(){
      
  }
    
}

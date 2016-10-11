"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var hero_service_1 = require('../../services/hero.service');
var HeroeList = (function () {
    function HeroeList(heroService) {
        this.heroService = heroService;
        this.title = "Tour of Heroes";
    }
    HeroeList.prototype.ngOnInit = function () {
        this.getHeroes();
    };
    HeroeList.prototype.getHeroes = function () {
        var _this = this;
        //this.heroService.getHeroes()
        //               .subscribe(
        //                 heroes => this.heroes = heroes,
        //                 error =>  this.errorMessage = <any>error);
        this.heroService.getHeroes().then(function (heroes) { return _this.heroes = heroes; });
    };
    HeroeList.prototype.addHero = function (name) {
        if (!name) {
            return;
        }
        //this.heroService.addHero(name)
        //                .subscribe(
        //                  hero  => this.heroes.push(hero),
        //                  error =>  this.errorMessage = <any>error);
        //this.heroService.addHeroes().then(heroes => this.heroes = heroes);
    };
    HeroeList.prototype.onSelect = function (hero) {
        this.selectedHero = hero;
    };
    HeroeList = __decorate([
        core_1.Component({
            selector: 'heroe-list',
            providers: [hero_service_1.HeroService],
            templateUrl: 'app/Components/heroes-list/heroe-list.html',
            styleUrls: ['app/Components/heroes-list/heroe-list.css']
        }), 
        __metadata('design:paramtypes', [hero_service_1.HeroService])
    ], HeroeList);
    return HeroeList;
}());
exports.HeroeList = HeroeList;
//# sourceMappingURL=heroe-list.component.js.map
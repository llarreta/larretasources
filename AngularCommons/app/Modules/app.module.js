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
var platform_browser_1 = require('@angular/platform-browser');
var forms_1 = require('@angular/forms');
var http_1 = require('@angular/http');
var ng_bootstrap_1 = require('@ng-bootstrap/ng-bootstrap');
//Simulador de servicios en memoria
var in_memory_web_api_module_1 = require('angular-in-memory-web-api/in-memory-web-api.module');
var in_memory_data_service_1 = require('../Mock/in-memory-data.service');
var app_component_1 = require('../Components/main-component/app.component');
var header_component_1 = require('../Components/header/header.component');
var body_component_1 = require('../Components/body/body.component');
var footer_component_1 = require('../Components/footer/footer.component');
var hero_search_component_1 = require('../Components/hero-search/hero-search.component');
var heroe_detail_component_1 = require('../Components/heroe-detail/heroe-detail.component');
var heroe_list_component_1 = require('../Components/heroes-list/heroe-list.component');
var dashboard_component_1 = require('../Components/dashboard/dashboard.component');
var menu_left_component_1 = require('../Components/menu-left/menu-left.component');
var content_body_component_1 = require('../Components/content-body/content-body.component');
var load_app_example_component_component_1 = require('../Components/load-app-example-component/load-app-example-component.component');
var hero_service_1 = require('../services/hero.service');
var app_routing_module_1 = require('./app-routing.module');
require('../rxjs-operators');
var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        core_1.NgModule({
            imports: [
                platform_browser_1.BrowserModule,
                forms_1.FormsModule,
                http_1.HttpModule,
                in_memory_web_api_module_1.InMemoryWebApiModule.forRoot(in_memory_data_service_1.InMemoryDataService),
                app_routing_module_1.AppRoutingModule,
                ng_bootstrap_1.NgbModule.forRoot(),
            ],
            declarations: [
                app_component_1.AppComponent,
                heroe_detail_component_1.HeroDetailComponent,
                heroe_list_component_1.HeroeList,
                dashboard_component_1.DashboardComponent,
                hero_search_component_1.HeroSearchComponent,
                header_component_1.HeaderComponent,
                body_component_1.BodyComponent,
                footer_component_1.FooterComponent,
                menu_left_component_1.MenuLeftComponent,
                content_body_component_1.ContentBodyComponent,
                load_app_example_component_component_1.LoadAppExampleComponent
            ],
            providers: [
                hero_service_1.HeroService
            ],
            bootstrap: [app_component_1.AppComponent]
        }), 
        __metadata('design:paramtypes', [])
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map
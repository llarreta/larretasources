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
var router_1 = require('@angular/router');
var LoadAppExampleComponent = (function () {
    function LoadAppExampleComponent(router) {
        this.router = router;
    }
    LoadAppExampleComponent.prototype.ngOnInit = function () {
    };
    LoadAppExampleComponent.prototype.resizeIframe = function (iframe) {
        console.log("hola");
        var iframe2 = document.getElementById("otherApp").contentWindow.document.getElementsByTagName('body');
        //.document.body.scrollHeight + 'px';
        //console.log(iframe2);
    };
    LoadAppExampleComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'load-app-example-component-common',
            templateUrl: 'load-app-example-component.html',
            styleUrls: ['load-app-example-component.css']
        }), 
        __metadata('design:paramtypes', [router_1.Router])
    ], LoadAppExampleComponent);
    return LoadAppExampleComponent;
}());
exports.LoadAppExampleComponent = LoadAppExampleComponent;
//# sourceMappingURL=load-app-example-component.component.js.map
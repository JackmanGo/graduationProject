"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var platform_browser_1 = require("@angular/platform-browser");
var ng2_bootstrap_1 = require("ng2-bootstrap");
var http_1 = require("@angular/http");
var router_1 = require("@angular/router");
var app_component_1 = require("./app.component");
var app_list_1 = require("./list/app.list");
var app_detail_1 = require("./detail/app.detail");
var app_service_1 = require("./app.service");
var ng2_bs3_modal_1 = require("ng2-bs3-modal/ng2-bs3-modal");
var cookies_service_1 = require("angular2-cookie/services/cookies.service");
var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    core_1.NgModule({
        declarations: [app_list_1.AppList, app_detail_1.AppDetail, app_component_1.AppComponent],
        imports: [
            router_1.RouterModule.forRoot([
                {
                    //默认路径，即根路径/
                    path: '',
                    component: app_list_1.AppList
                }, {
                    path: 'detail/:id',
                    component: app_detail_1.AppDetail
                }
            ]),
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            ng2_bootstrap_1.AlertModule.forRoot(),
            ng2_bootstrap_1.DatepickerModule.forRoot(),
            http_1.HttpModule,
            ng2_bs3_modal_1.Ng2Bs3ModalModule
        ],
        providers: [app_service_1.AppService, app_detail_1.AppDetail, cookies_service_1.CookieService],
        bootstrap: [app_component_1.AppComponent]
    })
], AppModule);
exports.AppModule = AppModule;
//# sourceMappingURL=app.module.js.map
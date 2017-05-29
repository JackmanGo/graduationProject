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
var core_1 = require("@angular/core");
var app_service_1 = require("../app.service");
var AppList = (function () {
    function AppList(appService) {
        this.appService = appService;
        this.countdownTimeStr = '01:12:07:13';
        this.countdownFlipTimeStr = '00:04:13:17';
    }
    AppList.prototype.ngOnInit = function () {
        var _this = this;
        this.appService.requestGoodsList().then(function (results) {
            _this.seckills = results;
        });
    };
    AppList.prototype.onElapse = function () {
        alert('Countdown is finished!');
    };
    AppList.prototype.onChange = function (value) {
        console.log(value);
    };
    AppList.prototype.getTime = function (data) {
        var date = new Date(data); //传个时间戳过去就可以了
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = (date.getMinutes() + 1 < 10 ? '0' + (date.getMinutes()) : date.getMinutes());
        return Y + M + D + h + m;
    };
    return AppList;
}());
AppList = __decorate([
    core_1.Component({
        selector: 'list',
        template: "<div class=\"container\">\n      <div class=\"panel panel-default\">\n          <div class=\"panel-heading text-center\">\n              <h2>\u79D2\u6740\u5217\u8868</h2>\n          </div>\n          <div class=\"panel-body\">\n              <table class=\"table table-hover\">\n                  <thead>\n                  <tr>\n                    <th>\u540D\u79F0</th>\n                    <th>\u5E93\u5B58</th>\n                    <th>\u5F00\u59CB\u65F6\u95F4</th>\n                    <th>\u7ED3\u675F\u65F6\u95F4</th>\n                    <th>\u521B\u5EFA\u65F6\u95F4</th>\n                    <th>\u8BE6\u60C5\u9875</th>\n                  </tr>\n                  </thead>\n                  <tbody>\n                    <tr *ngFor=\"let sk of seckills;let i=index\">\n                      <td>{{sk.name}}</td>\n                      <td>{{sk.number}}</td>\n                      <td>{{getTime(sk.startTime)}}</td>\n                      <td>{{getTime(sk.endTime)}}</td>\n                      <td>{{getTime(sk.createTime)}}</td>\n                      <td><a class=\"btn btn-info\" routerLink=\"/detail/{{sk.seckillId}}\" target=\"_blank\">\u8BE6\u60C5</a></td>\n                    </tr>\n                  </tbody>\n              </table>\n          </div>\n      </div>\n  </div>"
    }),
    __metadata("design:paramtypes", [app_service_1.AppService])
], AppList);
exports.AppList = AppList;
//# sourceMappingURL=app.list.js.map
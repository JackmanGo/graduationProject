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
var router_1 = require("@angular/router");
var ng2_bs3_modal_1 = require("ng2-bs3-modal/ng2-bs3-modal");
var core_2 = require("angular2-cookie/core");
var Rx_1 = require("rxjs/Rx");
var AppDetail = (function () {
    function AppDetail(appService, router, cookieService) {
        this.appService = appService;
        this.router = router;
        this.cookieService = cookieService;
    }
    AppDetail.prototype.ngOnInit = function () {
        var _this = this;
        this.router.params.forEach(function (params) {
            _this.appService.requestGoodsDetail(params['id']).then(function (results) {
                _this.seckill = results;
                console.log(_this.seckill);
                _this.checkUserInfo();
            });
        });
    };
    AppDetail.prototype.checkUserInfo = function () {
        //手机验证和登录,计时交互
        //规划我们的交互流程
        //在cookie中查找手机号
        var userPhone = this.cookieService.get("userPhone");
        //验证手机号
        if (!this.validatePhone(userPhone)) {
            //绑定手机
            this.modal.open();
        }
        else {
            this.modal.close();
            this.userCompleteLogin();
        }
    };
    AppDetail.prototype.userCompleteLogin = function () {
        var _this = this;
        //已经登录
        //计时交互
        this.appService.requestNowTime().then(function (nowTime) {
            _this.nowTime = nowTime;
            //时间判断 计时交互
            _this.calculateCountDown(_this.seckill.seckillId, _this.nowTime, _this.seckill.startTime, _this.seckill.endTime);
        });
    };
    AppDetail.prototype.killPhoneKey = function (inputPhone) {
        console.log("inputPhone: " + inputPhone + this.validatePhone(inputPhone));
        if (this.validatePhone(inputPhone)) {
            //电话写入cookie(7天过期)
            this.cookieService.put('userPhone', inputPhone);
            //验证通过
            var userPhone = this.cookieService.get("userPhone");
            ;
            this.userCompleteLogin();
        }
        else {
            //todo 错误文案信息抽取到前端字典里
            $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
        }
    };
    AppDetail.prototype.validatePhone = function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true; //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        }
        else {
            return false;
        }
    };
    AppDetail.prototype.calculateCountDown = function (seckillId, nowTime, startTime, endTime) {
        var _this = this;
        var seckillBox = $('#seckill-box');
        console.log("nowTime:" + nowTime);
        console.log("startTime:" + startTime);
        console.log("endTime:" + endTime);
        //test data
        nowTime = 1487214167223;
        startTime = 1487224167223;
        endTime = 1487214367223;
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束!');
        }
        else if (nowTime < startTime) {
            //秒杀未开始,计时事件绑定
            console.log(startTime);
            var killTime_1 = new Date(startTime + 1000); //todo 防止时间偏移
            this.countTimeDiff = startTime - nowTime;
            console.log(this.countTimeDiff);
            Rx_1.Observable.interval(1000).map(function (x) {
                _this.countTimeDiff = Math.floor((killTime_1.getTime() - new Date().getTime()) / 1000);
            }).subscribe(function (x) {
                _this.countTimeMessage = _this.dhms(_this.countTimeDiff);
                console.log(_this.countTimeMessage);
                seckillBox.html('秒杀开始时间!' + _this.countTimeMessage);
                //秒杀开始
                _this.handlerSeckill(seckillId, seckillBox);
            });
        }
        else {
            //秒杀开始
            this.handlerSeckill(seckillId, seckillBox);
        }
    };
    AppDetail.prototype.handlerSeckill = function (seckillId, node) {
        //获取秒杀地址,控制显示器,执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        //开启秒杀
        //获取秒杀地址
        this.appService.requestSeckillUrl(seckillId).then(function (exposer) {
            if (exposer.data.exposed) {
                var md5_1 = exposer.data.md5;
                //绑定一次点击事件
                $('#killBtn').one('click', function () {
                    //执行秒杀请求
                    //1.先禁用按钮
                    $(this).addClass('disabled');
                    //2.发送秒杀请求执行秒杀
                    this.appService.executionSeckill(seckillId, md5_1).then(function (seckilledResult) {
                        if (seckilledResult.success) {
                            var killResult = seckilledResult.data;
                            var state = killResult.state;
                            var stateInfo = killResult.stateInfo;
                            //显示秒杀结果
                            node.html('<span class="label label-success">' + stateInfo + '</span>');
                        }
                    });
                });
                node.show();
            }
            else {
                //未开启秒杀(浏览器计时偏差)
                var now = exposer['now'];
                var start = exposer['start'];
                var end = exposer['end'];
            }
        });
    };
    //calculate time
    AppDetail.prototype.dhms = function (t) {
        var days, hours, minutes, seconds;
        days = Math.floor(t / 86400);
        t -= days * 86400;
        hours = Math.floor(t / 3600) % 24;
        t -= hours * 3600;
        minutes = Math.floor(t / 60) % 60;
        t -= minutes * 60;
        seconds = t % 60;
        return [
            days + '天',
            hours + '小时',
            minutes + '分钟',
            seconds + '秒'
        ].join(' ');
    };
    return AppDetail;
}());
__decorate([
    core_1.ViewChild('killPhoneModal'),
    __metadata("design:type", ng2_bs3_modal_1.ModalComponent)
], AppDetail.prototype, "modal", void 0);
AppDetail = __decorate([
    core_1.Component({
        selector: 'detail',
        template: "<div class=\"container\">\n    <div class=\"panel panel-default text-center\">\n        <div class=\"pannel-heading\">\n            <h1></h1>\n        </div>\n        <div class=\"panel-body\">\n            <h2 class=\"text-danger\">\n                <span class=\"glyphicon glyphicon-time\"></span>\n                <span class=\"glyphicon\" id=\"seckill-box\"></span>\n            </h2>\n        </div>\n    </div>\n</div>\n<modal [animation]=\"true\" [keyboard]=\"false\" [backdrop]=\"false\"\n    [cssClass]=\"cssClass\" #killPhoneModal>\n        <form #modalForm=\"ngForm\">\n            <modal-header [show-close]=\"true\">\n                <h3 class=\"modal-title text-center\">\n                    <span class=\"glyphicon glyphicon-phone\"> </span>\u79D2\u6740\u7535\u8BDD:\n                </h3>\n            </modal-header>\n            <modal-body>\n                <div class=\"row\">\n                    <div class=\"col-xs-8 col-xs-offset-2\">\n                        <input type=\"text\" name=\"killPhone\" placeholder=\"\u586B\u5199\u624B\u673A\u53F7^o^\" class=\"form-control\" #phoneInput>\n                    </div>\n                </div>\n            </modal-body>\n            <modal-footer>\n                <span id=\"killPhoneMessage\" class=\"glyphicon\"> </span>\n                <button type=\"button\" id=\"killPhoneBtn\" class=\"btn btn-success\" (click)=\"killPhoneKey(phoneInput.value)\">\n                    <span class=\"glyphicon glyphicon-phone\"></span>\n                    Submit\n                </button>\n            </modal-footer>\n        </form>\n</modal>"
    }),
    __metadata("design:paramtypes", [app_service_1.AppService, router_1.ActivatedRoute, core_2.CookieService])
], AppDetail);
exports.AppDetail = AppDetail;
//# sourceMappingURL=app.detail.js.map
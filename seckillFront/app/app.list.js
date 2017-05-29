"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var AppList = (function () {
    function AppList() {
        this.name = "wx";
    }
    return AppList;
}());
AppList = __decorate([
    core_1.Component({
        selector: 'my-app',
        template: "\n    <html>\n<head>\n    <title>\u79D2\u6740\u5546\u54C1\u5217\u8868</title>\n</head>\n<body>\n<div class=\"container\">\n    <div class=\"panel panel-default\">\n        <div class=\"panel-heading text-center\">\n            <h2>\u79D2\u6740\u5217\u8868</h2>\n        </div>\n        <div class=\"panel-body\">\n            <table class=\"table table-hover\">\n                <thead>\n                <tr>\n                    <th>\u540D\u79F0</th>\n                    <th>\u5E93\u5B58</th>\n                    <th>\u5F00\u59CB\u65F6\u95F4</th>\n                    <th>\u7ED3\u675F\u65F6\u95F4</th>\n                    <th>\u521B\u5EFA\u65F6\u95F4</th>\n                    <th>\u8BE6\u60C5\u9875</th>\n                </tr>\n                </thead>\n                <tbody>\n                <c:forEach items=\"" + list + "\" var=\"sk\">\n                    <tr>\n                        <td>" + sk.name + "</td>\n                        <td>" + sk.number + "</td>\n                        <td>\n                            <fmt:formatDate value=\"" + sk.startTime + "\" pattern=\"yyyy-MM-dd HH:mm:ss\" />\n                        </td>\n                        <td>\n                            <fmt:formatDate value=\"" + sk.endTime + "\" pattern=\"yyyy-MM-dd HH:mm:ss\" />\n                        </td>\n                        <td>\n                            <fmt:formatDate value=\"" + sk.createTime + "\" pattern=\"yyyy-MM-dd HH:mm:ss\" />\n                        </td>\n                        <td><a class=\"btn btn-info\" href=\"/seckill/" + sk.seckillId + "/detail\" target=\"_blank\">\u8BE6\u60C5</a></td>\n                    </tr>\n                </c:forEach>\n                </tbody>\n            </table>\n\n        </div>\n    </div>\n</div>\n</body>\n</html>\n  ",
    })
], AppList);
exports.AppList = AppList;
//# sourceMappingURL=app.list.js.map
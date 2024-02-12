"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.AppModule = void 0;
var common_1 = require("@nestjs/common");
var app_controller_1 = require("./app.controller");
var app_service_1 = require("./app.service");
var shared_module_1 = require("./shared/shared/shared.module");
var core_1 = require("@nestjs/core");
var http_filter_1 = require("./shared/utills/http.filter");
var forex_module_1 = require("./features/forex/forex.module");
var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        (0, common_1.Module)({
            imports: [
                shared_module_1.SharedModule,
                forex_module_1.ForexModule
            ],
            controllers: [app_controller_1.AppController],
            providers: [
                {
                    provide: core_1.APP_FILTER,
                    useClass: http_filter_1.HttpExceptionFilter
                },
                app_service_1.AppService,
            ]
        })
    ], AppModule);
    return AppModule;
}());
exports.AppModule = AppModule;

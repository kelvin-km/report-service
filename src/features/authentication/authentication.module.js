"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.AuthenticationModule = void 0;
var common_1 = require("@nestjs/common");
var core_1 = require("@nestjs/core");
var auth_guard_1 = require("./auth-guard/auth-guard");
var shared_module_1 = require("../../shared/shared/shared.module");
var AuthenticationModule = /** @class */ (function () {
    function AuthenticationModule() {
    }
    AuthenticationModule = __decorate([
        (0, common_1.Module)({
            imports: [shared_module_1.SharedModule],
            providers: [
                {
                    provide: core_1.APP_GUARD,
                    useClass: auth_guard_1.AuthGuard
                },
            ]
        })
    ], AuthenticationModule);
    return AuthenticationModule;
}());
exports.AuthenticationModule = AuthenticationModule;

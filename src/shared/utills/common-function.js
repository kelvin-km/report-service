"use strict";
exports.__esModule = true;
exports.CommonFunction = void 0;
var date_fns_1 = require("date-fns");
var CommonFunction = /** @class */ (function () {
    function CommonFunction() {
    }
    CommonFunction.prototype.getuuid = function () {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    };
    CommonFunction.prototype.getCurrentTimestamp = function () {
        return (0, date_fns_1.format)(new Date(), "yyyy-MM-dd'T'HH:mm:ss");
    };
    return CommonFunction;
}());
exports.CommonFunction = CommonFunction;

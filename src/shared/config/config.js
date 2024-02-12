"use strict";
exports.__esModule = true;
exports.config = exports.baseUrls = void 0;
exports.baseUrls = {
    SOA_BASE_URL: process.env.SOA_BASE_URL
};
exports.config = {
    SOA_BASE_URL: process.env.SOA_BASE_URL,
    SOA_USERNAME: process.env.SOA_BASE_URL,
    SOA_PASSWORD: process.env.SOA_BASE_URL,
    FOREX_PATH: exports.baseUrls.SOA_BASE_URL + "/BS/Common/CurrencyExchangeRate/Get/2.0"
};

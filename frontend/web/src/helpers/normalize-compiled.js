'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});
/**
 * Created by gwonseongbong on 8/3/16.
 */

var normalizeYear = exports.normalizeYear = function normalizeYear(value, previousValue) {
    if (!value) {
        return value;
    }
    var onlyNums = String(value).replace(/[^\d]/g, '');
    if (onlyNums > 9999) {
        return previousValue;
    }
    return onlyNums;
};

var normalizeSubject = exports.normalizeSubject = function normalizeSubject(value, previousValue) {
    if (!value) {
        return value;
    }
    if (String(value).length > 100) {
        return previousValue;
    }
    return value;
};

//# sourceMappingURL=normalize-compiled.js.map
'use strict';

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _extends2 = require('babel-runtime/helpers/extends');

var _extends3 = _interopRequireDefault(_extends2);

exports.default = reducer;
exports.isLoaded = isLoaded;
exports.load = load;
exports.authToken = authToken;
exports.login = login;
exports.logout = logout;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * Created by gwonseongbong on 8/10/16.
 */

var AUTH_LOAD = 'auth/AUTH_LOAD';
var AUTH_LOAD_SUCCESS = 'auth/AUTH_LOAD_SUCCESS';
var AUTH_LOAD_FAIL = 'auth/AUTH_LOAD_FAIL';

var AUTH_TOKEN = 'auth/AUTH_TOKEN';
var AUTH_TOKEN_SUCCESS = 'auth/AUTH_TOKEN_SUCCESS';
var AUTH_TOKEN_FAIL = 'auth/AUTH_TOKEN_FAIL';

var LOGIN = 'auth/LOGIN';
var LOGIN_SUCCESS = 'auth/LOGIN_SUCCESS';
var LOGIN_FAIL = 'auth/LOGIN_FAIL';

var LOGOUT = 'auth/LOGOUT';
var LOGOUT_SUCCESS = 'auth/LOGOUT_SUCCESS';
var LOGOUT_FAIL = 'auth/LOGOUT_FAIL';

var initialState = {
    loaded: false
};

function reducer() {
    var state = arguments.length <= 0 || arguments[0] === undefined ? initialState : arguments[0];
    var action = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    switch (action.type) {
        case AUTH_LOAD:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case AUTH_LOAD_SUCCESS:
            if (action.result.email) {
                return (0, _extends3.default)({}, state, {
                    loading: false,
                    loaded: true,
                    user: action.result
                });
            }

            return (0, _extends3.default)({}, state, {
                loading: false,
                loaded: false
            });
        case AUTH_LOAD_FAIL:
            return (0, _extends3.default)({}, state);
        case AUTH_TOKEN:
            return (0, _extends3.default)({}, state);
        case AUTH_TOKEN_SUCCESS:
            return (0, _extends3.default)({}, state, {
                token: action.result.token
            });
        case LOGIN:
            return (0, _extends3.default)({}, state);
        case LOGIN_SUCCESS:
            return (0, _extends3.default)({}, state, {
                loaded: true,
                user: action.result
            });
        case LOGIN_FAIL:
            return (0, _extends3.default)({}, state);
        case LOGOUT:
            return (0, _extends3.default)({}, state);
        case LOGOUT_SUCCESS:
        case LOGOUT_FAIL:
            return (0, _extends3.default)({}, state, {
                user: null,
                token: null,
                loaded: false
            });
        default:
            return state;
    }
}

function isLoaded(globalState) {
    return globalState.auth && globalState.auth.loaded;
}

function load() {
    return {
        types: [AUTH_LOAD, AUTH_LOAD_SUCCESS, AUTH_LOAD_FAIL],
        promise: function promise(client) {
            return client.get('/auth/me');
        }
    };
}

function authToken() {
    return {
        types: [AUTH_TOKEN, AUTH_TOKEN_SUCCESS, AUTH_TOKEN_FAIL],
        promise: function promise(client) {
            return client.get('/auth');
        }
    };
}

function login(values, token) {
    return {
        types: [LOGIN, LOGIN_SUCCESS, LOGIN_FAIL],
        promise: function promise(client) {
            return client.post('/auth/login', {
                data: values,
                token: token,
                authorization: 'Basic ' + btoa(values.email + ':' + values.password)
            });
        }
    };
}

function logout(token) {
    return {
        types: [LOGOUT, LOGOUT_SUCCESS, LOGOUT_FAIL],
        promise: function promise(client) {
            return client.post('/auth/logout', {
                token: token
            });
        }
    };
}

//# sourceMappingURL=auth-compiled.js.map
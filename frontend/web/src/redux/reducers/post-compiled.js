"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});

var _assign = require("babel-runtime/core-js/object/assign");

var _assign2 = _interopRequireDefault(_assign);

var _extends2 = require("babel-runtime/helpers/extends");

var _extends3 = _interopRequireDefault(_extends2);

exports.default = reducer;
exports.findAll = findAll;
exports.findList = findList;
exports.findOne = findOne;
exports.resetOne = resetOne;
exports.create = create;
exports.update = update;
exports.remove = remove;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * Created by 권성봉 on 2016. 8. 1..
 */
var FIND_ALL = "post/FIND";
var FIND_ALL_SUCCESS = "post/FIND_ALL_SUCCESS";
var FIND_ALL_FAIL = "post/FIND_ALL_FAIL";

var FIND_LIST = "post/FIND_LIST";
var FIND_LIST_SUCCESS = "post/FIND_LIST_SUCCESS";
var FIND_LIST_FAIL = "post/FIND_LIST_FAIL";

var FIND_ONE = "post/FIND_ONE";
var FIND_ONE_SUCCESS = "post/FIND_ONE_SUCCESS";
var FIND_ONE_FAIL = "post/FIND_ONE_FAIL";

var RESET_ONE = "post/RESET_ONE";

var CREATE = "post/CREATE";
var CREATE_SUCCESS = "post/CREATE_SUCCESS";
var CREATE_FAIL = "post/CREATE_FAIL";

var UPDATE = "post/UPDATE";
var UPDATE_SUCCESS = "post/UPDATE_SUCCESS";
var UPDATE_FAIL = "post/UPDATE_FAIL";

var REMOVE = "post/REMOVE";
var REMOVE_SUCCESS = "post/REMOVE_SUCCESS";
var REMOVE_FAIL = "post/REMOVE_FAIL";

var initialState = {
    loading: false,
    postList: {},
    post: {}
};

function reducer() {
    var state = arguments.length <= 0 || arguments[0] === undefined ? initialState : arguments[0];
    var action = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    switch (action.type) {
        case FIND_ALL:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case FIND_ALL_SUCCESS:
            return (0, _extends3.default)({}, state, {
                postList: action.result,
                loading: false
            });
        case FIND_ALL_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case FIND_LIST:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case FIND_LIST_SUCCESS:
            return (0, _extends3.default)({}, state, {
                postList: action.result,
                loading: false
            });
        case FIND_LIST_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case FIND_ONE:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case FIND_ONE_SUCCESS:
            return (0, _extends3.default)({}, state, {
                post: action.result,
                loading: false
            });
        case FIND_ONE_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case RESET_ONE:
            return (0, _extends3.default)({}, state, {
                post: {}
            });
        case CREATE:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case CREATE_SUCCESS:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case CREATE_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case UPDATE:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case UPDATE_SUCCESS:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case UPDATE_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        case REMOVE:
            return (0, _extends3.default)({}, state, {
                loading: true
            });
        case REMOVE_SUCCESS:
            return (0, _extends3.default)({}, state, {
                postList: (0, _assign2.default)({}, state.postList, {
                    content: state.postList.content.filter(function (post) {
                        return post.idx !== action.idx;
                    })
                }),
                loading: false
            });
        case REMOVE_FAIL:
            return (0, _extends3.default)({}, state, {
                loading: false
            });
        default:
            return state;
    }
}

function findAll() {
    return {
        types: [FIND_ALL, FIND_ALL_SUCCESS, FIND_ALL_FAIL],
        promise: function promise(client) {
            return client.get('/post/findAll');
        }
    };
}

function findList() {
    var page = arguments.length <= 0 || arguments[0] === undefined ? 0 : arguments[0];

    return {
        types: [FIND_LIST, FIND_LIST_SUCCESS, FIND_LIST_FAIL],
        promise: function promise(client) {
            return client.get('/post?page=' + page);
        }
    };
}

function findOne(idx) {
    return {
        types: [FIND_ONE, FIND_ONE_SUCCESS, FIND_ONE_FAIL],
        promise: function promise(client) {
            return client.get('/post/' + idx);
        }
    };
}

function resetOne() {
    return {
        type: RESET_ONE
    };
}

function create(post) {
    return {
        types: [CREATE, CREATE_SUCCESS, CREATE_FAIL],
        promise: function promise(client) {
            return client.post('/post', {
                data: post
            });
        }
    };
}

function update(post) {
    var attach = undefined;

    if (post.file && post.file.file) {
        attach = [];
        attach.push(data.file.file);
        post.file.previewUrl = '';
    }

    return {
        types: [UPDATE, UPDATE_SUCCESS, UPDATE_FAIL],
        promise: function promise(client) {
            return client.patch('/post', {
                data: post
            });
        }
    };
}

function remove(idx) {
    return {
        types: [REMOVE, REMOVE_SUCCESS, REMOVE_FAIL],
        promise: function promise(client) {
            return client.del('/post/' + idx);
        },
        idx: idx
    };
}

//# sourceMappingURL=post-compiled.js.map
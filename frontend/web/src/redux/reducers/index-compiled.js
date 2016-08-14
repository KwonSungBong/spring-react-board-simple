'use strict';

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _redux = require('redux');

var _reactRouterRedux = require('react-router-redux');

var _reduxForm = require('redux-form');

var _reduxConnect = require('redux-connect');

var _auth = require('./auth');

var _auth2 = _interopRequireDefault(_auth);

var _post = require('./post');

var _post2 = _interopRequireDefault(_post);

var _normalize = require('../../helpers/normalize');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = (0, _redux.combineReducers)({
	routing: _reactRouterRedux.routerReducer,
	reduxAsyncConnect: _reduxConnect.reducer,
	form: _reduxForm.reducer.normalize({
		HistoryForm: {
			subject: _normalize.normalizeSubject
		}
	}),
	auth: _auth2.default,
	post: _post2.default
}); /**
     * 리듀서 전체 모음
     * 리듀서 파일 추가 시 마다 여기에 추가를 해주어야 한다.
     * Created by kwonohbin on 2016. 7. 31..
     */

module.exports = exports['default'];

//# sourceMappingURL=index-compiled.js.map
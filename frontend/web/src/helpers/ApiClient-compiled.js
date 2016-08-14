'use strict';

Object.defineProperty(exports, "__esModule", {
	value: true
});
exports.default = undefined;

var _stringify = require('babel-runtime/core-js/json/stringify');

var _stringify2 = _interopRequireDefault(_stringify);

var _promise = require('babel-runtime/core-js/promise');

var _promise2 = _interopRequireDefault(_promise);

var _classCallCheck2 = require('babel-runtime/helpers/classCallCheck');

var _classCallCheck3 = _interopRequireDefault(_classCallCheck2);

var _createClass2 = require('babel-runtime/helpers/createClass');

var _createClass3 = _interopRequireDefault(_createClass2);

var _superagent = require('superagent');

var _superagent2 = _interopRequireDefault(_superagent);

var _config = require('../config');

var _config2 = _interopRequireDefault(_config);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var methods = ['get', 'post', 'put', 'patch', 'del'];

function formatUrl(path) {
	var adjustedPath = path[0] !== '/' ? '/' + path : path;

	if (path.startsWith('http://') || path.startsWith('https://')) {
		return path;
	}

	if (__SERVER__) {
		if (path.startsWith('/auth')) {
			return 'http://' + _config2.default.secHost + ':' + _config2.default.secPort + adjustedPath.replace('/auth', '');
		}
		// Prepend host and port of the API server to the path.
		return 'http://' + _config2.default.apiHost + ':' + _config2.default.apiPort + adjustedPath;
	}
	if (path.startsWith('/auth')) {
		return adjustedPath;
	}
	// Prepend `/api` to relative URL, to proxy to API server.
	return '/api' + adjustedPath;
}

var ApiClient = function () {
	function ApiClient(req) {
		var _this = this;

		(0, _classCallCheck3.default)(this, ApiClient);

		methods.forEach(function (method) {
			return _this[method] = function (path) {
				var _ref = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

				var params = _ref.params;
				var data = _ref.data;
				var token = _ref.token;
				var authorization = _ref.authorization;
				var attach = _ref.attach;
				return new _promise2.default(function (resolve, reject) {
					var request = _superagent2.default[method](formatUrl(path));

					if (params) {
						request.query(params);
					}

					if (__SERVER__ && req.get('cookie')) {
						request.set('cookie', req.get('cookie'));
					}

					if (token) {
						request.set('X-XSRF-TOKEN', token);
					}

					if (authorization) {
						request.set('authorization', authorization);
					}

					if (attach) {
						var formData = new FormData();
						if (data) {
							formData.append("data", new Blob([(0, _stringify2.default)(data)], { type: 'application/json' }));
						}

						for (var key in attach) {
							// is the item a File?
							if (attach.hasOwnProperty(key) && attach[key] instanceof File) {
								formData.append('file', attach[key]);
							}
						}

						request.send(formData);
					} else {
						if (data) {
							request.send(data);
						}
					}

					/*if (data) {
     	request.send('data', data);
     }*/

					request.end(function (err) {
						var _ref2 = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

						var body = _ref2.body;
						return err ? reject(body || err) : resolve(body);
					});
				});
			};
		});
	}

	/*
  * There's a V8 bug where, when using Babel, exporting classes with only
  * constructors sometimes fails. Until it's patched, this is a solution to
  * "ApiClient is not defined" from issue #14.
  * https://github.com/erikras/react-redux-universal-hot-example/issues/14
  *
  * Relevant Babel bug (but they claim it's V8): https://phabricator.babeljs.io/T2455
  *
  * Remove it at your own risk.
  */

	(0, _createClass3.default)(ApiClient, [{
		key: 'empty',
		value: function empty() {}
	}]);
	return ApiClient;
}();

exports.default = ApiClient;
module.exports = exports['default'];

//# sourceMappingURL=ApiClient-compiled.js.map
'use strict';

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _getPrototypeOf = require('babel-runtime/core-js/object/get-prototype-of');

var _getPrototypeOf2 = _interopRequireDefault(_getPrototypeOf);

var _classCallCheck2 = require('babel-runtime/helpers/classCallCheck');

var _classCallCheck3 = _interopRequireDefault(_classCallCheck2);

var _createClass2 = require('babel-runtime/helpers/createClass');

var _createClass3 = _interopRequireDefault(_createClass2);

var _possibleConstructorReturn2 = require('babel-runtime/helpers/possibleConstructorReturn');

var _possibleConstructorReturn3 = _interopRequireDefault(_possibleConstructorReturn2);

var _inherits2 = require('babel-runtime/helpers/inherits');

var _inherits3 = _interopRequireDefault(_inherits2);

var _redboxReact2 = require('redbox-react');

var _redboxReact3 = _interopRequireDefault(_redboxReact2);

var _react2 = require('react');

var _react3 = _interopRequireDefault(_react2);

var _reactTransformCatchErrors3 = require('react-transform-catch-errors');

var _reactTransformCatchErrors4 = _interopRequireDefault(_reactTransformCatchErrors3);

var _reactBootstrap = require('react-bootstrap');

var _reactRouter = require('react-router');

var _reactRouterBootstrap = require('react-router-bootstrap');

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var _components = {
	Top: {
		displayName: 'Top'
	}
};

var _reactTransformCatchErrors2 = (0, _reactTransformCatchErrors4.default)({
	filename: 'C:/Users/ksb/IdeaProjects/untitled5/frontend/web/admin-view/src/components/Root/Top.js',
	components: _components,
	locals: [],
	imports: [_react3.default, _redboxReact3.default]
});

function _wrapComponent(id) {
	return function (Component) {
		return _reactTransformCatchErrors2(Component, id);
	};
} /**
   * Created by kwonohbin on 2016. 7. 31..
   */

var Top = _wrapComponent('Top')(function (_Component) {
	(0, _inherits3.default)(Top, _Component);

	function Top() {
		(0, _classCallCheck3.default)(this, Top);
		return (0, _possibleConstructorReturn3.default)(this, (0, _getPrototypeOf2.default)(Top).apply(this, arguments));
	}

	(0, _createClass3.default)(Top, [{
		key: 'render',
		value: function render() {
			return _react3.default.createElement(
				_reactBootstrap.Navbar,
				null,
				_react3.default.createElement(_reactBootstrap.Navbar.Header, null),
				_react3.default.createElement(
					_reactBootstrap.Navbar.Collapse,
					null,
					_react3.default.createElement(
						_reactBootstrap.Nav,
						null,
						_react3.default.createElement(
							_reactRouterBootstrap.LinkContainer,
							{ to: '' },
							_react3.default.createElement(
								_reactBootstrap.NavItem,
								{ eventKey: 1 },
								'홈'
							)
						),
						_react3.default.createElement(
							_reactBootstrap.NavDropdown,
							{ eventKey: 2, title: '멀티플', id: 'etc' },
							_react3.default.createElement(
								_reactRouterBootstrap.LinkContainer,
								{ to: '/post' },
								_react3.default.createElement(
									_reactBootstrap.MenuItem,
									{ eventKey: 2.1 },
									'포스트'
								)
							),
							_react3.default.createElement(
								_reactRouterBootstrap.LinkContainer,
								{ to: '/card' },
								_react3.default.createElement(
									_reactBootstrap.MenuItem,
									{ eventKey: 2.2 },
									'카드'
								)
							),
							_react3.default.createElement(
								_reactRouterBootstrap.LinkContainer,
								{ to: '/chat' },
								_react3.default.createElement(
									_reactBootstrap.MenuItem,
									{ eventKey: 2.3 },
									'채팅'
								)
							),
							_react3.default.createElement(
								_reactRouterBootstrap.LinkContainer,
								{ to: '/login' },
								_react3.default.createElement(
									_reactBootstrap.MenuItem,
									{ eventKey: 2.4 },
									'로그인'
								)
							)
						)
					)
				)
			);
		}
	}]);
	return Top;
}(_react2.Component));

exports.default = Top;
module.exports = exports['default'];

//# sourceMappingURL=Top-compiled.js.map
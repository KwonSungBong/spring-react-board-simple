import superagent from 'superagent';
import config from '../config';

const methods = ['get', 'post', 'put', 'patch', 'del'];

function formatUrl(path) {
	const adjustedPath = path[0] !== '/' ? '/' + path : path;

	if (path.startsWith('http://') || path.startsWith('https://')) {
		return path;
	}

	if (__SERVER__) {
		if(path.startsWith('/auth')){
			return 'http://' + config.secHost + ':' + config.secPort + adjustedPath.replace('/auth', '');
		}
		// Prepend host and port of the API server to the path.
		return 'http://' + config.apiHost + ':' + config.apiPort + adjustedPath;
	}
	if(path.startsWith('/auth')){
		return adjustedPath;
	}
	// Prepend `/api` to relative URL, to proxy to API server.
	return '/api' + adjustedPath;
}

export default class ApiClient {
	constructor(req) {
		methods.forEach((method) =>
			this[method] = (path, {params, data, token, authorization, attach} = {}) => new Promise((resolve, reject) => {
				const request = superagent[method](formatUrl(path));

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
					let formData = new FormData();
					if(data) {
						formData.append("data", new Blob([JSON.stringify(data)], {type : 'application/json'}));
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

				request.end((err, {body} = {}) => err ? reject(body || err) : resolve(body));
			}));
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
	empty() {
	}
}
